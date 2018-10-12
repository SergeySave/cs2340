package edu.gatech.orangeblasters;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import edu.gatech.orangeblasters.location.Location;

public class LocationDetailsActivity extends AppCompatActivity {

    public static final String EXTRA_LOCATION = "LOCATION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_details);

        Location location = (Location) getIntent().getSerializableExtra(EXTRA_LOCATION);

        TextView locName = findViewById(R.id.locationName);
        TextView locType = findViewById(R.id.locationType);
        TextView locAddr = findViewById(R.id.locationAddress);
        TextView locPNum = findViewById(R.id.locationNumber);

        locName.setText(location.getName());
        locType.setText(location.getType().getFullName());
        locAddr.setText(location.getAddress());
        locPNum.setText(location.getPhoneNumber());
    }
}
