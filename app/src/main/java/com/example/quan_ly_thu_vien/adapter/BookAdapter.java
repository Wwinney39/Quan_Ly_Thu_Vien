package com.example.quan_ly_thu_vien.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.quan_ly_thu_vien.R;
import com.example.quan_ly_thu_vien.model.Book;
import java.util.ArrayList;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {
    private ArrayList<Book> list;

    public BookAdapter(ArrayList<Book> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book book = list.get(position);
        holder.tvTenSach.setText(book.getTenSach());
        holder.tvMaSach.setText("Mã: " + book.getMaSach());
        holder.tvTacGia.setText("Tác giả: " + book.getTacGia());

        if (book.getTrangThai() == 0) {
            holder.tvTrangThai.setText("Có sẵn");
            holder.tvTrangThai.setTextColor(Color.GREEN);
        } else {
            holder.tvTrangThai.setText("Đang mượn");
            holder.tvTrangThai.setTextColor(Color.RED);
        }
    }

    @Override
    public int getItemCount() { return list.size(); }

    public static class BookViewHolder extends RecyclerView.ViewHolder {
        TextView tvTenSach, tvMaSach, tvTacGia, tvTrangThai;
        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTenSach = itemView.findViewById(R.id.tvTenSach);
            tvMaSach = itemView.findViewById(R.id.tvMaSach);
            tvTacGia = itemView.findViewById(R.id.tvTacGia);
            tvTrangThai = itemView.findViewById(R.id.tvTrangThai);
        }
    }
}