package com.example.ibrohimjon.kundalik;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.ActionMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Qarzlarim_royh extends AppCompatActivity {

    Qarzdorlar_adapter adapter = null;
    ArrayAdapter<String> adapter_auto;
    ListView gridView;
    ArrayList<String> list_auto;
    ArrayList<Qarzdorlar_list> list;
    EditText edt_kirim_nomi;
    ImageView btn_kirim_add;
    ImageButton btn_kirim_filter;
    ArrayList<String> kirim_items = new ArrayList<>();
    int a = 2;
    String tanish = "";
    View item_view = null;
    CheckBox select_all;
    int[] images_btn = {R.drawable.sort_asc, R.drawable.sort_desc};
    int count = 0;
    ActionMode actionMode;
    TextView txt_kirim_nomi_tanish;
    View liniya;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kirim_nomi_activity);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

//        Intent intent = getIntent();
        tanish = Qarzlarim_sana.qarzim_ismi;

        list = new ArrayList<>();
        gridView = (ListView) findViewById(R.id.gview_kirim_nomi);
        edt_kirim_nomi = (EditText) findViewById(R.id.edt_kirim_nomi);
        btn_kirim_add = (ImageView) findViewById(R.id.btn_kirim_add_p0);
        btn_kirim_filter = (ImageButton) findViewById(R.id.btn_kirim_filter);
        txt_kirim_nomi_tanish = (TextView) findViewById(R.id.txt_kirim_nomi_tanish);
        select_all = (CheckBox) findViewById(R.id.select_all);
        liniya = findViewById(R.id.kirim_nomi_liniya);

        txt_kirim_nomi_tanish.setText(tanish + "dan barcha qarzlarim ro'yhati!");
        txt_kirim_nomi_tanish.setVisibility(View.VISIBLE);
        liniya.setVisibility(View.VISIBLE);

        Cursor cursor = AsosiyOyna.sqLiteHelper.getData("SELECT name FROM TANISHLAR");
        list_auto = new ArrayList<>();
        list_auto.clear();
        while (cursor.moveToNext()) {
            String name = cursor.getString(0);
            list_auto.add(name);
        }
        adapter_auto = new ArrayAdapter<>(this, android.R.layout.select_dialog_item, list_auto);

        edt_kirim_nomi.setHint("Qidirish...");
        edt_kirim_nomi.setVisibility(View.GONE);
        btn_kirim_filter.setVisibility(View.GONE);

        adapter = new Qarzdorlar_adapter(this, R.layout.savdo_royhat_items, list);
        gridView.setAdapter(adapter);

        try {
            int son = getdata("SELECT * FROM QARZLARIM WHERE tanish_ismi LIKE '" + tanish.replace("'", "''") + "' ORDER BY sana_solish DESC");
            if (son == 0) {
                Toast.makeText(getApplicationContext(), "Qarzlarimda hech nima yo'q!", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                final LinearLayout layout = (LinearLayout) view.findViewById(R.id.layout_qarz_items);
                layout.setBackgroundColor(Color.rgb(218, 112, 214));

                CharSequence[] items = {"O'zgartirish", "O'chirish"};
                AlertDialog.Builder dialog = new AlertDialog.Builder(Qarzlarim_royh.this);

                TextView txt_id = (TextView) view.findViewById(R.id.txtSavdoListId);
                final int id_ozg = Integer.valueOf(txt_id.getText().toString());

                dialog.setTitle("Tanlang");
                dialog.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {

                        if (item == 0) {
                            //Update
                            Cursor cursor1 = AsosiyOyna.sqLiteHelper.getData("SELECT tanish_ismi,summa,sana ,izox FROM QARZLARIM WHERE Id LIKE '" + id_ozg + "'");
                            if (cursor1.getCount() != 0) {
                                cursor1.moveToFirst();
                                String tanish_ismi = cursor1.getString(0);
                                String txt_summa = cursor1.getString(1);
                                String txt_sana = cursor1.getString(2);
                                String izox = cursor1.getString(3);
                                //show dialog update at here
                                showDialogUpdate_(String.valueOf(id_ozg), tanish_ismi, txt_summa, txt_sana, izox);
                            }
                            layout.setBackgroundColor(Color.TRANSPARENT);
                        } else {
                            // delete
                            showDialogDelete(null, id_ozg);
                            layout.setBackgroundColor(Color.TRANSPARENT);
                        }
                    }
                });


                dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        layout.setBackgroundColor(Color.TRANSPARENT);
                    }
                });
