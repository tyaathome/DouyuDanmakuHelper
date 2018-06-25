package com.tyaathome.douyudanmakuhelper.net.http.service;

import com.tyaathome.douyudanmakuhelper.net.bean.danmaku.DanmakuBean;
import com.tyaathome.douyudanmakuhelper.net.bean.game.GameBean;
import com.tyaathome.douyudanmakuhelper.net.bean.room.RoomBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by tyaathome on 2018/06/25.
 */
public interface AppAPI {

    @GET("game")
    Observable<GameBean> getGame();

    @GET("live/{id}")
    Observable<RoomBean> getRoomList(@Path("id") String id);

    @GET("room/{id}")
    Observable<DanmakuBean> getRoomDetail(@Path("id") String id);
}
