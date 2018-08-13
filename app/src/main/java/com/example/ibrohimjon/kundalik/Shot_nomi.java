package com.example.ibrohimjon.kundalik;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Shot_nomi extends AppCompatActivity {

    Kirim_nomi_adapter adapter = null;
    ListView gridView;
    ArrayList<Kirim_nomi_list> list;
    EditText edt_kirim_nomi;
    ImageView btn_kirim_add;
    ImageButton btn_kirim_filter;
//    ArrayList<String> kirim_items = new ArrayList<String>();
    int a = 2;
    View item_view = null;
    int[] images_btn = {R.drawable.sort_asc, R.drawable.sort_desc};
    int count = 0;
    TextView txt_kirim_nomi_yoq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kirim_nomi_activity);

        list = new ArrayList<>();
        gridView = (ListView) findViewById(R.id.gview_kirim_nomi);
        edt_kirim_nomi = (EditText) findViewById(R.id.edt_kirim_nomi);
        btn_kirim_add = (ImageView) findViewById(R.id.btn_kirim_add_p0);
        btn_kirim_filter = (ImageButton) findViewById(R.id.btn_kirim_filter);
        edt_kirim_nomi.setHint("Shot nomi");
        txt_kirim_nomi_yoq = (TextView) findViewById(R.id.txt_kirim_nomi_yoq);

        assert txt_kirim_nomi_yoq != null;
        txt_kirim_nomi_yoq.setText("Shot nomida hech nima yo'q");

        adapter = new Kirim_nomi_adapter(this, R.layout.kirim_nomi_items, list);
        gridView.setAdapter(adapter);

        try {
            int son = getdata("SELECT * FROM SHOT_NOMI ORDER BY name ASC");
            if (son == 0) {
                Toast.makeText(getApplicationContext(), "Shot nomida hech nima yo'q", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

//        gridView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE_MODAL);

//        gridView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
//            @Override
//            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
//                item_view = (View) gridView.getChildAt(position);
//                TextView txt = (TextView) item_view.findViewById(R.id.txt_kirim_item_id);
//                String kirim_id = txt.getText().toString();
//                if (checked) {
//                    count++;
//                    kirim_items.add(kirim_id);
//                    gridView.getChildAt(position).setBackgroundColor(Color.rgb(218, 112, 214));
//                    mode.setTitle(count + "ta tanlandi");
//                } else {
//                    count--;
//                    gridView.getChildAt(position).setBackgroundColor(Color.TRANSPARENT);
//                    mode.setTitle(count + "ta tanlandi");
//                    kirim_items.remove(kirim_id);
//                }
//            }
//
//            @Override
//            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
//                MenuInflater inflater = mode.getMenuInflater();
//                inflater.inflate(R.menu.my_context_menu, menu);
//
//                return true;
//            }
//
//            @Override
//            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
//                return false;
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
//                count = 0;
//            }
//        });
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                final LinearLayout layout = (LinearLayout) view.findViewById(R.id.layout_kirim_item);
                layout.setBackgroundColor(Color.rgb(218, 112, 214));

                CharSequence[] items = {"O'zgartirish", "O'chirish"};
                AlertDialog.Builder dialog = new AlertDialog.Builder(Shot_nomi.this);
                TextView txt_id = (TextView) view.findViewById(R.id.txt_kirim_item_id);
                final TextView txt_chiqim = (TextView) view.findViewById(R.id.txt_kirim_name);
                final int id3 = Integer.parseInt(txt_id.getText().toString());
                dialog.setTitle("Tanlang");
                dialog.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {

                        if (item == 0) {
                            //Update
                            showDialog_Update(Shot_nomi.this,id3,txt_chiqim.getText().toString().trim());
                            layout.setBackgroundColor(Color.TRANSPARENT);
                        } else {
                            // delete
                            showDialogDelete_for_one(id3);
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

        btn_kirim_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (a == 1) {
                    getdata("SELECT * FROM SHOT_NOMI ORDER BY name ASC");
                    btn_kirim_filter.setImageResource(images_btn[1]);
                    a = 2;
                } else if (a == 2) {
                    getdata("SELECT * FROM SHOT_NOMI ORDER BY name DESC");
                    btn_kirim_filter.setImageResource(images_btn[0]);
                    a = 1;
                }
            }
        });

        btn_kirim_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edt_kirim_nomi.getText().toString().equals("")) {
                    String add = edt_kirim_nomi.getText().toString().trim();
                    try {
                        String sql = "INSERT INTO SHOT_NOMI VALUES (NULL, ?)";
                        AsosiyOyna.sqLiteHelper.insertData_Shot_nomi(add, sql);
                        edt_kirim_nomi.setText("");
                        getdata("SELECT * FROM SHOT_NOMI ORDER BY name ASC");
                        Toast.makeText(getApplicationContext(), R.string.muvaf_saqlash, Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(),"Saqlanishda xatolik bo'ldi!!!", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Iltimos Shot nomini kiriting!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void showDialogDelete_for_one(final int id) {
        AlertDialog.Builder dialogDelete = new AlertDialog.Builder(Shot_nomi.this);
        dialogDelete.setTitle("Ogohlantirish!!!");
        dialogDelete.setMessage("Haqiqatdan ham ushbu ma'lumotni o'chirmoqchimisiz?");
        dialogDelete.setPositiveButton("Ha", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                try {
                    AsosiyOyna.sqLiteHelper.deleteData_Shot_nomi(id, "DELETE FROM SHOT_NOMI WHERE Id = ?");
                    Toast.makeText(getApplicationContext(), "Muvaffaqqiyatli o'chirildi!!!", Toast.LENGTH_SHORT).show();
                } catch (Exception error) {
                    error.printStackTrace();
                }
                getdata("SELECT * FROM SHOT_NOMI ORDER BY name ASC");
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


    private void showDialog_Update(Activity activity, final int id, final String kirim_nomi) {

        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.kirim_update);
        dialog.setCancelable(false);
        dialog.setTitle("O'chirish");

        Button btn_kirim_back = (Button) dialog.findViewById(R.id.btn_shot_upd_back);
        Button btn_kirim_update = (Button) dialog.findViewById(R.id.btn_shot_upd);
        TextView txt_kirim_upd = (TextView) dialog.findViewById(R.id.txt_shot_update);
        final EditText edt_kirim_upd = (EditText) dialog.findViewById(R.id.edt_shot_update);

        edt_kirim_upd.setHint("Shot nomi");
        edt_kirim_upd.setText(kirim_nomi);
        edt_kirim_upd.setSelection(kirim_nomi.length());
        txt_kirim_upd.setText(kirim_nomi);

        txt_kirim_upd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edt_kirim_upd.setText(kirim_nomi);
            }
        });

        btn_kirim_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btn_kirim_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (!edt_kirim_upd.getText().toString().equals("")) {
                        AsosiyOyna.sqLiteHelper.updateData_shot_nomi("UPDATE SHOT_NOMI SET name = ? WHERE Id = ?", edt_kirim_upd.getText().toString().trim(), id);

                        Toast.makeText(getApplicationContext(), "Muvaffaqqiyatli o'zgartirildi!", Toast.LENGTH_SHORT).show();
                        getdata("SELECT * FROM SHOT_NOMI ORDER BY name ASC");
                        dialog.dismiss();
                    } else {
                        Toast.makeText(getApplicationContext(), "Iltimos Shot nomini kiriting!", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        int width = activity.getResources().getDisplayMetrics().widthPixels;
        int height = (int) (activity.getResources().getDisplayMetrics().heightPixels);

        dialog.getWindow().setLayout(width, height);
        dialog.show();
    }

//    private void showDialogDelete(final ActionMode mode) {
//        AlertDialog.Builder dialogDelete = new AlertDialog.Builder(Shot_nomi.this);
//        dialogDelete.setTitle(R.string.ogohlantirish);
//        dialogDelete.setMessage("Haqiqatdan ham ushbu ma'lumotni o'chirmoqchimisiz?");
//        dialogDelete.setPositiveButton("Ha", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//                try {
//                    for (String id_shot : kirim_items) {
//                        AsosiyOyna.sqLiteHelper.deleteData_Shot_nomi(Integer.parseInt(id_shot), "DELETE FROM SHOT_NOMI WHERE Id = ?");
//                    }
//                    for (int i = 0; i < gridView.getCount(); i++) {
//                        gridView.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
//                    }
//                    count = 0;
//                    mode.finish();
//
//                    Toast.makeText(getApplicationContext(), "Muvaffaqqiyatli o'chirildi!", Toast.LENGTH_SHORT).show();
//                } catch (Exception error) {
//                    error.printStackTrace();
//                }
//                getdata("SELECT * FROM SHOT_NOMI ORDER BY name ASC");
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
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            do {
                id_soni++;
                int tartib = id_soni;
                String kirim_nomi = cursor.getString(1);
                int id = cursor.getInt(0);
                String bosh_harf = "";
                if (!kirim_nomi.equals("")) {
                    if (kirim_nomi.substring(1, 2).equals("'")) {
                        bosh_harf = kirim_nomi.substring(0, 2).toUpperCase();
                    } else {
                        bosh_harf = kirim_nomi.substring(0, 1).toUpperCase();
                    }
                }
                list.add(new Kirim_nomi_list(kirim_nomi, tartib, bosh_harf, id));
            } while (cursor.moveToNext());
            adapter.notifyDataSetChanged();
            txt_kirim_nomi_yoq.setVisibility(View.GONE);
            return 1;
        } else {
            list.clear();
            adapter.notifyDataSetChanged();
            txt_kirim_nomi_yoq.setVisibility(View.VISIBLE);
            return 0;
        }
    }
}
