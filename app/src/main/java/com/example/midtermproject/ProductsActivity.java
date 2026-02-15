package com.example.midtermproject;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class ProductsActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ImageView searchIcon, cartIcon, filterIcon, userProfileIcon;
    private TextView cartBadge;
    private RecyclerView productsRecyclerView;
    private FloatingActionButton fabQuickAction;
    private androidx.appcompat.widget.SearchView searchView;
    private boolean isAscending = true;
    private ArrayList<Product> productList = new ArrayList<>();
    private ProductRecyclerAdapter productAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        toolbar = findViewById(R.id.toolbar);
        searchIcon = findViewById(R.id.searchIcon);
        cartIcon = findViewById(R.id.cartIcon);
        cartBadge = findViewById(R.id.cartBadge);
        filterIcon = findViewById(R.id.filterIcon);
        productsRecyclerView = findViewById(R.id.productsRecyclerView);
        fabQuickAction = findViewById(R.id.fabQuickAction);
        userProfileIcon = findViewById(R.id.userProfileIcon);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) getSupportActionBar().setTitle("");
        searchView = findViewById(R.id.searchView);
        searchIcon.setOnClickListener(v -> {
            if (searchView.getVisibility() == View.GONE) {
                searchView.setVisibility(View.VISIBLE);
                searchView.setIconified(false);
            } else {
                searchView.setVisibility(View.GONE);
            }
        });
        searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override public boolean onQueryTextSubmit(String query) { productAdapter.filter(query); return true; }
            @Override public boolean onQueryTextChange(String newText) { productAdapter.filter(newText); return true; }
        });
        cartIcon.setOnClickListener(v -> startActivity(new Intent(this, ShoppingCartActivity.class)));
        filterIcon.setOnClickListener(v -> {
            if (isAscending) {
                productList.sort((p1, p2) -> Double.compare(p1.getPrice(), p2.getPrice()));
            } else {
                productList.sort((p1, p2) -> Double.compare(p2.getPrice(), p1.getPrice()));
            }
            isAscending = !isAscending;
            productAdapter.updateList(productList);
        });
        userProfileIcon.setOnClickListener(v -> startActivity(new Intent(this, UserProfileActivity.class)));
        productsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        productAdapter = new ProductRecyclerAdapter(this, productList);
        productsRecyclerView.setAdapter(productAdapter);
        productAdapter.setOnProductClickListener(product -> {
            Intent intent = new Intent(this, ProductDetailsActivity.class);
            intent.putExtra("product", product);
            startActivity(intent);
        });
        fetchProducts();
    }
    private void fetchProducts() {
        ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);
        apiService.getProducts().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    productList.clear();
                    productList.addAll(response.body());
                    productAdapter.updateList(productList);
                }
            }
            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                loadLocalData();
            }
        });
    }
    private void loadLocalData() {
        productList.clear();
        productList.add(new Product("p1", "هاتف ذكي", "مواصفات عالية وكاميرا احترافية", 2500.00, 4.5, R.drawable.smartphone_image, 1));
        productList.add(new Product("p2", "سماعات", "جودة صوت عالية وعزل ضوضاء", 350.00, 4.2, R.drawable.headphones_image, 1));
        productList.add(new Product("p3", "ساعة ذكية", "تتبع اللياقة البدنية ومقاومة للماء", 700.00, 4.7, R.drawable.smartwatch_image, 1));
        productList.add(new Product("p4", "لابتوب", "معالج i9 وذاكرة 32GB", 4800.00, 4.8, R.drawable.laptop_image, 1));
        productList.add(new Product("p5", "كاميرا", "دقة 24 ميجابكسل وتسجيل 4K", 3200.00, 4.6, R.drawable.camera_image, 1));
        productAdapter.updateList(productList);
    }
    @Override
    protected void onResume() {
        super.onResume();
        updateCartBadge();
    }
    private void updateCartBadge() {
        int count = CartManager.getInstance().getCartItemCount();
        cartBadge.setText(String.valueOf(count));
        cartBadge.setVisibility(count > 0 ? View.VISIBLE : View.GONE);
    }
}
