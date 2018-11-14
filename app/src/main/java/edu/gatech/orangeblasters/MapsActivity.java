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

import java.util.stream.Stream;

import edu.gatech.orangeblasters.location.Location;
import edu.gatech.orangeblasters.location.LocationService;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback  {

    public static final LatLng ATLANTA = new LatLng(33.748997, -84.387985);
    public static final int ZOOM_LEVEL = 10;
    public static final int ZOOM_TIME = 2000;

    private OrangeBlastersApplication orangeBlastersApplication = OrangeBlastersApplication.getInstance();
    private LocationService locationService = orangeBlastersApplication.getLocationService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String userId = getIntent().getStringExtra(OrangeBlastersApplication.PARAM_USER_ID);

        setContentView(R.layout.activity_google_map);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
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
        GoogleMap mMap = googleMap;

        Stream<Location> locations = locationService.getLocations();
        locations.forEach(l -> {
            LatLng location = new LatLng(l.getLatitude(), l.getLongitude());
            Marker m = mMap.addMarker(new MarkerOptions()
                    .position(location)
                    .title(l.getName())
                    .snippet(l.getPhoneNumber()));
            m.showInfoWindow();
        });

        mMap.moveCamera(CameraUpdateFactory.newLatLng(ATLANTA));
        mMap.animateCamera(CameraUpdateFactory.zoomIn());
        mMap.animateCamera(CameraUpdateFactory.zoomTo(ZOOM_LEVEL), ZOOM_TIME, null);
    }
}