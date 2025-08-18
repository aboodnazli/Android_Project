package com.example.midtermproject;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.List;

public class WishlistActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ListView wishlistListView;
    private TextView emptyWishlistText;
    private WishlistAdapter wishlistAdapter;
    private List<Product> wishlistItems;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist);


        toolbar = findViewById(R.id.toolbar);
        wishlistListView = findViewById(R.id.wishlistListView);
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
        wishlistAdapter = new WishlistAdapter(this, wishlistItems);
        wishlistListView.setAdapter(wishlistAdapter);


        checkEmptyWishlist();
    }

    @Override
    protected void onResume() {
        super.onResume();

        wishlistItems.clear();
        wishlistItems.addAll(WishlistManager.getInstance().getWishlistItems());
        wishlistAdapter.notifyDataSetChanged();
        checkEmptyWishlist();
    }

    private void checkEmptyWishlist() {
        if (wishlistItems.isEmpty()) {
            emptyWishlistText.setVisibility(View.VISIBLE);
            wishlistListView.setVisibility(View.GONE);
        } else {
            emptyWishlistText.setVisibility(View.GONE);
            wishlistListView.setVisibility(View.VISIBLE);
        }
    }
}
