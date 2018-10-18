package edu.gatech.orangeblasters;

import android.app.Application;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;
import java.util.stream.Collectors;

import edu.gatech.orangeblasters.account.Account;
import edu.gatech.orangeblasters.account.AccountState;
import edu.gatech.orangeblasters.account.User;
import edu.gatech.orangeblasters.location.Location;
import edu.gatech.orangeblasters.location.LocationType;

public class OrangeBlastersApplication extends Application {
    private List<Account> accounts = new ArrayList<>();
    private LiveList<Location> locations = new LiveList<>(new ArrayList<>());

    public List<Account> getAccounts() {
        return accounts;
    }

    public List<Location> getLocations() {
        return locations;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        //Add default user
        accounts.add(new User("User", "user@user.com", "pass", AccountState.NORMAL));

        Thread locationInitializationThread = new Thread(() -> {
            try(Scanner scan = new Scanner(getResources().openRawResource(R.raw.location_data))) {
                // A list is used here so I can then easily find the indexes
                // the header names are first converted to lowercase to match better
                List<String> header = Arrays.stream(scan.nextLine().split(","))
                        .map(String::toLowerCase)
                        .collect(Collectors.toList());

                int nameIndex = header.indexOf("name");
                int typeIndex = header.indexOf("type");
                int longIndex = header.indexOf("longitude");
                int latIndex = header.indexOf("latitude");
                int addrIndex = header.indexOf("street address");
                int pNumIndex = header.indexOf("phone");

                if (nameIndex == -1 || typeIndex == -1 || longIndex == -1
                        || latIndex == -1 || addrIndex == -1 || pNumIndex == -1) {
                    throw new RuntimeException("Location Data Missing Header Information");
                }

                //This is a map of location type names to location types
                //This is done here to allow O(1) conversion from name to type
                //The location types are all lowercased to facilitate better matching
                Map<String, LocationType> typeMap = Arrays.stream(LocationType.values())
                        .collect(Collectors.toMap(((Function<LocationType, String>)LocationType::getFullName)
                                .andThen(String::toLowerCase), Function.identity()));

                while (scan.hasNextLine()) {
                    String[] entry = scan.nextLine().split(",");

                    String typeString = entry[typeIndex].toLowerCase();
                    if (!typeMap.containsKey(typeString)) {
                        Log.e("Location Initialization", "Location Type " + typeString +
                                " does not exist. Location Entry Skipped.");
                    }
                    try {
                        Location newLocation = new Location(entry[nameIndex], typeMap.get(typeString),
                                Double.parseDouble(entry[longIndex]), Double.parseDouble(entry[latIndex]),
                                entry[addrIndex], entry[pNumIndex]);
                        locations.add(newLocation);
                    } catch (NumberFormatException e) {
                        Log.e("Location Initialization", "Location " + entry[nameIndex] +
                                " contains an latitude or longitude. Location Entry Skipped.");
                    }
                }
            }
        }, "Location Initialization Thread");

        locationInitializationThread.setDaemon(true);
        locationInitializationThread.start();
    }
}
