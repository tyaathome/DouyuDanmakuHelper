package com.tyaathome.douyudanmakuhelper.net.bean.game;

import java.util.List;

/**
 * Created by tyaathome on 2018/06/25.
 */
public class GameBean {

    private String error;

    private List<Game> data;

    public String getError ()
    {
        return error;
    }

    public void setError (String error)
    {
        this.error = error;
    }

    public List<Game> getData ()
    {
        return data;
    }

    public void setData (List<Game> data)
    {
        this.data = data;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [error = "+error+", data = "+data+"]";
    }

}
