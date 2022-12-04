package com.portal.exchangerate.service;

import com.portal.exchangerate.job.DataLoaderJob;
import com.portal.exchangerate.model.Currency;
import com.portal.exchangerate.repository.CurrencyRepository;
import com.portal.exchangerate.utils.HttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;

@Service
public class CurrencyLoader extends DataLoader{
    private static final Logger log = LoggerFactory.getLogger(DataLoaderJob.class);

    @Autowired
    private final CurrencyRepository currencyRepository = null;

    @Override
    public void reset() {
        currencyRepository.deleteAll();
    }

    @Override
    public StringBuilder ladXmlDataFromInternet() {
        HttpURLConnection connection = null;
        try {
            connection = HttpUtils.soap2Connection();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        //String xml = HttpUtils.getFxRatesForCurrencyOutputXML("USD", "2022-10-11", "2022-11-11");
        //.getCurrentFxRatesOutputXML();
        String xml = HttpUtils.getCurrencyListOutputXML();
        StringBuilder response = null;
        try {
            response = HttpUtils.response(connection, xml);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return response;
    }

    @Override
    public void loadXmlDataToDB(StringBuilder xmlData) {
        try{
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            ByteArrayInputStream input = new ByteArrayInputStream(xmlData.toString().getBytes("UTF-8"));
            log.info("xmlData.toString() = "+xmlData.toString());
            Document doc = builder.parse(input);
            Element root = doc.getDocumentElement();

            NodeList nList = doc.getElementsByTagName("CcyNtry");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;

                    String currencyName = eElement.getElementsByTagName("Ccy").item(0).getTextContent();
                    String descriptionLT = eElement.getElementsByTagName("CcyNm").item(0).getTextContent();
                    String descriptionEN = eElement.getElementsByTagName("CcyNm").item(1).getTextContent();
                    String isoNumber = eElement.getElementsByTagName("CcyNbr").item(0).getTextContent();
                    String exponentUnits = eElement.getElementsByTagName("CcyMnrUnts").item(0).getTextContent();

                    Currency currency = new Currency();
                    currency.setName(currencyName);
                    currency.setDescriptionLt(descriptionLT);
                    currency.setDescriptionEn(descriptionEN);
                    currency.setIsoNumber(isoNumber);
                    currency.setExponentUnits(Integer.parseInt(exponentUnits));
                    currencyRepository.save(currency);

                }
            }
        }catch (Exception e){
            log.error(e.getMessage(), e);
        }

    }

    public void test(){

    }
}
