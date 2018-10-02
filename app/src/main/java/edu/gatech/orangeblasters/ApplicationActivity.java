package edu.gatech.orangeblasters;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

public class ApplicationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application);


        Button logout = findViewById(R.id.logout);
        logout.setOnClickListener(view -> finish());
    }
}
