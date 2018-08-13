package com.example.ibrohimjon.kundalik;


import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.widget.Toast.LENGTH_SHORT;

public class Qarzlarim_sana extends AppCompatActivity {

    public static Qarzdorlar_sana_adapter adapter = null;
    ListView listView;
    public static ArrayList<Qarzdorlar_sana_list> list;
    EditText edt_tanish;
    ImageView btn_qarz_add;
    ImageButton btn_qarz_filter;
    int qidirish = 0;
    int a = 2;
    int[] images_btn = {R.drawable.sort_asc, R.drawable.sort_desc};
    public static TextView txt_kirim_nomi_yoq;
    public static String qarzim_ismi = "";

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kirim_nomi_activity);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        list = new ArrayList<>();
        listView = (ListView) findViewById(R.id.gview_kirim_nomi);
        edt_tanish = (EditText) findViewById(R.id.edt_kirim_nomi);
        btn_qarz_add = (ImageView) findViewById(R.id.btn_kirim_add_p0);
        btn_qarz_filter = (ImageButton) findViewById(R.id.btn_kirim_filter);
        txt_kirim_nomi_yoq = (TextView) findViewById(R.id.txt_kirim_nomi_yoq);

        assert txt_kirim_nomi_yoq != null;
        txt_kirim_nomi_yoq.setText("Qarzlarimda hech nima yo'q");

//        btn_qarz_add.setVisibility(View.GONE);
        edt_tanish.setHint("Qidirish...");

        btn_qarz_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Qarzlarim_sana.this, Qarzlarim_add.class);
                startActivity(intent);
            }
        });

        adapter = new Qarzdorlar_sana_adapter(Qarzlarim_sana.this, R.layout.savdo_royhat_items, list);
        listView.setAdapter(adapter);

        String sql = "SELECT DISTINCT tanish_ismi FROM QARZLARIM ORDER BY tanish_ismi ASC";
        Get_data(sql);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView txt_tanish = (TextView) view.findViewById(R.id.txtSavdoListDokNom);
                String data = txt_tanish.getText().toString().trim();
                Intent intent = new Intent(Qarzlarim_sana.this, Qarzlarim_royh.class);
                qarzim_ismi = data;
