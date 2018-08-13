package com.example.ibrohimjon.kundalik;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class Hisobot_royh extends AppCompatActivity {

    Button btn_hisob_alohida, btn_hisob_umumiy;
    ImageView ima_alohida, ima_umum;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hisobot_list_activity);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        btn_hisob_alohida = (Button) findViewById(R.id.btn_hisob_alohida);
        btn_hisob_umumiy = (Button) findViewById(R.id.btn_hisob_umumiy);
        ima_alohida = (ImageView) findViewById(R.id.image_hisob_alohida);
        ima_umum = (ImageView) findViewById(R.id.image_hisob_hamma);

        ima_umum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Hisobot_royh.this, Hisobot_umumiy.class);
                startActivity(intent1);
            }
        });

        ima_alohida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Hisobot_royh.this, Hisobot_alohida.class);
                startActivity(intent);
            }
        });

        btn_hisob_alohida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Hisobot_royh.this, Hisobot_alohida.class);
                startActivity(intent);
            }
        });

        btn_hisob_umumiy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Hisobot_royh.this, Hisobot_umumiy.class);
                startActivity(intent1);
            }
        });
    }
}
