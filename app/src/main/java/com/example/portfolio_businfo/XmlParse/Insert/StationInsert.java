package com.example.portfolio_businfo.XmlParse.Insert;

import android.util.Log;

import com.example.portfolio_businfo.Model.Bus_Station;
import com.example.portfolio_businfo.XmlParse.Insert_Api;

import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.HashMap;

public class StationInsert extends Insert_Api {
    private ArrayList<Bus_Station> Station_result_lists = new ArrayList<>();
    public ArrayList<Bus_Station> BusStation(String url1, String url2) {
        NodeList tagList = xml(url1,url2);
        HashMap<String,String> map = new HashMap<>(10);
        for (int i = 0; i < tagList.getLength(); ++i) {
            NodeList chilData = tagList.item(i).getChildNodes();
            for (int j = 0; j < chilData.getLength(); ++j) {
                switch (chilData.item(j).getNodeName()) {
                    case "BUSSTOP_ID":
                    case "BUSSTOP_NAME":
                    case "NAME_E":
                    case "LONGITUDE":
                    case "LATITUDE":
                    case "ARS_ID":
                    case "NEXT_BUSSTOP":
                        map.put(chilData.item(j).getNodeName(), chilData.item(j).getTextContent());
                        break;
                }
            }
            Station_result_lists.add( new Bus_Station(
                    map.containsKey("BUSSTOP_ID") ? map.get("BUSSTOP_ID") : "null",
                    map.containsKey("BUSSTOP_NAME") ? map.get("BUSSTOP_NAME") : "null",
                    map.containsKey("NAME_E") ? map.get("NAME_E") : "null",
                    map.containsKey("LONGITUDE") ? map.get("LONGITUDE") : "null",
                    map.containsKey("LATITUDE") ? map.get("LATITUDE") : "null",
                    map.containsKey("ARS_ID") ? map.get("ARS_ID") : "0000",
                    map.containsKey("NEXT_BUSSTOP") ? map.get("NEXT_BUSSTOP") : "null", false));
            map.clear();
        }
        Log.d( "BusStation: ", String.valueOf(Station_result_lists.size()));
        return Station_result_lists;
    }
}
