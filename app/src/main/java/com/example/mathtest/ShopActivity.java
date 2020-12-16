package com.example.mathtest;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class ShopActivity extends AppCompatActivity {

   private ListView listView;
   private ArrayList<Items> itemsArrayList=new ArrayList<Items>(10);
   Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        toolbar = findViewById(R.id.toolbar_shop);
        toolbar.setNavigationIcon(R.mipmap.ic_keyboard_arrow_left_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                finish();
            }
        });

        listView = findViewById(R.id.ShopListView);

        if (CreateItems() == 0){
            //创建返回的对话框
            AlertDialog.Builder isReturn = new AlertDialog.Builder(this);
            //设置标题
            isReturn.setTitle("消息提醒");
            //设置对话框的消息
            isReturn.setMessage("Sorry，商城现在一件商品都没有TAT");
            //设置按钮并进行监听
            isReturn.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    ShopActivity.this.finish();
                }
            });
            isReturn.show();
        }
        else {
            CoinsLoad();
            ItemsAdapter itemsAdapter = new ItemsAdapter(ShopActivity.this,R.layout.shopitem,itemsArrayList);
            listView.setAdapter(itemsAdapter);
        }
    }

    private int CreateItems(){
        //ArrayList.add出了个sourse code dose not match the byte code的错误，百度了各种方法无法解决，先暂时搁置了
        return 0;
        //itemsArrayList.set(0,new Items("小红花","装饰用的十分美丽",10));
        //itemsArrayList.set(1,new Items("棒棒糖","十分甜，但是很快就吃完了",30));
       // return itemsArrayList.size();
    }
    private int  ItemsLoad (){
        String path = this.getFilesDir().getPath() + "/AShopItems.json";
        File file = new File(path);
        BufferedReader reader = null ;
        String itemsload = "";
        BufferedWriter writer = null;

        //判断有无文件存在
        if (!file.exists()){
            Items items=new Items("小红花","装饰用的十分美丽",10);
            itemsArrayList.add(items);
            items=new Items("棒棒糖","十分甜，可惜吃一下就吃完了",30);
            itemsArrayList.add(items);
            ItemSave();
            itemsArrayList.clear();
            return 0;
        }

        //读取文件存放在String itemsload中
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, StandardCharsets.UTF_8);
            reader = new BufferedReader(inputStreamReader);
            String tempString = null;
            while((tempString = reader.readLine())!=null){
                itemsload += tempString;
                //Items items=JSON.parseObject(tempString,Items.class);
                //itemsArrayList.add(items);
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        //将String itemload 转为列表
        System.out.println(itemsload);
        itemsArrayList  = JSON.parseObject(itemsload, new TypeReference<ArrayList<Items>>(){});
        return itemsArrayList.size();
    }

    private void ItemSave(){
        String path = this.getFilesDir().getPath() + "/AShopItems.json";
        File file = new File(path);
        String goldload = "";
        BufferedWriter writer = null;

        //判断有无文件存在
        if (!file.exists()){
            try {
                file.createNewFile();
            }catch (IOException e){
                e.printStackTrace();
            }
        }

        String str = JSON.toJSONString(itemsArrayList);
        System.out.println(str);
        try{
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file,false), StandardCharsets.UTF_8));
            writer.write(str);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (writer!=null)
                    writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("文件写入成功！");
    }

    //金币相关操作

    //从文件中读取金币数量
    private int CoinsLoad (){
        String path = this.getFilesDir().getPath() + "/GoldCoins.json";
        File file = new File(path);
        BufferedReader reader = null ;
        String goldload = "";

        //判断有无文件存在
        if (!file.exists()){
            return 0;
        }

        //读取文件存放在String goldload中
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, StandardCharsets.UTF_8);
            reader = new BufferedReader(inputStreamReader);
            String tempString = null;
            while((tempString = reader.readLine())!=null){
                goldload += tempString;
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        //将String loveload 转为列表
        System.out.println(goldload);
        GoldCoins.getInstance().SetCoins(JSON.parseObject(goldload,int.class));

        return 1;
    }

    //将金币数量保存在本地
    public void CoinsSave(){
        String path = this.getFilesDir().getPath() + "/GoldCoins.json";
        File file = new File(path);
        String goldload = "";
        BufferedWriter writer = null;

        //判断有无文件存在
        if (!file.exists()){
            try {
                file.createNewFile();
            }catch (IOException e){
                e.printStackTrace();
            }
        }

        String str = JSON.toJSONString(GoldCoins.getInstance().GetCoins_Int());
        System.out.println(str);
        try{
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file,false), StandardCharsets.UTF_8));
            writer.write(str);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (writer!=null)
                    writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("文件写入成功！");
    }
}
