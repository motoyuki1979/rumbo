
package com.wa.rumbo.model;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NotificationRespList implements Serializable
{

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("comment_id")
    @Expose
    private String commentId;
    @SerializedName("from_user_id")
    @Expose
    private String fromUserId;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("datetime")
    @Expose
    private String datetime;

    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("post_id")
    @Expose
    private String post_id;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(String fromUserId) {
        this.fromUserId = fromUserId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }
}
