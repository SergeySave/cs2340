package edu.gatech.orangeblasters;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.time.format.DateTimeFormatter;
import java.util.Optional;

import edu.gatech.orangeblasters.donation.Donation;

public class DonationDetailsActivity extends AppCompatActivity {

    public static final String EXTRA_DONATION = "DONATION";

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm-MM/dd/yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation_details);

        Donation donation = getIntent().getParcelableExtra(EXTRA_DONATION);

        TextView donTime = findViewById(R.id.donationTime);
        TextView donShortDes = findViewById(R.id.donationShortDes);
        TextView donLocation = findViewById(R.id.donationLocation);
        TextView donCategory = findViewById(R.id.donationCategory);
        TextView donComments = findViewById(R.id.donationComments);
        TextView donLongDes = findViewById(R.id.donationLongDes);
        ImageView iamge = findViewById(R.id.imageDisplay);

        donTime.setText(dateTimeFormatter.format(donation.getTimestamp()));
        donLocation.setText(donation.getLocation().getName());
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
        int pictureLocation = donation.getPictureLocation();
        if (pictureLocation >= 0) {
            iamge.setImageBitmap(((OrangeBlastersApplication) getApplication()).getBitmaps().get(pictureLocation));

            iamge.setVisibility(View.VISIBLE);
        } else {
            iamge.setVisibility(View.INVISIBLE);
        }
    }
}