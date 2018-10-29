package edu.gatech.orangeblasters;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.List;
import java.util.stream.Collectors;

import edu.gatech.orangeblasters.account.AccountState;
import edu.gatech.orangeblasters.account.AccountTypes;
import edu.gatech.orangeblasters.account.Admin;
import edu.gatech.orangeblasters.account.LocationEmployee;
import edu.gatech.orangeblasters.account.Manager;
import edu.gatech.orangeblasters.account.User;
import edu.gatech.orangeblasters.location.Location;

/**
 * A login screen that offers login via username/password.
 */
public class RegisterActivity extends AppCompatActivity  {

    // UI references.
    private EditText mUsernameView;
    private EditText mPasswordView;
    private Spinner userSpinner;
    private Spinner location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        location = findViewById(R.id.location);

        location.setVisibility(View.INVISIBLE);

        // Set up the login form.
        mUsernameView = findViewById(R.id.username);

        //set up the spinner
        userSpinner = findViewById(R.id.userType);
        ArrayAdapter<AccountTypes> adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, AccountTypes.values());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userSpinner.setAdapter(adapter);
        userSpinner.setSelection(adapter.getPosition(AccountTypes.USER)); //User is default

        userSpinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                        if (userSpinner.getSelectedItem() == AccountTypes.EMPLOYEE) {
                            location.setVisibility(View.VISIBLE);
                        } else {
                            location.setVisibility(View.INVISIBLE);
                        }

                    }
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });

        location = findViewById(R.id.location);

        List<Location> names = OrangeBlastersApplication.getInstance().getLocationService().getLocations().collect(Collectors.toList());

        ArrayAdapter<Location> adapter2 = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, names);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        location.setAdapter(adapter2);

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
                Location selectedItem = (Location) location.getSelectedItem();

                ((OrangeBlastersApplication) getApplication()).getAccounts()
                        .add(new LocationEmployee(name, email, password, AccountState.NORMAL, selectedItem));
                break;
        }
        finish();
    }
}

