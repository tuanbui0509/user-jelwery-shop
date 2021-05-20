package com.example.de_tai_di_dong.main;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.de_tai_di_dong.R;
import com.example.de_tai_di_dong.adapter.ItemClickListener;
import com.example.de_tai_di_dong.adapter.StatisAdapter;
import com.example.de_tai_di_dong.getData.CallAPI;
import com.example.de_tai_di_dong.getData.GetData_Product;
import com.example.de_tai_di_dong.model.SanPham;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CT_HoaDonActivity extends AppCompatActivity implements ItemClickListener {
    int idsp=0;
    int idKH=0;
    int order = 0;
    ArrayList<SanPham> arraySanPham;
    StatisAdapter adapterSP;
    ListView listsanpham;
    TextView tv;
    @Override
    public void ClickItem(int idSP) {
        idsp= idSP;
        Intent intent =new Intent(CT_HoaDonActivity.this, SanPhamActivity.class);
        intent.putExtra("idSP",idsp);
        intent.putExtra("idKH",idKH);
        startActivity(intent);
    }
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_static);
        idKH=getIntent().getIntExtra("idKH",0);
        order=getIntent().getIntExtra("idOrder",0);
        setControl();
        setEvent();
        requestSanPham();
    }

    private void setEvent() {

    }

    private void setControl() {
        arraySanPham= new ArrayList<>();
        listsanpham = findViewById(R.id.listSanPham);
        tv = findViewById(R.id.total);
    }

    private void showSanPham(List<SanPham> listSanPham){
        Double total = 0.0;
        for(int i = 0;i<listSanPham.size();i++){
            total+=listSanPham.get(i).getPrice();
        }
        tv.setText(tv.getText().toString()+total);
        Log.d("listSanPham", listSanPham.toString());
        adapterSP = new StatisAdapter(this,(ArrayList<SanPham>) listSanPham,this);
        listsanpham.setAdapter(adapterSP);
    }
    private void requestSanPham() {
        GetData_Product service =  CallAPI.getRetrofitInstance().create(GetData_Product.class);
        Call<ArrayList<SanPham>> call = service.getDetailOrder(order);
        call.enqueue(new Callback<ArrayList<SanPham>>() {
            @Override
            public void onResponse(Call<ArrayList<SanPham>> call, Response<ArrayList<SanPham>> response) {
                Log.d("arrmovie", response.toString());
                //show san pham
                showSanPham(response.body());
            }
            @Override
            public void onFailure(Call<ArrayList<SanPham>> call, Throwable t) {
                Log.d("arrmovie", t.toString());
            }
        });
    }
}
