package edu.gatech.orangeblasters;

import android.app.Application;
import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

import edu.gatech.orangeblasters.account.Account;
import edu.gatech.orangeblasters.account.AccountState;
import edu.gatech.orangeblasters.account.User;
import edu.gatech.orangeblasters.location.LocationService;
import edu.gatech.orangeblasters.location.impl.LocationServiceInMemoryImpl;

public class OrangeBlastersApplication extends Application {
    private static OrangeBlastersApplication instance;

    private List<Account> accounts = new ArrayList<>();
    private List<Bitmap> bitmaps = new ArrayList<>();
    private LocationService locationService;

    public List<Account> getAccounts() {
        return accounts;
    }

    public LocationService getLocationService() {
        return locationService;
    }

    public List<Bitmap> getBitmaps() {
        return bitmaps;
    }

    public static OrangeBlastersApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        //Add default user
        accounts.add(new User("User", "user@user.com", "pass", AccountState.NORMAL));

        locationService = new LocationServiceInMemoryImpl(getResources().openRawResource(R.raw.location_data));
    }
}
