package edu.gatech.orangeblasters;

import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Button;
import android.net.Uri;
import android.graphics.BitmapFactory;

import android.database.Cursor;

public class AddDonationActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView imageUpload;
    Button uploadButton;
    EditText donationComments;
    private static int RESULT_LOAD_IMAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_donation);

        imageUpload =  findViewById(R.id.imageUpload);
        uploadButton =  findViewById(R.id.uploadButton);
        donationComments = findViewById(R.id.donationComments);

        imageUpload.setOnClickListener(this);
        uploadButton.setOnClickListener(this);
        donationComments.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent (
                Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();

//            String[] filePathColumn = { MediaStore.Images.Media.DATA };
//
//            Cursor cursor = getContentResolver().query(selectedImage,
//                    filePathColumn, null, null, null);
//            cursor.moveToFirst();
//
//            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//            String picturePath = cursor.getString(columnIndex);
//            cursor.close();

            ImageView imageUpload = findViewById(R.id.imageUpload);
            imageUpload.setImageURI(selectedImage);
//            imageUpload.setImageBitmap(BitmapFactory.decodeFile(picturePath));
        }
    }
}
