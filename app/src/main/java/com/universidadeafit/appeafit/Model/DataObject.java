package com.universidadeafit.appeafit.Model;

public class DataObject {
    String placa;
    String marca;
    int photoId;
    String tipo;
    String color;

    public DataObject(String placa, String marcaRef, int photoId, String tipo, String color) {
        this.placa = placa;
        this.marca = marcaRef;
        this.photoId = photoId;
        this.tipo = tipo;
        this.color = color;
    }

    public String getPlaca()    {
        return placa;
    }

    public String getMarca()    {
        return marca;
    }

    public String getTipo()    {
        return tipo;
    }

    public String getColor() {
        return color;
    }

    public int getPhoto() {
        return photoId;
    }

}
