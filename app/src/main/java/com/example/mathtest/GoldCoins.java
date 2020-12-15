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

public class GoldCoins {
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

    public int AddCoins(int increaseNum){
        coins += increaseNum;
        return coins;
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
}
