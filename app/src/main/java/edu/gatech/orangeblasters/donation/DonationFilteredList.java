package edu.gatech.orangeblasters.donation;

import android.support.v7.util.ListUpdateCallback;

import java.util.Comparator;
import java.util.Optional;
import java.util.function.BiFunction;

import edu.gatech.orangeblasters.FilteredList;
import edu.gatech.orangeblasters.OrangeBlastersApplication;
import edu.gatech.orangeblasters.location.Location;
import edu.gatech.orangeblasters.location.LocationService;

public class DonationFilteredList extends FilteredList<Donation> {

    private static final int POINTS_SAME_NAME = 20;
    private static final int POINTS_SIMILAR_NAME = 5;
    private static final int POINTS_SIMILAR_DESC = 2;
    private static final int POINTS_CATEGORY = 3;

    public DonationFilteredList(ListUpdateCallback listUpdater) {
        this(DonationFilteredList::relevanceFunction, listUpdater);
    }

    private DonationFilteredList(BiFunction<String, Donation, Integer> relevanceFunction, ListUpdateCallback listUpdater) {
        super(Donation.class, relevanceFunction,
                Comparator.comparing(Donation::getDescShort),
                (don1, don2) -> don1.getId().equals(don2.getId()),
                listUpdater);
    }

    /**
     * A function to measure the relevance of a donation based on the text
     *
     * @param text the text to compare to
     * @param donation the donation to text relevance to
     * @return the donation's relevance int
     */
    private static int relevanceFunction(String text, Donation donation) {
        if ((text == null) || text.isEmpty()) {
            return 1;
        }

        String lower = text.toLowerCase();
        String descShort = donation.getDescShort();
        String lowerDescShort = descShort.toLowerCase();
        String descLong = donation.getDescLong();
        String lowerDescLong = descLong.toLowerCase();
        Optional<String> comments = donation.getComments();
        Optional<String> lowerComments = comments.map(String::toLowerCase);
        Optional<Integer> lowerCommentInt = lowerComments.map(s -> s.contains(lower) ? 1 : 0);

        OrangeBlastersApplication orangeBlastersApplication = OrangeBlastersApplication.getInstance();
        LocationService locationService = orangeBlastersApplication.getLocationService();
        Optional<Location> location = locationService.getLocation(donation.getLocationId());

        Optional<String> locName = location.map(Location::getName);
        Optional<String> locLowerName = locName.map(String::toLowerCase);
        Optional<Integer> locPoint = locLowerName.map(s -> s.contains(lower) ? 1 : 0);
        DonationCategory donationCategory = donation.getDonationCategory();
        String fullName = donationCategory.getFullName();
        String lowerFullname = fullName.toLowerCase();
        return (descShort.equalsIgnoreCase(lower) ? POINTS_SAME_NAME : 0) //Exact name = 20 points
                + (lowerDescShort.contains(lower) ? POINTS_SIMILAR_NAME : 0) //Contains name = 5 point
                + (lowerDescLong.contains(lower) ? POINTS_SIMILAR_DESC : 0) //Type = 2 point
                + (lowerCommentInt.orElse(0)) //comments = 1 point
                + (locPoint.orElse(0)) // donation has the right name = 1 point
                + (lowerFullname.contains(lower) ? POINTS_CATEGORY : 0); //category = 3 points

    }
}
