package com.wa.rumbo.model;

import java.util.List;

public class PostDetailModel {
    String success;
    String message;
    GetAllPost_Data post;
    List<CommentDetail> post_comments;

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

    public GetAllPost_Data getPost() {
        return post;
    }

    public void setPost(GetAllPost_Data post) {
        this.post = post;
    }

    public List<CommentDetail> getPost_comments() {
        return post_comments;
    }

    public void setPost_comments(List<CommentDetail> post_comments) {
        this.post_comments = post_comments;
    }
}