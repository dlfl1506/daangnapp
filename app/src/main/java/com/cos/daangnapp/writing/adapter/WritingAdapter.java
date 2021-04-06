package com.cos.daangnapp.writing.adapter;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cos.daangnapp.R;
import com.cos.daangnapp.writing.WritingActivity;

import java.util.ArrayList;

public class WritingAdapter extends RecyclerView.Adapter<WritingAdapter.MyViewHolder>{
      private static final String TAG = "WritingAdapter";
     private ArrayList<Uri> uriArrayList;
     private WritingActivity wContext;

        public WritingAdapter(ArrayList<Uri> uriArrayList,WritingActivity wContext) {
            this.uriArrayList = uriArrayList;
            this.wContext = wContext;
        }

        public void removeItem(int position){
            uriArrayList.remove(position);
            notifyDataSetChanged();
        }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: ");
        LayoutInflater inflater =(LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);   // main엑티비티에 연결할 객체를 생성해주는 인플레이터
        View view = inflater.inflate(R.layout.upload_image,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

         holder.setItem(uriArrayList.get(position));
         holder.removeImage.setOnClickListener(v -> {
            removeItem(position);
             Log.d(TAG, "onBindViewHolder: "+uriArrayList.size());

         });
    }

    @Override
    public int getItemCount() {
            return  uriArrayList.size();
    }



    public static class MyViewHolder extends RecyclerView.ViewHolder{

        private ImageView IvImage,removeImage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            IvImage =itemView.findViewById(R.id.writing_iv_upload_image);
            removeImage = itemView.findViewById(R.id.iv_remove);
            IvImage.setClipToOutline(true);
        }
     public void setItem(Uri uri) {
          IvImage.setImageURI(uri);
        }

    }
}
