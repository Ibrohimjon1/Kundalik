package com.example.ibrohimjon.kundalik;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

public class Settings extends AppCompatActivity {

    Button btn_set_dok_Nom, btn_set_shot_nomi, btn_set_kirim_nomi,
            btn_set_chiqim_nomi, btn_set_tanishlar;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

//        btn_set_dok_Nom = (Button) findViewById(R.id.btn_set_dok_nomi);
//        btn_set_shot_nomi = (Button) findViewById(R.id.btn_set_shot_nomi);
//        btn_set_kirim_nomi = (Button) findViewById(R.id.btn_set_kirim_nomi);
//        btn_set_chiqim_nomi = (Button) findViewById(R.id.btn_set_chiqim_nomi);
//        btn_set_tanishlar = (Button) findViewById(R.id.btn_set_tanishlar);
//
//
//        btn_set_dok_Nom.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Settings.this, Dok_nomi.class);
//                startActivity(intent);
//            }
//        });
//
//        btn_set_shot_nomi.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent1 = new Intent(Settings.this, Shot_nomi.class);
//                startActivity(intent1);
//            }
//        });
//
//        btn_set_kirim_nomi.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Settings.this, Kirim_nomi.class);
//                startActivity(intent);
//            }
//        });
//
//        btn_set_chiqim_nomi.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Settings.this, Chiqim_nomi.class);
//                startActivity(intent);
//            }
//        });
//
//        btn_set_tanishlar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Settings.this, Tanish_ismi.class);
//                startActivity(intent);
//            }
//        });
    }
}
