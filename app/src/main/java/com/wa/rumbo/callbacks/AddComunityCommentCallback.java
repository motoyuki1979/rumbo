package com.wa.rumbo.callbacks;

import com.wa.rumbo.model.CommentPostModel;
import com.wa.rumbo.model.GetCommentPost;
import com.wa.rumbo.model.GetComunityComents;
import com.wa.rumbo.model.Status_Model;

public interface AddComunityCommentCallback {
    void onResponse(CommentPostModel model);

}
