package com.example.inmobiliaria.ui.request;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
public class ApiClient {
    public static  final String URLBASE = "https://inmobiliariaulp-amb5hwfqaraweyga.canadacentral-01.azurewebsites.net/";

    public static InmobiliariaService getApiInmobiliaria(){
        Gson gson = new GsonBuilder()
                //.setLenient()
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
    }
}
