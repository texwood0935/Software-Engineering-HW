package com.example.mathtest;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

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

public class GoldCoins extends AppCompatActivity {
//懒汉式的单例模式
    private static GoldCoins instance =null;

    public static GoldCoins getInstance(){
        if (instance == null) {
            synchronized (GoldCoins.class) {
                if (instance == null) {
                    instance = new GoldCoins();
                }
            }
        }
        return instance;
    }
    private GoldCoins(){coins=0;}

    private int coins;

    public boolean AddCoins(int increaseNum){
        if((coins+increaseNum)<0){
            return false;
        }
        coins += increaseNum;
        return true;
    }

    public String GetCoins_String(){
        return String.valueOf(coins);
    }
    public int GetCoins_Int(){
        return coins;
    }

    public int SetCoins(int nums){
        coins = nums;
        return coins;
    }

    //从文件中读取金币数量
    private void CoinsLoad (){
        SharedPreferences sp=getSharedPreferences("coins",MODE_PRIVATE);
        this.SetCoins(sp.getInt("coins",0));
    }

    //将金币数量保存在本地
    public void CoinsSave(){
        SharedPreferences sp=getSharedPreferences("coins",MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putInt("coins",this.GetCoins_Int());
        editor.commit();
    }

    @Override
    protected void onStop(){
        CoinsSave();
        super.onStop();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        instance.CoinsLoad();
    }
}
