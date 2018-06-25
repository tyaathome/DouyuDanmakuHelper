package com.tyaathome.douyudanmakuhelper.net.bean.room;

import java.util.List;

/**
 * Created by tyaathome on 2018/06/25.
 */
public class RoomBean {

    private String error;

    private List<Room> data;

    public String getError ()
    {
        return error;
    }

    public void setError (String error)
    {
        this.error = error;
    }

    public List<Room> getData ()
    {
        return data;
    }

    public void setData (List<Room> data)
    {
        this.data = data;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [error = "+error+", data = "+data+"]";
    }

}
