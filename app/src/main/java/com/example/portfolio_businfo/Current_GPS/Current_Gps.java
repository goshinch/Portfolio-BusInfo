package com.example.portfolio_businfo.Current_GPS;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;

import com.example.portfolio_businfo.View.Activity.MainActivity;
import com.example.portfolio_businfo.ViewModel.Activity.Main_ViewModel;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.Priority;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

public class Current_Gps extends MainActivity {
    private FusedLocationProviderClient fusedLocationClient;
    private LocationManager locationManager;
    private LocationCallback locationCallback;
    private LocationRequest locationRequest;
    private Context context;
    private MainActivity mainActivity;

    public Current_Gps(Context context, MainActivity mainActivity,LocationManager locationManager, FusedLocationProviderClient fusedLocationClient) {
        this.context = context;
        this.mainActivity = mainActivity;
        this.fusedLocationClient = fusedLocationClient;
        this.locationManager = locationManager;
    }

    public void startLocationCallback() {
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                super.onLocationResult(locationResult);
                if (locationResult == null) {
                    Toast.makeText(getApplicationContext(), "GPS가 켜져 있는지 확인해 주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                for (Location location : locationResult.getLocations()) {
                    // Update UI with location data
                    mainActivity.mMain_viewModel.setSetMapLocataion(location);
                    Log.d( "onLocationResult: ", location.getLatitude()+"/"+location.getLongitude());

                }
            }
        };
    }

    // LocationRequest.PRIORITY_HIGH_ACCURACY is deprecated 됐다고 한다.
    public void createLocationRequest() {
        locationRequest = LocationRequest.create();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(Priority.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);

        SettingsClient client = LocationServices.getSettingsClient(context);
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());

        task.addOnSuccessListener(mainActivity, onSuccessListener);

        task.addOnFailureListener(mainActivity, onFailureListener);
    }
    OnSuccessListener<LocationSettingsResponse> onSuccessListener = new OnSuccessListener<LocationSettingsResponse>() {
        @Override
        public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
            // All location settings are satisfied. The client can initialize
            // location requests here.
            // ...
            Log.d("onSuccess: ", String.valueOf(locationSettingsResponse.getLocationSettingsStates().isNetworkLocationUsable()));
        }
    };
    OnFailureListener onFailureListener = new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
            if (e instanceof ResolvableApiException) {
                // Location settings are not satisfied, but this can be fixed
                // by showing the user a dialog.
                try {
                    // Show the dialog by calling startResolutionForResult(),
                    // and check the result in onActivityResult().
                    ResolvableApiException resolvable = (ResolvableApiException) e;
                    resolvable.startResolutionForResult(mainActivity,
                            REQUEST_CHECK_SETTINGS);
                } catch (IntentSender.SendIntentException sendEx) {
                    // Ignore the error.
                    Log.d("onFailure: ", String.valueOf(sendEx));
                }
            }
        }
    };

    public void checkSelfPermission() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            onCheckPermissions();
            return;
        }
    }

    public void startLocationUpdates() {
        checkSelfPermission();
        fusedLocationClient.requestLocationUpdates(locationRequest,
                locationCallback,
                Looper.getMainLooper());
    }

    public void stopLocationUpdates() {
        if (fusedLocationClient != null && locationCallback != null)
            fusedLocationClient.removeLocationUpdates(locationCallback);
    }

    public void Dialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("GPS를 켜주세요.").setMessage("GPS가 꺼진 상태로 앱을 이용할 수 없습니다.");
        builder.setPositiveButton("동의", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
                    //GPS 설정화면으로 이동
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    intent.addCategory(Intent.CATEGORY_DEFAULT);
                    mainActivity.startActivity(intent);
                }
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(mainActivity.getApplicationContext(), "GPS가 off 되어있어 현재 위치를 확인할 수 없습니다.", Toast.LENGTH_SHORT).show();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


    public boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_COARSE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    public void onCheckPermissions() {
        try {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_DENIED ||
                    ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(mainActivity, Manifest.permission.ACCESS_FINE_LOCATION)) {
                    ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_REQUEST);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }
}
