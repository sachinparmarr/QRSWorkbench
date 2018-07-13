package com.example.sachin.qrsworkbench;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

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

public class NewCart extends AppCompatActivity {
EditText token_id, token_name, token_phone;
Button newCartBtn;
String tokenid,cust_name,cust_phone;
ProgressBar progressBar;
AlertDialog alertdialog;
String new_cart_url ="https://qrstest.000webhostapp.com/newCart.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_cart);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build();
        StrictMode.setThreadPolicy(policy);

        token_id=findViewById(R.id.token_id);
        token_name=findViewById(R.id.token_name);
        token_phone=findViewById(R.id.token_phone);
        newCartBtn=findViewById(R.id.newCartBtn);
        progressBar=findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        newCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                tokenid=token_id.getText().toString().trim();
                cust_name=token_name.getText().toString().trim();
                cust_phone=token_phone.getText().toString().trim();

                int check = regCustomer(tokenid,cust_name,cust_phone);

                if(check==1)
                {
                    Intent intent = new Intent(NewCart.this,CurrentCart.class);
                    intent.putExtra("token_id",tokenid);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(NewCart.this,"CART NOT CREATED",Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

    public int regCustomer(final String tokenid,final String cust_name, final String cust_phone)
    {
        try
        {
            URL url = new URL(new_cart_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream OS = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS,"UTF-8"));
            String data = URLEncoder.encode("tokenid", "UTF-8") + "=" + URLEncoder.encode(tokenid, "UTF-8") + "&" +
                    URLEncoder.encode("cust_name", "UTF-8") + "=" + URLEncoder.encode(cust_name, "UTF-8") + "&" +
                    URLEncoder.encode("cust_phone", "UTF-8") + "=" + URLEncoder.encode(cust_phone, "UTF-8");
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
            //Toast.makeText(this,"Product id = "+response,Toast.LENGTH_LONG).show();
            if(response.equals("1")) {

                Toast.makeText(this,"CART CREATED SUCESSFULY",Toast.LENGTH_LONG).show();
                return 1;
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
        return 0;
    }
    @Override

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id == R.id.id_logout){

            alertdialog = new AlertDialog.Builder(NewCart.this).create();
            alertdialog.setTitle("Logout");
            alertdialog.setMessage("Are you sure! Logout?");
            alertdialog.setCancelable(false);
            alertdialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    alertdialog.dismiss();
                }
            });
            alertdialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    SharedPreferences preferences =getSharedPreferences("mypref", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.clear();
                    editor.apply();
                    finish();
                    Intent intent = new Intent(NewCart.this,Login.class);
                    startActivity(intent);
                }
            });
            alertdialog.show();
            return true;

        }
        if(id == R.id.id_aboutus){

            Intent intent = new Intent(NewCart.this,AboutUs.class);
            startActivity(intent);
            return true;

        }
        if(id == R.id.id_settings){

            Intent intent = new Intent(NewCart.this,Settings.class);
            startActivity(intent);
            return true;

        }

        return true;
    }

}
