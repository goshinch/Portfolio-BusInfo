package com.example.portfolio_businfo.View.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Log;
import android.widget.ProgressBar;

import com.example.portfolio_businfo.Model.Bus_Line_Station;
import com.example.portfolio_businfo.R;
import com.example.portfolio_businfo.Room.Bus_Database;
import com.example.portfolio_businfo.ViewModel.Activity.Splash_ViewModel;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.ThreeBounce;

import java.util.ArrayList;
import java.util.List;

public class Splash extends AppCompatActivity {
    private SharedPreferences db_check;
    private SharedPreferences.Editor editor;
    private boolean db_Down;
    private ProgressBar progressBar;
    private Splash_ViewModel splashViewModel;
    private Bus_Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        db = Room.databaseBuilder(this, Bus_Database.class, "BusData").build();
        splashViewModel = new ViewModelProvider(this).get(Splash_ViewModel.class);

        db_check = getSharedPreferences("check", 0);
        db_Down = db_check.getBoolean("download", false);

        progressBar = findViewById(R.id.progressBar);
        Sprite threeBounce = new ThreeBounce();
        threeBounce.setColor(R.color.one);
        progressBar.setIndeterminateDrawable(threeBounce);

        splashViewModel.init(this, db);

        if (!db_Down) {
            splashViewModel.check().observe(this, new Observer<Boolean>() {
                @Override
                public void onChanged(Boolean aBoolean) {
                    editor = db_check.edit();
                    editor.putBoolean("download", aBoolean).commit();
                }
            });
            splashViewModel.backgroundTask();
        }
        else {
            splashViewModel.FinishActivity();
        }
    }
}