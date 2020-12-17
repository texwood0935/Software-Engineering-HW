package com.example.mathtest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.widget.Toolbar;

public class KSKSActivity extends BaseActivity{
    Toolbar toolbar;
    Button mButton1;
    Button mButton2;
    Button mButton3;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ksks);
        toolbar = findViewById(R.id.toolbar_select);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.ic_keyboard_arrow_left_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                finish();
            }
        });
        //初始化实例
        mButton1 = findViewById(R.id.level_one);
        mButton2 = findViewById(R.id.level_two);
        mButton3 = findViewById(R.id.level_three);
        //连接所有的按钮
        mButton1.setOnClickListener(new ButtonListener());
        mButton2.setOnClickListener(new ButtonListener());
        mButton3.setOnClickListener(new ButtonListener());
    }
    //统一处理三个按钮的点击事件
    private class ButtonListener implements  View.OnClickListener{
        public void onClick(View view){
            switch (view.getId()){
                case R.id.level_one:
//                    InputNumberOfQuestions();
                    Intent intent1 = new Intent(KSKSActivity.this , DTActivity.class);
                    intent1.putExtra("level",1);
//                    intent1.putExtra("number",NumberOfQuestions);
                    startActivity(intent1);
                    break;
                case R.id.level_two:
                    Intent intent2 = new Intent(KSKSActivity.this , DTActivity.class);
                    intent2.putExtra("level",2);
                    startActivity(intent2);
                    break;
                case R.id.level_three:
                    Intent intent3 = new Intent(KSKSActivity.this , DTActivity.class);
                    intent3.putExtra("level",3);
                    startActivity(intent3);
                    break;
                default:
                    break;
            }
        }
    }
}
