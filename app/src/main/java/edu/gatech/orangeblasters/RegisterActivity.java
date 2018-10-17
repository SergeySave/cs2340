package edu.gatech.orangeblasters;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import edu.gatech.orangeblasters.account.AccountState;
import edu.gatech.orangeblasters.account.AccountTypes;
import edu.gatech.orangeblasters.account.Admin;
import edu.gatech.orangeblasters.account.LocationEmployee;
import edu.gatech.orangeblasters.account.Manager;
import edu.gatech.orangeblasters.account.User;

/**
 * A login screen that offers login via username/password.
 */
public class RegisterActivity extends AppCompatActivity {

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

        //set up the spinner
        userSpinner = findViewById(R.id.userType);
        ArrayAdapter<AccountTypes> adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, AccountTypes.values());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userSpinner.setAdapter(adapter);
        userSpinner.setSelection(adapter.getPosition(AccountTypes.USER)); //User is default

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
        String email = mUsernameView.getText().toString();
        String password = mPasswordView.getText().toString();
        String name = email;

        if (!email.matches("[^@]+@[^@]+\\.[^@.]+")) {
            Snackbar.make(findViewById(R.id.myCoordinatorLayout), R.string.error_invalid_email, Snackbar.LENGTH_SHORT).show();
            return;
        }

        if (password.length() < 4) {
            Snackbar.make(findViewById(R.id.myCoordinatorLayout), R.string.error_invalid_password, Snackbar.LENGTH_SHORT).show();
            return;
        }

        switch (((AccountTypes) userSpinner.getSelectedItem())) {
            case USER:
                ((OrangeBlastersApplication) getApplication()).getAccounts()
                        .add(new User(name, email, password, AccountState.NORMAL));
                break;
            case ADMIN:
                ((OrangeBlastersApplication) getApplication()).getAccounts()
                        .add(new Admin(name, email, password, AccountState.NORMAL));
                break;
            case MANAGER:
                ((OrangeBlastersApplication) getApplication()).getAccounts()
                        .add(new Manager(name, email, password, AccountState.NORMAL));
                break;
            case EMPLOYEE:
                ((OrangeBlastersApplication) getApplication()).getAccounts()
                        .add(new LocationEmployee(name, email, password, AccountState.NORMAL, null));
                break;
        }
        finish();
    }
}

