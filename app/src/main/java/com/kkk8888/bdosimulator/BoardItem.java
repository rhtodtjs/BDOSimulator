package com.kkk8888.bdosimulator;

/**
 * Created by alfo06-18 on 2017-08-23.
 */

public class BoardItem {

    private String writer, text, content, imgUrl;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public BoardItem(String writer, String text, String content, String imgUrl) {
        this.writer = writer;
        this.text = text;
        this.content = content;
        this.imgUrl = imgUrl;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public BoardItem(String writer, String text) {
        this.writer = writer;
        this.text = text;
    }
}
