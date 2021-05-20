package com.example.de_tai_di_dong.main;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.de_tai_di_dong.R;
import com.example.de_tai_di_dong.adapter.ItemClickListener;
import com.example.de_tai_di_dong.adapter.PopularAdapter;
import com.example.de_tai_di_dong.adapter.SanPhamAdapter;
import com.example.de_tai_di_dong.adapter.SellerAdapter;
import com.example.de_tai_di_dong.getData.CallAPI;
import com.example.de_tai_di_dong.getData.GetData_Product;
import com.example.de_tai_di_dong.model.SanPham;
import com.google.android.material.navigation.NavigationView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements ItemClickListener {

    SearchView svR;
    String normalText;
    Toolbar toolbar;
    ArrayList<SanPham> arraySanPham;
    SanPhamAdapter adapterSP;
    ListView listsanpham;
    TextView tvv;
    EditText etSearch;
    RecyclerView popularRecycler, sellRecycler;
    PopularAdapter popularAdapter;
    SellerAdapter sellerAdapter;
    ImageView account, btnCart;
    //navigation
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle mToggle;
    int idsp = 0;
    int idKH = 0;
    NavigationView navigationView;
    Toolbar infoAccount;
    private MySwipeRefreshLayout swipeRefreshLayout;
    boolean update = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        idKH = getIntent().getIntExtra("idKH", 0);
        setControl();
        requestSanPham();
        requestAdver();
        setEvent();
        sellRecycler.setOnTouchListener(new TranslateAnimationUtil(this,popularRecycler,tvv));
    }

    private void requestSanPham() {
        GetData_Product service = CallAPI.getRetrofitInstance().create(GetData_Product.class);
        Call<ArrayList<SanPham>> call = service.getAllSanPham();
        call.enqueue(new Callback<ArrayList<SanPham>>() {
            @Override
            public void onResponse(Call<ArrayList<SanPham>> call, Response<ArrayList<SanPham>> response) {
                Log.d("arrmovie", response.toString());
                //show san pham
                update = false;
                sellerProduct(response.body());

            }

            @Override
            public void onFailure(Call<ArrayList<SanPham>> call, Throwable t) {
                Log.d("arrmovie", t.toString());
            }

        });
        call = service.getAllAdver();
        call.enqueue(new Callback<ArrayList<SanPham>>() {
            @Override
            public void onResponse(Call<ArrayList<SanPham>> call, Response<ArrayList<SanPham>> response) {
                Log.d("arrmovie", response.toString());
                //show san pham
                popularProduct(response.body());

            }
            @Override
            public void onFailure(Call<ArrayList<SanPham>> call, Throwable t) {
                Log.d("arrmovie", t.toString());
            }

        });
    }
    private void requestAdver() {
        GetData_Product service = CallAPI.getRetrofitInstance().create(GetData_Product.class);
        Call<ArrayList<SanPham>> call = service.getAllAdver();
        call.enqueue(new Callback<ArrayList<SanPham>>() {
            @Override
            public void onResponse(Call<ArrayList<SanPham>> call, Response<ArrayList<SanPham>> response) {
                Log.d("arrmovie", response.toString());
                //show san pham
                popularProduct(response.body());

            }
            @Override
            public void onFailure(Call<ArrayList<SanPham>> call, Throwable t) {
                Log.d("arrmovie", t.toString());
            }

        });

    }

    private void setEvent() {

        svR.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                sellerAdapter.getFilter().filter(newText);
                return false;
            }
        });

        swipeRefreshLayout = (MySwipeRefreshLayout) findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestSanPham();
                new Handler().postDelayed(new Runnable() {
                    @Override public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 1000);
            }
        });
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);


        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (idKH == 0) {
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(MainActivity.this, UserActivity.class);
                    intent.putExtra("idKH", idKH);
                    startActivity(intent);
                }
            }
        });

        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(idKH==0){
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);

                }
                else{
                    Intent intent = new Intent(getApplicationContext(), GioHangActivity.class);
                    intent.putExtra("idKH", idKH);
                    startActivity(intent);
                }
            }
        });
    }

    private void setControl() {
        arraySanPham = new ArrayList<>();
        listsanpham = findViewById(R.id.listSanPham);
        account = findViewById(R.id.account);
        btnCart = findViewById(R.id.btnCart);
        svR = findViewById(R.id.editText);
        popularRecycler = findViewById(R.id.popular_recycler);
        sellRecycler = findViewById(R.id.seller_recycler);
        tvv = findViewById(R.id.textView2);
        normalText = tvv.getText().toString();
//        infoAccount = findViewById(R.id.infoAccount);
//        setSupportActionBar(infoAccount);
    }

    private void popularProduct(List<SanPham> listSanPham) {

        for (SanPham sp : listSanPham) {
            if(!sp.getDisplay())
                listSanPham.remove(sp);
        }
        if(listSanPham.size()==0){
            //tvv.setVisibility(View.GONE);
            //.setWillNotDraw(true);
            //normalText = tvv.getText().toString();
            tvv.setText("");
        }else{
            tvv.setText(normalText);
        }
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        popularRecycler.setLayoutManager(layoutManager);
        popularAdapter = new PopularAdapter(this, listSanPham, idKH);
        popularRecycler.setAdapter(popularAdapter);
    }

    private void sellerProduct(List<SanPham> listSanPham) {

        for(int i = 0 ;i<listSanPham.size();i++){
            SanPham sp = listSanPham.get(i);
            if(!sp.getDisplay())
                listSanPham.remove(sp);
        }

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        sellRecycler.setLayoutManager(layoutManager);
        sellerAdapter = new SellerAdapter(this, listSanPham, idKH);
        sellRecycler.setAdapter(sellerAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        //getMenuInflater().inflate(R.menu.draw_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //sự kiện click vào item
    @Override
    public void ClickItem(int idSP) {
        idsp = idSP;
        Intent intent = new Intent(MainActivity.this, SanPhamActivity.class);
        intent.putExtra("idSP", idsp);
        intent.putExtra("idKH", idKH);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
}
