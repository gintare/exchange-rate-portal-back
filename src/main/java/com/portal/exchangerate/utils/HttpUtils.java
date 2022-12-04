package com.portal.exchangerate.utils;

import com.portal.exchangerate.info.Constants;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

public class HttpUtils {

    public static HttpURLConnection soap2Connection() throws IOException {

        URL url = new URL(Constants.SOAP12_LINK);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(Constants.POST);
        connection.setRequestProperty(Constants.ACCEPT_CHARSET, Constants.UTF_8);
        connection.setRequestProperty(Constants.CONTENT_TYPE, Constants.CONTENT_TYPE_TEXT);
        return connection;
    }

    public static String getCurrencyListOutputXML(){
        return "<?xml version=\"1.0\" encoding=\"utf-8\"?> " +
                "<soap12:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap12=\"http://www.w3.org/2003/05/soap-envelope\"> " +
                "<soap12:Body> <getCurrencyList xmlns=\"http://www.lb.lt/WebServices/FxRates\" /> " +
                "</soap12:Body> </soap12:Envelope>";
    }

    public static String getCurrentFxRatesOutputXML() {
        return "<?xml version=\"1.0\" encoding=\"utf-8\"?> " +
                "<soap12:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap12=\"http://www.w3.org/2003/05/soap-envelope\"> " +
                "<soap12:Body> <getCurrentFxRates xmlns=\"http://www.lb.lt/WebServices/FxRates\"> " +
                "<tp>EU</tp> " +
                "</getCurrentFxRates> </soap12:Body> </soap12:Envelope>";
    }

    public static String getFxRatesForCurrencyOutputXML(String currencyName, String dateFrom, String dateTo){
        return "<?xml version=\"1.0\" encoding=\"utf-8\"?> " +
                "<soap12:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap12=\"http://www.w3.org/2003/05/soap-envelope\"> " +
                "<soap12:Body> " +
                "<getFxRatesForCurrency xmlns=\"http://www.lb.lt/WebServices/FxRates\"> " +
                "<tp>EU</tp> <ccy>"+currencyName+"</ccy> <dtFrom>"+dateFrom+"</dtFrom> <dtTo>"+dateTo+"</dtTo> " +
                "</getFxRatesForCurrency> " +
                "</soap12:Body> </soap12:Envelope>";
    }

    public static StringBuilder response(HttpURLConnection connection, String xml) throws IOException {
        connection.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
        wr.writeBytes(xml);
        wr.flush();
        wr.close();
        String responseStatus = connection.getResponseMessage();
        System.out.println(responseStatus);
        Charset charset = Charset.forName("UTF8");
        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), charset));
        StringBuilder response = new StringBuilder();
        String str;
        while (null != (str = br.readLine())) {
            response.append(str);//.append("\r\n");
        }
        br.close();

        return response;
    }
}
