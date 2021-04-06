package com.wa.rumbo.adapters;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wa.rumbo.Api;
import com.wa.rumbo.R;
import com.wa.rumbo.callbacks.DeletePostCommentCallback;
import com.wa.rumbo.interfaces.FragmentReplac;
import com.wa.rumbo.model.Status_Model;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.wa.rumbo.R.string.block_user;

public class Community_Adapter extends RecyclerView.Adapter<Community_Adapter.MyViewHolder> {

    Context context;
    Activity activity;
    FragmentReplac fragmentReplac;
    Animation mClickEffect;

    public Community_Adapter(Context context, FragmentReplac fragmentReplac) {
        this.context = context;
        this.fragmentReplac = fragmentReplac;
        mClickEffect = AnimationUtils.loadAnimation(activity, R.anim.grow);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_community, viewGroup, false);
        // View view = LayoutInflater.from(context).inflate(R.layout.new_arrival_adapter, viewGroup, false);
        return new Community_Adapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int position) {


        myViewHolder.lin_community_adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentReplac.clicked();
                //  v.getContext().startActivity(new Intent(context, MyPageFollow_Fragment.class));
                //  v.getContext().startActivity(new Intent(context, MyPageFollow_Fragment.class));

               /* ((MainActivity) v.getContext()).getFragmentManager().beginTransaction()
                        .replace(R.id.lin_community_adapter, MyPageFollow_Fragment.class)
                        .commit();*/

                /*
                 Fragment mFragment = new CardExpandFragment();
            context.getSupportFragmentManager().beginTransaction().replace(R.id.cardExpand, mFragment).commit();
        }
                 */

                 /*FragmentTransaction transaction = ((Community_Fragment)context).getSupportFragmentManager().beginTransaction();
    transaction.replace(R.id.lin_community_adapter, MyPageFollow_Fragment);
    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
    transaction.addToBackStack(null);
    transaction.commit();*/

                // activity.startActivity(new Intent(activity, MyPageFollow_Fragment.class));

            }
        });

    }


    @Override
    public int getItemCount() {
        return 6;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.img_money)
        ImageView img_money;

        @BindView(R.id.tv_title)
        TextView tv_title;

        @BindView(R.id.tv_comment)
        TextView tv_comment;

        @BindView(R.id.tv_comment_number)
        TextView tv_comment_number;

        @BindView(R.id.tv_date)
        TextView tv_date;

        @BindView(R.id.tv_time)
        TextView tv_time;

        @BindView(R.id.lin_community_adapter)
        LinearLayout lin_community_adapter;

        public MyViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);


            /*
             @BindView(R.id.tv_top)
        TextView tv_top;

        @BindView(R.id.tv_date)
        TextView tv_date;

        @BindView(R.id.tv_time)
        TextView tv_time;
        public MyViewHolder( View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
             */
        }
    }

}
