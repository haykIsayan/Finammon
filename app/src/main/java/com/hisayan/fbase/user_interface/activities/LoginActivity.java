package com.hisayan.fbase.user_interface.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.hisayan.fbase.authentication.FacebookAuthDelegate;
import com.hisayan.fbase.authentication.FacebookAuthService;
import com.hisayan.fbase.storage.FBDatabaseHelper;
import com.hisayan.fbase.R;
import com.hisayan.fbase.objects.User;


public class LoginActivity extends BaseActivity implements View.OnClickListener, FacebookAuthDelegate {

    //widgets
    private ImageView ivUserPhoto;

    private TextView tvUserDisplayName;

    private LoginButton lbLoginWithFacebook;

    private Button bShowOnlineUsers;

    //variables
    private FacebookAuthService mFacebookAuthService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        connectXML();

        setOperations();

    }


    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser firebaseUser = mFacebookAuthService.getFirebaseAuth().getCurrentUser();

        if (firebaseUser != null) {

            updateUserInterface(new User(
                    firebaseUser.getUid(),
                    firebaseUser.getDisplayName(),
                    firebaseUser.getPhotoUrl().toString(),
                    true));


        }

    }


    @Override
    void connectXML() {

        ivUserPhoto = (ImageView) findViewById(R.id.iv_user_photo_activity_login);

        tvUserDisplayName = (TextView) findViewById(R.id.tv_user_display_name_activity_login);

        lbLoginWithFacebook = (LoginButton) findViewById(R.id.lb_login_with_facebook_activity_login);

        bShowOnlineUsers = (Button) findViewById(R.id.b_show_online_users_activty_login);

    }


    @Override
    void setOperations() {

        FacebookSdk.sdkInitialize(getApplicationContext());

        mFacebookAuthService = new FacebookAuthService(this.lbLoginWithFacebook);

        mFacebookAuthService.setFacebookAuthDelegate(this);

        mFacebookAuthService.login();

        mFacebookAuthService.trackAccessToken();

        bShowOnlineUsers.setOnClickListener(this);

    }


    //Updates the user interface with information on the current user
    private void updateUserInterface(User user){

        Log.d("LoginActivty","updateUserInterface called");

        Glide.with(this).load(Uri.parse(user.getImage())).placeholder(R.drawable.profile_placeholder).into(ivUserPhoto);

        tvUserDisplayName.setText(user.getDisplayName());

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        mFacebookAuthService.getCallbackManager().onActivityResult(requestCode, resultCode, data);

    }


    @Override
    int getLayoutResource() {
        return R.layout.activity_login;
    }


    @Override
    public void onClick(View view) {

        Intent intent = new Intent(this, UserListActivity.class);

        startActivity(intent);

    }


    //implementing DacebookAuthService
    @Override
    public void onAuthComplete(User user) {

        updateUserInterface(user);

    }

    @Override
    public void onAuthFailed() {

        Toast.makeText(this,getResources().getText(R.string.authentication_failed_messege),Toast.LENGTH_LONG).show();

    }

    @Override
    public void onLogout() {

        updateUserInterface(new User());

    }

}
