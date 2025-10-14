package com.example.inmobiliaria.ui.perfil;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.inmobiliaria.model.Propietario;
import com.example.inmobiliaria.ui.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;

public class PerfilViewModel extends AndroidViewModel {

    private MutableLiveData<Propietario> mp = new MutableLiveData<>();
    private MutableLiveData<Boolean> mutableEstado = new MutableLiveData<>();
    private MutableLiveData<String> mutableNombreBt = new MutableLiveData<>();

    public PerfilViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Propietario> getPropietario() {
        return mp;
    }

    public LiveData<Boolean> getEstado() {
        return mutableEstado;
    }

    public LiveData<String> getNombreBt() {
        return mutableNombreBt;
    }

    public void guardar(String btEditar, String dni, String nombre, String apellido, String email, String telefono) {
        if (btEditar.equalsIgnoreCase("Editar")) {
            mutableEstado.setValue(true);
            mutableNombreBt.setValue("Guardar");

        } else {
            if (!dni.isEmpty() && !nombre.isEmpty() && !apellido.isEmpty() && !email.isEmpty() && !telefono.isEmpty()) {
                Propietario propietario = new Propietario();
                propietario.setIdPropietario(mp.getValue().getIdPropietario());
                propietario.setDni(dni);
                propietario.setNombre(nombre);
                propietario.setApellido(apellido);
                propietario.setEmail(email);
                propietario.setTelefono(telefono);
                propietario.setClave(null);

                mutableEstado.setValue(false);
                mutableNombreBt.setValue("Editar");

                String token = ApiClient.obtenerToken(getApplication());
                ApiClient.InmobiliariaService api = ApiClient.getApiInmobiliaria();
                Call<Propietario> call = api.actualizarPropietario("Bearer " + token, propietario);
                call.enqueue(new Callback<Propietario>() {
                    @Override
                    public void onResponse(Call<Propietario> call, retrofit2.Response<Propietario> response) {
                        if (response.isSuccessful()) {
                            mp.postValue(response.body());
                        } else {
                            Toast.makeText(getApplication(), "Error al actualizar el propietario", Toast.LENGTH_SHORT).show();
                            Log.d("PerfilViewModel", "Error al actualizar el propietario: " + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<Propietario> call, Throwable t) {
                        Toast.makeText(getApplication(), "Error de conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Toast.makeText(getApplication(), "Todos los campos deben completarse", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void leerPropietaro() {
        String token = ApiClient.obtenerToken(getApplication());
        ApiClient.InmobiliariaService api = ApiClient.getApiInmobiliaria();
        Call<Propietario> call = api.obtenerPropietario("Bearer " + token);
        call.enqueue(new Callback<Propietario>() {
            @Override
            public void onResponse(Call<Propietario> call, retrofit2.Response<Propietario> response) {
                if (response.isSuccessful()) {
                    mp.postValue(response.body());
                } else {
                    Toast.makeText(getApplication(), "Error al obtener el propietario", Toast.LENGTH_SHORT).show();
                    Log.d("PerfilViewModel", "Error al obtener el propietario: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Propietario> call, Throwable t) {
                Toast.makeText(getApplication(), "Error de conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("PerfilViewModel", "Error de conexión: " + t.getMessage());
            }
        });
    }
}
