package com.example.sachin.qrsworkbench;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import static java.lang.Thread.sleep;

public class SplashScreen extends AppCompatActivity {
    String audit = "Audit";
    String sales = "Sales";
    String cash = "Cash";
    ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        actionBar=getSupportActionBar();
        actionBar.hide();

        Thread thread = new Thread() {

            @Override
                    public void run()
            {
                try {
                    sleep(3000);
                    SharedPreferences shared = getSharedPreferences("mypref", Context.MODE_PRIVATE);
                    String val = shared.getString("u_phone","");
                    String dept = shared.getString("u_dept","");
                    if(val.length()==0)
                    {
                        Intent intent = new Intent(SplashScreen.this,Login.class);
                        startActivity(intent);
                        finish();
                    }
                    else {
                        if (dept.equals(sales)) {
                            Intent intent = new Intent(SplashScreen.this, SalesmanProfile.class);
                            startActivity(intent);
                            finish();
                        }
                        if (dept.equals(audit)) {
                            Intent intent = new Intent(SplashScreen.this, AuditProfile.class);
                            startActivity(intent);
                            finish();
                        }
                        if (dept.equals(cash)) {
                            Intent intent = new Intent(SplashScreen.this, SalesmanProfile.class);
                            startActivity(intent);
                            finish();
                        }
                        if (dept.equals("admin")) {
                            Intent intent = new Intent(SplashScreen.this, AdminActivityPro.class);
                            startActivity(intent);
                            finish();

                        }
                    }


                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }

            }
        };

        thread.start();


    }
}
