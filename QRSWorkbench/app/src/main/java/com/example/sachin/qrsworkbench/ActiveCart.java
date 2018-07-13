package com.example.sachin.qrsworkbench;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ActiveCart extends AppCompatActivity {
    ListView dataView;
    List<String> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_cart);
        dataView = findViewById(R.id.activeCartsList);
        dataList = new ArrayList<>();
        String custid = null, amount = null;
        //data retrieval code goes here

        //loop to start added here
        //retrieve all variables here one by one
      //  ActiveCartData data = new ActiveCartData(custid, amount);
       // dataList.add(data);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new ReadJSON().execute("http://qrstest.000webhostapp.com/activeCarts.php");
            }
        });
      /*  dataView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ActiveCartData obj = (ActiveCartData) adapterView.getItemAtPosition(i);
                String tok = obj.getCust_id();
                Intent intent = new Intent(ActiveCart.this, CurrentCart.class);
                intent.putExtra("cartToken", tok);
                startActivity(intent);
            }
        });
        */
    }
    class ReadJSON extends AsyncTask<String, Integer, String> {

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(String... params) {
            return readURL(params[0]);
        }

        @Override
        protected void onPostExecute(String content) {
            Toast.makeText(ActiveCart.this,content,Toast.LENGTH_LONG).show();
            try {
                if (content.equals("empty")) {
                    Toast.makeText(ActiveCart.this, "No entries found in this category!", Toast.LENGTH_LONG).show();
                } else {
                    JSONObject jsonObject = new JSONObject(content);
                    JSONArray jsonArray = jsonObject.getJSONArray("");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject productObject = jsonArray.getJSONObject(i);

                        dataList.add(productObject.getString("tokenid"));
                    }
                    Toast.makeText(ActiveCart.this,dataList.toString() ,Toast.LENGTH_LONG).show();
                    ArrayAdapter <String> adapter = new ArrayAdapter<String>(ActiveCart.this,android.R.layout.simple_list_item_1,dataList);
                   dataView.setAdapter(adapter);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    private static String readURL(String theUrl) {

        StringBuilder content = new StringBuilder();

        try {
            URL url = new URL(theUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String line;

            while ((line = bufferedReader.readLine()) != null) {
                content.append(line + "\n");
            }

            bufferedReader.close();
            inputStream.close();

        } catch (MalformedURLException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        return content.toString();

    }
}