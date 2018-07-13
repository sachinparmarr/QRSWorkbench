package com.example.sachin.qrsworkbench;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ActiveCartDataList extends ArrayAdapter<ActiveCartData> {
    private Activity context;
    private List<ActiveCartData> dataList;

    public ActiveCartDataList(Activity context, List<ActiveCartData> dataList) {
        super(context,R.layout.cartlist,dataList);
        this.context = context;
        this.dataList = dataList;
    }
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater=context.getLayoutInflater();
        View listViewItem= layoutInflater.inflate(R.layout.cartlist,null,true);

        TextView cust_id, amount;
        cust_id=listViewItem.findViewById(R.id.cust_id);
        amount=listViewItem.findViewById(R.id.amount);

        ActiveCartData data1 = dataList.get(position);
        cust_id.setText(data1.getCust_id());
        amount.setText(data1.getAmount());

        return listViewItem;
    }
}
