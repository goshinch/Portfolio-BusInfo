package com.example.portfolio_businfo.Model;

import androidx.room.ColumnInfo;

public class Bus_Line_Line_Kind {
    @ColumnInfo(name = "like_kind")
    private String LIKE_KIND;

    public Bus_Line_Line_Kind(String LIKE_KIND) {
        this.LIKE_KIND = LIKE_KIND;
    }

    public String getLIKE_KIND() {
        return LIKE_KIND;
    }
}
