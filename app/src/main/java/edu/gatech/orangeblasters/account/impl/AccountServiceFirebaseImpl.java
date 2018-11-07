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

    private String createId() {
        return random.ints(4).mapToObj(Integer::toHexString).collect(Collectors.joining());
    }

    private String to64(String input) {
        return Base64.getEncoder().encodeToString(input.getBytes(Charset.forName("UTF-8")));
    }

// --Commented out by Inspection START (11/7/18, 2:37 PM):
//    private String from64(String input) {
//        return new String(Base64.getDecoder().decode(input), Charset.forName("UTF-8"));
//    }
// --Commented out by Inspection STOP (11/7/18, 2:37 PM)

    @Override
    public void tryLogin(String email, String password, AccountCallback<Account> callback) {
        databaseReference.child(EMAILS).child(to64(email)).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String userID = dataSnapshot.getValue(String.class);

                if (userID == null) {
                    callback.onComplete(Optional.empty());
                    return;
                }

                databaseReference.child(IDS).child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        AccountDAO accountDAO = dataSnapshot.getValue(AccountDAO.class);

                        if (accountDAO != null && accountDAO.password.equals(password)) {
                            callback.onComplete(Optional.ofNullable(accountDAO.toAccount()));
                        } else {
                            callback.onComplete(Optional.empty());
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) { callback.onComplete(Optional.empty()); }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { callback.onComplete(Optional.empty()); }
        });
    }

    @Override
    public void getAccount(String id, AccountCallback<Account> callback) {
        databaseReference.child(IDS).child(id).addListenerForSingleValueEvent(new ValueEventListener() {
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
    public void createUser(String name, String email, String password, AccountCallback<? super User> callback) {
        User user = new User(createId(), name, email, password, AccountState.NORMAL);

        databaseReference.child(EMAILS).child(to64(email)).setValue(user.getId());
        databaseReference.child(IDS).child(user.getId()).setValue(AccountDAO.fromUser(user));

        callback.onComplete(Optional.of(user));
    }

    @Override
    public void createAdmin(String name, String email, String password, AccountCallback<? super Admin> callback) {
        Admin admin = new Admin(createId(), name, email, password, AccountState.NORMAL);

        databaseReference.child(EMAILS).child(to64(email)).setValue(admin.getId());
        databaseReference.child(IDS).child(admin.getId()).setValue(AccountDAO.fromAdmin(admin));

        callback.onComplete(Optional.of(admin));
    }

    @Override
    public void createManager(String name, String email, String password, AccountCallback<? super Manager> callback) {
        Manager manager = new Manager(createId(), name, email, password, AccountState.NORMAL);

        databaseReference.child(EMAILS).child(to64(email)).setValue(manager.getId());
        databaseReference.child(IDS).child(manager.getId()).setValue(AccountDAO.fromManager(manager));

        callback.onComplete(Optional.of(manager));
    }

    @Override
    public void createLocationEmployee(String name, String email, String password, String locationId, AccountCallback<? super LocationEmployee> callback) {
        LocationEmployee employee = new LocationEmployee(createId(), name, email, password, AccountState.NORMAL, locationId);

        databaseReference.child(EMAILS).child(to64(email)).setValue(employee.getId());
        databaseReference.child(IDS).child(employee.getId()).setValue(AccountDAO.fromLocationEmployee(employee));

        callback.onComplete(Optional.of(employee));
    }
}
