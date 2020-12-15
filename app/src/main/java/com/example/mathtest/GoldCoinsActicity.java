package com.example.mathtest;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.TypeReference;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import java.nio.charset.StandardCharsets;

public class GoldCoinsActicity extends AppCompatActivity {

    private class GoldCoins {

        private int coins;
    
        public GoldCoins(int initCoinsNum) {
            coins = initCoinsNum;
        }
    
        public int AddCoins(int increaseNum){
            coins += increaseNum;
            return coins;
        }
    
        public int GetCoins(){
            return coins;
        }
    
        public int SetCoins(int nums){
            coins = nums;
            return coins;
        }
    
    }

    GoldCoins Coins=new GoldCoins(0);  //金币

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void initCoinsNum() {
        CoinsLoad();
    }

    //从文件中读取金币数量
    private int CoinsLoad (){
        String path = this.getFilesDir().getPath() + "/GoldCoins.json";
        File file = new File(path);
        BufferedReader reader = null ;
        String goldload = "";

        //判断有无文件存在
        if (!file.exists()){
            Coins.SetCoins(0);
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
        Coins=JSON.parseObject(goldload,GoldCoins.class);

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
        
        String str = JSON.toJSONString(Coins);
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
        //Log.d("TAG", "file=" + file.getAbsolutePath());
    }

    //增加金币数量
    public boolean IncreaseCoins(int increaseNum){
        Coins.AddCoins(increaseNum);
        return true;
    }

    //获得金币数量
    public String GetCoinsNum(){
        
        return String.valueOf(Coins.GetCoins());
    }
}
