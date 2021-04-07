package com.cos.daangnapp.main.chat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cos.daangnapp.R;
import com.cos.daangnapp.main.chat.adapter.ChatAdapter;
import com.cos.daangnapp.main.chat.model.Chat;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {
    private static final String TAG = "ChatActivity";
    private ImageButton ibBack,ibMore;
    private TextView chatNickName;
    private RecyclerView rvChat;
    private EditText etChatMsg;
    private Button btnSend;
    private  DatabaseReference myRef;
    private ChatAdapter chatAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        Log.d(TAG, "onCreate: 실행됨");
        init();
        initSetting();

    }

    public void init(){
        ibBack = findViewById(R.id.chat_iv_back);
        ibMore = findViewById(R.id.chat_iv_more);
        chatNickName = findViewById(R.id.chat_nickname);
        rvChat = findViewById(R.id.rv_chat);

        LinearLayoutManager manager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rvChat.setLayoutManager(manager);

        etChatMsg = findViewById(R.id.et_msg);
        btnSend = findViewById(R.id.btn_chat);

    }

    public void initSetting(){

        Intent intent = getIntent();
        String nickname = intent.getStringExtra("nickName");
        String uri = intent.getStringExtra("uri");
        int postId = intent.getIntExtra("postId", 1);
        int userId = intent.getIntExtra("userId",0);
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        String myNick = pref.getString("myNick",null);
        String photo = pref.getString("photo",null);

        chatNickName.setText(nickname);

        ibBack.setOnClickListener(v -> {
            onBackPressed();
        });

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference(String.valueOf(postId)).child("chat");

        btnSend.setOnClickListener(v -> {
            String msg = etChatMsg.getText().toString(); // msg
            if (msg != null){
                Chat chat = new Chat();
                chat.setNickname(myNick);
               chat.setProfileUri(uri);
                chat.setMsg(msg);
                myRef.push().setValue(chat);
                //myRef.setValue("Hello, World!123");
                etChatMsg.setText("");
            }
        });

        List<Chat> chats = new ArrayList<>();
        chatAdapter = new ChatAdapter(ChatActivity.this,chats,nickname,photo);
        rvChat.setAdapter(chatAdapter);

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Log.d(TAG, "onChildAdded: 스냅샷 " + snapshot);
                Chat chat = snapshot.getValue(Chat.class);
                chatAdapter.addItem(chat);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}