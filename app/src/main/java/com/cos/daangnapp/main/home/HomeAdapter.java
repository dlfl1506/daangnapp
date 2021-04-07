package com.cos.daangnapp.main.home;

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

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder>{

    private static final String TAG = "HomeAdapter";
    private List<PostRespDto> mItemsList;
    private LayoutInflater mInflater;
    private Context mContext;
    private  HomeFragment homeFragment;

    public HomeAdapter(List<PostRespDto> mItemsList, Context mContext) {
        this.mItemsList = mItemsList;
//        this.mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        holder.setItem(mContext,mItemsList.get(position));
    }

    @Override
    public int getItemCount() {
        return mItemsList.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{

        private ImageView photo;
        private TextView tvTitle,tvDong,tvTime,tvPrice,tvFavorite;
        private LinearLayout productItem;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            photo= itemView.findViewById(R.id.home_iv_product_pic);
            tvTitle=itemView.findViewById(R.id.home_tv_product_name);
            tvDong=itemView.findViewById(R.id.home_tv_address);
            tvTime =itemView.findViewById(R.id.home_tv_reroll);
            tvPrice= itemView.findViewById(R.id.home_tv_price);
            tvFavorite = itemView.findViewById(R.id.home_tv_interest);
            productItem= itemView.findViewById(R.id.product_item);

         /*   photo.setColorFilter(Color.parseColor("#FF3E3B3B"), PorterDuff.Mode.MULTIPLY);
            productItem.setBackgroundColor(Color.parseColor("#FF3E3B3B"));
*/
        }
        public void setItem(Context mContext, PostRespDto postRespDto){
            try {
                String tmp;
                if(postRespDto.getPrice().equals("무료나눔")){
                    tmp ="무료나눔";
                }else {
                    tmp = moneyFormatToWon(Integer.parseInt(postRespDto.getPrice()));
                }

               Glide.with(mContext).load(postRespDto.getImages().get(0).getUri()).into(photo);
                photo.setClipToOutline(true);
                photo.setScaleType(ImageView.ScaleType.FIT_XY);

                tvTitle.setText(postRespDto.getTitle());
                tvDong.setText(postRespDto.getDong());
                tvTime.setText(postRespDto.getCreateDate().toString());
                if(tmp.equals("무료나눔")){
                    tvPrice.setText(tmp);
                }else{
                    tvPrice.setText(tmp+"원");
                }
                tvFavorite.setText(postRespDto.getFavorite()+"");

                productItem.setOnClickListener(v -> {
                    Intent intent = new Intent(v.getContext(), DetailActivity.class);
                    intent.putExtra("postId", postRespDto.getId());
                    v.getContext().startActivity(intent);
                });
            } catch (Exception e) {
                Log.d(TAG, "null");
            }

        }

        public static String moneyFormatToWon(int inputMoney) {
            DecimalFormat decimalFormat = new DecimalFormat("#,##0");
            return decimalFormat.format(inputMoney);
        }
    }
}