//                intent.putExtra("tanish", data);
                startActivity(intent);
            }
        });

        btn_qarz_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (a == 1) {
                    String sql = "SELECT DISTINCT tanish_ismi FROM QARZLARIM ORDER BY tanish_ismi ASC";
                    Get_data(sql);
                    btn_qarz_filter.setImageResource(images_btn[1]);
                    a = 2;
                } else if (a == 2) {
                    String sql = "SELECT DISTINCT tanish_ismi FROM QARZLARIM ORDER BY tanish_ismi DESC";
                    Get_data(sql);
                    btn_qarz_filter.setImageResource(images_btn[0]);
                    a = 1;
                }
            }
        });

        edt_tanish.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (!edt_tanish.getText().toString().equals("")) {
                    String sql = "SELECT DISTINCT tanish_ismi FROM QARZLARIM WHERE tanish_ismi LIKE '" + edt_tanish.getText().toString() + "%' ORDER BY tanish_ismi ASC";
                    int qaytdi = Get_filter_data(sql);
                    if (qaytdi == 0) {
                        String sql1 = "SELECT DISTINCT tanish_ismi FROM QARZLARIM WHERE tanish_ismi LIKE '%" + edt_tanish.getText().toString() + "%' ORDER BY tanish_ismi ASC";
                        int aa = Get_filter_data(sql1);
                        if (aa == 0) {
                            txt_kirim_nomi_yoq.setText("Qarzlarimda bunday odam yo'q");
                            txt_kirim_nomi_yoq.setVisibility(View.VISIBLE);
                        } else {
                            txt_kirim_nomi_yoq.setVisibility(View.GONE);
                        }
                    } else {
                        txt_kirim_nomi_yoq.setVisibility(View.GONE);
                    }
                    qidirish = 1;
                } else {
                    String sql = "SELECT DISTINCT tanish_ismi FROM QARZLARIM ORDER BY tanish_ismi ASC";
                    Get_data(sql);
                    qidirish = 0;
                    txt_kirim_nomi_yoq.setVisibility(View.GONE);
                    txt_kirim_nomi_yoq.setText("Qarzlarimda hech nima yo'q");

                }
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, final View view, final int position, long id) {

                final LinearLayout layout = (LinearLayout) view.findViewById(R.id.layout_qarz_items);
                layout.setBackgroundColor(Color.rgb(218, 112, 214));

                CharSequence[] items = {"O'zgartirish", "O'chirish"};
                AlertDialog.Builder dialog = new AlertDialog.Builder(Qarzlarim_sana.this);

                dialog.setTitle("Tanlang");
                dialog.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        TextView txt_tanish = (TextView) view.findViewById(R.id.txtSavdoListDokNom);

                        if (item == 0) {
                            //Update
                            Toast.makeText(getApplicationContext(), "Bu yerda o'zgartirish mumkin emas!", LENGTH_SHORT).show();
//                            showDialog_Update(Savdo_sana_yangi.this, Integer.parseInt(id), txt_kirim.getText().toString());
                            layout.setBackgroundColor(Color.TRANSPARENT);
                        } else {
                            // delete
                            showDialogDelete_for_one(txt_tanish.getText().toString().trim());
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

    @Override
    protected void onResume() {
        super.onResume();
        String sql = "SELECT DISTINCT tanish_ismi FROM QARZLARIM ORDER BY tanish_ismi ASC";
        Get_data(sql);
    }

    private void showDialogDelete_for_one(final String tanish_ismi) {
        AlertDialog.Builder dialogDelete = new AlertDialog.Builder(Qarzlarim_sana.this);
        dialogDelete.setTitle("Ogohlantirish!!!");
        dialogDelete.setMessage("Haqiqatdan ham ushbu ma'lumotni o'chirmoqchimisiz?");
        dialogDelete.setPositiveButton("Ha", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                try {
                    AsosiyOyna.sqLiteHelper.insertData_Shot_nomi(tanish_ismi, "DELETE FROM QARZLARIM WHERE tanish_ismi = ?");
                    Toast.makeText(getApplicationContext(), "Muvaffaqqiyatli o'chirildi!!!", LENGTH_SHORT).show();
                } catch (Exception error) {
                    error.printStackTrace();
                }
                String sql = "SELECT DISTINCT tanish_ismi FROM QARZLARIM ORDER BY tanish_ismi ASC";
                Get_data(sql);
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

    public static void Get_data(String sql) {

        Cursor cursor = AsosiyOyna.sqLiteHelper.getData(sql);

        list.clear();
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            do {
                String tanish = cursor.getString(0);
                tanish = tanish.replace("'", "''");
                int summa_yigindi = 0;
                int summa_dollar = 0;

                String sql1 = "SELECT * FROM QARZLARIM WHERE tanish_ismi LIKE '" + tanish + "'";
                Cursor cursor1 = AsosiyOyna.sqLiteHelper.getData(sql1);
                if (cursor1.getCount() != 0) {
                    cursor1.moveToFirst();
                    do {
                        if (cursor1.getString(2).endsWith("$")) {
                            summa_dollar += cursor1.getInt(4);
                        } else {
                            summa_yigindi = summa_yigindi + cursor1.getInt(4);
                        }
                    } while (cursor1.moveToNext());

                    String bosh_harf = "";
                    if (!tanish.equals("")) {
                        if (tanish.substring(1, 2).equals("'")) {
                            bosh_harf = tanish.substring(0, 2).toUpperCase();
                        } else {
                            bosh_harf = tanish.substring(0, 1).toUpperCase();
                        }
                    }
                    String sum = String.valueOf(summa_yigindi);
                    sum = NumberTextWatcherForThousand.getDecimalFormattedString(sum);
                    String sum_dol = String.valueOf(summa_dollar);
                    sum_dol = NumberTextWatcherForThousand.getDecimalFormattedString(sum_dol);
                    list.add(new Qarzdorlar_sana_list(tanish.replace("''", "'"), sum + "\n" + sum_dol+"$", bosh_harf, null, 0));
                    adapter.notifyDataSetChanged();
                }
            } while (cursor.moveToNext());
            txt_kirim_nomi_yoq.setVisibility(View.GONE);
        } else {
            list.clear();
            adapter.notifyDataSetChanged();
            txt_kirim_nomi_yoq.setText("Qarzlarimda hech nima yo'q");
            txt_kirim_nomi_yoq.setVisibility(View.VISIBLE);
//            Toast.makeText(getApplicationContext(), "Qarzlarimda hech nima yo'q!", LENGTH_SHORT).show();
        }

    }

    public int Get_filter_data(String sql) {

        Cursor cursor = AsosiyOyna.sqLiteHelper.getData(sql);

        int qaytar;
        list.clear();
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            do {
                String tanish = cursor.getString(0);
                tanish = tanish.replace("'", "''");
                int summa_yigindi = 0;
                int summa_dollar = 0;

                String sql1 = "SELECT * FROM QARZLARIM WHERE tanish_ismi LIKE '" + tanish + "'";
                Cursor cursor1 = AsosiyOyna.sqLiteHelper.getData(sql1);
                if (cursor1.getCount() != 0) {
                    cursor1.moveToFirst();
                    do {
                        if (cursor1.getString(2).endsWith("$")) {
                            summa_dollar += cursor1.getInt(4);
                        } else {
                            summa_yigindi = summa_yigindi + cursor1.getInt(4);
                        }
                    } while (cursor1.moveToNext());

                    String bosh_harf;
                    if (tanish.substring(1, 2).equals("'")) {
                        bosh_harf = tanish.substring(0, 2).toUpperCase();
                    } else {
                        bosh_harf = tanish.substring(0, 1).toUpperCase();
                    }
                    String sum = String.valueOf(summa_yigindi);
                    sum = NumberTextWatcherForThousand.getDecimalFormattedString(sum);
                    String sum_dol = String.valueOf(summa_dollar);
                    sum_dol = NumberTextWatcherForThousand.getDecimalFormattedString(sum_dol);
                    list.add(new Qarzdorlar_sana_list(tanish.replace("''", "'"), sum + "\n" + sum_dol+"$", bosh_harf, null, 0));
                    adapter.notifyDataSetChanged();
                }
            } while (cursor.moveToNext());
            qaytar = 1;
        } else {
            list.clear();
            adapter.notifyDataSetChanged();
            qaytar = 0;
        }
        return qaytar;
    }
}
