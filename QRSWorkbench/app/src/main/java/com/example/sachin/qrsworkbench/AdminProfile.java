package com.example.sachin.qrsworkbench;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class AdminProfile extends AppCompatActivity {
    EditText category, brand;
    Button addbr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_profile);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build();
        StrictMode.setThreadPolicy(policy);

        category = findViewById(R.id.editCategoryName);
        brand = findViewById(R.id.editBrandName);
        addbr = findViewById(R.id.addBrandBtn);

        addbr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String b_name = brand.getText().toString().trim().toLowerCase();
                String add_brand_url = "https://qrstest.000webhostapp.com/addBrand.php";
                System.out.print("hi");
                if(b_name.isEmpty())
                {
                    Toast.makeText(AdminProfile.this,"Field Cannot be Empty",Toast.LENGTH_LONG);

                }
            }
        });
    }

    public void addCategory(View view) {
        final String c_name = category.getText().toString().trim().toLowerCase();
        String add_category_url = "https://qrstest.000webhostapp.com/addCategory.php";
        if (c_name.isEmpty()) {
            Toast.makeText(this, "Field Cannot be Empty", Toast.LENGTH_LONG);

        } else {
            try
            {
                URL url = new URL(add_category_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream OS = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
                String data = URLEncoder.encode("c_name", "UTF-8") + "=" + URLEncoder.encode(c_name, "UTF-8");
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
                bufferedReader.close();
                IS.close();
                httpURLConnection.disconnect();

                if(response.equals("1")) {

                    Toast.makeText(this," Category Added!",Toast.LENGTH_LONG).show();
                }

                else {
                    Toast.makeText(this,response,Toast.LENGTH_LONG).show();
                }


            }
            catch (MalformedURLException e)
            {
                e.printStackTrace();
            }catch (ProtocolException e)
            {
                e.printStackTrace();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
    public  void add_Brand(View view)
    {
        String b_name = brand.getText().toString().trim().toLowerCase();
        String add_brand_url = "https://qrstest.000webhostapp.com/addBrand.php";
        System.out.print("hi");
        if(b_name.isEmpty())
        {
            Toast.makeText(this,"Field Cannot be Empty",Toast.LENGTH_LONG);

        }
        else
        {
            try
            {
                URL url = new URL(add_brand_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream OS = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
                String data = URLEncoder.encode("b_name", "UTF-8") + "=" + URLEncoder.encode(b_name, "UTF-8");
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
                bufferedReader.close();
                IS.close();
                httpURLConnection.disconnect();

                if(response.equals("1")) {

                    Toast.makeText(this,"Brand Added",Toast.LENGTH_LONG).show();
                }

                else {
                    Toast.makeText(this,response,Toast.LENGTH_LONG).show();
                }


            }
            catch (MalformedURLException e)
            {
                e.printStackTrace();
            }catch (ProtocolException e)
            {
                e.printStackTrace();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}
