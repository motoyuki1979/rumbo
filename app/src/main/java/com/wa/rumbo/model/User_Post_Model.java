package com.wa.rumbo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class User_Post_Model {

    @SerializedName("success")
    @Expose
    private  String success;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("user")
    @Expose
    private User user_detail;


    @SerializedName("user_posts")
    @Expose
    private List<GetAllPost_Data> getAllPostDataList;


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


    public User getUser_detail() {
        return user_detail;
    }

    public void setUser_detail(User user_detail) {
        this.user_detail = user_detail;
    }

    public List<GetAllPost_Data> getGetAllPostDataList() {
        return getAllPostDataList;
    }

    public void setGetAllPostDataList(List<GetAllPost_Data> getAllPostDataList) {
        this.getAllPostDataList = getAllPostDataList;
    }


}
