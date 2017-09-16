package com.example.android.bigbasket;

import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Formatter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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
   // public String prod_name;
    final ArrayList<String> items = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

    }

    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(this,"To add product to cart, Click on the Camera",Toast.LENGTH_SHORT).show();
        //updateUI();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(t == 1){
            //Toast.makeText(this, code + " selected", Toast.LENGTH_SHORT).show();
            t = 0;
            searchProduct(code);
            //updateUI();
        }
        if(t == 2){
            //Toast.makeText(this, "Nothing selected!!!", Toast.LENGTH_SHORT).show();
            t = 0;
        }
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
        Log.e("Cart","in update cart.java "+items);
        ListView ls = (ListView)findViewById(R.id.listcart) ;
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,items);
        ls.setAdapter(adapter);
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
                            //cTextView.setText("Response = " + response);
                            //"Unit Price" t get unit price
                            //"Product Name" to get product name
                            //"Barcode" for barcode no
                            //"Category" to know the category
                            JSONObject res = response.getJSONObject(0);

                            String unitPrice = res.getString("Unit Price");
                            String prod_name = res.getString("Product Name");
                            int barcode = res.getInt("Barcode");
                            String category = res.getString("Category");

                            items.add(prod_name);
                            Log.e("Cart","in Serch product  "+items);
                            updateUI();
                            /*cTextView.setText("Product Name : " + product_name + "\nUnit Price : " + unitPrice+
                                    "\nBarcode : " + barcode + "\nCategory : " + category);*/
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
