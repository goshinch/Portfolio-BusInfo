package com.example.portfolio_businfo.Model;

import androidx.room.ColumnInfo;

public class Bus_Line_Indiv {
    @ColumnInfo(name = "first_run")
    private String FIRST_RUN_TIME;
    @ColumnInfo(name = "last_run")
    private String LAST_RUN_TIME;
    @ColumnInfo(name = "run_interval")
    private String RUN_INTERVAL;
    @ColumnInfo(name = "like_kind")
    private String LIKE_KIND;

    public Bus_Line_Indiv(String FIRST_RUN_TIME, String LAST_RUN_TIME, String RUN_INTERVAL, String LIKE_KIND) {
        this.FIRST_RUN_TIME = FIRST_RUN_TIME;
        this.LAST_RUN_TIME = LAST_RUN_TIME;
        this.RUN_INTERVAL = RUN_INTERVAL;
        this.LIKE_KIND = LIKE_KIND;
    }

    public String getFIRST_RUN_TIME() {
        return FIRST_RUN_TIME;
    }

    public String getLAST_RUN_TIME() {
        return LAST_RUN_TIME;
    }

    public String getRUN_INTERVAL() {
        return RUN_INTERVAL;
    }

    public String getLIKE_KIND() {
        return LIKE_KIND;
    }
}
