package com.hisayan.fbase.user_interface.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import com.hisayan.fbase.storage.FBDatabaseHelper;
import com.hisayan.fbase.R;
import com.hisayan.fbase.objects.User;


public class LoginActivity extends BaseActivity {

    //widgets
    private TextView textView;

    private ImageView imageView;

    private LoginButton lbLoginWithFacebook;

    //variables
    private FirebaseAuth mFireBaseAuth;
    private CallbackManager mCallbackManager;

    private FBDatabaseHelper mFBDatabaseHelper;


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

        FirebaseUser firebaseUser = mFireBaseAuth.getCurrentUser();

        if (firebaseUser != null)

            textView.setText(firebaseUser.getDisplayName());

        else
            textView.setText(null);
        
    }


    //setup a connection with the resource layout xml file
    @Override
    void connectXML() {

        textView = (TextView) findViewById(R.id.textview);

        imageView = (ImageView) findViewById(R.id.imageview_bitch);

        lbLoginWithFacebook = (LoginButton) findViewById(R.id.lb_login_with_facebook_activity_login);

    }


    //setup the main logic of the activity
    @Override
    void setOperations() {

        FacebookSdk.sdkInitialize(getApplicationContext());

        mFBDatabaseHelper = new FBDatabaseHelper();

        mFireBaseAuth = FirebaseAuth.getInstance();

        mCallbackManager = CallbackManager.Factory.create();

        loginWithFacebook();

    }


    //setup the necessary conditions for a facebook login
    private void loginWithFacebook(){

        lbLoginWithFacebook.setReadPermissions("email","public_profile");

        lbLoginWithFacebook.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                authenticateUser(loginResult.getAccessToken());

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

                Toast.makeText(LoginActivity.this, getResources().getText(R.string.login_failed_messege), Toast.LENGTH_LONG).show();

            }

        });

    }


    //login to Firebase with the credentials obtained from the facebook login
    private void authenticateUser(AccessToken accessToken){

        AuthCredential authCredential = FacebookAuthProvider.getCredential(accessToken.getToken());

        mFireBaseAuth.signInWithCredential(authCredential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {

                    FirebaseUser firebaseUser = mFireBaseAuth.getCurrentUser();

                    User user = new User();

                    user.setDisplayName(firebaseUser.getDisplayName());
                    user.setImage(firebaseUser.getPhotoUrl().toString());

                    mFBDatabaseHelper.addUser(user);

                    Intent intent = new Intent(LoginActivity.this,UserListActivity.class);

                    startActivity(intent);


                } else {
                    // If sign in fails, display a message to the user.

                    Toast.makeText(LoginActivity.this, getResources().getText(R.string.authentication_failed_messege), Toast.LENGTH_LONG).show();
                }

            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    int getLayoutResource() {
        return R.layout.activity_login;
    }

}
