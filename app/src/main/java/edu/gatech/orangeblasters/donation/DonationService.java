package edu.gatech.orangeblasters.donation;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Abstracts the storage for donations
 */
public interface DonationService {

    /**
     * Get the donation for a given id
     *
     * @param id the donation's id
     * @return the donation
     */
    Optional<Donation> getDonation(String id);

    /**
     * Get all of the current donations
     *
     * @return the current donations
     */
    Stream<Donation> getDonations();

    /**
     * Create a new donation
     *
     * @param timestamp the timestamp of when the donation was created
     * @param locationId the id of the location
     * @param descShort the short description of the donation
     * @param descLong the long description of the donation
     * @param value the value of the donation
     * @param donationCategory the category of the donation
     * @param comments the comments of the donation
     * @param pictureId the id of the donation's bitmap picture
     * @return the new donation
     */
    Donation createDonation(OffsetDateTime timestamp, String locationId, String descShort,
                            String descLong, BigDecimal value, DonationCategory donationCategory,
                            String comments, String pictureId);
}
