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

public class CurrentCartDataList extends ArrayAdapter<CurrentCartData> {
    private Activity context;
    private List<CurrentCartData> dataList;

    public CurrentCartDataList(Activity context, List<CurrentCartData> dataList) {
        super(context, R.layout.cartproductlist, dataList);
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = context.getLayoutInflater();
        View listViewItem = layoutInflater.inflate(R.layout.cartproductlist, null, true);

        TextView p_name,price,quantity;
        p_name=listViewItem.findViewById(R.id.p_nameCart);
        price=listViewItem.findViewById(R.id.priceCart);
        quantity=listViewItem.findViewById(R.id.quantityCart);

        CurrentCartData data1=dataList.get(position);
        p_name.setText(data1.getPname());
        price.setText(data1.getPrice());
        quantity.setText(data1.getQuantity());
        return listViewItem;
    }
}
