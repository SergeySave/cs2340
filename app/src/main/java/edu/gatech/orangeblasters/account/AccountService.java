package edu.gatech.orangeblasters.account;

/**
 * Abstracts an account storage method
 */
public interface AccountService {

    /**
     * Try to login to an account
     *
     * @param email the account's email
     * @param password the account's password
     * @param callback a callback for the account, if it returns a value then it was a successful
     *                 login
     */
    void tryLogin(String email, String password, AccountCallback<Account> callback);

    /**
     * Get an account for a given id
     *
     * @param id the account id
     * @param callback a callback for the account
     */
    void getAccount(String id, AccountCallback<Account> callback);

    /**
     * Creates a new User account
     *
     * @param name the name
     * @param email the email
     * @param password the password
     * @param callback a callback for the created user
     */
    void createUser(String name, String email, String password,
                    AccountCallback<? super User> callback);

    /**
     * Creates a new Admin account
     *
     * @param name the name
     * @param email the email
     * @param password the password
     * @param callback a callback for the created admin
     */
    void createAdmin(String name, String email, String password,
                     AccountCallback<? super Admin> callback);

    /**
     * Creates a new Manager account
     *
     * @param name the name
     * @param email the email
     * @param password the password
     * @param callback a callback for the created manager
     */
    void createManager(String name, String email, String password,
                       AccountCallback<? super Manager> callback);

    /**
     * Creates a new Location Employee account
     *
     * @param name the name
     * @param email the email
     * @param password the password
     * @param locationId the id of the employee's location
     * @param callback a callback for the created location employee
     */
    void createLocationEmployee(String name, String email, String password, String locationId,
                                AccountCallback<? super LocationEmployee> callback);

    /**
     * Create an account of a given type
     *
     * @param type the account's type
     * @param name the account's name
     * @param email the account's email
     * @param password the account's password
     * @param locationId the location id (only used for location employees)
     * @param accountCreationCallback a callback for the created account
     */
    default void createAccount(AccountType type, String name, String email,
                               String password, String locationId,
                               AccountCallback<Account> accountCreationCallback) {
        switch (type) {
            case USER:
                createUser(name, email, password, accountCreationCallback);
                break;
            case ADMIN:
                createAdmin(name, email, password, accountCreationCallback);
                break;
            case MANAGER:
                createManager(name, email, password, accountCreationCallback);
                break;
            case EMPLOYEE:
                createLocationEmployee(name, email, password, locationId, accountCreationCallback);
                break;
        }
    }
}
