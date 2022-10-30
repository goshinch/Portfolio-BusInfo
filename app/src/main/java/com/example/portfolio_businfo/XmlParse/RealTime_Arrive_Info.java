package com.example.portfolio_businfo.XmlParse;

import android.content.Context;
import android.util.Log;

import androidx.room.Room;

import com.example.portfolio_businfo.Model.Bus_Arrive_Info;
import com.example.portfolio_businfo.Model.Bus_Line_Station;
import com.example.portfolio_businfo.Model.Bus_Line_Station_HolderData;
import com.example.portfolio_businfo.Room.Bus_Database;

import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.HashMap;

public class RealTime_Arrive_Info extends Insert_Api {
    private Context context;
    private NodeList tagList;
    private ArrayList<String> tmpcurrId = new ArrayList<>();
    private ArrayList<Bus_Arrive_Info> tempList = new ArrayList<>();
    private String tmp = "";

    public RealTime_Arrive_Info(Context context) {
        this.context = context;
    }

    public ArrayList<Bus_Arrive_Info> select_Arrive(String url1, String url2) {
        Bus_Database db = Room.databaseBuilder(context, Bus_Database.class, "BusData")
                .build();
        tagList = xml(url1, url2);
        HashMap<String, String> map = new HashMap<>(15);
        for (int i = 0; i < tagList.getLength(); ++i) {
            NodeList chilData = tagList.item(i).getChildNodes();
            for (int j = 0; j < chilData.getLength(); ++j) {
                switch (chilData.item(j).getNodeName()) {
                    case "LINE_ID":
                    case "LINE_NAME":
                    case "BUS_ID":
                    case "METRO_FLAG":
                    case "CURR_STOP_ID":
                    case "BUSSTOP_NAME":
                    case "REMAIN_MIN":
                    case "REMAIN_STOP":
                    case "DIR_START":
                    case "DIR_END":
                    case "LOW_BUS":
                    case "ENG_BUSSTOP_NAME":
                    case "ARRIVE_FLAG":
                        map.put(chilData.item(j).getNodeName(), chilData.item(j).getTextContent());
                        break;
                }
            }

            map.put("LINE_KIND", db.dao().getLine_Kind(map.get("LINE_ID")).getLIKE_KIND());
            tempList.add(new Bus_Arrive_Info(
                    map.containsKey("LINE_ID") ? map.get("LINE_ID") : "null",
                    map.containsKey("LINE_NAME") ? map.get("LINE_NAME") : "null",
                    map.containsKey("BUS_ID") ? map.get("BUS_ID") : "null",
                    map.containsKey("METRO_FLAG") ? map.get("METRO_FLAG") : "null",
                    map.containsKey("CURR_STOP_ID") ? map.get("CURR_STOP_ID") : "null",
                    map.containsKey("BUSSTOP_NAME") ? map.get("BUSSTOP_NAME") : "null",
                    map.containsKey("REMAIN_MIN") ? map.get("REMAIN_MIN") : "null",
                    map.containsKey("REMAIN_STOP") ? map.get("REMAIN_STOP") : "null",
                    map.containsKey("DIR_START") ? map.get("DIR_START") : "null",
                    map.containsKey("DIR_END") ? map.get("DIR_END") : "null",
                    map.containsKey("LOW_BUS") ? map.get("LOW_BUS") : "null",
                    map.containsKey("ENG_BUSSTOP_NAME") ? map.get("ENG_BUSSTOP_NAME") : "null",
                    map.containsKey("ARRIVE_FLAG") ? map.get("ARRIVE_FLAG") : "null",
                    map.containsKey("LINE_KIND") ? map.get("LINE_KIND") : "null"));
            map.clear();
        }
        Log.d("select_Arrive: ", String.valueOf(tempList.size()));

        return tempList;
    }

    public ArrayList<String> select_Arrive_Position(String url1, String url2, ArrayList<Bus_Line_Station> searchReal, String line_id) {
        try {
            if (tmpcurrId == null) tmpcurrId = new ArrayList<>();
            for (Bus_Line_Station s : searchReal) {
                tagList = new Insert_Api().xml(url1, url2 + s.getBUSSTOP_ID());
                for (int i = 0; i < tagList.getLength(); ++i) {
                    NodeList chilData = tagList.item(i).getChildNodes();
                    if (chilData.item(1).getTextContent().equals(line_id)) {
                        if (!(tmp.equals(chilData.item(9).getTextContent()))) {
                            for (int j = 3; j < chilData.getLength(); ++j) {
                                switch (chilData.item(j).getNodeName()) {
                                    case "CURR_STOP_ID":
                                        tmpcurrId.add(chilData.item(j).getTextContent());
                                        tmp = chilData.item(j).getTextContent();
                                        break;
                                }
                            }
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        for (String s : tmpcurrId)
            Log.d("::::::====================", String.valueOf(s));
        return tmpcurrId;
    }
}

