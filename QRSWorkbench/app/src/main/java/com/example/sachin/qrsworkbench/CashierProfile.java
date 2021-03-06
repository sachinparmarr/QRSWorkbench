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

public class CashierProfile extends AppCompatActivity {
TextView uname;
AlertDialog alertdialog;
Button newcart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cashier_profile);
        uname=findViewById(R.id.usname);
        newcart = findViewById(R.id.newcartc);
        SharedPreferences preferences =getSharedPreferences("mypref", Context.MODE_PRIVATE);
       String name = preferences.getString("u_name","");
        if(name!=null) {
            uname.setText(name);
        }

        newcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CashierProfile.this,NewCart.class);
                startActivity(intent);
            }
        });

    }

   /* public void signout(View view)
    {
        SharedPreferences preferences =getSharedPreferences("mypref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
        finish();
        Intent intent = new Intent(CashierProfile.this,Login.class);
        startActivity(intent);
    }*/
    @Override

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id == R.id.id_logout){
            alertdialog = new AlertDialog.Builder(CashierProfile.this).create();
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
                    Intent intent = new Intent(CashierProfile.this,Login.class);
                    startActivity(intent);
                }
            });
            alertdialog.show();
            return true;

        }
        if(id == R.id.id_aboutus){

            Intent intent = new Intent(CashierProfile.this,AboutUs.class);
            startActivity(intent);
            return true;

        }
        if(id == R.id.id_settings){

            Intent intent = new Intent(CashierProfile.this,Settings.class);
            startActivity(intent);
            return true;

        }

        return true;
    }

}
