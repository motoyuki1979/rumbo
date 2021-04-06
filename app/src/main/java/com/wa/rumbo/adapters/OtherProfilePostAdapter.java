package com.wa.rumbo.adapters;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
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
import com.wa.rumbo.fragments.OtherUserFragment;
import com.wa.rumbo.interfaces.Register_Interfac;
import com.wa.rumbo.model.CommentDetail;
import com.wa.rumbo.model.GetAllPost;
import com.wa.rumbo.model.Status_Model;
import com.wa.rumbo.model.UserPostModel;
import com.wa.rumbo.utils;

import java.util.ArrayList;
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

public class OtherProfilePostAdapter extends RecyclerView.Adapter<OtherProfilePostAdapter.ViewHolder> {

    Context context;

    Activity mActivity;
    Dialog mDialog;
    Animation mClickEffect;
    ArrayList<UserPostModel.GetAllPost_Data> mList;


    public OtherProfilePostAdapter(Activity mActivity, ArrayList<UserPostModel.GetAllPost_Data> mList) {
        this.mActivity = mActivity;
        this.mList = mList;

        mDialog = UsefullData.getProgressDialog(mActivity);
        mClickEffect = AnimationUtils.loadAnimation(mActivity, R.anim.grow);
    }

    @Override
    public OtherProfilePostAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.rv_chat_write, viewGroup, false);
        return new OtherProfilePostAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OtherProfilePostAdapter.ViewHolder viewHolder, int i) {

        if (mList.get(i).getUserImage() != null) {
            decodeBase64AndSetImage(mList.get(i).getUserImage(), viewHolder.commentor_img);
        }
        if (!mList.get(i).getUserName().equalsIgnoreCase("")) {
            viewHolder.commentor_name.setText(mList.get(i).getUserName());
        } else {
            viewHolder.commentor_name.setText("Username");
        }
        if (!mList.get(i).getTodaysTweets().equalsIgnoreCase("")) {
            viewHolder.user_comment.setText(mList.get(i).getTodaysTweets());
        }

        if (!mList.get(i).getDate().equalsIgnoreCase("")) {
            viewHolder.commenting_date.setText(mList.get(i).getDate());
        }
        if (!mList.get(i).getLikes_count().equalsIgnoreCase("")) {
            viewHolder.total_comment_like.setText(mList.get(i).getLikes_count());
        } else {
            viewHolder.total_comment_like.setText("0");
        }

        viewHolder.ivComment.setVisibility(View.VISIBLE);
        viewHolder.tvCommentCount.setVisibility(View.VISIBLE);

        if (!mList.get(i).getComments_count().equalsIgnoreCase("")) {
            viewHolder.tvCommentCount.setText(mList.get(i).getComments_count());
        } else {
            viewHolder.tvCommentCount.setText("0");
        }

        if (mList.get(i).getIs_like() == true) {
            viewHolder.user_comment_like.setImageResource(R.mipmap.heart);
        } else {
            viewHolder.user_comment_like.setImageResource(R.mipmap.heart_unselect);
        }
    }


    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //user_comment_like

        @BindView(R.id.user_comment_like)
        ImageView user_comment_like;

       @BindView(R.id.arrival_adapter_comment)
        ImageView ivComment;

        @BindView(R.id.commentor_name)
        TextView commentor_name;

       @BindView(R.id.arrival_adapter_comment_val)
        TextView tvCommentCount;

        @BindView(R.id.commenting_date)
        TextView commenting_date;

        @BindView(R.id.user_comment)
        TextView user_comment;

        @BindView(R.id.total_comment_like)
        TextView total_comment_like;

        @BindView(R.id.commentor_img)
        CircleImageView commentor_img;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

        }
    }

    private void decodeBase64AndSetImage(String completeImageData, CircleImageView imageView) {

        if (completeImageData != null) {
            Uri imageUri = Uri.parse(completeImageData);
            imageView.setImageURI(imageUri);
        }

    }
}
