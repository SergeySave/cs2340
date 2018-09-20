package edu.gatech.orangeblasters;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * A login screen that offers login via username/password.
 */
public class LoginActivity extends AppCompatActivity {

     // UI references.
    private EditText mUserNameView;
    private EditText mPasswordView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Set up the login form.
        mUserNameView = findViewById(R.id.username);

        mPasswordView = findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = findViewById(R.id.username_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        //Set up the Register form
        Button register = findViewById(R.id.registerButton);
        register.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }

    private void attemptLogin() {
        if ("user".equals(mUserNameView.getText().toString()) && "pass".equals(mPasswordView.getText().toString())) {
            startActivity(new Intent(LoginActivity.this, ApplicationActivity.class));
        } else {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.myCoordinatorLayout), "Invalid username or password.", 5);
            mySnackbar.show();
        }
    }
}

