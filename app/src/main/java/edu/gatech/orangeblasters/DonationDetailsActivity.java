package edu.gatech.orangeblasters;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Optional;

import edu.gatech.orangeblasters.bitmap.BitmapService;
import edu.gatech.orangeblasters.donation.Donation;
import edu.gatech.orangeblasters.donation.DonationCategory;
import edu.gatech.orangeblasters.donation.DonationService;
import edu.gatech.orangeblasters.location.Location;
import edu.gatech.orangeblasters.location.LocationService;

public class DonationDetailsActivity extends AppCompatActivity {

    public static final String EXTRA_DONATION = "DONATION";

    private static final DateTimeFormatter dateTimeFormatter =
            DateTimeFormatter.ofPattern("HH:mm-MM/dd/yyyy");

    private final OrangeBlastersApplication orangeBlastersApplication =
            OrangeBlastersApplication.getInstance();
    private final DonationService donationService = orangeBlastersApplication.getDonationService();
    private final BitmapService bitmapService = orangeBlastersApplication.getBitmapService();
    private final LocationService locationService = orangeBlastersApplication.getLocationService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation_details);

        Intent intent = getIntent();
        String donationId = intent.getStringExtra(EXTRA_DONATION);
        Optional<Donation> optionalDonation = donationService.getDonation(donationId);
        Donation donation = optionalDonation.orElse(null);
        if ((donation == null) || (donation.getLocationId() == null)) {
            finish();
        }


        Donation nonNull = Objects.requireNonNull(donation);
        Optional<Location> optionalLocation = locationService
                .getLocation(nonNull.getLocationId());
        Location location = optionalLocation.orElse(null);
        if ((location == null) || (location.getName() == null)) {
            finish();
        }

        TextView donTime = findViewById(R.id.time);
        TextView donShortDes = findViewById(R.id.item_name);
        TextView donLocation = findViewById(R.id.donationLocation);
        TextView donCategory = findViewById(R.id.donationCategory);
        TextView donComments = findViewById(R.id.donationComments);
        TextView donLongDes = findViewById(R.id.donationLongDes);
        ImageView image = findViewById(R.id.imageDisplay);

        donTime.setText(dateTimeFormatter.format(donation.getTimestamp()));
        Location locationNonNull = Objects.requireNonNull(location);
        donLocation.setText(locationNonNull.getName());
        DonationCategory donationCategory = donation.getDonationCategory();
        donCategory.setText(donationCategory.getFullName());
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
        pictureId.ifPresent(s -> {
            bitmapService.getBitmap(s, bitmap -> bitmap.ifPresent(bm -> {
                image.setImageBitmap(bm);

                image.setVisibility(View.VISIBLE);
            }));
        });
    }
}