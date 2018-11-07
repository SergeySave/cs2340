package edu.gatech.orangeblasters.account;

import java.io.Serializable;

public abstract class Account implements Serializable {

    private final String id;

    private String name;
    private String password;
    private String email;
    private AccountState accountState;

    public Account(String id, String name, String email, String password, AccountState accountState) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
        this.accountState = accountState;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

// --Commented out by Inspection START (11/7/18, 2:37 PM):
//    public void setName(String name) {
//        this.name = name;
//    }
// --Commented out by Inspection STOP (11/7/18, 2:37 PM)

    public String getPassword() {
        return password;
    }

// --Commented out by Inspection START (11/7/18, 2:37 PM):
//    public void setPassword(String password) {
//        this.password = password;
//    }
// --Commented out by Inspection STOP (11/7/18, 2:37 PM)

    public String getEmail() {
        return email;
    }

// --Commented out by Inspection START (11/7/18, 2:37 PM):
//    public void setEmail(String email) {
//        this.email = email;
//    }
// --Commented out by Inspection STOP (11/7/18, 2:37 PM)

    public AccountState getAccountState() {
        return accountState;
    }

// --Commented out by Inspection START (11/7/18, 2:37 PM):
//    public void setAccountState(AccountState accountState) {
//        this.accountState = accountState;
//    }
// --Commented out by Inspection STOP (11/7/18, 2:37 PM)
}
