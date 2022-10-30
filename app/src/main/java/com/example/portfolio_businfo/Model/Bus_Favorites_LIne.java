package com.example.portfolio_businfo.Model;

public class Bus_Favorites_LIne {
    Integer POSITION;
    Boolean CHECK;

    public Bus_Favorites_LIne(Integer POSITION, Boolean CHECK) {
        this.POSITION = POSITION;
        this.CHECK = CHECK;
    }

    public Integer getPOSITION() {
        return POSITION;
    }

    public Boolean getCHECK() {
        return CHECK;
    }
}
