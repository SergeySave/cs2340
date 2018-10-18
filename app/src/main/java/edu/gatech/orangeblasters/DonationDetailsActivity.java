package edu.gatech.orangeblasters;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import edu.gatech.orangeblasters.donation.Donation;
import edu.gatech.orangeblasters.location.Location;

public class DonationDetailsActivity extends AppCompatActivity {

    public static final String EXTRA_DONATION = "DONATION";

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation_details);

        Donation donation = (Donation) getIntent().getSerializableExtra(EXTRA_DONATION);

        TextView donTime = findViewById(R.id.donationTime);
        TextView donShortDes = findViewById(R.id.donationShortDes);
        TextView donLocation = findViewById(R.id.donationLocation);
        TextView donCategory = findViewById(R.id.donationCategory);
        TextView donComments = findViewById(R.id.donationComments);

        donTime.setText(donation.getTimestamp().getDayOfMonth());
        donLocation.setText(donation.getLocation().getName());
        donCategory.setText(donation.getDonationCategory().getFullName());
        donComments.setText(donation.getComments().toString());
    }
}