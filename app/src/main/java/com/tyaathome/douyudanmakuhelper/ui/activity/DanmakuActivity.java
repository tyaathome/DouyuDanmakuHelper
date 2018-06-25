package com.tyaathome.douyudanmakuhelper.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.tyaathome.douyudanmakuhelper.R;
import com.tyaathome.douyudanmakuhelper.net.http.service.AppService;
import com.tyaathome.douyudanmakuhelper.net.tcp.SocketService;
import com.tyaathome.douyudanmakuhelper.ui.adapter.DanmakuAdapter;
import com.tyaathome.douyudanmakuhelper.utils.constant.BundleConstant;
import com.tyaathome.douyudanmakuhelper.utils.manager.LayoutID;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;

@LayoutID(R.layout.activity_danmaku)
public class DanmakuActivity extends BaseActivity {

    private DanmakuAdapter adapter;
    private RecyclerView recyclerView;
    private List<String> messageList = new ArrayList<>();

    @Override
    public void initViews(Bundle savedInstanceState) {
        initList();
    }

    @Override
    public void initEventAndData(Bundle savedInstanceState) {
        getRoomDetail();
    }

    private void getRoomDetail() {
        String id = getIntent().getStringExtra(BundleConstant.ROOM_ID);
        if(!TextUtils.isEmpty(id)) {
            AppService.getInstance().getRoomDetail(id, danmakuBean -> {
                if(danmakuBean != null) {
                    SocketService.getInstance().connect(connectionInfo -> {
                        SocketService.getInstance().send("type@=loginreq/roomid@=" + id + "/");
                        SocketService.getInstance().send("type@=joingroup/rid@=" + id + "/gid@=-9999/");
                        Observable.timer(45, TimeUnit.SECONDS).subscribe(aLong -> SocketService.getInstance().send("type@=mrkl/"));

                    }, s -> {
                        if (!TextUtils.isEmpty(s)) {
                            runOnUiThread(() -> adapter.addMessage(s));

                        }
                    });
                }
            });
        }
    }

    private void initList() {
        recyclerView = findViewById(R.id.recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new DanmakuAdapter(messageList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        SocketService.getInstance().send("type@=logout/");
        //SocketService.getInstance().disconnect();
    }
}
