package edu.gatech.orangeblasters.account;

import edu.gatech.orangeblasters.Account;

public class User extends Account {
    public User(String name, String email, String password, AccountState accountState) {
        super(name, email, password, accountState);
    }
}
