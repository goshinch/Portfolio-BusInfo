package com.example.portfolio_businfo.XmlParse;


import android.content.Context;
import android.util.Log;

import com.example.portfolio_businfo.Model.Bus_Arrive_Info;
import com.example.portfolio_businfo.Model.Bus_Line;
import com.example.portfolio_businfo.Model.Bus_Line_Station;
import com.example.portfolio_businfo.Model.Bus_Station;
import com.example.portfolio_businfo.XmlParse.Insert.LineInsert;
import com.example.portfolio_businfo.XmlParse.Insert.LineStationInsert;
import com.example.portfolio_businfo.XmlParse.Insert.StationInsert;

import java.util.ArrayList;

public class Bus_SplashData_api {

    /**get data from 'data.go.kr' 광주광역시 BIS 노선정보*/
    public ArrayList<Bus_Line> lines() {
        return new LineInsert().BusLine("lineInfo","//Encoding key//");
    }

    /**get data from 'data.go.kr' 광주광역시 BIS 정류소 정보*/
    public ArrayList<Bus_Station> stations() {

        return new StationInsert().BusStation("stationInfo","//Encoding key//");
    }

    /**get data from 'data.go.kr' 노선-정류소 정보*/
    public ArrayList<Bus_Line_Station> lineStations(String LineId) {
        return new LineStationInsert().BusLineStation("lineStationInfo",
                "//Encoding key//"+LineId);
    }

    /**get data from 'data.go.kr' 광주광역시 BIS 도착정보*/
    public ArrayList<Bus_Arrive_Info> select_Arrive(Context context, String Busstop_Id) {
        return new RealTime_Arrive_Info(context).select_Arrive("arriveInfo",
                "//Encoding key//" +
                        Busstop_Id);
    }

    /**get data from 'data.go.kr' 광주광역시 BIS 도착정보*/
    public ArrayList<String> select_Arrive_Position(Context context, ArrayList<Bus_Line_Station> searchReal, String line_id) {
        return new RealTime_Arrive_Info(context).select_Arrive_Position("arriveInfo",
                "//Encoding key//", searchReal, line_id );
    }
}
