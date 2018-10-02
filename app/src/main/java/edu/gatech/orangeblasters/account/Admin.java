package edu.gatech.orangeblasters.account;

import edu.gatech.orangeblasters.Account;

public class Admin extends Account {
    public Admin(String name, String email, String password, AccountState accountState) {
        super(name, email, password, accountState);
    }
}