//                dialog.setCancelable(false);
                dialog.show();
                return true;
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
//
                TextView txt_id = (TextView) view.findViewById(R.id.txtSavdoListId);
                final String id_ozg = txt_id.getText().toString();
                Intent intent = new Intent(Qarzlarim_royh.this, Qarzlarim_izox.class);
                intent.putExtra("id", id_ozg);
                startActivity(intent);
            }
        });

        select_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (select_all.isChecked()) {
                    for (int i = 0; i < gridView.getCount(); i++) {
                        if (!gridView.isItemChecked(i)) {
                            gridView.setItemChecked(i, true);
                        }
                    }
                } else {
                    actionMode.finish();
                }
            }
        });
        btn_kirim_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (a == 2) {
                    getdata("SELECT * FROM QARZLARIM WHERE tanish_ismi LIKE '" + tanish.replace("'", "''") + "' ORDER BY sana_solish ASC");
                    btn_kirim_filter.setImageResource(images_btn[1]);
                    a = 1;
                } else if (a == 1) {
                    getdata("SELECT * FROM QARZLARIM WHERE tanish_ismi LIKE '" + tanish.replace("'", "''") + "' ORDER BY sana_solish DESC");
                    btn_kirim_filter.setImageResource(images_btn[0]);
                    a = 2;
                }
            }
        });

        btn_kirim_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogUpdate();
            }
        });

    }

    private void showDialogUpdate_(final String id, final String tanish_ismi, final String summa, final String sana, final String izox) {

        final Dialog dialog = new Dialog(Qarzlarim_royh.this);
        dialog.setContentView(R.layout.qarzdorlik_update);
        dialog.setCancelable(false);
        dialog.setTitle("O'zgartirish");

        final EditText edt_summa = (EditText) dialog.findViewById(R.id.edt_qarz_update_summa);
        final EditText edtt_izox = (EditText) dialog.findViewById(R.id.edt_qarz_update_izox);
        Button btn_Update = (Button) dialog.findViewById(R.id.btn_qarz_update_ok);
        TextView txt_yang = (TextView) dialog.findViewById(R.id.txt_yang);
        Button btn_back = (Button) dialog.findViewById(R.id.btn_qarz_update_back);
        TextView txt_id = (TextView) dialog.findViewById(R.id.txt_qarz_update_id);
        TextView txt_sana = (TextView) dialog.findViewById(R.id.txt_qarz_update_sana);
        TextView txt_summa = (TextView) dialog.findViewById(R.id.txt_update_summa);
        final AutoCompleteTextView auto_tanish = (AutoCompleteTextView) dialog.findViewById(R.id.qarz_update_auto_tanish);

        auto_tanish.setThreshold(1);
        auto_tanish.setAdapter(adapter_auto);


        edt_summa.addTextChangedListener(new NumberTextWatcherForThousand(edt_summa,1,null));

        txt_summa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edt_summa.getText().toString().equals("") && !edt_summa.getText().toString().endsWith("$")){
                    edt_summa.setText(String.format("%s$", edt_summa.getText().toString()));
                }
            }
        });

        edt_summa.setText(summa);
        edt_summa.setSelection(summa.length());
        edt_summa.requestFocus();
        txt_id.setText(String.valueOf(id));
        txt_sana.setText(sana);
        auto_tanish.setText(tanish_ismi);
        edtt_izox.setText(izox);

        btn_Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (!edt_summa.getText().toString().equals("") && !auto_tanish.getText().toString().equals("")) {
                        String summa = edt_summa.getText().toString();
                        int haq_summa = Integer.parseInt(summa.replaceAll("[^0-9]", ""));
                        String tanishismi = auto_tanish.getText().toString();
                        String izox = edtt_izox.getText().toString();

                        Cursor cursor = AsosiyOyna.sqLiteHelper.getData("SELECT * FROM TANISHLAR WHERE name LIKE '" + tanishismi.replace("'", "''") + "'");
                        if (cursor.getCount() == 0) {
                            String sql1 = "INSERT INTO TANISHLAR VALUES (NULL, ?)";
                            AsosiyOyna.sqLiteHelper.insertData_Shot_nomi(tanishismi, sql1);
                        }
                        if (summa.endsWith("$")){
                            summa = NumberTextWatcherForThousand.getDecimalFormattedString(summa.replaceAll("[^0-9]", "")) + "$";
                        } else {
                            summa = NumberTextWatcherForThousand.getDecimalFormattedString(summa.replaceAll("[^0-9]", ""));
                        }
                        // Bazaga malumot yozish
                        String sql = "UPDATE QARZLARIM SET tanish_ismi = ?, summa = ?, haq_summa = ?, izox = ? WHERE id = ?";
                        AsosiyOyna.sqLiteHelper.updateData_qarz(sql, tanishismi, summa, haq_summa, izox, id);
                        Toast.makeText(getApplicationContext(), "Muvaffaqqiyatli o'zgartirildi!", Toast.LENGTH_SHORT).show();
                        int so = getdata("SELECT * FROM QARZLARIM WHERE tanish_ismi LIKE '" + tanish.replace("'", "''") + "' ORDER BY sana_solish DESC");
                        if (so == 0){
                            finish();
                        }
                        dialog.dismiss();
                    } else {
                        Toast.makeText(getApplicationContext(), "Iltimos Summani va Tanish ismini kiriting!", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

//        edt_summa.addTextChangedListener(new NumberTextWatcherForThousand(edt_summa, 2, null));
        int width = Qarzlarim_royh.this.getResources().getDisplayMetrics().widthPixels;
        int height = (int) (Qarzlarim_royh.this.getResources().getDisplayMetrics().heightPixels);
        dialog.getWindow().setLayout(width, height);
        dialog.show();

    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            int son = getdata("SELECT * FROM QARZLARIM WHERE tanish_ismi LIKE '" + tanish.replace("'", "''") + "' ORDER BY sana_solish DESC");
            if (son == 0) {
                finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showDialogDelete(final ActionMode mode, final int deleting) {
        final AlertDialog.Builder dialogDelete = new AlertDialog.Builder(Qarzlarim_royh.this);
        dialogDelete.setTitle(R.string.ogohlantirish);
        dialogDelete.setMessage("Haqiqatdan ham ushbu ma'lumotlarni o'chirmoqchimisiz?");
        dialogDelete.setPositiveButton("Ha", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                try {
                    if (deleting == -5) {
                        for (String id_shot : kirim_items) {
                            AsosiyOyna.sqLiteHelper.deleteData_Shot_nomi(Integer.parseInt(id_shot), "DELETE FROM QARZLARIM WHERE Id = ?");
                        }
                        for (int i = 0; i < gridView.getCount(); i++) {
                            gridView.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
                        }
                        count = 0;
                        mode.finish();
                        Toast.makeText(getApplicationContext(), "Muvaffaqqiyatli o'chirildi!", Toast.LENGTH_SHORT).show();
                    }else {
                        AsosiyOyna.sqLiteHelper.deleteData_Shot_nomi(deleting, "DELETE FROM QARZLARIM WHERE Id = ?");
                        Toast.makeText(getApplicationContext(), "Muvaffaqqiyatli o'chirildi!", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception error) {
                    error.printStackTrace();
                    Toast.makeText(getApplicationContext(), "O'chirishda xatolik bo'ldi!", Toast.LENGTH_SHORT).show();
                }
                int asas = getdata("SELECT * FROM QARZLARIM WHERE tanish_ismi LIKE '" + tanish.replace("'","''") + "' ORDER BY sana_solish DESC");
                if (asas == 0){
                    finish();
                }
            }
        });

        dialogDelete.setNegativeButton("Yo'q", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialogDelete.show();
    }


    private void showDialogUpdate() {

        final Dialog dialog = new Dialog(Qarzlarim_royh.this);
        dialog.setContentView(R.layout.qarzdorlik_update);
        dialog.setCancelable(false);
        dialog.setTitle("Qo'shish");

        final EditText edt_summa = (EditText) dialog.findViewById(R.id.edt_qarz_update_summa);
        final EditText edt_izox = (EditText) dialog.findViewById(R.id.edt_qarz_update_izox);
        Button btn_Update = (Button) dialog.findViewById(R.id.btn_qarz_update_ok);
        TextView txt_yang = (TextView) dialog.findViewById(R.id.txt_yang);
        Button btn_back = (Button) dialog.findViewById(R.id.btn_qarz_update_back);
        TextView txt_id = (TextView) dialog.findViewById(R.id.txt_qarz_update_id);
        TextView txt_sana = (TextView) dialog.findViewById(R.id.txt_qarz_update_sana);
        TextView txt_summa = (TextView) dialog.findViewById(R.id.txt_update_summa);
        final AutoCompleteTextView auto_tanish = (AutoCompleteTextView) dialog.findViewById(R.id.qarz_update_auto_tanish);

        auto_tanish.setThreshold(1);
        auto_tanish.setAdapter(adapter_auto);

        edt_summa.addTextChangedListener(new NumberTextWatcherForThousand(edt_summa,1,null));

        txt_summa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edt_summa.getText().toString().equals("") && !edt_summa.getText().toString().endsWith("$")){
                    edt_summa.setText(String.format("%s$", edt_summa.getText().toString()));
                }
            }
        });

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        final String strDate = format.format(calendar.getTime());

        SimpleDateFormat format1 = new SimpleDateFormat("yyyy.MM.dd");
        final String strDate_saralash = format1.format(calendar.getTime());

        txt_yang.setText("Yangi");
        txt_sana.setText(strDate);
        auto_tanish.requestFocus();
        btn_Update.setText("Saqlash");

        btn_Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (!edt_summa.getText().toString().equals("") && !auto_tanish.getText().toString().equals("")) {
                        String summa = edt_summa.getText().toString();
                        int haq_summa = Integer.parseInt(summa.replaceAll("[^0-9]", ""));
                        String tanishismi = auto_tanish.getText().toString();
                        String izox = edt_izox.getText().toString();

                        Cursor cursor = AsosiyOyna.sqLiteHelper.getData("SELECT * FROM TANISHLAR WHERE name LIKE '" + tanishismi.replace("'", "''") + "'");
                        if (cursor.getCount() == 0) {
                            String sql1 = "INSERT INTO TANISHLAR VALUES (NULL, ?)";
                            AsosiyOyna.sqLiteHelper.insertData_Shot_nomi(tanishismi, sql1);
                        }
                        // Bazaga malumot yozish
                        String sql = "INSERT INTO QARZLARIM VALUES (NULL, ?, ?, ?, ?, ?, ?)";
                        if (summa.endsWith("$")){
                            summa = NumberTextWatcherForThousand.getDecimalFormattedString(summa.replaceAll("[^0-9]", "")) + "$";
                        } else {
                            summa = NumberTextWatcherForThousand.getDecimalFormattedString(summa.replaceAll("[^0-9]", ""));
                        }
                        AsosiyOyna.sqLiteHelper.insertData_qarzdor(tanishismi, summa, strDate, haq_summa, strDate_saralash, izox, sql);

                        getdata("SELECT * FROM QARZLARIM WHERE tanish_ismi LIKE '" + tanish.replace("'", "''") + "' ORDER BY sana_solish DESC");
                        dialog.dismiss();
                    } else {
                        Toast.makeText(getApplicationContext(), "Iltimos Summani va Tanish ismini kiriting!", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

//        edt_summa.addTextChangedListener(new NumberTextWatcherForThousand(edt_summa, 2, null));
        int width = Qarzlarim_royh.this.getResources().getDisplayMetrics().widthPixels;
        int height = (int) (Qarzlarim_royh.this.getResources().getDisplayMetrics().heightPixels);
        dialog.getWindow().setLayout(width, height);
        dialog.show();

    }

    public int getdata(String sql) {
        Cursor cursor = AsosiyOyna.sqLiteHelper.getData(sql);
        list.clear();
        if (cursor.getCount() == 0) {
            list.clear();
            adapter.notifyDataSetChanged();
//            Toast.makeText(getApplicationContext(), "Qarzdorlarda hech nima yo'q!", Toast.LENGTH_LONG).show();
            return 0;
        } else {
            cursor.moveToFirst();
            do {
                String tanish_ismi = cursor.getString(1);
                int id = cursor.getInt(0);
                String bosh_harf;
                if (tanish_ismi.substring(1, 2).equals("'")) {
                    bosh_harf = tanish_ismi.substring(0, 2).toUpperCase();
                } else {
                    bosh_harf = tanish_ismi.substring(0, 1).toUpperCase();
                }
                String sana = cursor.getString(3);
                String summa = cursor.getString(2);

                list.add(new Qarzdorlar_list(tanish_ismi, summa, bosh_harf, sana, id));
            } while (cursor.moveToNext());
            adapter.notifyDataSetChanged();
            return 1;
        }
    }
}
