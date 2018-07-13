package com.example.sachin.qrsworkbench;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintJob;
import android.print.PrintManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CurrentCart extends AppCompatActivity {
    AlertDialog alertdialog;
    String token;
    ListView activeCartList;
    static List<CurrentCartData> dataList=new ArrayList<>();;
    Button addP, checkout;
    TextView tokenI, amount, items;
    String pname = null, price = null, quantity = null;
    private WebView mWebView;
    static float total=0;
    static int item=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_cart);
        activeCartList = findViewById(R.id.activeCartsList);
       // dataList = new ArrayList<>();

        addP = findViewById(R.id.addProduct);
        checkout = findViewById(R.id.checkout);
        tokenI = findViewById(R.id.tokenId);
        amount = findViewById(R.id.amount);
        items = findViewById(R.id.noOfItems);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        token=intent.getStringExtra("token_id");
       /* if(intent.getStringExtra("token_id")==null)
        {
            token=intent.getStringExtra("token_id");
        }
        if(intent.getStringExtra("cartToken")==null)
        {
            token=intent.getStringExtra("cartToken");
        }
*/
        pname = bundle.getString("pName");
        price = bundle.getString("pPrice");
        quantity = bundle.getString("pQuantity");

        tokenI.setText(token);
        for(int i=1;i<dataList.size();i++)
        {
            CurrentCartData obj=dataList.get(i);

            int price1=Integer.parseInt(obj.getPrice());
            int quant=Integer.parseInt(obj.getQuantity());

                total +=price1*quant;
                item+=price1;
        }
        amount.setText(String.valueOf(total));
        items.setText("");

        CurrentCartData data = new CurrentCartData(pname, price, quantity);
        dataList.add(data);

        CurrentCartDataList adapter = new CurrentCartDataList(CurrentCart.this, dataList);
        activeCartList.setAdapter(adapter);
        addP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CurrentCart.this, AddToCart.class);
                intent.putExtra("token_id", token);
                startActivity(intent);
            }
        });
    }

    private void createWebPrintJob(WebView webView) {

        // Get a PrintManager instance
        PrintManager printManager = (PrintManager) CurrentCart.this
                .getSystemService(Context.PRINT_SERVICE);

        // Get a print adapter instance
        PrintDocumentAdapter printAdapter = webView.createPrintDocumentAdapter();

        // Create a print job with name and adapter instance
        String jobName = getString(R.string.app_name) + " Document";
        PrintJob printJob = printManager.print(jobName, printAdapter,
                new PrintAttributes.Builder().build());
    }

    public void bill(View view) {

        WebView webView = new WebView(CurrentCart.this);
        webView.setWebViewClient(new WebViewClient() {

            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                createWebPrintJob(view);
                mWebView = null;
            }
        });
        String a="<html> <body> <p align='center'>Store Name</p><p align='center'>Invoice</p><p align='left'>Customer Id:"+token+"</p><p align='center'>Contents</p><table border='1' cellpadding='0' cellspacing='0' style='border-collapse: collapse' bordercolor='#111111' width='100%'><tr><td width='33%'><p align='center'>Product Name</td><td width='33%'><p align='center'>Quantity</td><td width='4%'><p align='center'>Amount</td></tr>";
        String b="";
        String name=null;
        // Generate an HTML document on the fly:
        for(int i=1;i<dataList.size();i++)
        {
            CurrentCartData obj=dataList.get(i);
            name=obj.getPname();
            int price1=Integer.parseInt(obj.getPrice());
            int quant=Integer.parseInt(obj.getQuantity());
            if(!name.equals(null)) {
                b += "<tr><td width='3%'><p align='center'>" + name + "</td><td width='33%'><p align='center'>" + quant + "</td><td width='34%'><p align='center'>" + price1 + "</td></tr>";
                total +=price1*quant;
                item+=quant;
            }
        }
        String c= "</table><p> Total Amount:"+total+" </p></body></html>";
        String htmlDocument=a+b+c;
        webView.loadDataWithBaseURL(null, htmlDocument, "text/HTML", "UTF-8", null);

        // Keep a reference to WebView object until you pass the PrintDocumentAdapter
        // to the PrintManager
        mWebView = webView;
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

            alertdialog = new AlertDialog.Builder(CurrentCart.this).create();
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
                    Intent intent = new Intent(CurrentCart.this,Login.class);
                    startActivity(intent);
                }
            });
            alertdialog.show();
            return true;

        }
        if(id == R.id.id_aboutus){

            Intent intent = new Intent(CurrentCart.this,AboutUs.class);
            startActivity(intent);
            return true;

        }
        if(id == R.id.id_settings){

            Intent intent = new Intent(CurrentCart.this,Settings.class);
            startActivity(intent);
            return true;

        }

        return true;
    }
}
