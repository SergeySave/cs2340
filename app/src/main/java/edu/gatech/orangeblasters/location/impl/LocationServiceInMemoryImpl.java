package edu.gatech.orangeblasters.location.impl;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Scanner;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import edu.gatech.orangeblasters.location.Location;
import edu.gatech.orangeblasters.location.LocationService;
import edu.gatech.orangeblasters.location.LocationType;

@Deprecated
public class LocationServiceInMemoryImpl implements LocationService {

    //A linked hash map is used to ensure that the order stays constant
    private final Map<String, Location> locations = new LinkedHashMap<>();
    private final MutableLiveData<List<String>> idList = new MutableLiveData<>();
    private final Random random = new Random();
    private String createId() {
        return random.ints(4).mapToObj(Integer::toHexString).collect(Collectors.joining());
    }

    /**
     * Imports the CSV into a format that we can use to handle
     * the location details
     *
     * @param inputCSV the CSV to import
     */
    public LocationServiceInMemoryImpl(InputStream inputCSV) {
        Thread locationInitializationThread = new Thread(() -> {
            try(Scanner scan = new Scanner(inputCSV)) {
                // A list is used here so I can then easily find the indexes
                // the header names are first converted to lowercase to match better
                List<String> header = Arrays.stream(scan.nextLine().substring(1).split(","))
                        .map(String::toLowerCase)
                        .collect(Collectors.toList());

                int idIndex = header.indexOf("key");
                int nameIndex = header.indexOf("name");
                int typeIndex = header.indexOf("type");
                int longIndex = header.indexOf("longitude");
                int latIndex = header.indexOf("latitude");
                int addressIndex = header.indexOf("street address");
                int pNumIndex = header.indexOf("phone");

                if (nameIndex == -1 || typeIndex == -1 || longIndex == -1
                        || latIndex == -1 || addressIndex == -1 || pNumIndex == -1) {
                    throw new RuntimeException("Location Data Missing Header Information");
                }

                //This is a map of location type names to location types
                //This is done here to allow O(1) conversion from name to type
                //The location types are all lower cased to facilitate better matching
                Map<String, LocationType> typeMap = Arrays.stream(LocationType.values())
                        .collect(Collectors.toMap(((Function<LocationType, String>)
                                LocationType::getFullName)
                                .andThen(String::toLowerCase), Function.identity()));

                while (scan.hasNextLine()) {
                    String[] entry = scan.nextLine().split(",");

                    String typeString = entry[typeIndex].toLowerCase();
                    if (!typeMap.containsKey(typeString)) {
                        Log.e("Location Initialization", "Location Type " + typeString +
                                " does not exist. Location Entry Skipped.");
                    }
                    try {
                        String id = entry[idIndex];
                        Location newLocation = new Location(createId(),
                                entry[nameIndex], typeMap.get(typeString),
                                Double.parseDouble(entry[longIndex]),
                                Double.parseDouble(entry[latIndex]),
                                entry[addressIndex], entry[pNumIndex]);
                        locations.put(id, newLocation);
                    } catch (NumberFormatException e) {
                        Log.e("Location Initialization", "Location " + entry[nameIndex] +
                                " contains an latitude or longitude. Location Entry Skipped.");
                    }
                }

                //Post the new list
                idList.postValue(new ArrayList<>(locations.keySet()));
            }
        }, "Location Initialization Thread");

        locationInitializationThread.setDaemon(true);
        locationInitializationThread.start();
    }

    @Override
    public Optional<Location> getLocation(String id) {
        return Optional.ofNullable(locations.get(id));
    }

    @Override
    public Stream<Location> getLocations() {
        return locations.values().stream();
    }

    @Override
    public LiveData<List<String>> getLiveIDList() {
        return idList;
    }

    @Override
    public void update(Location location) {
        locations.put(location.getId(), location);
    }

    @Override
    public Location addLocation(String name, LocationType type, double longitude,
                                double latitude, String address, String phoneNumber) {
        Location location = new Location(createId(), name, type, longitude, latitude,
                address, phoneNumber);
        locations.put(location.getId(), location);
        return location;
    }
}
