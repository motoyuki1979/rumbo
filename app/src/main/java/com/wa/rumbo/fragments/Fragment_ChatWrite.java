package com.wa.rumbo.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.wa.rumbo.R;
import com.wa.rumbo.RetrofitInstance;
import com.wa.rumbo.activities.MainActivity;
import com.wa.rumbo.adapters.Chat_Write_Adapter;
import com.wa.rumbo.common.CommonData;
import com.wa.rumbo.common.UsefullData;
import com.wa.rumbo.interfaces.Register_Interfac;
import com.wa.rumbo.model.CommentDetail;
import com.wa.rumbo.model.Comment_Request_Model;
import com.wa.rumbo.model.GetAllPost_Data;
import com.wa.rumbo.model.GetCommentPost;
import com.wa.rumbo.model.GetUserProfileModel;
import com.wa.rumbo.model.PostDetailModel;
import com.wa.rumbo.model.Status_Model;
import com.wa.rumbo.utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.wa.rumbo.R.string.not_now;
import static com.wa.rumbo.R.string.please_register_or_login_first;
import static com.wa.rumbo.R.string.register;
import static com.wa.rumbo.common.ConstantValue.TOKEN;
import static com.wa.rumbo.common.ConstantValue.USER_ID;
import static com.wa.rumbo.common.ConstantValue.USER_NAME;

public class Fragment_ChatWrite extends Fragment {

    @BindView(R.id.edt_comment_write)
    EditText edt_comment_write;
    @BindView(R.id.tv_comment)
    TextView tv_comment;
    @BindView(R.id.iv_post_like)
    ImageView iv_post_like;
    @BindView(R.id.tv_expenditure)
    TextView tv_expenditure;
    @BindView(R.id.tv_clicked_post_date)
    TextView tv_clicked_post_date;
    @BindView(R.id.tv_clicked_total_like)
    TextView tv_clicked_total_like;
    @BindView(R.id.tv_user_name)
    TextView tv_user_name;

    @BindView(R.id.tv_comments_count)
    public TextView tv_comments_count;
    @BindView(R.id.tvSend)
    TextView tvSend;

    @BindView(R.id.img_clicked_post_user)
    CircleImageView img_clicked_post_user;

    @BindView(R.id.rv_chat_write)
    RecyclerView rv_chat_write;
    CommonData commonData;
    GetCommentPost getCommentPost;
    PostDetailModel postDetailModel;
    GetAllPost_Data commentDetailModel;
    String post_id, title, description, image, date, price, likeCount, commentCount, user_id;
    Chat_Write_Adapter chat_write_adapter;
    List<CommentDetail> commentDetailList = new ArrayList<>();

