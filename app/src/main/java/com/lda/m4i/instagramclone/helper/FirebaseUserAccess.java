//
// Author: Bruno Lima
// Company: M4I
// 31/08/2021 at 16:33
//

package com.lda.m4i.instagramclone.helper;

import android.net.Uri;
import android.util.Log;
import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.lda.m4i.instagramclone.config.FirebaseConfiguration;
import com.lda.m4i.instagramclone.model.User;

public class FirebaseUserAccess {

    public static String getUserId() {
        FirebaseAuth user = FirebaseConfiguration.getFirebaseAuth();
        String email = user.getCurrentUser().getEmail();
        return Base64Custom.encodeBase64(email);
    }

    public static FirebaseUser getActualUser() {
        FirebaseAuth user = FirebaseConfiguration.getFirebaseAuth();
        return user.getCurrentUser();
    }

    public static boolean updateUserPhoto(Uri url) {
        try {
            FirebaseUser user = getActualUser();
            UserProfileChangeRequest profile = new UserProfileChangeRequest.Builder()
                    .setPhotoUri(url)
                    .build();
            user.updateProfile(profile).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (!task.isSuccessful()) {
                        Log.d("Profile", "Error on updating the profile image.");
                    }
                }
            });
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    public static boolean updateUserName(String name) {
        try {
            FirebaseUser user = getActualUser();
            UserProfileChangeRequest profile = new UserProfileChangeRequest.Builder()
                    .setDisplayName(name)
                    .build();
            user.updateProfile(profile).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (!task.isSuccessful()) {
                        Log.d("Profile", "Error on updating the profile name.");
                    }
                }
            });
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    public static User getDataFromLoggedUser() {
        FirebaseUser firebaseUser = getActualUser();
        User user = new User();
        user.setEmail(firebaseUser.getEmail());
        user.setName(firebaseUser.getDisplayName());

        if (firebaseUser.getPhotoUrl() == null) {
            user.setPhoto("");
        } else {
            user.setPhoto(firebaseUser.getPhotoUrl().toString());
        }
        return user;
    }
}