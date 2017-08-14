package com.kkk8888.bdosimulator;

/**
 * Created by alfo06-18 on 2017-08-05.
 */

public class EnchantItem {

    private String itemType = "";


    private String imgUrl = "";
    private String tableName = ""; // (방어구 , 악세 , 무기)
    private String subType = ""; //아이템 종류 (GREATSWORD , IRONGLOVE ..)

    private int grade = 0; // 아이템의 등급..녹템 초템 파템
    private int maxGrade = 0; //아이템의 최대 강화 단계..
    private int minDMG = 0; // 아이템의 방어력 증가량
    private int maxDMG = 0;// 아이템의 공격력 증가량
    private int nowGrade = 0; //아이템의 현재 강화 단계..
    private int baseAp = 0; // 아이템의 베이스 공격력..
    private int needItemID = 0;

    public int getNeedItemID() {
        return needItemID;
    }

    public void setNeedItemID(int needItemID) {
        this.needItemID = needItemID;
    }

    public int getIdForSelect() {
        return idForSelect;
    }

    public void setIdForSelect(int idForSelect) {
        this.idForSelect = idForSelect;
    }

    public int getGradeID() {
        return gradeID;
    }

    public void setGradeID(int gradeID) {
        this.gradeID = gradeID;
    }

    int idForSelect = 0;
    private int itemId = 0;
    private int gradeID = 0;

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    public int getBaseAp() {
        return baseAp;

    }

    public void setBaseAp(int baseAp) {
        this.baseAp = baseAp;
    }


    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public int getNowGrade() {
        return nowGrade;
    }

    public void setNowGrade(int nowGrade) {
        this.nowGrade = nowGrade;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public int getMaxGrade() {
        return maxGrade;
    }

    public void setMaxGrade(int maxGrade) {
        this.maxGrade = maxGrade;
    }

    public int getMinDMG() {
        return minDMG;
    }

    public void setMinDMG(int minDMG) {
        this.minDMG = minDMG;
    }

    public int getMaxDMG() {
        return maxDMG;
    }

    public void setMaxDMG(int maxDMG) {
        this.maxDMG = maxDMG;
    }

    public EnchantItem(String itemType, String imgUrl, int grade, int maxGrade, int minDMG, int maxDMG, int nowGrade, String tableName, String subType, int idForSelect, int gradeID) {
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
        this.gradeID = gradeID;
    }

    public void success() {
        nowGrade++;
    }


}
