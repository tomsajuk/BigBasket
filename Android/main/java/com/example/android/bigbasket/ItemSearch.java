package com.example.android.bigbasket;

import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

//import static com.example.android.bigbasket.R.id.barcode;


public class ItemSearch extends AppCompatActivity implements Response.Listener,
        Response.ErrorListener {
    public static final String REQUEST_TAG = "MainVolleyActivity";
    private TextView cTextView;
    private TextView nTextView;
    //private Button cButton;
    private Button nButton;
   // private EditText cEditText;
    private EditText nEditText;
    private RequestQueue mQueue;
    //Context context = getApplicationContext();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_search);

       // cTextView = (TextView) findViewById(R.id.cTextView);
        //cButton = (Button) findViewById(R.id.cButton);
        nTextView = (TextView) findViewById(R.id.nTextView);
        nButton = (Button) findViewById(R.id.nButton);
        //cEditText = (EditText) findViewById(barcode);
        nEditText = (EditText) findViewById(R.id.name);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Instantiate the RequestQueue.

        nButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = nEditText.getText().toString();
                WifiManager wm = (WifiManager) getSystemService(WIFI_SERVICE);
                String ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());
                int leng = ip.length();
                int i = leng - 1;
                while(i>=0 && ip.charAt(i)!='.')i--;
                String ipaddress = ip.substring(0,i+1);
                ipaddress = ipaddress + "1";
                Log.e(REQUEST_TAG,ipaddress);


                String url = "http://"+ipaddress+":8080/search/name/" + name;
                Log.e(REQUEST_TAG,url);

                JsonArrayRequest jsonRequest = new JsonArrayRequest
                        (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                // the response is already constructed as a JSONObject!
                                try {
                                    nTextView.setText("Response = " + response);
                                    JSONObject res = response.getJSONObject(0);
                                    //"Unit Price" for unitPrice
                                    //"Rack No" to get the rack no
                                    //"Shelf No" to get shelf no
                                    //"Product Name" to get the name of product
                                    if(res.length()==0){
                                        nTextView.setText("Sorry, this product is not available.");
                                    }
                                    else{
                                        String unitPrice = res.getString("Unit Price");
                                        String rack_no = res.getString("Rack No");
                                        String shelf_no = res.getString("Shelf No");
                                        String product_name = res.getString("Product Name");

                                        //Log.i("Unit Price : ",unitPrice);
                                        nTextView.setText("Product Name : " + product_name + "\nUnit Price : " + unitPrice +
                                                "\nRack No: " + rack_no + "\nShelf No : " + shelf_no);
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    nTextView.setText(e.getMessage());
                                }
                            }
                        }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                error.printStackTrace();
                                nTextView.setText(error.getMessage());
                            }
                        });

                Volley.newRequestQueue(getApplicationContext()).add(jsonRequest);

            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mQueue != null) {
            mQueue.cancelAll(REQUEST_TAG);
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        cTextView.setText(error.getMessage());
    }

    @Override
    public void onResponse(Object response) {
        cTextView.setText("Response is: " + response);
        try {
            cTextView.setText(cTextView.getText() + "\n\n" + ((JSONObject) response).getString
                    ("name"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
