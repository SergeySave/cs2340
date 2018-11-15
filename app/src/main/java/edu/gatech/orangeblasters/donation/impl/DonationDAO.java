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
/**
 * Represents a donation in the database
 */
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
     * Get the timestamp
     *
     * @return the timestamp
     */
    public long getTimestamp() {
        return timestamp;
    }

    /**
     * Set the timestamp
     *
     * @param timestamp the timestamp
     */
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Get the location id
     *
     * @return the location id
     */
    public String getLocationId() {
        return locationId;
    }

    /**
     * Set the location id
     *
     * @param locationId the location id
     */
    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    /**
     * Get the short description
     *
     * @return the short description
     */
    public String getDescShort() {
        return descShort;
    }

    /**
     * Set the short description
     *
     * @param descShort the short description
     */
    public void setDescShort(String descShort) {
        this.descShort = descShort;
    }

    /**
     * Get the long description
     *
     * @return the long description
     */
    public String getDescLong() {
        return descLong;
    }

    /**
     * Set the long description
     *
     * @param descLong the long description
     */
    public void setDescLong(String descLong) {
        this.descLong = descLong;
    }

    /**
     * Get the value
     *
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * Set the value
     *
     * @param value the value
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Get the category
     *
     * @return the category
     */
    public DonationCategory getDonationCategory() {
        return donationCategory;
    }

    /**
     * Set the category
     *
     * @param donationCategory the category
     */
    public void setDonationCategory(DonationCategory donationCategory) {
        this.donationCategory = donationCategory;
    }

    /**
     * Get the comments
     *
     * @return the comments
     */
    public String getComments() {
        return comments;
    }

    /**
     * Set the comments
     *
     * @param comments the comments
     */
    public void setComments(String comments) {
        this.comments = comments;
    }

    /**
     * Get the picture id
     *
     * @return the picture id
     */
    public String getPictureId() {
        return pictureId;
    }

    /**
     * Set the picture id
     *
     * @param pictureId the picture id
     */
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
        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(Instant.ofEpochSecond(timestamp),
                utc);
        return new Donation(id, zonedDateTime.toOffsetDateTime(), locationId, descShort, descLong,
                new BigDecimal(value), donationCategory, comments, pictureId);
    }

}
