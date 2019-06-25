
package com.wa.rumbo.model;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User implements Serializable
{

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
    private final static long serialVersionUID = -208708044910418091L;

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

}
