package com.wa.rumbo.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class GetCalenderBookingModel implements Serializable{
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

    public static class Object implements Serializable {

        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("category_title")
        @Expose
        private String categoryTitle;
        @SerializedName("category_image")
        @Expose
        private String categoryImage;
        @SerializedName("amount")
        @Expose
        private String amount;
        @SerializedName("comment")
        @Expose
        private String comment;
        @SerializedName("date")
        @Expose
        private String date;
        @SerializedName("user_id")
        @Expose
        private String userId;
        @SerializedName("post_category")
        @Expose
        private String post_category;

        public String getPost_category() {
            return post_category;
        }

        public void setPost_category(String post_category) {
            this.post_category = post_category;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCategoryTitle() {
            return categoryTitle;
        }

        public void setCategoryTitle(String categoryTitle) {
            this.categoryTitle = categoryTitle;
        }

        public String getCategoryImage() {
            return categoryImage;
        }

        public void setCategoryImage(String categoryImage) {
            this.categoryImage = categoryImage;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

    }
}
