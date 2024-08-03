package com.example.messenger.registration;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.messenger.userlist.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationViewModel extends AndroidViewModel {
    private static final String FIREBASE_REFERENCE = "Users";
    private FirebaseAuth auth;
    private MutableLiveData<FirebaseUser> resultOfRegistration = new MutableLiveData<>();
    private MutableLiveData<String> errorText = new MutableLiveData<>();
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference refDB;

    public LiveData<String> getErrorText() {
        return errorText;
    }

    public LiveData<FirebaseUser> getResultOfRegistration() {
        return resultOfRegistration;
    }

    public RegistrationViewModel(@NonNull Application application) {
        super(application);
        auth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        refDB = FirebaseDatabase.getInstance().getReference(FIREBASE_REFERENCE);
    }

    public void userRegistration(
            String email,
            String password,
            String name,
            String lastNam,
            int age
    ) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        resultOfRegistration.setValue(authResult.getUser());
                        FirebaseUser firebaseUser = authResult.getUser();
                        if (firebaseUser == null) {
                            return;
                        } else {
                            User user = new User(
                                    firebaseUser.getUid(),
                                    name,
                                    lastNam,
                                    age,
                                    false
                            );
                            refDB.child(user.getId()).setValue(user);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        errorText.setValue(e.getMessage());
                    }
                });
    }
}
