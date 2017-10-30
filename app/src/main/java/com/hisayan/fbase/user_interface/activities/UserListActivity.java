package com.hisayan.fbase.user_interface.activities;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.facebook.GraphRequest;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.hisayan.fbase.R;
import com.hisayan.fbase.objects.User;
import com.hisayan.fbase.storage.FBDatabaseHelper;
import com.hisayan.fbase.user_interface.adapters.UserListAdapter;

import java.time.LocalDate;
import java.util.ArrayList;

public class UserListActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener{


    //widgets
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;


    //variables
    private FirebaseAuth mFirebaseAuth;

    private FBDatabaseHelper mFBDatabaseHelper;

    private UserListAdapter mUserListAdapter;

    private ArrayList<User> userList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        connectXML();

        setOperations();

    }


    @Override
    void connectXML() {

       // mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout_activity_user_list);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_activity_user_list);

    }


    @Override
    void setOperations() {

        mFBDatabaseHelper = new FBDatabaseHelper();

        userList = new ArrayList<>();

        mFBDatabaseHelper.getDatabaseReference()
                .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot userSnapshot : dataSnapshot.getChildren()){

                    userList.add(userSnapshot.getValue(User.class));

                    Log.d("User",userSnapshot.getValue(User.class).getDisplayName());

                }

                buildRecyclerView();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

    }


    //builds recycler view
    private void buildRecyclerView(){

        mUserListAdapter = new UserListAdapter(this,userList);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        mRecyclerView.setAdapter(mUserListAdapter);

    }


    @Override
    int getLayoutResource() {
        return R.layout.activity_user_list;
    }


    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(false);

        userList.clear();

    }
}
