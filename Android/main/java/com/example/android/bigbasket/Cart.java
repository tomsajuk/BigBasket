package com.example.android.bigbasket;

import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Formatter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Cart extends AppCompatActivity {

    String TAG = "In CartActivity";
    static String code;
    static int t = 0;
    static Button mPay;
    static TextView mAmount;
    static float payAmount = 0;
    String suggest;
   // public String prod_name;
    final static ArrayList<Product> items = new ArrayList<Product>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        mPay = (Button)findViewById(R.id.checkOut);
        mAmount = (TextView)findViewById(R.id.payamount);
        Toast.makeText(this,"To add product to cart, Click on the Camera",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateUI();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //updateUI();
        if(t == 1){
            t = 0;
            searchProduct(code);
        }
        if(t == 2){
            t = 0;
        }
    }

    public void payBill(View v){
        Toast.makeText(this,"Thank You for Shopping with us",Toast.LENGTH_SHORT).show();
        payAmount = 0;
        int j = items.size();
        for(int i=0; i<j; i++)
            items.remove(0);
        finish();
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cart_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_camera:
               //Log.d(TAG, "OpenCamera");
                Intent intent = new Intent(this, Scanner.class);
                startActivity(intent);

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void set_code(String s){
        code = s;
    }
    public void set_t(int x){
        t = x;
    }

    public void updateUI(){
        //items.add(new Product('A',"Shailendra PAtel Anuj Singh","100","123","xyz"));
        ListView ls = (ListView)findViewById(R.id.listView) ;
        CustomProductAdapter adapter = new CustomProductAdapter(this,items);
        ls.setAdapter(adapter);
        generateBill();
    }

    public static void generateBill(){
        Product p;
        payAmount = 0;
        for(int i=0; i<items.size(); i++)
        {
            p = items.get(i);
            payAmount += Float.valueOf(p.getAmount());
        }
        if(payAmount > 0){
            mAmount.setVisibility(View.VISIBLE);
            mPay.setVisibility(View.VISIBLE);
            mAmount.setText(String.valueOf("Pay : ₹"+payAmount));
        }
        else
        {
            mAmount.setVisibility(View.INVISIBLE);
            mPay.setVisibility(View.INVISIBLE);
        }
    }

    public void searchProduct(String code){

        Log.e("Cart","in Search");
        WifiManager wm = (WifiManager) getSystemService(WIFI_SERVICE);
        String ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());

        int leng = ip.length();
        int i = leng - 1;
        while(i>=0 && ip.charAt(i)!='.')i--;
        String ipaddress = ip.substring(0,i+1);
        ipaddress = ipaddress + "1";

        String url = "http://"+ipaddress+":8080/search/code/" + code;
        JsonArrayRequest jsonRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // the response is already constructed as a JSONObject!
                        try {
                            JSONObject res = response.getJSONObject(0);

                            String unitPrice = res.getString("Unit Price");
                            String prod_name = res.getString("Product Name");
                            String barcode = res.getString("Barcode");
                            String category = res.getString("Category");
                            suggest = res.getString("Suggest");

                            items.add(new Product(prod_name.charAt(0),prod_name,unitPrice,barcode,category));
                            Toast.makeText(getApplicationContext(),"People also buy "+suggest,Toast.LENGTH_SHORT).show();

                            Log.e("Cart","in Serch product  "+items.get(0).getProdName());
                            updateUI();
                        } catch (JSONException e) {
                            e.printStackTrace();
                           // cTextView.setText(e.getMessage());
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        //cTextView.setText(error.getMessage());
                    }
                });

        Volley.newRequestQueue(getApplicationContext()).add(jsonRequest);

    }
}
