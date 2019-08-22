
package com.wa.rumbo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Category_Data implements Serializable {

    @SerializedName("category_id")
    @Expose
    private String categoryId;
    @SerializedName("category_name")
    @Expose
    private String categoryName;
    @SerializedName("category_image")
    @Expose
    private String category_image;
    @SerializedName("total_comments")
    @Expose
    private String total_comments;

    @SerializedName("last_comment")
    @Expose
    private CommentDetail last_comment;

    // private final static long serialVersionUID = -8499531912126071320L;

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setCategory_image(String category_image) {
        this.category_image = category_image;
    }

    public String getCategory_image() {
        return category_image;
    }

    public String getTotal_comments() {
        return total_comments;
    }

    public void setTotal_comments(String total_comments) {
        this.total_comments = total_comments;
    }

    public CommentDetail getLast_comment() {
        return last_comment;
    }

    public void setLast_comment(CommentDetail last_comment) {
        this.last_comment = last_comment;
    }
}
