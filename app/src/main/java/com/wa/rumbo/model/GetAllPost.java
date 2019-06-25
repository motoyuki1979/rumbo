package com.wa.rumbo.model;


import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetAllPost implements Serializable
{

    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("object")
    @Expose
    private List<GetAllPost_Data> object = null;
  //  private final static long serialVersionUID = -9105191274641042750L;

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

    public List<GetAllPost_Data> getObject() {
        return object;
    }

    public void setObject(List<GetAllPost_Data> object) {
        this.object = object;
    }

}
