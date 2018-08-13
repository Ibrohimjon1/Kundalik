package com.example.ibrohimjon.kundalik;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Hisobot_umumiy extends AppCompatActivity {

    ImageButton btn_hisob_umumiy_yigma;
    TextView txt_umumiy_bosh_sana, txt_umumiy_tug_sana, txt_umumiy_kirim_nomi, txt_umumiy_summa, txt_umumiy_malumot;
    int DatePicker_umumiy = 1, DatePicker1_umumiy = 2;
    int year_x, month_x, day_x;
    TableLayout tableLayout_hisob_umumiy;
    TableRow tableRow_umumiy;
    int umumiy_soni_umumiy = 0;
    String foyda_umum = "";
    View view_umumiy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.hisobot_umumiy_activity);

        btn_hisob_umumiy_yigma = (ImageButton) findViewById(R.id.btn_hisob_umumiy_yigma);
        txt_umumiy_bosh_sana = (TextView) findViewById(R.id.txt_hisob_umumiy_bosh_sana);
        txt_umumiy_tug_sana = (TextView) findViewById(R.id.txt_hisob_umumiy_tug_sana);
        tableLayout_hisob_umumiy = (TableLayout) findViewById(R.id.table_layout_hisob_umumiy);
        txt_umumiy_malumot = (TextView) findViewById(R.id.txt_umumiy_malumot);
        view_umumiy = findViewById(R.id.immmm_umumiy);
        tableLayout_hisob_umumiy.setColumnStretchable(0, true);
        tableLayout_hisob_umumiy.setColumnStretchable(1, true);

        Calendar calendar1 = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        String strDate = format.format(calendar1.getTime());

        txt_umumiy_bosh_sana.setText(strDate);
        txt_umumiy_tug_sana.setText(strDate);
        final Calendar calendar = Calendar.getInstance();
        year_x = calendar.get(Calendar.YEAR);
        month_x = calendar.get(Calendar.MONTH);
        day_x = calendar.get(Calendar.DAY_OF_MONTH);

        txt_umumiy_bosh_sana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DatePicker_umumiy);
            }
        });

        txt_umumiy_tug_sana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DatePicker1_umumiy);
            }
        });

        btn_hisob_umumiy_yigma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tableLayout_hisob_umumiy.removeAllViews();
                umumiy_soni_umumiy = 0;
                SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");

                try {
                    Date date1 = format.parse(txt_umumiy_bosh_sana.getText().toString());
                    Date date2 = format.parse(txt_umumiy_tug_sana.getText().toString());

                    if (!date2.before(date1)) {

                        Cursor cursor_dokon = AsosiyOyna.sqLiteHelper.getData("SELECT * FROM DOKON_NOMI");

                        txt_umumiy_malumot.setBackgroundColor(Color.BLACK);
                        txt_umumiy_malumot.setTextColor(Color.WHITE);
                        txt_umumiy_malumot.setVisibility(View.VISIBLE);
                        txt_umumiy_malumot.setText(txt_umumiy_bosh_sana.getText().toString() + "' dan '" + txt_umumiy_tug_sana.getText().toString() + "' gacha bo'lgan umumiy Hisobot");

                        Liniya();
                        if (cursor_dokon.getCount() != 0 && cursor_dokon.getCount() != 0) {
                            foyda_umum = Yangilash(cursor_dokon, "Do'konlar", date1, date2);
                        }

                        Liniya();

                        Umumiy_hisob("foyda", foyda_umum);

                        Liniya();
                    } else {
                        txt_umumiy_malumot.setText("");
                        txt_umumiy_malumot.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), "Iltimos sanalarni to'g'ri kiriting!", Toast.LENGTH_SHORT).show();
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == DatePicker_umumiy) {
            return new DatePickerDialog(this, dpickerListener, year_x, month_x, day_x);
        } else if (id == DatePicker1_umumiy) {
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
            String string = kun + oy + String.valueOf(year);
            txt_umumiy_bosh_sana.setText(string);
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
            String string = kun + oy + String.valueOf(year);

            txt_umumiy_tug_sana.setText(string);
        }
    };

    public String Yangilash(Cursor cursor_dokon, String nom, Date bosh_sanaa, Date tug_sanaa) throws ParseException {

        Date boshi;
        int umumiy_foyda = 0;
        tableRow_umumiy = new TableRow(Hisobot_umumiy.this);
        txt_umumiy_kirim_nomi = new TextView(Hisobot_umumiy.this);

        txt_umumiy_kirim_nomi.setText(nom);
        txt_umumiy_kirim_nomi.setTextSize(35);
        txt_umumiy_kirim_nomi.setTextColor(Color.RED);
        txt_umumiy_kirim_nomi.setGravity(Gravity.CENTER_HORIZONTAL);

        tableRow_umumiy.addView(txt_umumiy_kirim_nomi);
        tableLayout_hisob_umumiy.addView(tableRow_umumiy);

        cursor_dokon.moveToFirst();

        int as = 0;
        String[] name = new String[cursor_dokon.getCount()];
        do {
            name[as] = cursor_dokon.getString(1);
            as++;
        } while (cursor_dokon.moveToNext());

        for (String aName : name) {
            String dokon_nomi = aName;

            int kirim_summa = 0, chiqim_summa = 0, dokon_umumiy_foyda;
            dokon_nomi = dokon_nomi.replace("'", "''");
            // barcha do'kondagi kirimlar olinadi
            String sql_kirim = "SELECT haq_summa, sana FROM KIRIM WHERE dokon_nomi LIKE '" + dokon_nomi.replace("'","''") + "'";
            Cursor cursor_kirim = AsosiyOyna.sqLiteHelper.getData(sql_kirim);
            if (cursor_kirim.getCount() != 0) {
                cursor_kirim.moveToFirst();

                do {
                    SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
                    boshi = format.parse(cursor_kirim.getString(1));
                    if (boshi.compareTo(bosh_sanaa) > -1 && boshi.compareTo(tug_sanaa) <= 0) {
                        kirim_summa = kirim_summa + cursor_kirim.getInt(0);
                    }
                } while (cursor_kirim.moveToNext());

            }

            // barcha do'kondagi chiqimlar olinadi
            String sql_chiqim = "SELECT haq_summa, sana FROM CHIQIM WHERE dokon_nomi LIKE '" + dokon_nomi.replace("'","''") + "'";
            Cursor cursor_chiqim = AsosiyOyna.sqLiteHelper.getData(sql_chiqim);
            if (cursor_chiqim.getCount() != 0 && cursor_chiqim.getCount() != 0) {
                cursor_chiqim.moveToFirst();
                do {
                    SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
                    boshi = format.parse(cursor_chiqim.getString(1));
                    if (boshi.compareTo(bosh_sanaa) > -1 && boshi.compareTo(tug_sanaa) <= 0) {
                        chiqim_summa = chiqim_summa + cursor_chiqim.getInt(0);
                    }
                } while (cursor_chiqim.moveToNext());
            }

            // do'kondagi umumiy foyda olinadi
            dokon_umumiy_foyda = kirim_summa - chiqim_summa;
            umumiy_foyda += dokon_umumiy_foyda;
            String haq_sum;
            if (dokon_umumiy_foyda < 0) {
                dokon_umumiy_foyda = dokon_umumiy_foyda * (-1);
                haq_sum = NumberTextWatcherForThousand.getDecimalFormattedString(String.valueOf(dokon_umumiy_foyda));
                haq_sum = "-" + haq_sum;
            } else {
                haq_sum = NumberTextWatcherForThousand.getDecimalFormattedString(String.valueOf(dokon_umumiy_foyda));
            }
            tableRow_umumiy = new TableRow(Hisobot_umumiy.this);
            txt_umumiy_kirim_nomi = new TextView(Hisobot_umumiy.this);
            txt_umumiy_summa = new TextView(Hisobot_umumiy.this);

            umumiy_soni_umumiy++;

            txt_umumiy_kirim_nomi.setText(dokon_nomi);
            txt_umumiy_kirim_nomi.setTextSize(30);
            txt_umumiy_kirim_nomi.setTextColor(Color.WHITE);
            txt_umumiy_kirim_nomi.setPadding(20, 10, 10, 10);

            txt_umumiy_summa.setText(haq_sum);
            txt_umumiy_summa.setPadding(10, 10, 20, 10);
            txt_umumiy_summa.setGravity(Gravity.RIGHT);
            txt_umumiy_summa.setTextColor(Color.RED);
            txt_umumiy_summa.setTextSize(30);

            tableRow_umumiy.addView(txt_umumiy_kirim_nomi);
            tableRow_umumiy.addView(txt_umumiy_summa);

            tableLayout_hisob_umumiy.addView(tableRow_umumiy);

        }

        if (umumiy_soni_umumiy == 0) {
            Hech_nima(nom);
        }
        String foyda;
        if (umumiy_foyda < 0){
            umumiy_foyda  = umumiy_foyda * (-1);
            foyda = NumberTextWatcherForThousand.getDecimalFormattedString(String.valueOf(umumiy_foyda));
            foyda = "-" + foyda;
        }else {
            foyda = NumberTextWatcherForThousand.getDecimalFormattedString(String.valueOf(umumiy_foyda));
        }
        return foyda;
    }

    public void Umumiy_hisob(String foyda, String umumiy) {
        tableRow_umumiy = new TableRow(Hisobot_umumiy.this);
        txt_umumiy_kirim_nomi = new TextView(Hisobot_umumiy.this);
        txt_umumiy_summa = new TextView(Hisobot_umumiy.this);

        txt_umumiy_kirim_nomi.setText("Umum " + foyda);
        txt_umumiy_summa.setText(umumiy);
        txt_umumiy_kirim_nomi.setTextSize(30);
        txt_umumiy_summa.setTextSize(30);
        txt_umumiy_kirim_nomi.setGravity(Gravity.LEFT);
        txt_umumiy_summa.setGravity(Gravity.RIGHT);
        txt_umumiy_kirim_nomi.setTextColor(Color.WHITE);
        txt_umumiy_summa.setTextColor(Color.RED);

        tableRow_umumiy.addView(txt_umumiy_kirim_nomi);
        tableRow_umumiy.addView(txt_umumiy_summa);
        tableLayout_hisob_umumiy.addView(tableRow_umumiy);
    }

    public void Liniya() {
        ImageView imageView = new ImageView(Hisobot_umumiy.this);
        ImageView imageView2 = new ImageView(Hisobot_umumiy.this);

        imageView.setBackgroundColor(Color.GRAY);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        tableLayout_hisob_umumiy.addView(imageView, view_umumiy.getWidth(), view_umumiy.getHeight());
        imageView2.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        tableLayout_hisob_umumiy.addView(imageView2, view_umumiy.getWidth(), 40);
    }

    public void Hech_nima(String kirim) {
        tableRow_umumiy = new TableRow(Hisobot_umumiy.this);
        txt_umumiy_kirim_nomi = new TextView(Hisobot_umumiy.this);
        txt_umumiy_summa = new TextView(Hisobot_umumiy.this);

        txt_umumiy_kirim_nomi.setText(kirim + " yo'q!");
        txt_umumiy_kirim_nomi.setTextSize(30);
        txt_umumiy_summa.setTextSize(30);
        txt_umumiy_kirim_nomi.setGravity(Gravity.LEFT);
        txt_umumiy_summa.setGravity(Gravity.RIGHT);
        txt_umumiy_kirim_nomi.setTextColor(Color.WHITE);
        txt_umumiy_summa.setTextColor(Color.RED);

        tableRow_umumiy.addView(txt_umumiy_summa);
        tableRow_umumiy.addView(txt_umumiy_kirim_nomi);
        tableLayout_hisob_umumiy.addView(tableRow_umumiy);
    }
}
