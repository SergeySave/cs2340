package edu.gatech.orangeblasters;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import edu.gatech.orangeblasters.account.Account;
import edu.gatech.orangeblasters.account.AccountCallback;
import edu.gatech.orangeblasters.account.AccountService;
import edu.gatech.orangeblasters.account.AccountType;
import edu.gatech.orangeblasters.location.Location;
import edu.gatech.orangeblasters.location.LocationService;

/**
 * A login screen that offers login via username/password.
 */
public class RegisterActivity extends AppCompatActivity  {

    // UI references.
    private EditText mUsernameView;
    private EditText mPasswordView;
    private Spinner userSpinner;
    private Spinner location;

    private final OrangeBlastersApplication orangeBlastersApplication = OrangeBlastersApplication.getInstance();
    private final LocationService locationService = orangeBlastersApplication.getLocationService();
    private final AccountService accountService = orangeBlastersApplication.getAccountService();

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
        ArrayAdapter<AccountType> adapter = new ArrayAdapter<>
                (this,android.R.layout.simple_spinner_item, AccountType.values());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userSpinner.setAdapter(adapter);
        userSpinner.setSelection(adapter.getPosition(AccountType.USER)); //User is default

        userSpinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                        if (userSpinner.getSelectedItem() == AccountType.EMPLOYEE) {
                            location.setVisibility(View.VISIBLE);
                        } else {
                            location.setVisibility(View.INVISIBLE);
                        }

                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });

        location = findViewById(R.id.location);

        Stream<Location> locations = locationService.getLocations();
        List<Location> names = locations.collect(Collectors.toList());

        ArrayAdapter<Location> adapter2 = new ArrayAdapter<>
                (this,android.R.layout.simple_spinner_item, names);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        location.setAdapter(adapter2);

        mPasswordView = findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener((textView, id, keyEvent) -> {
            if ((id == EditorInfo.IME_ACTION_DONE) || (id == EditorInfo.IME_NULL)) {
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

    /**
     * Creates an account with the given information
     */
    private void attemptCreateAccount() {
        Editable mUsernameViewText = mUsernameView.getText();
        String email = mUsernameViewText.toString();
        Editable mPasswordViewText = mPasswordView.getText();
        String password = mPasswordViewText.toString();

        if (!email.matches("[^@]+@[^@]+\\.[^@.]+")) {
            Snackbar snackbar = Snackbar.make(findViewById(R.id.myCoordinatorLayout), R.string.error_invalid_email,
                    Snackbar.LENGTH_SHORT);
            snackbar.show();
            return;
        }

        if (password.length() < 4) {
            Snackbar snackbar = Snackbar.make(findViewById(R.id.myCoordinatorLayout), R.string.error_invalid_password,
                    Snackbar.LENGTH_SHORT);
            snackbar.show();
            return;
        }

        Location selectedItem = (Location) location.getSelectedItem();
        String selectedId = null;
        if (selectedItem != null) {
            selectedId = selectedItem.getId();
        }
        //finish will be called if an account is created
        //if not nothing will happen (this maybe should be fixed)
        AccountCallback<Account> accountCreationCallback = result -> result.ifPresent(__->finish());
        accountService.createAccount(((AccountType) userSpinner.getSelectedItem()), email, email, password, selectedId, accountCreationCallback);
    }
}

