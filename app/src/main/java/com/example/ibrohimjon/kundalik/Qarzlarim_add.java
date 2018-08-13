package com.example.ibrohimjon.kundalik;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Pattern;

public class Qarzlarim_add extends AppCompatActivity {

    AutoCompleteTextView auto_qarzdor, dynamic_auto_qarz;
    ImageView btn_add, liniya;
    ArrayList<String> list;
    EditText edt_summa, dynamic_edittext;
    GridLayout grid_layout;
    int spinner_soni = 0;
    int index_views = 3;
    int viewlar_idlar = 0;
    ArrayAdapter<String> adapter;
    //    TextView dynamic_txt_tanish;
//    LinearLayout layout_tanish, layout_summa;
    RelativeLayout relativeLayout;
    //    List<EditText> all_Edittext_summa = new ArrayList<>();
//    List<AutoCompleteTextView> all_auto_qarz = new ArrayList<>();
//    List<ImageView> all_image_view = new ArrayList<>();
//    List<LinearLayout> all_layout_summa = new ArrayList<>();
//    List<LinearLayout> all_layout_tanish = new ArrayList<>();
//    List<TextView> all_text_view = new ArrayList<>();
    ArrayList<AutoCompleteTextView> all_auto_haqdor = new ArrayList<>();
    ArrayList<EditText> all_summa = new ArrayList<>();
    ArrayList<EditText> all_izox = new ArrayList<>();
    EditText edt_izox;
    TextView txt_summa;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qarzdorlar_add_activity);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        btn_add = (ImageView) findViewById(R.id.btn_qarz_add_p);
        edt_summa = (EditText) findViewById(R.id.edt_qarzdor_summa);
        edt_izox = (EditText) findViewById(R.id.edt_qarzdor_izox);
        grid_layout = (GridLayout) findViewById(R.id.grid_layout_qarz);
        relativeLayout = (RelativeLayout) findViewById(R.id.relative1);
        txt_summa = (TextView) findViewById(R.id.txt_add_summa);
        auto_qarzdor = (AutoCompleteTextView) findViewById(R.id.auto_qarzdor);


        Cursor cursor = AsosiyOyna.sqLiteHelper.getData("SELECT name FROM TANISHLAR");
        list = new ArrayList<>();
        list.clear();
        while (cursor.moveToNext()) {
            String name = cursor.getString(0);
            list.add(name);
        }
        adapter = new ArrayAdapter<>(this, R.layout.spinner_items, list);
