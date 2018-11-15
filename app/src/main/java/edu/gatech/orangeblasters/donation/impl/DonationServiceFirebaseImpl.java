package edu.gatech.orangeblasters.donation.impl;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import edu.gatech.orangeblasters.OrangeBlastersApplication;
import edu.gatech.orangeblasters.donation.Donation;
import edu.gatech.orangeblasters.donation.DonationCategory;
import edu.gatech.orangeblasters.donation.DonationService;
import edu.gatech.orangeblasters.location.Location;
import edu.gatech.orangeblasters.location.LocationService;

/**
 * Represents a storage of donations in firebase
 */
public class DonationServiceFirebaseImpl implements DonationService {

    private static final String DONATIONS = "donations";
    private static final String IDS = "ids";

    private final OrangeBlastersApplication orangeBlastersApplication =
            OrangeBlastersApplication.getInstance();
    private final LocationService locationService = orangeBlastersApplication.getLocationService();
    private final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private final DatabaseReference databaseReference = firebaseDatabase.getReference(DONATIONS);

    //Locations are stored locally to allow me to not have to rewrite the whole
    private final Map<String, Donation> donations = new LinkedHashMap<>();

    private final Random random = new Random();
    private String createId() {
        IntStream ints = random.ints(4);
        Stream<String> hexs = ints.mapToObj(Integer::toHexString);
        return hexs.collect(Collectors.joining());
    }

    /**
     * Create a new DonationServiceFirebaseImpl
     */
    public DonationServiceFirebaseImpl() {
        DatabaseReference ids = databaseReference.child(IDS);
        ids.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                DonationDAO donationDAO = dataSnapshot.getValue(DonationDAO.class);
                if (donationDAO != null) {
                    Donation value = donationDAO.toDonation();
                    donations.put(donationDAO.getId(), value);
                    Optional<List<Donation>> location = getLocation(value);
                    location.ifPresent( dons -> dons.add(value));
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                DonationDAO donationDAO = dataSnapshot.getValue(DonationDAO.class);
                if (donationDAO != null) {
                    Donation value = donationDAO.toDonation();
                    donations.put(donationDAO.getId(), value);
                    Optional<List<Donation>> location = getLocation(value);
                    location.ifPresent( dons -> dons.add(value));
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                DonationDAO donationDAO = dataSnapshot.getValue(DonationDAO.class);
                if (donationDAO != null) {
                    donations.remove(donationDAO.getId());
                    Optional<List<Donation>> location = getLocation(donationDAO);
                    location.ifPresent( dons -> dons.removeIf(don -> {
                        String id = don.getId();
                        return id.equals(donationDAO.getId());
                    }));
                }
            }

            private Optional<List<Donation>> getLocation(DonationDAO donationDAO) {
                Optional<Location> location = locationService
                        .getLocation(donationDAO.getLocationId());
                return location.map(Location::getDonations);
            }

            private Optional<List<Donation>> getLocation(Donation value) {
                Optional<Location> location = locationService.getLocation(value.getLocationId());
                return location.map(Location::getDonations);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) { }

            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });
    }

    @Override
    public Optional<Donation> getDonation(String id) {
        return Optional.ofNullable(donations.get(id));
    }

    @Override
    public Stream<Donation> getDonations() {
        Collection<Donation> values = donations.values();
        return values.stream();
    }

    @Override
    public Donation createDonation(OffsetDateTime timestamp, String locationId, String descShort,
                                   String descLong, BigDecimal value,
                                   DonationCategory donationCategory,
                                   String comments, String pictureId) {
        Donation donation = new Donation(createId(), timestamp, locationId, descShort,
                descLong, value, donationCategory, comments, pictureId);
        DatabaseReference ids = databaseReference.child(IDS);
        DatabaseReference idRef = ids.child(donation.getId());
        idRef.setValue(DonationDAO.fromDonation(donation));
        return donation;
    }
}
