package com.example.quick_test;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductAdapter extends BaseAdapter {
    private List<Product> productList;

    public ProductAdapter(List<Product> productList) {
        this.productList = productList;
    }

    @Override
    public int getCount() {
        return productList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_product, viewGroup, false);
        }

        RoundedImageView imageProduct = view.findViewById(R.id.image_product);
        TextView titleProduct = view.findViewById(R.id.title_product);
        TextView priceProduct = view.findViewById(R.id.price_product);
        TextView soldProduct = view.findViewById(R.id.sold_product);
        TextView descProduct = view.findViewById(R.id.desc_product);

        final Product product = productList.get(position);
        if (!productList.isEmpty()) {
            titleProduct.setText(product.getTitle());
            priceProduct.setText("$" + product.getPrice());
            soldProduct.setText("Sold:  " + product.getSold());
            descProduct.setText(product.getDescription());
            Picasso.get().load(product.getImageUrl()).into(imageProduct);
        }

        //nếu như sử dụng listview thuần thì bên java main tạo ra array Adapter rồi add (new Obijet vào)

        return view;
    }
}
