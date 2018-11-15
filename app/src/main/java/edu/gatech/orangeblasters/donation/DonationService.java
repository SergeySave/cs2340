package edu.gatech.orangeblasters.donation;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.stream.Stream;

public interface DonationService {

    Optional<Donation> getDonation(String id);
    Stream<Donation> getDonations();
    Donation createDonation(OffsetDateTime timestamp, String locationId, String descShort,
                            String descLong, BigDecimal value, DonationCategory donationCategory,
                            String comments, String pictureId);
}
