package com.wa.rumbo.adapters;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.wa.rumbo.Api;
import com.wa.rumbo.R;
import com.wa.rumbo.RetrofitInstance;
import com.wa.rumbo.callbacks.DeletePostCommentCallback;
import com.wa.rumbo.common.CommonData;
import com.wa.rumbo.common.UsefullData;
import com.wa.rumbo.fragments.Fragment_ChatWrite;
import com.wa.rumbo.fragments.Fragment_other;
import com.wa.rumbo.fragments.NewArrivalFragment;
import com.wa.rumbo.fragments.OtherUserFragment;
import com.wa.rumbo.interfaces.Register_Interfac;
import com.wa.rumbo.model.GetAllPost_Data;
import com.wa.rumbo.model.Status_Model;
import com.wa.rumbo.model.User;
import com.wa.rumbo.utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.wa.rumbo.R.string.block_user;
import static com.wa.rumbo.common.ConstantValue.TOKEN;
import static com.wa.rumbo.common.ConstantValue.USER_ID;

public class NewArrivalAdapter extends RecyclerView.Adapter<NewArrivalAdapter.MyView> {

    Context context;
    CommonData commonData;
    Activity mActivity;
    List<GetAllPost_Data> getAllPost_dataList;

    Retrofit retrofit = RetrofitInstance.getClient();
    Register_Interfac register_interfac = retrofit.create(Register_Interfac.class);

    Animation clickAnimation;
    Dialog mDialog;
    NewArrivalFragment fragment;
    public OnBlockListner onBlockListner;

    public NewArrivalAdapter(Context context, Activity mActivity, List<GetAllPost_Data> getAllPost_dataList, OnBlockListner onBlockListner) {
        this.context = context;
        this.mActivity = mActivity;
        this.getAllPost_dataList = getAllPost_dataList;
        this.onBlockListner = onBlockListner;
        commonData = new CommonData(context);
        clickAnimation = AnimationUtils.loadAnimation(context, R.anim.grow);
        mDialog = UsefullData.getProgressDialog(mActivity);
    }

