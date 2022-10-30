package com.example.portfolio_businfo.ViewModel.Activity;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.portfolio_businfo.Model.Bus_Line;
import com.example.portfolio_businfo.Model.Bus_Line_Station;
import com.example.portfolio_businfo.Model.Bus_Station;
import com.example.portfolio_businfo.Room.Bus_Database;
import com.example.portfolio_businfo.View.Activity.MainActivity;
import com.example.portfolio_businfo.View.Activity.Splash;
import com.example.portfolio_businfo.XmlParse.Bus_SplashData_api;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class Splash_ViewModel extends ViewModel {
    private static Disposable backgroundtask;
    private static Bus_SplashData_api line_api;
    private Bus_Database db;
    private Splash mSplash;
    private MutableLiveData<Boolean> chk;

    public LiveData<Boolean> check() {
        return chk;
    }

    public void init(Context context, Bus_Database db) {
        this.mSplash = (Splash) context;
        this.db = db;
        chk = new MutableLiveData<>();
    }

    public void loading(Boolean chk) {
        this.chk.setValue(chk);
    }

    public void backgroundTask() {
        backgroundtask = Flowable.fromCallable(() -> {
            roomInsertLines();
            roomInsertStations();
            return true;
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    loading(result);
                    FinishActivity();
                }, Throwable :: getMessage);
    }

    private void roomInsertLines() {
        line_api = new Bus_SplashData_api();
        for (Bus_Line i : line_api.lines()) {
            db.dao().Insert_Line(new Bus_Line(i.getLINE_ID(), i.getLINE_NAME(), i.getDIR_UP_NAME(),
                    i.getDIR_DOWN_NAME(), i.getFIRST_RUN_TIME(), i.getLAST_RUN_TIME(), i.getRUN_INTERVAL(), i.getLIKE_KIND(), false));
        }
    }
    private void roomInsertStations() {
        line_api = new Bus_SplashData_api();
        for (Bus_Station j : line_api.stations()) {
            db.dao().Insert_Station(new Bus_Station(j.getBUSSTOP_ID(), j.getBUSSTOP_NAME(), j.getNAME_E(),
                    j.getLONGITUDE(), j.getLATITUDE(), j.getARS_ID(), j.getNEXT_BUSSTOP(), false));
        }
    }
    public void FinishActivity() {
        Intent main = new Intent(mSplash, MainActivity.class);
        mSplash.startActivity(main);
        mSplash.finish();
        onCleared();
    }

    @Override
    protected void onCleared() {
        if (backgroundtask != null) backgroundtask.dispose();
        super.onCleared();
    }
}
