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
import com.tyaathome.douyudanmakuhelper.net.bean.game.Game;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by tyaathome on 2018/06/25.
 */
public class GameAdapter extends RecyclerView.Adapter<GameAdapter.ViewHolder> {

    private List<Game> gameList;
    private PublishSubject<Game> gamePublishSubject = PublishSubject.create();

    public GameAdapter(List<Game> list) {
        this.gameList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_game, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Game game = gameList.get(position);
        holder.tvName.setText(game.getGame_name());
        holder.itemView.setOnClickListener(v -> gamePublishSubject.onNext(game));
        Picasso.get().load(game.getGame_icon()).into(holder.ivGame);
    }

    @Override
    public int getItemCount() {
        return gameList != null ? gameList.size() : 0;
    }

    public Observable<Game> getClick() {
        return gamePublishSubject.hide();
    }

    public void update(List<Game> list) {
        this.gameList = list;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvName;
        ImageView ivGame;
        public ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            ivGame = itemView.findViewById(R.id.iv_game);
        }
    }

}
