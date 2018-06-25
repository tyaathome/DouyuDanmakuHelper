package com.tyaathome.douyudanmakuhelper.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.tyaathome.douyudanmakuhelper.R;
import com.tyaathome.douyudanmakuhelper.net.bean.game.Game;
import com.tyaathome.douyudanmakuhelper.net.http.service.AppService;
import com.tyaathome.douyudanmakuhelper.ui.adapter.GameAdapter;
import com.tyaathome.douyudanmakuhelper.ui.adapter.decoration.SpaceItemDecoration;
import com.tyaathome.douyudanmakuhelper.utils.CommonUtils;
import com.tyaathome.douyudanmakuhelper.utils.constant.BundleConstant;
import com.tyaathome.douyudanmakuhelper.utils.manager.LayoutID;

import java.util.ArrayList;
import java.util.List;

@LayoutID(R.layout.activity_main)
public class MainActivity extends BaseActivity {

    private RecyclerView recyclerView;
    private GameAdapter adapter;
    private List<Game> gameList = new ArrayList<>();

    @Override
    public void initViews(Bundle savedInstanceState) {
        initList();
    }

    @Override
    public void initEventAndData(Bundle savedInstanceState) {
        requestGame();
    }

    private void requestGame() {
        AppService.getInstance().getGame(gameCategory -> {
            if(gameCategory != null) {
                adapter.update(gameCategory.getData());
            }
        });
    }

    @SuppressLint("CheckResult")
    private void initList() {
        recyclerView = findViewById(R.id.recyclerview);
        adapter = new GameAdapter(gameList);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
        int margin = (int) CommonUtils.dp2px(this, 10);
        recyclerView.addItemDecoration(new SpaceItemDecoration(margin, margin, 4));
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);
        adapter.getClick().subscribe(game -> {
            if(game != null) {
                gotoRoom(game.getCate_id());
            }
        });
    }

    private void gotoRoom(String id) {
        Intent intent = new Intent(this, RoomActivity.class);
        intent.putExtra(BundleConstant.GAME_ID, id);
        startActivity(intent);
    }
}
