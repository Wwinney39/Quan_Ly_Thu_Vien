package com.example.quan_ly_thu_vien.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.quan_ly_thu_vien.database.DbHelper;
import com.example.quan_ly_thu_vien.model.Book;
import java.util.ArrayList;

public class BookDAO {
    private SQLiteDatabase db;

    public BookDAO(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public boolean insertBook(Book book) {
        Cursor c = db.rawQuery("SELECT * FROM tb_book WHERE ma_sach = ?", new String[]{book.getMaSach()});
        if (c.getCount() > 0) { c.close(); return false; } // Trùng mã
        c.close();

        ContentValues values = new ContentValues();
        values.put("ma_sach", book.getMaSach());
        values.put("ten_sach", book.getTenSach());
        values.put("tac_gia", book.getTacGia());
        values.put("the_loai", book.getTheLoai());
        values.put("trang_thai", 0);
        return db.insert("tb_book", null, values) != -1;
    }

    public ArrayList<Book> getBooks(String keyword) {
        ArrayList<Book> list = new ArrayList<>();
        Cursor c;
        if (keyword.isEmpty()) {
            c = db.rawQuery("SELECT * FROM tb_book", null);
        } else {
            c = db.rawQuery("SELECT * FROM tb_book WHERE ten_sach LIKE ? OR tac_gia LIKE ?", new String[]{"%" + keyword + "%", "%" + keyword + "%"});
        }
        if (c.moveToFirst()) {
            do {
                list.add(new Book(c.getString(0), c.getString(1), c.getString(2), c.getString(3), c.getInt(4)));
            } while (c.moveToNext());
        }
        c.close();
        return list;
    }

    // Lấy danh sách các sách Có sẵn (trạng thái = 0)
    public ArrayList<Book> getAvailableBooks() {
        ArrayList<Book> list = new ArrayList<>();
        Cursor c = db.rawQuery("SELECT * FROM tb_book WHERE trang_thai = 0", null);
        if (c.moveToFirst()) {
            do {
                list.add(new Book(c.getString(0), c.getString(1), c.getString(2), c.getString(3), c.getInt(4)));
            } while (c.moveToNext());
        }
        c.close();
        return list;
    }

    public boolean updateBookStatus(String maSach, int status) {
        ContentValues values = new ContentValues();
        values.put("trang_thai", status);
        return db.update("tb_book", values, "ma_sach=?", new String[]{maSach}) > 0;
    }
}