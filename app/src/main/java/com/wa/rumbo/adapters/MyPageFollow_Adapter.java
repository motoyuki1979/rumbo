package com.wa.rumbo.adapters;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wa.rumbo.Api;
import com.wa.rumbo.R;
import com.wa.rumbo.callbacks.DefaultCallback;
import com.wa.rumbo.common.CommonData;
import com.wa.rumbo.model.GetFollowersModel;
import com.wa.rumbo.model.Status_Model;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyPageFollow_Adapter extends RecyclerView.Adapter<MyPageFollow_Adapter.MYviewHolder> {

    Activity mActivity;
    ArrayList<GetFollowersModel.Object> mList;

    public MyPageFollow_Adapter(Activity mActivity, ArrayList<GetFollowersModel.Object> mList) {
        this.mActivity = mActivity;
        this.mList = mList;

    }

    @Override
    public MYviewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.rv_follow_list_adapter, viewGroup, false);
        return new MyPageFollow_Adapter.MYviewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MYviewHolder mYviewHolder, final int i) {

        decodeBase64AndSetImage(mList.get(i).getFollowerImage(), mYviewHolder.ivProfile);

        if (!mList.get(i).getFollowerUsername().equalsIgnoreCase("")) {
            mYviewHolder.tvUserName.setText(mList.get(i).getFollowerUsername());
        } else {
            mYviewHolder.tvUserName.setText(mActivity.getResources().getString(R.string.username));
        }

        if (!mList.get(i).getFollowerInfo().equalsIgnoreCase("")) {
            mYviewHolder.tvInfo.setText(mList.get(i).getFollowerInfo());
        } else {
            mYviewHolder.tvInfo.setText(mActivity.getResources().getString(R.string.no_profile_yet));
        }
        mYviewHolder.tvFollow.setText(mActivity.getResources().getString(R.string.following));
        mYviewHolder.tvFollow.setTextColor(mActivity.getResources().getColor(R.color.tab_text_color));
        mYviewHolder.tvFollow.setBackground(mActivity.getResources().getDrawable(R.drawable.rect_grey));


        mYviewHolder.tvFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mYviewHolder.tvFollow.getText().equals(mActivity.getResources().getString(R.string.following))){
                    //delete follow

                    new Api(mActivity).deleteFollowUserApi(mList.get(i).getUserId(), mList.get(i).getFollowerId(), new DefaultCallback() {
                        @Override
                        public void onResponse(Status_Model model) {
                            mYviewHolder.tvFollow.setText(mActivity.getResources().getString(R.string.follow));
                            mYviewHolder.tvFollow.setTextColor(mActivity.getResources().getColor(R.color.white));
                            mYviewHolder.tvFollow.setBackground(mActivity.getResources().getDrawable(R.drawable.tab_select_bg));
                        }
                    });


                }else{
                    //Add follow list

                    new  Api(mActivity).followUserApi(mList.get(i).getUserId(), mList.get(i).getFollowerId(), new DefaultCallback() {
                        @Override
                        public void onResponse(Status_Model model) {
                            mYviewHolder.tvFollow.setText(mActivity.getResources().getString(R.string.following));
                            mYviewHolder.tvFollow.setTextColor(mActivity.getResources().getColor(R.color.tab_text_color));
                            mYviewHolder.tvFollow.setBackground(mActivity.getResources().getDrawable(R.drawable.rect_grey));
                        }
                    });


                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class MYviewHolder extends RecyclerView.ViewHolder {
        CircleImageView ivProfile;
        TextView tvInfo, tvUserName,tvFollow;

        public MYviewHolder(View itemView) {
            super(itemView);

            ivProfile = itemView.findViewById(R.id.arrival_adptr_img);
            tvInfo = itemView.findViewById(R.id.tvInfo);
            tvUserName = itemView.findViewById(R.id.notifications);
            tvFollow = itemView.findViewById(R.id.new_arrival);

        }
    }

    private void decodeBase64AndSetImage(String completeImageData, CircleImageView imageView) {

        if (completeImageData != null) {
            Uri imageUri = Uri.parse(completeImageData);
            imageView.setImageURI(imageUri);
        }

    }
}
