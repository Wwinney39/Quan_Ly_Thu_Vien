package com.example.quan_ly_thu_vien.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.quan_ly_thu_vien.R;
import com.example.quan_ly_thu_vien.adapter.TicketAdapter;
import com.example.quan_ly_thu_vien.dao.TicketDAO;
import com.example.quan_ly_thu_vien.model.Ticket;
import java.util.ArrayList;

public class TicketActivity extends AppCompatActivity {
    RecyclerView rcvTickets;
    TicketDAO ticketDAO;
    ArrayList<Ticket> list;
    TicketAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);

        rcvTickets = findViewById(R.id.rcvTickets);
        ticketDAO = new TicketDAO(this);

        rcvTickets.setLayoutManager(new LinearLayoutManager(this));

        findViewById(R.id.fabAddTicket).setOnClickListener(v -> {
            startActivity(new Intent(TicketActivity.this, AddTicketActivity.class));
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Load lại danh sách mỗi khi màn hình hiển thị (ví dụ thêm xong quay lại)
        list = ticketDAO.getAllTickets();
        adapter = new TicketAdapter(this, list);
        rcvTickets.setAdapter(adapter);
    }
}