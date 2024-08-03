package com.example.messenger.userlist;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UsersActivityViewModel extends ViewModel {

    private static final String FIREBASE_REFERENCE = "Users";
    private static final String REF_DB_CHILD_PATH = "online";
    private FirebaseAuth auth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference refDB;
    private MutableLiveData<FirebaseUser> user = new MutableLiveData<>();

    public LiveData<List<User>> getListOfUsers() {
        return listOfUsers;
    }

    private MutableLiveData<List<User>> listOfUsers = new MutableLiveData<>();

    public LiveData<FirebaseUser> getUser() {
        return user;
    }

    public UsersActivityViewModel() {
        super();

        auth = FirebaseAuth.getInstance();
        auth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth auth) {
                if (auth.getCurrentUser() == null) {
                    user.setValue(auth.getCurrentUser());
                }
            }
        });
        firebaseDatabase = FirebaseDatabase.getInstance();
        refDB = FirebaseDatabase.getInstance().getReference(FIREBASE_REFERENCE);
        refDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                FirebaseUser currentUser = auth.getCurrentUser();
                if (currentUser == null) {
                    return;
                }
                List<User> userList = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    User user = dataSnapshot.getValue(User.class);
                    if (user == null) {
                        return;
                    }
                    if (user.getId() != null && currentUser != null) {
                        if (!user.getId().equals(currentUser.getUid())) {
                            userList.add(user);
                        }
                    }
                }
                listOfUsers.setValue(userList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void setUserOnline(boolean isOnline) {
        FirebaseUser firebaseUser = auth.getCurrentUser();
        if (firebaseUser == null) {
            return;
        }

        refDB.child(firebaseUser.getUid()).child(REF_DB_CHILD_PATH).setValue(isOnline);
    }

    public void logOut() {
        setUserOnline(false);
        auth.signOut();
    }

}
