package edu.gatech.orangeblasters.account.impl;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.nio.charset.Charset;
import java.util.Base64;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import edu.gatech.orangeblasters.account.Account;
import edu.gatech.orangeblasters.account.AccountCallback;
import edu.gatech.orangeblasters.account.AccountService;
import edu.gatech.orangeblasters.account.AccountState;
import edu.gatech.orangeblasters.account.Admin;
import edu.gatech.orangeblasters.account.LocationEmployee;
import edu.gatech.orangeblasters.account.Manager;
import edu.gatech.orangeblasters.account.User;

public class AccountServiceFirebaseImpl implements AccountService {

    private static final String USERS = "users";
    private static final String EMAILS = "emails";
    private static final String IDS = "ids";

    private final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private final DatabaseReference databaseReference = firebaseDatabase.getReference(USERS);
    private final Random random = new Random();

    /**
     * Creates the account ID
     *
     * @return the account ID
     */
    private String createId() {
        IntStream ints = random.ints(4);
        Stream<String> hexs = ints.mapToObj(Integer::toHexString);
        return hexs.collect(Collectors.joining());
    }

    /**
     * Puts the input into a 64bit format
     *
     * @param input the account to be converted
     * @return the account in a 64 format
     */
    private String to64(String input) {
        Base64.Encoder encoder = Base64.getEncoder();
        return encoder.encodeToString(input.getBytes(Charset.forName("UTF-8")));
    }

// --Commented out by Inspection START (11/7/18, 2:37 PM):
//    private String from64(String input) {
//        return new String(Base64.getDecoder().decode(input), Charset.forName("UTF-8"));
//    }
// --Commented out by Inspection STOP (11/7/18, 2:37 PM)

    @Override
    public void tryLogin(String email, String password, AccountCallback<Account> callback) {
        DatabaseReference emails = databaseReference.child(EMAILS);
        DatabaseReference emailRef = emails.child(to64(email));
        emailRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String userID = dataSnapshot.getValue(String.class);

                if (userID == null) {
                    callback.onComplete(Optional.empty());
                    return;
                }

                DatabaseReference ids = databaseReference.child(IDS);
                DatabaseReference idRef = ids.child(userID);
                idRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        AccountDAO accountDAO = dataSnapshot.getValue(AccountDAO.class);

                        if (accountDAO != null) {
                            String pass = accountDAO.getPassword();
                            if (pass.equals(password)) {
                                callback.onComplete(Optional.ofNullable(accountDAO.toAccount()));
                            } else {
                                callback.onComplete(Optional.empty());
                            }
                        } else {
                            callback.onComplete(Optional.empty());
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        callback.onComplete(Optional.empty());
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                callback.onComplete(Optional.empty());
            }
        });
    }

    @Override
    public void getAccount(String id, AccountCallback<Account> callback) {
        DatabaseReference ids = databaseReference.child(IDS);
        DatabaseReference idRef = ids.child(id);
        idRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                AccountDAO accountDAO = dataSnapshot.getValue(AccountDAO.class);

                if (accountDAO != null) {
                    callback.onComplete(Optional.ofNullable(accountDAO.toAccount()));
                } else {
                    callback.onComplete(Optional.empty());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }

    @Override
    public void createUser(String name, String email, String password,
                           AccountCallback<? super User> callback) {
        User user = new User(createId(), name, email, password, AccountState.NORMAL);

        DatabaseReference emails = databaseReference.child(EMAILS);
        DatabaseReference emailRef = emails.child(to64(email));
        emailRef.setValue(user.getId());
        DatabaseReference ids = databaseReference.child(IDS);
        DatabaseReference idRef = ids.child(user.getId());
        idRef.setValue(AccountDAO.fromUser(user));

        callback.onComplete(Optional.of(user));
    }

    @Override
    public void createAdmin(String name, String email, String password,
                            AccountCallback<? super Admin> callback) {
        Admin admin = new Admin(createId(), name, email, password, AccountState.NORMAL);

        DatabaseReference emails = databaseReference.child(EMAILS);
        DatabaseReference emailRef = emails.child(to64(email));
        emailRef.setValue(admin.getId());
        DatabaseReference ids = databaseReference.child(IDS);
        DatabaseReference idRef = ids.child(admin.getId());
        idRef.setValue(AccountDAO.fromAdmin(admin));

        callback.onComplete(Optional.of(admin));
    }

    @Override
    public void createManager(String name, String email, String password,
                              AccountCallback<? super Manager> callback) {
        Manager manager = new Manager(createId(), name, email, password, AccountState.NORMAL);

        DatabaseReference emails = databaseReference.child(EMAILS);
        DatabaseReference emailRef = emails.child(to64(email));
        emailRef.setValue(manager.getId());
        DatabaseReference ids = databaseReference.child(IDS);
        DatabaseReference idRef = ids.child(manager.getId());
        idRef.setValue(AccountDAO.fromManager(manager));

        callback.onComplete(Optional.of(manager));
    }

    @Override
    public void createLocationEmployee(String name, String email, String password,
                                       String locationId,
                                       AccountCallback<? super LocationEmployee> callback) {
        LocationEmployee employee = new LocationEmployee(createId(), name, email, password,
                AccountState.NORMAL, locationId);

        DatabaseReference emails = databaseReference.child(EMAILS);
        DatabaseReference emailRef = emails.child(to64(email));
        emailRef.setValue(employee.getId());
        DatabaseReference ids = databaseReference.child(IDS);
        DatabaseReference idRef = ids.child(employee.getId());
        idRef.setValue(AccountDAO.fromLocationEmployee(employee));

        callback.onComplete(Optional.of(employee));
    }
}
