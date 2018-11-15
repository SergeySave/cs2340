package edu.gatech.orangeblasters;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Optional;

import edu.gatech.orangeblasters.location.Location;
import edu.gatech.orangeblasters.location.LocationService;
import edu.gatech.orangeblasters.location.LocationType;

public class LocationDetailsActivity extends AppCompatActivity {

    public static final String EXTRA_LOCATION_ID = "LOCATION_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_details);

        Intent origIntent = getIntent();
        String userId = origIntent.getStringExtra(OrangeBlastersApplication.PARAM_USER_ID);

        String locId = origIntent.getStringExtra(EXTRA_LOCATION_ID);
        OrangeBlastersApplication orangeBlastersApplication = OrangeBlastersApplication.getInstance();
        LocationService locationService = orangeBlastersApplication.getLocationService();
        Optional<Location> optionalLocation = locationService.getLocation(locId);
        if (!optionalLocation.isPresent()) {
            finish();
        } else {
            Location location = optionalLocation.get();

            TextView locName = findViewById(R.id.locationName);
            TextView locType = findViewById(R.id.locationType);
            TextView locAddress = findViewById(R.id.locationAddress);
            TextView locPNum = findViewById(R.id.locationNumber);

            locName.setText(location.getName());
            LocationType type = location.getType();
            locType.setText(type.getFullName());
            locAddress.setText(location.getAddress());
            locPNum.setText(location.getPhoneNumber());

            ImageView button = findViewById(R.id.go_to_donation);
            button.setOnClickListener(view -> {
                Intent intent = new Intent(this, DonationListActivity.class);
                intent.putExtra(OrangeBlastersApplication.PARAM_USER_ID, userId);
                intent.putExtra(DonationListActivity.PARAM_LOCATION_ID, location.getId());
                startActivity(intent);
            });
        }
    }
}
