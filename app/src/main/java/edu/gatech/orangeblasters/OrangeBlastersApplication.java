package edu.gatech.orangeblasters;

import android.app.Application;
import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

import edu.gatech.orangeblasters.account.AccountService;
import edu.gatech.orangeblasters.account.impl.AccountServiceInMemoryImpl;
import edu.gatech.orangeblasters.location.LocationService;
import edu.gatech.orangeblasters.location.impl.LocationServiceInMemoryImpl;

public class OrangeBlastersApplication extends Application {
    private static OrangeBlastersApplication instance;

    private AccountService accountService;
    private List<Bitmap> bitmaps = new ArrayList<>();
    private LocationService locationService;

    public AccountService getAccountService() {
        return accountService;
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

        accountService = new AccountServiceInMemoryImpl();
        locationService = new LocationServiceInMemoryImpl(getResources().openRawResource(R.raw.location_data));

        //Add default user
        accountService.createUser("User", "user@user.com", "pass");
    }
}
