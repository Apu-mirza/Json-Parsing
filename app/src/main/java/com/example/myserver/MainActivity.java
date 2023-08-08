package com.example.myserver;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    String title, price, thumbnail, discount, brand, description, rating, stock;
    String titles, thumbnails, prices, discounts, descriptions, stocks, ratings, brands, image;
    String localImageString;
    //    HashMap<String,String> imagesList;
    ArrayList<ArrayList<String>> imagesList = new ArrayList<>();
    JSONObject jsonObject;
    GridView gridView;
    ProgressBar progressBar;
    HashMap<String,String> hashMap;
    ArrayList<HashMap<String,String>> arrayList = new ArrayList<>();
    ArrayList<HashMap<String,String>> arrayList2 = new ArrayList<>();
    String imageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridView = findViewById(R.id.mainGrid);
        progressBar = findViewById(R.id.progressBar);

        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        String url = "https://mejajta.000webhostapp.com/apps1/grocer.json";
//        String url = "https://dummyjson.com/products"; it does not work

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                progressBar.setVisibility(View.GONE);
                try {
                    for (int i = 0; i < response.length(); i++) {
                        jsonObject = response.getJSONObject(i);
                        title = jsonObject.getString("title");
                        price = jsonObject.getString("price");
                        thumbnail = jsonObject.getString("thumbnail");
                        discount = jsonObject.getString("discountPercentage");
                        String brand = jsonObject.getString("brand");
                        description = jsonObject.getString("description");
                        rating = jsonObject.getString("rating");
                        stock = jsonObject.getString("stock");

                        JSONArray imagesArray = jsonObject.getJSONArray("images");
                        ArrayList<String> imagesList1 = new ArrayList<>();
                        for (int x = 0; x < imagesArray.length(); x++) {
                            imageUrl = imagesArray.getString(x);
                            imagesList1.add(imageUrl);
                        }
                        imagesList.add(imagesList1);


                        hashMap = new HashMap<>();
                        hashMap.put("title",title);
                        hashMap.put("price",price);
                        hashMap.put("discount",discount);
                        hashMap.put("brand",brand);
                        hashMap.put("description",description);
                        hashMap.put("rating",rating);
                        hashMap.put("stock",stock);
                        hashMap.put("thumbnail",thumbnail);
                        arrayList.add(hashMap);
                    }
                    MyAdapter myAdapter = new MyAdapter(this, title, thumbnail, price, discount, brand, description,rating,stock);
                    gridView.setAdapter(myAdapter);


                    gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            HashMap<String, String> hashMap = arrayList.get(position);
                            String title1 = hashMap.get("title");
                            String price1 = hashMap.get("price");
                            String image1 = hashMap.get("thumbnail");
                            String discount1 = hashMap.get("discount");
                            String brand1 = hashMap.get("brand");
                            String description1 = hashMap.get("description");
                            String stock1 = hashMap.get("stock");
                            String rating1 = hashMap.get("rating");

                            ArrayList<String> imageslist = imagesList.get(position);

                            Intent intent = new Intent(getApplicationContext(),MainActivity2.class);
                            intent.putExtra("item_title", title1);
                            intent.putExtra("item_image_url", image1);
                            intent.putExtra("item_price", price1);
                            intent.putExtra("item_description", description1);
                            intent.putExtra("item_stock", stock1);
                            intent.putExtra("item_rating", rating1);
                            intent.putExtra("item_brand", brand1);
                            intent.putExtra("item_discount", discount1);
                            intent.putExtra("images",imageslist);
                            startActivity(intent);
                        }
                    });


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(jsonArrayRequest);

    }

    public class MyAdapter extends BaseAdapter {

        String title, thumbnail, price, discount, description, stock, rating, brand;
        ArrayList<String> imagesList;
        Context context;
        LayoutInflater inflater;


        public MyAdapter(Response.Listener<JSONArray> jsonArrayListener, String title, String thumbnail, String price, String discount, String brand, String description, String rating, String stock) {
            this.title = title;
            this.price = price;
            this.thumbnail = thumbnail;
            this.discount = discount;
            this.description = description;
            this.stock = stock;
            this.brand = brand;
            this.rating = rating;
            this.thumbnail = thumbnail;
            this.imagesList = imagesList;
        }

        @Override
        public int getCount() {
            return arrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater layoutInflater = getLayoutInflater();
            View myView = layoutInflater.inflate(R.layout.item_layout,null);
            TextView productName = myView.findViewById(R.id.nameId);
            ImageView productImage = myView.findViewById(R.id.imageId);
            TextView productPrice = myView.findViewById(R.id.priceId);
//            Button cartButton = myView.findViewById(R.id.cartButton);
            TextView discountView = myView.findViewById(R.id.discountId);
            TextView brandView = myView.findViewById(R.id.brandId);
            TextView descriptionView = myView.findViewById(R.id.descriptionId);
            TextView ratingView = myView.findViewById(R.id.ratingTextId);
            TextView stockView = myView.findViewById(R.id.stockId);

            HashMap<String, String> hashMap = arrayList.get(position);
            String title = hashMap.get("title");
            String price = hashMap.get("price");
            String image = hashMap.get("thumbnail");
            String discount = hashMap.get("discount");
            String brand = hashMap.get("brand");
            String description = hashMap.get("description");
            String stock = hashMap.get("stock");
            String rating = hashMap.get("rating");

            int desSize = 10;

            productName.setText(title);
            productPrice.append(price+" ");
            brandView.append(brand);
            discountView.setText(discount);
            ratingView.setText(rating);
            stockView.append(stock);

            if (description.length() > desSize){
                String truncatedDes = description.substring(0,desSize-2)+"...";
                descriptionView.append(truncatedDes);
            }
            else{
                descriptionView.append(description);
            }
            Picasso.get().load(image).placeholder(R.drawable.malaysia).into(productImage);
            return myView;
        }
    }

}