package com.wa.rumbo.callbacks;

import com.wa.rumbo.model.GetCalenderBookingModel;

public interface GetCalenderBookingCalback {
    void onRespose(GetCalenderBookingModel model);
    void onFailure();
}
