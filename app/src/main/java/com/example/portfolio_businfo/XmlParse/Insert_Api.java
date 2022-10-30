package com.example.portfolio_businfo.XmlParse;


import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import java.net.URLEncoder;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

// 매번 클래스를 생성하기 보단 인터페이스 클래스로 만드는게 효율이 좋을듯 하다.
public class Insert_Api {
    public NodeList xml(String url1, String url2){
        DocumentBuilderFactory factory = null;
        DocumentBuilder builder = null;
        StringBuilder urlBuilder = null;
        NodeList tagList = null;
        Document document = null;
        try {
            factory = DocumentBuilderFactory.newInstance();
            builder = factory.newDocumentBuilder();
            /**광주광역시에서 제공한 버스 데이터 URL*/
            urlBuilder = new StringBuilder("//https://bus.gwangju.go.kr/guide/usemethod/apiMethod# 해당 사이트에서 제공되는 openAPI 참고//" + url1);
            urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8")
                    + url2); /*Service Key*/
            Log.d("xml url: ", String.valueOf(urlBuilder));
            document = builder.parse(urlBuilder.toString());
            Log.d("xml doc: ", document.toString());
            document.getDocumentElement().normalize();
            switch (url1) {
                case "lineInfo":
                    tagList = document.getElementsByTagName("LINE");
                    Log.d("xml: ", tagList.toString());
                    return tagList;
                case "stationInfo":
                    tagList = document.getElementsByTagName("STATION");
                    Log.d("xml: ", tagList.toString());
                    return tagList;
                case "lineStationInfo":
                    tagList = document.getElementsByTagName("BUSSTOP");
                    Log.d("xml: ", tagList.toString());
                    return tagList;
                case "arriveInfo":
                    tagList = document.getElementsByTagName("ARRIVE");
                    Log.d("xml: ", tagList.toString());
                    return tagList;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
