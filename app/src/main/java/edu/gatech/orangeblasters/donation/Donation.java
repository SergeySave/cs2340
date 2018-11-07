package edu.gatech.orangeblasters.donation;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.Optional;

public class Donation implements Serializable {

    private final String id;

    private OffsetDateTime timestamp;
    private String locationId;
    private String descShort;
    private String descLong;
    private BigDecimal value;
    private DonationCategory donationCategory;
    private String comments;
    private String pictureId;

    public Donation(String id, OffsetDateTime timestamp, String locationId, String descShort, String descLong, BigDecimal value, DonationCategory donationCategory) {
        this(id, timestamp, locationId, descShort, descLong, value, donationCategory, null, null);
    }

    public Donation(String id, OffsetDateTime timestamp, String locationId, String descShort, String descLong, BigDecimal value, DonationCategory donationCategory, String comments) {
        this(id, timestamp, locationId, descShort, descLong, value, donationCategory, comments, null);
    }

    public Donation(String id, OffsetDateTime timestamp, String locationId, String descShort, String descLong, BigDecimal value, DonationCategory donationCategory, String comments, String pictureId) {
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

    public String getId() {
        return id;
    }

    public OffsetDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(OffsetDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String id) {
        this.locationId = id;
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

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public DonationCategory getDonationCategory() {
        return donationCategory;
    }

    public void setDonationCategory(DonationCategory donationCategory) {
        this.donationCategory = donationCategory;
    }

    public Optional<String> getComments() {
        return Optional.ofNullable(comments);
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Optional<String> getPictureId() {
        return Optional.ofNullable(pictureId);
    }

    public void setPictureId(String pictureId) {
        this.pictureId = pictureId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Donation donation = (Donation) o;
        return Objects.equals(timestamp, donation.timestamp) &&
                Objects.equals(locationId, donation.locationId) &&
                Objects.equals(descShort, donation.descShort) &&
                Objects.equals(descLong, donation.descLong) &&
                Objects.equals(value, donation.value) &&
                donationCategory == donation.donationCategory &&
                Objects.equals(comments, donation.comments) &&
                Objects.equals(pictureId, donation.pictureId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(timestamp, locationId, descShort, descLong, value, donationCategory, comments, pictureId);
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
