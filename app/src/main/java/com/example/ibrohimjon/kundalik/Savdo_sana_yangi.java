package com.example.ibrohimjon.kundalik;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Savdo_sana_yangi extends AppCompatActivity {

    Kirim_nomi_adapter adapter = null;
    ListView gridView;
    ArrayList<Kirim_nomi_list> list;
    EditText edt_kirim_nomi;
    ImageView btn_kirim_add;
    ImageButton btn_kirim_filter;
//    ArrayList<String> kirim_items = new ArrayList<>();
    int a = 0;
    View item_view = null;
    CheckBox select_all;
    ActionMode actionMode;
    int count = 0;
    TextView txt_kirim_nomi_yoq ;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kirim_nomi_activity);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        list = new ArrayList<>();
        gridView = (ListView) findViewById(R.id.gview_kirim_nomi);
        edt_kirim_nomi = (EditText) findViewById(R.id.edt_kirim_nomi);
        btn_kirim_add = (ImageView) findViewById(R.id.btn_kirim_add_p0);
        btn_kirim_filter = (ImageButton) findViewById(R.id.btn_kirim_filter);
        select_all = (CheckBox) findViewById(R.id.select_all);
        txt_kirim_nomi_yoq = (TextView) findViewById(R.id.txt_kirim_nomi_yoq);

        assert txt_kirim_nomi_yoq != null;
        txt_kirim_nomi_yoq.setText("Savdoda hech nima yo'q");

//        btn_kirim_add.setVisibility(View.GONE);
        edt_kirim_nomi.setVisibility(View.GONE);
        btn_kirim_filter.setVisibility(View.GONE);

        adapter = new Kirim_nomi_adapter(this, R.layout.kirim_nomi_items, list);
        gridView.setAdapter(adapter);

        try {
            int son = getdata("SELECT DISTINCT sana FROM SAVDO ORDER BY sana_solish DESC");
            if (son == 0) {
                Toast.makeText(getApplicationContext(), "Savdoda hech nima yo'q!", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        btn_kirim_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Savdo_sana_yangi.this, Savdo_Add.class);
                startActivity(intent);
            }
        });

