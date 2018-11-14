package edu.gatech.orangeblasters.location.impl;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import edu.gatech.orangeblasters.OrangeBlastersApplication;
import edu.gatech.orangeblasters.donation.Donation;
import edu.gatech.orangeblasters.location.Location;
import edu.gatech.orangeblasters.location.LocationType;

public class LocationDAO {
    public String id;

    private String name;
    private LocationType type;
    private double longitude;
    private double latitude;
    private String address;
    private String phoneNumber;
    private List<String> donations;

    public static LocationDAO fromLocation(Location location) {
        LocationDAO locationDAO = new LocationDAO();
        locationDAO.id = location.getId();
        locationDAO.name = location.getName();
        locationDAO.type = location.getType();
        locationDAO.longitude = location.getLongitude();
        locationDAO.latitude = location.getLatitude();
        locationDAO.address = location.getAddress();
        locationDAO.phoneNumber = location.getPhoneNumber();
        locationDAO.donations = location.getDonations().stream().map(Donation::getId).collect(Collectors.toList());
        return locationDAO;
    }

    public Location toLocation() {
        Location location = new Location(id, name, type, longitude, latitude, address, phoneNumber);
        //location.getDonations()
        if (donations != null) {
            donations.stream().map(id -> OrangeBlastersApplication.getInstance().getDonationService().getDonation(id))
                    .map(op -> op.orElse(null))
                    .filter(Objects::nonNull)
                    .forEach(don -> location.getDonations().add(don));
        }
        return location;
    }
}
