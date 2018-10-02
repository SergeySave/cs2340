package edu.gatech.orangeblasters;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;

/**
 * A login screen that offers login via username/password.
 */
public class RegisterActivity extends AppCompatActivity {

    public static final String ARG_ACCOUNT_LIST = "ACCOUNT_LIST";

    // UI references.
    private EditText mUsernameView;
    private EditText mPasswordView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Set up the login form.
        mUsernameView = findViewById(R.id.username);

        mPasswordView = findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener((textView, id, keyEvent) -> {
            if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                attemptCreateAccount();
                return true;
            }
            return false;
        });

        Button mEmailSignInButton = findViewById(R.id.register);
        mEmailSignInButton.setOnClickListener(view -> attemptCreateAccount());

        Button mCancelButton = findViewById(R.id.cancel);
        mCancelButton.setOnClickListener(view -> finish());
    }

    private void attemptCreateAccount() {
        Snackbar.make(findViewById(R.id.myCoordinatorLayout), R.string.unsupported_operation, Snackbar.LENGTH_SHORT).show();
    }
}