//        gridView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE_MODAL);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = (TextView) view.findViewById(R.id.txt_kirim_name);
                String data = textView.getText().toString();
                Intent intent = new Intent(Savdo_sana_yangi.this, Savdo_royh_yangi.class);
                intent.putExtra("sana", data);
                startActivity(intent);
            }
        });

        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, final View view, final int position, long id) {

                final LinearLayout layout = (LinearLayout) view.findViewById(R.id.layout_kirim_item);
                layout.setBackgroundColor(Color.rgb(218, 112, 214));
                CharSequence[] items = {"O'zgartirish", "O'chirish"};
                AlertDialog.Builder dialog = new AlertDialog.Builder(Savdo_sana_yangi.this);

                dialog.setTitle("Tanlang");
                dialog.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        TextView txt_kirim = (TextView) view.findViewById(R.id.txt_kirim_name);

                        if (item == 0) {
                            //Update
//                            Toast.makeText(getApplicationContext(), "Sanani o'zgartirish mumkin emas!", Toast.LENGTH_SHORT).show();

                            ShowDialog_update_sana(txt_kirim.getText().toString().trim());

                            layout.setBackgroundColor(Color.TRANSPARENT);
                        } else {
                            // delete
                            showDialogDelete_for_one(txt_kirim.getText().toString().trim());
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
                return true;
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

    }

    @Override
    protected void onResume() {
        super.onResume();
        getdata("SELECT DISTINCT sana FROM SAVDO ORDER BY sana_solish DESC");
    }

    int DatePicker = 1;
    int year_x = 0, month_x = 0, day_x = 0;
    String eski_sana = "";

    public void ShowDialog_update_sana(String sana){
        if (!sana.equals("")){
            day_x = Integer.parseInt(sana.substring(0,2));
            month_x = Integer.parseInt(sana.substring(3,5)) - 1;
            year_x = Integer.parseInt(sana.substring(6,10));
            eski_sana = sana;
            showDialog(DatePicker);
        }
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == DatePicker) {
            return new DatePickerDialog(this, dpickerListener, year_x, month_x, day_x);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener dpickerListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(android.widget.DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            String oy, kun;
            if (monthOfYear < 10) {
                oy = "0" + String.valueOf(monthOfYear + 1);
            } else {
                oy = String.valueOf(monthOfYear + 1);
            }
            if (dayOfMonth < 10) {
                kun = "0" + String.valueOf(dayOfMonth);
            } else {
                kun = String.valueOf(dayOfMonth);
            }
            String sana = kun + "." + oy + "." + String.valueOf(year);
            String solishtirma = String.valueOf(year) + "." + oy + "." + kun;


            if (!sana.equals("")){
                try {
                    String sql = "UPDATE SAVDO SET sana = ?, sana_solish = ? WHERE sana = ?";
                    AsosiyOyna.sqLiteHelper.update_sana(sana,solishtirma,sql,eski_sana);
                    getdata("SELECT DISTINCT sana FROM SAVDO ORDER BY sana_solish DESC");
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    };

    private void showDialogDelete_for_one(final String sana) {
        AlertDialog.Builder dialogDelete = new AlertDialog.Builder(Savdo_sana_yangi.this);
        dialogDelete.setTitle("Ogohlantirish!!!");
        dialogDelete.setMessage("Haqiqatdan ham ushbu ma'lumotni o'chirmoqchimisiz?");
        dialogDelete.setPositiveButton("Ha", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                try {
                    Cursor cursor = AsosiyOyna.sqLiteHelper.getData("SELECT Id FROM SAVDO WHERE sana LIKE '" + sana + "'");
                    if (cursor.getCount() != -1) {
                        cursor.moveToFirst();
                        do {
                            int id = cursor.getInt(0);
                            AsosiyOyna.sqLiteHelper.deleteData_Shot_nomi(id, "DELETE FROM SAVDO WHERE Id = ?");
                        } while (cursor.moveToNext());
                    }
                    Toast.makeText(getApplicationContext(), "Muvaffaqqiyatli o'chirildi!!!", Toast.LENGTH_SHORT).show();
                } catch (Exception error) {
                    error.printStackTrace();
                }
                getdata("SELECT DISTINCT sana FROM SAVDO ORDER BY sana_solish DESC");
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

//    private void showDialogDelete(final ActionMode mode) {
//        final AlertDialog.Builder dialogDelete = new AlertDialog.Builder(Savdo_sana_yangi.this);
//        dialogDelete.setTitle(R.string.ogohlantirish);
//        dialogDelete.setMessage("Haqiqatdan ham ushbu ma'lumotlarni o'chirmoqchimisiz?");
//        dialogDelete.setPositiveButton("Ha", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//                try {
//                    for (String id_shot : kirim_items) {
//                        Cursor cursor = AsosiyOyna.sqLiteHelper.getData("SELECT Id FROM SAVDO WHERE sana LIKE '" + id_shot + "'");
//                        if (cursor.getCount() != -1) {
//                            cursor.moveToFirst();
//                            do {
//                                int id = cursor.getInt(0);
//                                AsosiyOyna.sqLiteHelper.deleteData_Shot_nomi(id, "DELETE FROM SAVDO WHERE Id = ?");
//                            } while (cursor.moveToNext());
//                        }
//                    }
//                    kirim_items.clear();
//                    for (int i = 0; i < gridView.getCount(); i++) {
//                        gridView.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
//                    }
//                    count = 0;
//                    mode.finish();
//                    Toast.makeText(getApplicationContext(), "Muvaffaqqiyatli o'chirildi!", Toast.LENGTH_SHORT).show();
//                } catch (Exception error) {
//                    error.printStackTrace();
//                }
//                getdata("SELECT DISTINCT sana FROM SAVDO ORDER BY sana_solish DESC");
//            }
//        });
//
//        dialogDelete.setNegativeButton("Yo'q", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//            }
//        });
//        dialogDelete.show();
//    }

    public int getdata(String sql) {
        Cursor cursor = AsosiyOyna.sqLiteHelper.getData(sql);
        int id_soni = 0;
        list.clear();
        if (cursor.getCount() == 0) {
            list.clear();
            adapter.notifyDataSetChanged();
            txt_kirim_nomi_yoq.setVisibility(View.VISIBLE);
            return 0;
        } else {
            cursor.moveToFirst();
            do {
                id_soni++;
                int tartib = id_soni;
                String kirim_nomi = cursor.getString(0);
                //int id = cursor.getInt(0);
                String bosh_harf = "";
                if (!kirim_nomi.equals("")) {
                    if (kirim_nomi.substring(1, 2).equals("'")) {
                        bosh_harf = kirim_nomi.substring(0, 2).toUpperCase();
                    } else {
                        bosh_harf = kirim_nomi.substring(0, 1).toUpperCase();
                    }
                }
                list.add(new Kirim_nomi_list(kirim_nomi, tartib, bosh_harf, 0));
            } while (cursor.moveToNext());
            adapter.notifyDataSetChanged();
            txt_kirim_nomi_yoq.setVisibility(View.GONE);
            return 1;
        }
    }
}
