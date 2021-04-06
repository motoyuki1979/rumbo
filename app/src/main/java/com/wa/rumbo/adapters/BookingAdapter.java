package com.wa.rumbo.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wa.rumbo.R;
import com.wa.rumbo.interfaces.Category_Interf;
import com.wa.rumbo.model.CategoryResponse;
import com.wa.rumbo.model.Category_Data;

import java.io.InputStream;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.MyViewHolder> {

    Context context;
    List<Category_Data> categoryList;
    Category_Interf category_interf;
    MyViewHolder prev_holder = null;
    int click = 0;
    String cat_name;
    String cat_id;
    Animation clickAnimation;
    int drawable;

    public BookingAdapter(Context context, List<Category_Data> categoryList, Category_Interf categoryInterf) {
        this.context = context;
        this.categoryList = categoryList;
        this.category_interf = categoryInterf;
        clickAnimation = AnimationUtils.loadAnimation(context, R.anim.grow);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_kakebo, viewGroup, false);
        return new BookingAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder myViewHolder, final int position) {

        CategoryResponse categoryResponse = new CategoryResponse();
        myViewHolder.rv_tv_kakebo.setText(categoryList.get(position).getCategoryName());

        if (position == 0) {
            myViewHolder.rv_img_kakebo.setImageResource(R.drawable.ic_category_00);
            myViewHolder.lin_category.setBackgroundResource(R.drawable.border_black_bg);
            prev_holder = myViewHolder;
            drawable = R.drawable.ic_category_00;
            cat_name = categoryList.get(position).getCategoryName();
            cat_id = categoryList.get(position).getCategoryId();

            Uri uri = Uri.parse("android.resource://" + context.getPackageName() + "/" + drawable);
            //InputStream iStream = context.getContentResolver().openInputStream(uri);

            category_interf.cat_data(cat_id, cat_name,uri);
        }
        if (position == 1) {
            myViewHolder.rv_img_kakebo.setImageResource(R.drawable.ic_category_01);
        }
        if (position == 2) {
            myViewHolder.rv_img_kakebo.setImageResource(R.drawable.ic_category_02);
        }
        if (position == 3) {
            myViewHolder.rv_img_kakebo.setImageResource(R.drawable.ic_category_03);
        }
        if (position == 4) {
            myViewHolder.rv_img_kakebo.setImageResource(R.drawable.ic_category_04);
        }
        if (position == 5) {
            myViewHolder.rv_img_kakebo.setImageResource(R.drawable.ic_category_05);
        }
        if (position == 6) {
            myViewHolder.rv_img_kakebo.setImageResource(R.drawable.ic_category_06);
        }
        if (position == 7) {
            myViewHolder.rv_img_kakebo.setImageResource(R.drawable.ic_category_07);
        }
        if (position == 8) {
            myViewHolder.rv_img_kakebo.setImageResource(R.drawable.ic_category_08);
        }
        if (position == 9) {
            myViewHolder.rv_img_kakebo.setImageResource(R.drawable.ic_category_09);
        }
        if (position == 10) {
            myViewHolder.rv_img_kakebo.setImageResource(R.drawable.ic_category_10);
        }
        if (position == 11) {
            myViewHolder.rv_img_kakebo.setImageResource(R.drawable.ic_category_11);
        }

        myViewHolder.lin_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(clickAnimation);
                click = 1;
                if (prev_holder != null) {
                    prev_holder.lin_category.setBackgroundResource(R.drawable.border_black);

                }
                myViewHolder.lin_category.setBackgroundResource(R.drawable.border_black_bg);
                prev_holder = myViewHolder;

                if(position == 0){
                    drawable = R.drawable.ic_category_00;
                }else  if (position == 1){
                    drawable = R.drawable.ic_category_01;
                }else  if (position == 2){
                    drawable = R.drawable.ic_category_02;
                }else  if (position == 3){
                    drawable = R.drawable.ic_category_03;
                }else  if (position == 4){
                    drawable = R.drawable.ic_category_04;
                }else  if (position == 5){
                    drawable = R.drawable.ic_category_05;
                }else  if (position == 6){
                    drawable = R.drawable.ic_category_06;
                }else  if (position == 7){
                    drawable = R.drawable.ic_category_07;
                }else  if (position == 8){
                    drawable = R.drawable.ic_category_08;
                }else  if (position == 9){
                    drawable = R.drawable.ic_category_09;
                }else  if (position == 10){
                    drawable = R.drawable.ic_category_10;
                }else  if (position == 11){
                    drawable = R.drawable.ic_category_11;
                }

                Uri uri = Uri.parse("android.resource://" + context.getPackageName() + "/" + drawable);
                //InputStream iStream = context.getContentResolver().openInputStream(uri);

                cat_name = categoryList.get(position).getCategoryName();
                cat_id = categoryList.get(position).getCategoryId();

                category_interf.cat_data(cat_id, cat_name,uri);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.rv_img_kakebo)
        ImageView rv_img_kakebo;
        @BindView(R.id.rv_tv_kakebo)
        TextView rv_tv_kakebo;
        //lin_category
        @BindView(R.id.lin_category)
        LinearLayout lin_category;

        public MyViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

        }
    }
}
