package edu.gatech.orangeblasters;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import edu.gatech.orangeblasters.account.AccountState;
import edu.gatech.orangeblasters.account.User;

/**
 * A login screen that offers login via email/password.
 */
public class WelcomeActivity extends AppCompatActivity {

     // UI references.
    private EditText mUserNameView;
    private EditText mPasswordView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((OrangeBlastersApplication) getApplication()).getAccounts()
                .add(new User("Default", "user", "pass", AccountState.NORMAL));

        setContentView(R.layout.activity_welcome);

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

        Button mEmailSignInButton = findViewById(R.id.signin);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        //Set up the Register form
        Button register = findViewById(R.id.register);
        register.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(WelcomeActivity.this, RegisterActivity.class));
            }
        });
    }

    private void attemptLogin() {
        final String userStr = mUserNameView.getText().toString();
        final String passStr = mPasswordView.getText().toString();
        if (((OrangeBlastersApplication) getApplication()).getAccounts().stream()
                .anyMatch(acc -> acc.getEmail().equals(userStr) && acc.getPassword().equals(passStr))) {
            startActivity(new Intent(this, ApplicationActivity.class));
        } else {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.myCoordinatorLayout), R.string.invalid_user_pass, Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        }
    }

    private void attemptRegister() {


    }
}

