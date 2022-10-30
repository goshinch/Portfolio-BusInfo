package com.example.portfolio_businfo.ViewModel.Activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.room.util.StringUtil;

import com.example.portfolio_businfo.Current_GPS.Current_Gps;
import com.example.portfolio_businfo.Model.Bus_Line_Station;
import com.example.portfolio_businfo.Model.Bus_Station;
import com.example.portfolio_businfo.R;
import com.example.portfolio_businfo.Room.Bus_Database;
import com.example.portfolio_businfo.View.Activity.Arrive_Info;
import com.example.portfolio_businfo.View.Activity.MainActivity;
import com.example.portfolio_businfo.XmlParse.Bus_SplashData_api;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class Main_ViewModel extends ViewModel {
    //숨겨진 페이지가 열렸는지 확인하는 변수
    private boolean isPageOpen;
    private Bus_SplashData_api insert_api;
    private Animation tranlateUpAnim;
    private Animation tranlateDownAnim;
    private Context context;
    private LinearLayout page;
    private ImageView button;
    private Bus_Database db;
    private GoogleMap mMap;
    private Marker mMarker;
    private Marker myMarker;
    private MarkerOptions myLocationMarker;
    private MarkerOptions mMkO;
    private View mView;
    private LatLng curPoint;
    private Location mLocation;
    private ArrayList<Bus_Station> bus_stations;

    private FusedLocationProviderClient fusedLocationClient;
    private Current_Gps gpsGet;
    private MutableLiveData<Location> setMapLocataion;

    public MutableLiveData<Location> getSetMapLocataion() {
        return setMapLocataion;
    }

    public void setSetMapLocataion(Location setMapLocataion) {
        this.setMapLocataion.postValue(setMapLocataion);
    }

    public void setPage(LinearLayout page) {
        this.page = page;
    }

    public void setPageOpen(boolean pageOpen) {
        isPageOpen = pageOpen;
    }

    public void init(Context context, LinearLayout page,
                     ImageView button, SupportMapFragment mapFragment, Bus_Database db, Current_Gps gpsGet, FusedLocationProviderClient fusedLocationClient) {
        this.context = context;
        this.page = page;
        this.button = button;
        this.db = db;
        this.bus_stations = new ArrayList<>();
        this.gpsGet = gpsGet;
        this.fusedLocationClient = fusedLocationClient;
        if (insert_api == null) insert_api = new Bus_SplashData_api();
        if (setMapLocataion == null) setMapLocataion = new MutableLiveData<>();

        mMkO = new MarkerOptions();

        mapFragment.getMapAsync((OnMapReadyCallback) context);

        //anim 폴더의 애니메이션을 가져와서 준비
        tranlateUpAnim = AnimationUtils.loadAnimation(context, R.anim.translate_up);
        tranlateDownAnim = AnimationUtils.loadAnimation(context, R.anim.translate_down);

        //페이지 슬라이딩 이벤트가 발생했을때 애니메이션이 시작 됐는지 종료 됐는지 감지할 수 있다.
        SlidingPageAnimationListener animListener = new SlidingPageAnimationListener();

        tranlateUpAnim.setAnimationListener(animListener);
        tranlateDownAnim.setAnimationListener(animListener);
    }

    public ArrayList<Bus_Station> getBus_stations() {
        return bus_stations;
    }

    public void setGoogleMap(ArrayList<Bus_Station> bus_stations, GoogleMap mMap) {
        this.bus_stations = bus_stations;
        mapReady(mMap);
    }

    public void onMaker(GoogleMap mMap, LatLng latLng, String title, String snippet) {
        mMap.addMarker(new MarkerOptions()
                        .position(latLng)
                        .title(title)
                        .snippet(snippet)
                        // 색인 순위
                        .zIndex(1.0f)
                        // 선택 마커에 따라 회전
                        .anchor(0.5f, 0.5f)
                        //투명도
//                .alpha(0.5f)
                        .icon(getIcon(context, R.drawable.station_icon))
        )
                .setTag(0);
    }

    public void mapWindowClick(Marker marker) {
        String arsId = marker.getSnippet().substring(marker.getSnippet().lastIndexOf("\n"));
        arsId = arsId.substring(1, arsId.length());
        for (Bus_Station i : bus_stations)
            if (i.getARS_ID().matches(arsId))
                ArriveInfoClassIntent(i.getBUSSTOP_NAME(), i.getNEXT_BUSSTOP(), i.getARS_ID(), i.getBUSSTOP_ID());
    }

    public void mapMarkerClick(Marker marker) {
        // Retrieve the data from the marker.
        Integer clickCount = (Integer) marker.getTag();
//        MarkerOptions mMkO = new MarkerOptions();
//        mMkO.position(marker.getPosition());
//        setMapCamera(marker.getPosition());
        // Check if a click count was set, then display the click count.
        if (clickCount != null) {
            clickCount = clickCount + 1;
            marker.setTag(clickCount);
            Toast.makeText(context,
                    marker.getTitle() +
                            " has been clicked " + clickCount + " times.",
                    Toast.LENGTH_SHORT).show();
        }

        // Return false to indicate that we have not consumed the event and that we wish
        // for the default behavior to occur (which is for the camera to move such that the
        // marker is centered and for the marker's info window to open, if it has one).
    }

    public void mapReady(GoogleMap googleMap) {
        this.mMap = googleMap;
        ArrayList<LatLng> sydney = new ArrayList<>();

        for (Bus_Station i : bus_stations)
            sydney.add(new LatLng(Double.parseDouble(i.getLATITUDE()), Double.parseDouble(i.getLONGITUDE())));
        for (int i = 0; i < bus_stations.size(); i++)
            onMaker(mMap, sydney.get(i), bus_stations.get(i).getBUSSTOP_NAME(),
                    bus_stations.get(i).getNEXT_BUSSTOP() + " 방향" + "\n" + bus_stations.get(i).getARS_ID());
        // Add a marker in Sydney and move the camera
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        mMap.setInfoWindowAdapter(infoWindowAdapter);

        mMap.setOnMarkerClickListener((GoogleMap.OnMarkerClickListener) context);
        mMap.setOnInfoWindowClickListener((GoogleMap.OnInfoWindowClickListener) context);

        if (curPoint == null)
            showMyLocationMarker(new LatLng(35.1595454,126.8526012));
        else
            showMyLocationMarker(curPoint);
    }

    public void setStationPosition(String BusstopId) {
        for (Bus_Station i : bus_stations) {
            if (i.getBUSSTOP_ID().equals(BusstopId)) {
                LatLng latLng = new LatLng(Double.parseDouble(i.getLATITUDE()), Double.parseDouble(i.getLONGITUDE()));
                mMarker = mMap.addMarker(new MarkerOptions().anchor(0.5F, 0.28F).alpha(0).position(latLng).title(i.getBUSSTOP_NAME()).snippet(i.getNEXT_BUSSTOP() + " 방향" + "\n" + i.getARS_ID()));
                mMarker.showInfoWindow();

                setMapCamera(latLng);
            }
        }
    }

    private GoogleMap.InfoWindowAdapter infoWindowAdapter = new GoogleMap.InfoWindowAdapter() {

        @Nullable
        @Override
        public View getInfoContents(@NonNull Marker marker) {
            if (marker.getPosition().equals(curPoint)) return null;
            return mView;
        }

        @Nullable
        @Override
        public View getInfoWindow(@NonNull Marker marker) {
            mView = LayoutInflater.from(context).inflate(R.layout.map_snippet, null);
            TextView title = mView.findViewById(R.id.title);
            TextView snippet = mView.findViewById(R.id.snippet);

            if (marker.getPosition().equals(curPoint)) {
                mView.setVisibility(View.GONE);
            } else {
                mView.setVisibility(View.VISIBLE);
                title.setText(marker.getTitle());
                snippet.setText(marker.getSnippet());
                return mView;
            }
            return null;
        }
    };

    private void setMapCamera(LatLng latLng) {
        mMap.moveCamera(CameraUpdateFactory.zoomTo(15));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
    }

    private BitmapDescriptor getIcon(Context context, int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    private class SlidingPageAnimationListener implements Animation.AnimationListener {
        @Override
        public void onAnimationStart(Animation animation) {

        }

        public void onAnimationEnd(Animation animation) {
            if (isPageOpen) {
                page.setVisibility(View.GONE);
                isPageOpen = false;
            } else {
                isPageOpen = true;
            }
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }

    public View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (isPageOpen) {
                PageDown();
                gpsGet.stopLocationUpdates();
            } else {
                PageUp();
                gpsGet.startLocationCallback();
                gpsGet.startLocationUpdates();
            }
        }
    };

    public void PageUp() {
        page.setVisibility(View.VISIBLE);
        page.startAnimation(tranlateUpAnim);
        button.startAnimation(tranlateUpAnim);
    }

    public void PageDown() {
        page.setVisibility(View.GONE);
        page.startAnimation(tranlateDownAnim);
        button.startAnimation(tranlateDownAnim);
    }

    private void ArriveInfoClassIntent(String putName, String putNext, String putArs, String putId) {
        Intent intent = new Intent((MainActivity) context, Arrive_Info.class);
        intent.putExtra("putname", putName);
        intent.putExtra("putars", putArs);
        intent.putExtra("putnext", putNext);
        intent.putExtra("putid", putId);
        context.startActivity(intent);
    }

    public void showMyLocationMarker(LatLng curPoint) {
        this.curPoint = curPoint;
        if (myLocationMarker == null) {
            myLocationMarker = new MarkerOptions(); // 마커 객체 생성
            myLocationMarker.position(curPoint);
            myLocationMarker.icon(getIcon(context, R.drawable.my_position));
            myMarker = mMap.addMarker(myLocationMarker);
            setMapCamera(curPoint);
        } else {
            myMarker.remove(); // 마커삭제
            myLocationMarker.position(this.curPoint);
            myMarker = mMap.addMarker(myLocationMarker);
        }
    }

    public void getLastLocation() {
        gpsGet.checkSelfPermission();
        fusedLocationClient.getLastLocation().addOnCompleteListener((MainActivity) context, onCompleteListener);
    }

    OnCompleteListener onCompleteListener = new OnCompleteListener() {
        @Override
        public void onComplete(@NonNull Task task) {
            if (task.isSuccessful() && task.getResult() != null) {
                mLocation = (Location) task.getResult();
                Log.d("onComplete: ", mLocation.getLatitude() + " / " + mLocation.getLongitude());
                curPoint = new LatLng(mLocation.getLatitude(),mLocation.getLongitude());
            }
        }
    };

    public View.OnClickListener onFloatingClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (getSetMapLocataion().getValue() != null)
                setMapCamera(new LatLng(getSetMapLocataion().getValue().getLatitude(), getSetMapLocataion().getValue().getLongitude()));
            else if (mLocation != null)
                setMapCamera(new LatLng(mLocation.getLatitude(), mLocation.getLongitude()));
            else
                setMapCamera(new LatLng(35.1595454,126.8526012));
        }
    };

    @Override
    protected void onCleared() {
        super.onCleared();

    }
}
