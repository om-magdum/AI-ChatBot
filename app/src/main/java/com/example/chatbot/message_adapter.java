package com.example.chatbot;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class message_adapter extends RecyclerView.Adapter<message_adapter.myViewholder> {

    List<message> messageList;
    public message_adapter(List<message> Messagelist) {
        this.messageList = Messagelist;
    }

    @NonNull
    @Override
    public myViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View chatview = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_design,null);
        myViewholder myViewholder = new myViewholder(chatview);
        return myViewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull myViewholder holder, int position) {
        message messageee = messageList.get(position);

          holder.usertxt.setText(messageee.getQry());
          holder.bottxt.setText(messageee.getResponse());


    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    public class myViewholder extends RecyclerView.ViewHolder{

        LinearLayout botchat, userchat;
        TextView bottxt, usertxt;

        public myViewholder(@NonNull View itemView) {
            super(itemView);

            botchat = itemView.findViewById(R.id.linlayout);
            userchat = itemView.findViewById(R.id.linlayout2);
            bottxt = itemView.findViewById(R.id.chattext1);
            usertxt = itemView.findViewById(R.id.chattext2);
        }
    }

}
