package com.universidadeafit.appeafit.Model;

/**
 * Created by LUISM on 01/03/2017.
 */

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Intenciones implements Serializable {

    @SerializedName("usuario")
    private String usuario;

    @SerializedName("intencion")
    private String intencion;

    @SerializedName("fecha")
    private String fecha;


    public Intenciones(String usuario, String intencion, String fecha) {
        this.usuario = usuario;
        this.intencion = intencion;
        this.fecha = fecha;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getIntencion() {
        return intencion;
    }

    public void setIntencion(String intencion) {
        this.intencion = intencion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}