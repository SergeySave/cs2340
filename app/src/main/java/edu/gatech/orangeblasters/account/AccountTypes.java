package edu.gatech.orangeblasters.account;

public enum AccountTypes {
    ADMIN("Admin"),
    MANAGER("Manager"),
    EMPLOYEE("Location Employee"),
    USER("User"),;

    /**
     * 2 letter abbreviation of class standing
     */
    private String userType;

    /**
     * Constructor for the enumeration
     *
     * @param userType the user type
     */
    AccountTypes(String userType) {
        this.userType = userType;
    }

    /**
     * @return the user type
     */
    public String getUserType() {
        return userType;
    }
    /**
     * @return the user type
     */
    public String setUserType(String userType) {
        return this.userType = userType;
    }

    @Override
    public String toString() {
        return userType;
    }
}
