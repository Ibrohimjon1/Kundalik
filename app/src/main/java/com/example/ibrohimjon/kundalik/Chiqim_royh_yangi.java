package com.example.ibrohimjon.kundalik;

import android.annotation.SuppressLint;
import android.app.Activity;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Chiqim_royh_yangi extends AppCompatActivity {

    ArrayList<Savdo_list_yangi> list_yangi;
    Savdo_adapter_yangi adapter_yangi = null;
    ListView listView;
    String sana = "";
    TextView txt_sana;
    ArrayList<String> list, list1;
    ImageView btn_add;
    ArrayList<String> savdo_idlar = new ArrayList<>();
    int a = 0;
    View item_view = null;
    CheckBox select_all;
    ActionMode actionMode;
    int count = 0;
    TextView txt_shot;
    boolean long_click = false;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.savdo_royh_yangi);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        sana = intent.getExtras().getString("sana");

        listView = (ListView) findViewById(R.id.list_view_yangi);
        txt_sana = (TextView) findViewById(R.id.sana);
        txt_sana.setText(sana);
        list_yangi = new ArrayList<>();
        btn_add = (ImageView) findViewById(R.id.btn_savdo_add_p_yang);
        select_all = (CheckBox) findViewById(R.id.savdo_select_all);
        txt_shot = (TextView) findViewById(R.id.txt_shot_nomi);

        txt_shot.setText("Chiqim nomi");

        adapter_yangi = new Savdo_adapter_yangi(this, R.layout.dynamics, list_yangi);
        listView.setAdapter(adapter_yangi);

        getData();

