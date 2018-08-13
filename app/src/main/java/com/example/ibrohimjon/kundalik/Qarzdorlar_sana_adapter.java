package com.example.ibrohimjon.kundalik;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class Qarzdorlar_sana_adapter extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<Qarzdorlar_sana_list> Qarz_list;

    public Qarzdorlar_sana_adapter(Context context, int layout, ArrayList<Qarzdorlar_sana_list> Qarz_list) {
        this.context = context;
        this.layout = layout;
        this.Qarz_list = Qarz_list;
    }

    @Override
    public int getCount() {
        return Qarz_list.size();
    }

    @Override
    public Object getItem(int position) {
        return Qarz_list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder {
        TextView txt_summa, txt_summa_dollar, txt_tanish, txt_bosh_harf, txt_sana, txt_qarz_id;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        View row = view;
        ViewHolder holder = new ViewHolder();

        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout, null);

            holder.txt_qarz_id = (TextView) row.findViewById(R.id.txtSavdoListId);
            holder.txt_summa = (TextView) row.findViewById(R.id.txtSavdoListSumma);
            holder.txt_tanish = (TextView) row.findViewById(R.id.txtSavdoListDokNom);
            holder.txt_bosh_harf = (TextView) row.findViewById(R.id.txtSavdo_list_bosh_harf);
            holder.txt_sana = (TextView) row.findViewById(R.id.txtSavdoListSana);

            holder.txt_tanish.setTextSize(30);

            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        Qarzdorlar_sana_list qarzdorlar_list= Qarz_list.get(position);

        holder.txt_tanish.setText(qarzdorlar_list.getTanish_ismi());
        holder.txt_summa.setText(qarzdorlar_list.getSumma());
        holder.txt_sana.setText(qarzdorlar_list.getSana());
        holder.txt_sana.setVisibility(View.GONE);
        holder.txt_qarz_id.setText(String.valueOf(qarzdorlar_list.getId()));
        holder.txt_bosh_harf.setText(qarzdorlar_list.getBosh_harf());

        return row;
    }
}
