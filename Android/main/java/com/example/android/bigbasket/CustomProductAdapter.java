package com.example.android.bigbasket;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by HP on 13-09-2017.
 */

public class CustomProductAdapter extends ArrayAdapter<Product> {

    public CustomProductAdapter(Activity context, ArrayList<Product> product_items){
        super(context,0,product_items);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listViewItem = convertView;

        if(listViewItem == null){
            listViewItem = LayoutInflater.from(getContext()).inflate(
                    R.layout.cart_item, parent, false);
        }

        TextView mfirst = (TextView)listViewItem.findViewById(R.id.first);
        TextView mproduct = (TextView)listViewItem.findViewById(R.id.product);
        final TextView mquant = (TextView)listViewItem.findViewById(R.id.quantity);
        final TextView mamount = (TextView)listViewItem.findViewById(R.id.amount);
        Button mdec_quant = (Button)listViewItem.findViewById(R.id.dec_button);
        Button minc_quant = (Button)listViewItem.findViewById(R.id.inc_button);

        final Product currentProduct = getItem(position);

        mfirst.setText(currentProduct.getFirstChar());
        mproduct.setText(currentProduct.getProdName());
        mquant.setText(currentProduct.getQuant());
        mamount.setText(currentProduct.getAmount());

        mdec_quant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = Integer.parseInt(mquant.getText().toString());
                quantity--;
                currentProduct.setQuant(quantity);
                currentProduct.setAmount(quantity);
                mquant.setText(currentProduct.getQuant());
                mamount.setText(currentProduct.getAmount());
                Cart.generateBill();
            }
        });

        minc_quant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = Integer.parseInt(mquant.getText().toString());
                quantity++;
                currentProduct.setQuant(quantity);
                currentProduct.setAmount(quantity);
                mquant.setText(currentProduct.getQuant());
                mamount.setText(currentProduct.getAmount());
                Cart.generateBill();
            }
        });
        return listViewItem;
    }
}
