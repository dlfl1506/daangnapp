package com.cos.daangnapp.location;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cos.daangnapp.R;
import com.cos.daangnapp.location.adapter.LocationAdapter;
import com.cos.daangnapp.location.model.CMRespDto;
import com.cos.daangnapp.location.model.LocationReqDto;
import com.cos.daangnapp.location.model.LocationRespDto;
import com.cos.daangnapp.location.service.LocationService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LocationActivity extends AppCompatActivity {

    private Button LocationSearchBtn;
    private static final String TAG = "LocationActivity";
    private LocationService locationService = LocationService .retrofit.create(LocationService .class);
    private RecyclerView rvLocationList;
    private LocationAdapter locationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        init();
        getLocation();
        LocationSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLocation();
            }
        });

    }

    public void init(){
        LocationSearchBtn = findViewById(R.id.location_btn_search);
        rvLocationList= findViewById(R.id.rv_locationlist);
        LinearLayoutManager manager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rvLocationList.setLayoutManager(manager);

        locationAdapter = new LocationAdapter(this);
        rvLocationList.setAdapter(locationAdapter);
    }

    final LocationListener gpsLocationListener = new LocationListener() {
        public void onLocationChanged(Location location) {

            String provider = location.getProvider();
            double longitude = location.getLongitude();
            double latitude = location.getLatitude();
            double altitude = location.getAltitude();

            Log.d(TAG, "onLocationChanged: "+"위치정보 : " + provider + "\n" +
                    "위도 : " + latitude + "\n" +
                 "경도 : " + longitude + "\n" +
                   "고도  : " + altitude);

            LocationReqDto locationReqDto = new LocationReqDto(longitude,latitude);

            LocationSearch(locationReqDto);



        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onProviderDisabled(String provider) {
        }
    };


    public void getLocation(){
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if ( Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission( getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions( LocationActivity.this, new String[] {  android.Manifest.permission.ACCESS_FINE_LOCATION  },
                    0 );
        }
        else{
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    1000,
                    1,
                    gpsLocationListener);
            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                    1000,
                    1,
                    gpsLocationListener);
        }
    }


    public void LocationSearch(LocationReqDto locationReqDto){
        Call<CMRespDto<List<LocationRespDto>>> call = locationService.getLocations(locationReqDto);
        call.enqueue(new Callback<CMRespDto<List<LocationRespDto>>>() {
            @Override
            public void onResponse(Call<CMRespDto<List<LocationRespDto>>> call, Response<CMRespDto<List<LocationRespDto>>> response) {
                CMRespDto<List<LocationRespDto>> cmRespDto = response.body();
                List<LocationRespDto> locations = cmRespDto.getData();
                Log.d(TAG, "onResponse: "+locations);
                locationAdapter.setLocations(locations);
            }

            @Override
            public void onFailure(Call<CMRespDto<List<LocationRespDto>>> call, Throwable t) {
                Log.d(TAG, "onFailure: 실패");
            }
        });
    }


}



