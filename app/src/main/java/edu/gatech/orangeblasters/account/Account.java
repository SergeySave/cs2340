package edu.gatech.orangeblasters.account;

import java.io.Serializable;

/**
 * Represents an account
 */
public abstract class Account implements Serializable {

    private final String id;

    private final String name;
    private final String password;
    private final String email;
    private final AccountState accountState;

    /**
     * Create a new account
     *
     * @param id the account id
     * @param name the account name
     * @param email the account email
     * @param password the account password
     * @param accountState the account state
     */
    Account(String id, String name, String email, String password, AccountState accountState) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
        this.accountState = accountState;
    }

    /**
     * Gets the ID
     *
     * @return an ID
     */
    public String getId() {
        return id;
    }

    /**
     * Get the name
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

// --Commented out by Inspection START (11/7/18, 2:37 PM):
//    public void setName(String name) {
//        this.name = name;
//    }
// --Commented out by Inspection STOP (11/7/18, 2:37 PM)

    /**
     * Gets the Password
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

// --Commented out by Inspection START (11/7/18, 2:37 PM):
//    public void setPassword(String password) {
//        this.password = password;
//    }
// --Commented out by Inspection STOP (11/7/18, 2:37 PM)

    /**
     * Gets the email
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

// --Commented out by Inspection START (11/7/18, 2:37 PM):
//    public void setEmail(String email) {
//        this.email = email;
//    }
// --Commented out by Inspection STOP (11/7/18, 2:37 PM)

    /**
     * Gets the account state
     *
     * @return the account state
     */
    public AccountState getAccountState() {
        return accountState;
    }

// --Commented out by Inspection START (11/7/18, 2:37 PM):
//    public void setAccountState(AccountState accountState) {
//        this.accountState = accountState;
//    }
// --Commented out by Inspection STOP (11/7/18, 2:37 PM)

    /**
     * Get the account type
     *
     * @return the account type
     */
    public abstract AccountType getType();
}
