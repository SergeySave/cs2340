package edu.gatech.orangeblasters.location;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

import edu.gatech.orangeblasters.LiveList;
import edu.gatech.orangeblasters.donation.Donation;

public class Location implements Serializable {
    private String name;
    private LocationType type;
    private double longitude;
    private double latitude;
    private String address;
    private String phoneNumber;
    private transient LiveList<Donation> donations = new LiveList<>(new ArrayList<>());

    public Location(String name, LocationType type, double longitude, double latitude, String address, String phoneNumber) {
        this.name = name;
        this.type = type;
        this.longitude = longitude;
        this.latitude = latitude;
        this.address = address;
        this.phoneNumber = phoneNumber;
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

    public LiveList<Donation> getDonations() {
        return donations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
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
        return "Location{" +
                "name='" + name + '\'' +
                ", type=" + type +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
