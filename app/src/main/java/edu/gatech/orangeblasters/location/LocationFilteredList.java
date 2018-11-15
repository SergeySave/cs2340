package edu.gatech.orangeblasters.location;

import android.support.v7.util.ListUpdateCallback;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.stream.Stream;

import edu.gatech.orangeblasters.FilteredList;
import edu.gatech.orangeblasters.donation.Donation;

public class LocationFilteredList extends FilteredList<Location> {

    private static final int POINTS_SAME_NAME = 20;
    private static final int POINTS_SIMILAR_NAME = 5;
    private static final int POINTS_TYPE = 2;
    private static final int POINTS_ADDRESS = 2;

    public LocationFilteredList(ListUpdateCallback listUpdater) {
        this(LocationFilteredList::relevanceFunction, listUpdater);
    }

    private LocationFilteredList(BiFunction<String, Location, Integer> relevanceFunction, ListUpdateCallback listUpdater) {
        super(Location.class, relevanceFunction,
                Comparator.comparing(Location::getName),
                (don1, don2) -> don1.getId().equals(don2.getId()),
                listUpdater);
    }

    /**
     * A function to measure the relevance of a donation based on the text
     *
     * @param text the text to compare to
     * @param location the donation to text relevance to
     * @return the donation's relevance int
     */
    private static int relevanceFunction(String text, Location location) {
        if ((text == null) || text.isEmpty()) {
            return 1;
        }

        String lower = text.toLowerCase();
        String name = location.getName();
        LocationType type = location.getType();
        String lowerName = name.toLowerCase();
        String typeName = type.getFullName();
        String typeNameLower = typeName.toLowerCase();
        String address = location.getAddress();
        String addressLower = address.toLowerCase();
        List<Donation> donations = location.getDonations();
        Stream<Donation> stream = donations.stream();
        Stream<Donation> filtered = stream.filter(donation -> {
            String donationDescShort = donation.getDescShort();
            String donationDescShortLower = donationDescShort.toLowerCase();
            String donationDescLong = donation.getDescLong();
            String donationDescLongLower = donationDescLong.toLowerCase();
            Optional<String> comments = donation.getComments();
            Optional<String> lowerComments = comments.map(String::toLowerCase);
            Optional<Boolean> booleanOptional = lowerComments.map(s -> s.contains(
                    lower));
            return donationDescShortLower.contains(lower) ||
                    donationDescLongLower.contains(lower) ||
                    booleanOptional.orElse(false);
        });
        return (name.equalsIgnoreCase(lower) ? POINTS_SAME_NAME : 0)
                //Exact name = 20 points
                + (lowerName.contains(lower) ? POINTS_SIMILAR_NAME : 0)
                //Contains name = 5 point
                + (typeNameLower.contains(lower) ? POINTS_TYPE : 0)
                //Type = 2 point
                + (addressLower.contains(lower) ? POINTS_ADDRESS : 0)
                //Address = 2 point
                + ((int) filtered.count());
    }
}
