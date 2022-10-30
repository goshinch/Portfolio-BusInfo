package com.example.portfolio_businfo.Recyclerview.ClickItem;


import com.example.portfolio_businfo.Model.Bus_Arrive_Info;
import com.example.portfolio_businfo.Model.Bus_Line;
import com.example.portfolio_businfo.Model.Bus_Line_Station;
import com.example.portfolio_businfo.Model.Bus_Line_Station_HolderData;
import com.example.portfolio_businfo.Model.Bus_Station;
import com.example.portfolio_businfo.Recyclerview.CaseType.SearchCase;
import com.example.portfolio_businfo.Recyclerview.RecyclerViewAdapter;

import java.util.ArrayList;

public class LineItemClick extends RecyclerViewAdapter {
    public LineItemClick(SearchCase type) {
        super(type);
    }

    public String LineName(int po, ArrayList<Bus_Line> list){
        Bus_Line data = list.get(po);
        return data.getLINE_NAME();
    }
    public String LineId(int po, ArrayList<Bus_Line> list){
        Bus_Line data = list.get(po);
        return data.getLINE_ID();
    }
    public String LikeKind(int po, ArrayList<Bus_Line> list){
        Bus_Line data = list.get(po);
        return data.getLIKE_KIND();
    }
    public String First_Run_time(int po, ArrayList<Bus_Line> list){
        Bus_Line data = list.get(po);
        return data.getFIRST_RUN_TIME();
    }
    public String LastRunTime(int po, ArrayList<Bus_Line> list){
        Bus_Line data = list.get(po);
        return data.getLAST_RUN_TIME();
    }
    public String RunInterval(int po, ArrayList<Bus_Line> list){
        Bus_Line data = list.get(po);
        return data.getRUN_INTERVAL();
    }
    public String DirUp(int po, ArrayList<Bus_Line> list){
        Bus_Line data = list.get(po);
        return data.getDIR_UP_NAME();
    }
    public String DirDown(int po, ArrayList<Bus_Line> list){
        Bus_Line data = list.get(po);
        return data.getDIR_DOWN_NAME();
    }


    public String StationName(int po, ArrayList<Bus_Station> list){
        Bus_Station data = list.get(po);
        return data.getBUSSTOP_NAME();
    }
    public String StationId(int po, ArrayList<Bus_Station> list){
        Bus_Station data = list.get(po);
        return data.getBUSSTOP_ID();
    }
    public String StationArs(int po, ArrayList<Bus_Station> list){
        Bus_Station data = list.get(po);
        return data.getARS_ID();
    }
    public String StationNext(int po, ArrayList<Bus_Station> list){
        Bus_Station data = list.get(po);
        return data.getNEXT_BUSSTOP();
    }


    public String ArriveLineName(int po, ArrayList<Bus_Arrive_Info> list){
        Bus_Arrive_Info data = list.get(po);
        return data.getLINE_NAME();
    }
    public String ArriveLineId(int po, ArrayList<Bus_Arrive_Info> list){
        Bus_Arrive_Info data = list.get(po);
        return data.getLINE_ID();
    }
    public String ArriveLineKind(int po, ArrayList<Bus_Arrive_Info> list){
        Bus_Arrive_Info data = list.get(po);
        return data.getLINE_KIND();
    }
    public String ArriveDIR_START(int po, ArrayList<Bus_Arrive_Info> list){
        Bus_Arrive_Info data = list.get(po);
        return data.getDIR_START();
    }
    public String ArriveDIR_END(int po, ArrayList<Bus_Arrive_Info> list){
        Bus_Arrive_Info data = list.get(po);
        return data.getDIR_END();
    }


    public String LineStationARS_ID(int po, ArrayList<Bus_Line_Station> list){
        Bus_Line_Station data = list.get(po);
        return data.getARS_ID();
    }
    public String LineStationBusstop_Name(int po, ArrayList<Bus_Line_Station> list) {
        Bus_Line_Station data = list.get(po);
        return data.getBUSSTOP_NAME();
    }
    public String LineStationBusstop_ID(int po, ArrayList<Bus_Line_Station> list) {
        Bus_Line_Station data = list.get(po);
        return data.getBUSSTOP_ID();
    }
}
