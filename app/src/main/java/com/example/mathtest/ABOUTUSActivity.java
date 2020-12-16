package com.example.mathtest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

public class ABOUTUSActivity extends BaseActivity {

    Toolbar toolbar;
    TextView mineCoins;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutus);

        mineCoins=findViewById(R.id.minecoins);
        mineCoins.setText(GoldCoins.getInstance().GetCoins_String());
        toolbar=findViewById(R.id.toolbar_aboutus);
        toolbar.setNavigationIcon(R.mipmap.ic_keyboard_arrow_left_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                finish();
            }
        });

    }
}
