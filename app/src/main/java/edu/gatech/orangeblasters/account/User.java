package edu.gatech.orangeblasters.account;

public class User extends Account {
    public User(String id, String name, String email, String password, AccountState accountState) {
        super(id, name, email, password, accountState);
    }

    @Override
    public AccountType getType() {
        return AccountType.USER;
    }
}
