package com.example.portfolio_businfo.ViewModel.Activity;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.portfolio_businfo.Model.Bus_Line_Station;
import com.example.portfolio_businfo.Model.Bus_Station_Nextstop;
import com.example.portfolio_businfo.R;
import com.example.portfolio_businfo.Recyclerview.CaseType.SearchCase;
import com.example.portfolio_businfo.Recyclerview.ClickItem.LineItemClick;
import com.example.portfolio_businfo.Recyclerview.OnClickListener;
import com.example.portfolio_businfo.Recyclerview.RecyclerViewAdapter;
import com.example.portfolio_businfo.Room.Bus_Database;
import com.example.portfolio_businfo.View.Activity.Arrive_Info;
import com.example.portfolio_businfo.View.Activity.Line_station_info;
import com.example.portfolio_businfo.XmlParse.Bus_SplashData_api;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.DoubleBounce;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class Line_station_info_ViewModel extends ViewModel {
    private Context context;
    private Bus_SplashData_api insert_api;
    private Bus_Database db;
    private LineItemClick mLineItemClick;
    private Line_station_info mLine_station_info;
    private ArrayList<Bus_Line_Station> tmpListdata;
    private MutableLiveData<List<Bus_Line_Station>> arrayListdata;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private RecyclerViewAdapter mRecyclerViewAdapter;
    private Disposable backgroundtask, backgroundtask_set;
    private Sprite doubleBounce;
    private String Line_Id;
    private LinearLayout buffering;
    private ProgressBar bufferBar;

    public RecyclerViewAdapter getmRecyclerViewAdapter() {
        return mRecyclerViewAdapter;
    }

    public void setRecyclerView(RecyclerView mRecyclerView) {
        this.mRecyclerView = mRecyclerView;
    }

    public LiveData<List<Bus_Line_Station>> setArrayListdata() {
        return arrayListdata;
    }

    private void setArrayList(ArrayList<Bus_Line_Station> arrayList) {
        arrayListdata.setValue(arrayList);
    }

    public void init(Context context, Bus_Database db, String Line_Id, LinearLayout buffering, ProgressBar bufferBar) {
        this.context = context;
        this.db = db;
        this.Line_Id = Line_Id;
        this.buffering = buffering;
        this.bufferBar = bufferBar;
        if (insert_api == null) insert_api = new Bus_SplashData_api();
        if (mLine_station_info == null) mLine_station_info = (Line_station_info) context;
        if (mLineItemClick == null)
            mLineItemClick = new LineItemClick(SearchCase.BUS_LINE_STATION_INFORMATION);
        if (arrayListdata == null) arrayListdata = new MutableLiveData<>();
        if (tmpListdata == null) tmpListdata = new ArrayList<>();
        if (doubleBounce == null) doubleBounce = new DoubleBounce();
    }

    public void getLineStationData_RoomSet(String line_id) {
        backgroundtask = Flowable.fromCallable(() -> {
            for (Bus_Line_Station i : insert_api.lineStations(line_id))
                db.dao().Insert_Line_Station(new Bus_Line_Station(i.getLINE_ID(), i.getBUSSTOP_ID(), i.getBUSSTOP_NAME(), i.getARS_ID(), i.getRETURN_FLAG(), i.isExist()));
            return null;
        })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(data -> {}, Throwable::getMessage);
    }

    // 내장 데이터에 값이 없다면 에러가 뜬다 fromIterable 함수를 더 공부해 봐야겠다.
//    public void getLineStationData_RoomSet(String line_id) {
//        backgroundtask = Flowable.fromIterable(insert_api.lineStations(line_id))
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(Schedulers.io())
//                .subscribe(i -> {
//                    try {
//                        Log.d("getLineStationData_RoomSet: ", i.getBUSSTOP_ID() + "//" + i.getRETURN_FLAG() + "//" + i.getBUSSTOP_NAME() + "//" + i.getARS_ID() + "//" + i.getLINE_ID() + "//" + i.isExist());
//                        db.dao().Insert_Line_Station(new Bus_Line_Station(i.getLINE_ID(), i.getBUSSTOP_ID(), i.getBUSSTOP_NAME(), i.getARS_ID(), i.getRETURN_FLAG(), i.isExist()));
//
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }, Throwable::getMessage);
//    }

    public void checkLSDataisExist(String Line_Id) {
        db.dao().getLSHolder(Line_Id).observe((LifecycleOwner) context, new Observer<List<Bus_Line_Station>>() {
            @Override
            public void onChanged(List<Bus_Line_Station> bus_line_stations) {
                setArrayList((ArrayList<Bus_Line_Station>) bus_line_stations);
            }
        });
    }

    public void RealTimeBusPosition(String line_id) {
        buffering.setVisibility(View.VISIBLE);
        doubleBounce.setColor(R.color.one);

        backgroundtask_set = Flowable.fromCallable(() -> {
            bufferBar.setIndeterminateDrawable(doubleBounce);
            return setInsert_api(line_id);
        })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(data -> {
                    Log.d("RealTimeBusPosition: ",data.toString());
                    setRealTimeBus(data);
                }, Throwable::getMessage);
    }

    private ArrayList<String> setInsert_api(String line_id) {
        return insert_api.select_Arrive_Position(context, getmRecyclerViewAdapter().ArrayListDataGet_LS(), line_id);
    }

    private void setRealTimeBus(ArrayList<String> setInsert_data) {
        try {
            mRecyclerViewAdapter.notifyItemRangeChanged(0, mRecyclerViewAdapter.getItemCount(), setInsert_data);
        }catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            buffering.setVisibility(View.GONE);
        }
    }

    public void getStationSelecting(int po) {
        try {
            db.dao().getStationNextstop(mLineItemClick.LineStationARS_ID(po, mRecyclerViewAdapter.ArrayListDataGet_LS()))
                    .observe((LifecycleOwner) context, new Observer<Bus_Station_Nextstop>() {
                        @Override
                        public void onChanged(Bus_Station_Nextstop bus_station_nextstop) {
                            ArriveInformationClassIntent(
                                    mLineItemClick.LineStationBusstop_Name(po, mRecyclerViewAdapter.ArrayListDataGet_LS()),
                                    mLineItemClick.LineStationBusstop_ID(po, mRecyclerViewAdapter.ArrayListDataGet_LS()),
                                    mLineItemClick.LineStationARS_ID(po, mRecyclerViewAdapter.ArrayListDataGet_LS()),
                                    bus_station_nextstop.getNEXT_BUSSTOP()
                            );
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void RecyclerInit() {
        mLinearLayoutManager = new LinearLayoutManager(context);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerViewAdapter = new RecyclerViewAdapter(SearchCase.BUS_LINE_STATION_INFORMATION);
        mRecyclerViewAdapter.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v, int po) {
                getStationSelecting(po);
            }
        });
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
    }

    public void ArriveInformationClassIntent(String putName, String putId, String putArs, String putNext) {
        try {
            Intent arrive = new Intent((Line_station_info) context, Arrive_Info.class);
            arrive.putExtra("putname", putName);
            arrive.putExtra("putid", putId);
            arrive.putExtra("putars", putArs);
            arrive.putExtra("putnext", putNext);
            mLine_station_info.startActivity(arrive);
            mLine_station_info.finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            RealTimeBusPosition(Line_Id);
        }
    };

    public void disposible() {
        if (backgroundtask != null) backgroundtask.dispose();
        if (backgroundtask_set != null) backgroundtask_set.dispose();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        try {
            backgroundtask.dispose();
            backgroundtask_set.dispose();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
