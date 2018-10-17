package edu.gatech.orangeblasters;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Button;

public class AddDonationActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView imageUpload;
    Button uploadButton;
    EditText donationComments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_donation);

        imageUpload = (ImageView) findViewById(R.id.imageUpload);
        uploadButton = (Button) findViewById(R.id.uploadButton);
        donationComments = (EditText) findViewById(R.id.donationComments);

        imageUpload.setOnClickListener(this);
        uploadButton.setOnClickListener(this);
        donationComments.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

    }
}
