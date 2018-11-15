package edu.gatech.orangeblasters.location.impl;

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

    private final transient OrangeBlastersApplication orangeBlastersApplication = OrangeBlastersApplication.getInstance();
    private final transient DonationService donationService = orangeBlastersApplication.getDonationService();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public List<String> getDonations() {
        return donations;
    }

    public void setDonations(List<String> donations) {
        this.donations = donations;
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
