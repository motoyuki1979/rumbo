package com.wa.rumbo;

import android.app.Activity;
import android.app.Dialog;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wa.rumbo.callbacks.AddComunityCommentCallback;
import com.wa.rumbo.callbacks.DefaultCallback;
import com.wa.rumbo.callbacks.DeletePostCommentCallback;
import com.wa.rumbo.callbacks.GetCalenderBookingCalback;
import com.wa.rumbo.callbacks.GetComunityCommentsCallback;
import com.wa.rumbo.callbacks.GetFollowersCallback;
import com.wa.rumbo.callbacks.GetUserPostCallback;
import com.wa.rumbo.callbacks.GetUserProfileCallback;
import com.wa.rumbo.common.CommonData;
import com.wa.rumbo.common.UsefullData;
import com.wa.rumbo.interfaces.Register_Interfac;
import com.wa.rumbo.model.AddCategoryCommentModel;
import com.wa.rumbo.model.CommentPostModel;
import com.wa.rumbo.model.DeletePostCommentModel;
import com.wa.rumbo.model.GetAllPost;
import com.wa.rumbo.model.GetCalenderBookingModel;
import com.wa.rumbo.model.GetComunityComents;
import com.wa.rumbo.model.GetFollowersModel;
import com.wa.rumbo.model.GetUserProfileModel;
import com.wa.rumbo.model.Status_Model;
import com.wa.rumbo.model.UserPostModel;

import java.lang.reflect.Type;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.wa.rumbo.common.ConstantValue.TOKEN;
import static com.wa.rumbo.common.ConstantValue.USER_ID;

public class Api {
    Activity mActivity;
    Retrofit retrofit = RetrofitInstance.getClient();

    Dialog mDialog;
    Register_Interfac register_interfac = retrofit.create(Register_Interfac.class);
    CommonData commonData;


    public Api(Activity mActivity) {
        this.mActivity = mActivity;

        mDialog = UsefullData.getProgressDialog(mActivity);
        commonData = new CommonData(mActivity);
    }

    public void getUserProfile(String user_id, final Boolean showProgressbar, final GetUserProfileCallback callback) {


        Call<GetUserProfileModel> call = register_interfac.getUserProfile(commonData.getString(TOKEN), user_id);
        if (showProgressbar) {
            mDialog.show();
        }
        call.enqueue(new Callback<GetUserProfileModel>() {
            @Override
            public void onResponse(Call<GetUserProfileModel> call, Response<GetUserProfileModel> response) {
                if (mDialog.isShowing()) {
                    mDialog.dismiss();
                }
                if (response.isSuccessful() && response.body() != null) {

                    Log.e("profile details", new Gson().toJson(response.body()));
                    String resp = new Gson().toJson(response.body());
                    callback.onResponse(response.body());

                }
            }

            @Override
            public void onFailure(Call<GetUserProfileModel> call, Throwable t) {
                if (mDialog.isShowing()) {
                    mDialog.dismiss();
                }
            }
        });
    }

