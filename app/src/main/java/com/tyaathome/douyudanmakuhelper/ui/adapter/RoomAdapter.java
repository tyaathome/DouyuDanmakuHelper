package com.tyaathome.douyudanmakuhelper.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tyaathome.douyudanmakuhelper.R;
import com.tyaathome.douyudanmakuhelper.net.bean.room.Room;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by tyaathome on 2018/06/25.
 */
public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.ViewHolder> {

    private List<Room> roomList;
    private PublishSubject<Room> roomPublishSubject = PublishSubject.create();

    public RoomAdapter(List<Room> list) {
        roomList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_room, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Room room = roomList.get(position);
        holder.itemView.setOnClickListener(v -> roomPublishSubject.onNext(room));
        holder.tvName.setText(room.getNickname());
        Picasso.get().load(room.getRoom_src()).into(holder.iv);
    }

    @Override
    public int getItemCount() {
        return roomList != null ? roomList.size() : 0;
    }

    public Observable<Room> getClick() {
        return roomPublishSubject.hide();
    }

    public void update(List<Room> list) {
        roomList = list;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView iv;
        TextView tvName;
        public ViewHolder(View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.iv_bg);
            tvName = itemView.findViewById(R.id.tv_name);
        }
    }

}
