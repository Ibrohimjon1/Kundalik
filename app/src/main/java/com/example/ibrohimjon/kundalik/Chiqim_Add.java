package com.example.ibrohimjon.kundalik;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Chiqim_Add extends AppCompatActivity {

    ArrayList<String> list, list1;
    ImageView btn_add, liniya, dyn_iks;
    EditText edt_kirim_Summa, dynamic_edittext;
    ImageView imgKirim_add_iks;
    Spinner spinner;
    GridLayout grid_layout;
    LinearLayout layout, layout_edit;
    Spinner spinner_dynamic_dok_nom;
    AutoCompleteTextView spinner_dynamic_kirim_nom;
    int spinner_soni = 0;
    List<Spinner> all_Spinner_dok_nom = new ArrayList<>();
    List<AutoCompleteTextView> all_Spinner_kirim_nom = new ArrayList<>();
    List<AutoCompleteTextView> all_Spinner_kirim_nom_dyn = new ArrayList<>();
    List<EditText> all_Edittext_summa = new ArrayList<>();
    List<EditText> all_Edittext_summa_dyn = new ArrayList<>();
    List<LinearLayout> all_linear_layout = new ArrayList<>();
    List<TextView> all_Textview_dok_nom_dyn = new ArrayList<>();
    List<LinearLayout> all_linear_layout_edit = new ArrayList<>();
    List<ImageView> all_image_view = new ArrayList<>();
    List<ImageView> all_image_iks = new ArrayList<>();
    RelativeLayout relativeLayout;
    int[] images = {R.drawable.iks};
    List<View> all_views_dyn = new ArrayList<>();
    ArrayAdapter adapter;
    AutoCompleteTextView auto_chiqim;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chiqim_add_activity);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        imgKirim_add_iks = (ImageView) findViewById(R.id.imgChiqim_add_iks);
        edt_kirim_Summa = (EditText) findViewById(R.id.edt_chiqim_summa);
        grid_layout = (GridLayout) findViewById(R.id.grid_chiqim_add);
        btn_add = (ImageView) findViewById(R.id.btn_chiqim_add_past);
        relativeLayout = (RelativeLayout) findViewById(R.id.rel_lay);
        auto_chiqim = (AutoCompleteTextView) findViewById(R.id.auto_chiqim);

        try {
            Cursor cursor = AsosiyOyna.sqLiteHelper.getData("SELECT name FROM DOKON_NOMI");
            list = new ArrayList<>();
            list1 = new ArrayList<>();
            list.clear();
            while (cursor.moveToNext()) {
                String name = cursor.getString(0);
                list.add(name);
            }

            ArrayAdapter<String> adapter_dok_nom = new ArrayAdapter<>(this, R.layout.spinner_items, list);
            spinner = (Spinner) findViewById(R.id.spin_chiqim_add_dok);
            assert spinner != null;
            adapter_dok_nom.setDropDownViewResource(R.layout.spinner_down_list);
            spinner.setAdapter(adapter_dok_nom);
        } catch (Exception er) {
            er.printStackTrace();
        }
        //AutoTextga shot nomlarini qoshish
        try {
            Cursor cursor1 = AsosiyOyna.sqLiteHelper.getData("SELECT name FROM CHIQIM_NOMI");
            list1.clear();
            while (cursor1.moveToNext()) {
                String name = cursor1.getString(0);
                list1.add(name);
            }

            adapter = new ArrayAdapter<>(this, R.layout.spinner_items, list1);
            adapter.setDropDownViewResource(R.layout.spinner_down_list_auto);
            auto_chiqim.setThreshold(1);
            auto_chiqim.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            edt_kirim_Summa.addTextChangedListener(new NumberTextWatcherForThousand(edt_kirim_Summa, 1, imgKirim_add_iks));
        } catch (Exception e) {
            e.printStackTrace();
        }
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Spinn_qosh();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        imgKirim_add_iks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (all_Spinner_dok_nom.size() > 0) {
                        qoshimcha_add(all_Spinner_dok_nom.get(all_Spinner_dok_nom.size() - 1).getSelectedItem().toString());
                    } else {
                        qoshimcha_add(spinner.getSelectedItem().toString());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void qoshimcha_add(String dok_nomi) {
        View savdo_dyn = new View(Chiqim_Add.this);
        LayoutInflater inflater = (LayoutInflater) Chiqim_Add.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        savdo_dyn = inflater.inflate(R.layout.dynamic_chiqim, null);

        AutoCompleteTextView auto_View = (AutoCompleteTextView) savdo_dyn.findViewById(R.id.auto_chiqim_dyn);
        auto_View.setThreshold(1);
        auto_View.requestFocus();
        auto_View.setAdapter(adapter);
        all_Spinner_kirim_nom_dyn.add(auto_View);

        EditText editText = (EditText) savdo_dyn.findViewById(R.id.edt_chiqim_dyn);
        editText.addTextChangedListener(new NumberTextWatcherForThousand(editText, 2, dyn_iks));

        all_Edittext_summa_dyn.add(editText);

        TextView txt_dok_nom = (TextView) savdo_dyn.findViewById(R.id.Chiqim_dyn_dok_nom);
        txt_dok_nom.setText(dok_nomi);
        all_Textview_dok_nom_dyn.add(txt_dok_nom);

        all_views_dyn.add(savdo_dyn);

        grid_layout.addView(savdo_dyn);
    }

    public void Spinn_qosh() {

        //qizil chiziq tortish
        liniya = new ImageView(Chiqim_Add.this);
        liniya.setBackgroundColor(Color.RED);
        all_image_view.add(liniya);
        liniya.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        grid_layout.addView(liniya, relativeLayout.getWidth(), 25);

        //spinnerlar uchun layout
        layout = new LinearLayout(Chiqim_Add.this);
        layout.setOrientation(LinearLayout.HORIZONTAL);

        //dokon nomi uchun spinner
        spinner_dynamic_dok_nom = new Spinner(Chiqim_Add.this);
        all_Spinner_dok_nom.add(spinner_dynamic_dok_nom);
        spinner_dynamic_dok_nom.setId(spinner_soni);
        spinner_soni++;
        spinner_dynamic_dok_nom.setAdapter(spinner.getAdapter());
        spinner_dynamic_dok_nom.setBottom(10);
        spinner_dynamic_dok_nom.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
        layout.addView(spinner_dynamic_dok_nom, spinner.getWidth(), spinner.getHeight());

        //shot nomi uchun spinner
        spinner_dynamic_kirim_nom = new AutoCompleteTextView(Chiqim_Add.this);
        all_Spinner_kirim_nom.add(spinner_dynamic_kirim_nom);
        spinner_dynamic_kirim_nom.setId(spinner_soni);
        spinner_soni++;
        spinner_dynamic_kirim_nom.setHint("Chiqim");
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0,10,0,15);
        spinner_dynamic_kirim_nom.setTextSize(30);
        spinner_dynamic_kirim_nom.setBackgroundColor(Color.WHITE);
        spinner_dynamic_kirim_nom.setTextColor(Color.BLACK);
        spinner_dynamic_kirim_nom.requestFocus();
        spinner_dynamic_kirim_nom.setBackgroundResource(R.drawable.round_layout);
        spinner_dynamic_kirim_nom.setPadding(10, 30, 10, 10);
        spinner_dynamic_kirim_nom.setThreshold(1);
        spinner_dynamic_kirim_nom.setAdapter(adapter);
//        spinner_dynamic_kirim_nom.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        spinner_dynamic_kirim_nom.setLayoutParams(params);
        layout.addView(spinner_dynamic_kirim_nom, auto_chiqim.getWidth(), auto_chiqim.getHeight());

        //edit_summa uchun layout
        layout_edit = new LinearLayout(Chiqim_Add.this);
        layout_edit.setOrientation(LinearLayout.HORIZONTAL);

        //edit_summa oldida o'chirish rasmi
        dyn_iks = new ImageView(Chiqim_Add.this);
        all_image_iks.add(dyn_iks);
        dyn_iks.setId(spinner_soni);
        spinner_soni++;
        dyn_iks.setImageResource(images[0]);
        dyn_iks.setVisibility(View.GONE);
        dyn_iks.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        //edit_summa yaratish
        dynamic_edittext = new EditText(Chiqim_Add.this);
        all_Edittext_summa.add(dynamic_edittext);
        dynamic_edittext.setId(spinner_soni);
        spinner_soni++;
        dynamic_edittext.setHint("Summa");
        dynamic_edittext.addTextChangedListener(new NumberTextWatcherForThousand(dynamic_edittext, 2, dyn_iks));
        dynamic_edittext.setGravity(edt_kirim_Summa.getGravity());
        dynamic_edittext.setTextSize(30);
        dynamic_edittext.setPadding(10,10,10,10);
        dynamic_edittext.setBackgroundColor(Color.WHITE);
        dynamic_edittext.setBackgroundResource(R.drawable.round_layout);
        dynamic_edittext.setTextColor(Color.BLACK);
        dynamic_edittext.setInputType(edt_kirim_Summa.getInputType());
        dynamic_edittext.setLayoutParams(params);
//        dynamic_edittext.requestFocus();

        //edit_summa layoutga edittext bilan rasmni qoshish
//        if (edt_kirim_Summa.getText().toString().equals("")) {
        dynamic_edittext.setLayoutParams(edt_kirim_Summa.getLayoutParams());
            layout_edit.addView(dynamic_edittext, edt_kirim_Summa.getWidth(), edt_kirim_Summa.getHeight());
//        } else {
//            int width = (int) (this.getResources().getDisplayMetrics().widthPixels);
//            layout_edit.addView(dynamic_edittext, width, edt_kirim_Summa.getHeight());
//        }
        layout_edit.addView(dyn_iks);

        //hamma layoutlar toplamiga layoutlarni qoshish
        all_linear_layout.add(layout);
        all_linear_layout_edit.add(layout_edit);

        //gridviewga layoutlarni qoshish
        grid_layout.addView(layout);
        grid_layout.addView(layout_edit);

        //x rasmi bosilsa edittextni textini o'chirish
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // tepada menu tugmalari qo'shish
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    // menu tugmalari bosilganda nima ish qilishi
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_add) {
            // qoshish tugmasi bosilsa
            String doknom = spinner.getSelectedItem().toString().trim();
            String chiqim_nom = auto_chiqim.getText().toString().trim();
            String summa = edt_kirim_Summa.getText().toString().trim();
            int haq_summa = 0;
            if (!summa.equals("")) {
                haq_summa = Integer.parseInt(summa.replaceAll("[^0-9]", ""));
            }
            try {
                // Hozirgi sanani olish
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
                String strDate = format.format(calendar.getTime());

                SimpleDateFormat format1 = new SimpleDateFormat("yyyy.MM.dd");
                String strDate_saralash = format1.format(calendar.getTime());

                String sql1 = "INSERT INTO CHIQIM_NOMI VALUES (NULL, ?)";
                Cursor cursor = AsosiyOyna.sqLiteHelper.getData("SELECT * FROM CHIQIM_NOMI WHERE name LIKE '" + chiqim_nom.replace("'","''") + "'");
                if (cursor.getCount() == 0) {
                    AsosiyOyna.sqLiteHelper.insertData_Shot_nomi(chiqim_nom, sql1);
                }

                // Bazaga malumot yozish
                String sql = "INSERT INTO CHIQIM VALUES (NULL, ?, ?, ?, ?, ?, ?)";
                if (!edt_kirim_Summa.getText().toString().equals("")) {
                    AsosiyOyna.sqLiteHelper.insertDataSavdo(doknom, chiqim_nom, summa, strDate, haq_summa, sql, strDate_saralash);
                }
                if (all_Edittext_summa.size() != 0 && all_Spinner_kirim_nom.size() != 0 && all_Spinner_dok_nom.size() != 0) {
                    for (int i = 0; i < all_Edittext_summa.size(); i++) {

                        String spin_dok = all_Spinner_dok_nom.get(i).getSelectedItem().toString().trim();
                        String chiqim = all_Spinner_kirim_nom.get(i).getText().toString().trim();
                        String summaa = all_Edittext_summa.get(i).getText().toString().trim();

                        if (!summaa.equals("") && !chiqim.equals("")) {
                            Cursor cursor2 = AsosiyOyna.sqLiteHelper.getData("SELECT * FROM CHIQIM_NOMI WHERE name LIKE '" + chiqim.replace("'","''") + "'");
                            if (cursor2.getCount() == 0) {
                                AsosiyOyna.sqLiteHelper.insertData_Shot_nomi(chiqim, sql1);
                            }
                            int haq_summa1 = Integer.parseInt(summaa.replaceAll("[^0-9]", ""));
                            AsosiyOyna.sqLiteHelper.insertDataSavdo(spin_dok, chiqim, summaa, strDate, haq_summa1, sql, strDate_saralash);
                        }
                    }

                    //grid viewdan layoutlarni o'chirish
                    for (LinearLayout linearLayout : all_linear_layout_edit) {
                        grid_layout.removeView(linearLayout);
                    }
                    for (LinearLayout linearLayout : all_linear_layout) {
                        grid_layout.removeView(linearLayout);
                    }
                    for (ImageView imageView : all_image_view) {
                        grid_layout.removeView(imageView);
                    }

                    //to'plamlardan viewlarni o'chirish
                    all_image_view.clear();
                    all_Spinner_kirim_nom.clear();
                    all_Spinner_dok_nom.clear();
                    all_Edittext_summa.clear();
                    all_image_iks.clear();
                    all_linear_layout_edit.clear();
                    all_linear_layout.clear();
                    all_image_view.clear();

                }
                if (all_views_dyn.size() != 0) {
                    for (int i = 0; i < all_Textview_dok_nom_dyn.size(); i++) {

                        String tex_dok = all_Textview_dok_nom_dyn.get(i).getText().toString().trim();
                        String spin_shot = all_Spinner_kirim_nom_dyn.get(i).getText().toString().trim();
                        String edt_summa_dyn = all_Edittext_summa_dyn.get(i).getText().toString().trim();

                        String sql_dyn = "INSERT INTO CHIQIM_NOMI VALUES (NULL, ?)";

                        Cursor cursor_dyn = AsosiyOyna.sqLiteHelper.getData("SELECT * FROM CHIQIM_NOMI WHERE name LIKE '" + spin_shot.replace("'","''") + "'");
                        if (cursor_dyn.getCount() == 0) {
                            AsosiyOyna.sqLiteHelper.insertData_Shot_nomi(spin_shot, sql_dyn);
                        }
                        if (!edt_summa_dyn.equals("")) {
                            int haq_sum = Integer.parseInt(edt_summa_dyn.replaceAll("[^0-9]", ""));
                            AsosiyOyna.sqLiteHelper.insertDataSavdo(tex_dok, spin_shot, edt_summa_dyn, strDate, haq_sum, sql, strDate_saralash);
                        }
                    }

                    for (View view : all_views_dyn) {
                        grid_layout.removeView(view);
                    }
                    all_views_dyn.clear();
                }
                edt_kirim_Summa.setText("");
                auto_chiqim.setText("");
                auto_chiqim.requestFocus();
                Toast.makeText(getApplicationContext(), "Muvaffaqqiyatli saqlandi!", Toast.LENGTH_SHORT).show();
            } catch (Exception error) {
                error.printStackTrace();
                Toast.makeText(getApplicationContext(), "Saqlanishda xatolik bo'ldi!!!", Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.action_list) {
            //list tugmasi bosilsa
            Intent intent = new Intent(Chiqim_Add.this, Chiqim_sana.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

}

