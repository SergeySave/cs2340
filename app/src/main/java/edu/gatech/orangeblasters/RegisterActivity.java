package edu.gatech.orangeblasters;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import edu.gatech.orangeblasters.account.AccountTypes;

/**
 * A login screen that offers login via username/password.
 */
public class RegisterActivity extends AppCompatActivity {

    public static final String ARG_ACCOUNT_LIST = "ACCOUNT_LIST";

    // UI references.
    private EditText mUsernameView;
    private EditText mPasswordView;
    private Spinner userSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Set up the login form.
        mUsernameView = findViewById(R.id.username);
        AccountTypes[] accounts = new AccountTypes[] {
        };
        //set up the spinner
        userSpinner = (Spinner) findViewById(R.id.userType);
        ArrayAdapter<String> adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, AccountTypes.values());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userSpinner.setAdapter(adapter);

        mPasswordView = findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptCreateAccount();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = findViewById(R.id.register);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptCreateAccount();
            }
        });

        Button mCancelButton = findViewById(R.id.cancel);
        mCancelButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void attemptCreateAccount() {
        Snackbar.make(findViewById(R.id.myCoordinatorLayout), R.string.unsupported_operation, Snackbar.LENGTH_SHORT).show();
    }
}

