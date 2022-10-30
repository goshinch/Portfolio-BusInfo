package com.example.portfolio_businfo.View.Activity;

import static com.example.portfolio_businfo.Recyclerview.CaseType.SearchCase.BUS_ARRIVE_INFORMATION;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.portfolio_businfo.Model.Bus_Arrive_Info;
import com.example.portfolio_businfo.R;
import com.example.portfolio_businfo.Recyclerview.ClickItem.LineItemClick;
import com.example.portfolio_businfo.Recyclerview.OnClickListener;
import com.example.portfolio_businfo.Recyclerview.RecyclerViewAdapter;
import com.example.portfolio_businfo.ViewModel.Activity.Arrive_Info_ViewModel;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class Arrive_Info extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private RecyclerViewAdapter mRecyclerViewAdapter;
    private Arrive_Info_ViewModel arrive_info_viewModel;
    private String busstop_name, busstop_id, ars_id, next_stop;
    private TextView Next_Stop, Ars_Id;
    private CollapsingToolbarLayout Busstop_Name;
    private ConstraintLayout open;
    private Resources res;
    private Drawable backgroundCorner;
    private Toolbar toolbar;
    private SharedPreferences getArsId;
    private SharedPreferences.Editor editor;
    int basic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arrive_info);
        arrive_info_viewModel = new ViewModelProvider(this).get(Arrive_Info_ViewModel.class);

        res = getResources();
        backgroundCorner = ResourcesCompat.getDrawable(res, R.drawable.bottom_right_corner, null);
        open = findViewById(R.id.Bar_open);
        basic = ContextCompat.getColor(this, R.color.basic);

        toolbar = findViewById(R.id.toolbar_arrive);
        mRecyclerView = findViewById(R.id.list_arriveinfo);
        Next_Stop = findViewById(R.id.next_stop);
        Ars_Id = findViewById(R.id.ars_id);
        Busstop_Name = findViewById(R.id.Bar_close);

        Intent intent = getIntent();
        busstop_name = intent.getStringExtra("putname");
        busstop_id = intent.getStringExtra("putid");
        ars_id = intent.getStringExtra("putars");
        next_stop = intent.getStringExtra("putnext");

        backgroundCorner.setTint(basic);
        open.setBackgroundResource(R.drawable.bottom_right_corner);

        Busstop_Name.setTitle(busstop_name);
        Ars_Id.setText(ars_id);
        Next_Stop.setText(next_stop);

        setSupportActionBar(toolbar);

        RecyclerInit();

        arrive_info_viewModel.init(this, mRecyclerViewAdapter, busstop_id);

        arrive_info_viewModel.setmArrive_Data_SearchTask(busstop_id);

        arrive_info_viewModel.setArrayListdata().observe(this, new Observer<List<Bus_Arrive_Info>>() {
            @Override
            public void onChanged(List<Bus_Arrive_Info> bus_arrive_infos) {
                Log.d("onChanged: ", String.valueOf(bus_arrive_infos.size()));
                mRecyclerViewAdapter.ArrayListDataSet_Arrive((ArrayList<Bus_Arrive_Info>) bus_arrive_infos);
            }
        });
    }

    private void RecyclerInit() {
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerViewAdapter = new RecyclerViewAdapter(BUS_ARRIVE_INFORMATION);
        mRecyclerViewAdapter.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v, int po) {
                try {
                    arrive_info_viewModel.roomDBLineSelecting(new LineItemClick(BUS_ARRIVE_INFORMATION), po);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.mapIn) {
            arrive_info_viewModel.onClickMenuItem();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        getArsId = getSharedPreferences("dataId", 0);
        editor = getArsId.edit();
        editor.clear().commit();
    }
}