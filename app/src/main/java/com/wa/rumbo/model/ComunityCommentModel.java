package com.wa.rumbo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ComunityCommentModel {
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("object")
    @Expose
    private ArrayList<Object> object = null;
    @SerializedName("success")
    @Expose
    private String success;

    public ArrayList<Object> getObject() {
        return object;
    }

    public void setObject(ArrayList<Object> object) {
        this.object = object;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }


    public class Object {

        @SerializedName("category_id")
        @Expose
        private String categoryId;
        @SerializedName("comment")
        @Expose
        private String comment;
        @SerializedName("comment_id")
        @Expose
        private String commentId;
        @SerializedName("comment_user_id")
        @Expose
        private String commentUserId;
        @SerializedName("date")
        @Expose
        private String date;
        @SerializedName("is_like")
        @Expose
        private String isLike;
        @SerializedName("likes_count")
        @Expose
        private String likesCount;
        @SerializedName("user_image")
        @Expose
        private String userImage;
        @SerializedName("user_name")
        @Expose
        private String userName;

        public String getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(String categoryId) {
            this.categoryId = categoryId;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public String getCommentId() {
            return commentId;
        }

        public void setCommentId(String commentId) {
            this.commentId = commentId;
        }

        public String getCommentUserId() {
            return commentUserId;
        }

        public void setCommentUserId(String commentUserId) {
            this.commentUserId = commentUserId;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getIsLike() {
            return isLike;
        }

        public void setIsLike(String isLike) {
            this.isLike = isLike;
        }

        public String getLikesCount() {
            return likesCount;
        }

        public void setLikesCount(String likesCount) {
            this.likesCount = likesCount;
        }

        public String getUserImage() {
            return userImage;
        }

        public void setUserImage(String userImage) {
            this.userImage = userImage;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

    }

}