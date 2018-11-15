package edu.gatech.orangeblasters.donation.impl;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;

import edu.gatech.orangeblasters.donation.Donation;
import edu.gatech.orangeblasters.donation.DonationCategory;

//All getter and setter methods are used by firebase
@SuppressWarnings("unused")
public class DonationDAO {
    private String id;

    private long timestamp;
    private String locationId;
    private String descShort;
    private String descLong;
    private String value;
    private DonationCategory donationCategory;
    private String comments;
    private String pictureId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getDescShort() {
        return descShort;
    }

    public void setDescShort(String descShort) {
        this.descShort = descShort;
    }

    public String getDescLong() {
        return descLong;
    }

    public void setDescLong(String descLong) {
        this.descLong = descLong;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public DonationCategory getDonationCategory() {
        return donationCategory;
    }

    public void setDonationCategory(DonationCategory donationCategory) {
        this.donationCategory = donationCategory;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getPictureId() {
        return pictureId;
    }

    public void setPictureId(String pictureId) {
        this.pictureId = pictureId;
    }

    /**
     * Gets the donation's details in DAO format
     *
     * @param donation the donation to get in the format
     * @return the Donation in DAO format
     */
    public static DonationDAO fromDonation(Donation donation) {
        DonationDAO donationDAO = new DonationDAO();
        donationDAO.id = donation.getId();
        OffsetDateTime timestamp = donation.getTimestamp();
        ZonedDateTime zonedDateTime = timestamp.atZoneSameInstant(ZoneId.of("UTC"));
        donationDAO.timestamp = zonedDateTime.toEpochSecond();
        donationDAO.locationId = donation.getLocationId();
        donationDAO.descShort = donation.getDescShort();
        donationDAO.descLong = donation.getDescLong();
        BigDecimal value = donation.getValue();
        donationDAO.value = value.toString();
        donationDAO.donationCategory = donation.getDonationCategory();
        Optional<String> comments = donation.getComments();
        donationDAO.comments = comments.orElse(null);
        Optional<String> pictureId = donation.getPictureId();
        donationDAO.pictureId = pictureId.orElse(null);
        return donationDAO;
    }

    /**
     * Puts the donation in
     *
     * @return the donation with all details
     */
    public Donation toDonation() {
        ZoneId utc = ZoneId.of("UTC");
        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(Instant.ofEpochSecond(timestamp), utc);
        return new Donation(id, zonedDateTime.toOffsetDateTime(), locationId, descShort, descLong,
                new BigDecimal(value), donationCategory, comments, pictureId);
    }

}
