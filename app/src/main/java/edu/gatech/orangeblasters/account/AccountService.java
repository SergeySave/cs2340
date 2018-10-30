package edu.gatech.orangeblasters.account;

public interface AccountService {

    void tryLogin(String email, String password, AccountCallback<Account> callback);

    void createUser(String name, String email, String password, AccountCallback<? super User> callback);
    void createAdmin(String name, String email, String password, AccountCallback<? super Admin> callback);
    void createManager(String name, String email, String password, AccountCallback<? super Manager> callback);
    void createLocationEmployee(String name, String email, String password, String locationId, AccountCallback<? super LocationEmployee> callback);
}
