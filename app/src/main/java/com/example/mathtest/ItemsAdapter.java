package com.example.mathtest;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ItemsAdapter extends ArrayAdapter<Items> {
    private int resourceId;

    public ItemsAdapter(Context context, int resource, ArrayList<Items> objects) {
        super(context, resource,objects);
        resourceId =  resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Items items = getItem(position);
        View view;
        ViewHolder viewHolder;
        if (convertView == null ){
            view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.name = view.findViewById(R.id.shopname);
            viewHolder.status = view.findViewById(R.id.status);
            viewHolder.price = view.findViewById(R.id.price);
            viewHolder.coins = view.findViewById(R.id.coins);
            view.setTag(viewHolder);
        }
        else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        String Nstring = items.getName();
        String Sstring = "商品描述: " + items.getStatus();
        String Pstring = "商品价格: " + items.getPrice_String();
        String Cstring = "你的金币: " + GoldCoins.getInstance().GetCoins_String();
        viewHolder.name.setText(Nstring);
        viewHolder.status.setText(Sstring);
        viewHolder.price.setText(Pstring);
        viewHolder.coins.setText(Cstring);
        return view;
    }

    class ViewHolder{
        TextView name;
        TextView status;
        TextView price;
        TextView coins;
    }

}
