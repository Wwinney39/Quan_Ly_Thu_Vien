package com.example.quan_ly_thu_vien.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.quan_ly_thu_vien.R;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btnQuanLySach).setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, BookActivity.class));
        });

        findViewById(R.id.btnQuanLyMuonTra).setOnClickListener(v -> {
             startActivity(new Intent(MainActivity.this, TicketActivity.class));
         });
    }
}