//        adapter.setDropDownViewResource(R.layout.spinner_down_list);
        adapter.setDropDownViewResource(R.layout.spinner_down_list_auto);
        auto_qarzdor.setThreshold(1);
        auto_qarzdor.setAdapter(adapter);

        auto_qarzdor.setHint("Haqdor");

        txt_summa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edt_summa.getText().toString().equals("") && !edt_summa.getText().toString().endsWith("$")){
                    edt_summa.setText(String.format("%s$", edt_summa.getText().toString()));
                }
            }
        });

        edt_summa.addTextChangedListener(new NumberTextWatcherForThousand(edt_summa, 2, null));

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Yangi_qoshish();
            }
        });
    }

    private void Yangi_qoshish() {
        View row;
        LayoutInflater inflater = (LayoutInflater) Qarzlarim_add.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        row = inflater.inflate(R.layout.qarzlarim_items, null);

        AutoCompleteTextView textView = (AutoCompleteTextView) row.findViewById(R.id.avto_qarzim_item_qarzdor);
        textView.setId(viewlar_idlar);
        textView.setHint("Haqdor");
        textView.setAdapter(adapter);
        textView.setThreshold(1);
        all_auto_haqdor.add(textView);
        viewlar_idlar++;

        final EditText editText_summa = (EditText) row.findViewById(R.id.edt_qarzim_item_summa);
        editText_summa.setId(viewlar_idlar);
//        editText_summa.addTextChangedListener(new NumberTextWatcherForThousand(editText_summa, 2, null));
        all_summa.add(editText_summa);
        viewlar_idlar++;

        TextView txt_sum = (TextView) row.findViewById(R.id.txt_add_item_summa);
        txt_sum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editText_summa.getText().toString().equals("") && !editText_summa.getText().toString().endsWith("$")){
                    editText_summa.setText(String.format("%s$", editText_summa.getText().toString()));
                }
            }
        });
        EditText edit_text_izox = (EditText) row.findViewById(R.id.edt_qarzim_item_izox);
        edit_text_izox.setId(viewlar_idlar);
        all_izox.add(edit_text_izox);
        viewlar_idlar++;

        grid_layout.addView(row, index_views);
        index_views++;
        textView.requestFocus();

    }

    public void Qoshish() {

//        //qora chiziq tortish
//        liniya = new ImageView(Qarzlarim_add.this);
//        liniya.setBackgroundColor(Color.BLACK);
//        all_image_view.add(liniya);
//        liniya.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//        grid_layout.addView(liniya, relativeLayout.getWidth(), 25);
//
//        layout_tanish = new LinearLayout(Qarzlarim_add.this);
//        layout_tanish.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//        layout_tanish.setOrientation(LinearLayout.HORIZONTAL);
//
//        //tanish degan text qoshish
//        dynamic_txt_tanish = new TextView(Qarzlarim_add.this);
//        all_text_view.add(dynamic_txt_tanish);
//        dynamic_txt_tanish.setId(spinner_soni);
//        spinner_soni++;
//        dynamic_txt_tanish.setText("Tanish  ");
//        dynamic_txt_tanish.setTextSize(30);
//        dynamic_txt_tanish.setTextColor(Color.RED);
//        dynamic_txt_tanish.setGravity(Gravity.CENTER_VERTICAL);
//        dynamic_txt_tanish.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//        layout_tanish.addView(dynamic_txt_tanish);
//
//
//        //autoCompleteTextView qoshish
//        dynamic_auto_qarz = new AutoCompleteTextView(Qarzlarim_add.this);
//        all_auto_qarz.add(dynamic_auto_qarz);
//        dynamic_auto_qarz.setId(spinner_soni);
//        spinner_soni++;
//        dynamic_auto_qarz.setBackgroundColor(Color.WHITE);
//        dynamic_auto_qarz.setThreshold(1);
//        dynamic_auto_qarz.setHint("Haqdor");
//        dynamic_auto_qarz.setTextColor(Color.BLACK);
//        dynamic_auto_qarz.setAdapter(adapter);
//        dynamic_auto_qarz.setPadding(10,10,10,10);
//
//        dynamic_auto_qarz.setTextSize(30);
//        dynamic_auto_qarz.requestFocus();
//        dynamic_auto_qarz.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//        layout_tanish.addView(dynamic_auto_qarz);
//
//        all_layout_tanish.add(layout_tanish);
//        grid_layout.addView(layout_tanish);
//
//
//        layout_summa = new LinearLayout(Qarzlarim_add.this);
//        layout_summa.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//        layout_summa.setOrientation(LinearLayout.HORIZONTAL);
//
//        //summa degan text qoshish
//        dynamic_txt_tanish = new TextView(Qarzlarim_add.this);
//        all_text_view.add(dynamic_txt_tanish);
//        dynamic_txt_tanish.setId(spinner_soni);
//        spinner_soni++;
//        dynamic_txt_tanish.setText("Summa");
//        dynamic_txt_tanish.setTextSize(30);
//        dynamic_txt_tanish.setTextColor(Color.RED);
//        dynamic_txt_tanish.setGravity(Gravity.CENTER_VERTICAL);
//        dynamic_txt_tanish.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//        layout_summa.addView(dynamic_txt_tanish);
//
//        //edit_summa yaratish
//        dynamic_edittext = new EditText(Qarzlarim_add.this);
//        all_Edittext_summa.add(dynamic_edittext);
//        dynamic_edittext.setId(spinner_soni);
//        spinner_soni++;
//        dynamic_edittext.setHint("Summa");
//        dynamic_edittext.addTextChangedListener(new NumberTextWatcherForThousand(dynamic_edittext, 2, null));
//        dynamic_edittext.setGravity(edt_summa.getGravity());
//        dynamic_edittext.setTextSize(30);
//        dynamic_edittext.setTextColor(Color.WHITE);
//        dynamic_edittext.setPadding(20, 0, 0, 20);
//        dynamic_edittext.setInputType(edt_summa.getInputType());
//        dynamic_edittext.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//        layout_summa.addView(dynamic_edittext);
//
//        all_layout_summa.add(layout_summa);
//        grid_layout.addView(layout_summa);

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // tepada menu tugmalari qo'shish
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_add) {
//            // qoshish tugmasi bosilsa
//            try {
//                // Hozirgi sanani olish
//                Calendar calendar = Calendar.getInstance();
//                SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
//                String strDate = format.format(calendar.getTime());
//
//                SimpleDateFormat format1 = new SimpleDateFormat("yyyy.MM.dd");
//                String strDate_saralash = format1.format(calendar.getTime());
//
//                String sql1 = "INSERT INTO TANISHLAR VALUES (NULL, ?)";
//
//                String tanish_ismi = auto_qarzdor.getText().toString().trim();
//                String summa = edt_summa.getText().toString();
//                Cursor cursor = AsosiyOyna.sqLiteHelper.getData("SELECT * FROM TANISHLAR WHERE name LIKE '" + tanish_ismi.replace("'","''") + "'");
//                if (cursor.getCount() == 0) {
//                    AsosiyOyna.sqLiteHelper.insertData_Shot_nomi(tanish_ismi, sql1);
//                }
//                // Bazaga malumot yozish
//                String sql = "INSERT INTO QARZLARIM VALUES (NULL, ?, ?, ?, ?, ?, ?)";
//                if (!summa.equals("") && !tanish_ismi.equals("")) {
//                    int haq_summa = Integer.parseInt(summa.replace(",", ""));
////                    AsosiyOyna.sqLiteHelper.insertData_qarzdor(tanish_ismi, summa, strDate, haq_summa, sql, strDate_saralash);
//                }
//                if (all_Edittext_summa.size() != 0 && all_auto_qarz.size() != 0) {
//
//                    for (int i = 0; i < all_Edittext_summa.size(); i++) {
//
//                        //qoshilgan barcha viewlardan ma'lumotlarni olish
//                        String tanish = all_auto_qarz.get(i).getText().toString();
//                        String summaa = all_Edittext_summa.get(i).getText().toString();
//
//                        if (!summaa.equals("") && !tanish.equals("")) {
//                            Cursor cursor2 = AsosiyOyna.sqLiteHelper.getData("SELECT * FROM TANISHLAR WHERE name LIKE '" + tanish.replace("'","''") + "'");
//                            if (cursor2.getCount() == 0) {
//                                AsosiyOyna.sqLiteHelper.insertData_Shot_nomi(tanish, sql1);
//                            }
//                            int haq_summa1 = Integer.parseInt(summaa.replace(",", ""));
////                            AsosiyOyna.sqLiteHelper.insertData_qarzdor(tanish, summaa, strDate, haq_summa1, sql, strDate_saralash);
//                        }
//                    }
//
//                    //grid viewdan layoutlarni o'chirish
//                    for (LinearLayout linearLayout : all_layout_summa) {
//                        grid_layout.removeView(linearLayout);
//                    }
//                    for (LinearLayout linearLayout : all_layout_tanish) {
//                        grid_layout.removeView(linearLayout);
//                    }
//                    for (ImageView imageView : all_image_view) {
//                        grid_layout.removeView(imageView);
//                    }
//
//                    //to'plamlardan viewlarni o'chirish
//                    all_image_view.clear();
//                    all_Edittext_summa.clear();
//                    all_layout_summa.clear();
//                    all_layout_tanish.clear();
//                    all_auto_qarz.clear();
//                    all_text_view.clear();
//
//                }
//                edt_summa.setText("");
//                auto_qarzdor.setText("");
//                auto_qarzdor.requestFocus();
//                Toast.makeText(getApplicationContext(), R.string.muvaf_saqlash, Toast.LENGTH_SHORT).show();
//            } catch (Exception error) {
//                error.printStackTrace();
//                Toast.makeText(getApplicationContext(), "Saqlanishda xatolik bo'ldi!!!", Toast.LENGTH_SHORT).show();
//            }
            Yangi_saqlash();
        } else if (id == R.id.action_list) {
            //list tugmasi bosilsa
            Intent intent = new Intent(Qarzlarim_add.this, Qarzlarim_sana.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void Yangi_saqlash() {
        try {
            // Hozirgi sanani olish
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
            String strDate = format.format(calendar.getTime());

            SimpleDateFormat format1 = new SimpleDateFormat("yyyy.MM.dd");
            String strDate_saralash = format1.format(calendar.getTime());

            String sql1 = "INSERT INTO TANISHLAR VALUES (NULL, ?)";

            String tanish_ismi = auto_qarzdor.getText().toString().trim();
            String summa = edt_summa.getText().toString().trim();
            String izox = edt_izox.getText().toString().trim();
            Cursor cursor = AsosiyOyna.sqLiteHelper.getData("SELECT * FROM TANISHLAR WHERE name LIKE '" + tanish_ismi.replace("'", "''") + "'");
            if (cursor.getCount() == 0) {
                AsosiyOyna.sqLiteHelper.insertData_Shot_nomi(tanish_ismi, sql1);
            }

            String sql = "INSERT INTO QARZLARIM (tanish_ismi, summa, sana, haq_summa, sana_solish, izox) VALUES (?, ?, ?, ?, ?, ?)";
            if (!summa.equals("") && !tanish_ismi.equals("")) {
                int haq_summa = Integer.parseInt(summa.replaceAll("[^0-9]", ""));
                if (summa.endsWith("$")){
                    summa = NumberTextWatcherForThousand.getDecimalFormattedString(summa.replaceAll("[^0-9]", "")) + "$";
                } else {
                    summa = NumberTextWatcherForThousand.getDecimalFormattedString(summa.replaceAll("[^0-9]", ""));
                }
                AsosiyOyna.sqLiteHelper.insertData_qarzdor(tanish_ismi, summa, strDate, haq_summa, strDate_saralash, izox, sql);
            }

            if (all_izox.size() != 0 && all_summa.size() != 0 && index_views > 3) {
                for (int i = 0; i < all_summa.size(); i++) {
                    String tanish = all_auto_haqdor.get(i).getText().toString();
                    String summaa = all_summa.get(i).getText().toString();
                    String izoxa = all_izox.get(i).getText().toString();

                    if (!summaa.equals("") && !tanish.equals("")) {
                        Cursor cursor2 = AsosiyOyna.sqLiteHelper.getData("SELECT * FROM TANISHLAR WHERE name LIKE '" + tanish.replace("'", "''") + "'");
                        if (cursor2.getCount() == 0) {
                            AsosiyOyna.sqLiteHelper.insertData_Shot_nomi(tanish, sql1);
                        }
                        int haq_summa1 = Integer.parseInt(summaa.replaceAll("[^0-9]", ""));
                        if (summaa.endsWith("$")){
                            summaa = NumberTextWatcherForThousand.getDecimalFormattedString(summaa.replaceAll("[^0-9]", "")) + "$";
                        } else {
                            summaa = NumberTextWatcherForThousand.getDecimalFormattedString(summaa.replaceAll("[^0-9]", ""));
                        }
                        AsosiyOyna.sqLiteHelper.insertData_qarzdor(tanish, summaa, strDate, haq_summa1, strDate_saralash, izoxa, sql);
                    }
                }

            }
            Toast.makeText(getApplicationContext(), R.string.muvaf_saqlash, Toast.LENGTH_SHORT).show();
            finish();
            String sql5 = "SELECT DISTINCT tanish_ismi FROM QARZLARIM ORDER BY tanish_ismi ASC";
            Qarzlarim_sana.Get_data(sql5);
        } catch (SQLException e) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(false);
            builder.setTitle("Bazada xatolik");
            builder.setMessage(e.getMessage());
            builder.show();
        }
    }
}
