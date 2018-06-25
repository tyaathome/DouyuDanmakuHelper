package com.tyaathome.douyudanmakuhelper.net.bean.danmaku;

/**
 * Created by tyaathome on 2018/06/25.
 */
public class Danmaku {

    private String owner_weight;

    private String room_status;

    private Gift[] gift;

    private String owner_name;

    private String room_name;

    private String start_time;

    private String cate_id;

    private String room_id;

    private String hn;

    private String avatar;

    private String fans_num;

    private String online;

    private String cate_name;

    private String room_thumb;

    public String getOwner_weight ()
    {
        return owner_weight;
    }

    public void setOwner_weight (String owner_weight)
    {
        this.owner_weight = owner_weight;
    }

    public String getRoom_status ()
    {
        return room_status;
    }

    public void setRoom_status (String room_status)
    {
        this.room_status = room_status;
    }

    public Gift[] getGift ()
    {
        return gift;
    }

    public void setGift (Gift[] gift)
    {
        this.gift = gift;
    }

    public String getOwner_name ()
    {
        return owner_name;
    }

    public void setOwner_name (String owner_name)
    {
        this.owner_name = owner_name;
    }

    public String getRoom_name ()
    {
        return room_name;
    }

    public void setRoom_name (String room_name)
    {
        this.room_name = room_name;
    }

    public String getStart_time ()
    {
        return start_time;
    }

    public void setStart_time (String start_time)
    {
        this.start_time = start_time;
    }

    public String getCate_id ()
    {
        return cate_id;
    }

    public void setCate_id (String cate_id)
    {
        this.cate_id = cate_id;
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

    public String getAvatar ()
    {
        return avatar;
    }

    public void setAvatar (String avatar)
    {
        this.avatar = avatar;
    }

    public String getFans_num ()
    {
        return fans_num;
    }

    public void setFans_num (String fans_num)
    {
        this.fans_num = fans_num;
    }

    public String getOnline ()
    {
        return online;
    }

    public void setOnline (String online)
    {
        this.online = online;
    }

    public String getCate_name ()
    {
        return cate_name;
    }

    public void setCate_name (String cate_name)
    {
        this.cate_name = cate_name;
    }

    public String getRoom_thumb ()
    {
        return room_thumb;
    }

    public void setRoom_thumb (String room_thumb)
    {
        this.room_thumb = room_thumb;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [owner_weight = "+owner_weight+", room_status = "+room_status+", gift = "+gift+", owner_name = "+owner_name+", room_name = "+room_name+", start_time = "+start_time+", cate_id = "+cate_id+", room_id = "+room_id+", hn = "+hn+", avatar = "+avatar+", fans_num = "+fans_num+", online = "+online+", cate_name = "+cate_name+", room_thumb = "+room_thumb+"]";
    }

}
