package edu.gatech.orangeblasters.donation.impl;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import edu.gatech.orangeblasters.donation.Donation;
import edu.gatech.orangeblasters.donation.DonationCategory;

public class DonationDAO {
    public String id;

    private long timestamp;
    public String locationId;
    private String descShort;
    private String descLong;
    private String value;
    private DonationCategory donationCategory;
    private String comments;
    private String pictureId;

    /**
     * Gets the donation's details in DAO format
     *
     * @param donation the donation to get in the format
     * @return the Donation in DAO format
     */
    public static DonationDAO fromDonation(Donation donation) {
        DonationDAO donationDAO = new DonationDAO();
        donationDAO.id = donation.getId();
        donationDAO.timestamp = donation.getTimestamp().atZoneSameInstant(
                ZoneId.of("UTC")).toEpochSecond();
        donationDAO.locationId = donation.getLocationId();
        donationDAO.descShort = donation.getDescShort();
        donationDAO.descLong = donation.getDescLong();
        donationDAO.value = donation.getValue().toString();
        donationDAO.donationCategory = donation.getDonationCategory();
        donationDAO.comments = donation.getComments().orElse(null);
        donationDAO.pictureId = donation.getPictureId().orElse(null);
        return donationDAO;
    }

    /**
     * Puts the donation in
     *
     * @return the donation with all details
     */
    public Donation toDonation() {
        return new Donation(id, ZonedDateTime.ofInstant(Instant.ofEpochSecond(timestamp), ZoneId.of(
                "UTC")).toOffsetDateTime(), locationId, descShort, descLong, new BigDecimal(
                        value), donationCategory, comments, pictureId);
    }

}
