package com.example.portfolio_businfo.XmlParse.Insert;


import android.util.Log;

import com.example.portfolio_businfo.Model.Bus_Line;
import com.example.portfolio_businfo.XmlParse.Insert_Api;

import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.HashMap;

public class LineInsert extends Insert_Api {

    private ArrayList<Bus_Line> Line_result_lists = new ArrayList<>();

    public ArrayList<Bus_Line> BusLine(String url1, String url2) {
        NodeList tagList = xml(url1,url2);
        HashMap<String,String> map = new HashMap<>(10);
        for (int i = 0; i < tagList.getLength(); i++) {
            NodeList chilData = tagList.item(i).getChildNodes();
            for (int j = 0; j < chilData.getLength(); j++) {
                switch (chilData.item(j).getNodeName()) {
                    case "LINE_ID":
                    case "LINE_NAME":
                    case "DIR_UP_NAME":
                    case "DIR_DOWN_NAME":
                    case "FIRST_RUN_TIME":
                    case "LAST_RUN_TIME":
                    case "RUN_INTERVAL":
                    case "LINE_KIND":
                        map.put(chilData.item(j).getNodeName(), chilData.item(j).getTextContent());
                        break;
                }
            }
            Line_result_lists.add( new Bus_Line(
                    map.containsKey("LINE_ID") ? map.get("LINE_ID") : "null",
                    map.containsKey("LINE_NAME") ? map.get("LINE_NAME") : "null",
                    map.containsKey("DIR_UP_NAME") ? map.get("DIR_UP_NAME") : "null",
                    map.containsKey("DIR_DOWN_NAME") ? map.get("DIR_DOWN_NAME") : "null",
                    map.containsKey("FIRST_RUN_TIME") ? map.get("FIRST_RUN_TIME") : "null",
                    map.containsKey("LAST_RUN_TIME") ? map.get("LAST_RUN_TIME") : "null",
                    map.containsKey("RUN_INTERVAL") ? map.get("RUN_INTERVAL") : "null",
                    map.containsKey("LINE_KIND") ? map.get("LINE_KIND") : "null", false));
            map.clear();
        }
        Log.d( "BusStation: ", String.valueOf(Line_result_lists.size()));
        return Line_result_lists;
    }
}
