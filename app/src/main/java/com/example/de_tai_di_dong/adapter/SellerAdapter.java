package com.example.de_tai_di_dong.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.de_tai_di_dong.R;
import com.example.de_tai_di_dong.getData.CallAPI;
import com.example.de_tai_di_dong.getData.GetDataCart;
import com.example.de_tai_di_dong.main.GioHangActivity;
import com.example.de_tai_di_dong.main.LoginActivity;
import com.example.de_tai_di_dong.main.MainActivity;
import com.example.de_tai_di_dong.main.SanPhamActivity;
import com.example.de_tai_di_dong.model.ResultCartItem;
import com.example.de_tai_di_dong.model.SanPham;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SellerAdapter extends RecyclerView.Adapter<com.example.de_tai_di_dong.adapter.SellerAdapter.SellerViewHolder> implements Filterable {

    Context context;
    List<SanPham> SellerList;
    List<SanPham> SellerListAll;
    int idKH;    Animation scaleUp;
    private SellerAdapter.ValueFilter valueFilter;
    private ArrayList<SanPham> spfilter;

    public SellerAdapter(Context context, List<SanPham> SellerList,int idKH) {
        this.context = context;
        this.SellerList = SellerList;
        this.SellerListAll = new ArrayList<>(this.SellerList);
        this.idKH=idKH;        scaleUp = AnimationUtils.loadAnimation(context, R.anim.scale);

    }

    @NonNull
    @Override
    public SellerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.seller_row_item, parent, false);
        return new SellerViewHolder(view);
    }

    @Override
    public void onBindViewHolder( SellerViewHolder holder, int position) {
        Glide.with(context).load(SellerList.get(position).getImage1()).into(holder.sanpham_image);
        holder.name.setText(SellerList.get(position).getName());
        holder.quality.setText(("SL: " + SellerList.get(position).getStock()));
        long number = Double.valueOf(SellerList.get(position).getPrice()).longValue();
        holder.price.setText("Giá: " + number);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, SanPhamActivity.class);
                i.putExtra("idSP",SellerList.get(position).getId());
                i.putExtra("idKH",idKH);
                context.startActivity(i);
            }
        });
        holder.itemView.startAnimation(scaleUp);
        holder.itemView.findViewById(R.id.btnBuy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (idKH == 0) {
                    Intent intent = new Intent(context, LoginActivity.class);
                    context.startActivity(intent);
                }
                if (idKH != 0) {
                    GetDataCart service = CallAPI.getRetrofitInstance().create(GetDataCart.class);
                    Call<ArrayList<ResultCartItem>> call = service.getCartItem(idKH, SellerList.get(position).getId(), 1);
                    call.enqueue(new Callback<ArrayList<ResultCartItem>>() {
                        @Override
                        public void onResponse(Call<ArrayList<ResultCartItem>> call, Response<ArrayList<ResultCartItem>> response) {
                            assert response.body() != null;
                            if (response.body().get(0).getResult() != 0) {
                                Toast.makeText(context, "Thêm sản phẩm vào giỏ hàng thành công!!", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(context, GioHangActivity.class);
                                intent.putExtra("idKH", idKH);
//                                context.startActivity(intent);
                            } else {
                                Toast.makeText(context, "Không thể thêm sản phẩm vào giỏ hàng!!", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ArrayList<ResultCartItem>> call, Throwable t) {

                        }
                    });


                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return SellerList.size();
    }

    @Override
    public Filter getFilter() {
        if(valueFilter==null) {

            valueFilter=new SellerAdapter.ValueFilter();
        }
        return valueFilter;
    }


    public static final class SellerViewHolder extends RecyclerView.ViewHolder{
        ImageView sanpham_image;
        TextView price, name, quality;

        public SellerViewHolder(@NonNull View itemView) {
            super(itemView);

            sanpham_image = itemView.findViewById(R.id.sanpham_image);
            quality = itemView.findViewById(R.id.quality);
            price = itemView.findViewById(R.id.price);
            name = itemView.findViewById(R.id.sanpham_name);
        }
    }


    private  class ValueFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results=new FilterResults();
            if(constraint!=null && constraint.length()>0){
                ArrayList<SanPham> filterList=new ArrayList<SanPham>();
                for(int i=0;i<SellerListAll.size();i++){
                    if((SellerListAll.get(i).getName().toUpperCase())
                            .contains(constraint.toString().toUpperCase())) {
                        SanPham c = new SanPham();
                        c.setName(SellerListAll.get(i).getName());
                        c.setId(SellerListAll.get(i).getId());
                        c.setImage1(SellerListAll.get(i).getImage1());
                        c.setProGroupId(SellerListAll.get(i).getProGroupId());
                        c.setPrice(SellerListAll.get(i).getPrice());
                        filterList.add(c);
                    }
                }
                results.count=filterList.size();
                results.values=filterList;
            }else{
                results.count=SellerListAll.size();
                results.values=SellerListAll;
            }
            return results;
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            SellerList.clear();
            //SellerList = (ArrayList<SanPham>) results.values;
            SellerList.addAll((Collection<? extends SanPham>) results.values);
            notifyDataSetChanged();
        }
    }


}
