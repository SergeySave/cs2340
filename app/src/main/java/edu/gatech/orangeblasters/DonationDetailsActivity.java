package edu.gatech.orangeblasters;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Optional;

import edu.gatech.orangeblasters.donation.Donation;
import edu.gatech.orangeblasters.location.Location;

public class DonationDetailsActivity extends AppCompatActivity {

    public static final String EXTRA_DONATION = "DONATION";

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm-MM/dd/yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation_details);

//        Donation donation = ((Donation) getIntent().getSerializableExtra(EXTRA_DONATION));
        String donationId = getIntent().getStringExtra(EXTRA_DONATION);
        Donation donation = OrangeBlastersApplication.getInstance().getDonationService().getDonation(donationId).orElse(null);
        if (donation == null || donation.getLocationId() == null) {
            finish();
        }




        Location location = OrangeBlastersApplication.getInstance().getLocationService()
                .getLocation(Objects.requireNonNull(donation).getLocationId()).orElse(null);
        if (location == null || location.getName() == null) {
            finish();
        }

        TextView donTime = findViewById(R.id.time);
        TextView donShortDes = findViewById(R.id.itemname);
        TextView donLocation = findViewById(R.id.donationLocation);
        TextView donCategory = findViewById(R.id.donationCategory);
        TextView donComments = findViewById(R.id.donationComments);
        TextView donLongDes = findViewById(R.id.donationLongDes);
        ImageView image = findViewById(R.id.imageDisplay);

        donTime.setText(dateTimeFormatter.format(donation.getTimestamp()));
        donLocation.setText(Objects.requireNonNull(location).getName());
        donCategory.setText(donation.getDonationCategory().getFullName());
        donShortDes.setText(donation.getDescShort());
        Optional<String> comments = donation.getComments();
        if (comments.isPresent()) {
            donComments.setText(comments.get());
            donComments.setVisibility(View.VISIBLE);
        } else {
            donComments.setVisibility(View.INVISIBLE);
        }
        donLongDes.setText(donation.getDescLong());
        Optional<String> pictureId = donation.getPictureId();
        image.setVisibility(View.INVISIBLE);
        pictureId.ifPresent(s -> OrangeBlastersApplication.getInstance().getBitmapService().getBitmap(s, bitmap -> {
            bitmap.ifPresent(bm -> {
                image.setImageBitmap(bm);

                image.setVisibility(View.VISIBLE);
            });
        }));
    }
}