package com.healthexpert.doctor.adapters;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.healthexpert.R;
import com.healthexpert.common.Config;
import com.healthexpert.data.remote.models.response.Messages;
import com.healthexpert.ui.widgets.BaseTextView;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Archish on 2/11/2018.
 */

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MessageViewHolder> {

    private List<Messages> mMessageList;
    private DatabaseReference mUserDatabase;

    public ChatAdapter(List<Messages> mMessageList) {

        this.mMessageList = mMessageList;

    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_chat, parent, false);

        return new MessageViewHolder(v);

    }

    public class MessageViewHolder extends RecyclerView.ViewHolder {

        public BaseTextView tvChat;
        public CircleImageView ivProfile;
        public BaseTextView tvName;

        public MessageViewHolder(View view) {
            super(view);

            tvChat = (BaseTextView) view.findViewById(R.id.tvChat);
            ivProfile = (CircleImageView) view.findViewById(R.id.ivProfile);

        }
    }

    @Override
    public void onBindViewHolder(final MessageViewHolder viewHolder, int i) {

        Messages c = mMessageList.get(i);
        String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String from_user = c.getFrom();
        if (from_user.equals(currentUserId)) {
            viewHolder.tvChat.setBackgroundColor(Color.WHITE);
            viewHolder.tvChat.setTextColor(Color.BLACK);

        } else {
            viewHolder.tvChat.setBackgroundResource(R.drawable.chat_background);
            viewHolder.tvChat.setTextColor(Color.WHITE);


        }

        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(from_user);


        mUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String image = dataSnapshot.child("image").getValue().toString();
                Picasso.with(viewHolder.ivProfile.getContext()).load(Config.BASE_URL + image).resize(128, 128)
                        .placeholder(R.drawable.avatar).into(viewHolder.ivProfile);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        viewHolder.tvChat.setText(c.getMessage());

    }

    @Override
    public int getItemCount() {
        return mMessageList.size();
    }


}