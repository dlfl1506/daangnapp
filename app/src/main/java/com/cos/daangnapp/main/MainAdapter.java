package com.cos.daangnapp.main;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cos.daangnapp.R;
import com.cos.daangnapp.main.model.PostRespDto;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MyViewHolder>{

    private static final String TAG = "ContactAdapter";
    private List<PostRespDto> posts;
    private MainActivity mContext;

    public MainAdapter(List<PostRespDto> posts, MainActivity mContext) {
        this.posts = posts;
        this.mContext = mContext;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: ");
        LayoutInflater inflater =(LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);   // main엑티비티에 연결할 객체를 생성해주는 인플레이터
        View view = inflater.inflate(R.layout.product_ltem,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
                 holder.setItem(posts.get(position));
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        private ImageView photo;
        private TextView tvTitle,tvDong,tvTime,tvPrice,tvReply,tvFavorite;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            photo= itemView.findViewById(R.id.home_iv_product_pic);
            tvTitle=itemView.findViewById(R.id.home_tv_product_name);
            tvDong=itemView.findViewById(R.id.home_tv_address);
            tvTime =itemView.findViewById(R.id.home_tv_reroll);
            tvPrice= itemView.findViewById(R.id.home_tv_price);
            tvFavorite = itemView.findViewById(R.id.home_tv_interest);
        }
        public void setItem(PostRespDto postRespDto){
            tvTitle.setText(postRespDto.getTitle());
            tvDong.setText(postRespDto.getDong());
            tvTime.setText(postRespDto.getCreateDate().toString());
            tvPrice.setText(postRespDto.getPrice());
            tvFavorite.setText(postRespDto.getFavorite()+"");
        }
    }


}