//        listView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE_MODAL);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, final int position, long id) {

                final LinearLayout layout = (LinearLayout) view.findViewById(R.id.layout_dyn);
                layout.setBackgroundColor(Color.rgb(218, 112, 214));

                CharSequence[] items = {"O'zgartirish", "O'chirish"};
                AlertDialog.Builder dialog = new AlertDialog.Builder(Chiqim_royh_yangi.this);
                TextView txt_id = (TextView) view.findViewById(R.id.Id_dyn);
                final int id3 = Integer.parseInt(txt_id.getText().toString());
                dialog.setTitle("Tanlang");
                dialog.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {

                        if (item == 0) {
                            //Update

                            TextView txt_dok_nom = (TextView) view.findViewById(R.id.DokonNomi_dyn);
                            TextView txt_shot_nom = (TextView) view.findViewById(R.id.ShotNomi_dyn);
                            TextView txt_summa = (TextView) view.findViewById(R.id.Summa_dyn);

                            //show dialog update at here
                            showDialogUpdate(Chiqim_royh_yangi.this, id3,
                                    txt_dok_nom.getText().toString(), txt_shot_nom.getText().toString(),
                                    txt_summa.getText().toString(), sana, 1);
                            layout.setBackgroundColor(Color.TRANSPARENT);

                        } else {
                            // delete
                            showDialogDelete(id3);
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

                dialog.show();
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (!long_click){
                    long_click = true;
                }
                return false;
            }
        });

        select_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (select_all.isChecked()) {
                    for (int i = 0; i < listView.getCount(); i++) {
                        if (!listView.isItemChecked(i)) {
                            listView.setItemChecked(i, true);
                        }
                    }
                } else {
                    actionMode.finish();
                }
            }
        });

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogUpdate(Chiqim_royh_yangi.this, 0, null, null, null, sana, 2);
            }
        });
    }

    private void show_Delete(final ActionMode mode) {
        final AlertDialog.Builder dialogDelete = new AlertDialog.Builder(Chiqim_royh_yangi.this);
        dialogDelete.setTitle(R.string.ogohlantirish);
        dialogDelete.setMessage("Haqiqatdan ham ushbu ma'lumotlarni o'chirmoqchimisiz?");
        dialogDelete.setPositiveButton("Ha", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                try {
                    for (String id_savdo : savdo_idlar) {
                        AsosiyOyna.sqLiteHelper.deleteData_Shot_nomi(Integer.parseInt(id_savdo), "DELETE FROM CHIQIM WHERE Id = ?");
                    }
                    savdo_idlar.clear();
                    mode.finish();
                    count = 0;
                    Toast.makeText(getApplicationContext(), "Muvaffaqqiyatli o'chirildi!", Toast.LENGTH_SHORT).show();
                } catch (Exception error) {
                    error.printStackTrace();
                }
                getData();
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


    private void showDialogDelete(final int id_shot) {
        AlertDialog.Builder dialogDelete = new AlertDialog.Builder(Chiqim_royh_yangi.this);
        dialogDelete.setTitle(R.string.ogohlantirish);
        dialogDelete.setMessage("Haqiqatdan ham ushbu ma'lumotni o'chirmoqchimisiz?");
        dialogDelete.setPositiveButton("Ha", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                try {
                    AsosiyOyna.sqLiteHelper.deleteData_Shot_nomi(id_shot, "DELETE FROM CHIQIM WHERE Id = ?");
                    Toast.makeText(getApplicationContext(), "Muvaffaqqiyatli o'chirildi!", Toast.LENGTH_SHORT).show();
                } catch (Exception error) {
                    error.printStackTrace();
                }
                getData();
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

    private void showDialogUpdate(Activity activity, final int id, String dok_nomi, String shot_nomi, final String summa, final String sana, final int update_add) {

        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.chiqim_update_yangi);
        dialog.setCancelable(false);
        dialog.setTitle("O'zgartirish");

        final EditText edt_summa = (EditText) dialog.findViewById(R.id.edt_chiqim_upd_summa_yangi);
        Button btn_Update = (Button) dialog.findViewById(R.id.btn_chiqim_upd_yangi);
        Button btn_back = (Button) dialog.findViewById(R.id.btn_chiqim_upd_back_yangi);
        TextView txt_id = (TextView) dialog.findViewById(R.id.txt_chiqim_upd_id_yangi);
        TextView txt_sana = (TextView) dialog.findViewById(R.id.txt_chiqim_upd_sana_yangi);
        final Spinner dokon_nomi = (Spinner) dialog.findViewById(R.id.DokonNomi_chiqim_yangi);
        final AutoCompleteTextView auto_chiqim = (AutoCompleteTextView) dialog.findViewById(R.id.auto_chiqim_yangi);
        TextView txt_nom = (TextView) dialog.findViewById(R.id.txt_chiqim_yangi);

        Cursor cursor = AsosiyOyna.sqLiteHelper.getData("SELECT name FROM DOKON_NOMI");
        list = new ArrayList<>();
        list1 = new ArrayList<>();
        list.clear();
        while (cursor.moveToNext()) {
            String name = cursor.getString(0);
            list.add(name);
        }
        ArrayAdapter<String> adapter_dok_nom = new ArrayAdapter<>(this, R.layout.spinner_items, list);
        adapter_dok_nom.setDropDownViewResource(R.layout.spinner_down_list_add);
        assert dokon_nomi != null;
        dokon_nomi.setAdapter(adapter_dok_nom);
        dokon_nomi.setPrompt("Chiqim nomlari");


        Cursor cursor1 = AsosiyOyna.sqLiteHelper.getData("SELECT name FROM CHIQIM_NOMI");
        list1.clear();
        while (cursor1.moveToNext()) {
            String name = cursor1.getString(0);
            list1.add(name);
        }
        ArrayAdapter<String> adapter_chiqim_nom = new ArrayAdapter<>(this,  android.R.layout.select_dialog_item, list1);
        adapter_chiqim_nom.setDropDownViewResource(R.layout.spinner_down_list_add);

        auto_chiqim.setThreshold(1);
        auto_chiqim.setAdapter(adapter_chiqim_nom);

        if (update_add == 1) {
            dokon_nomi.setSelection(list.indexOf(dok_nomi));
            auto_chiqim.setText(shot_nomi);
            edt_summa.setText(summa);
            edt_summa.requestFocus();
            edt_summa.setSelection(summa.length());
            txt_id.setText(String.valueOf(id));
        } else {
            txt_nom.setText("Yangi");
            btn_Update.setText("Saqlash");
        }
        txt_sana.setText(sana);


        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btn_Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (!edt_summa.getText().toString().equals("")) {
                        String nom_dok = dokon_nomi.getSelectedItem().toString().trim();
                        String shot_nom = auto_chiqim.getText().toString().trim();
                        String summa = edt_summa.getText().toString().trim();
                        int haq_summa = 0;
                        if (!summa.equals("")) {
                             haq_summa = Integer.parseInt(summa.replaceAll("[^0-9]", ""));
                        }
                        String sql1 = "INSERT INTO CHIQIM_NOMI VALUES (NULL, ?)";
                        Cursor cursor = AsosiyOyna.sqLiteHelper.getData("SELECT * FROM CHIQIM_NOMI WHERE name LIKE '" + shot_nom.replace("'","''").trim() + "'");
                        if (cursor.getCount() == 0) {
                            AsosiyOyna.sqLiteHelper.insertData_Shot_nomi(shot_nom, sql1);
                        }

                        if (update_add == 1) {
                            String sql = "UPDATE CHIQIM SET dokon_nomi = ?, chiqim_nomi = ?, summa = ?, haq_summa = ? WHERE id = ?";
                            AsosiyOyna.sqLiteHelper.updateData_savdo(sql, id, nom_dok, shot_nom, summa);
                            Toast.makeText(getApplicationContext(), "Muvaffaqqiyatli o'zgartirildi!", Toast.LENGTH_SHORT).show();
                        } else {
                            Cursor cursor_sana = AsosiyOyna.sqLiteHelper.getData("SELECT sana_solish FROM CHIQIM WHERE sana LIKE '%" + sana + "%'");
                            cursor_sana.moveToFirst();
                            String str_data_saralash = cursor_sana.getString(0);
                            String sql = "INSERT INTO CHIQIM VALUES (NULL, ?, ?, ?, ?, ?, ?)";
                            AsosiyOyna.sqLiteHelper.insertDataSavdo(nom_dok, shot_nom, summa, sana, haq_summa, sql, str_data_saralash);
                            Toast.makeText(getApplicationContext(), "Muvaffaqqiyatli saqlandi!", Toast.LENGTH_SHORT).show();
                        }
                        getData();
                        dialog.dismiss();
                    } else {
                        Toast.makeText(getApplicationContext(), "Iltimos Summani kiriting!", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        edt_summa.addTextChangedListener(new NumberTextWatcherForThousand(edt_summa, 2, null));
        int width = (int) (activity.getResources().getDisplayMetrics().widthPixels);
        int height = (int) (activity.getResources().getDisplayMetrics().heightPixels);
        dialog.getWindow().setLayout(width, height);
        dialog.show();

    }

    public void getData() {

        Cursor cursor_dok = AsosiyOyna.sqLiteHelper.getData("SELECT DISTINCT dokon_nomi FROM CHIQIM WHERE sana LIKE '%" + sana + "%'");
        list_yangi.clear();
        if (cursor_dok.getCount() != 0) {
            cursor_dok.moveToFirst();
            do {
                int visible = 0;
                String dok_nomi = cursor_dok.getString(0);

                Cursor cursor_savdo = AsosiyOyna.sqLiteHelper.getData("SELECT * FROM CHIQIM WHERE sana LIKE '%" + sana + "%' AND dokon_nomi LIKE '" + dok_nomi.replace("'","''") + "%'");
                if (cursor_savdo.getCount() != 0) {
                    cursor_savdo.moveToFirst();
                    do {
                        int id = cursor_savdo.getInt(0);
                        String dokon_nomi = cursor_savdo.getString(1);
                        String shot_nomi = cursor_savdo.getString(2);
                        String summa = cursor_savdo.getString(3);
                        list_yangi.add(new Savdo_list_yangi(dokon_nomi, shot_nomi, summa, id, visible));
                        visible++;
                    } while (cursor_savdo.moveToNext());
                }
            } while (cursor_dok.moveToNext());
            adapter_yangi.notifyDataSetChanged();

        }else {
            list_yangi.clear();
            adapter_yangi.notifyDataSetChanged();
            finish();
        }
    }

}
