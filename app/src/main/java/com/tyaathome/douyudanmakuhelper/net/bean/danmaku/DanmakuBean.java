package com.tyaathome.douyudanmakuhelper.net.bean.danmaku;

/**
 * Created by tyaathome on 2018/06/25.
 */
public class DanmakuBean {

    private String error;

    private Danmaku data;

    public String getError ()
    {
        return error;
    }

    public void setError (String error)
    {
        this.error = error;
    }

    public Danmaku getData ()
    {
        return data;
    }

    public void setData (Danmaku data)
    {
        this.data = data;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [error = "+error+", data = "+data+"]";
    }

}
