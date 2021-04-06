package com.wa.rumbo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetUserProfileModel {
    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("object")
    @Expose
    private List<UserDetails> userDetails = null;

    public List<UserDetails> getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(List<UserDetails> userDetails) {
        this.userDetails = userDetails;
    }


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


    public static class UserDetails {

        @SerializedName("user_id")
        @Expose
        private String userId;
        @SerializedName("device_id")
        @Expose
        private java.lang.Object deviceId;
        @SerializedName("user_name")
        @Expose
        private String userName;
        @SerializedName("address")
        @Expose
        private String address;
        @SerializedName("token")
        @Expose
        private String token;
        @SerializedName("device_token")
        @Expose
        private java.lang.Object deviceToken;
        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("password")
        @Expose
        private String password;

        @SerializedName("introdunction")
        @Expose
        private String introduction;

        @SerializedName("following_count")
        @Expose
        private String following_count;

        @SerializedName("follower_count")
        @Expose
        private String follower_count;

        public String getFollowing_count() {
            return following_count;
        }

        public void setFollowing_count(String following_count) {
            this.following_count = following_count;
        }

        public String getFollower_count() {
            return follower_count;
        }

        public void setFollower_count(String follower_count) {
            this.follower_count = follower_count;
        }

        public String getIntroduction() {
            return introduction;
        }

        public void setIntroduction(String introduction) {
            this.introduction = introduction;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public java.lang.Object getDeviceId() {
            return deviceId;
        }

        public void setDeviceId(java.lang.Object deviceId) {
            this.deviceId = deviceId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public java.lang.Object getDeviceToken() {
            return deviceToken;
        }

        public void setDeviceToken(java.lang.Object deviceToken) {
            this.deviceToken = deviceToken;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

}