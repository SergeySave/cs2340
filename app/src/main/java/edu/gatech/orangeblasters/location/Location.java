package edu.gatech.orangeblasters.location;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import edu.gatech.orangeblasters.donation.Donation;

public class Location {

    private final String id;

    private String name;
    private LocationType type;
    private double longitude;
    private double latitude;
    private String address;
    private String phoneNumber;
    private final List<Donation> donations = new ArrayList<>();
    private String website;

    public Location(String id, String name, LocationType type, double longitude, double latitude, String address, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.longitude = longitude;
        this.latitude = latitude;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.website = website;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocationType getType() {
        return type;
    }

    public void setType(LocationType type) {
        this.type = type;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<Donation> getDonations() {
        return donations;
    }

    public String getWebsite(){return website;}

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
