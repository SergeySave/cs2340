package edu.gatech.orangeblasters.account;

public class LocationEmployee extends Account {
    private final String locationId;

    public LocationEmployee(String id, String name, String email, String password,
                            AccountState accountState, String locationId) {
        super(id, name, email, password, accountState);
        this.locationId = locationId;
    }

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
