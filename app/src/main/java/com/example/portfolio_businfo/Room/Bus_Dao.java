package com.example.portfolio_businfo.Room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.portfolio_businfo.Model.Bus_Line;
import com.example.portfolio_businfo.Model.Bus_Line_Indiv;
import com.example.portfolio_businfo.Model.Bus_Line_Line_Kind;
import com.example.portfolio_businfo.Model.Bus_Line_Station;
import com.example.portfolio_businfo.Model.Bus_Line_Station_HolderData;
import com.example.portfolio_businfo.Model.Bus_Line_checkbox;
import com.example.portfolio_businfo.Model.Bus_Station;
import com.example.portfolio_businfo.Model.Bus_Station_Nextstop;
import com.example.portfolio_businfo.Model.Bus_Station_checkbox;

import java.util.List;

@Dao
public interface Bus_Dao {

    @Query("SELECT EXISTS (SELECT * FROM linestation WHERE lineid =:lineId LIMIT 1) AS SUCCESS")
    LiveData<String> getLSExist(String lineId);

    @Query("SELECT * FROM linestation WHERE lineid =:lineid")
    LiveData<List<Bus_Line_Station>> getLSHolder(String lineid);

    @Query("SELECT DISTINCT * FROM line ORDER BY like_kind")
    LiveData<List<Bus_Line>> getLineAll();

    @Query("SELECT DISTINCT * FROM line WHERE checked = :checked")
    LiveData<List<Bus_Line>> getLineCheckedAll(boolean checked);

    @Query("SELECT DISTINCT first_run, last_run, run_interval,like_kind FROM line AS l WHERE l.line_id = :line_id")
    LiveData<List<Bus_Line_Indiv>> getLineIndiv(String line_id);

    @Query("SELECT DISTINCT like_kind FROM line WHERE line_id = :line_id")
    Bus_Line_Line_Kind getLine_Kind(String line_id);



    @Query("SELECT DISTINCT next_busstop FROM station AS s WHERE ars_id = :ars_id")
    LiveData<Bus_Station_Nextstop> getStationNextstop(String ars_id);

    @Query("SELECT DISTINCT * FROM station")
    LiveData<List<Bus_Station>> getStationAll();

    @Query("SELECT DISTINCT * FROM station WHERE checked = :checked")
    LiveData<List<Bus_Station>> getStationCheckedAll(boolean checked);


    @Insert
    void Insert_Line(Bus_Line bus_line);

    @Insert
    void Insert_Station(Bus_Station bus_station);

    @Insert
    void Insert_Line_Station(Bus_Line_Station bus_line_station);



    @Query("UPDATE line SET checked = :check_state WHERE line_id = :line_id")
    void Updata_Line_checkbox(String line_id, boolean check_state);

    @Query("UPDATE station SET checked = :check_state WHERE busstop_id =:busstop_id")
    void Updata_Station_checkbox(String busstop_id, boolean check_state);
}
