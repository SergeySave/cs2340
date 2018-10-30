package edu.gatech.orangeblasters.donation.impl;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import edu.gatech.orangeblasters.donation.Donation;
import edu.gatech.orangeblasters.donation.DonationCategory;

public class DonationDAO {
    public String id;

    public long timestamp;
    public String locationId;
    public String descShort;
    public String descLong;
    public String value;
    public DonationCategory donationCategory;
    public String comments;
    public String pictureId;


    public static DonationDAO fromDonation(Donation donation) {
        DonationDAO donationDAO = new DonationDAO();
        donationDAO.id = donation.getId();
        donationDAO.timestamp = donation.getTimestamp().atZoneSameInstant(ZoneId.of("UTC")).toEpochSecond();
        donationDAO.locationId = donation.getLocationId();
        donationDAO.descShort = donation.getDescShort();
        donationDAO.descLong = donation.getDescLong();
        donationDAO.value = donation.getValue().toString();
        donationDAO.donationCategory = donation.getDonationCategory();
        donationDAO.comments = donation.getComments().orElse(null);
        donationDAO.pictureId = donation.getPictureId().orElse(null);
        return donationDAO;
    }

    public Donation toDonation() {
        return new Donation(id, ZonedDateTime.ofInstant(Instant.ofEpochSecond(timestamp), ZoneId.of("UTC")).toOffsetDateTime(), locationId, descShort, descLong, new BigDecimal(value), donationCategory, comments, pictureId);
    }

}
