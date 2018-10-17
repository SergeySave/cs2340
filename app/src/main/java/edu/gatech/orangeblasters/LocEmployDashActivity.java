package edu.gatech.orangeblasters;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;

import java.util.Optional;

import edu.gatech.orangeblasters.account.Account;
import edu.gatech.orangeblasters.account.LocationEmployee;

/**
 * A login Location Employees see right when they log in
 */
public class LocEmployDashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_locemploydash);

        Button mAddDonationButton = findViewById(R.id.addDonation);
        mAddDonationButton.setOnClickListener(view -> addingDonation());

        //Set up the Register form
        Button register = findViewById(R.id.register);
        register.setOnClickListener(v -> startActivity(new Intent(LocEmployDashActivity.this, RegisterActivity.class)));
    }
    private void addingDonation() {
        startActivity(new Intent (this, AddDonationActivity.class));
    }
}