package com.tyaathome.douyudanmakuhelper.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;

import com.tyaathome.douyudanmakuhelper.R;
import com.tyaathome.douyudanmakuhelper.net.tcp.mina.MinaService;
import com.tyaathome.douyudanmakuhelper.ui.adapter.DanmakuAdapter;
import com.tyaathome.douyudanmakuhelper.utils.MessageUtils;
import com.tyaathome.douyudanmakuhelper.utils.constant.BundleConstant;
import com.tyaathome.douyudanmakuhelper.utils.manager.LayoutID;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
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
    private MinaService service;
    private Disposable heartDisposable;

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
            Observable.create((ObservableOnSubscribe<String>) emitter -> {
                if(service == null) {
                    service = new MinaService(emitter);
                }
                //service.setHeartBeat("type@=mrkl/");
                service.connect();
                service.send("type@=loginreq/roomid@=" + id + "/");
                service.send("type@=joingroup/rid@=" + id + "/gid@=-9999/");
                //service.send("type@=mrkl/");
                Observable.timer(45, TimeUnit.SECONDS).subscribe(aLong -> service.send("type@=mrkl/"));
                heartDisposable = Observable.interval(0, 10, TimeUnit.SECONDS)
                        .subscribe(aLong -> {
                            System.out.println("心跳包发送 : " + aLong);
                            service.send("type@=mrkl/");
                        });
            })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<String>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(String s) {
                            if(!TextUtils.isEmpty(s)) {
                                Log.e("ClientHandler", s);
                                MessageUtils.MessageBean bean = MessageUtils.receive(s.getBytes());
                                if (bean != null) {
                                    switch (bean.type) {
                                        case "type@=loginres":
//                                        Observable.timer(10, TimeUnit.SECONDS).subscribe(aLong -> SocketService
// .getInstance().send
//                                                ("type@=logout/"));
                                            break;
                                        case "type@=error":
                                            //SocketService.getInstance().disconnect();
                                            break;
                                        case "type@=chatmsg":
                                            String name = Thread.currentThread().getName();
                                            adapter.addMessage(bean.message);
                                            break;
                                    }
                                }
                            }
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
        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                recyclerView.smoothScrollToPosition(positionStart - 1);
                //recyclerView.scrollToPosition(positionStart-1);
            }
        });
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
        if(heartDisposable != null && !heartDisposable.isDisposed()) {
            heartDisposable.dispose();
            heartDisposable = null;
        }
        super.onBackPressed();
    }
}
