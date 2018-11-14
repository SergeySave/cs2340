package edu.gatech.orangeblasters.bitmap.impl;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import edu.gatech.orangeblasters.bitmap.BitmapCallback;
import edu.gatech.orangeblasters.bitmap.BitmapService;

public class BitmapFirebaseService implements BitmapService {

    private static final String BITMAPS = "bitmaps";
    private static final String IDS = "ids";
    public static final int QUALITY = 75;

    private final Random random = new Random();

    private final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private final DatabaseReference databaseReference = firebaseDatabase.getReference(BITMAPS);

    private String createId() {
        return random.ints(4).mapToObj(Integer::toHexString).collect(Collectors.joining());
    }

    @Override
    public String addBitmap(Bitmap bitmap) {
        String id = createId();

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, QUALITY, byteArrayOutputStream);

        String encoded = Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray());
        databaseReference.child(IDS).child(id).setValue(encoded);
        bitmap.recycle();

        return id;
    }

    @Override
    public void getBitmap(String id, BitmapCallback callback) {
        databaseReference.child(IDS).child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String encoded = dataSnapshot.getValue(String.class);
                if (encoded != null) {
                    byte[] decoded = Base64.getDecoder().decode(encoded);
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    Bitmap bitmap = BitmapFactory.decodeByteArray(decoded, 0, decoded.length, options);
                    callback.onComplete(Optional.ofNullable(bitmap));
                } else {
                    callback.onComplete(Optional.empty());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });
    }
}
