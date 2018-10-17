package edu.gatech.orangeblasters.donation;

import java.math.BigDecimal;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.Optional;

import edu.gatech.orangeblasters.location.Location;

public class Donation {

    private OffsetDateTime timestamp;
    private Location location;
    private String descShort;
    private String descLong;
    private BigDecimal value;
    private DonationCategory donationCategory;
    private String comments;
    private URI pictureLocation;

    public Donation(OffsetDateTime timestamp, Location location, String descShort, String descLong, BigDecimal value, DonationCategory donationCategory) {
        this(timestamp, location, descShort, descLong, value, donationCategory, null, null);
    }

    public Donation(OffsetDateTime timestamp, Location location, String descShort, String descLong, BigDecimal value, DonationCategory donationCategory, String comments) {
        this(timestamp, location, descShort, descLong, value, donationCategory, comments, null);
    }

    public Donation(OffsetDateTime timestamp, Location location, String descShort, String descLong, BigDecimal value, DonationCategory donationCategory, String comments, URI pictureLocation) {
        this.timestamp = timestamp;
        this.location = location;
        this.descShort = descShort;
        this.descLong = descLong;
        this.value = value;
        this.donationCategory = donationCategory;
        this.comments = comments;
        this.pictureLocation = pictureLocation;
    }

    public OffsetDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(OffsetDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
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

    public Optional<URI> getPictureLocation() {
        return Optional.ofNullable(pictureLocation);
    }

    public void setPictureLocation(URI pictureLocation) {
        this.pictureLocation = pictureLocation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Donation donation = (Donation) o;
        return Objects.equals(timestamp, donation.timestamp) &&
                Objects.equals(location, donation.location) &&
                Objects.equals(descShort, donation.descShort) &&
                Objects.equals(descLong, donation.descLong) &&
                Objects.equals(value, donation.value) &&
                donationCategory == donation.donationCategory &&
                Objects.equals(comments, donation.comments) &&
                Objects.equals(pictureLocation, donation.pictureLocation);
    }

    @Override
    public int hashCode() {

        return Objects.hash(timestamp, location, descShort, descLong, value, donationCategory, comments, pictureLocation);
    }

    @Override
    public String toString() {
        return "Donation{" +
                "timestamp=" + timestamp +
                ", location=" + location +
                ", descShort='" + descShort + '\'' +
                ", descLong='" + descLong + '\'' +
                ", value=" + value +
                ", donationCategory=" + donationCategory +
                ", comments='" + comments + '\'' +
                ", pictureLocation=" + pictureLocation +
                '}';
    }
}
