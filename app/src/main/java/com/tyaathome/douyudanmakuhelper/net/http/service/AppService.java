package com.tyaathome.douyudanmakuhelper.net.http.service;

import com.tyaathome.douyudanmakuhelper.impl.OnCall;
import com.tyaathome.douyudanmakuhelper.net.bean.danmaku.DanmakuBean;
import com.tyaathome.douyudanmakuhelper.net.bean.game.GameBean;
import com.tyaathome.douyudanmakuhelper.net.bean.room.RoomBean;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * app service
 * Created by tyaathome on 2016/4/15.
 */
public class AppService {
    public static AppService instance = null;

    private Retrofit mRetrofit = null;
    private AppAPI mApi = null;

    private AppService() {
        init();
    }

    public static AppService getInstance() {
        if (instance == null) {
            instance = new AppService();
        }
        return instance;
    }

    private void init() {
        if (mRetrofit == null) {
            create();
        }
        if (mApi == null && mRetrofit != null) {
            mApi = mRetrofit.create(AppAPI.class);
        }
    }

    public Retrofit create() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl("http://open.douyucdn.cn/api/RoomApi/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return mRetrofit;
    }

    public AppAPI getApi() {
        if(mApi == null) {
            init();
        }
        return mApi;
    }

    public void getGame(OnCall<GameBean> call) {
        init();
        mApi.getGame()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GameBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(GameBean gameBean) {
                        if(call != null) {
                            call.onCall(gameBean);
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

    public void getRoomList(String id, OnCall<RoomBean> call) {
        init();
        mApi.getRoomList(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RoomBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(RoomBean roomBean) {
                        if(call != null) {
                            call.onCall(roomBean);
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

    public void getRoomDetail(String id, OnCall<DanmakuBean> call) {
        init();
        mApi.getRoomDetail(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DanmakuBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(DanmakuBean danmakuBean) {
                        if(call != null) {
                            call.onCall(danmakuBean);
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