    public void updateUserProfile(String username, String email, String password, String introduction, String image) {
        mDialog.show();

        Call<Status_Model> call = register_interfac.updateUserProfile(commonData.getString(TOKEN), commonData.getString(USER_ID), username, email, password, introduction, image);
        // mProgressDialog.show();
        call.enqueue(new Callback<Status_Model>() {
            @Override
            public void onResponse(Call<Status_Model> call, Response<Status_Model> response) {
                mDialog.dismiss();
                if (response.isSuccessful() && response.body() != null) {
                    mDialog.dismiss();

                    Log.e("profile details", new Gson().toJson(response.body()));
                    String resp = new Gson().toJson(response.body());
                    Toast.makeText(mActivity, mActivity.getResources().getString(R.string.profile_updated_successfully), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<Status_Model> call, Throwable t) {
                mDialog.dismiss();
                Log.e("update profile failed =>  ", t.getMessage());
            }
        });
    }

    public void addCommentInCategory(final Activity mActivity, String comment, final AddComunityCommentCallback callback) {
        mDialog.show();

        AddCategoryCommentModel addCategoryCommentModel = new AddCategoryCommentModel();
        addCategoryCommentModel.setComment(comment.trim());
        addCategoryCommentModel.setComment_user_id(commonData.getString(USER_ID));
        addCategoryCommentModel.setCategory_id("99");
        addCategoryCommentModel.setDate("15March");

        Call<CommentPostModel> call = register_interfac.addCategoryComm(commonData.getString(USER_ID), commonData.getString(TOKEN), addCategoryCommentModel);
        // mProgressDialog.show();
        call.enqueue(new Callback<CommentPostModel>() {
            @Override
            public void onResponse(Call<CommentPostModel> call, Response<CommentPostModel> response) {
                mDialog.dismiss();
                if (response.isSuccessful() && response.body() != null) {
                    mDialog.dismiss();

                    Log.e("comment ", new Gson().toJson(response.body()));
                    String resp = new Gson().toJson(response.body());
                    //convert to model
                    callback.onResponse(response.body());
                    Status_Model status_model = new Gson().fromJson(resp, Status_Model.class);

                    if (status_model.getSuccess().equals("true")) {

                        Toast.makeText(mActivity, mActivity.getResources().getString(R.string.comment_sent_successfully), Toast.LENGTH_SHORT).show();
                        //   getPostComment(); //refresh all comment list
                    }
                }
            }

            @Override
            public void onFailure(Call<CommentPostModel> call, Throwable t) {
                mDialog.dismiss();
            }
        });
    }

    public void getComunityComment(final GetComunityCommentsCallback callback) {

        mDialog.show();

        Call call1 = register_interfac.get_comunity_coment(commonData.getString(USER_ID), commonData.getString(TOKEN));
        call1.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {

                Log.e("GET_Comunity COMMENT >>>>", response.raw() + "");

                if (response.isSuccessful() && response.body() != null) {
                    mDialog.dismiss();

                    Log.e("Success_post", new Gson().toJson(response.body()));
                    String resp = new Gson().toJson(response.body());

                    GetComunityComents getComunityComents = new Gson().fromJson(resp, GetComunityComents.class);
                    Type listType = new TypeToken<List<GetComunityComents>>() {
                    }.getType();


                    callback.onResponse(getComunityComents);
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                mDialog.dismiss();

            }
        });
    }

    public void CommentInCategoryLike(final Activity mActivity, String comment_id, final AddComunityCommentCallback callback) {
        mDialog.show();

        Call<CommentPostModel> call = register_interfac.CategoryCommentLike(commonData.getString(USER_ID), commonData.getString(TOKEN), comment_id);
        // mProgressDialog.show();
        call.enqueue(new Callback<CommentPostModel>() {
            @Override
            public void onResponse(Call<CommentPostModel> call, Response<CommentPostModel> response) {
                mDialog.dismiss();
                if (response.isSuccessful() && response.body() != null) {
                    mDialog.dismiss();

                    Log.e("comment ", new Gson().toJson(response.body()));
                    String resp = new Gson().toJson(response.body());
                    //convert to model
                    callback.onResponse(response.body());
                    Status_Model status_model = new Gson().fromJson(resp, Status_Model.class);


                    if (status_model.getMessage().contains("UnLike")) {

                        //   Toast.makeText(mActivity, mActivity.getResources().getString(R.string.comment_unlike_successfully), Toast.LENGTH_SHORT).show();

                    } else {
                        //  Toast.makeText(mActivity,mActivity.getResources().getString(R.string.comment_like_successfully), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<CommentPostModel> call, Throwable t) {
                mDialog.dismiss();
            }
        });
    }

    public void deletePostCommentApi(final Activity mActivity, String post_id, String comment_id, String comment, final DeletePostCommentCallback callback) {
        mDialog.show();

        DeletePostCommentModel deletePostCommentModel = new DeletePostCommentModel();
        deletePostCommentModel.setComment(comment.trim());
        deletePostCommentModel.setCommentId(comment_id.trim());
        deletePostCommentModel.setPostId(post_id.trim());

        Call<Status_Model> call = register_interfac.deletePostComment(commonData.getString(USER_ID), commonData.getString(TOKEN), deletePostCommentModel);
        // mProgressDialog.show();
        call.enqueue(new Callback<Status_Model>() {
            @Override
            public void onResponse(Call<Status_Model> call, Response<Status_Model> response) {
                mDialog.dismiss();
                if (response.isSuccessful() && response.body() != null) {
                    mDialog.dismiss();

                    Log.e(" delete comment ", new Gson().toJson(response.body()));
                    String resp = new Gson().toJson(response.body());
                    //convert to model
                    callback.onResponse(response.body());
                    Status_Model status_model = new Gson().fromJson(resp, Status_Model.class);

                    if (status_model.getSuccess().equals("true")) {

                        Toast.makeText(mActivity, mActivity.getResources().getString(R.string.comment_delete_successfully), Toast.LENGTH_SHORT).show();
                        //   getPostComment(); //refresh all comment list
                    }
                }
            }

            @Override
            public void onFailure(Call<Status_Model> call, Throwable t) {
                mDialog.dismiss();
            }
        });
    }

    public void blockUserApi(final Activity mActivity, String blockedUserId) {
        mDialog.show();

        Call<Status_Model> call = register_interfac.blockUser(commonData.getString(USER_ID), commonData.getString(TOKEN), blockedUserId);

        call.enqueue(new Callback<Status_Model>() {
            @Override
            public void onResponse(Call<Status_Model> call, Response<Status_Model> response) {
                mDialog.dismiss();
                if (response.isSuccessful() && response.body() != null) {
                    mDialog.dismiss();

                    Log.e(" blocked user ", new Gson().toJson(response.body()));
                    String resp = new Gson().toJson(response.body());
                    //convert to model
                    Status_Model status_model = new Gson().fromJson(resp, Status_Model.class);

                    if (status_model.getSuccess().equals("true")) {

                        Toast.makeText(mActivity, mActivity.getResources().getString(R.string.user_blocked_successfully), Toast.LENGTH_SHORT).show();

                    }
                }
            }

            @Override
            public void onFailure(Call<Status_Model> call, Throwable t) {
                mDialog.dismiss();
            }
        });
    }

    public void getFollowersApi(final Activity mActivity, Boolean isLoader, final GetFollowersCallback callback) {
        if (isLoader) {
            mDialog.show();
        }
        Call<GetFollowersModel> call = register_interfac.getFollowers(commonData.getString(USER_ID), commonData.getString(TOKEN));
        // mProgressDialog.show();
        call.enqueue(new Callback<GetFollowersModel>() {
            @Override
            public void onResponse(Call<GetFollowersModel> call, Response<GetFollowersModel> response) {
                if (mDialog.isShowing()) {
                    mDialog.dismiss();
                }
                if (response.isSuccessful() && response.body() != null) {

                    Log.e(" delete comment ", new Gson().toJson(response.body()));
                    String resp = new Gson().toJson(response.body());
                    callback.onResponse(response.body());
                }
            }

            @Override
            public void onFailure(Call<GetFollowersModel> call, Throwable t) {
                if (mDialog.isShowing()) {
                    mDialog.dismiss();
                }
            }
        });
    }

    public void getFollowsApi(final Activity mActivity, Boolean isLoader, final GetFollowersCallback callback) {
        if (isLoader) {
            mDialog.show();
        }
        Call<GetFollowersModel> call = register_interfac.getFollows(commonData.getString(USER_ID), commonData.getString(TOKEN));
        // mProgressDialog.show();
        call.enqueue(new Callback<GetFollowersModel>() {
            @Override
            public void onResponse(Call<GetFollowersModel> call, Response<GetFollowersModel> response) {
                if (mDialog.isShowing()) {
                    mDialog.dismiss();
                }
                if (response.isSuccessful() && response.body() != null) {
                    Log.e("getFollowsApi ", new Gson().toJson(response.body()));
                    String resp = new Gson().toJson(response.body());
                    callback.onResponse(response.body());
                } else {
                    callback.onFailure();
                }
            }

            @Override
            public void onFailure(Call<GetFollowersModel> call, Throwable t) {
                if (mDialog.isShowing()) {
                    mDialog.dismiss();
                }

            }
        });
    }

    public void getUserPostApi(String userId, final GetUserPostCallback callback) {
        mDialog.show();

        Call<UserPostModel> call = register_interfac.userPostGet(userId, commonData.getString(TOKEN));
        // mProgressDialog.show();
        call.enqueue(new Callback<UserPostModel>() {
            @Override
            public void onResponse(Call<UserPostModel> call, Response<UserPostModel> response) {
                mDialog.dismiss();
                if (response.isSuccessful() && response.body() != null) {
                    mDialog.dismiss();

                    Log.e("get user profile failed", new Gson().toJson(response.body()));
                    String resp = new Gson().toJson(response.body());
                    callback.onResponse(response.body());

                }
            }

            @Override
            public void onFailure(Call<UserPostModel> call, Throwable t) {
                mDialog.dismiss();
                callback.onFailutre();
                Log.e("get user profile failed =>  ", t.getMessage());
            }
        });
    }

    public void followUserApi(String user_id, String follow_id, final DefaultCallback callback) {
        mDialog.show();

        Call<Status_Model> call = register_interfac.addFollow(commonData.getString(TOKEN), user_id, follow_id);
        // mProgressDialog.show();
        call.enqueue(new Callback<Status_Model>() {
            @Override
            public void onResponse(Call<Status_Model> call, Response<Status_Model> response) {
                mDialog.dismiss();
                if (response.isSuccessful() && response.body() != null) {
                    mDialog.dismiss();

                    Log.e("fillow details", new Gson().toJson(response.body()));
                    String resp = new Gson().toJson(response.body());
                    Toast.makeText(mActivity, mActivity.getResources().getString(R.string.user_added_successfully), Toast.LENGTH_SHORT).show();
                    callback.onResponse(response.body());
                }
            }

            @Override
            public void onFailure(Call<Status_Model> call, Throwable t) {
                mDialog.dismiss();
                Log.e("follow failed =>  ", t.getMessage());
            }
        });
    }

    public void deleteFollowUserApi(String user_id, String follow_id, final DefaultCallback callback) {
        mDialog.show();

        Call<Status_Model> call = register_interfac.deleteFollow(commonData.getString(TOKEN), user_id, follow_id);
        // mProgressDialog.show();
        call.enqueue(new Callback<Status_Model>() {
            @Override
            public void onResponse(Call<Status_Model> call, Response<Status_Model> response) {
                mDialog.dismiss();
                if (response.isSuccessful() && response.body() != null) {
                    mDialog.dismiss();

                    Log.e("fillow details", new Gson().toJson(response.body()));
                    String resp = new Gson().toJson(response.body());
                    Toast.makeText(mActivity, mActivity.getResources().getString(R.string.user_removed_successfully), Toast.LENGTH_SHORT).show();
                    callback.onResponse(response.body());
                }
            }

            @Override
            public void onFailure(Call<Status_Model> call, Throwable t) {
                mDialog.dismiss();
                Log.e("follow failed =>  ", t.getMessage());
            }
        });
    }


    public void getCalenderBookingApi( final GetCalenderBookingCalback callback) {
        mDialog.show();

        Call<GetCalenderBookingModel> call = register_interfac.getCalenderBooking(commonData.getString(USER_ID), commonData.getString(TOKEN));
        // mProgressDialog.show();
        call.enqueue(new Callback<GetCalenderBookingModel>() {
            @Override
            public void onResponse(Call<GetCalenderBookingModel> call, Response<GetCalenderBookingModel> response) {
                mDialog.dismiss();
                if (response.isSuccessful() && response.body() != null) {
                    mDialog.dismiss();

                    Log.e("get calender_bookings => ", new Gson().toJson(response.body()));
                    String resp = new Gson().toJson(response.body());
                    callback.onRespose(response.body());

                }
            }

            @Override
            public void onFailure(Call<GetCalenderBookingModel> call, Throwable t) {
                mDialog.dismiss();
                callback.onFailure();
                Log.e("get user profile failed =>  ", t.getMessage());
            }
        });
    }
}
