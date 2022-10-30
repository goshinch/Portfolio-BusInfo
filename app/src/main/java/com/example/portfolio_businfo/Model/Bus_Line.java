package com.example.portfolio_businfo.Model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "line")
public class Bus_Line {
    @PrimaryKey(autoGenerate = false)
    @NonNull
    @ColumnInfo(name = "line_id")
    private String LINE_ID;
    @ColumnInfo(name = "line_name")
    private String LINE_NAME;
    @ColumnInfo(name = "dir_up")
    private String DIR_UP_NAME;
    @ColumnInfo(name = "dir_down")
    private String DIR_DOWN_NAME;
    @ColumnInfo(name = "first_run")
    private String FIRST_RUN_TIME;
    @ColumnInfo(name = "last_run")
    private String LAST_RUN_TIME;
    @ColumnInfo(name = "run_interval")
    private String RUN_INTERVAL;
    @ColumnInfo(name = "like_kind")
    private String LIKE_KIND;
    @ColumnInfo(name = "checked")
    private boolean CHECK;

    public Bus_Line(@NonNull String LINE_ID, String LINE_NAME, String DIR_UP_NAME, String DIR_DOWN_NAME, String FIRST_RUN_TIME, String LAST_RUN_TIME, String RUN_INTERVAL, String LIKE_KIND, boolean CHECK) {
        this.LINE_ID = LINE_ID;
        this.LINE_NAME = LINE_NAME;
        this.DIR_UP_NAME = DIR_UP_NAME;
        this.DIR_DOWN_NAME = DIR_DOWN_NAME;
        this.FIRST_RUN_TIME = FIRST_RUN_TIME;
        this.LAST_RUN_TIME = LAST_RUN_TIME;
        this.RUN_INTERVAL = RUN_INTERVAL;
        this.LIKE_KIND = LIKE_KIND;
        this.CHECK = CHECK;
    }

    @NonNull
    public String getLINE_ID() {
        return LINE_ID;
    }

    public void setLINE_ID(@NonNull String LINE_ID) {
        this.LINE_ID = LINE_ID;
    }

    public String getLINE_NAME() {
        return LINE_NAME;
    }

    public void setLINE_NAME(String LINE_NAME) {
        this.LINE_NAME = LINE_NAME;
    }

    public String getDIR_UP_NAME() {
        return DIR_UP_NAME;
    }

    public void setDIR_UP_NAME(String DIR_UP_NAME) {
        this.DIR_UP_NAME = DIR_UP_NAME;
    }

    public String getDIR_DOWN_NAME() {
        return DIR_DOWN_NAME;
    }

    public void setDIR_DOWN_NAME(String DIR_DOWN_NAME) {
        this.DIR_DOWN_NAME = DIR_DOWN_NAME;
    }

    public String getFIRST_RUN_TIME() {
        return FIRST_RUN_TIME;
    }

    public void setFIRST_RUN_TIME(String FIRST_RUN_TIME) {
        this.FIRST_RUN_TIME = FIRST_RUN_TIME;
    }

    public String getLAST_RUN_TIME() {
        return LAST_RUN_TIME;
    }

    public void setLAST_RUN_TIME(String LAST_RUN_TIME) {
        this.LAST_RUN_TIME = LAST_RUN_TIME;
    }

    public String getRUN_INTERVAL() {
        return RUN_INTERVAL;
    }

    public void setRUN_INTERVAL(String RUN_INTERVAL) {
        this.RUN_INTERVAL = RUN_INTERVAL;
    }

    public String getLIKE_KIND() {
        return LIKE_KIND;
    }

    public void setLIKE_KIND(String LIKE_KIND) {
        this.LIKE_KIND = LIKE_KIND;
    }

    public boolean isCHECK() {
        return CHECK;
    }

    public void setCHECK(boolean CHECK) {
        this.CHECK = CHECK;
    }
}
