package com.wa.rumbo.callbacks;

import com.wa.rumbo.model.GetFollowersModel;

public interface GetFollowersCallback {

    void onResponse(GetFollowersModel model);
    void  onFailure();
}
