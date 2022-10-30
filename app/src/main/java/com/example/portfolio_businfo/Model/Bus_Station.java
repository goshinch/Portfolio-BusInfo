package com.example.portfolio_businfo.Model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "station")
public class Bus_Station {
    @PrimaryKey
    @ColumnInfo(name = "busstop_id")
    @NonNull
    String BUSSTOP_ID;
    @ColumnInfo(name = "busstop_name")
    String BUSSTOP_NAME;
    @ColumnInfo(name = "name_e")
    String NAME_E;
    @ColumnInfo(name = "longitude")
    String LONGITUDE;
    @ColumnInfo(name = "latitude")
    String LATITUDE;
    @ColumnInfo(name = "ars_id")
    String ARS_ID;
    @ColumnInfo(name = "next_busstop")
    String NEXT_BUSSTOP;
    @ColumnInfo(name = "checked")
    boolean CHECK;

    public Bus_Station(@NonNull String BUSSTOP_ID, String BUSSTOP_NAME, String NAME_E, String LONGITUDE, String LATITUDE, String ARS_ID, String NEXT_BUSSTOP, boolean CHECK) {
        this.BUSSTOP_ID = BUSSTOP_ID;
        this.BUSSTOP_NAME = BUSSTOP_NAME;
        this.NAME_E = NAME_E;
        this.LONGITUDE = LONGITUDE;
        this.LATITUDE = LATITUDE;
        this.ARS_ID = ARS_ID;
        this.NEXT_BUSSTOP = NEXT_BUSSTOP;
        this.CHECK = CHECK;
    }

    @NonNull
    public String getBUSSTOP_ID() {
        return BUSSTOP_ID;
    }

    public void setBUSSTOP_ID(@NonNull String BUSSTOP_ID) {
        this.BUSSTOP_ID = BUSSTOP_ID;
    }

    public String getBUSSTOP_NAME() {
        return BUSSTOP_NAME;
    }

    public void setBUSSTOP_NAME(String BUSSTOP_NAME) {
        this.BUSSTOP_NAME = BUSSTOP_NAME;
    }

    public String getNAME_E() {
        return NAME_E;
    }

    public void setNAME_E(String NAME_E) {
        this.NAME_E = NAME_E;
    }

    public String getLONGITUDE() {
        return LONGITUDE;
    }

    public void setLONGITUDE(String LONGITUDE) {
        this.LONGITUDE = LONGITUDE;
    }

    public String getLATITUDE() {
        return LATITUDE;
    }

    public void setLATITUDE(String LATITUDE) {
        this.LATITUDE = LATITUDE;
    }

    public String getARS_ID() {
        return ARS_ID;
    }

    public void setARS_ID(String ARS_ID) {
        this.ARS_ID = ARS_ID;
    }

    public String getNEXT_BUSSTOP() {
        return NEXT_BUSSTOP;
    }

    public void setNEXT_BUSSTOP(String NEXT_BUSSTOP) {
        this.NEXT_BUSSTOP = NEXT_BUSSTOP;
    }

    public boolean isCHECK() {
        return CHECK;
    }

    public void setCHECK(boolean CHECK) {
        this.CHECK = CHECK;
    }
}