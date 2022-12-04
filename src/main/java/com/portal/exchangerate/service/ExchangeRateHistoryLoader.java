package com.portal.exchangerate.service;

import com.portal.exchangerate.model.Currency;
import com.portal.exchangerate.model.ExchangeRateCurrent;
import com.portal.exchangerate.model.ExchangeRateHistory;
import com.portal.exchangerate.repository.CurrencyRepository;
import com.portal.exchangerate.repository.ExchangeRateHistoryRepository;
import com.portal.exchangerate.utils.HttpUtils;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.text.SimpleDateFormat;
import java.util.List;

@Service
@Setter
@Getter
public class ExchangeRateHistoryLoader extends DataLoader{
    private static final Logger log = LoggerFactory.getLogger(ExchangeRateHistoryLoader.class);
    private String currecyName = null;
    private String dateFrom = null;
    private String dateTo = null;

    @Autowired
    private final ExchangeRateHistoryRepository exchangeRateHistoryRepository = null;
    @Autowired
    private final CurrencyRepository currencyRepository = null;

    @Override
    public void reset() {
        exchangeRateHistoryRepository.deleteAll();
    }

    @Override
    public StringBuilder ladXmlDataFromInternet() {
        HttpURLConnection connection = null;
        try {
            connection = HttpUtils.soap2Connection();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        String xml = HttpUtils.getFxRatesForCurrencyOutputXML(this.getCurrecyName(), this.getDateFrom(), this.getDateTo());
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
            log.info("input = "+input);
            Document doc = builder.parse(input);
            Element root = doc.getDocumentElement();

            NodeList nList = doc.getElementsByTagName("FxRate");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;

                    String type = eElement.getElementsByTagName("Tp").item(0).getTextContent();
                    String date = eElement.getElementsByTagName("Dt").item(0).getTextContent();
                    String currencyName = eElement.getElementsByTagName("Ccy").item(1).getTextContent();
                    String amount = eElement.getElementsByTagName("Amt").item(1).getTextContent();

                    List<Currency> currencies = this.currencyRepository.findByName(currencyName);
                    int currencyId = currencies.get(0).getId();
                    log.info("currencyId = "+currencyId+", date = "+date+", amount = "+amount);

                    ExchangeRateHistory history = new ExchangeRateHistory();
                    history.setCurrencyId(currencyId);
                    history.setCurrencyDate(new SimpleDateFormat("yyyy-MM-dd").parse(date));
                    history.setAmount(Double.parseDouble(amount));
                    history.setCurrency(currencies.get(0));
                    this.exchangeRateHistoryRepository.save(history);

                }
            }
        }catch (Exception e){
            log.error(e.getMessage(), e);
        }
    }
}
