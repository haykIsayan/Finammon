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
import com.hisayan.fbase.authentication.FacebookAuthService;
import com.hisayan.fbase.objects.User;
import com.hisayan.fbase.storage.FBDatabaseHelper;
import com.hisayan.fbase.user_interface.adapters.UserListAdapter;

import java.time.LocalDate;
import java.util.ArrayList;

public class UserListActivity extends BaseActivity implements FBDatabaseHelper.FBDatabaseDelegate{


    //widgets
    private RecyclerView mRecyclerView;

    //variables
    private FBDatabaseHelper mFBDatabaseHelper;
    private UserListAdapter mUserListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        connectXML();

        setOperations();

    }


    @Override
    void connectXML() {

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_activity_user_list);

    }


    @Override
    void setOperations() {

        mFBDatabaseHelper = new FBDatabaseHelper();

        mFBDatabaseHelper.setFbDatabaseDelegate(this);

        mFBDatabaseHelper.getOnlineUsers();

    }


    //builds recycler view
    private void buildRecyclerView(ArrayList<User> users){

        mUserListAdapter = new UserListAdapter(this,users);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        mRecyclerView.setAdapter(mUserListAdapter);

    }


    @Override
    int getLayoutResource() {
        return R.layout.activity_user_list;
    }


    @Override
    public void getOnlineUsers(ArrayList<User> users) {

        buildRecyclerView(users);

    }
}
