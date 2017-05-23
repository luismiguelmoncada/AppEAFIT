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

    @SerializedName("pregunta")
    private String pregunta;

    @SerializedName("respuesta")
    private String respuesta;


    public Intenciones(String usuario, String intencion, String fecha,String pregunta, String respuesta) {
        this.usuario = usuario;
        this.intencion = intencion;
        this.fecha = fecha;
        this.pregunta = pregunta;
        this.respuesta = respuesta;
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

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }
}