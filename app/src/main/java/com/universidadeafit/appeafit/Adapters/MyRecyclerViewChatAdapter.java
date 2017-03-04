package com.universidadeafit.appeafit.Adapters;

/**
 * Created by LUISM on 01/03/2017.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.universidadeafit.appeafit.Model.Message;
import com.universidadeafit.appeafit.R;

import java.util.ArrayList;

public class MyRecyclerViewChatAdapter extends RecyclerView.Adapter<MyRecyclerViewChatAdapter.ViewHolder> {

    private int SELF = 100;
    private ArrayList<Message> messageArrayList;
    private static MyClickListener myClickListener;

    public class ViewHolder extends RecyclerView.ViewHolder implements View
            .OnClickListener{
        TextView message;

        public ViewHolder(View view) {
            super(view);
            message = (TextView) itemView.findViewById(R.id.message);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            myClickListener.onItemClick(getAdapterPosition(), v);
        }
    }

    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public MyRecyclerViewChatAdapter(ArrayList<Message> messageArrayList) {
        this.messageArrayList=messageArrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;

        // view type is to identify where to render the chat message
        // left or right
        if (viewType == SELF ) {
            // user message
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.chat_item_user, parent, false);
        } else {
            // WatBot message
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.chat_item_watson, parent, false);
        }
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Message message = messageArrayList.get(position);
        message.setMessage(message.getMessage());
        holder.message.setText(message.getMessage());
    }


    @Override
    public int getItemViewType(int position) {
        Message message = messageArrayList.get(position);
        if (message.getId().equals("1")) {
            return SELF;
        }

        return position;
    }

    @Override
    public int getItemCount() {
        return messageArrayList.size();
    }


    public Message getObjeto(int position) {
        return messageArrayList.get(position);
    }


    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }
}