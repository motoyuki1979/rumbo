package com.wa.rumbo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GetBlockedListModel {
    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("object")
    @Expose
    private ArrayList<Object> object = null;

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

    public ArrayList<Object> getObject() {
        return object;
    }

    public void setObject(ArrayList<Object> object) {
        this.object = object;
    }

    public class Object {

        @SerializedName("blocked_user_id")
        @Expose
        private String blockedUserId;
        @SerializedName("user_id")
        @Expose
        private String userId;

        public String getBlockedUserId() {
            return blockedUserId;
        }

        public void setBlockedUserId(String blockedUserId) {
            this.blockedUserId = blockedUserId;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

    }
}
