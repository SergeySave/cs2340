package edu.gatech.orangeblasters;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import edu.gatech.orangeblasters.bitmap.BitmapService;
import edu.gatech.orangeblasters.donation.DonationCategory;

public class AddDonationActivity extends AppCompatActivity {
    //private Button uploadButton;
    private EditText donationComments;
    private EditText shortDesc;
    private EditText longDesc;
    private EditText price;
    private EditText date;
    private EditText time;
    private Spinner category;
//    private Uri uri;
    private Bitmap bitmap;
    private static final int RESULT_LOAD_IMAGE = 1;


    public static final String RETURN_IMAGE = "IMAGE_BITMAP";
    public static final String RETURN_DESC_SHORT = "DESC_SHORT";
    public static final String RETURN_DESC_LONG = "DESC_LONG";
    public static final String RETURN_PRICE = "PRICE";
    public static final String RETURN_CATEGORY = "CATEGORY";
    public static final String RETURN_COMMENTS = "COMMENTS";
    public static final String RETURN_TIME = "TIME";

    private static final DateTimeFormatter dateFormatter =
            DateTimeFormatter.ofPattern("MM/dd/yyyy");
    private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    private final OrangeBlastersApplication orangeBlastersApplication = OrangeBlastersApplication.getInstance();
    private final BitmapService bitmapService = orangeBlastersApplication.getBitmapService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_donation);

        ImageView imageUpload = findViewById(R.id.imageUpload);
        //uploadButton =  findViewById(R.id.uploadButton);
        shortDesc = findViewById(R.id.donationShortDes);
        longDesc = findViewById(R.id.donationLongDes);
        price = findViewById(R.id.donationPrice);
        date = findViewById(R.id.donationDate);
        time = findViewById(R.id.donationTime);
        category = findViewById(R.id.donationCategory);
        donationComments = findViewById(R.id.donationComments);
        Button saveButton = findViewById(R.id.saveButton);

        ArrayAdapter<DonationCategory> adapter = new ArrayAdapter<>
                (this, android.R.layout.simple_spinner_item, DonationCategory.values());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category.setAdapter(adapter);
        category.setSelection(0);

        imageUpload.setOnClickListener(this::uploadImage);
        //uploadButton.setOnClickListener(this::uploadImage);
        saveButton.setOnClickListener(this::returnDonation);
    }

    /**
     * Uploads the Image
     *
     * @param v the view being passed in
     */
    private void uploadImage(View v) {
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, RESULT_LOAD_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == RESULT_LOAD_IMAGE) && (resultCode == RESULT_OK) && (null != data)) {
            Uri uri = data.getData();

            ImageView imageUpload = findViewById(R.id.imageUpload);
//            imageUpload.setImageURI(uri);
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                imageUpload.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Returns the donation
     *
     * @param v the View being passed in
     */
    private void returnDonation(View v) {
        Intent data = new Intent();

        if (bitmap != null) {
            String id = bitmapService.addBitmap(bitmap);
            data.putExtra(RETURN_IMAGE, id);
        } else {
            data.putExtra(RETURN_IMAGE, (String)null);
        }
        Editable shortDescText = shortDesc.getText();
        data.putExtra(RETURN_DESC_SHORT, shortDescText.toString());
        Editable longDescText = longDesc.getText();
        data.putExtra(RETURN_DESC_LONG, longDescText.toString());
        Editable priceText = price.getText();
        data.putExtra(RETURN_PRICE, priceText.toString());
        data.putExtra(RETURN_CATEGORY, ((DonationCategory) category.getSelectedItem()));
        Editable donationCommentsText = donationComments.getText();
        data.putExtra(RETURN_COMMENTS, donationCommentsText.toString());
        //And the time
        Editable dateText = this.date.getText();
        String date = dateText.toString();
        LocalDate localDate = LocalDate.parse(date, dateFormatter);
        Editable timeText = this.time.getText();
        String time = timeText.toString();
        LocalTime localTime = LocalTime.parse(time, timeFormatter);
        LocalDateTime localDateTime = localDate.atTime(localTime);
        data.putExtra(RETURN_TIME, localDateTime.atOffset(ZoneOffset.UTC));
        //return it
        setResult(RESULT_OK, data);
        finish();
    }
}
