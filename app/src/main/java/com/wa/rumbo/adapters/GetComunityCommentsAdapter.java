package com.wa.rumbo.adapters;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wa.rumbo.Api;
import com.wa.rumbo.R;
import com.wa.rumbo.callbacks.AddComunityCommentCallback;
import com.wa.rumbo.common.CommonData;
import com.wa.rumbo.common.UsefullData;
import com.wa.rumbo.fragments.OtherUserFragment;
import com.wa.rumbo.interfaces.Category_Interf;
import com.wa.rumbo.model.Category_Data;
import com.wa.rumbo.model.CommentPostModel;
import com.wa.rumbo.model.GetComunityComents;
import com.wa.rumbo.utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.wa.rumbo.common.ConstantValue.USER_ID;

public class GetComunityCommentsAdapter extends RecyclerView.Adapter<GetComunityCommentsAdapter.MyViewHolder> {

    Context context;
    Activity mActivity;
    List<GetComunityComents.Object> getComunityComents;
    // Category_Interf category_interf;
    Animation clcikAnimation;
    CommonData commonData;

    public GetComunityCommentsAdapter(Context context, Activity mActivity, List<GetComunityComents.Object> getComunityComents) {
        this.context = context;
        this.mActivity = mActivity;
        this.getComunityComents = getComunityComents;
        //  this.category_interf = categoryInterf;
        commonData = new CommonData(mActivity);
        clcikAnimation = AnimationUtils.loadAnimation(context, R.anim.grow);
    }

    @Override
    public GetComunityCommentsAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        // View view = LayoutInflater.from(context).inflate(R.layout.adapter_categories, viewGroup, false);
        View view = LayoutInflater.from(context).inflate(R.layout.new_arrival_adapter, viewGroup, false);
        return new GetComunityCommentsAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final GetComunityCommentsAdapter.MyViewHolder holder, final int position) {

        holder.tv_category_name.setText(getComunityComents.get(position).getUserName());

        if (!getComunityComents.get(position).getUserName().equalsIgnoreCase("")) {
            if (!getComunityComents.get(position).getUserImage().equals("")) {

                decodeBase64AndSetImage(getComunityComents.get(position).getUserImage(), holder.ivProfile);
            }
        }

        holder.tvCommentCount.setVisibility(View.GONE);
        holder.ivComment.setVisibility(View.GONE);
        holder.tv_last_comment.setText(getComunityComents.get(position).getComment());
        holder.tv_datetime.setText(UsefullData.getDateTimeFromMills(getComunityComents.get(position).getDate()));
        holder.tvTotalLike.setText(getComunityComents.get(position).getLikesCount());

        if (getComunityComents.get(position).getIsLike().equalsIgnoreCase("true")) {
            holder.ivLike.setImageDrawable(mActivity.getResources().getDrawable(R.mipmap.heart));

        } else {
            holder.ivLike.setImageDrawable(mActivity.getResources().getDrawable(R.mipmap.heart_unselect));

        }

        holder.ivProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(clcikAnimation);
                jumpToOtherPragment(getComunityComents.get(position).getCommentUserId());

            }
        });

        holder.tv_category_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(clcikAnimation);
                jumpToOtherPragment(getComunityComents.get(position).getCommentUserId());

            }
        });

        holder.ivLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(clcikAnimation);
                if (commonData.getString(USER_ID) != null && !commonData.getString(USER_ID).equals("")) {
                    new Api(mActivity).CommentInCategoryLike(mActivity, getComunityComents.get(position).getCommentId(), new AddComunityCommentCallback() {
                        @Override
                        public void onResponse(CommentPostModel model) {

                            if (model.getMessage().contains("UnLike")) {
                                getComunityComents.get(position).setIsLike("false");
                                getComunityComents.get(position).setLikesCount(Integer.valueOf(getComunityComents.get(position).getLikesCount()) - 1 + "");
                                holder.tvTotalLike.setText(getComunityComents.get(position).getLikesCount());
                                holder.ivLike.setImageDrawable(mActivity.getResources().getDrawable(R.mipmap.heart_unselect));
                            } else {
                                getComunityComents.get(position).setIsLike("true");
                                getComunityComents.get(position).setLikesCount(Integer.valueOf(getComunityComents.get(position).getLikesCount()) + 1 + "");
                                holder.tvTotalLike.setText(getComunityComents.get(position).getLikesCount());
                                holder.ivLike.setImageDrawable(mActivity.getResources().getDrawable(R.mipmap.heart));
                            }

                        }
                    });
                } else {
                    utils.showRegisterDialog(mActivity);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return getComunityComents.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.arrival_adptr_name)
        TextView tv_category_name;
        /* @BindView(R.id.iv_category_image)
         ImageView iv_category_image;*/
        @BindView(R.id.arrival_adptr_date)
        TextView tv_datetime;
        //lin_category
        @BindView(R.id.arrival_adptr_comment_txt)
        TextView tv_last_comment;
        @BindView(R.id.arrival_adapter_comment_val)
        TextView tvCommentCount;
        @BindView(R.id.arrival_adapter_like_val)
        TextView tvTotalLike;
        @BindView(R.id.arrival_adapter_comment)
        ImageView ivComment;
        @BindView(R.id.arrival_adapter_big_heart)
        ImageView ivLike;
        @BindView(R.id.arrival_adptr_img)
        CircleImageView ivProfile;

        public MyViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

        }
    }

    void  jumpToOtherPragment(String user_id){
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

    private void decodeBase64AndSetImage(String completeImageData, CircleImageView imageView) {

        if (completeImageData != null) {
            Uri imageUri = Uri.parse(completeImageData);
            try {
                imageView.setImageURI(imageUri);
            } catch (Exception e) {

            }

        } else {
            imageView.setImageDrawable(mActivity.getResources().getDrawable(R.mipmap.image_dummy));
        }

    }
}
