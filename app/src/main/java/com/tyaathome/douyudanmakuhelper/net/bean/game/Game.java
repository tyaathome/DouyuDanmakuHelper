package com.tyaathome.douyudanmakuhelper.net.bean.game;

/**
 * Created by tyaathome on 2018/06/25.
 */
public class Game {

    private String game_url;

    private String game_src;

    private String cate_id;

    private String short_name;

    private String game_name;

    private String game_icon;

    public String getGame_url ()
    {
        return game_url;
    }

    public void setGame_url (String game_url)
    {
        this.game_url = game_url;
    }

    public String getGame_src ()
    {
        return game_src;
    }

    public void setGame_src (String game_src)
    {
        this.game_src = game_src;
    }

    public String getCate_id ()
    {
        return cate_id;
    }

    public void setCate_id (String cate_id)
    {
        this.cate_id = cate_id;
    }

    public String getShort_name ()
    {
        return short_name;
    }

    public void setShort_name (String short_name)
    {
        this.short_name = short_name;
    }

    public String getGame_name ()
    {
        return game_name;
    }

    public void setGame_name (String game_name)
    {
        this.game_name = game_name;
    }

    public String getGame_icon ()
    {
        return game_icon;
    }

    public void setGame_icon (String game_icon)
    {
        this.game_icon = game_icon;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [game_url = "+game_url+", game_src = "+game_src+", cate_id = "+cate_id+", short_name = "+short_name+", game_name = "+game_name+", game_icon = "+game_icon+"]";
    }

}
