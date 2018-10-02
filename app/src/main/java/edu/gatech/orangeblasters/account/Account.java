package edu.gatech.orangeblasters.account;

import java.io.Serializable;

public abstract class Account implements Serializable {
    private String name;
    private String password;
    private String email;
    private AccountState accountState;

    public Account(String name, String email, String password, AccountState accountState) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.accountState = accountState;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public AccountState getAccountState() {
        return accountState;
    }

    public void setAccountState(AccountState accountState) {
        this.accountState = accountState;
    }
}
