package com.example.de_tai_di_dong.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.de_tai_di_dong.R;
import com.example.de_tai_di_dong.model.Orders;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class HoaDonAdapter extends BaseAdapter {
    private ArrayList<Orders> listOrders;
    private Context context;
    private ItemClickListener clickItem;
    private int idKH;

    public HoaDonAdapter(ArrayList<Orders> listOrders, Context context, int idKH, ItemClickListener clickItem) {
        this.context = context;
        this.listOrders = listOrders;
        this.idKH = idKH;
        this.clickItem = clickItem;
    }

    @Override
    public int getCount() {
        return listOrders.size();
    }

    @Override
    public Object getItem(int position) {
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder {
        TextView name;
        TextView date;
        //        EditText phone;
//        EditText email;
//        EditText note;
        TextView totalPrice;
        TextView address;
        TextView status;
        LinearLayout listHD;

    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hoa_don, parent, false);
            holder = new ViewHolder();
            holder.name = view.findViewById(R.id.nameHD);
            holder.date = view.findViewById(R.id.ngayHD);
            holder.address = view.findViewById(R.id.addressHD);
            holder.listHD = view.findViewById(R.id.listHD);
            holder.totalPrice = view.findViewById(R.id.totalPrice);
            holder.status = view.findViewById(R.id.status);
            view.setTag(holder);
        } else holder = (ViewHolder) view.getTag();
        final Orders orders = listOrders.get(position);
        holder.name.setText(orders.getName());
        holder.address.setText(orders.getAddress());
        double total=0;
        for (int i=0;i<listOrders.size();i++){

        }
        if(orders.getStatusId()==1){
            holder.status.setText("Chưa Xác Nhận");
        }else if (orders.getStatusId()==2){
            holder.status.setText("Đã Xác Nhận");
        }
//        holder.totalPrice.setText();
        holder.date.setText(orders.getBuyingDay().substring(0,10));
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//        String formatDateTime = localDateTime.format(formatter);
        holder.listHD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickItem.ClickItem(orders.getId());
            }
        });
        return view;
    }
}
