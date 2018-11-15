package edu.gatech.orangeblasters.location;

import android.arch.lifecycle.LiveData;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Represents an abstraction for the location manager
 */
public interface LocationService {

    /**
     * Get the location for a given id
     *
     * @param id the location's id
     * @return the location
     */
    Optional<Location> getLocation(String id);

    /**
     * Get all of the current tracked location
     *
     * @return all of the current tracked locations
     */
    Stream<Location> getLocations();

    /**
     * Get an observable version of the list of location ids
     *
     * @return a list of the location ids
     */
    LiveData<List<String>> getLiveIDList();

    /**
     * Update a location in the database
     *
     * @param location the location to update
     */
    void update(Location location);

    /**
     * Add a new location to the system
     *
     * @param name the locations name
     * @param type the location type
     * @param longitude the locations longitude
     * @param latitude the locations latitude
     * @param address the locations address
     * @param phoneNumber the locations phone number
     * @return the newly created location
     */
    //While this method is currently unused it maybe be used in the future
    @SuppressWarnings("unused")
    Location addLocation(String name, LocationType type, double longitude, double latitude,
                         String address, String phoneNumber);
}
