package com.wa.rumbo.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wa.rumbo.R;
import com.wa.rumbo.common.UsefullData;
import com.wa.rumbo.interfaces.Category_Interf;
import com.wa.rumbo.model.Category_Data;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.MyViewHolder> {

    Context context;
    List<Category_Data> categoryList;
    Category_Interf category_interf;


    public CategoriesAdapter(Context context, List<Category_Data> categoryList, Category_Interf categoryInterf) {
        this.context = context;
        this.categoryList = categoryList;
        this.category_interf = categoryInterf;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_categories, viewGroup, false);
        return new CategoriesAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.tv_category_name.setText(categoryList.get(position).getCategoryName());
        if (!categoryList.get(position).getCategory_image().isEmpty()) {
            Picasso.with(context).load(categoryList.get(position).getCategory_image()).into(holder.iv_category_image);
        }

        holder.tv_comments_count.setText(categoryList.get(position).getTotal_comments());

        if (categoryList.get(position).getLast_comment() == null) {
            holder.tv_last_comment.setText("");
            holder.tv_datetime.setText("");
        } else {
            holder.tv_last_comment.setText(categoryList.get(position).getLast_comment().getComment());
            holder.tv_datetime.setText(UsefullData.getDateTimeFromMills(categoryList.get(position).getLast_comment().getDate()));
        }
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_category_name)
        TextView tv_category_name;
        @BindView(R.id.iv_category_image)
        ImageView iv_category_image;
        @BindView(R.id.tv_datetime)
        TextView tv_datetime;
        //lin_category
        @BindView(R.id.tv_last_comment)
        TextView tv_last_comment;
        @BindView(R.id.tv_comments_count)
        TextView tv_comments_count;

        public MyViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

        }
    }
}
