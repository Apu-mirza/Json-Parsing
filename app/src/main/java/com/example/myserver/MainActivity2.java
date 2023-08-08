package com.example.myserver;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {

    TextView title, price, discount, brand, description, rating, stock;
    ImageView  thumbnailView, localImageView;
    private ViewPager viewPager;
    private ImageAdapter adapter;
    private TextView textViewImageCount;
    private FloatingActionButton fabArrow, fabArrow2;
    Integer position;
    RecyclerView recyclerView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        title = findViewById(R.id.nameId);
        price = findViewById(R.id.priceId);
        discount = findViewById(R.id.discountId);
        brand = findViewById(R.id.brand);
        description = findViewById(R.id.descriptionId);
        rating = findViewById(R.id.ratingTextId);
        stock = findViewById(R.id.stockId);
        viewPager = findViewById(R.id.viewPager);
        localImageView = findViewById(R.id.imageRecycleId);

        Intent intent = getIntent();
        String itemTitle = intent.getStringExtra("item_title");
        String thumbnail = intent.getStringExtra("item_image_url");
        String itemPrice = intent.getStringExtra("item_price");
        String itemDescription = intent.getStringExtra("item_description");
        String itemStock = intent.getStringExtra("item_stock");
        String itemRating = intent.getStringExtra("item_rating");
        String itemBrand = intent.getStringExtra("item_brand");
        String itemDiscount = intent.getStringExtra("item_discount");
        ArrayList<String> images = intent.getStringArrayListExtra("images");

        title.setText(itemTitle);
        price.setText(itemPrice);
        discount.setText(itemDiscount);
//        brand.append(itemBrand);
        description.append(itemDescription);
        rating.setText(itemRating);
        stock.append(itemStock);
//        Picasso.get().load(thumbnail).placeholder(R.drawable.malaysia).into(viewPager);

        viewPager = findViewById(R.id.viewPager);
        textViewImageCount = findViewById(R.id.textViewImageCount);
        fabArrow = findViewById(R.id.fabArrow);
        fabArrow2 = findViewById(R.id.fabArrow2);

        adapter = new ImageAdapter(this, images);
        viewPager.setAdapter(adapter);

        updateImageCount(1); // Display initial count

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                updateImageCount(position + 1); // Update count when page changes
                updateFabVisibility(position);
                updateFab2Visibility(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        fabArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, true);
            }
        });
        fabArrow2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(viewPager.getCurrentItem() - 1, true);
            }
        });

    }

    private void updateImageCount(int currentPosition) {
        int totalImages = adapter.getCount();
        textViewImageCount.setText(currentPosition + "/" + totalImages);
    }

    private void updateFabVisibility(int currentPosition) {
        int totalImages = adapter.getCount();
        if (currentPosition < totalImages - 1) {
            fabArrow.setVisibility(View.VISIBLE);
        } else {
            fabArrow.setVisibility(View.GONE);
        }
    }
    private void updateFab2Visibility(int currentPosition) {
        int totalImages = adapter.getCount();
        if (currentPosition >= 1) {
            fabArrow2.setVisibility(View.VISIBLE);
        } else {
            fabArrow2.setVisibility(View.GONE);
        }
    }

}