package com.example.sachin.qrsworkbench;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

public class AddToCart extends AppCompatActivity {
    EditText pId, pQuan;
    int produtId, quantity;
    Button scan, addTo;
    private IntentIntegrator qrScan;
    String tokenId;
    String p_name,p_price;
    int p_idt = 0, p_quantity = 0,p_quantityyy;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_cart);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build();
        StrictMode.setThreadPolicy(policy);
        pId = findViewById(R.id.product_id);
        pQuan = findViewById(R.id.product_quantity);
        qrScan = new IntentIntegrator(this);
        scan = findViewById(R.id.scanP);
        addTo = findViewById(R.id.addto);
        progressBar=findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        Intent int1 = getIntent();

        tokenId = int1.getStringExtra("token_id");
        //Toast.makeText(AddToCart.this,tokenId,Toast.LENGTH_LONG).show();

        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //initiating the qr code scan
                qrScan.initiateScan();
            }
        });
        addTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                produtId = Integer.parseInt(pId.getText().toString().trim());
                quantity = Integer.parseInt(pQuan.getText().toString().trim());
                progressBar.setVisibility(View.VISIBLE);
                addingToCart(produtId, quantity);

            }
        });
    }

    //Getting the scan results
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            //if qrcode has nothing in it
            if (result.getContents() == null) {
                Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
            } else {
                String res = result.getContents().toString();
              //  Toast.makeText(AddToCart.this, "Product Id:" + res, Toast.LENGTH_LONG).show();
                pId.setText(res);
                //if qr contains data
               /* try {
                    //converting the data to json
                    JSONObject obj = new JSONObject(result.getContents());
                    //setting values to textviews
                    pId.setText(obj.getString("name"));
                } catch (JSONException e) {
                    e.printStackTrace();
                    //if control comes here
                    //that means the encoded format not matches
                    //in this case you can display whatever data is available on the qrcode
                    //to a toast
                    Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                }*/
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


    public void addingToCart(int p_id, int p_quantity) {

        String add_to_cart = "https://qrstest.000webhostapp.com/addToCartFinal.php?p_id=" + p_id + "&p_quantity=" + p_quantity + "";
        RequestQueue requestQueue = Volley.newRequestQueue(AddToCart.this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, add_to_cart, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response.equals("0")) {
                    // Toast.makeText(AddToCart.this,"Andarr aao ",Toast.LENGTH_LONG).show();
                    System.out.print("1");
                } else {
                    try {
                //        Toast.makeText(AddToCart.this, response, Toast.LENGTH_LONG).show();
                        System.out.print("In try block");
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = jsonObject.getJSONArray("result");

                        JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                        String p_ids = jsonObject1.getString("p_id");
                        p_name = jsonObject1.getString("p_name");
                        p_price = jsonObject1.getString("p_price");
                        String p_quantityy = jsonObject1.getString("p_quantity");
                        String val = jsonObject1.getString("val");
                        if (val.equals("true")) {

                            p_idt = Integer.parseInt(p_ids);
                            p_quantityyy = Integer.parseInt(p_quantityy);
                            addedToCart(p_idt,p_quantityyy);

                        } else {
                            Toast.makeText(AddToCart.this, "Insufficient Stock", Toast.LENGTH_SHORT).show();
                        }

                        //Toast.makeText(AddToCart.this,"RESPONSE MEIN = "+p_name,Toast.LENGTH_LONG).show();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("Error", "" + error);
                Log.i("RishabhURLerror", "" + error);
            }
        }
        );
        requestQueue.add(stringRequest);

    }

    public void addedToCart(final int p_id,final int p_quantityy)
    {
        p_quantity= p_quantityy - quantity;
        if(p_id!=0){
            try {
                String add_to_carts = "https://qrstest.000webhostapp.com/addToCart.php";
                URL url = new URL(add_to_carts);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream OS = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
                String data = URLEncoder.encode("tokenId", "UTF-8") + "=" + URLEncoder.encode(tokenId, "UTF-8") + "&" +
                        URLEncoder.encode("p_id", "UTF-8") + "=" + URLEncoder.encode(""+p_id, "UTF-8")+ "&" +
                        URLEncoder.encode("p_quant_rem", "UTF-8") + "=" + URLEncoder.encode(""+p_quantity, "UTF-8")+ "&" +
                        URLEncoder.encode("p_quantity", "UTF-8") + "=" + URLEncoder.encode(""+quantity, "UTF-8");
                Toast.makeText(AddToCart.this, data, Toast.LENGTH_SHORT).show();
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();
                InputStream IS = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(IS, "iso-8859-1"));
                String response = "";
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    response += line;
                }
                bufferedReader.close();
                IS.close();
                httpURLConnection.disconnect();

                //Toast.makeText(AddToCart.this,response,Toast.LENGTH_LONG).show();

                if (response.equals("added")) {

                    Toast.makeText(AddToCart.this, "Product Added to Cart", Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(AddToCart.this,CurrentCart.class);
                    Bundle bundle=new Bundle();
                    bundle.putString("pName",p_name);
                    bundle.putString("pPrice",p_price);
                    bundle.putString("pQuantity",String.valueOf(quantity));
                    bundle.putString("token_id",tokenId);
                    intent.putExtras(bundle);
                    progressBar.setVisibility(View.GONE);
                    startActivity(intent);

                } else {
                    Toast.makeText(AddToCart.this,"BADIYA BADIYA", Toast.LENGTH_LONG).show();
                }

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else
        {
            Toast.makeText(AddToCart.this,"No Product Added",Toast.LENGTH_SHORT).show();
        }
    }

}