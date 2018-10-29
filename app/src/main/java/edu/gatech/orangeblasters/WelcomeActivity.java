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
 * A login screen that offers login via email/password.
 */
public class WelcomeActivity extends AppCompatActivity {

     // UI references.
    private EditText mUserNameView;
    private EditText mPasswordView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_welcome);

        // Set up the login form.
        mUserNameView = findViewById(R.id.username);

        mPasswordView = findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener((textView, id, keyEvent) -> {
            if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                attemptLogin();
                return true;
            }
            return false;
        });

        Button mEmailSignInButton = findViewById(R.id.signin);
        mEmailSignInButton.setOnClickListener(view -> attemptLogin());

        //Set up the Register form
        Button register = findViewById(R.id.register);
        register.setOnClickListener(v -> startActivity(new Intent(WelcomeActivity.this, RegisterActivity.class)));
    }

    private void attemptLogin() {
        final String userStr = mUserNameView.getText().toString();
        final String passStr = mPasswordView.getText().toString();
        Optional<Account> optionalAccount = OrangeBlastersApplication.getInstance().getAccountService()
                .tryLogin(userStr, passStr);
        if (optionalAccount.isPresent()) {
            Account account = optionalAccount.get();
            if (account instanceof LocationEmployee) {
                Intent intent = new Intent(this, LocEmployDashActivity.class);
                String locationId = ((LocationEmployee) account).getLocation();
                intent.putExtra(LocEmployDashActivity.PARAM_LOCATION_ID, locationId);
                startActivity(intent);
            } else {
                startActivity(new Intent(this, LocationListActivity.class));
            }
        } else {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.myCoordinatorLayout), R.string.invalid_user_pass, Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        }
    }
}

