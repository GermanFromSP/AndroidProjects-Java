package com.example.messenger.forgot_password;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordViewModel extends ViewModel {
    private FirebaseAuth auth;
    public ForgotPasswordViewModel() {
        super();
        auth = FirebaseAuth.getInstance();
    }
    private MutableLiveData<String> error = new MutableLiveData<>();
    private MutableLiveData<Boolean> isDone = new MutableLiveData<>();

    public LiveData<String> getError() {
        return error;
    }

    public LiveData<Boolean> getIsDone() {
        return isDone;
    }
    public void sendLinkForNewPassword(String email){
        auth.sendPasswordResetEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                isDone.setValue(true);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                error.setValue(e.getMessage());
                isDone.setValue(false);
            }
        });
    }
}
