package com.universidadeafit.appeafit.Model;

import java.io.Serializable;

public class Solicitud implements Serializable {
    String pregunta;
    String motivo;
    int photoId;
    String observacion;
    String fecha;
    String respuesta;

    public Solicitud(String pregunta, String motivo, int photoId, String observacion, String fecha,String respuesta) {
        this.pregunta = pregunta;
        this.motivo = motivo;
        this.photoId = photoId;
        this.observacion = observacion;
        this.fecha = fecha;
        this.respuesta = respuesta;
    }

    public String getPregunta()    {
        return pregunta;
    }

    public String getMotivo()    {
        return motivo;
    }

    public String getObservacion()    {
        return observacion;
    }

    public String getFecha() {
        return fecha;
    }

    public int getPhoto() {
        return photoId;
    }

    public String getRespuesta()    {
        return respuesta;
    }

}
