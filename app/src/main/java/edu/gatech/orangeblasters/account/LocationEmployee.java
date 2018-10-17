package edu.gatech.orangeblasters.account;

import edu.gatech.orangeblasters.location.Location;

public class LocationEmployee extends Account {
    private Location location;

    public LocationEmployee(String name, String email, String password, AccountState accountState, Location location) {
        super(name, email, password, accountState);
        this.location = location;
    }

    public Location getLocation(){
        return location;
    }

    public void setLocation(Location location){
        this.location = location;
    }
}
