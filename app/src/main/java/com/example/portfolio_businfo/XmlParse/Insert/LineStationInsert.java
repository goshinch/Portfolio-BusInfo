package com.example.portfolio_businfo.XmlParse.Insert;

import android.util.Log;

import com.example.portfolio_businfo.Model.Bus_Line_Station;
import com.example.portfolio_businfo.XmlParse.Insert_Api;

import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.HashMap;

public class LineStationInsert extends Insert_Api {
    private NodeList tagList = null;
    private ArrayList<Bus_Line_Station> LineStation_result_lists = new ArrayList<>();

    public ArrayList<Bus_Line_Station> BusLineStation(String url1, String url2) {
        LineStation_result_lists.clear();
        Log.d("BusLineStation: ", url1 + url2);
        try {
            tagList = xml(url1, url2);
            Log.d("BusLineStation: ", String.valueOf(tagList));
            HashMap<String, String> map = new HashMap<>();
            for (int i = 0; i < tagList.getLength(); ++i) {
                NodeList chilData = tagList.item(i).getChildNodes();
                for (int j = 0; j < chilData.getLength(); ++j) {
                    switch (chilData.item(j).getNodeName()) {
                        case "LINE_ID":
                        case "BUSSTOP_ID":
                        case "BUSSTOP_NAME":
                        case "ARS_ID":
                        case "RETURN_FLAG":
                            map.put(chilData.item(j).getNodeName(), chilData.item(j).getTextContent());
                            break;
                    }
                }
                LineStation_result_lists.add(new Bus_Line_Station(
                        map.containsKey("LINE_ID") ? map.get("LINE_ID") : "null",
                        map.containsKey("BUSSTOP_ID") ? map.get("BUSSTOP_ID") : "null",
                        map.containsKey("BUSSTOP_NAME") ? map.get("BUSSTOP_NAME") : "null",
                        map.containsKey("ARS_ID") ? map.get("ARS_ID") : "0000",
                        map.containsKey("RETURN_FLAG") ? map.get("RETURN_FLAG") : "null", false));
                map.clear();
            }
            for (Bus_Line_Station i : LineStation_result_lists)
                Log.d("BusLineStation: ", String.valueOf(i.getBUSSTOP_ID() + "/" + i.getLINE_ID() + "/" + i.getRETURN_FLAG() + "/" + i.isExist()));
    }
        catch (Exception e) {
            e.printStackTrace();
        }

        return LineStation_result_lists;
    }
}
