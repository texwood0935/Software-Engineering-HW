package com.example.mathtest;

import android.content.Intent;

import android.os.Bundle;

import android.view.View;

import android.widget.Button;

public class MainActivity extends BaseActivity {
    Button JZCTbtn;
    Button KSKSbtn;
    Button CTRJbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        JZCTbtn = findViewById(R.id.JZCTBtn);
        KSKSbtn = findViewById(R.id.KSKSBtn);
        CTRJbtn = findViewById(R.id.CTRJtn);


        setStatusBarColorToLOLLIPOP(MainActivity.this,R.color.colorApp);

        //跳转到快速开始页面
        KSKSbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, KSKSActivity.class);
                startActivity(intent);
            }
        });

        //跳转到错题日记页面
        CTRJbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CTRJActivity.class);
                startActivity(intent);
            }
        });

        //跳转到家长出题页面
        JZCTbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, JZCTActivity.class);
                startActivity(intent);
            }
        });


    }


}
