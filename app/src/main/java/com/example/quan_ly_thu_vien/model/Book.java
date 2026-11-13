package com.example.quan_ly_thu_vien.model;

public class Book {
    private String maSach, tenSach, tacGia, theLoai;
    private int trangThai; // 0: Có sẵn, 1: Đang mượn

    public Book(String maSach, String tenSach, String tacGia, String theLoai, int trangThai) {
        this.maSach = maSach;
        this.tenSach = tenSach;
        this.tacGia = tacGia;
        this.theLoai = theLoai;
        this.trangThai = trangThai;
    }

    public String getMaSach() { return maSach; }
    public String getTenSach() { return tenSach; }
    public String getTacGia() { return tacGia; }
    public String getTheLoai() { return theLoai; }
    public int getTrangThai() { return trangThai; }
}