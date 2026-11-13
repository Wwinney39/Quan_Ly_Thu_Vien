package com.example.quan_ly_thu_vien.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
    public DbHelper(Context context) {
        super(context, "library_db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 1. Tạo bảng
        db.execSQL("CREATE TABLE tb_book (ma_sach TEXT PRIMARY KEY, ten_sach TEXT NOT NULL, tac_gia TEXT NOT NULL, the_loai TEXT, trang_thai INTEGER DEFAULT 0)");
        db.execSQL("CREATE TABLE tb_ticket (ma_phieu INTEGER PRIMARY KEY AUTOINCREMENT, ma_sach TEXT REFERENCES tb_book(ma_sach), nguoi_muon TEXT NOT NULL, sdt TEXT, ngay_muon TEXT, ngay_tra TEXT, trang_thai_phieu INTEGER DEFAULT 0)");

        // 2. Thêm dữ liệu mẫu cho bảng Sách
        // Cuốn B01 có trạng thái = 1 (Đang mượn), các cuốn khác = 0 (Có sẵn)
        db.execSQL("INSERT INTO tb_book VALUES ('B01', 'Lập trình Android cơ bản', 'Google', 'Công nghệ', 1)");
        db.execSQL("INSERT INTO tb_book VALUES ('B02', 'Cấu trúc dữ liệu và giải thuật', 'PTIT', 'Giáo trình', 0)");
        db.execSQL("INSERT INTO tb_book VALUES ('B03', 'Quản trị Mạng máy tính', 'Cisco', 'Công nghệ', 0)");
        db.execSQL("INSERT INTO tb_book VALUES ('B04', 'Đắc Nhân Tâm', 'Dale Carnegie', 'Kỹ năng sống', 0)");

        // 3. Thêm dữ liệu mẫu cho bảng Phiếu mượn (Mượn cuốn B01)
        db.execSQL("INSERT INTO tb_ticket(ma_sach, nguoi_muon, sdt, ngay_muon, ngay_tra, trang_thai_phieu) VALUES ('B01', 'Nguyễn Quang Hoàng', '0987654321', '13/11/2026', '20/11/2026', 0)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS tb_ticket");
        db.execSQL("DROP TABLE IF EXISTS tb_book");
        onCreate(db);
    }
}