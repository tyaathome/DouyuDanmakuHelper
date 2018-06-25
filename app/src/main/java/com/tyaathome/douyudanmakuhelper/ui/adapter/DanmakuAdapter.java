package com.tyaathome.douyudanmakuhelper.ui.adapter;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class DanmakuAdapter extends RecyclerView.Adapter<DanmakuAdapter.ViewHolder> {

    private List<String> messageList;

    public DanmakuAdapter(List<String> list) {
        messageList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TextView tv = new TextView(parent.getContext());
        tv.setTextColor(Color.BLACK);
        tv.setTextSize(20);
        return new ViewHolder(tv);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String message = messageList.get(position);
        if(!TextUtils.isEmpty(message)) {
            holder.tv.setText(message);
        }
    }

    @Override
    public int getItemCount() {
        return messageList != null ? messageList.size() : 0;
    }

    public void addMessage(String message) {
        messageList.add(message);
        notifyItemRangeInserted(messageList.size(), 1);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv;

        ViewHolder( View itemView) {
            super(itemView);
            tv = (TextView) itemView;
        }
    }

}
