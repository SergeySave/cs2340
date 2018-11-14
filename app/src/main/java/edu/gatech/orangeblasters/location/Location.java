package edu.gatech.orangeblasters.location;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import edu.gatech.orangeblasters.donation.Donation;

public class Location {

    private final String id;

    private final String name;
    private final LocationType type;
    private final double longitude;
    private final double latitude;
    private final String address;
    private final String phoneNumber;
    private final List<Donation> donations = new ArrayList<>();
    private String website;

    public Location(String id, String name, LocationType type, double longitude, double latitude,
                    String address, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.longitude = longitude;
        this.latitude = latitude;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

// --Commented out by Inspection START (11/7/18, 2:37 PM):
//    public void setName(String name) {
//        this.name = name;
//    }
// --Commented out by Inspection STOP (11/7/18, 2:37 PM)

    public LocationType getType() {
        return type;
    }

// --Commented out by Inspection START (11/7/18, 2:37 PM):
//    public void setType(LocationType type) {
//        this.type = type;
//    }
// --Commented out by Inspection STOP (11/7/18, 2:37 PM)

    public double getLongitude() {
        return longitude;
    }

// --Commented out by Inspection START (11/7/18, 2:37 PM):
//    public void setLongitude(double longitude) {
//        this.longitude = longitude;
//    }
// --Commented out by Inspection STOP (11/7/18, 2:37 PM)

    public double getLatitude() {
        return latitude;
    }

// --Commented out by Inspection START (11/7/18, 2:37 PM):
//    public void setLatitude(double latitude) {
//        this.latitude = latitude;
//    }
// --Commented out by Inspection STOP (11/7/18, 2:37 PM)

    public String getAddress() {
        return address;
    }

// --Commented out by Inspection START (11/7/18, 2:37 PM):
//    public void setAddress(String address) {
//        this.address = address;
//    }
// --Commented out by Inspection STOP (11/7/18, 2:37 PM)

    public String getPhoneNumber() {
        return phoneNumber;
    }

// --Commented out by Inspection START (11/7/18, 2:37 PM):
//    public void setPhoneNumber(String phoneNumber) {
//        this.phoneNumber = phoneNumber;
//    }
// --Commented out by Inspection STOP (11/7/18, 2:37 PM)

    public List<Donation> getDonations() {
        return donations;
    }

    // --Commented out by Inspection (11/7/18, 2:37 PM):public String getWebsite(){return website;}

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Location location = (Location) o;
        return Double.compare(location.longitude, longitude) == 0 &&
                Double.compare(location.latitude, latitude) == 0 &&
                Objects.equals(name, location.name) &&
                type == location.type &&
                Objects.equals(address, location.address) &&
                Objects.equals(phoneNumber, location.phoneNumber);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, type, longitude, latitude, address, phoneNumber);
    }

    @Override
    public String toString() {
        return getName();
    }
}
