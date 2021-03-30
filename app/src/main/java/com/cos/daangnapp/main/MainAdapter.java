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

import com.bumptech.glide.Glide;
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
                 holder.setItem(mContext,posts.get(position));
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
        public void setItem(MainActivity mContext,PostRespDto postRespDto){
            String tmp = postRespDto.getPrice();

            if(postRespDto.getImg() == null) {

            }else if(postRespDto.getImg().length() <=50){
                Glide.with(mContext).load(postRespDto.getImg()).into(photo);
            }else{
                String str = postRespDto.getImg();
                String str2 = str.replace("[", "&");
                String gubun[] = str2.split("&");
                String gubun1[] = gubun[1].split(",");
                Glide.with(mContext).load(""+gubun1[0]+"").into(photo);
            }
     //       Glide.with(mContext).load(postRespDto.getImg()).into(photo);
            tvTitle.setText(postRespDto.getTitle());
            tvDong.setText(postRespDto.getDong());
            tvTime.setText(postRespDto.getCreateDate().toString());
            if(tmp.equals("무료나눔")){
                tvPrice.setText(tmp);
            }else{
                tvPrice.setText(tmp+"원");
            }
            tvFavorite.setText(postRespDto.getFavorite()+"");
        }

    }

}
