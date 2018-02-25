package com.example.jdemu.pruebaselena;

import android.graphics.Bitmap;

/**
 * Created by jdemu on 25/02/2018.
 */

public class productos {
    String nombre;
    String cantidad;
    Bitmap foto;

    public productos(String nombre, String cantidad, Bitmap foto) {
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.foto = foto;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCantidad() {
        return cantidad;
    }

    public Bitmap getFoto() {
        return foto;
    }
}
