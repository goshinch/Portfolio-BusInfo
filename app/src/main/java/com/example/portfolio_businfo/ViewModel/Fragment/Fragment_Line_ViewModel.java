package com.example.portfolio_businfo.ViewModel.Fragment;


import static com.example.portfolio_businfo.Recyclerview.CaseType.SearchCase.BUS_LINE;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.portfolio_businfo.Model.Bus_Line;
import com.example.portfolio_businfo.Model.Bus_Line_checkbox;
import com.example.portfolio_businfo.Recyclerview.ClickItem.LineItemClick;
import com.example.portfolio_businfo.Recyclerview.OnCheckListener;
import com.example.portfolio_businfo.Recyclerview.OnClickListener;
import com.example.portfolio_businfo.Recyclerview.RecyclerViewAdapter;
import com.example.portfolio_businfo.Room.Bus_Database;
import com.example.portfolio_businfo.View.Activity.Line_station_info;
import com.example.portfolio_businfo.View.Fragment.Line;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class Fragment_Line_ViewModel extends ViewModel {
    Context context;
    ArrayList<Bus_Line> SearchList;
    ArrayList<Bus_Line> tempList;
    MutableLiveData<List<Bus_Line>> arrayListdata;
    ArrayList<Bus_Line_checkbox> bus_line_checkbox;
    RecyclerView mRecyclerView;
    LinearLayoutManager mLinearLayoutManager;
    RecyclerViewAdapter mRecyclerViewAdapter;
    Bus_Database db;

    public void getRecyclerView(RecyclerView mRecyclerView) {
        this.mRecyclerView = mRecyclerView;
    }

    public void init(Context context, Bus_Database db) {
        this.context = context;
        if (SearchList == null) SearchList = new ArrayList<>();
        if (arrayListdata == null) arrayListdata = new MutableLiveData<>();
        if (tempList == null) tempList = new ArrayList<>();
        if (bus_line_checkbox == null) bus_line_checkbox = new ArrayList<>();
        this.db = db;
    }

    public RecyclerViewAdapter getmRecyclerViewAdapter() {
        return mRecyclerViewAdapter;
    }

    public LiveData<List<Bus_Line>> setArrayListdata() {
        return arrayListdata;
    }

    private void setArrayList(ArrayList<Bus_Line> arrayList) {
        arrayListdata.setValue(arrayList);
    }

    public void RecyclerSet() {
        db.dao().getLineAll().observe((LifecycleOwner) context, data -> {
            tempList.clear();
            for (Bus_Line i : data) {
                tempList.add(new Bus_Line(i.getLINE_ID(), i.getLINE_NAME(), i.getDIR_UP_NAME(), i.getDIR_DOWN_NAME(), i.getFIRST_RUN_TIME(), i.getLAST_RUN_TIME(), i.getRUN_INTERVAL(), i.getLIKE_KIND(), i.isCHECK()));
            }
        });
    }

    public void RecyclerInit() {
        mLinearLayoutManager = new LinearLayoutManager(context);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerViewAdapter = new RecyclerViewAdapter(BUS_LINE);
        mRecyclerViewAdapter.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v, int po) {
                LineStationClassIntent(
                        new LineItemClick(BUS_LINE).LineName(po, mRecyclerViewAdapter.ArrayListDataGet_Line()),
                        new LineItemClick(BUS_LINE).LineId(po, mRecyclerViewAdapter.ArrayListDataGet_Line()),
                        new LineItemClick(BUS_LINE).LikeKind(po, mRecyclerViewAdapter.ArrayListDataGet_Line()),
                        new LineItemClick(BUS_LINE).First_Run_time(po, mRecyclerViewAdapter.ArrayListDataGet_Line()),
                        new LineItemClick(BUS_LINE).LastRunTime(po, mRecyclerViewAdapter.ArrayListDataGet_Line()),
                        new LineItemClick(BUS_LINE).RunInterval(po, mRecyclerViewAdapter.ArrayListDataGet_Line()),
                        new LineItemClick(BUS_LINE).DirUp(po, mRecyclerViewAdapter.ArrayListDataGet_Line()),
                        new LineItemClick(BUS_LINE).DirDown(po, mRecyclerViewAdapter.ArrayListDataGet_Line())
                );
            }
        });
        mRecyclerViewAdapter.setOnCheckListener(new OnCheckListener() {
            @Override
            public void onCheck(int po, String lineId, boolean check) {
                Log.d("mRecyclerViewAdapter onCheck: ", String.valueOf(check));
                bus_line_checkbox.clear();
                bus_line_checkbox.add(new Bus_Line_checkbox(po, lineId, check));
                mRecyclerViewAdapter.notifyItemRangeChanged(0, mRecyclerViewAdapter.getItemCount(), bus_line_checkbox);
                Observable.fromCallable(() -> {
                    db.dao().Updata_Line_checkbox(lineId, check);
                    return null;
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(data -> {
                }, Throwable::getMessage);
            }
        });
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
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

    public void SearchLineData(String s) {
        for (Bus_Line i : tempList) {
            if (i.getLINE_NAME().contains(s)) {
                SearchList.add(new Bus_Line(i.getLINE_ID(), i.getLINE_NAME(), i.getDIR_UP_NAME(), i.getDIR_DOWN_NAME(), i.getFIRST_RUN_TIME(), i.getLAST_RUN_TIME(), i.getRUN_INTERVAL(), i.getLIKE_KIND(), i.isCHECK()));
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
