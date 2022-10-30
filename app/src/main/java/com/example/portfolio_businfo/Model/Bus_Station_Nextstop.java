package com.example.portfolio_businfo.Model;

import androidx.room.ColumnInfo;

public class Bus_Station_Nextstop {
    @ColumnInfo(name = "next_busstop")
    String NEXT_BUSSTOP;

    public Bus_Station_Nextstop(String NEXT_BUSSTOP) {
        this.NEXT_BUSSTOP = NEXT_BUSSTOP;
    }

    public String getNEXT_BUSSTOP() {
        return NEXT_BUSSTOP;
    }
}
