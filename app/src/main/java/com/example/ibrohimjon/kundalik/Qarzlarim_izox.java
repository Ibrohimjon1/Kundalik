package com.example.ibrohimjon.kundalik;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Ibrohimjon on 29.06.2018.
 */

public class Qarzlarim_izox extends AppCompatActivity {

    ImageView imageView_add;
    TextView txt_id, txt_sana, txt_summa, txt_ism, txt_izox;
    ArrayAdapter<String> adapter_auto;
    ArrayList<String> list_auto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qarzdorlar_izox);

        imageView_add = (ImageView) findViewById(R.id.btn_qarz_izox_ozgartir);
        txt_id = (TextView) findViewById(R.id.txt_qarz_izox_id);
        txt_sana = (TextView) findViewById(R.id.txt_qarz_izox_sana);
        txt_summa = (TextView) findViewById(R.id.txt_qarz_izox_summa);
        txt_ism = (TextView) findViewById(R.id.txt_qarz_izox_ism);
        txt_izox = (TextView) findViewById(R.id.txt_qarz_izox_izox);

        Cursor cursor = AsosiyOyna.sqLiteHelper.getData("SELECT name FROM TANISHLAR");
        list_auto = new ArrayList<>();
        list_auto.clear();
        while (cursor.moveToNext()) {
            String name = cursor.getString(0);
            list_auto.add(name);
        }
        adapter_auto = new ArrayAdapter<>(this, android.R.layout.select_dialog_item, list_auto);

        Intent intent = getIntent();
        String id = intent.getExtras().getString("id");
        Cursor cursor1 = AsosiyOyna.sqLiteHelper.getData("SELECT * FROM QARZLARIM WHERE Id LIKE '" + id + "'");
        if (cursor1.getCount() != 0) {
            cursor1.moveToFirst();
            String tanish = cursor1.getString(1);
            String summa = cursor1.getString(2);
            String sana = cursor1.getString(3);
            String izox = cursor1.getString(6);
            txt_id.setText(id);
            txt_summa.setText(summa);
            txt_ism.setText(tanish);
            txt_izox.setText(izox);
            txt_sana.setText(sana);
        }

        imageView_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence[] items = {"O'zgartirish", "O'chirish"};
                AlertDialog.Builder dialog = new AlertDialog.Builder(Qarzlarim_izox .this);

                final int ozg_id = Integer.valueOf(txt_id.getText().toString());
                dialog.setTitle("Tanlang");
                dialog.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {

                        if (item == 0) {
                            //Update
                            String ismi = txt_ism.getText().toString();
                            String izox = txt_izox.getText().toString();
                            String summa = txt_summa.getText().toString();

                            //show dialog update at here
                            showDialogUpdate(String.valueOf(ozg_id), ismi, summa, txt_sana.getText().toString(), izox);
                        } else {
                            // delete
                            showDialogDelete(ozg_id);
                        }
                    }
                });
                dialog.show();
            }
        });

    }

    private void showDialogUpdate(final String id, final String tanish_ismi, final String summa, final String sana, final String izox) {

        final Dialog dialog = new Dialog(Qarzlarim_izox .this);
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
        TextView txt_sum = (TextView) dialog.findViewById(R.id.txt_update_summa);
        final AutoCompleteTextView auto_tanish = (AutoCompleteTextView) dialog.findViewById(R.id.qarz_update_auto_tanish);

        auto_tanish.setThreshold(1);
        auto_tanish.setAdapter(adapter_auto);

        txt_sum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edt_summa.getText().toString().equals("") && !edt_summa.getText().toString().endsWith("$")){
                    edt_summa.setText(String.format("%s$", edt_summa.getText().toString()));
                }
            }
        });

        edt_summa.addTextChangedListener(new NumberTextWatcherForThousand(edt_summa,1,null));
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
                        // Bazaga malumot yozish
                        if (summa.endsWith("$")){
                            summa = NumberTextWatcherForThousand.getDecimalFormattedString(summa.replaceAll("[^0-9]", "")) + "$";
                        } else {
                            summa = NumberTextWatcherForThousand.getDecimalFormattedString(summa.replaceAll("[^0-9]", ""));
                        }
                        String sql = "UPDATE QARZLARIM SET tanish_ismi = ?, summa = ?, haq_summa = ?, izox = ? WHERE id = ?";
                        AsosiyOyna.sqLiteHelper.updateData_qarz(sql, tanishismi, summa, haq_summa, izox, id);
                        Toast.makeText(getApplicationContext(), "Muvaffaqqiyatli o'zgartirildi!", Toast.LENGTH_SHORT).show();
                        txt_ism.setText(tanish_ismi);
                        txt_izox.setText(izox);
                        txt_summa.setText(summa);
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
        int width = Qarzlarim_izox .this.getResources().getDisplayMetrics().widthPixels;
        int height = (int) (Qarzlarim_izox .this.getResources().getDisplayMetrics().heightPixels);
        dialog.getWindow().setLayout(width, height);
        dialog.show();

    }

    private void showDialogDelete(final int deleting) {
        final AlertDialog.Builder dialogDelete = new AlertDialog.Builder(Qarzlarim_izox .this);
        dialogDelete.setTitle(R.string.ogohlantirish);
        dialogDelete.setMessage("Haqiqatdan ham ushbu ma'lumotlarni o'chirmoqchimisiz?");
        dialogDelete.setPositiveButton("Ha", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                try {
                    AsosiyOyna.sqLiteHelper.deleteData_Shot_nomi(deleting, "DELETE FROM QARZLARIM WHERE Id = ?");
                    Toast.makeText(getApplicationContext(), "Muvaffaqqiyatli o'chirildi!", Toast.LENGTH_SHORT).show();
                } catch (Exception error) {
                    error.printStackTrace();
                    Toast.makeText(getApplicationContext(), "O'chirishda xatolik bo'ldi!", Toast.LENGTH_SHORT).show();
                }
                finish();

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

}
