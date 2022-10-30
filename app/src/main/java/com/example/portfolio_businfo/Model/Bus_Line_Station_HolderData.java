package com.example.portfolio_businfo.Model;

import androidx.room.ColumnInfo;

public class Bus_Line_Station_HolderData {
    @ColumnInfo(name = "busstop_id")
    String BUSSTOP_ID;
    @ColumnInfo(name = "busstop_name")
    String BUSSTOP_NAME;
    @ColumnInfo(name = "ars_id")
    String ARS_ID;
    @ColumnInfo(name = "return_frag")
    String RETURN_FRAG;
    @ColumnInfo(name = "exist")
    boolean Exist;

    public Bus_Line_Station_HolderData(String BUSSTOP_ID, String BUSSTOP_NAME, String ARS_ID, String RETURN_FRAG, boolean Exist) {
        this.BUSSTOP_ID = BUSSTOP_ID;
        this.BUSSTOP_NAME = BUSSTOP_NAME;
        this.ARS_ID = ARS_ID;
        this.RETURN_FRAG = RETURN_FRAG;
        this.Exist = Exist;
    }

    public String getBUSSTOP_ID() {
        return BUSSTOP_ID;
    }

    public String getBUSSTOP_NAME() {
        return BUSSTOP_NAME;
    }

    public String getARS_ID() {
        return ARS_ID;
    }

    public String getRETURN_FRAG() {
        return RETURN_FRAG;
    }

    public boolean isExist() {
        return Exist;
    }

    public void setExist(boolean Exist) {
        this.Exist = Exist;
    }
}
