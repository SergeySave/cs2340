package edu.gatech.orangeblasters.donation;

import android.os.Parcel;
import android.os.Parcelable;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.Optional;

import edu.gatech.orangeblasters.OrangeBlastersApplication;
import edu.gatech.orangeblasters.location.Location;

public class Donation implements Parcelable {

    private OffsetDateTime timestamp;
    private Location location;
    private String descShort;
    private String descLong;
    private BigDecimal value;
    private DonationCategory donationCategory;
    private String comments;
    private int pictureIndex;

    public Donation(OffsetDateTime timestamp, Location location, String descShort, String descLong, BigDecimal value, DonationCategory donationCategory) {
        this(timestamp, location, descShort, descLong, value, donationCategory, null, -1);
    }

    public Donation(OffsetDateTime timestamp, Location location, String descShort, String descLong, BigDecimal value, DonationCategory donationCategory, String comments) {
        this(timestamp, location, descShort, descLong, value, donationCategory, comments, -1);
    }

    public Donation(OffsetDateTime timestamp, Location location, String descShort, String descLong, BigDecimal value, DonationCategory donationCategory, String comments, int pictureIndex) {
        this.timestamp = timestamp;
        this.location = location;
        this.descShort = descShort;
        this.descLong = descLong;
        this.value = value;
        this.donationCategory = donationCategory;
        this.comments = comments;
        this.pictureIndex = pictureIndex;
    }

    protected Donation(Parcel in) {
        timestamp = ((OffsetDateTime) in.readSerializable());
        location = ((Location) in.readSerializable());
        OrangeBlastersApplication.getInstance().getLocations().stream().filter((a) -> a.equals(this.location)).findFirst().ifPresent(location1 -> this.location = location1);
        descShort = in.readString();
        descLong = in.readString();
        value = ((BigDecimal) in.readSerializable());
        donationCategory = ((DonationCategory) in.readSerializable());
        comments = in.readString();
        pictureIndex = in.readInt();
    }

    public static final Creator<Donation> CREATOR = new Creator<Donation>() {
        @Override
        public Donation createFromParcel(Parcel in) {
            return new Donation(in);
        }

        @Override
        public Donation[] newArray(int size) {
            return new Donation[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(timestamp);
        dest.writeSerializable(location);
        dest.writeString(descShort);
        dest.writeString(descLong);
        dest.writeSerializable(value);
        dest.writeSerializable(donationCategory);
        dest.writeString(comments);
        dest.writeInt(pictureIndex);
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

    public int getPictureLocation() {
        return pictureIndex;
    }

    public void setPictureLocation(int pictureIndex) {
        this.pictureIndex = pictureIndex;
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
                Objects.equals(pictureIndex, donation.pictureIndex);
    }

    @Override
    public int hashCode() {

        return Objects.hash(timestamp, location, descShort, descLong, value, donationCategory, comments, pictureIndex);
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
                '}';
    }
}
