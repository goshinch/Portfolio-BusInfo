package com.example.portfolio_businfo.ViewModel.Fragment;


import static com.example.portfolio_businfo.Recyclerview.CaseType.SearchCase.BUS_STATION;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.portfolio_businfo.Model.Bus_Line_checkbox;
import com.example.portfolio_businfo.Model.Bus_Station;
import com.example.portfolio_businfo.Model.Bus_Station_checkbox;
import com.example.portfolio_businfo.Recyclerview.ClickItem.LineItemClick;
import com.example.portfolio_businfo.Recyclerview.OnCheckListener;
import com.example.portfolio_businfo.Recyclerview.OnClickListener;
import com.example.portfolio_businfo.Recyclerview.OnMapClickListener;
import com.example.portfolio_businfo.Recyclerview.RecyclerViewAdapter;
import com.example.portfolio_businfo.Room.Bus_Database;
import com.example.portfolio_businfo.View.Activity.Arrive_Info;
import com.example.portfolio_businfo.View.Activity.MainActivity;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class Fragment_Station_ViewModel extends ViewModel {
    private Context context;
    private LinearLayoutManager mLinearLayoutManager;
    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter mRecyclerViewAdapter;
    private ArrayList<Bus_Station> SearchList;
    private ArrayList<Bus_Station> tempList;
    private ArrayList<Bus_Station_checkbox> bus_station_checkbox;
    private Bus_Database db;
    private SharedPreferences saveStopId;
    private SharedPreferences.Editor editor;
    private MutableLiveData<List<Bus_Station>> arrayListdata;
    private MainActivity mMainActivity;

    public LiveData<List<Bus_Station>> setArrayListdata() {
        return arrayListdata;
    }

    public void init(Context context, RecyclerView mRecyclerView, Bus_Database db, MainActivity mMainActivity) {
        this.context = context;
        this.mRecyclerView = mRecyclerView;
        this.db = db;
        this.mMainActivity = mMainActivity;
        if (arrayListdata == null) arrayListdata = new MutableLiveData<>();
        if (tempList == null) tempList = new ArrayList<>();
        if (SearchList == null) SearchList = new ArrayList<>();
        if (bus_station_checkbox == null) bus_station_checkbox = new ArrayList<>();
    }

    private void setArrayList(ArrayList<Bus_Station> arrayList) {
        arrayListdata.setValue(arrayList);
    }

    public RecyclerViewAdapter getmRecyclerViewAdapter() {
        return mRecyclerViewAdapter;
    }

    public void RecyclerSet() {
        db.dao().getStationAll().observe((LifecycleOwner) context, data -> {
            tempList.clear();
            for (Bus_Station i : data) {
                tempList.add(new Bus_Station(i.getBUSSTOP_ID(), i.getBUSSTOP_NAME(), i.getNAME_E(), i.getLONGITUDE(), i.getLATITUDE(), i.getARS_ID(), i.getNEXT_BUSSTOP() + " 방면", i.isCHECK()));
            }
        });
    }

    public void RecyclerInit() {
        mLinearLayoutManager = new LinearLayoutManager(context);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerViewAdapter = new RecyclerViewAdapter(BUS_STATION);
        mRecyclerViewAdapter.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v, int po) {
                ArriveInfoClassIntent(
                        new LineItemClick(BUS_STATION).StationName(po, mRecyclerViewAdapter.ArrayListDataGet_Station()),
                        new LineItemClick(BUS_STATION).StationNext(po, mRecyclerViewAdapter.ArrayListDataGet_Station()),
                        new LineItemClick(BUS_STATION).StationArs(po, mRecyclerViewAdapter.ArrayListDataGet_Station()),
                        new LineItemClick(BUS_STATION).StationId(po, mRecyclerViewAdapter.ArrayListDataGet_Station())
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
        mRecyclerView.setAdapter(mRecyclerViewAdapter);

        mRecyclerViewAdapter.setOnMapClickListener(new OnMapClickListener() {
            @Override
            public void MapClick(String id) {
                Log.d("MapClick: ", id);Log.d("MapClick: ", String.valueOf(id.length()));
                MapClickItem(id);
            }
        });
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

    public void SearchStationData(String s) {
        for (Bus_Station i : tempList) {
            if (i.getBUSSTOP_NAME().contains(s)) {
                SearchList.add(new Bus_Station(i.getBUSSTOP_ID(), i.getBUSSTOP_NAME(), i.getNAME_E(), i.getLONGITUDE(), i.getLATITUDE(), i.getARS_ID(), i.getNEXT_BUSSTOP(), i.isCHECK()));
            }
        }
        setArrayList(SearchList);
        SearchList.clear();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }

}
