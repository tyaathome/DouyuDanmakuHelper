package com.tyaathome.douyudanmakuhelper.net.bean.room;

/**
 * Created by tyaathome on 2018/06/25.
 */
public class Room {

    private String owner_uid;

    private String nickname;

    private String room_name;

    private String room_src;

    private String room_id;

    private String hn;

    private String url;

    private String online;

    public String getOwner_uid ()
    {
        return owner_uid;
    }

    public void setOwner_uid (String owner_uid)
    {
        this.owner_uid = owner_uid;
    }

    public String getNickname ()
    {
        return nickname;
    }

    public void setNickname (String nickname)
    {
        this.nickname = nickname;
    }

    public String getRoom_name ()
    {
        return room_name;
    }

    public void setRoom_name (String room_name)
    {
        this.room_name = room_name;
    }

    public String getRoom_src ()
    {
        return room_src;
    }

    public void setRoom_src (String room_src)
    {
        this.room_src = room_src;
    }

    public String getRoom_id ()
    {
        return room_id;
    }

    public void setRoom_id (String room_id)
    {
        this.room_id = room_id;
    }

    public String getHn ()
    {
        return hn;
    }

    public void setHn (String hn)
    {
        this.hn = hn;
    }

    public String getUrl ()
    {
        return url;
    }

    public void setUrl (String url)
    {
        this.url = url;
    }

    public String getOnline ()
    {
        return online;
    }

    public void setOnline (String online)
    {
        this.online = online;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [owner_uid = "+owner_uid+", nickname = "+nickname+", room_name = "+room_name+", room_src = "+room_src+", room_id = "+room_id+", hn = "+hn+", url = "+url+", online = "+online+"]";
    }

}
