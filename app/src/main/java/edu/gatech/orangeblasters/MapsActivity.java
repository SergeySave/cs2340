package edu.gatech.orangeblasters;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.stream.Stream;

import edu.gatech.orangeblasters.location.Location;
import edu.gatech.orangeblasters.location.LocationService;

/**
 * Represents a screen the map
 */
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback  {

    private static final LatLng ATLANTA = new LatLng(33.748997, -84.387985);
    private static final int ZOOM_LEVEL = 10;
    private static final int ZOOM_TIME = 2000;

    private final OrangeBlastersApplication orangeBlastersApplication =
            OrangeBlastersApplication.getInstance();
    private final LocationService locationService = orangeBlastersApplication.getLocationService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent origIntent = getIntent();
        String userId = origIntent.getStringExtra(OrangeBlastersApplication.PARAM_USER_ID);

        setContentView(R.layout.activity_google_map);
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        SupportMapFragment mapFragment = (SupportMapFragment) supportFragmentManager
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Button dashboard = findViewById(R.id.dashboard_button);
        dashboard.setOnClickListener(v -> {
            Intent intent = new Intent(MapsActivity.this, DashboardActivity.class);
            intent.putExtra(OrangeBlastersApplication.PARAM_USER_ID, userId);
            startActivity(intent);
        });


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        Stream<Location> locations = locationService.getLocations();
        locations.forEach(l -> {
            LatLng location = new LatLng(l.getLatitude(), l.getLongitude());
            MarkerOptions markerOptions = new MarkerOptions();
            MarkerOptions position = markerOptions.position(location);
            MarkerOptions title = position.title(l.getName());
            Marker marker = googleMap.addMarker(title.snippet(l.getPhoneNumber()));
            marker.showInfoWindow();
        });

        googleMap.moveCamera(CameraUpdateFactory.newLatLng(ATLANTA));
        googleMap.animateCamera(CameraUpdateFactory.zoomIn());
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(ZOOM_LEVEL), ZOOM_TIME, null);
    }
}