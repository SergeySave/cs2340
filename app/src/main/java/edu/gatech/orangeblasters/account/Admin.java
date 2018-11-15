package edu.gatech.orangeblasters.account;

/**
 * Represents an Admin's account
 */
public class Admin extends Account {
    /**
     * Create a new Admin account
     *
     * @param id the account id
     * @param name the account name
     * @param email the account email
     * @param password the account password
     * @param accountState the account state
     */
    public Admin(String id, String name, String email, String password, AccountState accountState) {
        super(id, name, email, password, accountState);
    }

    @Override
    public AccountType getType() {
        return AccountType.ADMIN;
    }
}
