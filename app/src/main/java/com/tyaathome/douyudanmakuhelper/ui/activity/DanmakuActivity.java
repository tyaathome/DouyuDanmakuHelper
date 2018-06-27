package com.tyaathome.douyudanmakuhelper.ui.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.tyaathome.douyudanmakuhelper.R;
import com.tyaathome.douyudanmakuhelper.net.tcp.mina.MinaService;
import com.tyaathome.douyudanmakuhelper.ui.adapter.DanmakuAdapter;
import com.tyaathome.douyudanmakuhelper.utils.constant.BundleConstant;
import com.tyaathome.douyudanmakuhelper.utils.manager.LayoutID;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@LayoutID(R.layout.activity_danmaku)
public class DanmakuActivity extends BaseActivity {

    private DanmakuAdapter adapter;
    private RecyclerView recyclerView;
    private List<String> messageList = new ArrayList<>();
    private MinaService service = new MinaService();

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
        if (!TextUtils.isEmpty(id)) {
//            AppService.getInstance().getRoomDetail(id, danmakuBean -> {
//                if (danmakuBean != null) {
//                    SocketService.getInstance().connect(connectionInfo -> {
//                        SocketService.getInstance().send(connectionInfo, "type@=loginreq/roomid@=" + id + "/");
//                        SocketService.getInstance().send(connectionInfo, "type@=joingroup/rid@=" + id + "/gid@=-9999/");
//                        SocketService.getInstance().send(connectionInfo, "type@=mrkl/");
////                        Observable.timer(45, TimeUnit.SECONDS).subscribe(aLong -> SocketService.getInstance().send
////                                ("type@=mrkl/"));
//
//                    }, bean -> {
//                        if (bean != null) {
//                            //runOnUiThread(() -> adapter.addMessage(s));
//                                switch (bean.type) {
//                                    case "loginres":
////                                        Observable.timer(10, TimeUnit.SECONDS).subscribe(aLong -> SocketService.getInstance().send
////                                                ("type@=logout/"));
//                                        break;
//                                    case "error":
//                                        //SocketService.getInstance().disconnect();
//                                        break;
//                                    case "chatmsg":
//                                        String name = Thread.currentThread().getName();
//                                        adapter.addMessage(bean.message);
//                                        break;
//                                }
//                        }
//                    });
//                }
//            });
            Observable.create(new ObservableOnSubscribe<Object>() {
                @SuppressLint("CheckResult")
                @Override
                public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
                    if(service == null) {
                        service = new MinaService();
                    }
                    service.connect();
                    service.send("type@=loginreq/roomid@=" + id + "/");
                    service.send("type@=joingroup/rid@=" + id + "/gid@=-9999/");
                    Observable.timer(45, TimeUnit.SECONDS).subscribe(aLong -> service.send("type@=mrkl/"));
                }
            })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<Object>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(Object o) {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onComplete() {

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
        //SocketService.getInstance().send("type@=logout/");
        //SocketService.getInstance().disconnect();
    }

    @Override
    public void onBackPressed() {
//        SocketService.getInstance().send("type@=logout/");
//        SocketService.getInstance().disconnect();
        if(service != null) {
            service.disconnect();
        }
        super.onBackPressed();
    }
}
