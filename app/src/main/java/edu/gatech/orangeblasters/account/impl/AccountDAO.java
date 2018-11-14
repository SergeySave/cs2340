package edu.gatech.orangeblasters.account.impl;

import java.io.Serializable;

import edu.gatech.orangeblasters.account.Account;
import edu.gatech.orangeblasters.account.AccountState;
import edu.gatech.orangeblasters.account.AccountType;
import edu.gatech.orangeblasters.account.Admin;
import edu.gatech.orangeblasters.account.LocationEmployee;
import edu.gatech.orangeblasters.account.Manager;
import edu.gatech.orangeblasters.account.User;

public class AccountDAO implements Serializable {
    private String id;

    private AccountType accountType;
    private String name;
    public String password;
    private String email;
    private AccountState accountState;
    private String locationId;

    public static AccountDAO fromUser(User user) {
        AccountDAO acc = new AccountDAO();
        acc.init(user);
        acc.accountType = AccountType.USER;
        return acc;
    }

    public static AccountDAO fromAdmin(Admin admin) {
        AccountDAO acc = new AccountDAO();
        acc.init(admin);
        acc.accountType = AccountType.ADMIN;
        return acc;
    }

    public static AccountDAO fromManager(Manager manager) {
        AccountDAO acc = new AccountDAO();
        acc.init(manager);
        acc.accountType = AccountType.MANAGER;
        return acc;
    }

    public static AccountDAO fromLocationEmployee(LocationEmployee locationEmployee) {
        AccountDAO acc = new AccountDAO();
        acc.init(locationEmployee);
        acc.accountType = AccountType.EMPLOYEE;
        acc.locationId = locationEmployee.getLocation();
        return acc;
    }

    private void init(Account account) {
        this.id = account.getId();
        this.name = account.getName();
        this.password = account.getPassword();
        this.email = account.getEmail();
        this.accountState = account.getAccountState();
    }

    public Account toAccount() {
        Account account = null;
        switch (accountType) {
            case USER:
                account = new User(id, name, email, password, accountState);
                break;
            case ADMIN:
                account = new Admin(id, name, email, password, accountState);
                break;
            case MANAGER:
                account = new Manager(id, name, email, password, accountState);
                break;
            case EMPLOYEE:
                account = new LocationEmployee(id, name, email, password, accountState, locationId);
                break;
        }
        return account;
    }
}
