package com.example.inmobiliaria.ui.request;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.inmobiliaria.model.Inmueble;
import com.example.inmobiliaria.model.Propietario;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public class ApiClient {
    public static  final String URLBASE = "https://inmobiliariaulp-amb5hwfqaraweyga.canadacentral-01.azurewebsites.net/";

    public static InmobiliariaService getApiInmobiliaria(){
        Gson gson = new GsonBuilder()
                .setStrictness(com.google.gson.Strictness.LENIENT)
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URLBASE)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        return retrofit.create(InmobiliariaService.class);
    }
    public static void guardarToken(Context context, String token) {
        SharedPreferences preferences = context.getSharedPreferences("token", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("token", token);
        editor.apply();
    }
    public static String obtenerToken(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("token", Context.MODE_PRIVATE);
        return preferences.getString("token", "");
    }


    public interface InmobiliariaService{
        @FormUrlEncoded
        @POST("api/propietarios/login")
        Call<String> login(@Field("usuario") String usuario, @Field("clave") String clave);

        @GET("api/Propietarios")
        Call<Propietario> obtenerPropietario(@Header("Authorization") String token);
        @PUT("api/Propietarios/actualizar")
        Call<Propietario> actualizarPropietario(@Header("Authorization") String token, @Body Propietario propietario);

        @GET("api/Inmuebles")
        Call<List<Inmueble>> obtenerInmuebles(@Header("Authorization") String token);

        @FormUrlEncoded
        @PUT("api/Propietarios/changePassword")
        Call<Void> cambiarClave(
                @Header("Authorization") String token,
                @Field("currentPassword") String actual,
                @Field("newPassword") String nueva
        );
        @PUT("api/Inmuebles/actualizar")
        Call<Inmueble> actualizarInmueble(
                @Header ("Authorization") String token,
                @Body Inmueble inmueble);

    }
}
