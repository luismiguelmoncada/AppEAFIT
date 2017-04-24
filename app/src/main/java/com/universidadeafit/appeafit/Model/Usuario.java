package com.universidadeafit.appeafit.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by LUISM on 29/12/2016.
 */

public class Usuario {
    @SerializedName("name")
    private String name;

    @SerializedName("username")
    private String username;

    @SerializedName("password")
    private String password;

    @SerializedName("email")
    private String email;

    @SerializedName("rol")
    private String rol;

    @SerializedName("codigo")
    private String codigo;

    @SerializedName("identificacion")
    private String identificacion;

    public Usuario() {

    }
    public Usuario(String name, String username, String password, String email,String rol,String codigo,String identificacion) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
        this.rol = rol;
        this.codigo = codigo;
        this.identificacion = identificacion;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }
}
