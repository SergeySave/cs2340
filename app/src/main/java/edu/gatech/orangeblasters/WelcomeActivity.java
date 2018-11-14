package edu.gatech.orangeblasters;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;

import edu.gatech.orangeblasters.account.Account;

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

        Button mEmailSignInButton = findViewById(R.id.sign_in);
        mEmailSignInButton.setOnClickListener(view -> attemptLogin());

        //Set up the Register form
        Button register = findViewById(R.id.register);
        register.setOnClickListener(v -> startActivity(new Intent(WelcomeActivity.this,
                RegisterActivity.class)));
    }

    /**
     * Checks to see if the user can log in by validating credentials
     */
    private void attemptLogin() {
        final String userStr = mUserNameView.getText().toString();
        final String passStr = mPasswordView.getText().toString();

        //Try logging in
        OrangeBlastersApplication.getInstance().getAccountService()
                .tryLogin(userStr, passStr, (optionalAccount -> {
                    //When the login attempt is processed
                    if (optionalAccount.isPresent()) {
                        Account account = optionalAccount.get();
                        Intent intent = new Intent(this, MapsActivity.class);
                        intent.putExtra(OrangeBlastersApplication.PARAM_USER_ID, account.getId());
                        startActivity(intent);
                    } else {
                        Snackbar my_Snack = Snackbar.make(findViewById(R.id.myCoordinatorLayout),
                                R.string.invalid_user_pass, Snackbar.LENGTH_SHORT);
                        my_Snack.show();
                    }
                }));
    }
}

