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

/**
 * The main application - source of services
 */
public class OrangeBlastersApplication extends Application {

    public static final String PARAM_USER_ID = "CURRENT_USER_ID";

    private static OrangeBlastersApplication instance;

    private AccountService accountService;
    private LocationService locationService;
    private DonationService donationService;
    private BitmapService bitmapService;

    /**
     * Get the current account service
     *
     * @return the account service
     */
    public AccountService getAccountService() {
        return accountService;
    }

    /**
     * Get the current locations service
     *
     * @return the location service
     */
    public LocationService getLocationService() {
        return locationService;
    }

    /**
     * Get the current donation service
     *
     * @return the donation service
     */
    public DonationService getDonationService() {
        return donationService;
    }

    /**
     * Get the current bitmap service
     *
     * @return the bitmap service
     */
    public BitmapService getBitmapService() {
        return bitmapService;
    }

    /**
     * Get the current application instance
     *
     * @return the application instance
     */
    public static OrangeBlastersApplication getInstance() {
        return instance;
    }

    private static void setInstance(OrangeBlastersApplication instance) {
        OrangeBlastersApplication.instance = instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        setInstance(this);
        FirebaseApp.initializeApp(this);

        accountService = new AccountServiceFirebaseImpl();
        locationService = new LocationServiceFirebaseImpl();
        donationService = new DonationServiceFirebaseImpl();
        bitmapService = new BitmapFirebaseService();
    }
}
