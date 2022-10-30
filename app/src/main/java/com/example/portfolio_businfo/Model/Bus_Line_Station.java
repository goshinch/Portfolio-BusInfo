package com.example.portfolio_businfo.Model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "linestation")
public class Bus_Line_Station {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "ls_id")
    int LS_id;
    @ColumnInfo(name = "stop_id")
    String BUSSTOP_ID;
    @ColumnInfo(name = "busstop_name")
    String BUSSTOP_NAME;
    @ColumnInfo(name = "ars_id")
    String ARS_ID;
    @ColumnInfo(name = "return_flag")
    String RETURN_FLAG;
    @ColumnInfo(name = "lineid")
    String LINE_ID;
    @ColumnInfo(name = "exist")
    boolean Exist;

    public Bus_Line_Station(@NonNull String LINE_ID, String BUSSTOP_ID, String BUSSTOP_NAME, String ARS_ID, String RETURN_FLAG, boolean Exist) {
        this.LINE_ID = LINE_ID;
        this.BUSSTOP_ID = BUSSTOP_ID;
        this.BUSSTOP_NAME = BUSSTOP_NAME;
        this.ARS_ID = ARS_ID;
        this.RETURN_FLAG = RETURN_FLAG;
        this.Exist = Exist;
    }

    public int getLS_id() {
        return LS_id;
    }

    public void setLS_id(int LS_id) {
        this.LS_id = LS_id;
    }

    public String getLINE_ID() {
        return LINE_ID;
    }

    public void setLINE_ID(String LINE_ID) {
        this.LINE_ID = LINE_ID;
    }

    public String getBUSSTOP_ID() {
        return BUSSTOP_ID;
    }

    public void setBUSSTOP_ID(String BUSSTOP_ID) {
        this.BUSSTOP_ID = BUSSTOP_ID;
    }

    public String getRETURN_FLAG() {
        return RETURN_FLAG;
    }

    public void setRETURN_FLAG(String RETURN_FLAG) {
        this.RETURN_FLAG = RETURN_FLAG;
    }

    public boolean isExist() {
        return Exist;
    }

    public void setExist(boolean Exist) {
        this.Exist = Exist;
    }

    public String getBUSSTOP_NAME() {
        return BUSSTOP_NAME;
    }

    public void setBUSSTOP_NAME(String BUSSTOP_NAME) {
        this.BUSSTOP_NAME = BUSSTOP_NAME;
    }

    public String getARS_ID() {
        return ARS_ID;
    }

    public void setARS_ID(String ARS_ID) {
        this.ARS_ID = ARS_ID;
    }
}
