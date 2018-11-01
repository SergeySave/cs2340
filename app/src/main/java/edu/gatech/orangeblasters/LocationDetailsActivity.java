package edu.gatech.orangeblasters;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import java.util.Optional;

import edu.gatech.orangeblasters.location.Location;

public class LocationDetailsActivity extends AppCompatActivity {

    public static final String EXTRA_LOCATION_ID = "LOCATION_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_details);

        String userId = getIntent().getStringExtra(OrangeBlastersApplication.PARAM_USER_ID);

        String locId = getIntent().getStringExtra(EXTRA_LOCATION_ID);
        Optional<Location> optionalLocation = OrangeBlastersApplication.getInstance().getLocationService().getLocation(locId);
        if (!optionalLocation.isPresent()) {
            finish();
        } else {
            Location location = optionalLocation.get();

            TextView locName = findViewById(R.id.locationName);
            TextView locType = findViewById(R.id.locationType);
            TextView locAddr = findViewById(R.id.locationAddress);
            TextView locPNum = findViewById(R.id.locationNumber);

            locName.setText(location.getName());
            locType.setText(location.getType().getFullName());
            locAddr.setText(location.getAddress());
            locPNum.setText(location.getPhoneNumber());

            Button button = findViewById(R.id.donationButton);
            button.setOnClickListener(view -> {
                Intent intent = new Intent(this, DonationListActivity.class);
                intent.putExtra(OrangeBlastersApplication.PARAM_USER_ID, userId);
                intent.putExtra(DonationListActivity.PARAM_LOCATION_ID, location.getId());
                startActivity(intent);
            });
        }
    }
}
