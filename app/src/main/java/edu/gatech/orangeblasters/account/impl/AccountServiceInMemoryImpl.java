package edu.gatech.orangeblasters.account.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import edu.gatech.orangeblasters.account.Account;
import edu.gatech.orangeblasters.account.AccountService;
import edu.gatech.orangeblasters.account.AccountState;
import edu.gatech.orangeblasters.account.Admin;
import edu.gatech.orangeblasters.account.LocationEmployee;
import edu.gatech.orangeblasters.account.Manager;
import edu.gatech.orangeblasters.account.User;

public class AccountServiceInMemoryImpl implements AccountService {

    private Map<String, Account> accounts = new HashMap<>();
    private Random random = new Random();

    private String createId() {
        return random.ints(4).mapToObj(Integer::toHexString).collect(Collectors.joining());
    }

    @Override
    public Optional<Account> tryLogin(String email, String password) {
        return accounts.values().stream().filter(acc -> acc.getEmail().equals(email) && acc.getPassword().equals(password)).findAny();
    }

    @Override
    public User createUser(String name, String email, String password) {
        User user = new User(createId(), name, email, password, AccountState.NORMAL);
        accounts.put(user.getId(), user);
        return user;
    }

    @Override
    public Admin createAdmin(String name, String email, String password) {
        Admin admin = new Admin(createId(), name, email, password, AccountState.NORMAL);
        accounts.put(admin.getId(), admin);
        return admin;
    }

    @Override
    public Manager createManager(String name, String email, String password) {
        Manager manager = new Manager(createId(), name, email, password, AccountState.NORMAL);
        accounts.put(manager.getId(), manager);
        return manager;
    }

    @Override
    public LocationEmployee createLocationEmployee(String name, String email, String password, String locationId) {
        LocationEmployee manager = new LocationEmployee(createId(), name, email, password, AccountState.NORMAL, locationId);
        accounts.put(manager.getId(), manager);
        return manager;
    }
}
