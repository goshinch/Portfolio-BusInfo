package com.example.portfolio_businfo.ViewModel.Fragment;

import static com.example.portfolio_businfo.Recyclerview.CaseType.SearchCase.BUS_FAVORITES_LINE;
import static com.example.portfolio_businfo.Recyclerview.CaseType.SearchCase.BUS_FAVORITES_STATION;
import static com.example.portfolio_businfo.Recyclerview.CaseType.SearchCase.BUS_LINE;
import static com.example.portfolio_businfo.Recyclerview.CaseType.SearchCase.BUS_STATION;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.portfolio_businfo.Model.Bus_Line;
import com.example.portfolio_businfo.Model.Bus_Line_checkbox;
import com.example.portfolio_businfo.Model.Bus_Station;
import com.example.portfolio_businfo.Model.Bus_Station_checkbox;
import com.example.portfolio_businfo.R;
import com.example.portfolio_businfo.Recyclerview.ClickItem.LineItemClick;
import com.example.portfolio_businfo.Recyclerview.OnCheckListener;
import com.example.portfolio_businfo.Recyclerview.OnClickListener;
import com.example.portfolio_businfo.Recyclerview.OnMapClickListener;
import com.example.portfolio_businfo.Recyclerview.RecyclerViewAdapter;
import com.example.portfolio_businfo.Room.Bus_Database;
import com.example.portfolio_businfo.View.Activity.Arrive_Info;
import com.example.portfolio_businfo.View.Activity.Line_station_info;
import com.example.portfolio_businfo.View.Activity.MainActivity;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.DoubleBounce;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class Fragment_Favorites_ViewModel extends ViewModel {
    private Context context;
    private RecyclerView mRecyclerView, mRecyclerView2;
    private LinearLayoutManager mLinearLayoutManager, mLinearLayoutManager2;
    private RecyclerViewAdapter mRecyclerViewAdapter, mRecyclerViewAdapter2;
    private MutableLiveData<List<Bus_Line>> lineLists;
    private MutableLiveData<List<Bus_Station>> stationLists;
    private ArrayList<Bus_Line> lines;
    private ArrayList<Bus_Station> stations;
    private ArrayList<Bus_Station_checkbox> bus_station_checkbox;
    private ArrayList<Bus_Line_checkbox> bus_line_checkbox;
    private Bus_Database db;
    private SharedPreferences saveStopId;
    private SharedPreferences.Editor editor;
    private MainActivity mMainActivity;

    public void init(Context context, Bus_Database db, MainActivity mMainActivity) {
        this.context = context;
        this.db = db;
        this.mMainActivity = mMainActivity;
        if (lineLists == null) lineLists = new MutableLiveData<>();
        if (stationLists == null) stationLists = new MutableLiveData<>();
        if (lines == null) lines = new ArrayList<>();
        if (stations == null) stations = new ArrayList<>();
        if (bus_station_checkbox == null) bus_station_checkbox = new ArrayList<>();
        if (bus_line_checkbox == null) bus_line_checkbox = new ArrayList<>();
    }

    public LiveData<List<Bus_Line>> setLineListsData() {
        return lineLists;
    }

    private void setLineLists(ArrayList<Bus_Line> busLines) {
        lineLists.setValue(busLines);
    }


    public LiveData<List<Bus_Station>> setStationListsData() {
        return stationLists;
    }

    private void setStationLists(ArrayList<Bus_Station> bus_stations) {
        stationLists.setValue(bus_stations);
    }

    public void setFavoritLists() {
        try {
            RecyclerSet_Line();
            RecyclerSet_Station();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void RecyclerSet_Line() {
        db.dao().getLineCheckedAll(true).observe((LifecycleOwner) context, data -> {
            lines.clear();
            for (Bus_Line i : data) {
                lines.add(new Bus_Line(i.getLINE_ID(), i.getLINE_NAME(), i.getDIR_UP_NAME(), i.getDIR_DOWN_NAME(), i.getFIRST_RUN_TIME(), i.getLAST_RUN_TIME(), i.getRUN_INTERVAL(), i.getLIKE_KIND(), i.isCHECK()));
            }
            setLineLists(lines);
        });
    }

    private void RecyclerSet_Station() {
        db.dao().getStationCheckedAll(true).observe((LifecycleOwner) context, data -> {
            stations.clear();
            for (Bus_Station i : data) {
                stations.add(new Bus_Station(i.getBUSSTOP_ID(), i.getBUSSTOP_NAME(), i.getNAME_E(), i.getLONGITUDE(), i.getLATITUDE(), i.getBUSSTOP_ID(), i.getNEXT_BUSSTOP(), i.isCHECK()));
            }
            setStationLists(stations);
        });
    }

    public void getRecyclerView(RecyclerView mRecyclerView) {
        this.mRecyclerView = mRecyclerView;
    }

    public void getRecyclerView2(RecyclerView mRecyclerView2) {
        this.mRecyclerView2 = mRecyclerView2;
    }

    public RecyclerViewAdapter getRecyclerViewAdapter() {
        return mRecyclerViewAdapter;
    }

    public RecyclerViewAdapter getRecyclerViewAdapter2() {
        return mRecyclerViewAdapter2;
    }

    public void OpenANDClose(RecyclerView station, RecyclerView line, Button openandclose) {
        if (station.getVisibility() == View.VISIBLE) {
            station.setVisibility(View.GONE);
            line.setVisibility(View.VISIBLE);
            openandclose.setText("버스");
        } else {
            station.setVisibility(View.VISIBLE);
            line.setVisibility(View.GONE);
            openandclose.setText("정류장");
        }
    }

    public void RecyclerInitStation() {
        mLinearLayoutManager = new LinearLayoutManager(context);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerViewAdapter = new RecyclerViewAdapter(BUS_FAVORITES_STATION);
        mRecyclerViewAdapter.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v, int po) {
                ArriveInfoClassIntent(
                        new LineItemClick(BUS_FAVORITES_STATION).StationName(po, mRecyclerViewAdapter.ArrayListDataGet_Favorit_S()),
                        new LineItemClick(BUS_FAVORITES_STATION).StationNext(po, mRecyclerViewAdapter.ArrayListDataGet_Favorit_S()),
                        new LineItemClick(BUS_FAVORITES_STATION).StationArs(po, mRecyclerViewAdapter.ArrayListDataGet_Favorit_S()),
                        new LineItemClick(BUS_FAVORITES_STATION).StationId(po, mRecyclerViewAdapter.ArrayListDataGet_Favorit_S())
                );
            }
        });
        mRecyclerViewAdapter.setOnCheckListener(new OnCheckListener() {
            @Override
            public void onCheck(int po, String busstopId, boolean check) {
                bus_station_checkbox.clear();
                bus_station_checkbox.add(new Bus_Station_checkbox(busstopId, po, check));
                mRecyclerViewAdapter.notifyItemRangeChanged(0, mRecyclerViewAdapter.getItemCount(), bus_station_checkbox);
                Observable.fromCallable(() -> {
                    db.dao().Updata_Station_checkbox(busstopId, check);
                    return null;
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(data -> {
                }, Throwable::getMessage);
            }
        });
        mRecyclerViewAdapter.setOnMapClickListener(new OnMapClickListener() {
            @Override
            public void MapClick(String id) {
                MapClickItem(id);
            }
        });
        Log.d("RecyclerInit: ", "what");
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
    }

    private void MapClickItem(String stopId) {
        Log.d("TAG", "MapClickItem: ");
        saveStopId = context.getSharedPreferences("dataId", 0);
        editor = saveStopId.edit();
        editor.putString("id", stopId);
        editor.commit();
        mMainActivity.checkMapOpened();
    }

    private void ArriveInfoClassIntent(String putName, String putNext, String putArs, String putId) {
        Intent intent = new Intent(context, Arrive_Info.class);
        intent.putExtra("putname", putName);
        intent.putExtra("putars", putArs);
        intent.putExtra("putnext", putNext);
        intent.putExtra("putid", putId);
        context.startActivity(intent);
    }

    public void RecyclerInitLine() {
        mLinearLayoutManager2 = new LinearLayoutManager(context);
        mRecyclerView2.setLayoutManager(mLinearLayoutManager2);
        mRecyclerViewAdapter2 = new RecyclerViewAdapter(BUS_FAVORITES_LINE);
        mRecyclerViewAdapter2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v, int po) {
                LineStationClassIntent(
                        new LineItemClick(BUS_FAVORITES_LINE).LineName(po, mRecyclerViewAdapter2.ArrayListDataGet_Favorit_L()),
                        new LineItemClick(BUS_FAVORITES_LINE).LineId(po, mRecyclerViewAdapter2.ArrayListDataGet_Favorit_L()),
                        new LineItemClick(BUS_FAVORITES_LINE).LikeKind(po, mRecyclerViewAdapter2.ArrayListDataGet_Favorit_L()),
                        new LineItemClick(BUS_FAVORITES_LINE).First_Run_time(po, mRecyclerViewAdapter2.ArrayListDataGet_Favorit_L()),
                        new LineItemClick(BUS_FAVORITES_LINE).LastRunTime(po, mRecyclerViewAdapter2.ArrayListDataGet_Favorit_L()),
                        new LineItemClick(BUS_FAVORITES_LINE).RunInterval(po, mRecyclerViewAdapter2.ArrayListDataGet_Favorit_L()),
                        new LineItemClick(BUS_FAVORITES_LINE).DirUp(po, mRecyclerViewAdapter2.ArrayListDataGet_Favorit_L()),
                        new LineItemClick(BUS_FAVORITES_LINE).DirDown(po, mRecyclerViewAdapter2.ArrayListDataGet_Favorit_L())
                );
            }

        });
        mRecyclerViewAdapter2.setOnCheckListener(new OnCheckListener() {
            @Override
            public void onCheck(int po, String lineId, boolean check) {
                bus_line_checkbox.clear();
                bus_line_checkbox.add(new Bus_Line_checkbox(po, lineId, check));
                Log.d("onCheck: ", mRecyclerViewAdapter2.getItemCount() + "  " + bus_line_checkbox.size());
                if (bus_line_checkbox != null)
                    mRecyclerViewAdapter2.notifyItemRangeChanged(0, mRecyclerViewAdapter2.getItemCount(), bus_line_checkbox);
                Observable.fromCallable(() -> {
                    if (check) {
                        db.dao().Updata_Line_checkbox(lineId, check);
                    } else {
                        db.dao().Updata_Line_checkbox(lineId, check);
                    }
                    return null;
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(data -> {
                }, Throwable::getMessage);
            }
        });
        mRecyclerView2.setAdapter(mRecyclerViewAdapter2);
    }

    private void LineStationClassIntent(String putDataName, String putDataId, String putDataKind, String putF_Time,
                                        String putL_Time, String putRunInterval, String putDirDown, String putDirUp) {
        Intent intent = new Intent(context, Line_station_info.class);
        intent.putExtra("line_name", putDataName);
        intent.putExtra("line_id", putDataId);
        intent.putExtra("like_kind", putDataKind);
        intent.putExtra("f_time", putF_Time);
        intent.putExtra("l_time", putL_Time);
        intent.putExtra("run_interval", putRunInterval);
        intent.putExtra("dir_down", putDirDown);
        intent.putExtra("dir_up", putDirUp);
        context.startActivity(intent);
    }


}
