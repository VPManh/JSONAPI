package com.example.quick_test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Product> productList;
    private ListView lvProducts;
    private ProductAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvProducts = findViewById(R.id.listView_product);
        productList = new ArrayList<>();
        adapter = new ProductAdapter(productList);
        lvProducts.setAdapter(adapter);

        new ReadApi().execute("https://fakestoreapi.com/products");
    }

    class ReadApi extends AsyncTask<String, Void, String> {
        StringBuilder content = new StringBuilder();

        @Override
        protected String doInBackground(String... strings) {

            try {
                URL url = new URL(strings[0]);
                InputStreamReader inputStreamReader = new InputStreamReader(url.openConnection().getInputStream());
                BufferedReader buffer = new BufferedReader(inputStreamReader);

                String line = "";

                while ((line = buffer.readLine()) != null) {
                    content.append(line);
                }

                buffer.close();
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return content.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                JSONArray array = new JSONArray(s);
                for (int i = 0; i < array.length(); i++) {
                    JSONObject obj = array.getJSONObject(i);
                    String title = obj.getString("title");
                    double price = obj.getDouble("price");
                    String desc = obj.getString("description");
                    String imgUrl = obj.getString("image");

                    JSONObject rating = obj.getJSONObject("rating");
                    int sold = rating.getInt("count");

                    Product item = new Product(title, price, desc, imgUrl, sold);
                    productList.add(item);
                }

                adapter.notifyDataSetChanged();

            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
