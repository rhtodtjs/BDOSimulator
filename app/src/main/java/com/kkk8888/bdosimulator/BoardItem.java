package com.kkk8888.bdosimulator;

/**
 * Created by alfo06-18 on 2017-08-23.
 */

public class BoardItem {

    private String writer, text, content, imgUrl, GM;

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

    public String getGM() {
        return GM;
    }

    public void setGM(String GM) {
        this.GM = GM;
    }

    public BoardItem(String writer, String text, String content, String imgUrl, String GM) {
        this.writer = writer;
        this.text = text;
        this.content = content;
        this.imgUrl = imgUrl;
        this.GM = GM;
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
