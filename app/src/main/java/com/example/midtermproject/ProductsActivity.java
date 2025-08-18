package com.example.midtermproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;


public class ProductsActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ImageView searchIcon;
    private ImageView cartIcon;
    private TextView cartBadge;
    private RecyclerView categoriesRecyclerView;
    private ImageView filterIcon;
    private ListView productsListView;
    private FloatingActionButton fabQuickAction;

    private ArrayList<Product> productList;
    private ProductAdapter productAdapter;
    private ImageView userProfileIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        toolbar = findViewById(R.id.toolbar);
        searchIcon = findViewById(R.id.searchIcon);
        cartIcon = findViewById(R.id.cartIcon);
        cartBadge = findViewById(R.id.cartBadge);
        categoriesRecyclerView = findViewById(R.id.categoriesRecyclerView);
        filterIcon = findViewById(R.id.filterIcon);
        productsListView = findViewById(R.id.productsListView);
        fabQuickAction = findViewById(R.id.fabQuickAction);
        cartIcon = findViewById(R.id.cartIcon);
        cartBadge = findViewById(R.id.cartBadge);
        userProfileIcon = findViewById(R.id.userProfileIcon);



        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("");
        }

        searchIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ProductsActivity.this, "البحث قيد التطوير", Toast.LENGTH_SHORT).show();
            }
        });

        cartIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductsActivity.this, ShoppingCartActivity.class);
                startActivity(intent);
            }
        });

        updateCartBadge();


        filterIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ProductsActivity.this, "التصفية قيد التطوير", Toast.LENGTH_SHORT).show();
            }
        });
        userProfileIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductsActivity.this, UserProfileActivity.class);
                startActivity(intent);
            }
        });


        categoriesRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        productList = new ArrayList<>();

        productList.add(new Product("p1", "هاتف ذكي حديث", "أحدث هاتف ذكي بمواصفات عالية وكاميرا احترافية وشاشة OLED مذهلة.", 2500.00, 4.5, R.drawable.smartphone_image, 1));
        productList.add(new Product("p2", "سماعات بلوتوث لاسلكية", "سماعات لاسلكية بجودة صوت عالية، عزل ضوضاء ممتاز، وعمر بطارية طويل يصل إلى 30 ساعة.", 350.00, 4.2, R.drawable.headphones_image, 1));
        productList.add(new Product("p3", "ساعة ذكية رياضية", "ساعة ذكية لتتبع اللياقة البدنية، مراقبة معدل ضربات القلب، وتتبع النوم، مقاومة للماء.", 700.00, 4.7, R.drawable.smartwatch_image, 1));
        productList.add(new Product("p4", "لابتوب فائق الأداء", "لابتوب قوي للألعاب والعمل الاحترافي، بمعالج i9 وذاكرة 32GB وبطاقة رسوميات RTX 4080.", 4800.00, 4.8, R.drawable.laptop_image, 1));
        productList.add(new Product("p5", "كاميرا رقمية احترافية", "كاميرا DSLR للمصورين المحترفين والهواة، بدقة 24 ميجابكسل وتسجيل فيديو 4K.", 3200.00, 4.6, R.drawable.camera_image, 1));
        productList.add(new Product("p6", "شاشة كمبيوتر 4K", "شاشة عرض عالية الدقة لتجربة بصرية مذهلة، بحجم 27 بوصة وتقنية HDR.", 1500.00, 4.3, R.drawable.display_image, 1));
        productList.add(new Product("p7", "طابعة متعددة الوظائف", "طابعة ليزرية سريعة وعالية الجودة للمنزل والمكتب، تدعم الطباعة والمسح الضوئي والنسخ.", 900.00, 4.0, R.drawable.printer_image, 1));
        productList.add(new Product("p8", "قرص صلب خارجي 2TB", "قرص صلب محمول بسعة 2 تيرابايت، سرعة نقل بيانات عالية، مثالي للنسخ الاحتياطي.", 280.00, 4.4, R.drawable.disk_image, 1));
        productList.add(new Product("p9", "جهاز عرض (بروجيكتور)", "جهاز عرض محمول بدقة Full HD، مثالي للمنزل والمكتب والعروض التقديمية.", 1100.00, 4.1, R.drawable.projector_image, 1));
        productList.add(new Product("p10", "كرسي ألعاب مريح", "كرسي ألعاب بتصميم مريح، دعم للظهر والرقبة، قابل للتعديل بالكامل.", 650.00, 4.9, R.drawable.chair_image, 1));

        productAdapter = new ProductAdapter(this, productList);
        productsListView.setAdapter(productAdapter);

        productsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Product selectedProduct = productList.get(position);
                Intent intent = new Intent(ProductsActivity.this, ProductDetailsActivity.class);
                intent.putExtra("product", selectedProduct);
                startActivity(intent);
            }
        });

        fabQuickAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ProductsActivity.this, "إجراء سريع قيد التطوير", Toast.LENGTH_SHORT).show();
            }
        });

    }
    @Override
    protected void onResume() {
        super.onResume();
        updateCartBadge();
    }

    private void updateCartBadge() {
        int itemCount = CartManager.getInstance().getCartItemCount();
        if (itemCount > 0) {
            cartBadge.setText(String.valueOf(itemCount));
            cartBadge.setVisibility(View.VISIBLE);
        } else {
            cartBadge.setVisibility(View.GONE);
        }
    }
}
