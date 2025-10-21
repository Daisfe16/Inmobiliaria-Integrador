package com.example.inmobiliaria.ui.inmueble;

import android.app.Application;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.inmobiliaria.model.Inmueble;
import com.example.inmobiliaria.ui.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalleDeInmuebleViewModel extends AndroidViewModel {

    private final MutableLiveData<Inmueble> mInmueble = new MutableLiveData<>();

    public LiveData<Inmueble> getmInmueble() {
        return mInmueble;
    }



    public DetalleDeInmuebleViewModel(@NonNull Application application) {
        super(application);
    }

    public void obtenerInmueble(Bundle bundle) {
        Inmueble inmueble = (Inmueble) bundle.getSerializable("inmueble");
        if(inmueble != null) {
            mInmueble.postValue(inmueble);
        }

    }
    public void actualizarInmueble(Boolean disponible) {
        Inmueble inmueble = new Inmueble();
        inmueble.setDisponible(disponible);
        inmueble.setIdInmueble(mInmueble.getValue().getIdInmueble());
        String token = ApiClient.obtenerToken(getApplication());
        ApiClient.InmobiliariaService api = ApiClient.getApiInmobiliaria();
        Call<Inmueble> llamada = api.actualizarInmueble(token, inmueble);
        llamada.enqueue(new Callback<Inmueble>() {
            @Override
            public void onResponse(Call<Inmueble> call, Response<Inmueble> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getApplication(), "Inmueble actualizado correctamente", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplication(), "No se pudo actualizar el inmueble: " + response.message(), Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<Inmueble> call, Throwable t) {
                Toast.makeText(getApplication(), "Error en servidor: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}