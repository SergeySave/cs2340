package edu.gatech.orangeblasters.account;

public class LocationEmployee extends Account {
    private String locationId;

    public LocationEmployee(String id, String name, String email, String password, AccountState accountState, String locationId) {
        super(id, name, email, password, accountState);
        this.locationId = locationId;
    }

    public String getLocation(){
        return locationId;
    }

    public void setLocation(String locationId){
        this.locationId = locationId;
    }
}
