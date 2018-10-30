package edu.gatech.orangeblasters.donation;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Optional;

public interface DonationService {

    Optional<Donation> getDonation(String id);
    Donation createDonation(OffsetDateTime timestamp, String locationId, String descShort, String descLong,
                          BigDecimal value, DonationCategory donationCategory, String comments, String pictureId);
}
