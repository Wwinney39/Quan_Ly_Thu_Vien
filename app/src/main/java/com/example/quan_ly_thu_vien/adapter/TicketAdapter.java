package com.example.quan_ly_thu_vien.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.quan_ly_thu_vien.R;
import com.example.quan_ly_thu_vien.dao.TicketDAO;
import com.example.quan_ly_thu_vien.model.Ticket;
import java.util.ArrayList;

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.TicketViewHolder> {
    private Context context;
    private ArrayList<Ticket> list;
    private TicketDAO ticketDAO;

    public TicketAdapter(Context context, ArrayList<Ticket> list) {
        this.context = context;
        this.list = list;
        this.ticketDAO = new TicketDAO(context);
    }

    @NonNull
    @Override
    public TicketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_ticket, parent, false);
        return new TicketViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TicketViewHolder holder, int position) {
        Ticket ticket = list.get(position);
        holder.tvMaPhieu.setText("Phiếu số: " + ticket.getMaPhieu());
        holder.tvNguoiMuon.setText("Người mượn: " + ticket.getNguoiMuon() + " (" + ticket.getSdt() + ")");
        holder.tvMaSach.setText("Mã sách mượn: " + ticket.getMaSach());
        holder.tvThoiGian.setText("Từ: " + ticket.getNgayMuon() + " - Đến: " + ticket.getNgayTra());

        if (ticket.getTrangThaiPhieu() == 0) {
            holder.tvTrangThai.setText("Trạng thái: Đang mượn");
            holder.tvTrangThai.setTextColor(Color.RED);
            holder.btnTraSach.setVisibility(View.VISIBLE);
        } else {
            holder.tvTrangThai.setText("Trạng thái: Đã trả");
            holder.tvTrangThai.setTextColor(Color.GREEN);
            holder.btnTraSach.setVisibility(View.GONE); // Đã trả rồi thì ẩn nút đi
        }

        holder.btnTraSach.setOnClickListener(v -> {
            if (ticketDAO.returnBook(ticket.getMaPhieu(), ticket.getMaSach())) {
                Toast.makeText(context, "Đã trả sách thành công!", Toast.LENGTH_SHORT).show();
                // Cập nhật lại UI
                ticket.setTrangThaiPhieu(1); // Cập nhật object hiện tại thành Đã trả (1)
                notifyItemChanged(holder.getAdapterPosition()); // Báo cho Adapter reload lại hàng này
            }
        });
    }

    @Override
    public int getItemCount() { return list.size(); }

    public static class TicketViewHolder extends RecyclerView.ViewHolder {
        TextView tvMaPhieu, tvNguoiMuon, tvMaSach, tvThoiGian, tvTrangThai;
        Button btnTraSach;

        public TicketViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMaPhieu = itemView.findViewById(R.id.tvMaPhieu);
            tvNguoiMuon = itemView.findViewById(R.id.tvNguoiMuon);
            tvMaSach = itemView.findViewById(R.id.tvMaSach);
            tvThoiGian = itemView.findViewById(R.id.tvThoiGian);
            tvTrangThai = itemView.findViewById(R.id.tvTrangThai);
            btnTraSach = itemView.findViewById(R.id.btnTraSach);
        }
    }
}