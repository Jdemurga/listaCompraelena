package com.example.jdemu.pruebaselena;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by jdemu on 25/02/2018.
 */

public class  adaptadorlista extends BaseAdapter {
    protected Activity activity;
    protected ArrayList<productos> items;

    public adaptadorlista(Activity activity, ArrayList<productos> items) {
        this.activity = activity;
        this.items = items;
    }
    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int arg0) {
        return items.get(arg0);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        //Asociamos el layout de la lista que hemos creado
        if (convertView == null) {
            LayoutInflater inf = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inf.inflate(R.layout.itemlista, null);
        }

        // Creamos un objeto directivo
        productos dir = items.get(position);
        //Rellenamos la fotografía
        ImageView foto = (ImageView) v.findViewById(R.id.foto);
        foto.setImageBitmap(dir.getFoto());
        //Rellenamos el nombre
        TextView nomb = (TextView) v.findViewById(R.id.nombre);
        TextView cant = (TextView) v.findViewById(R.id.cantidad);
        nomb.setText(dir.getNombre());
        cant.setText(dir.getCantidad());
        //Rellenamos el cargo


        // Retornamos la vista
        return v;
    }

    @Nullable
    @Override
    public CharSequence[] getAutofillOptions() {
        return new CharSequence[0];
    }

}
