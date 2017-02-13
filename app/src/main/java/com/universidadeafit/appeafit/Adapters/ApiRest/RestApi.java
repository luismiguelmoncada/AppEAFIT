package com.universidadeafit.appeafit.Adapters.ApiRest;



import com.universidadeafit.appeafit.Model.Usuario;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by LUISM on 30/12/2016.
 */

public interface RestApi {

    //  "/api/retrofit_users"

    @GET("/Retrofit-Prueba/getData.php")
    Call<List<Usuario>> getUsers();

    @POST("/Retrofit-Prueba/User_Registration.php")
    Call<ServerResponse> createUser(@Body Usuario Usuario);

    @POST("/Retrofit-Prueba/User_Login.php")
    Call<ServerResponse> obtenerUsuario(@Body Usuario Usuario);
}
