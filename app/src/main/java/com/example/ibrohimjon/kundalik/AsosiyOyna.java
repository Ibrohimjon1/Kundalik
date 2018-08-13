package com.example.ibrohimjon.kundalik;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

public class AsosiyOyna extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static SQLiteHelper sqLiteHelper;
    ImageButton btnKirim, btnChiqim, btnSettings, btnHisobot, btnQarzdorlik, btnSavdo;
    final int ABOUT = 3;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        btnKirim = (ImageButton) findViewById(R.id.btnKirim);
        btnChiqim = (ImageButton) findViewById(R.id.btnChiqim);
        btnHisobot = (ImageButton) findViewById(R.id.btnHisobot);
        btnSettings = (ImageButton) findViewById(R.id.btnSettings);
        btnSavdo = (ImageButton) findViewById(R.id.btnSavdo);
        btnQarzdorlik = (ImageButton) findViewById(R.id.btnQarzdorlik);

        sqLiteHelper = new SQLiteHelper(this, "Kundalik.sqlite", null, 1);

        sqLiteHelper.queryData("CREATE TABLE IF NOT EXISTS DOKON_NOMI (Id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR)");
        sqLiteHelper.queryData("CREATE TABLE IF NOT EXISTS SHOT_NOMI (Id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR)");
        sqLiteHelper.queryData("CREATE TABLE IF NOT EXISTS KIRIM_NOMI (Id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR)");
        sqLiteHelper.queryData("CREATE TABLE IF NOT EXISTS CHIQIM_NOMI (Id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR)");

        sqLiteHelper.queryData("CREATE TABLE IF NOT EXISTS TANISHLAR (Id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR)");
        sqLiteHelper.queryData("CREATE TABLE IF NOT EXISTS QARZDORLAR (Id INTEGER PRIMARY KEY AUTOINCREMENT, tanish_ismi VARCHAR, summa VARCHAR, sana DATE, haq_summa INTEGER, sana_solish VARCHAR, izox VARCHAR DEFAULT 'Izox yozilmagan')");
        sqLiteHelper.queryData("CREATE TABLE IF NOT EXISTS QARZLARIM (Id INTEGER PRIMARY KEY AUTOINCREMENT, tanish_ismi VARCHAR, summa VARCHAR, sana DATE, haq_summa INTEGER, sana_solish VARCHAR, izox VARCHAR DEFAULT 'Izox yozilmagan')");

        sqLiteHelper.queryData("CREATE TABLE IF NOT EXISTS SAVDO (Id INTEGER PRIMARY KEY AUTOINCREMENT, dokon_nomi VARCHAR, shot_nomi VARCHAR, summa VARCHAR, sana DATE, haq_summa INTEGER, sana_solish VARCHAR)");
        sqLiteHelper.queryData("CREATE TABLE IF NOT EXISTS DELETED_SAVDO (Id INTEGER PRIMARY KEY AUTOINCREMENT, dokon_nomi VARCHAR, shot_nomi VARCHAR, summa VARCHAR, sana DATE, haq_summa INTEGER, ochirilgan_sana DATE)");
        sqLiteHelper.queryData("CREATE TABLE IF NOT EXISTS KIRIM (Id INTEGER PRIMARY KEY AUTOINCREMENT, dokon_nomi VARCHAR, kirim_nomi VARCHAR, summa VARCHAR, sana DATE, haq_summa INTEGER, sana_solish VARCHAR)");
        sqLiteHelper.queryData("CREATE TABLE IF NOT EXISTS CHIQIM (Id INTEGER PRIMARY KEY AUTOINCREMENT, dokon_nomi VARCHAR, chiqim_nomi VARCHAR, summa VARCHAR, sana DATE, haq_summa INTEGER, sana_solish VARCHAR)");

        btnSavdo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AsosiyOyna.this, Savdo_sana_yangi.class);
                startActivity(intent);
            }
        });

        btnKirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AsosiyOyna.this, Kirim_sana.class);
                startActivity(intent);
            }
        });

        btnQarzdorlik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AsosiyOyna.this, Qarzdorlar_sana_yangi.class);
                startActivity(intent);
            }
        });

        btnChiqim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AsosiyOyna.this, Chiqim_sana.class);
                startActivity(intent);
            }
        });

        btnHisobot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AsosiyOyna.this, Hisobot_royh.class);
                startActivity(intent);
            }
        });

        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AsosiyOyna.this, Qarzlarim_sana.class);
                startActivity(intent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        assert drawer != null;
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        assert navigationView != null;
        navigationView.setNavigationItemSelectedListener(this);

    }

//    public boolean onCreateOptionsMenu(Menu menu) {
//
//        getMenuInflater().inflate(R.menu.asosiy_menu, menu);
//        return true;
//    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.about) {

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected Dialog onCreateDialog(int id) {
        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        switch (id) {
            case ABOUT:
                adb.setMessage("Ushbu dastur Ahmadjonov Ibrohimjon Ilhomjon o'g'li tomonidan tuzildi! \nTaklif va mulohazalar uchun \nTel:+998 93 789 94 91\n\t\t \t+998 93 699 57 75\n\nVersion 1.0.0.5\n\n2018-yil");
                adb.setTitle("Dastur haqida");
                adb.setNegativeButton("Ok", myClickListener);
                break;
        }
        return adb.create();
    }

    DialogInterface.OnClickListener myClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case Dialog.BUTTON_NEUTRAL:
                    break;
            }
        }
    };

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        assert drawer != null;
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.btn_set_dok_nomi) {
            Intent intent = new Intent(AsosiyOyna.this, Dok_nomi.class);
            startActivity(intent);
        } else if (id == R.id.btn_set_shot_nomi) {
            Intent intent = new Intent(AsosiyOyna.this, Shot_nomi.class);
            startActivity(intent);
        } else if (id == R.id.btn_set_kirim_nomi) {
            Intent intent = new Intent(AsosiyOyna.this, Kirim_nomi.class);
            startActivity(intent);
        } else if (id == R.id.btn_set_chiqim_nomi) {
            Intent intent = new Intent(AsosiyOyna.this, Chiqim_nomi.class);
            startActivity(intent);
        } else if (id == R.id.btn_set_tanishlar) {
            Intent intent = new Intent(AsosiyOyna.this, Tanish_ismi.class);
            startActivity(intent);
        } else if (id == R.id.btn_set_settings) {
//            Intent intent = new Intent(AsosiyOyna.this, Settings.class);
//            startActivity(intent);
        } else if (id == R.id.btn_set_about) {
            Intent intent = new Intent(AsosiyOyna.this, BoshOyna.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
