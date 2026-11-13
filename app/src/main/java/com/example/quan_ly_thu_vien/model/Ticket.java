package com.example.quan_ly_thu_vien.model;

public class Ticket {
    private int maPhieu;
    private String maSach, nguoiMuon, sdt, ngayMuon, ngayTra;
    private int trangThaiPhieu; // 0: Đang mượn, 1: Đã trả

    public Ticket(int maPhieu, String maSach, String nguoiMuon, String sdt, String ngayMuon, String ngayTra, int trangThaiPhieu) {
        this.maPhieu = maPhieu;
        this.maSach = maSach;
        this.nguoiMuon = nguoiMuon;
        this.sdt = sdt;
        this.ngayMuon = ngayMuon;
        this.ngayTra = ngayTra;
        this.trangThaiPhieu = trangThaiPhieu;
    }
    // Tạo các getter tương tự như class Book
    public int getMaPhieu() { return maPhieu; }
    public String getMaSach() { return maSach; }
    public String getNguoiMuon() { return nguoiMuon; }
    public String getSdt() { return sdt; }
    public String getNgayMuon() { return ngayMuon; }
    public String getNgayTra() { return ngayTra; }
    public int getTrangThaiPhieu() { return trangThaiPhieu; }

    public void setTrangThaiPhieu(int trangThaiPhieu) {
        this.trangThaiPhieu = trangThaiPhieu;
    }
}