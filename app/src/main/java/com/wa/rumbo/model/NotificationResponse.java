
package com.wa.rumbo.model;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NotificationResponse implements Serializable
{

    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("object")
    @Expose
    private List<NotificationRespList> notificationRespLists = null;


    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<NotificationRespList> getNotificationRespLists() {
        return notificationRespLists;
    }

    public void setNotificationRespLists(List<NotificationRespList> notificationRespLists1) {
        this.notificationRespLists = notificationRespLists1;
    }

}
