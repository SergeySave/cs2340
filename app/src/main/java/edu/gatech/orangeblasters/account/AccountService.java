package edu.gatech.orangeblasters.account;

public interface AccountService {

    void tryLogin(String email, String password, AccountCallback<Account> callback);
    void getAccount(String id, AccountCallback<Account> callback);

    void createUser(String name, String email, String password,
                    AccountCallback<? super User> callback);
    void createAdmin(String name, String email, String password,
                     AccountCallback<? super Admin> callback);
    void createManager(String name, String email, String password,
                       AccountCallback<? super Manager> callback);
    void createLocationEmployee(String name, String email, String password, String locationId,
                                AccountCallback<? super LocationEmployee> callback);

    default void createAccount(AccountType selectedItem, String name, String email,
                               String password, String locationId,
                               AccountCallback<Account> accountCreationCallback) {
        switch (selectedItem) {
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
