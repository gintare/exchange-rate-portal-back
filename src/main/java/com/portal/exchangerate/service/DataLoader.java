package com.portal.exchangerate.service;


abstract class DataLoader {

    abstract void reset();
    abstract StringBuilder ladXmlDataFromInternet();
    abstract void loadXmlDataToDB(StringBuilder xmlData);

}
