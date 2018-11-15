package edu.gatech.orangeblasters.account;

import java.util.Objects;


/**
 * Represents a User Account
 */
public class User extends Account {
    /**
     * Make a new user
     *
     * @param id the user's id
     * @param name the user's name
     * @param email the user's email
     * @param password the user's password
     * @param accountState the user's state
     *
     */
    public User(String id, String name, String email, String password, AccountState accountState) {
        super(id, name, email, password, accountState);
    }

    @Override
    public AccountType getType() {
        return AccountType.USER;
    }


}
