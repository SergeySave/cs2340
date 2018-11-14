package edu.gatech.orangeblasters.location;

import android.arch.lifecycle.LiveData;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public interface LocationService {

    Optional<Location> getLocation(String id);
    Stream<Location> getLocations();
    LiveData<List<String>> getLiveIDList();
    void update(Location location);

    //While this method is currently unused it maybe be used in the future
    @SuppressWarnings("unused")
    Location addLocation(String name, LocationType type, double longitude, double latitude,
                         String address, String phoneNumber);
}
