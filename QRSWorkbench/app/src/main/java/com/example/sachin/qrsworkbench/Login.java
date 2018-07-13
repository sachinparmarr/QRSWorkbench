package com.example.sachin.qrsworkbench;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class Login extends AppCompatActivity {
   /* public static final String LOGIN_URL="http://rahulkr.esy.es/UserRegistration/login.php";
    public static final String KEY_PHONE="phone";
    public static final String KEY_PASSWORD="password";
    public static final String LOGIN_SUCCESS="success";
    public static final String SHARED_PREF_NAME="qrs";
    public static final String PHONE_SHARED_PREF="phone";
    public static final String LOGGEDIN_SHARED_PREF="loggedin";
    private boolean loggedIn=false;
*/
    private Button loginbtn, signupbtn;

    private EditText phone,password;
    TextView alerts;
    String u_phone, u_password;
    String url;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginbtn=findViewById(R.id.loginbtn);
        signupbtn=findViewById(R.id.signupbtn);
        phone=findViewById(R.id.phone);
        password=findViewById(R.id.password);
        progressBar=findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        alerts=findViewById(R.id.alerts);
        alerts.setText("Login to continue.");

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
          public void onClick(View view) {
                alerts.setText("");
                u_phone = phone.getText().toString();
                u_password = password.getText().toString();

                if(u_phone.isEmpty() || u_password.isEmpty())
                {
                    alerts.setTextColor(Color.RED);
                    alerts.setText("Enter Credentials!");
                    Toast.makeText(Login.this,"Fill Details",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if(u_phone.equals("321")&&u_password.equals("123"))
                    {
                        SharedPreferences shared = getSharedPreferences("mypref", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = shared.edit();
                        editor.putString("u_id", "007");
                        editor.putString("u_phone", u_phone);
                        editor.putString("u_password", u_password);
                        editor.putString("u_name","Admin");
                        editor.putString("u_dept", "admin");
                        editor.commit();
                        Intent intent=new Intent(Login.this,AdminActivityPro.class);
                        startActivity(intent);
                    }
                    else
                    login(u_phone,u_password);
                }


            }
        });
        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Login.this,Register.class);
                startActivity(intent);
            }
        });
    }

    public void login(final String u_phone,final String u_password)
    {
   // progressDialog.show(this,"Logging You In!","Please Wait",true,false);
        progressBar.setVisibility(View.VISIBLE);
        System.out.println("ghusa0");
        url = "https://qrstest.000webhostapp.com/login.php?u_phone="+u_phone+"&u_password="+u_password+"";
        Log.i("Rishabhurl",""+url);
        RequestQueue requestQueue = Volley.newRequestQueue(Login.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>()
        {

            @Override
            public void onResponse(String response) {

                if(response.equals("0"))
                {
                  progressBar.setVisibility(View.GONE);
                    Toast.makeText(Login.this,"Incorrect Credentials! \nTry Again!",Toast.LENGTH_SHORT).show();
                    alerts.setTextColor(Color.RED);
                    alerts.setText("Incorrect Phone/Password \nTry Again!");
                }
                else {
                    System.out.println("ghusa1");
                    try {
                        System.out.print("In try block");
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = jsonObject.getJSONArray("result");
                        JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                        String u_id = jsonObject1.getString("u_id");
                        String u_phone = jsonObject1.getString("u_phone");
                        String u_password = jsonObject1.getString("u_password");
                        String u_name = jsonObject1.getString("u_name");
                        String u_dept = jsonObject1.getString("u_dept");
                        SharedPreferences shared = getSharedPreferences("mypref", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = shared.edit();
                        editor.putString("u_id", u_id);
                        editor.putString("u_phone", u_phone);
                        editor.putString("u_password", u_password);
                        editor.putString("u_name", u_name);
                        editor.putString("u_dept", u_dept);
                        editor.commit();
                        progressBar.setVisibility(View.GONE);
                        if (u_dept.equals("Sales")) {
                            Intent intent = new Intent(Login.this, SalesmanProfile.class);
                            startActivity(intent);
                        }
                        if (u_dept.equals("Audit")) {
                            Intent intent = new Intent(Login.this, AuditProfile.class);
                            startActivity(intent);
                        }
                        if (u_dept.equals("Cash")) {
                            Intent intent = new Intent(Login.this, CashierProfile.class);
                            startActivity(intent);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override

            public void onErrorResponse(VolleyError error) {
                System.out.print("Error aaya!");
                Log.i("Error",""+error);
                Log.i("RishabhURLerror",""+error);
            }
        }
        );
        requestQueue.add(stringRequest);
    }
}
