package com.cos.daangnapp.sale;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cos.daangnapp.R;
import com.cos.daangnapp.main.home.detail.DetailActivity;
import com.cos.daangnapp.main.home.model.PostRespDto;

import java.text.DecimalFormat;
import java.util.List;

public class SaleAdapter extends RecyclerView.Adapter<SaleAdapter.MyViewHolder> {

    private static final String TAG = "GridViewadapter";
    private List<PostRespDto> posts;
    private Context mContext;

    public SaleAdapter(List<PostRespDto> posts, Context mContext) {
        this. posts =  posts;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: ");
        LayoutInflater inflater =(LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);   // main엑티비티에 연결할 객체를 생성해주는 인플레이터
        View view = inflater.inflate(R.layout.sales_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.setItem(mContext, posts.get(position));
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        private ImageView ivImage;
        private TextView tvTitle,tvaddress,tvfavorite,tvPrice,tvTime;
        private LinearLayout salesItem;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            ivImage = itemView.findViewById(R.id.sales_iv_product_pic);
            tvTitle = itemView.findViewById(R.id.sales_tv_product_name);
            tvPrice = itemView.findViewById(R.id.sales_tv_price);
            tvaddress = itemView.findViewById(R.id.sales_tv_address);
            tvfavorite = itemView.findViewById(R.id.sales_tv_interest);
            tvTime = itemView.findViewById(R.id.sales_tv_reroll);
            salesItem = itemView.findViewById(R.id.sales_item);
        }

        public void setItem(Context mContext, PostRespDto postRespDto){
            String tmp;
            if(postRespDto.getPrice().equals("무료나눔")){
                tmp ="무료나눔";
            }else {
                tmp = moneyFormatToWon(Integer.parseInt(postRespDto.getPrice()));
            }

            Glide.with(mContext).load(postRespDto.getImages().get(0).getUri()).into(ivImage);
            ivImage.setClipToOutline(true);
            ivImage.setScaleType(ImageView.ScaleType.FIT_XY);

            /*Uri uri = Uri.parse(postRespDto.getImages().get(0).getUri());
            ivImage.setImageURI(uri);
            ivImage.setClipToOutline(true);
            ivImage.setScaleType(ImageView.ScaleType.FIT_XY);*/

            tvTitle.setText(postRespDto.getTitle());
            tvaddress.setText(postRespDto.getDong());
            tvTime.setText(postRespDto.getCreateDate()+"");

            tvfavorite.setText(postRespDto.getFavorite()+"");
            if(tmp.equals("무료나눔")){
                tvPrice.setText(tmp);
            }else{
                tvPrice.setText(tmp+"원");
            }


            salesItem.setOnClickListener(v -> {
                Intent intent = new Intent(v.getContext(), DetailActivity.class);
                intent.putExtra("postId", postRespDto.getId());
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                v.getContext().startActivity(intent);
            });
        }
        public static String moneyFormatToWon(int inputMoney) {
            DecimalFormat decimalFormat = new DecimalFormat("#,##0");
            return decimalFormat.format(inputMoney);
        }
    }

}
