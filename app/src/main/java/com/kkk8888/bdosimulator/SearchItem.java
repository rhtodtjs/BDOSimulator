package com.kkk8888.bdosimulator;

/**
 * Created by alfo06-18 on 2017-08-08.
 */

public class SearchItem {

    String itemName;
    int _id;

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public SearchItem(String itemName, int _id) {

        this.itemName = itemName;
        this._id = _id;
    }

}
