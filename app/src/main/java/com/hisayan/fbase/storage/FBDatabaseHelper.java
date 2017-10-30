package com.hisayan.fbase.storage;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hisayan.fbase.objects.User;

import java.util.ArrayList;

/**
 * Created by hisayan on 10/29/17.
 */

public class FBDatabaseHelper {

    //fields
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    private ArrayList<User> users;

    //constructor
    public FBDatabaseHelper() {

        firebaseDatabase = FirebaseDatabase.getInstance();

        databaseReference = firebaseDatabase.getReference();

        users = new ArrayList<>();

    }


    public void addUser(User user){

        String id = databaseReference.push().getKey();

        user.setId(id);

        databaseReference.child(id).setValue(user);

    }


    public DatabaseReference getDatabaseReference() {
        return databaseReference;
    }

}
