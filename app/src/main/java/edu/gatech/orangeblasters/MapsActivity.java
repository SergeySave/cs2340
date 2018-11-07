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


        for (Location l: OrangeBlastersApplication.getInstance().getLocationService().getLocations().collect(Collectors.toList())) {
            LatLng location = new LatLng(l.getLatitude(), l.getLongitude());
            Marker m = mMap.addMarker(new MarkerOptions()
                    .position(location)
                    .title(l.getName() + " " + l.getPhoneNumber())
                    .snippet(l.getAddress())
                    .snippet(l.getPhoneNumber())
                    .snippet(l.getWebsite()));
            m.showInfoWindow();

        }

        mMap.moveCamera(CameraUpdateFactory.newLatLng(Atlanta));
        mMap.animateCamera(CameraUpdateFactory.zoomIn());
        mMap.animateCamera(CameraUpdateFactory.zoomTo(10),2000, null);
    }
}