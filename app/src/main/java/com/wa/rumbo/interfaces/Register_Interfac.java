package com.wa.rumbo.interfaces;

import com.wa.rumbo.fragments.BookingFragment;
import com.wa.rumbo.model.AddCategoryCommentModel;
import com.wa.rumbo.model.CategoryResponse;
import com.wa.rumbo.model.CommentPostModel;
import com.wa.rumbo.model.Comment_Request_Model;
import com.wa.rumbo.model.DeletePostCommentModel;
import com.wa.rumbo.model.GetAllPost;
import com.wa.rumbo.model.GetBlockedListModel;
import com.wa.rumbo.model.GetCalenderBookingModel;
import com.wa.rumbo.model.GetCommentPost;
import com.wa.rumbo.model.GetComunityCommentModel;
import com.wa.rumbo.model.GetFollowersModel;
import com.wa.rumbo.model.GetUserProfileModel;
import com.wa.rumbo.model.NotificationResponse;
import com.wa.rumbo.model.PostDetailModel;
import com.wa.rumbo.model.RegisterModel;
import com.wa.rumbo.model.Register_Model;
import com.wa.rumbo.model.Status_Model;
import com.wa.rumbo.model.UserPostModel;
import com.wa.rumbo.model.User_Post_Model;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Register_Interfac {

    @POST("register")
    Call<RegisterModel> registeration(
            @Query("device_id") String device_id, @Query("device_token") String device_token, @Query("email") String email, @Query("password") String password, @Query("following_count") String following_count, @Query("follower_count") String follower_count);

    @POST("login")
    Call<Register_Model> login(
            @Query("device_id") String device_id, @Query("device_token") String device_token, @Query("email") String email, @Query("password") String password);

    @POST("category_list")
    Call<CategoryResponse> category_list(
            @Header("user_id") String user_id, @Header("authenticate") String authenticate);

    @GET("posts?_embed&filter[taxonomy]=category&filter[term]=sports&page=1")
    Call<CategoryResponse> posts();

    @POST("add_post")
    Call<CategoryResponse> addPost(
            @Header("user_id") String user_id, @Header("authenticate") String authenticate,
            @Body BookingFragment.GetPost getPost);

    @POST("get_all_post")
    Call<GetAllPost> allPostGet(
            @Header("user_id") String user_id, @Header("authenticate") String authenticate/*, @Body KakeboFragment.GetPost getPost*/);

    @POST("post_comment")
    Call<GetAllPost> post_coment(
            @Header("user_id") String user_id, @Header("authenticate") String authenticate,
            @Body Comment_Request_Model comment_request_model);

    @POST("get_post_comment")
    Call<GetCommentPost> getpost_coment(
            @Header("user_id") String user_id, @Header("authenticate") String authenticate,
            @Query("post_id") String postID);

    @POST("get_post_detail")
    Call<PostDetailModel> getPostDetails(
            @Header("user_id") String user_id, @Header("authenticate") String authenticate,
            @Query("post_id") String postID);

    @POST("post_like")
    Call<Status_Model> getPostLike(
            @Header("user_id") String user_id, @Header("authenticate") String authenticate,
            @Query("post_id") String postID);

    @POST("comment_like")
    Call<Status_Model> getCommentLike(
            @Header("user_id") String user_id, @Header("authenticate") String authenticate,
            @Query("comment_id") String commentID, @Query("post_id") String postID);

    @POST("notifications")
    Call<NotificationResponse> getNotifications(
            @Header("user_id") String user_id, @Header("authenticate") String authenticate);

    @POST("get_user_by_id")
    Call<User_Post_Model> getUserPostList(
            @Header("user_id") String user_id, @Header("authenticate") String authenticate,
            @Query("user_id") String from_user_id);


    @POST("get_comunity_comment")
    Call<GetComunityCommentModel> get_comunity_coment(
            @Header("user_id") String user_id, @Header("authenticate") String authenticate);


    @POST("update_user_by_id")
    Call<Status_Model> updateUserProfile(
            @Header("authenticate") String authenticate,
            @Query("user_id") String user_id, @Query("user_name") String user_name, @Query("email") String email, @Query("password") String password, @Query("introduction") String introduction, @Query("image") String image, @Query("isEmailChanged") String isEmailChanged);

    @POST("get_user_profile")
    Call<GetUserProfileModel> getUserProfile(@Header("authenticate") String authenticate, @Query("user_id") String user_id);

    @POST("add_category_comment")
    Call<CommentPostModel> addCategoryComm(
            @Header("user_id") String user_id, @Header("authenticate") String authenticate,
            @Body AddCategoryCommentModel addCategoryCommentModel);

    @POST("comunity_comment_like")
    Call<CommentPostModel> CategoryCommentLike(
            @Header("user_id") String user_id, @Header("authenticate") String authenticate, @Query("comment_id") String comment_id);

    @POST("delete_post_comment")
    Call<Status_Model> deletePostComment(@Header("user_id") String user_id, @Header("authenticate") String authenticate, @Body DeletePostCommentModel deletePostCommentModel);

    @POST("delete_post")
    Call<Status_Model> deletePost(@Header("id") String id, @Header("authenticate") String authenticate, @Header("random_id") String random_id);

    @POST("block_user")
    Call<Status_Model> blockUser(
            @Header("user_id") String user_id, @Header("authenticate") String authenticate, @Query("blocked_user_id") String blocked_user_id);

    @POST("get_followers_list")
    Call<GetFollowersModel> getFollowers(
            @Header("user_id") String user_id, @Header("authenticate") String authenticate);

    @POST("get_follow_list")
    Call<GetFollowersModel> getFollows(
            @Header("user_id") String user_id, @Header("authenticate") String authenticate);

    @POST("get_user_post")
    Call<UserPostModel> userPostGet(
            @Header("user_id") String user_id, @Header("authenticate") String authenticate);

    @POST("add_follow")
    Call<Status_Model> addFollow(
            @Header("authenticate") String authenticate, @Header("user_id") String user_id, @Query("follower_id") String follower_id);

    @POST("delete_follow")
    Call<Status_Model> deleteFollow(@Header("authenticate") String authenticate, @Header("user_id") String user_id, @Query("follower_id") String follower_id);

    @POST("get_calender_bookings")
    Call<GetCalenderBookingModel> getCalenderBooking(@Header("user_id") String user_id, @Header("authenticate") String authenticate);

    @POST("get_blockList")
    Call<GetBlockedListModel> getBlockedList(@Header("user_id") String user_id, @Header("authenticate") String authenticate);

}