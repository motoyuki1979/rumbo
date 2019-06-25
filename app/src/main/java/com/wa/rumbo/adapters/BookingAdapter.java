package com.wa.rumbo.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wa.rumbo.R;
import com.wa.rumbo.interfaces.Category_Interf;
import com.wa.rumbo.model.CategoryResponse;
import com.wa.rumbo.model.Category_Data;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.MyViewHolder> {

    Context context;
    List<Category_Data> categoryList;
    Category_Interf category_interf;
    MyViewHolder prev_holder=null;
    int click=0;


    public BookingAdapter(Context context, List<Category_Data> categoryList, Category_Interf categoryInterf) {
        this.context = context;
        this.categoryList = categoryList;
        this.category_interf = categoryInterf;
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
        if (position==0)
        myViewHolder.rv_img_kakebo.setImageResource(R.mipmap.syokuhi);
        if (position==1)
            myViewHolder.rv_img_kakebo.setImageResource(R.mipmap.zakka);
        if (position==2)
            myViewHolder.rv_img_kakebo.setImageResource(R.mipmap.ihuku);
        if (position==3)
            myViewHolder.rv_img_kakebo.setImageResource(R.mipmap.kyouiku);
        if (position==4)
            myViewHolder.rv_img_kakebo.setImageResource(R.mipmap.kousai);
        if (position==5)
            myViewHolder.rv_img_kakebo.setImageResource(R.mipmap.goraku);
        if (position==6)
            myViewHolder.rv_img_kakebo.setImageResource(R.mipmap.hoken);
        if (position==7)
            myViewHolder.rv_img_kakebo.setImageResource(R.mipmap.koutsuu);
        if (position==8)
            myViewHolder.rv_img_kakebo.setImageResource(R.mipmap.kounetsu);
        if (position==9)
            myViewHolder.rv_img_kakebo.setImageResource(R.mipmap.tsuushin);
        if (position==10)
            myViewHolder.rv_img_kakebo.setImageResource(R.mipmap.jyuutaku);
        if (position==11)
            myViewHolder.rv_img_kakebo.setImageResource(R.mipmap.sonota);





        myViewHolder.lin_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click=1;
                if (prev_holder!=null){
                    prev_holder.lin_category.setBackgroundResource(R.drawable.border_black);

                }
               myViewHolder.lin_category.setBackgroundResource(R.drawable.border_black_bg);
                prev_holder=myViewHolder;

                String cat_name = categoryList.get(position).getCategoryName();
                String cat_id = categoryList.get(position).getCategoryId();

                category_interf.cat_data(cat_id, cat_name );
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
