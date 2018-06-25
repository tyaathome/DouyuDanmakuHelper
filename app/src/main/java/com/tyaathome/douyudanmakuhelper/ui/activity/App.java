package com.tyaathome.douyudanmakuhelper.ui.activity;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.xuhao.android.libsocket.sdk.OkSocket;

public class App extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        OkSocket.initialize(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
