package edu.gatech.orangeblasters.account;

public enum AccountType {
    ADMIN("Admin"),
    MANAGER("Manager"),
    EMPLOYEE("Location Employee"),
    USER("User"),;

    /**
     * 2 letter abbreviation of class standing
     */
    private final String userType;

    /**
     * Constructor for the enumeration
     *
     * @param userType the user type
     */
    AccountType(String userType) {
        this.userType = userType;
    }

// --Commented out by Inspection START (11/7/18, 2:36 PM):
//    /**
//     * @return the user type
//     */
//    public String getUserType() {
//        return userType;
//    }
// --Commented out by Inspection STOP (11/7/18, 2:36 PM)
// --Commented out by Inspection START (11/7/18, 2:36 PM):
//    /**
//     * @return the user type
//     */
//    public String setUserType(String userType) {
//        return this.userType = userType;
//    }
// --Commented out by Inspection STOP (11/7/18, 2:36 PM)

    @Override
    public String toString() {
        return userType;
    }
}
