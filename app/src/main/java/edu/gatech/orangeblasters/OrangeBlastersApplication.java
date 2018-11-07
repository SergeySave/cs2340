package edu.gatech.orangeblasters;

import android.app.Application;

import com.google.firebase.FirebaseApp;

import edu.gatech.orangeblasters.account.AccountService;
import edu.gatech.orangeblasters.account.impl.AccountServiceFirebaseImpl;
import edu.gatech.orangeblasters.bitmap.BitmapService;
import edu.gatech.orangeblasters.bitmap.impl.BitmapFirebaseService;
import edu.gatech.orangeblasters.donation.DonationService;
import edu.gatech.orangeblasters.donation.impl.DonationServiceFirebaseImpl;
import edu.gatech.orangeblasters.location.LocationService;
import edu.gatech.orangeblasters.location.impl.LocationServiceFirebaseImpl;

public class OrangeBlastersApplication extends Application {

    public static final String PARAM_USER_ID = "CURRENT_USER_ID";

    private static OrangeBlastersApplication instance;

    private AccountService accountService;
    private LocationService locationService;
    private DonationService donationService;
    private BitmapService bitmapService;

    public AccountService getAccountService() {
        return accountService;
    }

    public LocationService getLocationService() {
        return locationService;
    }

    public DonationService getDonationService() {
        return donationService;
    }

    public BitmapService getBitmapService() {
        return bitmapService;
    }

    public static OrangeBlastersApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        FirebaseApp.initializeApp(this);

        accountService = new AccountServiceFirebaseImpl();
        locationService = new LocationServiceFirebaseImpl();
        donationService = new DonationServiceFirebaseImpl();
        bitmapService = new BitmapFirebaseService();
    }
}
