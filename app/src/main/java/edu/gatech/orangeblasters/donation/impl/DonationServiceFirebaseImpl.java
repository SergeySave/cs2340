package edu.gatech.orangeblasters.donation.impl;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import edu.gatech.orangeblasters.OrangeBlastersApplication;
import edu.gatech.orangeblasters.donation.Donation;
import edu.gatech.orangeblasters.donation.DonationCategory;
import edu.gatech.orangeblasters.donation.DonationService;
import edu.gatech.orangeblasters.location.Location;
import edu.gatech.orangeblasters.location.LocationService;

public class DonationServiceFirebaseImpl implements DonationService {

    private static final String DONATIONS = "donations";
    private static final String IDS = "ids";

    private final OrangeBlastersApplication orangeBlastersApplication = OrangeBlastersApplication.getInstance();
    private final LocationService locationService = orangeBlastersApplication.getLocationService();
    private final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private final DatabaseReference databaseReference = firebaseDatabase.getReference(DONATIONS);

    //Locations are stored locally to allow me to not have to rewrite the whole
    private final Map<String, Donation> donations = new LinkedHashMap<>();

    private final Random random = new Random();
    private String createId() {
        return random.ints(4).mapToObj(Integer::toHexString).collect(Collectors.joining());
    }

    public DonationServiceFirebaseImpl() {
        databaseReference.child(IDS).addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                DonationDAO donationDAO = dataSnapshot.getValue(DonationDAO.class);
                if (donationDAO != null) {
                    Donation value = donationDAO.toDonation();
                    donations.put(donationDAO.id, value);
                    getLocation(value)
                            .ifPresent( dons -> dons.add(value));
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                DonationDAO donationDAO = dataSnapshot.getValue(DonationDAO.class);
                if (donationDAO != null) {
                    Donation value = donationDAO.toDonation();
                    donations.put(donationDAO.id, value);
                    getLocation(value)
                            .ifPresent( dons -> dons.add(value));
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                DonationDAO donationDAO = dataSnapshot.getValue(DonationDAO.class);
                if (donationDAO != null) {
                    donations.remove(donationDAO.id);
                    getLocation(donationDAO)
                            .ifPresent( dons -> dons.removeIf(don -> don.getId().equals(donationDAO.id)));
                }
            }

            private Optional<List<Donation>> getLocation(DonationDAO donationDAO) {
                return locationService.getLocation(donationDAO.locationId).map(Location::getDonations);
            }

            private Optional<List<Donation>> getLocation(Donation value) {
                return locationService.getLocation(value.getLocationId()).map(Location::getDonations);
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
        return donations.values().stream();
    }

    @Override
    public Donation createDonation(OffsetDateTime timestamp, String locationId, String descShort, String descLong, BigDecimal value, DonationCategory donationCategory, String comments, String pictureId) {
        Donation donation = new Donation(createId(), timestamp, locationId, descShort, descLong, value, donationCategory, comments, pictureId);
        databaseReference.child(IDS).child(donation.getId()).setValue(DonationDAO.fromDonation(donation));
        return donation;
    }
}
