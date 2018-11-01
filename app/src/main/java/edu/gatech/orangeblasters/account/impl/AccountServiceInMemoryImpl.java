package edu.gatech.orangeblasters.account.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import edu.gatech.orangeblasters.account.Account;
import edu.gatech.orangeblasters.account.AccountCallback;
import edu.gatech.orangeblasters.account.AccountService;
import edu.gatech.orangeblasters.account.AccountState;
import edu.gatech.orangeblasters.account.Admin;
import edu.gatech.orangeblasters.account.LocationEmployee;
import edu.gatech.orangeblasters.account.Manager;
import edu.gatech.orangeblasters.account.User;

@Deprecated
public class AccountServiceInMemoryImpl implements AccountService {

    private Map<String, Account> accounts = new HashMap<>();
    private Random random = new Random();

    private String createId() {
        return random.ints(4).mapToObj(Integer::toHexString).collect(Collectors.joining());
    }

    @Override
    public void tryLogin(String email, String password, AccountCallback<Account> callback) {
        callback.onComplete(accounts.values().stream().filter(acc -> acc.getEmail().equals(email) && acc.getPassword().equals(password)).findAny());
    }

    @Override
    public void getAccount(String id, AccountCallback<Account> callback) {
        callback.onComplete(Optional.ofNullable(accounts.get(id)));
    }

    @Override
    public void createUser(String name, String email, String password, AccountCallback<? super User> callback) {
        User user = new User(createId(), name, email, password, AccountState.NORMAL);
        accounts.put(user.getId(), user);
        callback.onComplete(Optional.of(user));
    }

    @Override
    public void createAdmin(String name, String email, String password, AccountCallback<? super Admin> callback) {
        Admin admin = new Admin(createId(), name, email, password, AccountState.NORMAL);
        accounts.put(admin.getId(), admin);
        callback.onComplete(Optional.of(admin));
    }

    @Override
    public void createManager(String name, String email, String password, AccountCallback<? super Manager> callback) {
        Manager manager = new Manager(createId(), name, email, password, AccountState.NORMAL);
        accounts.put(manager.getId(), manager);
        callback.onComplete(Optional.of(manager));
    }

    @Override
    public void createLocationEmployee(String name, String email, String password, String locationId, AccountCallback<? super LocationEmployee> callback) {
        LocationEmployee employee = new LocationEmployee(createId(), name, email, password, AccountState.NORMAL, locationId);
        accounts.put(employee.getId(), employee);
        callback.onComplete(Optional.of(employee));
    }
}
