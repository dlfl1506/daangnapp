package com.cos.daangnapp.main.chat.adapter;


import android.content.Context;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
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
import com.cos.daangnapp.main.chat.ChatActivity;
import com.cos.daangnapp.main.chat.model.Chat;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> {
    private static final String TAG = "UserAdapter";

    // 4번 컬렉션 생성
    private ChatActivity chatActivity;
    private final List<Chat> chats;
    private String nickname;
    private String photo;

    public ChatAdapter(ChatActivity chatActivity, List<Chat> chats, String nickname, String photo) {
        this.chatActivity = chatActivity;
        this.chats = chats;
        this.nickname = nickname;
        this.photo = photo;
    }

    // 5번 addItem, removeItem
    public void addItem(Chat chat){
        Log.d(TAG, "addItem: 에드아이템" + chat);
        chats.add(chat);
        notifyItemInserted(chats.size()-1);
        //notifyDataSetChanged();
    }

    public void removeItem(int position){
        chats.remove(position);
    }

    // 7번 getView랑 똑같음
    // 차이점이 있다면 listView는 화면에 3개가 필요 최초 로딩시에 3개를 그려야하니까 getView가 3번 호출됨
    // 그 다음에 스크롤을 해서 2개가 추가되야 될때, 다시 getView를 호출함.
    // 하지만 recyclerView는 스크롤을 해서 2개가 추가되어야 할 때 onBindViewHolder를 호출함
    // onCreateViewHolder는 해당 activity 실행시에만 호출 됨 / 최초 로딩시에만 호출되고 더이상 호출안됨
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // 메인 엑티비티에 연결할 객체를 만듬
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE); // 고정이다.
        View view = inflater.inflate(R.layout.chat_item, parent, false); // 객체만 만들어져있음 뿌려져있지않음

        return new MyViewHolder(view); // view가 리스트뷰에 하나 그려짐
    }

    @Override // 최초 로딩끝나고 그 뒤부터는 얘가 호출됨, 데이터 추가
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: ");
        holder.setItem(chatActivity,chats.get(position), nickname,photo);

    }

    // 6번 컬렉션 크기 알려주기 (화면에 몇개 그려야할지를 알아야 하기 때문)
    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: 체팅 사이즈" + chats.size());
        return chats.size();
    }

    // 1번 ViewHolder 만들기
    // ViewHolder란 하나의 View를 가지고 있는 Holder이다.
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        // 2번 user_item이 가지고 있는 위젯들을 선언
        private TextView tvmsg;
        private ImageView ivImage;
        private LinearLayout layoutChat;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvmsg = itemView.findViewById(R.id.chat_tv_msg);
            ivImage = itemView.findViewById(R.id.chat_iv_photo);
            layoutChat = itemView.findViewById(R.id.layout_chat);
        }

        public void setItem(ChatActivity chatActivity,Chat chat,String nickname,String photo){

            Log.d(TAG, "setItem: 닉네임" + chat.getNickname());
            Log.d(TAG, "setItem: 내닉네임" + nickname);
            Log.d(TAG, "setItem: 다른포토"+chat.getProfileUri());
            Log.d(TAG, "setItem: 내포토"+photo);
            tvmsg.setText(chat.getMsg());

                if (chat.getNickname().equals(nickname)){
                    layoutChat.setGravity(View.TEXT_ALIGNMENT_TEXT_END);
                    Glide.with(chatActivity).load(chat.getProfileUri()).into(ivImage);
                    ivImage.setBackground(new ShapeDrawable(new OvalShape()));
                    ivImage.setClipToOutline(true);
                    ivImage.setScaleType(ImageView.ScaleType.FIT_XY);
                }else{
                    Glide.with(chatActivity).load(photo).into(ivImage);
                    ivImage.setBackground(new ShapeDrawable(new OvalShape()));
                    ivImage.setClipToOutline(true);
                    ivImage.setScaleType(ImageView.ScaleType.FIT_XY);
                }
        }
    }
}