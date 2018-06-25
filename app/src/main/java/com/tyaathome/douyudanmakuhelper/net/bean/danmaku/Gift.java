package com.tyaathome.douyudanmakuhelper.net.bean.danmaku;

/**
 * Created by tyaathome on 2018/06/25.
 */
public class Gift {

    private String id;

    private String desc;

    private String gx;

    private String pc;

    private String name;

    private String mimg;

    private String himg;

    private String type;

    private String intro;

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getDesc ()
    {
        return desc;
    }

    public void setDesc (String desc)
    {
        this.desc = desc;
    }

    public String getGx ()
    {
        return gx;
    }

    public void setGx (String gx)
    {
        this.gx = gx;
    }

    public String getPc ()
    {
        return pc;
    }

    public void setPc (String pc)
    {
        this.pc = pc;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getMimg ()
    {
        return mimg;
    }

    public void setMimg (String mimg)
    {
        this.mimg = mimg;
    }

    public String getHimg ()
    {
        return himg;
    }

    public void setHimg (String himg)
    {
        this.himg = himg;
    }

    public String getType ()
    {
        return type;
    }

    public void setType (String type)
    {
        this.type = type;
    }

    public String getIntro ()
    {
        return intro;
    }

    public void setIntro (String intro)
    {
        this.intro = intro;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [id = "+id+", desc = "+desc+", gx = "+gx+", pc = "+pc+", name = "+name+", mimg = "+mimg+", himg = "+himg+", type = "+type+", intro = "+intro+"]";
    }

}
