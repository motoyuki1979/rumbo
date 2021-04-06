package com.wa.rumbo.callbacks;

import com.wa.rumbo.model.GetUserProfileModel;

import okhttp3.ResponseBody;

public interface GetUserProfileCallback {

    void onResponse(GetUserProfileModel model);
}
