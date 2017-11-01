package com.hisayan.fbase.user_interface.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.facebook.FacebookSdk;

public abstract class BaseActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResource());
    }


    //setup a connection with the resource layout xml file
    abstract void connectXML();


    //setup the main logic of the activity
    abstract void setOperations();


    abstract int getLayoutResource();

}
