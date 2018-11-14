package edu.gatech.orangeblasters.account.impl;

import java.io.Serializable;

import edu.gatech.orangeblasters.account.Account;
import edu.gatech.orangeblasters.account.AccountState;
import edu.gatech.orangeblasters.account.AccountType;
import edu.gatech.orangeblasters.account.Admin;
import edu.gatech.orangeblasters.account.LocationEmployee;
import edu.gatech.orangeblasters.account.Manager;
import edu.gatech.orangeblasters.account.User;

//All getter and setters used by firebase
@SuppressWarnings("unused")
public class AccountDAO implements Serializable {

    private String id;
    private AccountType accountType;
    private String name;
    private String password;
    private String email;
    private AccountState accountState;
    private String locationId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
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

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    /**
     * Creates a user with the given information
     *
     * @param user the user to be created
     * @return the user in AccountDAO format
     */
    public static AccountDAO fromUser(User user) {
        AccountDAO acc = new AccountDAO();
        acc.init(user);
        acc.accountType = AccountType.USER;
        return acc;
    }

    /**
     * Creates a admin with the given information
     *
     * @param admin the user to be created
     * @return the user in AccountDAO format
     */
    public static AccountDAO fromAdmin(Admin admin) {
        AccountDAO acc = new AccountDAO();
        acc.init(admin);
        acc.accountType = AccountType.ADMIN;
        return acc;
    }

    /**
     * Creates a manager with the given information
     *
     * @param manager the user to be created
     * @return the user in AccountDAO format
     */
    public static AccountDAO fromManager(Manager manager) {
        AccountDAO acc = new AccountDAO();
        acc.init(manager);
        acc.accountType = AccountType.MANAGER;
        return acc;
    }

    /**
     * Creates a location employee with the given information
     *
     * @param locationEmployee the user to be created
     * @return the user in AccountDAO format
     */
    public static AccountDAO fromLocationEmployee(LocationEmployee locationEmployee) {
        AccountDAO acc = new AccountDAO();
        acc.init(locationEmployee);
        acc.accountType = AccountType.EMPLOYEE;
        acc.locationId = locationEmployee.getLocation();
        return acc;
    }

    /**
     * Initialized the account
     *
     * @param account the account to be initialized
     */
    private void init(Account account) {
        this.id = account.getId();
        this.name = account.getName();
        this.password = account.getPassword();
        this.email = account.getEmail();
        this.accountState = account.getAccountState();
    }

    /**
     * Makes it in the account format
     *
     * @return the Account of in correct format
     */
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