    @NonNull
    @Override
    public MyView onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.new_arrival_adapter, viewGroup, false);
        return new MyView(view);
    }

    @Override
    public void onBindViewHolder(final MyView myView, final int i) {

        if (getAllPost_dataList.get(i).getUserImage() != null && !getAllPost_dataList.get(i).getUserImage().isEmpty()) {
            Picasso.with(context).load(getAllPost_dataList.get(i).getUserImage()).placeholder(R.drawable.image_dummy).into(myView.arrival_adptr_img);
        }
        myView.arrival_adptr_name.setText(!getAllPost_dataList.get(i).getTitle().equals("") ? getAllPost_dataList.get(i).getTitle() : "Username");


        myView.arrival_adptr_price_val.setText(UsefullData.getCommaPrice(mActivity, getAllPost_dataList.get(i).getExpenditure()));

        myView.arrival_adptr_date.setText(!getAllPost_dataList.get(i).getDate().equals("") ? getAllPost_dataList.get(i).getDate() : "6-1-2016");
        myView.arrival_adptr_comment_txt.setText(getAllPost_dataList.get(i).getTodaysTweets());
        myView.arrival_adapter_like_val.setText(getAllPost_dataList.get(i).getLikes_count());
        myView.arrival_adapter_comment_val.setText(getAllPost_dataList.get(i).getComments_count());
        myView.arrival_adptr_price.setText(getAllPost_dataList.get(i).getCategoryName());

        if (getAllPost_dataList.get(i).getIs_like() == true) {
            myView.arrival_adapter_big_heart.setImageResource(R.mipmap.heart);
        } else {
            myView.arrival_adapter_big_heart.setImageResource(R.mipmap.heart_unselect);
        }

        if (getAllPost_dataList.get(i).getCategoryImage() != null && !getAllPost_dataList.get(i).getCategoryImage().equalsIgnoreCase("")) {
            UsefullData.decodeBase64AndSetImage(getAllPost_dataList.get(i).getCategoryImage(), myView.ivDining);
        }

        myView.rel_arrival_adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(clickAnimation);
                Activity activity = (Activity) v.getContext();
                Fragment myFragment = new Fragment_ChatWrite();
                FragmentManager fragmentManager = activity.getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Bundle bundle = new Bundle();
                // bundle.putString("data", new Gson().toJson(getAllPost_dataList.get(i))); //key and value
                bundle.putString("id", getAllPost_dataList.get(i).getId()); //key and value
                bundle.putString("post_id", getAllPost_dataList.get(i).getPostId()); //key and value
                bundle.putString("title", getAllPost_dataList.get(i).getTitle()); //key and value
                bundle.putString("description", getAllPost_dataList.get(i).getTodaysTweets()); //key and value
                bundle.putString("date", getAllPost_dataList.get(i).getDate()); //key and value
                bundle.putString("price", getAllPost_dataList.get(i).getExpenditure()); //key and value
                bundle.putString("image", getAllPost_dataList.get(i).getUserImage()); //key and value
                bundle.putString("like_count", getAllPost_dataList.get(i).getLikes_count()); //key and value
                bundle.putString("comment_count", getAllPost_dataList.get(i).getComments_count()); //key and value
                bundle.putBoolean("is_like", getAllPost_dataList.get(i).getIs_like()); //key and value
                bundle.putString("user_id", getAllPost_dataList.get(i).getUserId()); //key and value
                bundle.putString("random_id", getAllPost_dataList.get(i).getRandomId()); //key and value

                myFragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.frameLayout, myFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });
        myView.arrival_adptr_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (commonData.getString(USER_ID).equalsIgnoreCase(getAllPost_dataList.get(i).getUserId())) {
                    Fragment myFragment = new Fragment_other();
                    FragmentManager fragmentManager = mActivity.getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frameLayout, myFragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();

                } else {
                    jumpToOtherPragment(getAllPost_dataList.get(i).getUserId());
                }

            }
        });

        myView.arrival_adapter_big_heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(clickAnimation);
                if (commonData.getString(USER_ID) != null && !commonData.getString(USER_ID).equals("")) {
                    mDialog.show();
                    Call call = register_interfac.getPostLike(commonData.getString(USER_ID), commonData.getString(TOKEN), getAllPost_dataList.get(i).getPostId());
                    call.enqueue(new Callback() {
                        @Override
                        public void onResponse(Call call, Response response) {
                            mDialog.dismiss();
                            Log.e("RESPONSE_LIKE_COM >>>>", response.raw() + "");
                            if (response.isSuccessful() && response.body() != null) {
                                String resp = new Gson().toJson(response.body());
                                Log.e("Success_Comment", resp);
                                Status_Model status_model = new Gson().fromJson(resp, Status_Model.class);
                                if (status_model.getSuccess().equals("true")) {
                                    if (getAllPost_dataList.get(i).getIs_like()) {
                                        getAllPost_dataList.get(i).setIs_like(false);
                                        int likesCount = Integer.parseInt(getAllPost_dataList.get(i).getLikes_count());
                                        getAllPost_dataList.get(i).setLikes_count("" + (likesCount - 1));
                                    } else {
                                        getAllPost_dataList.get(i).setIs_like(true);
                                        int likesCount = Integer.parseInt(getAllPost_dataList.get(i).getLikes_count());
                                        getAllPost_dataList.get(i).setLikes_count("" + (likesCount + 1));
                                    }
                                    notifyDataSetChanged();
                                } else {
                                    Toast.makeText(context, status_model.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call call, Throwable t) {
                            mDialog.dismiss();
                        }
                    });
                } else {
                    utils.showRegisterDialog(mActivity);
                }

            }
        });

        myView.ivMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getAllPost_dataList.get(i).getUserId().equalsIgnoreCase(commonData.getString(USER_ID))) {
                    showCustomDialog(mActivity, i, getAllPost_dataList.get(i).getId(), getAllPost_dataList.get(i).getRandomId());
                } else {
                    showBlockUserDialog(mActivity, getAllPost_dataList.get(i).getUserId());
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return getAllPost_dataList.size();
    }

    private void showCustomDialog(final Activity mActivity, final int position, final String id, final String randomId) {
        final Dialog dialog = new Dialog(mActivity);
        dialog.setContentView(R.layout.dialog_comment_delete);
        dialog.getWindow().setBackgroundDrawable(mActivity.getResources().getDrawable(R.drawable.dialog_bg));

        ImageView ivCross = (ImageView) dialog.findViewById(R.id.ivCross);
        TextView tvMessage = dialog.findViewById(R.id.tvMessage);
        tvMessage.setText(mActivity.getResources().getString(R.string.are_you_sure_to_delete_post));

        ivCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        TextView tvYes = (TextView) dialog.findViewById(R.id.tvYes);
        TextView tvNo = (TextView) dialog.findViewById(R.id.tvNo);

        // if button is clicked, close the custom dialog
        tvYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Api(mActivity).deletePostApi(mActivity, id, randomId, new DeletePostCommentCallback() {
                    @Override
                    public void onResponse(Status_Model model) {
                        dialog.dismiss();
                        getAllPost_dataList.remove(position);
                        notifyDataSetChanged();
                        Toast.makeText(mActivity, "Post deleted successfully", Toast.LENGTH_SHORT).show();

                    }
                });

            }
        });
        tvNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void showBlockUserDialog(final Activity mActivity, final String blockedUserId) {
        final Dialog dialog = new Dialog(mActivity);
        dialog.setContentView(R.layout.dialog_comment_delete);
        dialog.getWindow().setBackgroundDrawable(mActivity.getResources().getDrawable(R.drawable.dialog_bg));

        ImageView ivCross = (ImageView) dialog.findViewById(R.id.ivCross);

        ivCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        TextView tvMessage = (TextView) dialog.findViewById(R.id.tvMessage);
        TextView tvYes = (TextView) dialog.findViewById(R.id.tvYes);
        TextView tvNo = (TextView) dialog.findViewById(R.id.tvNo);

        tvMessage.setText(mActivity.getResources().getString(block_user));

        // if button is clicked, close the custom dialog
        tvYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Api(mActivity).blockUserApi(mActivity, blockedUserId);
                onBlockListner.onUserBlocked();
                dialog.dismiss();
            }
        });
        tvNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }


    void jumpToOtherPragment(String user_id) {
        Fragment myFragment = new OtherUserFragment();
        FragmentManager fragmentManager = mActivity.getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putString("user_id", user_id); //key and value

        myFragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.frameLayout, myFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public class MyView extends RecyclerView.ViewHolder {

        //liked_big_heart

        @BindView(R.id.liked_big_heart)
        ImageView liked_big_heart;

        @BindView(R.id.arrival_adapter_more)
        ImageView ivMore;

        @BindView(R.id.arrival_adapter_big_heart)
        ImageView arrival_adapter_big_heart;

        @BindView(R.id.ivDining)
        ImageView ivDining;

        @BindView(R.id.arrival_adptr_img)
        CircleImageView arrival_adptr_img;

        @BindView(R.id.arrival_adptr_name)
        TextView arrival_adptr_name;

        @BindView(R.id.arrival_adptr_price_val)
        TextView arrival_adptr_price_val;

        @BindView(R.id.arrival_adptr_price)
        TextView arrival_adptr_price;

        @BindView(R.id.arrival_adptr_comment_txt)
        TextView arrival_adptr_comment_txt;

        @BindView(R.id.arrival_adptr_date)
        TextView arrival_adptr_date;

        @BindView(R.id.arrival_adapter_like_val)
        TextView arrival_adapter_like_val;

        @BindView(R.id.arrival_adapter_comment_val)
        TextView arrival_adapter_comment_val;

        @BindView(R.id.rel_arrival_adapter)
        RelativeLayout rel_arrival_adapter;


        public MyView(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnBlockListner {
        void onUserBlocked ();
    }
}
