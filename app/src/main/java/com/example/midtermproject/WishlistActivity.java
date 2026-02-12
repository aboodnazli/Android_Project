package com.example.midtermproject;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
public class WishlistActivity extends AppCompatActivity implements WishlistRecyclerAdapter.OnWishlistChangeListener {
    private Toolbar toolbar;
    private RecyclerView wishlistRecyclerView;
    private TextView emptyWishlistText;
    private WishlistRecyclerAdapter wishlistAdapter;
    private List<Product> wishlistItems;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist);
        toolbar = findViewById(R.id.toolbar);
        wishlistRecyclerView = findViewById(R.id.wishlistRecyclerView);
        emptyWishlistText = findViewById(R.id.emptyWishlistText);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        wishlistItems = WishlistManager.getInstance().getWishlistItems();
        wishlistRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        wishlistAdapter = new WishlistRecyclerAdapter(this, wishlistItems);
        wishlistAdapter.setOnWishlistChangeListener(this);
        wishlistRecyclerView.setAdapter(wishlistAdapter);
        checkEmptyWishlist();
    }
    @Override
    protected void onResume() {
        super.onResume();
        wishlistItems = WishlistManager.getInstance().getWishlistItems();
        wishlistAdapter.updateList(wishlistItems);
        checkEmptyWishlist();
    }
    @Override
    public void onWishlistChanged() {
        wishlistItems = WishlistManager.getInstance().getWishlistItems();
        wishlistAdapter.updateList(wishlistItems);
        checkEmptyWishlist();
    }
    private void checkEmptyWishlist() {
        if (wishlistItems.isEmpty()) {
            emptyWishlistText.setVisibility(View.VISIBLE);
            wishlistRecyclerView.setVisibility(View.GONE);
        } else {
            emptyWishlistText.setVisibility(View.GONE);
            wishlistRecyclerView.setVisibility(View.VISIBLE);
        }
    }
}
