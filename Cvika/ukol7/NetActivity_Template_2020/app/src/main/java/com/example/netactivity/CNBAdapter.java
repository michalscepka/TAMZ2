package com.example.netactivity;

import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

// TODO 1. - Aktualne funguje aplikace tak, ze se nacte a zobrazi v listu kody men ze souboru https://www.cnb.cz/cs/financni_trhy/devizovy_trh/kurzy_devizoveho_trhu/denni_kurz.xml
// TODO 1. - Cilem je upravit zobrazovani tak aby se zobrazovalo: vlajka statu, nazev meny, kod statu, nazev statu a kurz.
// TODO 1. - Kazdy radek listu je reprezentovan instancemi tridy Entry, v tento okamzik je ve tride Entry udaj pouze pro kod zeme
// TODO 1. - V nastaveni aplikace muzete nastavit jestli se maji data stahovat pouze pres wifi
//  |-----------|----------------------------------|
//  | ImageView |          NAZEV MENY              |  
//  |           |Kod                               | 
//  |  vlajka   |Stat                              |
//  |   statu   |Kurz                              |
//  |           |                                  |
//  |----------------------------------------------|
//

public class CNBAdapter extends ArrayAdapter<Entry>{

    Context context;
    int layoutResourceId;   
    List<Entry> data = null;
    
   
    public CNBAdapter(Context context, int layoutResourceId, List<Entry> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        EntryHolder holder = null;
       
        if(row == null)
        {
        	LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );      	
            row = inflater.inflate(layoutResourceId, parent, false);
           
            holder = new EntryHolder();
            holder.txtKod = row.findViewById(R.id.txtKod);
            holder.txtMena = row.findViewById(R.id.txtMena);
            holder.txtZeme = row.findViewById(R.id.txtZeme);
            holder.txtKurz = row.findViewById(R.id.txtKurz);
            holder.imgFlag = row.findViewById(R.id.imgFlag);

            row.setTag(holder);
        }
        else
        {
            holder = (EntryHolder)row.getTag();
        }
       
        Entry entry = data.get(position);
        holder.txtKod.setText(entry.kod);
        holder.txtMena.setText(entry.mena);
        holder.txtZeme.setText(entry.zeme);
        holder.txtKurz.setText(entry.kurz);
        holder.imgFlag.setImageResource(context.getResources().getIdentifier("flag_" + entry.kod.toLowerCase(), "drawable", context.getPackageName()));

        return row;
    }
   
    static class EntryHolder
    {
        TextView txtKod;
        TextView txtMena;
        TextView txtZeme;
        TextView txtKurz;
        ImageView imgFlag;
    }
}
