package edu.gatech.orangeblasters.donation.impl;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import edu.gatech.orangeblasters.OrangeBlastersApplication;
import edu.gatech.orangeblasters.donation.Donation;
import edu.gatech.orangeblasters.donation.DonationCategory;
import edu.gatech.orangeblasters.donation.DonationService;

public class DonationServiceFirebaseImpl implements DonationService {

    private static final String DONATIONS = "donations";
    private static final String IDS = "ids";

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference(DONATIONS);

    //Locations are stored locally to allow me to not have to rewrite the whole
    private Map<String, Donation> donations = new LinkedHashMap<>();

    private Random random = new Random();
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
                    OrangeBlastersApplication.getInstance().getLocationService().getLocation(value.getLocationId())
                            .ifPresent( loc -> loc.getDonations().add(value));
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                DonationDAO donationDAO = dataSnapshot.getValue(DonationDAO.class);
                if (donationDAO != null) {
                    Donation value = donationDAO.toDonation();
                    donations.put(donationDAO.id, value);
                    OrangeBlastersApplication.getInstance().getLocationService().getLocation(value.getLocationId())
                            .ifPresent( loc -> loc.getDonations().add(value));
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                DonationDAO donationDAO = dataSnapshot.getValue(DonationDAO.class);
                if (donationDAO != null) {
                    donations.remove(donationDAO.id);
                    OrangeBlastersApplication.getInstance().getLocationService().getLocation(donationDAO.locationId)
                            .ifPresent( loc -> loc.getDonations().removeIf(don -> don.getId().equals(donationDAO.id)));
                }
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
    public Donation createDonation(OffsetDateTime timestamp, String locationId, String descShort, String descLong, BigDecimal value, DonationCategory donationCategory, String comments, String pictureId) {
        Donation donation = new Donation(createId(), timestamp, locationId, descShort, descLong, value, donationCategory, comments, pictureId);
        databaseReference.child(IDS).child(donation.getId()).setValue(DonationDAO.fromDonation(donation));
        return donation;
    }
}
