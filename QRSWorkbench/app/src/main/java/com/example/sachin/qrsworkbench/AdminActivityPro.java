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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.w3c.dom.Text;

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
import java.util.List;

public class AdminActivityPro extends AppCompatActivity {
    AlertDialog alertdialog;
    EditText fieldBrand;
    Button brandBtn;
    Button logoutBtn;
    Spinner categories;
    int c_id = 0,c_ids;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_pro);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build();
        StrictMode.setThreadPolicy(policy);


        fieldBrand = findViewById(R.id.fieldBrand);
         brandBtn = findViewById(R.id.brandBtn);

         //logoutBtn = findViewById(R.id.logoutBtn);
         categories=findViewById(R.id.category);
        List<String> spinnerArray =  new ArrayList<String>();
        spinnerArray.add("Select Category");
        spinnerArray.add("Household");
        spinnerArray.add("Personal Care");
        spinnerArray.add("Electronics");
        spinnerArray.add("Food");



        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categories.setAdapter(adapter);

          /*  categoryBtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 //Toast.makeText(AdminActivityPro.this,"Inside Category",Toast.LENGTH_SHORT).show();
                String CATEGORY = fieldCategory.getText().toString().trim();
                 addCategoryDb(CATEGORY);
             }
         }); */


       brandBtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 //Toast.makeText(AdminActivityPro.this,"Inside Brand",Toast.LENGTH_SHORT).show();

                 String c_name = categories.getSelectedItem().toString().trim();
                 c_ids = renderSpinner(c_name);
                String BRAND = fieldBrand.getText().toString().trim();
                /*c_id = renderSpinner();*/
                 Toast.makeText(AdminActivityPro.this, "C ID is"+c_ids,Toast.LENGTH_SHORT);
                addBrandDb(BRAND,c_ids);
             }
         });

        /*logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences preferences =getSharedPreferences("mypref", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.apply();
                finish();
                Toast.makeText(AdminActivityPro.this,"Logged out Sucessfully",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AdminActivityPro.this,Login.class);
                startActivity(intent);
            }
        });*/
    }
    /*public void addCategoryDb(final String c_name)
    {
        String add_category_url = "https://qrstest.000webhostapp.com/addCategory.php";
        if(c_name.isEmpty())
        Toast.makeText(AdminActivityPro.this,"Field is Empty",Toast.LENGTH_SHORT).show();
        else {
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

                    Toast.makeText(this,"Category Added",Toast.LENGTH_LONG).show();
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
        }
    }*/

    public void addBrandDb(final String brand_name,int c_ids) {
        String add_brand_url = "https://qrstest.000webhostapp.com/addBrand.php";
        if (brand_name.isEmpty())
            Toast.makeText(AdminActivityPro.this, "Field is Empty", Toast.LENGTH_SHORT).show();
        else {
            try {
                URL url = new URL(add_brand_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream OS = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
                String data = URLEncoder.encode("brand_name", "UTF-8") + "=" + URLEncoder.encode(brand_name, "UTF-8") + "&" +
                        URLEncoder.encode("c_id", "UTF-8") + "=" + URLEncoder.encode(""+c_ids, "UTF-8");
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

                if (response.equals("1")) {

                    Toast.makeText(this, "Brand Added", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, response, Toast.LENGTH_LONG).show();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public int renderSpinner(final String c_name)
    {
     /*List<String> spinnerArray =  new ArrayList<String>();
        spinnerArray.add("Select Category");
        spinnerArray.add("Household");
        spinnerArray.add("Personal Care");
        spinnerArray.add("Electronics");
        spinnerArray.add("Food");



     ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerArray);
     adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
     categories.setAdapter(adapter);*/

    // String c_name = categories.getSelectedItem().toString().trim();
     if (c_name.isEmpty()||c_name.equals("Select Category"))
         Toast.makeText(AdminActivityPro.this, "Category Cannot Be Empty", Toast.LENGTH_SHORT).show();

     if(c_name.equals("Household")) {
         c_id = 1;
     }
     if(c_name.equals("Personal Care")) {
         c_id = 2;
     }
     if (c_name.equals("Electronics"))
     {
         c_id= 3;
     }
     if (c_name.equals("Food"))
     {
            c_id= 4; }

           // System.out.print("cid ki value"+c_id);
        return  c_id;
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


            alertdialog = new AlertDialog.Builder(AdminActivityPro.this).create();
            alertdialog.setTitle("Logout");
            alertdialog.setMessage("Are you sure ! logout");
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
                    Intent intent = new Intent(AdminActivityPro.this,Login.class);
                    startActivity(intent);
                }
            });
            alertdialog.show();
            return true;

           /* SharedPreferences preferences =getSharedPreferences("mypref", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.apply();
            finish();
            Toast.makeText(AdminActivityPro.this,"Logged out Sucessfully",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(AdminActivityPro.this,Login.class);
            startActivity(intent);
*/
        }
        if(id == R.id.id_aboutus){

            Intent intent = new Intent(AdminActivityPro.this,AboutUs.class);
            startActivity(intent);
            return true;

        }
        if(id == R.id.id_settings){

            Intent intent = new Intent(AdminActivityPro.this,Settings.class);
            startActivity(intent);
            return true;

        }

        return true;
    }



}
