package com.tyaathome.douyudanmakuhelper.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;

import com.tyaathome.douyudanmakuhelper.R;
import com.tyaathome.douyudanmakuhelper.net.http.service.AppService;
import com.tyaathome.douyudanmakuhelper.utils.constant.BundleConstant;
import com.tyaathome.douyudanmakuhelper.utils.manager.LayoutID;

@LayoutID(R.layout.activity_danmaku)
public class DanmakuActivity extends BaseActivity {

    @Override
    public void initViews(Bundle savedInstanceState) {

    }

    @Override
    public void initEventAndData(Bundle savedInstanceState) {
        getRoomDetail();
    }

    private void getRoomDetail() {
        String id = getIntent().getStringExtra(BundleConstant.ROOM_ID);
        if(!TextUtils.isEmpty(id)) {
            AppService.getInstance().getRoomDetail(id, danmakuBean -> {

            });
        }
    }
}
