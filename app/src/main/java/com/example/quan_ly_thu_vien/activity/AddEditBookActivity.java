package com.example.quan_ly_thu_vien.activity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.quan_ly_thu_vien.R;
import com.example.quan_ly_thu_vien.dao.BookDAO;
import com.example.quan_ly_thu_vien.model.Book;

public class AddEditBookActivity extends AppCompatActivity {
    EditText edtMaSach, edtTenSach, edtTacGia;
    Spinner spnTheLoai; // Đã đổi từ EditText sang Spinner
    BookDAO bookDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_book);

        edtMaSach = findViewById(R.id.edtMaSach);
        edtTenSach = findViewById(R.id.edtTenSach);
        edtTacGia = findViewById(R.id.edtTacGia);
        spnTheLoai = findViewById(R.id.spnTheLoai); // Ánh xạ Spinner
        bookDAO = new BookDAO(this);

        // Tạo danh sách các thể loại có sẵn để người dùng chọn
        String[] danhSachTheLoai = {"Công nghệ", "Giáo trình", "Văn học", "Kỹ năng sống", "Tiểu thuyết", "Khác"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, danhSachTheLoai);
        spnTheLoai.setAdapter(adapter);

        findViewById(R.id.btnSaveBook).setOnClickListener(v -> {
            String ma = edtMaSach.getText().toString().trim();
            String ten = edtTenSach.getText().toString().trim();
            String tacGia = edtTacGia.getText().toString().trim();

            // Lấy text từ mục đang được chọn trong Spinner
            String theLoai = spnTheLoai.getSelectedItem().toString();

            if (ma.isEmpty() || ten.isEmpty() || tacGia.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đủ Mã, Tên sách và Tác giả!", Toast.LENGTH_SHORT).show();
                return;
            }

            Book book = new Book(ma, ten, tacGia, theLoai, 0);
            if (bookDAO.insertBook(book)) {
                Toast.makeText(this, "Thêm sách thành công!", Toast.LENGTH_SHORT).show();
                finish(); // Đóng form, về danh sách
            } else {
                Toast.makeText(this, "Mã sách đã tồn tại!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}