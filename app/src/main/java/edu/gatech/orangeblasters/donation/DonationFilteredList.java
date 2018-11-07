package edu.gatech.orangeblasters.donation;

import android.support.v7.util.ListUpdateCallback;

import java.util.Comparator;
import java.util.function.BiFunction;

import edu.gatech.orangeblasters.FilteredList;
import edu.gatech.orangeblasters.OrangeBlastersApplication;

public class DonationFilteredList extends FilteredList<Donation> {
    public DonationFilteredList(ListUpdateCallback listUpdater) {
        this(DonationFilteredList::relevanceFunction, listUpdater);
    }

    public DonationFilteredList(BiFunction<String, Donation, Integer> relevanceFunction, ListUpdateCallback listUpdater) {
        super(Donation.class, relevanceFunction,
                Comparator.comparing(Donation::getDescShort),
                (don1, don2) -> don1.getId().equals(don2.getId()),
                listUpdater);
    }

    private static int relevanceFunction(String text, Donation donation) {
        if (text == null || text.isEmpty()) {
            return 1;
        }

        String lower = text.toLowerCase();
        return (donation.getDescShort().equalsIgnoreCase(lower) ? 20 : 0) //Exact name = 20 points
                + (donation.getDescShort().toLowerCase().contains(lower) ? 5 : 0) //Contains name = 5 point
                + (donation.getDescLong().toLowerCase().contains(lower) ? 2 : 0) //Type = 2 point
                + (donation.getComments().map(str -> str.toLowerCase().contains(lower) ? 1 : 0).orElse(0)) //comments = 1 point
                + (OrangeBlastersApplication.getInstance().getLocationService().getLocation(donation.getLocationId())
                .map(loc -> loc.getName().toLowerCase().contains(lower) ? 1 : 0).orElse(0)) // donation has the right name = 1 point
                + (donation.getDonationCategory().getFullName().toLowerCase().contains(lower) ? 3 : 0); //category = 3 points

    }
}
