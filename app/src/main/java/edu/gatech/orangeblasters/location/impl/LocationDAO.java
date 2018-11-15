package edu.gatech.orangeblasters.location.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import edu.gatech.orangeblasters.OrangeBlastersApplication;
import edu.gatech.orangeblasters.donation.Donation;
import edu.gatech.orangeblasters.donation.DonationService;
import edu.gatech.orangeblasters.location.Location;
import edu.gatech.orangeblasters.location.LocationType;

//All getter and setter methods used by firebase
/**
 * Represents a location in firebase
 */
@SuppressWarnings("unused")
public class LocationDAO {
    private String id;

    private String name;
    private LocationType type;
    private double longitude;
    private double latitude;
    private String address;
    private String phoneNumber;
    private List<String> donations;

    private final transient OrangeBlastersApplication orangeBlastersApplication =
            OrangeBlastersApplication.getInstance();
    private final transient DonationService donationService =
            orangeBlastersApplication.getDonationService();

    /**
     * Get the id
     *
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Set the id
     *
     * @param id the id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Get the name
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the type
     *
     * @return the type
     */
    public LocationType getType() {
        return type;
    }

    /**
     * Set the type
     *
     * @param type the type
     */
    public void setType(LocationType type) {
        this.type = type;
    }

    /**
     * Get the longitude
     *
     * @return the longitude
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * Set the longitude
     *
     * @param longitude the longitude
     */
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    /**
     * Get the latitude
     *
     * @return the latitude
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * Set the latitude
     *
     * @param latitude the latitude
     */
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    /**
     * Get the address
     *
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Set the address
     *
     * @param address the address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Get the phone number
     *
     * @return the phone number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Set the phone number
     *
     * @param phoneNumber the phone number
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Get the donations
     *
     * @return the donations
     */
    public List<String> getDonations() {
        return Collections.unmodifiableList(donations);
    }

    /**
     * Set the donations
     *
     * @param donations the donation
     */
    public void setDonations(Collection<String> donations) {
        this.donations.clear();
        this.donations.addAll(donations);
    }

    /**
     * Gets the location's details in DAO format
     *
     * @param location the location to get in the format
     * @return the Location in DAO format
     */
    public static LocationDAO fromLocation(Location location) {
        LocationDAO locationDAO = new LocationDAO();
        locationDAO.id = location.getId();
        locationDAO.name = location.getName();
        locationDAO.type = location.getType();
        locationDAO.longitude = location.getLongitude();
        locationDAO.latitude = location.getLatitude();
        locationDAO.address = location.getAddress();
        locationDAO.phoneNumber = location.getPhoneNumber();
        List<Donation> donations = location.getDonations();
        Stream<Donation> stream = donations.stream();
        Stream<String> stringStream = stream.map(Donation::getId);
        locationDAO.donations = stringStream.collect(Collectors.toList());
        return locationDAO;
    }

    /**
     * Puts the location in
     *
     * @return the location with all details
     */
    public Location toLocation() {
        Location location = new Location(id, name, type, longitude, latitude, address, phoneNumber);
        //location.getDonations()
        if (donations != null) {
            Stream<String> stream = donations.stream();
            Stream<Optional<Donation>> optionalStream = stream.map(donationService::getDonation);
            Stream<Donation> donationStream = optionalStream
                    .map(op -> op.orElse(null));
            Stream<Donation> donationStream1 = donationStream
                    .filter(Objects::nonNull);
            donationStream1.forEach(don -> {
                        List<Donation> donations = location.getDonations();
                        donations.add(don);
                    });
        }
        return location;
    }
}
