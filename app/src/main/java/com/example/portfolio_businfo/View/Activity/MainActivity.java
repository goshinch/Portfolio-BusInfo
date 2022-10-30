package com.example.portfolio_businfo.View.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;
import androidx.viewpager2.widget.ViewPager2;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.portfolio_businfo.Current_GPS.Current_Gps;
import com.example.portfolio_businfo.Model.Bus_Station;
import com.example.portfolio_businfo.R;
import com.example.portfolio_businfo.Room.Bus_Database;
import com.example.portfolio_businfo.View.ViewAdapter.ViewPagerAdapter;
import com.example.portfolio_businfo.ViewModel.Activity.Main_ViewModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements GoogleMap.OnMarkerClickListener, GoogleMap.OnInfoWindowClickListener, OnMapReadyCallback {
    private EditText edt;
    private FloatingActionButton mMypo;
    private TabLayout mTabLayout;
    private ViewPager2 mViewPager;
    private LinearLayout page;
    private ImageView button;
    public Main_ViewModel mMain_viewModel;
    private SupportMapFragment mapFragment;
    private Bus_Database db;
    private SharedPreferences getstopId;
    private SharedPreferences.Editor editor;
    private FusedLocationProviderClient fusedLocationClient;
    private LocationManager locationManager;
    private Current_Gps gpsGet;
    final static int LOCATION_SETTING_CHECK = 0;
    public final static int PERMISSION_REQUEST = 0x001, REQUEST_CHECK_SETTINGS = 0x000;
    public final static String[] PERMISSIONS = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edt = findViewById(R.id.search_text);
        mMypo = findViewById(R.id.mypo);
        page = findViewById(R.id.page);
        button = findViewById(R.id.button);
        mTabLayout = findViewById(R.id.tablayout);
        mViewPager = findViewById(R.id.pager);
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        getstopId = getSharedPreferences("dataId", 0);
        editor = getstopId.edit();
        editor.clear().commit();

        mMain_viewModel = new ViewModelProvider(this).get(Main_ViewModel.class);

        db = Room.databaseBuilder(this, Bus_Database.class, "BusData").build();

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        gpsGet = new Current_Gps(this, MainActivity.this, locationManager, fusedLocationClient);

        mMain_viewModel.init(this, page, button, mapFragment, db, gpsGet, fusedLocationClient);

        ViewPagerAdapter mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), getLifecycle());
        mViewPager.setAdapter(mViewPagerAdapter);
        new TabLayoutMediator(mTabLayout, mViewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position) {
                    case 0:
                        tab.setText("즐겨찾기");
                        break;
                    case 1:
                        tab.setText("정류장");
                        break;
                    default:
                        tab.setText("버스");
                        break;
                }
            }
        }).attach();

        gpsGet.onCheckPermissions();

        gpsGet.createLocationRequest();

        button.setOnClickListener(mMain_viewModel.onClickListener);

        mMypo.setOnClickListener(mMain_viewModel.onFloatingClickListener);

        checkMapOpened();

        mMain_viewModel.getSetMapLocataion().observe(this, new Observer<Location>() {
            @Override
            public void onChanged(Location location) {
                Log.d("getSetMapLocataion: ", location.getLatitude() + "/" + location.getLongitude());
                if (location != null) {
                    mMain_viewModel.showMyLocationMarker(new LatLng(location.getLatitude(), location.getLongitude()));
                }
            }
        });
    }

    @Override
    protected void onStart() {
        if (!gpsGet.checkPermissions()) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_REQUEST);
        }
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();

        checkMapOpened();

        if (pageState()) {
            gpsGet.startLocationCallback();
            gpsGet.startLocationUpdates();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        gpsGet.stopLocationUpdates();
    }

    public void checkMapOpened() {

        String BusstopId = getstopId.getString("id", "null");
        if (!(BusstopId.equals("null"))) {
            mMain_viewModel.setStationPosition(BusstopId);
            mMain_viewModel.setPageOpen(false);
            mMain_viewModel.setPage(page);
            mMain_viewModel.PageUp();
        } else {
            mMain_viewModel.setPageOpen(true);
            mMain_viewModel.setPage(page);
            mMain_viewModel.PageDown();
        }
    }

    public boolean pageState() {
        switch (page.getVisibility()) {
            case 0:
                Log.d("pageState: ", String.valueOf(true));
                return true;
            default:
                Log.d("pageState: ", String.valueOf(false));
                return false;
        }
    }

    @Override
    public void onInfoWindowClick(@NonNull Marker marker) {
        mMain_viewModel.mapWindowClick(marker);
    }

    @Override
    public boolean onMarkerClick(@NonNull Marker marker) {
        mMain_viewModel.mapMarkerClick(marker);
        return false;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMain_viewModel.getLastLocation();

        if (mMain_viewModel.getBus_stations().size() == 0)
            db.dao().getStationAll().observe(this, new Observer<List<Bus_Station>>() {
                @Override
                public void onChanged(List<Bus_Station> bus_stations) {
                    mMain_viewModel.setGoogleMap((ArrayList<Bus_Station>) bus_stations, googleMap);
                }
            });
        else
            mMain_viewModel.mapReady(googleMap);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CHECK_SETTINGS:
                if (resultCode == LOCATION_SETTING_CHECK)
                    gpsGet.Dialog();
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case PERMISSION_REQUEST:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    Toast.makeText(this, "위치 권한이 확인 되었습니다.", Toast.LENGTH_SHORT).show();
                else
                    gpsGet.onCheckPermissions();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (page.getVisibility() == View.VISIBLE) {
            mMain_viewModel.setPageOpen(true);
            mMain_viewModel.setPage(page);
            mMain_viewModel.PageDown();
        } else {
            editor.clear();
            editor.commit();
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (editor != null) {
            editor.clear();
            editor.commit();
        }
    }
}