package com.example.portfolio_businfo.Model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "stationcheckbox")
public class Bus_Station_checkbox {
    @PrimaryKey
    @ColumnInfo(name = "busstop_id")
    @NonNull
    String BUSSTOP_ID;
    @ColumnInfo(name = "position")
    private Integer POSITION;
    @ColumnInfo(name = "checked")
    private boolean CHECK;

    public Bus_Station_checkbox(@NonNull String BUSSTOP_ID, Integer POSITION, boolean CHECK) {
        this.BUSSTOP_ID = BUSSTOP_ID;
        this.POSITION = POSITION;
        this.CHECK = CHECK;
    }

    @NonNull
    public String getBUSSTOP_ID() {
        return BUSSTOP_ID;
    }

    public Integer getPOSITION() {
        return POSITION;
    }

    public boolean isCHECK() {
        return CHECK;
    }
}
