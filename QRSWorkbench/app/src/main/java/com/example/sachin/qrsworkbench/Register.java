package com.example.sachin.qrsworkbench;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    EditText editName;
    EditText editPhone;
    EditText editPassword;
    Spinner editDept;
    Button login;
    Button signup;
    ProgressBar progressBar;
   // private static final String REGISTER_URL = "https://qrstest.000webhostapp.com/userRegistration/registerUser.php";
    String url = "https://qrstest.000webhostapp.com/register.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ArrayList<String> l1 = new ArrayList<String>();
        l1.add("Select Dept");
        l1.add("Audit");
        l1.add("Cash");
        l1.add("Sales");
        ArrayAdapter<String> ad1 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, l1);
        editDept = findViewById(R.id.editDept);
        editDept.setAdapter(ad1);
        editName = (EditText) findViewById(R.id.editName);
        editPhone = (EditText) findViewById(R.id.editPhone);
        editPassword = (EditText) findViewById(R.id.editPassword);
        login = findViewById(R.id.login);
        signup = findViewById(R.id.signup);
        progressBar=findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });
    }

    private void loginUser() {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }

    private void registerUser() {

        String u_name = editName.getText().toString().trim();
        String u_phone = editPhone.getText().toString().trim();
        String u_password = editPassword.getText().toString().trim();
        String u_dept = editDept.getSelectedItem().toString().trim();
        if(u_name.isEmpty() ||u_password.isEmpty() ||u_dept.isEmpty() ||u_phone.isEmpty())
        {
            Toast.makeText(Register.this,"Fill All The Details",Toast.LENGTH_SHORT).show();
        }
        else {
            progressBar.setVisibility(View.VISIBLE);
            register(u_name, u_password, u_phone, u_dept);
            SharedPreferences preferences = getSharedPreferences("mypref", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.putString("editName",u_name);
            editor.putString("editPassword",u_password);
            editor.putString("editDept",u_dept);
            editor.putString("editPhone",u_phone);
            editor.commit();
            show(u_dept);
        }
    }
    public void show(String u_dept)
    {
        progressBar.setVisibility(View.GONE);
        if(u_dept.equals("Audit")) {
            Intent intent = new Intent(Register.this, AuditProfile.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        if(u_dept.equals("Sales")) {
            Intent intent = new Intent(Register.this, SalesmanProfile.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        if(u_dept.equals("Cash")) {
            Intent intent = new Intent(Register.this, CashierProfile.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }

    }
    private void register(final String u_name, final String u_password, final String u_phone, final String u_dept) {
        RequestQueue requestQueue = Volley.newRequestQueue(Register.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("Rishabh", "" + response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("Rishabh",""+error);
                Toast.makeText(Register.this, ""+error, Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String,String> getParams() throws AuthFailureError
            {
                Map<String,String> stringMap = new HashMap<>();

                stringMap.put("u_name",u_name);
                stringMap.put("u_phone",u_phone);
                stringMap.put("u_password",u_password);
                stringMap.put("u_dept",u_dept);
                return stringMap;
            }
        };
        requestQueue.add(stringRequest);

        /* String urlSuffix = "?u_name=" + u_name + "&u_password=" + u_password + "&u_phone=" + u_phone + "&u_dept=" + u_dept;

        class RegisterUser extends AsyncTask<String, Void, String> {

            ProgressDialog loading;
            @Override

            protected void onPreExecute()
            {
                super.onPreExecute();
                loading = ProgressDialog.show(Register.this,"Loading",null,true,true);
            }

            @Override

            protected void onPostExecute(String s)
            {
                loading.cancel();
                super.onPostExecute(s);
                Toast.makeText(Register.this, "Done", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected String doInBackground(String... strings) {
                String s = strings[0];
                BufferedReader bufferedReader = null;
                try
                {
                    URL url = new URL(REGISTER_URL+s);
                    //System.out.println("1");
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    //System.out.println("2");
                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    //System.out.println("3");
                    String result;
                    //System.out.println("4");
                    result = bufferedReader.readLine();
                    //System.out.println("5");
                    return result;
                }
                catch (Exception e)
                {
                return null;
                }
            }
        }
        RegisterUser ur=new RegisterUser();
        ur.execute(urlSuffix); */
    }
}
