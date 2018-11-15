package edu.gatech.orangeblasters.account;

/**
 * Represents a Manager's Account
 */
public class Manager extends Account {
    /**
     * Create a new Manager
     *
     * @param id the accounts id
     * @param name the accounts name
     * @param email the accounts email
     * @param password the accounts password
     * @param accountState the accounts state
     */
    public Manager(String id, String name, String email, String password,
                   AccountState accountState) {
        super(id, name, email, password, accountState);
    }

    @Override
    public AccountType getType() {
        return AccountType.MANAGER;
    }
}
