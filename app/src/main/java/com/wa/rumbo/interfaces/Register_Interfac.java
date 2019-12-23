package com.wa.rumbo.interfaces;

import com.wa.rumbo.fragments.BookingFragment;
import com.wa.rumbo.model.CategoryResponse;
import com.wa.rumbo.model.Comment_Request_Model;
import com.wa.rumbo.model.GetAllPost;
import com.wa.rumbo.model.GetCommentPost;
import com.wa.rumbo.model.NotificationResponse;
import com.wa.rumbo.model.PostDetailModel;
import com.wa.rumbo.model.Register_Model;
import com.wa.rumbo.model.Status_Model;
import com.wa.rumbo.model.User_Post_Model;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Register_Interfac {

    @POST("register")
    Call<Register_Model> registeration(
            @Query("device_id") String device_id, @Query("device_token") String device_token);

    @POST("category_list")
    Call<CategoryResponse>category_list(
            @Header("user_id") String user_id, @Header("authenticate") String authenticate);

    @GET("posts?_embed&filter[taxonomy]=category&filter[term]=sports&page=1")
    Call<CategoryResponse>posts();

    @POST("add_post")
    Call<CategoryResponse>addPost(
            @Header("user_id") String user_id, @Header("authenticate") String authenticate,
            @Body BookingFragment.GetPost getPost);

    @POST("get_all_post")
    Call<GetAllPost>allPostGet(
            @Header("user_id") String user_id, @Header("authenticate") String authenticate/*, @Body KakeboFragment.GetPost getPost*/);

    @POST("post_comment")
    Call<GetAllPost>post_coment(
            @Header("user_id") String user_id, @Header("authenticate") String authenticate,
            @Body Comment_Request_Model comment_request_model);

    @POST("get_post_comment")
    Call<GetCommentPost>getpost_coment(
            @Header("user_id") String user_id, @Header("authenticate") String authenticate,
            @Query("post_id") String postID);

    //get_post_details

    @POST("get_post_detail")
    Call<PostDetailModel>getPostDetails(
            @Header("user_id") String user_id, @Header("authenticate") String authenticate,
            @Query("post_id") String postID);

    @POST("post_like")
    Call<Status_Model>getPostLike(
            @Header("user_id") String user_id, @Header("authenticate") String authenticate,
            @Query("post_id") String postID);

    @POST("comment_like")
    Call<Status_Model>getCommentLike(
            @Header("user_id") String user_id, @Header("authenticate") String authenticate,
            @Query("comment_id") String commentID,@Query("post_id") String postID);



    @POST("notifications")
    Call<NotificationResponse>getNotifications(
            @Header("user_id") String user_id, @Header("authenticate") String authenticate);


    @POST("get_user_by_id")
    Call<User_Post_Model> getUserPostList(
            @Header("user_id")String user_id, @Header("authenticate") String authenticate,
            @Query("user_id") String from_user_id);






}
