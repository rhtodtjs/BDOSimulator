package com.kkk8888.bdosimulator;

/**
 * Created by alfo06-18 on 2017-07-24.
 */

public class CharInfomation {

    private String Name, DESC;
    private int img;

    public CharInfomation(String name, String DESC, int img) {
        Name = name;
        this.DESC = DESC;
        this.img = img;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDESC() {
        return DESC;
    }

    public void setDESC(String DESC) {
        this.DESC = DESC;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }
}
