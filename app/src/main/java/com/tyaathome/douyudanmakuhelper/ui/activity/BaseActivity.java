package com.tyaathome.douyudanmakuhelper.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.tyaathome.douyudanmakuhelper.impl.BaseView;
import com.tyaathome.douyudanmakuhelper.utils.manager.InjectManager;

/**
 * Created by tyaathome on 2018/06/25.
 */
public abstract class BaseActivity extends AppCompatActivity implements BaseView {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InjectManager.inject(this);
        initViews(savedInstanceState);
        initEventAndData(savedInstanceState);
    }
}
