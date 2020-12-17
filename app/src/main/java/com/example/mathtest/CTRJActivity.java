package com.example.mathtest;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;


public class CTRJActivity extends BaseActivity {

    Toolbar toolbar;
    Button DELETEbtn;
    private ListView listView;
    private ArrayList<TM> TMArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ctrj);
        toolbar = findViewById(R.id.toolbar_ctrj);
        toolbar.setNavigationIcon(R.mipmap.ic_keyboard_arrow_left_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                finish();
            }
        });

        listView = findViewById(R.id.ErrorListView);
        if (ErrorLoad() == 0){
            //创建返回的对话框
            AlertDialog.Builder isReturn = new AlertDialog.Builder(this);
            //设置标题
            isReturn.setTitle("消息提醒");
            //设置对话框的消息
            isReturn.setMessage("还没有历史错题哦，先去做题吧！");
            //设置按钮并进行监听
            isReturn.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    CTRJActivity.this.finish();
                }
            });
            isReturn.show();
        }
        else {
            CTRJAdapter errorQuestionAdapter = new CTRJAdapter(CTRJActivity.this,R.layout.item,TMArrayList);
            listView.setAdapter(errorQuestionAdapter);
        }

        DELETEbtn = findViewById(R.id.DELETEBtn);
        DELETEbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listView.setAdapter(null);
                String path=GetPath();
                File file=new File(path);
                file.delete();

            }
        });

    }

    private int  ErrorLoad (){
        String path = this.getFilesDir().getPath() + "/ErrorTM.json";
        File file = new File(path);
        BufferedReader reader = null ;
        String errorload = "";

        //判断有无文件存在
        if (!file.exists()){
            return 0;
        }

        //读取文件存放在String errorload中
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, Charset.forName("UTF-8"));
            reader = new BufferedReader(inputStreamReader);
            String tempString = null;
            while((tempString = reader.readLine())!=null){
                errorload += tempString;
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

        //将String errorload 转为列表
        System.out.println(errorload);
        TMArrayList  = JSON.parseObject(errorload, new TypeReference<ArrayList<TM>>(){});
        return TMArrayList.size();
    }



    private String GetPath(){
        String path = this.getFilesDir().getPath() + "/Error.json";
        return path;
    }


}