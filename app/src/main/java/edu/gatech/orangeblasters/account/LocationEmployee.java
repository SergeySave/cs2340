package edu.gatech.orangeblasters.account;

/**
 * Represents a Location Employee's account
 */
public class LocationEmployee extends Account {
    private final String locationId;

    /**
     * Create a new LocationEmployee
     *
     * @param id the accounts id
     * @param name the accounts name
     * @param email the accounts email
     * @param password the accounts password
     * @param accountState the accounts state
     * @param locationId the id of the location that the employee works at
     */
    public LocationEmployee(String id, String name, String email, String password,
                            AccountState accountState, String locationId) {
        super(id, name, email, password, accountState);
        this.locationId = locationId;
    }

    /**
     * Get the id of the location
     *
     * @return the id of the location
     */
    public String getLocation(){
        return locationId;
    }

// --Commented out by Inspection START (11/7/18, 2:37 PM):
//    public void setLocation(String locationId){
//        this.locationId = locationId;
//    }
// --Commented out by Inspection STOP (11/7/18, 2:37 PM)


    @Override
    public AccountType getType() {
        return AccountType.EMPLOYEE;
    }
}
