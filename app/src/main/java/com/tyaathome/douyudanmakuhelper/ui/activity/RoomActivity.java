package com.tyaathome.douyudanmakuhelper.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.tyaathome.douyudanmakuhelper.R;
import com.tyaathome.douyudanmakuhelper.net.bean.room.Room;
import com.tyaathome.douyudanmakuhelper.net.http.service.AppService;
import com.tyaathome.douyudanmakuhelper.ui.adapter.RoomAdapter;
import com.tyaathome.douyudanmakuhelper.ui.adapter.decoration.SpaceItemDecoration;
import com.tyaathome.douyudanmakuhelper.utils.CommonUtils;
import com.tyaathome.douyudanmakuhelper.utils.constant.BundleConstant;
import com.tyaathome.douyudanmakuhelper.utils.manager.LayoutID;

import java.util.ArrayList;
import java.util.List;

@LayoutID(R.layout.activity_room)
public class RoomActivity extends BaseActivity {

    private RecyclerView recyclerView;
    private RoomAdapter adapter;
    private List<Room> roomList = new ArrayList<>();

    @Override
    public void initViews(Bundle savedInstanceState) {
        initList();
    }

    @Override
    public void initEventAndData(Bundle savedInstanceState) {
        requestRoom();
    }

    private void requestRoom() {
        String id = getIntent().getStringExtra(BundleConstant.GAME_ID);
        if(!TextUtils.isEmpty(id)) {
            AppService.getInstance().getRoomList(id, roomBean -> {
                if(roomBean != null) {
                    adapter.update(roomBean.getData());
                }
            });
        }
    }

    @SuppressLint("CheckResult")
    private void initList() {
        recyclerView = findViewById(R.id.recyclerview);
        adapter = new RoomAdapter(roomList);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        int margin = (int) CommonUtils.dp2px(this, 10);
        recyclerView.addItemDecoration(new SpaceItemDecoration(margin, margin, 2));
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);
        adapter.getClick().subscribe(room -> {
            if(room != null) {
                gotoDanmaku(room.getRoom_id());
            }
        });
    }

    private void gotoDanmaku(String id) {
        Intent intent = new Intent(this, DanmakuActivity.class);
        intent.putExtra(BundleConstant.ROOM_ID, id);
        startActivity(intent);
    }
}
