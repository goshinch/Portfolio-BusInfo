package com.example.portfolio_businfo.ViewModel.Activity;

import static com.example.portfolio_businfo.Recyclerview.CaseType.SearchCase.BUS_ARRIVE_INFORMATION;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.room.Room;

import com.example.portfolio_businfo.Model.Bus_Arrive_Info;
import com.example.portfolio_businfo.Model.Bus_Line_Indiv;
import com.example.portfolio_businfo.Recyclerview.ClickItem.LineItemClick;
import com.example.portfolio_businfo.Recyclerview.RecyclerViewAdapter;
import com.example.portfolio_businfo.Room.Bus_Database;
import com.example.portfolio_businfo.View.Activity.Arrive_Info;
import com.example.portfolio_businfo.View.Activity.Line_station_info;
import com.example.portfolio_businfo.XmlParse.Bus_SplashData_api;
import com.example.portfolio_businfo.XmlParse.Insert_Api;
import com.example.portfolio_businfo.XmlParse.RealTime_Arrive_Info;

import org.w3c.dom.NodeList;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class Arrive_Info_ViewModel extends ViewModel {
    private Context context;
    private Disposable mArrive_Data;
    private Bus_SplashData_api insert_api;
    private RecyclerViewAdapter mRecyclerViewAdapter;
    private Arrive_Info mArrive_Info;
    private LineItemClick mLineItemClick;
    private MutableLiveData<List<Bus_Arrive_Info>> arrayListdata;
    private SharedPreferences saveStopId;
    private SharedPreferences.Editor editor;
    private boolean a = false;
    private String stopId;

    public void init(Context context, RecyclerViewAdapter mRecyclerViewAdapter, String stopId) {
        this.context = context;
        this.mRecyclerViewAdapter = mRecyclerViewAdapter;
        if (insert_api == null) insert_api = new Bus_SplashData_api();
        this.stopId = stopId;
        if (mLineItemClick == null) mLineItemClick = new LineItemClick(BUS_ARRIVE_INFORMATION);
        if (arrayListdata == null) arrayListdata = new MutableLiveData<>();
        if (mArrive_Info == null) mArrive_Info = (Arrive_Info) context;
    }

    public void check(boolean a) {
        this.a = a;
    }

    public LiveData<List<Bus_Arrive_Info>> setArrayListdata() {
        return arrayListdata;
    }

    private void setArrayList(ArrayList<Bus_Arrive_Info> arrayList) {
        if (a == false) arrayListdata.postValue(arrayList);
        else {
            adapterReal(arrayList);
            check(true);
        }
        Log.d("setArrayList: ", String.valueOf(arrayList.size()));
    }

    private void adapterReal(List<Bus_Arrive_Info> bus_arrive_infos) {
        try {
            Log.d("TAG", "adapterReal: ");
            int count = 0;
            ArrayList<Bus_Arrive_Info> tempInfo = new ArrayList<>();
            ArrayList<Integer> abPo = new ArrayList<>();
            HashMap<String, Integer> map = new HashMap<>();
            for (int i = 0; i < mRecyclerViewAdapter.ArrayListDataSet_Arrive().size(); i++)
                map.put(mRecyclerViewAdapter.ArrayListDataSet_Arrive().get(i).getLINE_ID(), i);
            for (int j = 0; j < bus_arrive_infos.size(); j++) {
                if (map.containsKey(bus_arrive_infos.get(j).getLINE_ID())) {
                    tempInfo.add(bus_arrive_infos.get(j));
                    map.remove(bus_arrive_infos.get(j).getLINE_ID());
                    abPo.add(j);
                }
            }
            mRecyclerViewAdapter.notifyItemRangeChanged(0, mRecyclerViewAdapter.getItemCount(), tempInfo);

            if (map.size() != 0) {
                for (int i = 0; i < mRecyclerViewAdapter.ArrayListDataSet_Arrive().size(); i++) {
                    if (map.containsKey(mRecyclerViewAdapter.ArrayListDataSet_Arrive().get(i).getLINE_ID())) {
                        mRecyclerViewAdapter.NotifyItemRemoved(i);
                        count++;
                    }
                }
                mRecyclerViewAdapter.notifyItemRangeRemoved(mRecyclerViewAdapter.getItemCount(), count);
            }

            for (int m : abPo) bus_arrive_infos.set(m, null);
            while (bus_arrive_infos.remove(null)) ;
            if (bus_arrive_infos.size() != 0) {
                mRecyclerViewAdapter.ArrayListDataSet_Arrive((ArrayList<Bus_Arrive_Info>) bus_arrive_infos);
                mRecyclerViewAdapter.notifyItemRangeInserted(0, bus_arrive_infos.size() - mRecyclerViewAdapter.getItemCount());
            }

            count = 0;
            tempInfo.clear();
            map.clear();
            abPo.clear();
            bus_arrive_infos.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setmArrive_Data_SearchTask(String Busstop_Id) {
        long now = System.currentTimeMillis();
        Date mDate = new Date(now);
        SimpleDateFormat mFormat = new SimpleDateFormat("ss");
        final int[] i = {Integer.parseInt(mFormat.format(mDate))};
        mArrive_Data = Flowable.fromCallable(() -> {
                    Log.d("setmArrive_Data_SearchTask: ", Busstop_Id);
            return insert_api.select_Arrive(context, Busstop_Id);
        })
                .repeatWhen(d -> d.delay(60 - i[0], TimeUnit.SECONDS))
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(data -> {
                    i[0] = 0;
                    setArrayList(data);
                }, Throwable::getMessage);
    }

    public void roomDBLineSelecting(LineItemClick Click, int po) {
        try {
            Bus_Database db = Room.databaseBuilder(context, Bus_Database.class, "BusData")
                    .build();
            db.dao().getLineIndiv(Click.ArriveLineId(po, mRecyclerViewAdapter.ArrayListDataSet_Arrive())).observe((LifecycleOwner) context, new Observer<List<Bus_Line_Indiv>>() {
                @Override
                public void onChanged(List<Bus_Line_Indiv> bus_line_indivs) {

                    LineStationClassIntent(
                            mLineItemClick.ArriveLineName(po, mRecyclerViewAdapter.ArrayListDataSet_Arrive()),
                            mLineItemClick.ArriveLineId(po, mRecyclerViewAdapter.ArrayListDataSet_Arrive()),
                            bus_line_indivs.get(0).getLIKE_KIND(),
                            bus_line_indivs.get(0).getFIRST_RUN_TIME(),
                            bus_line_indivs.get(0).getLAST_RUN_TIME(),
                            bus_line_indivs.get(0).getRUN_INTERVAL(),
                            mLineItemClick.ArriveDIR_END(po, mRecyclerViewAdapter.ArrayListDataSet_Arrive()),
                            mLineItemClick.ArriveDIR_START(po, mRecyclerViewAdapter.ArrayListDataSet_Arrive())
                    );
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onClickMenuItem() {
        Log.d("TAG", "onClick: ");
        saveStopId = context.getSharedPreferences("dataId", 0);
        editor = saveStopId.edit();
        editor.putString("id", stopId);
        editor.commit();
        mArrive_Info.finish();
    }

    private void LineStationClassIntent(String putDataName, String putDataId, String putDataKind, String putF_Time,
                                        String putL_Time, String putRunInterval, String putDirDown, String putDirUp) {
        try {
            Intent lsInfo = new Intent((Arrive_Info) context, Line_station_info.class);
            lsInfo.putExtra("line_name", putDataName);
            lsInfo.putExtra("line_id", putDataId);
            lsInfo.putExtra("like_kind", putDataKind);
            lsInfo.putExtra("f_time", putF_Time);
            lsInfo.putExtra("l_time", putL_Time);
            lsInfo.putExtra("run_interval", putRunInterval);
            lsInfo.putExtra("dir_down", putDirDown);
            lsInfo.putExtra("dir_up", putDirUp);
            mArrive_Info.startActivity(lsInfo);
            mArrive_Info.finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        mArrive_Data.dispose();
    }


}