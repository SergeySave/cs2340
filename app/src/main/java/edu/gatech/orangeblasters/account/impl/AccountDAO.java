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
/**
 * Represents an account in the firebase
 */
@SuppressWarnings("unused")
public class AccountDAO implements Serializable {

    private String id;
    private AccountType accountType;
    private String name;
    private String password;
    private String email;
    private AccountState accountState;
    private String locationId;

    /**
     * Get the id
     *
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Set the id
     *
     * @param id the id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Get the account type
     *
     * @return the account type
     */
    public AccountType getAccountType() {
        return accountType;
    }

    /**
     * Set the account type
     *
     * @param accountType the account type
     */
    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    /**
     * Get the name
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the password
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set the password
     *
     * @param password the password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Get the email
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Set the email
     *
     * @param email the email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Get the account state
     *
     * @return the account state
     */
    public AccountState getAccountState() {
        return accountState;
    }

    /**
     * Set the account state
     *
     * @param accountState the account state
     */
    public void setAccountState(AccountState accountState) {
        this.accountState = accountState;
    }

    /**
     * Get the location id
     *
     * @return the location id
     */
    public String getLocationId() {
        return locationId;
    }

    /**
     * Set the location id
     *
     * @param locationId the location id
     */
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
