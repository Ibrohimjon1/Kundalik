package com.example.ibrohimjon.kundalik;

import android.annotation.SuppressLint;
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

public class Kirim_sana extends AppCompatActivity {

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
    TextView txt_yoq;

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
        txt_yoq = (TextView) findViewById(R.id.txt_kirim_nomi_yoq);

        assert txt_yoq != null;
        txt_yoq.setText("Kirimda hech nima yo'q!");

//        btn_kirim_add.setVisibility(View.GONE);
        edt_kirim_nomi.setVisibility(View.GONE);
        btn_kirim_filter.setVisibility(View.GONE);

        adapter = new Kirim_nomi_adapter(this, R.layout.kirim_nomi_items, list);
        gridView.setAdapter(adapter);

        btn_kirim_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Kirim_sana.this, Kirim_Add.class);
                startActivity(intent);
            }
        });

        try {
            int son = getdata("SELECT DISTINCT sana FROM KIRIM ORDER BY sana_solish DESC");
            if (son == 0) {
                Toast.makeText(getApplicationContext(), "Kirimda hech nima yo'q!", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

//        gridView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE_MODAL);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = (TextView) view.findViewById(R.id.txt_kirim_name);
                String data = textView.getText().toString();
                Intent intent = new Intent(Kirim_sana.this, Kirim_royh_yangi.class);
                intent.putExtra("sana", data);
                startActivity(intent);
            }
        });


//        gridView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
//            @Override
//            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
//                item_view = (View) gridView.getChildAt(position);
//                TextView txt = (TextView) item_view.findViewById(R.id.txt_kirim_name);
//                String kirim_id = txt.getText().toString();
//
//                if (checked) {
//                    count++;
//                    kirim_items.add(kirim_id);
//                    gridView.getChildAt(position).setBackgroundColor(Color.rgb(218, 112, 214));
//                    mode.setTitle(count + "ta tanlandi");
//                    if (count == gridView.getCount()){
//                        select_all.setChecked(true);
//                    }else {
//                        select_all.setChecked(false);
//                    }
//                } else {
//                    count--;
//                    gridView.getChildAt(position).setBackgroundColor(Color.TRANSPARENT);
//                    mode.setTitle(count + "ta tanlandi");
//                    kirim_items.remove(kirim_id);
//                    select_all.setChecked(false);
//                }
//            }
//
//            @Override
//            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
//                MenuInflater inflater = mode.getMenuInflater();
//                inflater.inflate(R.menu.my_context_menu, menu);
//
//                actionMode = mode;
//                select_all.setVisibility(View.VISIBLE);
//                return true;
//            }
//
//            @Override
//            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
//
//                return true;
//            }
//
//            @Override
//            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
//
//                switch (item.getItemId()) {
//                    case R.id.delete_id: {
//                        showDialogDelete(mode);
//                        return true;
//                    }
//                    default:
//                        return false;
//                }
//            }
//
//            @Override
//            public void onDestroyActionMode(ActionMode mode) {
//                for (int i = 0; i < gridView.getCount(); i++) {
//                    gridView.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
//                }
//                select_all.setVisibility(View.GONE);
//                count = 0;
//            }
//        });

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

        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, final View view, final int position, long id) {

                final LinearLayout layout = (LinearLayout) view.findViewById(R.id.layout_kirim_item);
                layout.setBackgroundColor(Color.rgb(218, 112, 214));
                CharSequence[] items = {"O'zgartirish", "O'chirish"};
                AlertDialog.Builder dialog = new AlertDialog.Builder(Kirim_sana.this);

                dialog.setTitle("Tanlang");
                dialog.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        TextView txt_kirim = (TextView) view.findViewById(R.id.txt_kirim_name);

                        if (item == 0) {
                            //Update
                            Toast.makeText(getApplicationContext(), "Sanani o'zgartirish mumkin emas!", Toast.LENGTH_SHORT).show();

//                            showDialog_Update(Savdo_sana_yangi.this, Integer.parseInt(id), txt_kirim.getText().toString());
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

    }

    private void showDialogDelete_for_one(final String sana) {
        AlertDialog.Builder dialogDelete = new AlertDialog.Builder(Kirim_sana.this);
        dialogDelete.setTitle("Ogohlantirish!!!");
        dialogDelete.setMessage("Haqiqatdan ham ushbu ma'lumotni o'chirmoqchimisiz?");
        dialogDelete.setPositiveButton("Ha", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                try {
                    AsosiyOyna.sqLiteHelper.insertData_Shot_nomi(sana , "DELETE FROM KIRIM WHERE sana = ?");
                    Toast.makeText(getApplicationContext(), "Muvaffaqqiyatli o'chirildi!!!", Toast.LENGTH_SHORT).show();
                } catch (Exception error) {
                    error.printStackTrace();
                }
                getdata("SELECT DISTINCT sana FROM KIRIM ORDER BY sana_solish DESC");
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
//        final AlertDialog.Builder dialogDelete = new AlertDialog.Builder(Kirim_sana.this);
//        dialogDelete.setTitle(R.string.ogohlantirish);
//        dialogDelete.setMessage("Haqiqatdan ham ushbu ma'lumotlarni o'chirmoqchimisiz?");
//        dialogDelete.setPositiveButton("Ha", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//                try {
//                    for (String id_shot : kirim_items) {
//                        Cursor cursor = AsosiyOyna.sqLiteHelper.getData("SELECT Id FROM KIRIM WHERE sana LIKE '" + id_shot + "'");
//                        if (cursor.getCount() != -1) {
//                            cursor.moveToFirst();
//                            do {
//                                int id = cursor.getInt(0);
//                                AsosiyOyna.sqLiteHelper.deleteData_Shot_nomi(id, "DELETE FROM KIRIM WHERE Id = ?");
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
//                getdata("SELECT DISTINCT sana FROM KIRIM ORDER BY sana_solish DESC");
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


    @Override
    protected void onResume() {
        super.onResume();
        getdata("SELECT DISTINCT sana FROM KIRIM ORDER BY sana_solish DESC");
    }

    public int getdata(String sql) {
        Cursor cursor = AsosiyOyna.sqLiteHelper.getData(sql);
        int id_soni = 0;
        list.clear();
        if (cursor.getCount() == 0) {
            list.clear();
            adapter.notifyDataSetChanged();
            txt_yoq.setVisibility(View.VISIBLE);
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

            txt_yoq.setVisibility(View.GONE);
            return 1;
        }
    }
}
