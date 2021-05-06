package com.wa.rumbo.adapters;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
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
import com.wa.rumbo.activities.MainActivity;
import com.wa.rumbo.callbacks.DeletePostCommentCallback;
import com.wa.rumbo.common.CommonData;
import com.wa.rumbo.common.UsefullData;
import com.wa.rumbo.fragments.Fragment_ChatWrite;
import com.wa.rumbo.fragments.Fragment_other;
import com.wa.rumbo.fragments.OtherUserFragment;
import com.wa.rumbo.interfaces.Register_Interfac;
import com.wa.rumbo.model.CommentDetail;
import com.wa.rumbo.model.PostDetailModel;
import com.wa.rumbo.model.Status_Model;
import com.wa.rumbo.utils;

import org.w3c.dom.Text;

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

public class Chat_Write_Adapter extends RecyclerView.Adapter<Chat_Write_Adapter.MyViewHolder> {

    Context context;
    CommonData commonData;
    Activity mActivity;
    Dialog mDialog;
    Animation mClickEffect;
    List<CommentDetail> getCommentDetail;
    Retrofit retrofit = RetrofitInstance.getClient();
    Register_Interfac register_interfac = retrofit.create(Register_Interfac.class);
    TextView tv_comments_count;
    String count;

    public Chat_Write_Adapter(Activity mActivity, Context context, List<CommentDetail> getCommentDetail, TextView tv_comments_count, String count) {
        this.mActivity = mActivity;
        this.context = context;
        this.tv_comments_count = tv_comments_count;
        this.getCommentDetail = getCommentDetail;
        this.count = count;
        commonData = new CommonData(mActivity);
        mDialog = UsefullData.getProgressDialog(mActivity);
        mClickEffect = AnimationUtils.loadAnimation(mActivity, R.anim.grow);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_chat_write, viewGroup, false);
        return new Chat_Write_Adapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder myViewHolder, final int position) {
        if(!getCommentDetail.get(position).getUserName().equalsIgnoreCase("")){
            myViewHolder.commentor_name.setText(getCommentDetail.get(position).getUserName());

        }else{
            myViewHolder.commentor_name.setText(mActivity.getResources().getString(R.string.username));

        }
        myViewHolder.commenting_date.setText(UsefullData.getDateTimeFromMills(getCommentDetail.get(position).getDate()));
        myViewHolder.total_comment_like.setText(getCommentDetail.get(position).getLikes());
        myViewHolder.user_comment.setText(getCommentDetail.get(position).getComment());


        if (getCommentDetail.get(position).getUserImage() != null && !getCommentDetail.get(position).getUserImage().isEmpty())
            Picasso.with(mActivity).load(getCommentDetail.get(position).getUserImage()).placeholder(R.drawable.image_dummy).into(myViewHolder.commentor_img);

        if (getCommentDetail.get(position).getIs_like() == true) {
            myViewHolder.user_comment_like.setImageResource(R.mipmap.heart);
        } else {
            myViewHolder.user_comment_like.setImageResource(R.mipmap.heart_unselect);
        }

        myViewHolder.user_comment_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(mClickEffect);
                if (commonData.getString(USER_ID) != null && !commonData.getString(USER_ID).equals("")) {
                    mDialog.show();
                    Call call = register_interfac.getCommentLike(commonData.getString(USER_ID), commonData.getString(TOKEN),
                            getCommentDetail.get(position).getCommentId(), getCommentDetail.get(position).getPostId());
                    call.enqueue(new Callback() {
                        @Override
                        public void onResponse(Call call, Response response) {
                            Log.e("RESPONSE_COMMENT >>>> ", response.raw() + "");
                            mDialog.dismiss();
                            if (response.isSuccessful() && response.body() != null) {
                                Log.e("Success_Comment", new Gson().toJson(response.body()));
                                String resp = new Gson().toJson(response.body());
                                Status_Model status_model = new Gson().fromJson(resp, Status_Model.class);
                                if (status_model.getSuccess().equals("true")) {
                                    if (status_model.getMessage().equalsIgnoreCase("UnLike")) {
                                        getCommentDetail.get(position).setIs_like(false);
                                        int likesCount = Integer.parseInt(getCommentDetail.get(position).getLikes());
                                        getCommentDetail.get(position).setLikes("" + (likesCount - 1));
                                    } else {
                                        getCommentDetail.get(position).setIs_like(true);
                                        int likesCount = Integer.parseInt(getCommentDetail.get(position).getLikes());
                                        getCommentDetail.get(position).setLikes("" + (likesCount + 1));
                                    }
                                    //   notifyDataSetChanged();
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

        myViewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                v.startAnimation(mClickEffect);
                if (getCommentDetail.get(position).getUserId().equalsIgnoreCase(commonData.getString(USER_ID))) {
                    showCustomDialog(mActivity, position, getCommentDetail.get(position).getComment(), getCommentDetail.get(position).getCommentId(), getCommentDetail.get(position).getPostId(), tv_comments_count);
                } else {
                    showBlockUserDialog(mActivity, getCommentDetail.get(position).getUserId());
                }
                return false;
            }
        });
        myViewHolder.commentor_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(mClickEffect);
                if (commonData.getString(USER_ID).equalsIgnoreCase(getCommentDetail.get(position).getUserId())) {
                    Fragment myFragment = new Fragment_other();
                    FragmentManager fragmentManager = mActivity.getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frameLayout, myFragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();

                } else {
                    jumpToOtherPragment(getCommentDetail.get(position).getUserId());
                }     // showBlockUserDialog(mActivity, getCommentDetail.get(position).getUserId());
            }
        });

        myViewHolder.commentor_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(mClickEffect);
                jumpToOtherPragment(getCommentDetail.get(position).getUserId());
                //  showBlockUserDialog(mActivity, getCommentDetail.get(position).getUserId());
            }
        });

        myViewHolder.commenting_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(mClickEffect);
                jumpToOtherPragment(getCommentDetail.get(position).getUserId());
                //  showBlockUserDialog(mActivity, getCommentDetail.get(position).getUserId());
            }

        });
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

    private void showCustomDialog(final Activity mActivity, final int position, final String comment, final String commentId, final String postId, final TextView tvTotalCommentCount) {
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

        TextView tvYes = (TextView) dialog.findViewById(R.id.tvYes);
        TextView tvNo = (TextView) dialog.findViewById(R.id.tvNo);

        // if button is clicked, close the custom dialog
        tvYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Api(mActivity).deletePostCommentApi(mActivity, postId, commentId, comment, new DeletePostCommentCallback() {
                    @Override
                    public void onResponse(Status_Model model) {
                        dialog.dismiss();
                        getCommentDetail.remove(position);
                        tvTotalCommentCount.setText(Integer.valueOf(count) - 1 + "");
                        notifyDataSetChanged();

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

    public int getItemCount() {

        Log.e("si\n" +
                "\n" +
                "    @Overridezee", String.valueOf(getCommentDetail.size()));
        return getCommentDetail.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        //user_comment_like

        @BindView(R.id.user_comment_like)
        ImageView user_comment_like;

        @BindView(R.id.commentor_name)
        TextView commentor_name;

        @BindView(R.id.commenting_date)
        TextView commenting_date;

        @BindView(R.id.user_comment)
        TextView user_comment;

        @BindView(R.id.total_comment_like)
        TextView total_comment_like;

        @BindView(R.id.commentor_img)
        CircleImageView commentor_img;

        public MyViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

        }
    }
}