    Retrofit retrofit = RetrofitInstance.getClient();
    Register_Interfac register_interfac = retrofit.create(Register_Interfac.class);
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.home_tabs)
    LinearLayout homeTabs;
    Dialog mDialog;
    Animation clickAnimation;
    GetAllPost_Data getAllPost_data;
    Boolean is_like = false;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chat_write_layout, container, false);

        ButterKnife.bind(this, view);
        UsefullData.setLocale(getActivity());
        mDialog = UsefullData.getProgressDialog(getActivity());
        clickAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.grow);
        ((MainActivity) getActivity()).getBottomSelectedTabs(2);
        ((MainActivity) getActivity()).btnBooking.setVisibility(View.GONE);
        commonData = new CommonData(getActivity());
        MainActivity.homeTabsLL.setVisibility(View.GONE);
        Bundle extras = getArguments();


        if (extras != null) {
            post_id = extras.getString("post_id");
            title = extras.getString("title");
            description = extras.getString("description");
            date = extras.getString("date");
            price = extras.getString("price");
            image = extras.getString("image");
            likeCount = extras.getString("like_count");
            commentCount = extras.getString("comment_count");
            is_like = extras.getBoolean("is_like");
            user_id = extras.getString("user_id");
        }


        setPostData();

       /* Fragment myFragment = new Fragment_ChatWrite();
        FragmentManager fragmentManager = getActivity().getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putString("data", new Gson().toJson(commentDetailModel)); //key and value
        myFragment.setArguments(bundle);*/


        rv_chat_write.setLayoutManager(new LinearLayoutManager(getActivity()));

        getPostComment();


        img_clicked_post_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (commonData.getString(USER_ID).equalsIgnoreCase(user_id)) {
                    Fragment myFragment = new Fragment_other();
                    FragmentManager fragmentManager = getActivity().getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frameLayout, myFragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();

                } else { jumpToOtherPragment(user_id);}
            }
        });


        edt_comment_write.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    switch (keyCode) {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:
                            comment_post();
                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(clickAnimation);
                getActivity().onBackPressed();


            }
        });
        tvSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(clickAnimation);
                if (commonData.getString(USER_ID) != null && !commonData.getString(USER_ID).equals("")) {
                    if (edt_comment_write.getText().toString().isEmpty()) {
                        Toast.makeText(getActivity(), "Please write a comment", Toast.LENGTH_SHORT).show();
                    } else {
                        comment_post();
                    }
                } else {
                    utils.showRegisterDialog(getActivity());
                }
            }
        });

        return view;
    }

    void jumpToOtherPragment(String user_id) {
        Fragment myFragment = new OtherUserFragment();
        FragmentManager fragmentManager = getActivity().getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putString("user_id", user_id); //key and value

        myFragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.frameLayout, myFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
    private void decodeBase64AndSetImage(String completeImageData, CircleImageView imageView) {

        if (completeImageData != null) {
            Uri imageUri = Uri.parse(completeImageData);
            imageView.setImageURI(imageUri);
        }

    }


    public void setPostData() {

        UsefullData.decodeBase64AndSetCircleImage(getActivity(),image, img_clicked_post_user);

        tv_user_name.setText(title);
        tv_comment.setText(description);
        tv_expenditure.setText(UsefullData.getCommaPrice(getActivity(), price));
        tv_clicked_post_date.setText(date);
        tv_clicked_total_like.setText(likeCount);
        tv_comments_count.setText(commentCount);

        if (is_like) {
            iv_post_like.setImageResource(R.mipmap.heart);
        } else {
            iv_post_like.setImageResource(R.mipmap.heart_unselect);
        }
    }

    public void comment_post() {

        /*final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message
        progressDialog.show();
*/
        mDialog.show();
        Comment_Request_Model comment_request_model = new Comment_Request_Model();
        comment_request_model.setComment(edt_comment_write.getText().toString().trim());
        comment_request_model.setPost_id(post_id);

        Call call = register_interfac.post_coment(commonData.getString(USER_ID), commonData.getString(TOKEN), comment_request_model);

        Log.e("add comment api => ", call.request().toString());

        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {

                Log.e("SEND_POST COMMENT >>>>", response.raw() + "");

                if (response.isSuccessful() && response.body() != null) {
                    mDialog.dismiss();

                    Log.e("comment ", new Gson().toJson(response.body()));
                    String resp = new Gson().toJson(response.body());
                    //convert to model
                    Status_Model status_model = new Gson().fromJson(resp, Status_Model.class);

                    if (status_model.getSuccess().equals("true")) {
                        edt_comment_write.setText("");
                        commentDetailModel.setComments_count("" + (Integer.parseInt(commentDetailModel.getComments_count()) + 1));
                        tv_comments_count.setText(commentDetailModel.getComments_count());
                        getPostComment(); //refresh all comment list
                    }
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                mDialog.dismiss();
                Log.e("onFailure_getting >>>>", "" + t.getMessage());
            }
        });


    }


    public void getPostComment() {

        /*final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message
        progressDialog.show();*/
        mDialog.show();

        Call call = register_interfac.getPostDetails(commonData.getString(USER_ID), commonData.getString(TOKEN), post_id);
        //Call call = register_interfac.getpost_coment(commonData.getString(USER_ID), commonData.getString(TOKEN), commentDetailModel.getPostId());
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {

                Log.e("GET_POST COMMENT >>>>", response.raw() + "");

                if (response.isSuccessful() && response.body() != null) {
                    mDialog.dismiss();

                    Log.e("USER'S_COMMENT", new Gson().toJson(response.body()));

                    String resp = new Gson().toJson(response.body());

                    //convert to model
                    //getCommentPost = new Gson().fromJson(resp, GetCommentPost.class);
                    postDetailModel = new Gson().fromJson(resp, PostDetailModel.class);

                    commentDetailList = postDetailModel.getPost_comments();
                    commentDetailModel = postDetailModel.getPost();
                    //setPostData();

                    chat_write_adapter = new Chat_Write_Adapter(getActivity(), getActivity(), commentDetailList, tv_comments_count, tv_comments_count.getText().toString());
                    rv_chat_write.setAdapter(chat_write_adapter);
                    if (commentDetailList.size() > 0)
                        rv_chat_write.scrollToPosition(commentDetailList.size() - 1);
                }


            }

            @Override
            public void onFailure(Call call, Throwable t) {
                mDialog.dismiss();

            }
        });


    }

    @OnClick(R.id.iv_post_like)
    public void doPostLike() {
        Call call = register_interfac.getPostLike(commonData.getString(USER_ID), commonData.getString(TOKEN), post_id);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {

                if (response.isSuccessful() && response.body() != null) {
                    String resp = new Gson().toJson(response.body());
                    Log.e("Success_like", resp);
                    Status_Model status_model = new Gson().fromJson(resp, Status_Model.class);
                    if (status_model.getSuccess().equals("true")) {
                        if (commentDetailModel.getIs_like()) {
                            commentDetailModel.setIs_like(false);
                            int likesCount = Integer.parseInt(commentDetailModel.getLikes_count()) - 1;
                            commentDetailModel.setLikes_count("" + likesCount);
                            tv_clicked_total_like.setText("" + likesCount);
                            iv_post_like.setImageResource(R.mipmap.heart_unselect);

                        } else {
                            commentDetailModel.setIs_like(true);
                            int likesCount = Integer.parseInt(commentDetailModel.getLikes_count()) + 1;
                            commentDetailModel.setLikes_count("" + likesCount);
                            tv_clicked_total_like.setText("" + likesCount);
                            iv_post_like.setImageResource(R.mipmap.heart);
                        }
                    } else {
                        Toast.makeText(getActivity(), status_model.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {

            }
        });


    }


}
