package com.example.portfolio_businfo.Model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "linecheckbox")
public class Bus_Line_checkbox {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "line_id")
    private String LINE_ID;
    @ColumnInfo(name = "position")
    private Integer POSITION;
    @ColumnInfo(name = "checked")
    private boolean CHECK;

    public Bus_Line_checkbox(Integer POSITION,String LINE_ID, boolean CHECK) {
        this.LINE_ID = LINE_ID;
        this.POSITION = POSITION;
        this.CHECK = CHECK;
    }

    public String getLINE_ID() {
        return LINE_ID;
    }

    public Integer getPOSITION() {
        return POSITION;
    }

    public boolean isCHECK() {
        return CHECK;
    }

    public void setCHECK(boolean CHECK) {
        this.CHECK = CHECK;
    }
}
