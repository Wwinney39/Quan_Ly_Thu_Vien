package com.example.quan_ly_thu_vien.activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.quan_ly_thu_vien.R;
import com.example.quan_ly_thu_vien.dao.BookDAO;
import com.example.quan_ly_thu_vien.dao.TicketDAO;
import com.example.quan_ly_thu_vien.model.Book;
import com.example.quan_ly_thu_vien.model.Ticket;
import java.util.ArrayList;
import java.util.Calendar;

public class AddTicketActivity extends AppCompatActivity {
    Spinner spnBooks;
    EditText edtNguoiMuon, edtSdt, edtNgayMuon, edtNgayTra;
    TicketDAO ticketDAO;
    BookDAO bookDAO;
    ArrayList<Book> listAvailableBooks;
    ArrayList<String> listBookNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ticket);

        spnBooks = findViewById(R.id.spnBooks);
        edtNguoiMuon = findViewById(R.id.edtNguoiMuon);
        edtSdt = findViewById(R.id.edtSdt);
        edtNgayMuon = findViewById(R.id.edtNgayMuon);
        edtNgayTra = findViewById(R.id.edtNgayTra);

        ticketDAO = new TicketDAO(this);
        bookDAO = new BookDAO(this);

        // Đổ dữ liệu Sách Có sẵn lên Spinner
        listAvailableBooks = bookDAO.getAvailableBooks();
        listBookNames = new ArrayList<>();

        if (listAvailableBooks.isEmpty()) {
            Toast.makeText(this, "Hiện không có sách nào trống để mượn!", Toast.LENGTH_LONG).show();
            findViewById(R.id.btnSaveTicket).setEnabled(false);
        } else {
            for (Book b : listAvailableBooks) {
                listBookNames.add(b.getMaSach() + " - " + b.getTenSach());
            }
        }

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, listBookNames);
        spnBooks.setAdapter(spinnerAdapter);

        // Bật lịch chọn Ngày mượn khi bấm vào ô EditText
        edtNgayMuon.setOnClickListener(v -> showDatePicker(edtNgayMuon));
        edtNgayMuon.setFocusable(false); // Chặn không cho bàn phím ảo hiện lên che lịch

        // Bật lịch chọn Ngày trả khi bấm vào ô EditText
        edtNgayTra.setOnClickListener(v -> showDatePicker(edtNgayTra));
        edtNgayTra.setFocusable(false);

        // Xử lý nút Lưu phiếu
        findViewById(R.id.btnSaveTicket).setOnClickListener(v -> {
            String nguoiMuon = edtNguoiMuon.getText().toString().trim();
            String sdt = edtSdt.getText().toString().trim();
            String ngayMuon = edtNgayMuon.getText().toString().trim();
            String ngayTra = edtNgayTra.getText().toString().trim();

            if (nguoiMuon.isEmpty() || ngayMuon.isEmpty() || ngayTra.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đủ thông tin (Họ tên, ngày mượn, ngày trả)!", Toast.LENGTH_SHORT).show();
                return;
            }

            int selectedIndex = spnBooks.getSelectedItemPosition();
            if (selectedIndex < 0 || selectedIndex >= listAvailableBooks.size()) {
                Toast.makeText(this, "Vui lòng chọn sách mượn hợp lệ!", Toast.LENGTH_SHORT).show();
                return;
            }

            String maSachDuocChon = listAvailableBooks.get(selectedIndex).getMaSach();

            Ticket ticket = new Ticket(0, maSachDuocChon, nguoiMuon, sdt, ngayMuon, ngayTra, 0);

            if (ticketDAO.insertTicket(ticket)) {
                Toast.makeText(this, "Tạo phiếu mượn thành công!", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Có lỗi xảy ra khi mượn sách!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Hàm gọi hộp thoại lịch (DatePickerDialog)
    private void showDatePicker(EditText edtTarget) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year1, month1, dayOfMonth) -> {
            // Định dạng ngày theo kiểu dd/mm/yyyy
            String dateStr = String.format("%02d/%02d/%d", dayOfMonth, (month1 + 1), year1);
            edtTarget.setText(dateStr);
        }, year, month, day);
        datePickerDialog.show();
    }
}