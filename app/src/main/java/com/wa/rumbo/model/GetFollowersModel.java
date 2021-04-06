package com.wa.rumbo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GetFollowersModel {
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

        @SerializedName("user_id")
        @Expose
        private String userId;
        @SerializedName("follower_id")
        @Expose
        private String followerId;
        @SerializedName("follower_username")
        @Expose
        private String followerUsername;
        @SerializedName("follower_image")
        @Expose
        private String followerImage;
        @SerializedName("follower_info")
        @Expose
        private String followerInfo;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getFollowerInfo() {
            return followerInfo;
        }

        public void setFollowerInfo(String followerInfo) {
            this.followerInfo = followerInfo;
        }

        public String getFollowerId() {
            return followerId;
        }

        public void setFollowerId(String followerId) {
            this.followerId = followerId;
        }

        public String getFollowerUsername() {
            return followerUsername;
        }

        public void setFollowerUsername(String followerUsername) {
            this.followerUsername = followerUsername;
        }

        public String getFollowerImage() {
            return followerImage;
        }

        public void setFollowerImage(String followerImage) {
            this.followerImage = followerImage;
        }

    }
}
