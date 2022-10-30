package com.example.portfolio_businfo.View.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.portfolio_businfo.Model.Bus_Line_Station;
import com.example.portfolio_businfo.R;
import com.example.portfolio_businfo.Room.Bus_Database;
import com.example.portfolio_businfo.ViewModel.Activity.Line_station_info_ViewModel;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class Line_station_info extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private Line_station_info_ViewModel line_station_info_viewModel;
    private Bus_Database db;
    private String Line_Id, Line_Name, Run_Interval, F_Time, L_Time, Like_kind, Dir_Up, Dir_Down;
    private TextView dir_up, dir_down, run_interval, like_kind, f_time, l_time;
    private ConstraintLayout open;
    private CollapsingToolbarLayout close;
    private Drawable backgroundCorner;
    private Resources res;
    private FloatingActionButton research;
    private LinearLayout buffering;
    private ProgressBar bufferBar;
    private SharedPreferences getArsId;
    private SharedPreferences.Editor editor;
    private Boolean isDataSet;
    int one, two, three, four, five;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_station_info);

        line_station_info_viewModel = new ViewModelProvider(this).get(Line_station_info_ViewModel.class);

        db = Room.databaseBuilder(this, Bus_Database.class, "BusData").build();

        res = getResources();
        backgroundCorner = ResourcesCompat.getDrawable(res, R.drawable.bottom_right_corner, null);
        open = findViewById(R.id.Bar_open);
        close = findViewById(R.id.Bar_close);
        mRecyclerView = findViewById(R.id.list_station_arrive);
        like_kind = findViewById(R.id.like_kind);
        f_time = findViewById(R.id.f_time);
        l_time = findViewById(R.id.l_time);
        run_interval = findViewById(R.id.run_time);
        dir_down = findViewById(R.id.DIR_DOWN_NAME);
        dir_up = findViewById(R.id.DIR_UP_NAME);
        research = findViewById(R.id.research);
        buffering = findViewById(R.id.buffering);
        bufferBar = findViewById(R.id.bufferBar);

        Intent intent = getIntent();
        Line_Name = intent.getStringExtra("line_name");
        Line_Id = intent.getStringExtra("line_id");
        Like_kind = intent.getStringExtra("like_kind");
        F_Time = intent.getStringExtra("f_time");
        L_Time = intent.getStringExtra("l_time");
        Run_Interval = intent.getStringExtra("run_interval");
        Dir_Up = intent.getStringExtra("dir_up");
        Dir_Down = intent.getStringExtra("dir_down");

        f_time.setText(F_Time);
        l_time.setText(L_Time);
        run_interval.setText(Run_Interval);
        dir_up.setText(Dir_Up);
        dir_down.setText(Dir_Down);

        f_time.setTextColor(R.color.black);
        l_time.setTextColor(R.color.black);
        run_interval.setTextColor(R.color.black);
        like_kind.setTextColor(R.color.black);
        dir_up.setTextColor(R.color.black);
        dir_down.setTextColor(R.color.black);

        one = ContextCompat.getColor(this, R.color.one);
        two = ContextCompat.getColor(this, R.color.two);
        three = ContextCompat.getColor(this, R.color.three);
        four = ContextCompat.getColor(this, R.color.four);
        five = ContextCompat.getColor(this, R.color.five);

        close.setTitle(Line_Name);
        setToolbarColor(Like_kind);
        buffering.bringToFront();

        line_station_info_viewModel.init(this, db, Line_Id, buffering, bufferBar);

        research.setOnClickListener(line_station_info_viewModel.onClickListener);

        line_station_info_viewModel.setRecyclerView(mRecyclerView);

        line_station_info_viewModel.RecyclerInit();

        db.dao().getLSExist(Line_Id).observe(this, new Observer<String>() {
            @Override
            public void onChanged(String chk) {
                if (chk.equals("0")){
                    Log.d("check: 값이 없다.",chk);
                    line_station_info_viewModel.getLineStationData_RoomSet(Line_Id);
                }
                else if (chk.equals("1")) {
                    Log.d("check: 값이 있다.",chk);
                    line_station_info_viewModel.checkLSDataisExist(Line_Id);
                }
            }
        });
        setRealtimeBP();
    }

    @Override
    protected void onResume() {
        super.onResume();
        line_station_info_viewModel.setArrayListdata().observe(this, new Observer<List<Bus_Line_Station>>() {
            @Override
            public void onChanged(List<Bus_Line_Station> bus_line_stations) {
                    line_station_info_viewModel.getmRecyclerViewAdapter().clear_Line_Station();
                    isDataSet = line_station_info_viewModel.getmRecyclerViewAdapter().ArrayListDataSet_LS((ArrayList<Bus_Line_Station>) bus_line_stations);

                    if (isDataSet) {
                        setRealtimeBP();
                        isDataSet = false;
                    }
            }
        });
    }
    private void setRealtimeBP() {
        line_station_info_viewModel.RealTimeBusPosition(Line_Id);
    }

    private void setToolbarColor(String id){
        switch (id){
            case "1":
                like_kind.setText("급행간선");
                backgroundCorner.setTint(one);
                open.setBackgroundResource(R.drawable.bottom_right_corner);
                break;
            case "2":
                like_kind.setText("간선");
                backgroundCorner.setTint(two);
                open.setBackgroundResource(R.drawable.bottom_right_corner);
                break;
            case "3":
                like_kind.setText("지선");
                backgroundCorner.setTint(three);
                open.setBackgroundResource(R.drawable.bottom_right_corner);
                break;
            case "4":
                like_kind.setText("마을버스");
                backgroundCorner.setTint(four);
                open.setBackgroundResource(R.drawable.bottom_right_corner);
                break;
            default:
                like_kind.setText("공항버스");
                backgroundCorner.setTint(five);
                open.setBackgroundResource(R.drawable.bottom_right_corner);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("TAG", "onDestroy: ");
        line_station_info_viewModel.disposible();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("TAG", "onStop: ");
        line_station_info_viewModel.disposible();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        getArsId = getSharedPreferences("dataId", 0);
        editor = getArsId.edit();
        editor.clear().commit();
    }
}