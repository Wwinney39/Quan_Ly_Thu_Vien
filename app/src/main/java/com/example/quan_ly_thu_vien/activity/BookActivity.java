package com.example.quan_ly_thu_vien.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.quan_ly_thu_vien.R;
import com.example.quan_ly_thu_vien.adapter.BookAdapter;
import com.example.quan_ly_thu_vien.dao.BookDAO;
import com.example.quan_ly_thu_vien.model.Book;
import java.util.ArrayList;

public class BookActivity extends AppCompatActivity {
    RecyclerView rcvBooks;
    BookDAO bookDAO;
    ArrayList<Book> list;
    BookAdapter adapter;
    EditText edtSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        rcvBooks = findViewById(R.id.rcvBooks);
        edtSearch = findViewById(R.id.edtSearch);
        bookDAO = new BookDAO(this);

        rcvBooks.setLayoutManager(new LinearLayoutManager(this));
        loadData("");

        findViewById(R.id.fabAddBook).setOnClickListener(v -> {
            startActivity(new Intent(BookActivity.this, AddEditBookActivity.class));
        });

        // Xử lý tìm kiếm sách real-time
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                loadData(s.toString());
            }
            @Override public void afterTextChanged(Editable s) {}
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData(edtSearch.getText().toString()); // Reload danh sách khi thêm xong
    }

    private void loadData(String keyword) {
        list = bookDAO.getBooks(keyword);
        adapter = new BookAdapter(list);
        rcvBooks.setAdapter(adapter);
    }
}