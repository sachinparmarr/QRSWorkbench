package com.example.sachin.qrsworkbench;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

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
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

//String[] categoryList;


public class AddProduct extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Button addProductBtn;
    Spinner brandSpinner,categorySpinner;
    //String[] categoryList = getResources().getStringArray(R.array.categoryList);
    EditText pid ,pname, pprice, pquantity;
    String add_product_url = "https://qrstest.000webhostapp.com/addProduct.php";
    ProgressDialog progressDialog;
    ArrayAdapter<String> brand_Adapter;
    ArrayList<String> brand_list = new ArrayList<String>();
    int c_id,c_ids;
    String c_name, brand_name,p_name;
    int p_price,p_quantity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build();
        StrictMode.setThreadPolicy(policy);

        addProductBtn = findViewById(R.id.addProductBtn);
        pname=findViewById(R.id.productName);
        pprice=findViewById(R.id.productPrice);
        pquantity=findViewById(R.id.productQuantity);
        brandSpinner = findViewById(R.id.brandSpinner);
        categorySpinner = findViewById(R.id.categorySpinner);
        brandSpinner.setOnItemSelectedListener(this);
        categorySpinner.setOnItemSelectedListener(this);
        c_id=0;
        c_ids=0;

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.categoryList));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);



        brandSpinner = findViewById(R.id.brandSpinner);


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

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        Spinner sp = (Spinner) adapterView;
        if (sp.getId() == brandSpinner.getId()) {
           Toast.makeText(this,"Hi",Toast.LENGTH_SHORT).show();
        }
        if (sp.getId() == categorySpinner.getId()) {
            c_name = categorySpinner.getSelectedItem().toString().trim();
            c_id = renderSpinner(c_name);
            brand_list.clear();
            Toast.makeText(AddProduct.this,"https://qrstest.000webhostapp.com/sachin_test/listBrand.php?c_id="+c_id,Toast.LENGTH_SHORT).show();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    new ReadJSON().execute("https://qrstest.000webhostapp.com/sachin_test/listBrand.php?c_id="+c_id);
                }
            });

            addProductBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                c_name=categorySpinner.getSelectedItem().toString().trim();
                brand_name=brandSpinner.getSelectedItem().toString().trim();
                p_name=pname.getText().toString().trim();
                p_price=Integer.parseInt(pprice.getText().toString().trim());
                p_quantity=Integer.parseInt(pquantity.getText().toString().trim());

                    if(c_name.isEmpty() ||p_name.isEmpty() ||brand_name.isEmpty() ||p_price==0 ||p_quantity==0)
                    {
                        Toast.makeText(AddProduct.this,"Fill All The Details",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        //String pid="hello";
                        //To receive returned product id
                        String p_id = addProduct(p_name,c_id,brand_name,p_price,p_quantity);
                        Toast.makeText(AddProduct.this,"here",Toast.LENGTH_LONG).show();
                        Intent intent=new Intent(AddProduct.this,QRGenerate.class);
                        intent.putExtra("product_id",p_id);
                        startActivity(intent);
                    }
                //Toast.makeText(AddProduct.this,c_name+" "+brand_name+" "+p_name+" "+p_price+" "+p_quanity,Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    class ReadJSON extends AsyncTask<String, Integer, String> {

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(AddProduct.this);
            progressDialog.setMessage("Loading...Please Wait!");
            progressDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {
            return readURL(params[0]);
        }

        @Override
        protected void onPostExecute(String content) {
            try {
                if(content.equals("empty")) {
                    Toast.makeText(AddProduct.this, "No entries found in this category!", Toast.LENGTH_LONG).show();
                }
                else {
                    JSONObject jsonObject = new JSONObject(content);
                    JSONArray jsonArray = jsonObject.getJSONArray("res");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject productObject = jsonArray.getJSONObject(i);
                        brand_list.add(productObject.getString("cat"));
                    }
                    brand_Adapter = new ArrayAdapter<String>(AddProduct.this, android.R.layout.simple_spinner_item, brand_list);
                    brand_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    brandSpinner.setAdapter(brand_Adapter);
                }
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
            progressDialog.dismiss();
        }

    }
    public int renderSpinner(final String c_name)
    {

        if (c_name.isEmpty()||c_name.equals("Select Category"))
            Toast.makeText(AddProduct.this, "Category Cannot Be Empty", Toast.LENGTH_SHORT).show();

        if(c_name.equals("Household")) {
            c_ids = 1;
        }
        if(c_name.equals("Personal Care")) {
            c_ids = 2;
        }
        if (c_name.equals("Electronics"))
        {
            c_ids= 3;
        }
        if (c_name.equals("Food"))
        {
            c_ids= 4; }

         System.out.print("cid ki value"+c_id);
        return  c_ids;
    }
    public String addProduct(final String p_name,final int c_id,final String brand_name, final int p_price, final int p_quantity)
    {       String p_id = "0";
        try
        {
            URL url = new URL(add_product_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream OS = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS,"UTF-8"));
            String data = URLEncoder.encode("p_name", "UTF-8") + "=" + URLEncoder.encode(p_name, "UTF-8") + "&" +
                    URLEncoder.encode("c_id", "UTF-8") + "=" + URLEncoder.encode(""+c_id, "UTF-8") + "&" +
                    URLEncoder.encode("brand_name", "UTF-8") + "=" + URLEncoder.encode(brand_name, "UTF-8") + "&" +
                    URLEncoder.encode("p_price", "UTF-8") + "=" + URLEncoder.encode(""+p_price, "UTF-8") + "&" +
                    URLEncoder.encode("p_quantity", "UTF-8") + "=" + URLEncoder.encode(""+p_quantity, "UTF-8") ;
            bufferedWriter.write(data);
            bufferedWriter.flush();
            bufferedWriter.close();
            OS.close();
            InputStream IS = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(IS, "iso-8859-1"));
            String response = "";
            String line;
            while ((line = bufferedReader.readLine())!= null) {
                response += line;
            }
            p_id = response;
            bufferedReader.close();
            IS.close();
            httpURLConnection.disconnect();
            Toast.makeText(this,"Product id = "+response,Toast.LENGTH_LONG).show();
            if(response.equals("1")) {

                Toast.makeText(this," Product Added!",Toast.LENGTH_LONG).show();

            }

            else {
                Toast.makeText(this,response,Toast.LENGTH_LONG).show();
            }


        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return p_id;

    }
}




