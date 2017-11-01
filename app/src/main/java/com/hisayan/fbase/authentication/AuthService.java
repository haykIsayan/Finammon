package com.hisayan.fbase.authentication;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by hisayan on 10/30/17.
 */

public abstract class AuthService {

    protected FirebaseAuth firebaseAuth;

    protected FirebaseUser firebaseUser;

    public AuthService() {

        this.firebaseAuth = FirebaseAuth.getInstance();

        this.firebaseUser = firebaseAuth.getCurrentUser();

    }

    public abstract void login();

    public abstract void register();

    void logout() { this.firebaseAuth.signOut(); }

}
