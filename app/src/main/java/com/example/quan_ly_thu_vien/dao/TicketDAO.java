package com.example.quan_ly_thu_vien.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.quan_ly_thu_vien.database.DbHelper;
import com.example.quan_ly_thu_vien.model.Ticket;
import java.util.ArrayList;

public class TicketDAO {
    private SQLiteDatabase db;
    private Context context;

    public TicketDAO(Context context) {
        this.context = context;
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public boolean insertTicket(Ticket ticket) {
        ContentValues values = new ContentValues();
        values.put("ma_sach", ticket.getMaSach());
        values.put("nguoi_muon", ticket.getNguoiMuon());
        values.put("sdt", ticket.getSdt());
        values.put("ngay_muon", ticket.getNgayMuon());
        values.put("ngay_tra", ticket.getNgayTra());
        values.put("trang_thai_phieu", 0); // 0: Đang mượn

        long result = db.insert("tb_ticket", null, values);
        if (result != -1) {
            // Liên kết dữ liệu: Cập nhật sách thành trạng thái Đang mượn (1)
            BookDAO bookDAO = new BookDAO(context);
            bookDAO.updateBookStatus(ticket.getMaSach(), 1);
            return true;
        }
        return false;
    }

    public ArrayList<Ticket> getAllTickets() {
        ArrayList<Ticket> list = new ArrayList<>();
        Cursor c = db.rawQuery("SELECT * FROM tb_ticket ORDER BY ma_phieu DESC", null);
        if (c.moveToFirst()) {
            do {
                list.add(new Ticket(
                        c.getInt(0), c.getString(1), c.getString(2),
                        c.getString(3), c.getString(4), c.getString(5), c.getInt(6)
                ));
            } while (c.moveToNext());
        }
        c.close();
        return list;
    }

    public boolean returnBook(int maPhieu, String maSach) {
        ContentValues values = new ContentValues();
        values.put("trang_thai_phieu", 1); // 1: Đã trả
        int row = db.update("tb_ticket", values, "ma_phieu=?", new String[]{String.valueOf(maPhieu)});

        if (row > 0) {
            // Liên kết dữ liệu: Nhả sách về trạng thái Có sẵn (0)
            BookDAO bookDAO = new BookDAO(context);
            bookDAO.updateBookStatus(maSach, 0);
            return true;
        }
        return false;
    }
}