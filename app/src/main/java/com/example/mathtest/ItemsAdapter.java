package com.example.mathtest;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import java.util.List;

import java.util.ArrayList;

public class ItemsAdapter extends BaseAdapter {
    private int resourceId;
    private LayoutInflater inflater;
    private List<Items> itemsList;
    Button BUYbtn;

    public ItemsAdapter(Context context,int resourceId, List<Items> objects) {
        this.itemsList = objects;
        this.inflater=LayoutInflater.from(context);
        this.resourceId=resourceId;
    }

    @Override
    public int getCount(){
        return itemsList==null?0:itemsList.size();
    }
    @Override
    public Items getItem(int position){
        return itemsList.get(position);
    }
    @Override
    public long getItemId(int position){
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        final Items items = getItem(position);
        View view;
        final ViewHolder viewHolder;
        if (convertView == null ){
            view = inflater.inflate(resourceId,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.name = view.findViewById(R.id.shopname);
            viewHolder.status = view.findViewById(R.id.status);
            viewHolder.price = view.findViewById(R.id.price);
            viewHolder.coins = view.findViewById(R.id.coins);
            viewHolder.buy_status=view.findViewById(R.id.buy_status);
            view.setTag(viewHolder);
            BUYbtn=(Button)view.findViewById(R.id.BUYBtn);
        }
        else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
            BUYbtn=(Button)view.findViewById(R.id.BUYBtn);
        }
        BUYbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!GoldCoins.getInstance().AddCoins(-items.getPrice_Int())){
                    String Bstring="抱歉，你的金币不足，无法购买TAT";
                    viewHolder.buy_status.setText(Bstring);
                }
                else{
                    String Bstring="恭喜购买成功^v^";
                    String Cstring = "你的金币: " + GoldCoins.getInstance().GetCoins_String();
                    viewHolder.coins.setText(Cstring);
                    viewHolder.buy_status.setText(Bstring);
                    notifyDataSetChanged();
                }
            }
        });
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
        TextView buy_status;
    }

}
