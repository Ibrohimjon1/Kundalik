package com.example.ibrohimjon.kundalik;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class Savdo_adapter_yangi extends BaseAdapter {

    private Context context;
    private int layout;
    private ArrayList<Savdo_list_yangi> SavdoList;

    public Savdo_adapter_yangi(Context context, int layout, ArrayList<Savdo_list_yangi> SavdoList) {
        this.context = context;
        this.layout = layout;
        this.SavdoList = SavdoList;
    }

    @Override
    public int getCount() {
        return SavdoList.size();
    }

    @Override
    public Object getItem(int position) {
        return SavdoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder {
        TextView dok_nomlari,txt_id,summa, shot_nomlari;
        View liniya;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {


        View row = view;
        ViewHolder holder = new ViewHolder();

        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout, null);

            holder.dok_nomlari = (TextView) row.findViewById(R.id.DokonNomi_dyn);
            holder.shot_nomlari = (TextView) row.findViewById(R.id.ShotNomi_dyn);
            holder.summa = (TextView) row.findViewById(R.id.Summa_dyn);
            holder.txt_id = (TextView) row.findViewById(R.id.Id_dyn);
            holder.liniya  = (View) row.findViewById(R.id.view_liniyaa);

            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }
        Savdo_list_yangi adapter_yangi = SavdoList.get(position);

        if (adapter_yangi.getDok_visibility() == 0){
            holder.dok_nomlari.setVisibility(View.VISIBLE);
            holder.liniya.setBackgroundColor(Color.BLACK);
            holder.liniya.setVisibility(View.VISIBLE);
            holder.txt_id.setVisibility(View.INVISIBLE);
        }else {
            holder.txt_id.setVisibility(View.GONE);
            holder.dok_nomlari.setVisibility(View.GONE);
            holder.liniya.setVisibility(View.INVISIBLE);
//            holder.liniya.setBackgroundColor(Color.RED);
        }
        holder.dok_nomlari.setText(adapter_yangi.getDokNom());
        holder.shot_nomlari.setText(adapter_yangi.getShotNom());
        holder.txt_id.setText(String.valueOf(adapter_yangi.getId()));
        holder.summa.setText(adapter_yangi.getSumma());

        return row;
    }
}
