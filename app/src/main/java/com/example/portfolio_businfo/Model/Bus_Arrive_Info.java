package com.example.portfolio_businfo.Model;

public class Bus_Arrive_Info {
    String LINE_ID,
            LINE_NAME,
            BUS_ID,
            METRO_FLAG,
            CURR_STOP_ID,
            BUSSTOP_NAME,
            REMAIN_MIN,
            REMAIN_STOP,
            DIR_START,
            DIR_END,
            LOW_BUS,
            ENG_BUSSTOP_NAME,
            ARRIVE_FLAG,
            LINE_KIND;

    public String getLINE_ID() {
        return LINE_ID;
    }

    public String getLINE_NAME() {
        return LINE_NAME;
    }

    public String getBUS_ID() {
        return BUS_ID;
    }

    public String getMETRO_FLAG() {
        return METRO_FLAG;
    }

    public String getCURR_STOP_ID() {
        return CURR_STOP_ID;
    }

    public String getBUSSTOP_NAME() {
        return BUSSTOP_NAME;
    }

    public String getREMAIN_MIN() {
        return REMAIN_MIN;
    }

    public String getREMAIN_STOP() {
        return REMAIN_STOP;
    }

    public String getDIR_START() {
        return DIR_START;
    }

    public String getDIR_END() {
        return DIR_END;
    }

    public String getLOW_BUS() {
        return LOW_BUS;
    }

    public String getENG_BUSSTOP_NAME() {
        return ENG_BUSSTOP_NAME;
    }

    public String getARRIVE_FLAG() {
        return ARRIVE_FLAG;
    }

    public String getLINE_KIND() {
        return LINE_KIND;
    }



    public void setREMAIN_MIN(String REMAIN_MIN) {
        this.REMAIN_MIN = REMAIN_MIN;
    }

    public Bus_Arrive_Info(String LINE_ID, String LINE_NAME, String BUS_ID, String METRO_FLAG, String CURR_STOP_ID, String BUSSTOP_NAME, String REMAIN_MIN, String REMAIN_STOP, String DIR_START, String DIR_END, String LOW_BUS, String ENG_BUSSTOP_NAME, String ARRIVE_FLAG, String LINE_KIND) {
        this.LINE_ID = LINE_ID;
        this.LINE_NAME = LINE_NAME;
        this.BUS_ID = BUS_ID;
        this.METRO_FLAG = METRO_FLAG;
        this.CURR_STOP_ID = CURR_STOP_ID;
        this.BUSSTOP_NAME = BUSSTOP_NAME;
        this.REMAIN_MIN = REMAIN_MIN;
        this.REMAIN_STOP = REMAIN_STOP;
        this.DIR_START = DIR_START;
        this.DIR_END = DIR_END;
        this.LOW_BUS = LOW_BUS;
        this.ENG_BUSSTOP_NAME = ENG_BUSSTOP_NAME;
        this.ARRIVE_FLAG = ARRIVE_FLAG;
        this.LINE_KIND = LINE_KIND;

    }
}