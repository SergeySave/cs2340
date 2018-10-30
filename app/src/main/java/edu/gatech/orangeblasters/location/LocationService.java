package edu.gatech.orangeblasters.location;

import android.arch.lifecycle.LiveData;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public interface LocationService {

    Optional<Location> getLocation(String id);
    Stream<Location> getLocations();
    LiveData<List<String>> getLiveIDList();
}