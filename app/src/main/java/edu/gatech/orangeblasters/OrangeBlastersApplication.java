package edu.gatech.orangeblasters;

import android.app.Application;

import edu.gatech.orangeblasters.account.AccountService;
import edu.gatech.orangeblasters.account.impl.AccountServiceInMemoryImpl;
import edu.gatech.orangeblasters.bitmap.BitmapService;
import edu.gatech.orangeblasters.bitmap.impl.BitmapInMemoryService;
import edu.gatech.orangeblasters.location.LocationService;
import edu.gatech.orangeblasters.location.impl.LocationServiceInMemoryImpl;

public class OrangeBlastersApplication extends Application {
    private static OrangeBlastersApplication instance;

    private AccountService accountService;
    private LocationService locationService;
    private BitmapService bitmapService;

    public AccountService getAccountService() {
        return accountService;
    }

    public LocationService getLocationService() {
        return locationService;
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

        accountService = new AccountServiceInMemoryImpl();
        locationService = new LocationServiceInMemoryImpl(getResources().openRawResource(R.raw.location_data));
        bitmapService = new BitmapInMemoryService();

        //Add default user
        accountService.createUser("User", "user@user.com", "pass", __->{});
    }
}
