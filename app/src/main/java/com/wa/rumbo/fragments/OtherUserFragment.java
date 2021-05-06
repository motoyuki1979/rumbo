package com.wa.rumbo.fragments;

import android.app.Dialog;
import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.wa.rumbo.Api;
import com.wa.rumbo.R;
import com.wa.rumbo.activities.MainActivity;
import com.wa.rumbo.adapters.GetComunityCommentsAdapter;
import com.wa.rumbo.adapters.OtherProfilePostAdapter;
import com.wa.rumbo.callbacks.DefaultCallback;
import com.wa.rumbo.callbacks.GetFollowersCallback;
import com.wa.rumbo.callbacks.GetUserPostCallback;
import com.wa.rumbo.callbacks.GetUserProfileCallback;
import com.wa.rumbo.common.CommonData;
import com.wa.rumbo.common.UsefullData;
import com.wa.rumbo.model.GetAllPost;
import com.wa.rumbo.model.GetFollowersModel;
import com.wa.rumbo.model.GetUserProfileModel;
import com.wa.rumbo.model.Status_Model;
import com.wa.rumbo.model.UserPostModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.wa.rumbo.common.ConstantValue.USER_ID;

public class OtherUserFragment extends Fragment {
    Animation clickAnimation;

    OtherProfilePostAdapter adapter;
    @BindView(R.id.rvList)
    RecyclerView rvList;

    @BindView(R.id.followings)
    TextView tvUsername;
    @BindView(R.id.imgBack)
    ImageView imgBack;
    @BindView(R.id.tv_prof_settings)
    TextView tvFollow;
    @BindView(R.id.tv_others)
    TextView tvInfo;
    @BindView(R.id.tvNoDataFound)
    TextView tvNoDataFound;
    @BindView(R.id.arrival_adptr_img)
    CircleImageView ivProfile;
    String user_id;
    ArrayList<UserPostModel.GetAllPost_Data> mList;
    ArrayList<String> followList = new ArrayList<>();
    CommonData commonData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.other_user_fragment, container, false);

        ButterKnife.bind(this, view);
        MainActivity.homeTabsLL.setVisibility(View.GONE);

        UsefullData.setLocale(getActivity());
        Bundle extras = getArguments();
        commonData = new CommonData(getActivity());

        mList = new ArrayList<>();
        if (extras != null) {
            user_id = extras.getString("user_id");
        }

        new Api(getActivity()).getFollowsApi(getActivity(), true, new GetFollowersCallback() {
            @Override
            public void onResponse(GetFollowersModel model) {
                if (commonData.getString(USER_ID).equalsIgnoreCase(user_id)) {
                    tvFollow.setVisibility(View.GONE);

                } else {
                    tvFollow.setVisibility(View.VISIBLE);
                    for (int j = 0; j < model.getObject().size(); j++) {
                        followList.add(model.getObject().get(j).getFollowerId());
                    }
                    if (followList.contains(user_id)) {
                        tvFollow.setText(getActivity().getResources().getString(R.string.following));
                        tvFollow.setTextColor(getActivity().getResources().getColor(R.color.tab_text_color));
                        tvFollow.setBackground(getActivity().getResources().getDrawable(R.drawable.rect_grey));
                    } else {
                        tvFollow.setText(getActivity().getResources().getString(R.string.follow));
                        tvFollow.setTextColor(getActivity().getResources().getColor(R.color.white));
                        tvFollow.setBackground(getActivity().getResources().getDrawable(R.drawable.tab_select_bg));
                    }
                }


            }

            @Override
            public void onFailure() {
                tvFollow.setVisibility(View.VISIBLE);
                tvFollow.setText(getActivity().getResources().getString(R.string.follow));
                tvFollow.setTextColor(getActivity().getResources().getColor(R.color.white));
                tvFollow.setBackground(getActivity().getResources().getDrawable(R.drawable.tab_select_bg));
            }
        });

        new Api(getActivity()).getUserProfile(user_id, false, new GetUserProfileCallback() {
            @Override
            public void onResponse(GetUserProfileModel model) {
                if (!model.getUserDetails().get(0).getUserName().equalsIgnoreCase("")) {
                    tvUsername.setText(model.getUserDetails().get(0).getUserName());
                } else {
                    tvUsername.setText(getActivity().getResources().getString(R.string.username));
                }
             if (!model.getUserDetails().get(0).getIntroduction().equalsIgnoreCase("")) {
                 tvInfo.setText(model.getUserDetails().get(0).getIntroduction());
                } else {
                 tvInfo.setText(getActivity().getResources().getString(R.string.profile_text));
                }

                UsefullData.decodeBase64AndSetCircleImage(getActivity() ,model.getUserDetails().get(0).getImage(), ivProfile);


                new Api(getActivity()).getUserPostApi(user_id, new GetUserPostCallback() {
                    @Override
                    public void onResponse(UserPostModel model) {
                        for (int i = 0; i < model.getObject().size(); i++) {
                            mList.add(model.getObject().get(i));
                        }


                        if (mList.size() > 0) {
                            tvNoDataFound.setVisibility(View.GONE);
                            rvList.setVisibility(View.VISIBLE);

                            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                            rvList.setLayoutManager(layoutManager);
                            adapter = new OtherProfilePostAdapter(getActivity(), mList);
                            rvList.setAdapter(adapter);

                        } else {
                            tvNoDataFound.setVisibility(View.VISIBLE);
                            rvList.setVisibility(View.GONE);
                        }

                    }

                    @Override
                    public void onFailutre() {
                        tvNoDataFound.setVisibility(View.VISIBLE);
                        rvList.setVisibility(View.GONE);
                    }
                });


            }
        });


        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();


            }
        });

        tvFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvFollow.getText().toString().equalsIgnoreCase(getActivity().getResources().getString(R.string.following))) {
                    new Api(getActivity()).deleteFollowUserApi(commonData.getString(USER_ID), user_id, new DefaultCallback() {
                        @Override
                        public void onResponse(Status_Model model) {
                            tvFollow.setText(getActivity().getResources().getString(R.string.follow));
                            tvFollow.setTextColor(getActivity().getResources().getColor(R.color.white));
                            tvFollow.setBackground(getActivity().getResources().getDrawable(R.drawable.tab_select_bg));
                        }
                    });
                } else {
                    new Api(getActivity()).followUserApi(commonData.getString(USER_ID), user_id, new DefaultCallback() {
                        @Override
                        public void onResponse(Status_Model model) {
                            tvFollow.setText(getActivity().getResources().getString(R.string.following));
                            tvFollow.setTextColor(getActivity().getResources().getColor(R.color.tab_text_color));
                            tvFollow.setBackground(getActivity().getResources().getDrawable(R.drawable.rect_grey));
                        }
                    });
                }
            }
        });

        return view;
    }

}
