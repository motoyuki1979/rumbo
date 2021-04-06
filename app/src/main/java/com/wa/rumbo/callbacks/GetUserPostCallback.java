package com.wa.rumbo.callbacks;

import com.wa.rumbo.model.GetAllPost;
import com.wa.rumbo.model.UserPostModel;

public interface GetUserPostCallback {
    void  onResponse(UserPostModel model);
    void  onFailutre();
}
