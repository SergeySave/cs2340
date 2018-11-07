package edu.gatech.orangeblasters.location;

import android.support.v7.util.ListUpdateCallback;

import java.util.Comparator;
import java.util.function.BiFunction;

import edu.gatech.orangeblasters.FilteredList;

public class LocationFilteredList extends FilteredList<Location> {
    public LocationFilteredList(ListUpdateCallback listUpdater) {
        this(LocationFilteredList::relevanceFunction, listUpdater);
    }

    public LocationFilteredList(BiFunction<String, Location, Integer> relevanceFunction, ListUpdateCallback listUpdater) {
        super(Location.class, relevanceFunction,
                Comparator.comparing(Location::getName),
                (don1, don2) -> don1.getId().equals(don2.getId()),
                listUpdater);
    }

    private static int relevanceFunction(String text, Location location) {
        if (text == null || text.isEmpty()) {
            return 1;
        }

        String lower = text.toLowerCase();
        return (location.getName().equalsIgnoreCase(lower) ? 20 : 0) //Exact name = 20 points
                + (location.getName().toLowerCase().contains(lower) ? 5 : 0) //Contains name = 5 point
                + (location.getType().getFullName().toLowerCase().contains(lower) ? 2 : 0) //Type = 2 point
                + (location.getAddress().toLowerCase().contains(lower) ? 2 : 0) //Address = 2 point
                + ((int)location.getDonations().stream().filter(donation ->  //1 point per relevant donation
                donation.getDescShort().toLowerCase().contains(lower) ||
                        donation.getDescLong().toLowerCase().contains(lower) ||
                        donation.getComments().map(str -> str.toLowerCase().contains(lower)).orElse(false)).count());
    }
}
