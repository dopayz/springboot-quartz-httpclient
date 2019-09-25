package com.yudu.face.model;

public class UserInfo {
    private String userInfoId;
    private String name;
    private String userName;
    private String passWord;
    private int isValid;
    private String userType;
    private int orderNum;
    private String departmentId;
    private String contactListId;
    private int isCheckAttendance;
    private String duty;

    public String getUserInfoId() {
        return userInfoId;
    }

    public void setUserInfoId(String userInfoId) {
        this.userInfoId = userInfoId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public int getIsValid() {
        return isValid;
    }

    public void setIsValid(int isValid) {
        this.isValid = isValid;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getContactListId() {
        return contactListId;
    }

    public void setContactListId(String contactListId) {
        this.contactListId = contactListId;
    }

    public int getIsCheckAttendance() {
        return isCheckAttendance;
    }

    public void setIsCheckAttendance(int isCheckAttendance) {
        this.isCheckAttendance = isCheckAttendance;
    }

    public String getDuty() {
        return duty;
    }

    public void setDuty(String duty) {
        this.duty = duty;
    }
}

