package com.wa.rumbo.firebase;

import android.util.Log;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.wa.rumbo.common.CommonData;

import static com.wa.rumbo.common.ConstantValue.FIREBASE_TOKEN;


public class MyfirebaseInstanceIDService extends FirebaseInstanceIdService {

    CommonData commonData;

    @Override
    public void onTokenRefresh() {

        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.e("InstanceIDService", "Refreshed token: " + refreshedToken);

        sendRegistrationToServer(refreshedToken);
        storeRegIdInPref(refreshedToken);

    }

    private void storeRegIdInPref(String refreshedToken) {
        commonData = new CommonData(MyfirebaseInstanceIDService.this);
        commonData.save(FIREBASE_TOKEN, refreshedToken);

        Log.e("InstanceIDService", "Save token: " + refreshedToken);
    }

    private void sendRegistrationToServer(final String token) {
        // sending gcm token to server
        Log.e("InstanceIDService", "sendRegistrationToServer: " + token);
    }

}
