package com.example.sachin.qrsworkbench;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SalesmanProfile extends AppCompatActivity {
TextView uname;
Button product,newCart,activeCarts;
AlertDialog alertdialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salesman_profile);
        SharedPreferences preferences =getSharedPreferences("mypref", Context.MODE_PRIVATE);
        String name = preferences.getString("u_name","");
        uname=findViewById(R.id.usnames);
        if(name!=null) {
            uname.setText(name);
        }
        product=findViewById(R.id.productadd);
        newCart=findViewById(R.id.newCartBtn);
        activeCarts=findViewById(R.id.activeCartsBtn);

        newCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

         Intent intent = new Intent(SalesmanProfile.this,NewCart.class);
         startActivity(intent);

            }
        });
        activeCarts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SalesmanProfile.this,ActiveCart.class);
                startActivity(intent);
            }
        });
    }
    /*public void logout(View view)
    {
        SharedPreferences preferences =getSharedPreferences("mypref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
        finish();
        Intent intent = new Intent(SalesmanProfile.this,Login.class);
        startActivity(intent);
    }*/

    public void productadd(View view)
    {
        Intent intent = new Intent(SalesmanProfile.this,AddProduct.class);
        startActivity(intent);
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

            alertdialog = new AlertDialog.Builder(SalesmanProfile.this).create();
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
                    Intent intent = new Intent(SalesmanProfile.this,Login.class);
                    startActivity(intent);
                }
            });
            alertdialog.show();
            return true;

        }
        if(id == R.id.id_aboutus){

            Intent intent = new Intent(SalesmanProfile.this,AboutUs.class);
            startActivity(intent);
            return true;

        }
        if(id == R.id.id_settings){

            Intent intent = new Intent(SalesmanProfile.this,Settings.class);
            startActivity(intent);
            return true;

        }

        return true;
    }

}
