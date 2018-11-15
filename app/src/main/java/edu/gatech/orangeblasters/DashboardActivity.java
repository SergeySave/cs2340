package edu.gatech.orangeblasters;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;



/**
 * A login Location Employees see right when they log in
 */
public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dashboard);

        Intent origIntent = getIntent();
        String userId = origIntent.getStringExtra(OrangeBlastersApplication.PARAM_USER_ID);

        Button locationButton = findViewById(R.id.locationButton);
        locationButton.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, LocationListActivity.class);
            intent.putExtra(OrangeBlastersApplication.PARAM_USER_ID, userId);
            startActivity(intent);
        });

        Button donationButton = findViewById(R.id.donationButton);
        donationButton.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, DonationListActivity.class);
            intent.putExtra(OrangeBlastersApplication.PARAM_USER_ID, userId);
            startActivity(intent);
        });

        Button mapButton = findViewById(R.id.mapButton);
        mapButton.setOnClickListener(v -> {
            finish();
//            Intent intent = new Intent(DashboardActivity.this, MapsActivity.class);
//            intent.putExtra(OrangeBlastersApplication.PARAM_USER_ID, userId);
//            startActivity(intent);
        });
    }
}