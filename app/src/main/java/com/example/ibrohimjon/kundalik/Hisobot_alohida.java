package com.example.ibrohimjon.kundalik;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Hisobot_alohida extends AppCompatActivity {

    ImageButton btn_hisob_yigma;
    TextView txt_bosh_sana, txt_tug_sana, txt_kirim_nomi, txt_summa, txt_malumot;
    int DatePicker = 1, DatePicker1 = 2;
    int year_x, month_x, day_x;
    Spinner spinner_hisob;
    ArrayList<String> list_dok;
    TableLayout tableLayout_hisob;
    TableRow tableRow;
    int umumiy_chiqim = 0, umumiy_kirim = 0, umumiy_soni = 0;
    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.hisobot_alohida_activity);

        btn_hisob_yigma = (ImageButton) findViewById(R.id.btn_hisob_alohida_yigma);
        txt_bosh_sana = (TextView) findViewById(R.id.txt_hisob_bosh_sana);
        txt_tug_sana = (TextView) findViewById(R.id.txt_hisob_tug_sana);
        spinner_hisob = (Spinner) findViewById(R.id.spin_hisob_alohida);
        tableLayout_hisob = (TableLayout) findViewById(R.id.table_layout_hisob);
        txt_malumot = (TextView) findViewById(R.id.txt_hisob_malumot);
        view = (View) findViewById(R.id.immmmm);
        tableLayout_hisob.setColumnStretchable(0, true);
        tableLayout_hisob.setColumnStretchable(1, true);

        tableLayout_hisob.setHorizontalScrollBarEnabled(true);
        tableLayout_hisob.setVerticalScrollBarEnabled(true);

        Calendar calendar1 = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        String strDate = format.format(calendar1.getTime());

        Cursor cursor = AsosiyOyna.sqLiteHelper.getData("SELECT name FROM DOKON_NOMI");
        list_dok = new ArrayList<>();
        list_dok.clear();
        while (cursor.moveToNext()) {
            String name = cursor.getString(0);
            list_dok.add(name);
        }

        ArrayAdapter<String> adapter_dok_nom = new ArrayAdapter<>(this, R.layout.spinner_items_hisobot, list_dok);
        adapter_dok_nom.setDropDownViewResource(R.layout.spinner_down_list_hisobot);

        spinner_hisob.setAdapter(adapter_dok_nom);
        spinner_hisob.setPrompt("Title");

        txt_bosh_sana.setText(strDate);
        txt_tug_sana.setText(strDate);
        final Calendar calendar = Calendar.getInstance();
        year_x = calendar.get(Calendar.YEAR);
        month_x = calendar.get(Calendar.MONTH);
        day_x = calendar.get(Calendar.DAY_OF_MONTH);

        txt_bosh_sana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DatePicker);
            }
        });

        txt_tug_sana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DatePicker1);
            }
        });

        btn_hisob_yigma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Yigma();
            }
        });
    }

    private void Yigma(){
        tableLayout_hisob.removeAllViews();
        umumiy_soni = 0;
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");

        try {
            Date date1 = format.parse(txt_bosh_sana.getText().toString().trim());
            Date date2 = format.parse(txt_tug_sana.getText().toString().trim());

            if (!date2.before(date1)) {

                Cursor cursor_kirim = AsosiyOyna.sqLiteHelper.getData("SELECT * FROM KIRIM_NOMI");
                String dokon = spinner_hisob.getSelectedItem().toString().trim();

                txt_malumot.setBackgroundColor(Color.BLACK);
                txt_malumot.setTextColor(Color.WHITE);
                txt_malumot.setVisibility(View.VISIBLE);
                txt_malumot.setText("'" + dokon + "'dagi '" + txt_bosh_sana.getText().toString() + "' dan '" + txt_tug_sana.getText().toString() + "' gacha bo'lgan Hisobot");

//                        Liniya();
                if (cursor_kirim.getCount() != 0) {
                    umumiy_kirim = Yangilash(cursor_kirim, dokon, "KIRIM", date1, date2);
                }

                Liniya();
                umumiy_soni = 0;

                Cursor cursor_chiqim = AsosiyOyna.sqLiteHelper.getData("SELECT * FROM CHIQIM_NOMI");

                if (cursor_chiqim.getCount() != 0) {
                    umumiy_chiqim = Yangilash(cursor_chiqim, dokon, "CHIQIM", date1, date2);
                }

                Liniya();
                Liniya();
                Umumiy_hisob("kirim", umumiy_kirim);
                Umumiy_hisob("chiqim", umumiy_chiqim);

                Liniya();
                Umumiy_hisob("foyda", umumiy_kirim - umumiy_chiqim);

                Liniya();
            } else {
                txt_malumot.setText("");
                txt_malumot.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), "Iltimos sanalarni to'g'ri kiriting!", Toast.LENGTH_SHORT).show();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == DatePicker) {
            return new DatePickerDialog(this, dpickerListener, year_x, month_x, day_x);
        } else if (id == DatePicker1) {
            return new DatePickerDialog(this, dpickerListener1, year_x, month_x, day_x);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener dpickerListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(android.widget.DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            String oy, kun;
            if (monthOfYear < 10) {
                oy = "0" + String.valueOf(monthOfYear + 1) + ".";
            } else {
                oy = String.valueOf(monthOfYear + 1) + ".";
            }
            if (dayOfMonth < 10) {
                kun = "0" + String.valueOf(dayOfMonth) + ".";
            } else {
                kun = String.valueOf(dayOfMonth) + ".";
            }
            String string = kun.toString() + oy.toString() + String.valueOf(year);
            txt_bosh_sana.setText(string);
        }
    };

    private DatePickerDialog.OnDateSetListener dpickerListener1 = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(android.widget.DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            String oy, kun;
            if (monthOfYear < 10) {
                oy = "0" + String.valueOf(monthOfYear + 1) + ".";
            } else {
                oy = String.valueOf(monthOfYear + 1) + ".";
            }
            if (dayOfMonth < 10) {
                kun = "0" + String.valueOf(dayOfMonth) + ".";
            } else {
                kun = String.valueOf(dayOfMonth) + ".";
            }
            String string = kun.toString() + oy.toString() + String.valueOf(year);

            txt_tug_sana.setText(string);
        }
    };

    public int Yangilash(Cursor cursor_kirim, String dokon, String nom, Date bosh_sanaa, Date tug_sanaa) throws ParseException {

        Date boshi, tugashi;
        int umumiy = 0;
        tableRow = new TableRow(Hisobot_alohida.this);
        txt_kirim_nomi = new TextView(Hisobot_alohida.this);
        txt_summa = new TextView(Hisobot_alohida.this);

        txt_kirim_nomi.setText(nom);
        txt_kirim_nomi.setTextSize(35);
        txt_kirim_nomi.setTextColor(Color.RED);
        txt_kirim_nomi.setGravity(Gravity.CENTER_HORIZONTAL);

        txt_summa.setText(dokon);
        txt_summa.setTextSize(35);
        txt_summa.setTextColor(Color.BLUE);
        txt_summa.setGravity(Gravity.CENTER_HORIZONTAL);

        tableRow.addView(txt_kirim_nomi);
        tableRow.addView(txt_summa);
        tableLayout_hisob.addView(tableRow);

        dokon = dokon.replace("'", "''");
        cursor_kirim.moveToFirst();

        int as = 0;
        String[] name = new String[cursor_kirim.getCount()];
        do {
            name[as] = cursor_kirim.getString(1);
            as++;
        } while (cursor_kirim.moveToNext());

        for (int i = 0; i < name.length; i++) {
            String kirim_nomi = name[i];

            String sql = "SELECT " + nom.toLowerCase() + "_nomi, haq_summa, sana FROM " + nom + " WHERE dokon_nomi LIKE '" + dokon.replace("'","''") + "' AND " + nom.toLowerCase() + "_nomi LIKE '" + kirim_nomi.replace("'","''") + "'";
            Cursor cursor = AsosiyOyna.sqLiteHelper.getData(sql);

            if (cursor.getCount() != -1 && cursor.getCount() != 0) {

                cursor.moveToFirst();
                String hisob_nomi = cursor.getString(0);
                int sum = 0;
                do {
                    SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
                    boshi = format.parse(cursor.getString(2));
                    if (boshi.compareTo(bosh_sanaa) > -1 && boshi.compareTo(tug_sanaa) <= 0) {
                        sum = sum + cursor.getInt(1);
                    }
                } while (cursor.moveToNext());

                umumiy += sum;

                String haq_sum = NumberTextWatcherForThousand.getDecimalFormattedString(String.valueOf(sum));

                tableRow = new TableRow(Hisobot_alohida.this);
                txt_kirim_nomi = new TextView(Hisobot_alohida.this);
                txt_summa = new TextView(Hisobot_alohida.this);

                umumiy_soni++;

                txt_kirim_nomi.setText(hisob_nomi);
                txt_kirim_nomi.setTextSize(30);
                txt_kirim_nomi.setTextColor(Color.WHITE);
                txt_kirim_nomi.setPadding(20, 10, 10, 10);

                txt_summa.setText(haq_sum);
                txt_summa.setPadding(10, 10, 20, 10);
                txt_summa.setGravity(Gravity.RIGHT);
                txt_summa.setTextColor(Color.RED);
                txt_summa.setTextSize(30);

                tableRow.addView(txt_kirim_nomi);
                tableRow.addView(txt_summa);

                tableLayout_hisob.addView(tableRow);

            }
        }
        if (umumiy_soni == 0) {

            Hech_nima(nom);
        }
        return umumiy;
    }

    public void Umumiy_hisob(String kirim, int umumiy) {
        tableRow = new TableRow(Hisobot_alohida.this);
        txt_kirim_nomi = new TextView(Hisobot_alohida.this);
        txt_summa = new TextView(Hisobot_alohida.this);

        txt_kirim_nomi.setText("Umum " + kirim);
        if (umumiy < 0) {
            umumiy = umumiy * (-1);
            String sss = "-";
            sss = sss + NumberTextWatcherForThousand.getDecimalFormattedString(String.valueOf(umumiy));
            txt_summa.setText(sss);

        }else {
            txt_summa.setText(NumberTextWatcherForThousand.getDecimalFormattedString(String.valueOf(umumiy)));
        }
        txt_kirim_nomi.setTextSize(30);
        txt_summa.setTextSize(30);
        txt_kirim_nomi.setGravity(Gravity.LEFT);
        txt_summa.setGravity(Gravity.RIGHT);
        txt_kirim_nomi.setTextColor(Color.WHITE);
        txt_summa.setTextColor(Color.RED);

        tableRow.addView(txt_kirim_nomi);
        tableRow.addView(txt_summa);
        tableLayout_hisob.addView(tableRow);
    }

    public void Liniya() {
        ImageView imageView = new ImageView(Hisobot_alohida.this);
        ImageView imageView2 = new ImageView(Hisobot_alohida.this);

        imageView.setBackgroundColor(Color.GRAY);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        tableLayout_hisob.addView(imageView, view.getWidth(), view.getHeight());
        imageView2.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        tableLayout_hisob.addView(imageView2, view.getWidth(), 40);
    }

    public void Hech_nima(String kirim) {
        tableRow = new TableRow(Hisobot_alohida.this);
        txt_kirim_nomi = new TextView(Hisobot_alohida.this);
        txt_summa = new TextView(Hisobot_alohida.this);

        txt_kirim_nomi.setText(kirim + " yo'q!");
        txt_kirim_nomi.setTextSize(30);
        txt_summa.setTextSize(30);
        txt_kirim_nomi.setGravity(Gravity.LEFT);
        txt_summa.setGravity(Gravity.RIGHT);
        txt_kirim_nomi.setTextColor(Color.WHITE);
        txt_summa.setTextColor(Color.RED);

        tableRow.addView(txt_summa);
        tableRow.addView(txt_kirim_nomi);
        tableLayout_hisob.addView(tableRow);
    }
}
