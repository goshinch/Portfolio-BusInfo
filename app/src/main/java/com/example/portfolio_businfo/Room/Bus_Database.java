package com.example.portfolio_businfo.Room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.portfolio_businfo.Model.Bus_Line;
import com.example.portfolio_businfo.Model.Bus_Line_Station;
import com.example.portfolio_businfo.Model.Bus_Line_checkbox;
import com.example.portfolio_businfo.Model.Bus_Station;
import com.example.portfolio_businfo.Model.Bus_Station_checkbox;

@Database(entities = {Bus_Line.class, Bus_Station.class, Bus_Line_checkbox.class, Bus_Station_checkbox.class, Bus_Line_Station.class}, version = 1)
public abstract class Bus_Database extends RoomDatabase {
    public abstract Bus_Dao dao();
}
