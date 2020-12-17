package com.example.mathtest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

public class MINEActivity extends BaseActivity{

    Toolbar toolbar ;
    Button MESSAGEbtn;
    Button ABOUTUSbtn;
    TextView mineCoins;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine);

        toolbar=findViewById(R.id.toolbar_mine);
        toolbar.setNavigationIcon(R.mipmap.ic_keyboard_arrow_left_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                finish();
            }
        });


        mineCoins = findViewById(R.id.minecoins);
        mineCoins.setText(GoldCoins.getInstance().GetCoins_String());
        MESSAGEbtn = findViewById(R.id.MESSAGEBtn);
        ABOUTUSbtn = findViewById(R.id.ABOUTUSBtn);

        //跳转到消息中心页面
        MESSAGEbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MINEActivity.this, MESSAGEActivity.class);
                startActivity(intent);
            }
        });

        //跳转到关于我们页面
        ABOUTUSbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MINEActivity.this, ABOUTUSActivity.class);
                startActivity(intent);
            }
        });

    }
}
