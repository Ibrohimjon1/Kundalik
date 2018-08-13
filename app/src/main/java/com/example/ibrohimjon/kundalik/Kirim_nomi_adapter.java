package com.example.ibrohimjon.kundalik;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class Kirim_nomi_adapter extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<Kirim_nomi_list> Kirim_list;

    public Kirim_nomi_adapter(Context context, int layout, ArrayList<Kirim_nomi_list> Kirim_list) {
        this.context = context;
        this.layout = layout;
        this.Kirim_list = Kirim_list;
    }

    @Override
    public int getCount() {
        return Kirim_list.size();
    }

    @Override
    public Object getItem(int position) {
        return Kirim_list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder {
        TextView txt_kirim_tartib, txt_kirim_name, txt_kirim_harf, txt_kirim_id;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        View row = view;
        ViewHolder holder = new ViewHolder();

        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout, null);

            holder.txt_kirim_id = (TextView) row.findViewById(R.id.txt_kirim_item_id);
            holder.txt_kirim_tartib = (TextView) row.findViewById(R.id.txt_kirim_tartib);
            holder.txt_kirim_harf = (TextView) row.findViewById(R.id.txt_kirim_harf);
            holder.txt_kirim_name = (TextView) row.findViewById(R.id.txt_kirim_name);

            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        Kirim_nomi_list kirim_nomi_list = Kirim_list.get(position);

        holder.txt_kirim_name.setText(kirim_nomi_list.getKirim_nomi());
        holder.txt_kirim_harf.setText(kirim_nomi_list.getBosh_harf());
        holder.txt_kirim_tartib.setText(String.valueOf(kirim_nomi_list.getTartib()));
        holder.txt_kirim_id.setText(String.valueOf(kirim_nomi_list.getId()));

        return row;
    }
}
