package com.example.hp0331.asta.view;

/**
 * Created by hp0331 on 2017/2/7.
 */

public class BannerBean {
    private int drawableforint;
    private String drawableforurl;
    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getDrawableforint() {
        return drawableforint;
    }

    public void setDrawableforint(int drawableforint) {
        this.drawableforint = drawableforint;
    }

    public String getDrawableforurl() {
        return drawableforurl;
    }

    public void setDrawableforurl(String drawableforurl) {
        this.drawableforurl = drawableforurl;
    }

    @Override
    public String toString() {
        return "BannerBean{" +
                "drawableforint=" + drawableforint +
                ", drawableforurl='" + drawableforurl + ",type=" + type +
                '}';
    }
}
