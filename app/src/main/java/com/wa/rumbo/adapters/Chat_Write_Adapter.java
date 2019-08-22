package com.wa.rumbo.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.wa.rumbo.R;
import com.wa.rumbo.RetrofitInstance;
import com.wa.rumbo.common.CommonData;
import com.wa.rumbo.common.UsefullData;
import com.wa.rumbo.interfaces.Register_Interfac;
import com.wa.rumbo.model.CommentDetail;
import com.wa.rumbo.model.Status_Model;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.wa.rumbo.common.ConstantValue.TOKEN;
import static com.wa.rumbo.common.ConstantValue.USER_ID;

public class Chat_Write_Adapter extends RecyclerView.Adapter<Chat_Write_Adapter.MyViewHolder> {

    Context context;
    CommonData commonData;

    List<CommentDetail> getCommentDetail;
    Retrofit retrofit = RetrofitInstance.getClient();
    Register_Interfac register_interfac = retrofit.create(Register_Interfac.class);

    public Chat_Write_Adapter(Context context, List<CommentDetail> getCommentDetail) {
        this.context = context;
        this.getCommentDetail = getCommentDetail;
        commonData = new CommonData(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_chat_write, viewGroup, false);
        return new Chat_Write_Adapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder myViewHolder, final int position) {
        myViewHolder.commentor_name.setText(getCommentDetail.get(position).getUserName());
        myViewHolder.commenting_date.setText(UsefullData.getDateTimeFromMills(getCommentDetail.get(position).getDate()));
        myViewHolder.total_comment_like.setText(getCommentDetail.get(position).getLikes());
        myViewHolder.user_comment.setText(getCommentDetail.get(position).getComment());

        if (getCommentDetail.get(position).getUserImage() != null && !getCommentDetail.get(position).getUserImage().isEmpty())
            Picasso.with(context).load(getCommentDetail.get(position).getUserImage());

        if (getCommentDetail.get(position).getIs_like() == true) {
            myViewHolder.user_comment_like.setImageResource(R.mipmap.heart);
        } else {
            myViewHolder.user_comment_like.setImageResource(R.mipmap.heart_unselect);
        }

        myViewHolder.user_comment_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Call call = register_interfac.getCommentLike(commonData.getString(USER_ID), commonData.getString(TOKEN),
                        getCommentDetail.get(position).getCommentId(), getCommentDetail.get(position).getPostId());
                call.enqueue(new Callback() {
                    @Override
                    public void onResponse(Call call, Response response) {
                        Log.e("RESPONSE_COMMENT >>>>", response.raw() + "");

                        if (response.isSuccessful() && response.body() != null) {
                            Log.e("Success_Comment", new Gson().toJson(response.body()));
                            String resp = new Gson().toJson(response.body());
                            Status_Model status_model = new Gson().fromJson(resp, Status_Model.class);
                            if (status_model.getSuccess().equals("true")) {
                                if (getCommentDetail.get(position).getIs_like()) {
                                    getCommentDetail.get(position).setIs_like(false);
                                    int likesCount = Integer.parseInt(getCommentDetail.get(position).getLikes());
                                    getCommentDetail.get(position).setLikes("" + (likesCount - 1));
                                } else {
                                    getCommentDetail.get(position).setIs_like(true);
                                    int likesCount = Integer.parseInt(getCommentDetail.get(position).getLikes());
                                    getCommentDetail.get(position).setLikes("" + (likesCount + 1));
                                }
                                notifyDataSetChanged();
                            } else {
                                Toast.makeText(context, status_model.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call call, Throwable t) {

                    }
                });

            }
        });

    }

    @Override
    public int getItemCount() {

        Log.e("sizee", String.valueOf(getCommentDetail.size()));
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
        ImageView commentor_img;

        public MyViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

        }
    }
}
