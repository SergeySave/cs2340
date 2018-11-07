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
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.stream.Collectors;

import edu.gatech.orangeblasters.location.Location;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String userId = getIntent().getStringExtra(OrangeBlastersApplication.PARAM_USER_ID);

        setContentView(R.layout.activity_google_map);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Button dashboard = findViewById(R.id.dashboardbutton);
        dashboard.setOnClickListener(v -> {
            Intent intent = new Intent(MapsActivity.this, DashboardActivity.class);
            intent.putExtra(OrangeBlastersApplication.PARAM_USER_ID, userId);
            startActivity(intent);
        });


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        GoogleMap mMap = googleMap;

        LatLng Atlanta = new LatLng(33.748997, -84.387985);


        for (Location l: OrangeBlastersApplication.getInstance().getLocationService().getLocations().collect(Collectors.toList())) {
            LatLng location = new LatLng(l.getLatitude(), l.getLongitude());
            Marker m = mMap.addMarker(new MarkerOptions()
                    .position(location)
                    .title(l.getName())
                    .snippet(l.getPhoneNumber()));
            m.showInfoWindow();

        }

        mMap.moveCamera(CameraUpdateFactory.newLatLng(Atlanta));
        mMap.animateCamera(CameraUpdateFactory.zoomIn());
        mMap.animateCamera(CameraUpdateFactory.zoomTo(10),2000, null);
    }
}