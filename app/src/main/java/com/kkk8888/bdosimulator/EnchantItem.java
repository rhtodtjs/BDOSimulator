package com.kkk8888.bdosimulator;

/**
 * Created by alfo06-18 on 2017-08-05.
 */

public class EnchantItem {

    private String itemType = "";
    private String imgUrl = "";
    private String tableName = "";
    private String subType = "";

    private int grade = 0; // 아이템의 등급..녹템 초템 파템
    private int maxGrade = 0; //아이템의 최대 강화 단계..
    private int minDMG = 0; // 아이템의 방어력 증가량
    private int maxDMG = 0;// 아이템의 공격력 증가량
    private int nowGrade = 0; //아이템의 현재 강화 단계..
    private int baseAp = 0; // 아이템의 베이스 공격력..

    int idForSelect = 0;


    private String getSubType() {
        return subType;
    }

    private void setSubType(String subType) {
        this.subType = subType;
    }

    private int getBaseAp() {
        return baseAp;

    }

    private void setBaseAp(int baseAp) {
        this.baseAp = baseAp;
    }


    private String getTableName() {
        return tableName;
    }

    private void setTableName(String tableName) {
        this.tableName = tableName;
    }

    private int getNowGrade() {
        return nowGrade;
    }

    private void setNowGrade(int nowGrade) {
        this.nowGrade = nowGrade;
    }

    private String getItemType() {
        return itemType;
    }

    private void setItemType(String itemType) {
        this.itemType = itemType;
    }

    private String getImgUrl() {
        return imgUrl;
    }

    private void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    private int getGrade() {
        return grade;
    }

    private void setGrade(int grade) {
        this.grade = grade;
    }

    private int getMaxGrade() {
        return maxGrade;
    }

    private void setMaxGrade(int maxGrade) {
        this.maxGrade = maxGrade;
    }

    private int getMinDMG() {
        return minDMG;
    }

    private void setMinDMG(int minDMG) {
        this.minDMG = minDMG;
    }

    private int getMaxDMG() {
        return maxDMG;
    }

    private void setMaxDMG(int maxDMG) {
        this.maxDMG = maxDMG;
    }

    private EnchantItem(String itemType, String imgUrl, int grade, int maxGrade, int minDMG, int maxDMG, int nowGrade, String tableName, String subType , int idForSelect) {
        this.itemType = itemType;
        this.imgUrl = imgUrl;
        this.grade = grade;
        this.maxGrade = maxGrade;
        this.minDMG = minDMG;
        this.maxDMG = maxDMG;
        this.nowGrade = nowGrade;
        this.tableName = tableName;
        this.subType = subType;
        this.idForSelect = idForSelect;
    }


}
