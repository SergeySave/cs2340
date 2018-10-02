package edu.gatech.orangeblasters;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

public class OrangeBlastersApplication extends Application {
    private List<Account> accounts = new ArrayList<>();

    public List<Account> getAccounts() {
        return accounts;
    }
}
