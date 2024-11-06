package com.example.btvn_lab1.Users;

public class User {
    private String maUser;
    private String ho;
    private String ten;
    private String namSinh;
    private String que;

    public User(String maUser, String ho, String ten, String namSinh, String que) {
        this.maUser = maUser;
        this.ho = ho;
        this.ten = ten;
        this.namSinh = namSinh;
        this.que = que;
    }

    public User() {
    }

    public String getMaUser() {
        return maUser;
    }

    public void setMaUser(String maUser) {
        this.maUser = maUser;
    }

    public String getHo() {
        return ho;
    }

    public void setHo(String ho) {
        this.ho = ho;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getNamSinh() {
        return namSinh;
    }

    public void setNamSinh(String namSinh) {
        this.namSinh = namSinh;
    }

    public String getQue() {
        return que;
    }

    public void setQue(String que) {
        this.que = que;
    }
}
