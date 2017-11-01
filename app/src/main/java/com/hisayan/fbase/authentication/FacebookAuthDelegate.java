package com.hisayan.fbase.authentication;

import com.google.firebase.auth.FirebaseUser;
import com.hisayan.fbase.objects.User;

/**
 * Created by hisayan on 10/30/17.
 */

public interface FacebookAuthDelegate {

    void onAuthComplete(User user);

    void onAuthFailed();

    void onLogout();

}
