package com.example.ibrohimjon.kundalik;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

public class SQLiteHelper extends SQLiteOpenHelper {

    static final int DB_VERSION = 2;

    public SQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public void queryData(String sql) {
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }

    public void insertData_Shot_nomi(String name, String sql) {
        SQLiteDatabase database = getWritableDatabase();

        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1, name.trim());
        statement.executeInsert();

    }

    public void updateData_shot_nomi(String sql, String summa, int id) {
        SQLiteDatabase database = getWritableDatabase();

        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1, summa.trim());
        statement.bindDouble(2, (double) id);

        statement.execute();
        database.close();
    }

    public void deleteData_Shot_nomi(int id, String sql) {
        SQLiteDatabase database = getWritableDatabase();

        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindDouble(1, (double) id);
        statement.execute();
        database.close();
    }

    public void updateData_savdo(String sql, int id, String dok_nomi, String shot_nomi, String summa) {
        SQLiteDatabase database = getWritableDatabase();

        SQLiteStatement statement = database.compileStatement(sql);

        statement.bindString(1, dok_nomi.trim());
        statement.bindString(2, shot_nomi.trim());
        statement.bindString(3, summa.trim());
        statement.bindDouble(4, (double) Integer.parseInt(summa.replaceAll("[^0-9]", "")));
        statement.bindDouble(5, (double) id);

        statement.execute();
        database.close();
    }

    public void insertData_qarzdor(String tanish, String summa, String sana, int haq_summa, String sana_sartir, String izox, String sql) {
        SQLiteDatabase database = getWritableDatabase();

        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1, tanish.trim());
        statement.bindString(2, summa.trim());
        statement.bindString(3, sana.trim());
        statement.bindDouble(4, (double) haq_summa);
        statement.bindString(5, sana_sartir.trim());
        statement.bindString(6, izox.trim());

        statement.executeInsert();
    }

    public void update_sana(String sana, String sana_solish, String sql, String eski_sana) {
        SQLiteDatabase database = getWritableDatabase();

        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1, sana.trim());
        statement.bindString(2, sana_solish.trim());
        statement.bindString(3, eski_sana.trim());

        statement.executeInsert();
    }

    public void insertDataSavdo(String dokNom, String shotNom, String price, String sana, int haq_summa, String sql, String sana_solish) {
        SQLiteDatabase database = getWritableDatabase();

        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1, dokNom.trim());
        statement.bindString(2, shotNom.trim());
        statement.bindString(3, price.trim());
        statement.bindString(4, sana.trim());
        statement.bindDouble(5, (double) haq_summa);
        statement.bindString(6, sana_solish.trim());

        statement.executeInsert();
    }

    public void updateData_qarz(String sql, String tanish_ismi, String summa, int haq_summa, String izox, String id) {
        SQLiteDatabase database = getWritableDatabase();

        SQLiteStatement statement = database.compileStatement(sql);

        statement.bindString(1, tanish_ismi.trim());
        statement.bindString(2, summa.trim());
        statement.bindDouble(3, (double) haq_summa);
        statement.bindString(4, izox.trim());
        statement.bindString(5, id);

        statement.execute();
        database.close();
    }

    public Cursor getData(String sql) {
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql, null);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        onUpgrade(db,0,DB_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

//        if (oldVersion < 2){
//            db.execSQL("ALTER TABLE QARZLARIM ADD COLUMN izox VARCHAR DEFAULT 'Izox yoq'" );
//            db.execSQL("ALTER TABLE QARZDORLAR ADD COLUMN izox VARCHAR DEFAULT 'Izox yoq'" );
//        }
    }
}
