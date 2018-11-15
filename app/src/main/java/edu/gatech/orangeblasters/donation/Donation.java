package edu.gatech.orangeblasters.donation;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.Optional;

/**
 * Represents a Donation in the system
 */
public class Donation implements Serializable {

    private final String id;

    private final OffsetDateTime timestamp;
    private final String locationId;
    private final String descShort;
    private final String descLong;
    private final BigDecimal value;
    private final DonationCategory donationCategory;
    private final String comments;
    private final String pictureId;

    /**
     * Creates a donation without the comments and picture
     *
     * @param id donation id
     * @param timestamp donation timestamp
     * @param locationId donation location
     * @param descShort donation short description
     * @param descLong donation long description
     * @param value donation value
     * @param donationCategory donation category
     */
    public Donation(String id, OffsetDateTime timestamp, String locationId, String descShort,
                    String descLong, BigDecimal value, DonationCategory donationCategory) {
        this(id, timestamp, locationId, descShort, descLong, value, donationCategory,
                null, null);
    }

    /**
     * Creates a donation without the picture
     *
     * @param id donation id
     * @param timestamp donation timestamp
     * @param locationId donation location
     * @param descShort donation short description
     * @param descLong donation long description
     * @param value donation value
     * @param donationCategory donation category
     * @param comments donation comments
     */
    public Donation(String id, OffsetDateTime timestamp, String locationId, String descShort,
                    String descLong, BigDecimal value, DonationCategory donationCategory,
                    String comments) {
        this(id, timestamp, locationId, descShort, descLong, value,
                donationCategory, comments, null);
    }

    /**
     * Creates a new donation
     *
     * @param id donation id
     * @param timestamp donation timestamp
     * @param locationId donation location
     * @param descShort donation short description
     * @param descLong donation long description
     * @param value donation value
     * @param donationCategory donation category
     * @param comments donation comments
     * @param pictureId donation picture
     */
    public Donation(String id, OffsetDateTime timestamp, String locationId, String descShort,
                    String descLong, BigDecimal value, DonationCategory donationCategory,
                    String comments, String pictureId) {
        this.id = id;
        this.timestamp = timestamp;
        this.locationId = locationId;
        this.descShort = descShort;
        this.descLong = descLong;
        this.value = value;
        this.donationCategory = donationCategory;
        this.comments = comments;
        this.pictureId = pictureId;
    }

    /**
     * Gets the donation ID
     *
     * @return donation ID
     */
    public String getId() {
        return id;
    }

    /**
     * Gets the donation time
     *
     * @return donation time stamp
     */
    public OffsetDateTime getTimestamp() {
        return timestamp;
    }

// --Commented out by Inspection START (11/7/18, 2:37 PM):
//    public void setTimestamp(OffsetDateTime timestamp) {
//        this.timestamp = timestamp;
//    }
// --Commented out by Inspection STOP (11/7/18, 2:37 PM)

    /**
     * Gets the location ID of the donation
     *
     * @return donation's location
     */
    public String getLocationId() {
        return locationId;
    }

// --Commented out by Inspection START (11/7/18, 2:37 PM):
//    public void setLocationId(String id) {
//        this.locationId = id;
//    }
// --Commented out by Inspection STOP (11/7/18, 2:37 PM)

    /**
     * Gets the donation's short description
     *
     * @return the donation's short description
     */
    public String getDescShort() {
        return descShort;
    }

// --Commented out by Inspection START (11/7/18, 2:37 PM):
//    public void setDescShort(String descShort) {
//        this.descShort = descShort;
//    }
// --Commented out by Inspection STOP (11/7/18, 2:37 PM)

    /**
     * Gets the donation's long description
     *
     * @return the donation's long description
     */
    public String getDescLong() {
        return descLong;
    }

// --Commented out by Inspection START (11/7/18, 2:37 PM):
//    public void setDescLong(String descLong) {
//        this.descLong = descLong;
//    }
// --Commented out by Inspection STOP (11/7/18, 2:37 PM)

    /**
     * Gets the donation's value
     *
     * @return the donation's value
     */
    public BigDecimal getValue() {
        return value;
    }

// --Commented out by Inspection START (11/7/18, 2:37 PM):
//    public void setValue(BigDecimal value) {
//        this.value = value;
//    }
// --Commented out by Inspection STOP (11/7/18, 2:37 PM)

    /**
     * Gets the donation category
     *
     * @return the donation category
     */
    public DonationCategory getDonationCategory() {
        return donationCategory;
    }

// --Commented out by Inspection START (11/7/18, 2:37 PM):
//    public void setDonationCategory(DonationCategory donationCategory) {
//        this.donationCategory = donationCategory;
//    }
// --Commented out by Inspection STOP (11/7/18, 2:37 PM)

    /**
     * Gets the donation comments
     *
     * @return the donation comments
     */
    public Optional<String> getComments() {
        return Optional.ofNullable(comments);
    }

// --Commented out by Inspection START (11/7/18, 2:37 PM):
//    public void setComments(String comments) {
//        this.comments = comments;
//    }
// --Commented out by Inspection STOP (11/7/18, 2:37 PM)

    /**
     * Gets the donation's picture
     *
     * @return the donation's picture
     */
    public Optional<String> getPictureId() {
        return Optional.ofNullable(pictureId);
    }

// --Commented out by Inspection START (11/7/18, 2:37 PM):
//    public void setPictureId(String pictureId) {
//        this.pictureId = pictureId;
//    }
// --Commented out by Inspection STOP (11/7/18, 2:37 PM)

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if ((o == null) || (getClass() != o.getClass())) {
            return false;
        }
        Donation donation = (Donation) o;
        return Objects.equals(timestamp, donation.timestamp) &&
                Objects.equals(locationId, donation.locationId) &&
                secondHalfOfEquals(donation);
    }

    private boolean secondHalfOfEquals(Donation donation) {
        return Objects.equals(descShort, donation.descShort) &&
                Objects.equals(descLong, donation.descLong) &&
                Objects.equals(value, donation.value) &&
                (donationCategory == donation.donationCategory) &&
                Objects.equals(comments, donation.comments) &&
                Objects.equals(pictureId, donation.pictureId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(timestamp, locationId, descShort, descLong, value, donationCategory,
                comments, pictureId);
    }

    @Override
    public String toString() {
        return "Donation{" +
                "timestamp=" + timestamp +
                ", locationId=" + locationId +
                ", descShort='" + descShort + '\'' +
                ", descLong='" + descLong + '\'' +
                ", value=" + value +
                ", donationCategory=" + donationCategory +
                ", comments='" + comments + '\'' +
                '}';
    }
}
