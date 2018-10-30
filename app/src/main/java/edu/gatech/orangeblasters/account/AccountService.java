package edu.gatech.orangeblasters.account;

import java.util.Optional;

public interface AccountService {

    Optional<Account> tryLogin(String email, String password);

    User createUser(String name, String email, String password);
    Admin createAdmin(String name, String email, String password);
    Manager createManager(String name, String email, String password);
    LocationEmployee createLocationEmployee(String name, String email, String password, String locationId);
}
