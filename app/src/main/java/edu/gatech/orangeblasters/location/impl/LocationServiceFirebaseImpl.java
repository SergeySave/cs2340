package edu.gatech.orangeblasters.location.impl;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import edu.gatech.orangeblasters.location.Location;
import edu.gatech.orangeblasters.location.LocationService;
import edu.gatech.orangeblasters.location.LocationType;

public class LocationServiceFirebaseImpl implements LocationService {

    private static final String LOCATIONS = "locations";
    private static final String IDS = "ids";

    private final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private final DatabaseReference databaseReference = firebaseDatabase.getReference(LOCATIONS);

    //Locations are stored locally to allow me to not have to rewrite the whole
    private final Map<String, Location> locations = new LinkedHashMap<>();
    private final MutableLiveData<List<String>> idList = new MutableLiveData<>();

    private final Random random = new Random();

    /**
     * Creates an ID for the location
     *
     * @return the location's ID
     */
    private String createId() {
        return random.ints(4).mapToObj(Integer::toHexString).collect(Collectors.joining());
    }

    public LocationServiceFirebaseImpl() {
        databaseReference.child(IDS).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                LocationDAO locationDAO = dataSnapshot.getValue(LocationDAO.class);
                if (locationDAO != null) {
                    locations.put(locationDAO.getId(), locationDAO.toLocation());
                    idList.postValue(new ArrayList<>(locations.keySet()));
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                LocationDAO locationDAO = dataSnapshot.getValue(LocationDAO.class);
                if (locationDAO != null) {
                    locations.put(locationDAO.getId(), locationDAO.toLocation());
                    idList.postValue(new ArrayList<>(locations.keySet()));
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                LocationDAO locationDAO = dataSnapshot.getValue(LocationDAO.class);
                if (locationDAO != null) {
                    locations.remove(locationDAO.getId());
                    idList.postValue(new ArrayList<>(locations.keySet()));
                }

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) { }

            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });
    }

    @Override
    public void update(Location location) {
        databaseReference.child(IDS).child(location.getId()).setValue(LocationDAO.fromLocation(location));
    }

    @Override
    public Location addLocation(String name, LocationType type, double longitude, double latitude, String address, String phoneNumber) {
        Location location = new Location(createId(), name, type, longitude, latitude, address, phoneNumber);
        locations.put(location.getId(), location);
        databaseReference.child(IDS).child(location.getId()).setValue(LocationDAO.fromLocation(location));
        return location;
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
}
