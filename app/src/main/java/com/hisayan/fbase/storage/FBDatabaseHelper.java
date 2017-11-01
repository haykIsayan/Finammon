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
    private FBDatabaseDelegate fbDatabaseDelegate;


    //constructor
    public FBDatabaseHelper() {

        firebaseDatabase = FirebaseDatabase.getInstance();

        databaseReference = firebaseDatabase.getReference();

    }


    //adds the provided user to the realtime database
    public void addUser(User user){

        databaseReference.child(user.getId()).setValue(user);

    }


    //identifies and sends all the online users using FBDatabase delegate
    public void getOnlineUsers(){

        final ArrayList<User> users = new ArrayList<>();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot onlineUserSnapshot : dataSnapshot.getChildren()){

                    User user = onlineUserSnapshot.getValue(User.class);

                    if (user.isOnline())

                        users.add(onlineUserSnapshot.getValue(User.class));

                    fbDatabaseDelegate.getOnlineUsers(users);

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

    }


    //updates the user with the provided id
    public void updateUser(final User user){

        Log.d("FBDatabaseHelper","updateUser called");

        databaseReference.child(user.getId()).setValue(user);

    }


    //delegate
    public interface FBDatabaseDelegate{

         void getOnlineUsers(ArrayList<User> users);

    }


    //setter
    public void setFbDatabaseDelegate(FBDatabaseDelegate fbDatabaseDelegate) {
        this.fbDatabaseDelegate = fbDatabaseDelegate;
    }
}
