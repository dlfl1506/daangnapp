package com.cos.daangnapp.main.home.detail.adapter;

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
import com.cos.daangnapp.main.home.detail.UpdateActivity;

import java.util.ArrayList;

public class UpdateAdapter extends RecyclerView.Adapter<UpdateAdapter.MyViewHolder>{
    private static final String TAG = "WritingAdapter";
    private ArrayList<Uri> uriArrayList;
    private UpdateActivity uContext;

    public UpdateAdapter(ArrayList<Uri> uriArrayList, UpdateActivity uContext) {
        this.uriArrayList = uriArrayList;
        this.uContext = uContext;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: ");
        LayoutInflater inflater =(LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);   // main엑티비티에 연결할 객체를 생성해주는 인플레이터
        View view = inflater.inflate(R.layout.update_image,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
                holder.setItem(uriArrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return uriArrayList.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{

        private ImageView IvImage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            IvImage =itemView.findViewById(R.id.update_iv_upload_image);
            IvImage.setClipToOutline(true);

        }
        public void setItem(Uri uri) {
            IvImage.setImageURI(uri);
        }
    }
}
