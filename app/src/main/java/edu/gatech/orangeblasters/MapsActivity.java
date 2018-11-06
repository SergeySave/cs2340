package edu.gatech.orangeblasters;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Button dashboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_google_map);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        dashboard = findViewById(R.id.dashboardbutton);
        dashboard.setOnClickListener(v -> startActivity(new Intent(MapsActivity.this, DashboardActivity.class)));

        
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng Atlanta = new LatLng(33.748997, -84.387985);
        mMap.addMarker(new MarkerOptions().position(Atlanta));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Atlanta));
        mMap.setMaxZoomPreference(mMap.getMaxZoomLevel());
    }
}