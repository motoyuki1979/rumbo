package com.wa.rumbo.callbacks;

import com.wa.rumbo.model.GetBlockedListModel;

public interface GetBlockedUserCallback {
   void onSuccess(GetBlockedListModel model);
   void onFailure();
}
