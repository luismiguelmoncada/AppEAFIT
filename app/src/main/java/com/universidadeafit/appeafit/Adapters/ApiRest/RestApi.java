package com.universidadeafit.appeafit.Adapters.ApiRest;



import com.universidadeafit.appeafit.Model.Intenciones;
import com.universidadeafit.appeafit.Model.Solicitud;
import com.universidadeafit.appeafit.Model.Usuario;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by LUISM on 30/12/2016.
 */

public interface RestApi {

    //  "/api/retrofit_users"

    @POST("/Retrofit-Prueba/User_Registration.php")
    Call<ServerResponse> createUser(@Body Usuario Usuario);

    @POST("/Retrofit-Prueba/User_Update.php")
    Call<ServerResponse> insertarTipoUser(@Body Usuario Usuario);

    @POST("/Retrofit-Prueba/User_PreguntaInsertar.php")
    Call<ServerResponse> insertarPregunta(@Body Solicitud Solicitud);

    @POST("/Retrofit-Prueba/User_IntencionesInsertar.php")
    Call<ServerResponse> insertarIntencion(@Body Intenciones intencion);

    @POST("/Retrofit-Prueba/User_Calificacion.php")
    Call<ServerResponse> insertarCalificacion(@Body Usuario Usuario);

    @GET("/Retrofit-Prueba/getData.php")
    Call<List<Usuario>> getUsers(
            @Query("email") String email,
            @Query("password") String password
    );

    @GET("/Retrofit-Prueba/Preguntas_Consultar.php")
    Call<List<Solicitud>> getPreguntas(@Query("email") String email);

    @GET("/Retrofit-Prueba/getDataRecordarCuenta.php")
    Call<List<Usuario>> getRecordarUsers(@Query("email") String email);

    @GET("/Retrofit-Prueba/getGrafico.php")
    Call<List<Solicitud>> getGrafico(@Query("email") String email);


    @GET("/Retrofit-Prueba/getIntenciones.php")
    Call<List<Intenciones>> getIntenciones(@Query("email") String email);

    @GET("/Retrofit-Prueba/getCalificaciones.php")
    Call<List<Usuario>> getCalificaciones(@Query("email") String email);

    @POST("/Retrofit-Prueba/User_Login.php")
    Call<ServerResponse> obtenerUsuario(@Body Usuario Usuario);
}
