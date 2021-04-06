package com.wa.rumbo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GetComunityCommentModel {
    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("object")
    @Expose
    private ArrayList<CommentDetails> object = null;

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

    public ArrayList<CommentDetails> getObject() {
        return object;
    }

    public void setObject(ArrayList<CommentDetails> object) {
        this.object = object;
    }

    public class CommentDetails {

        @SerializedName("comment_id")
        @Expose
        private String commentId;
        @SerializedName("comment_user_id")
        @Expose
        private String commentUserId;
        @SerializedName("category_id")
        @Expose
        private String categoryId;
        @SerializedName("comment")
        @Expose
        private String comment;
        @SerializedName("date")
        @Expose
        private String date;
        @SerializedName("is_like")
        @Expose
        private String isLike;
        @SerializedName("likes_count")
        @Expose
        private java.lang.Object likesCount;
        @SerializedName("user_name")
        @Expose
        private String userName;
        @SerializedName("user_image")
        @Expose
        private java.lang.Object userImage;

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

        public java.lang.Object getLikesCount() {
            return likesCount;
        }

        public void setLikesCount(java.lang.Object likesCount) {
            this.likesCount = likesCount;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public java.lang.Object getUserImage() {
            return userImage;
        }

        public void setUserImage(java.lang.Object userImage) {
            this.userImage = userImage;
        }

    }
}
