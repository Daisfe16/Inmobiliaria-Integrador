package com.example.inmobiliaria.ui.contra;

import static androidx.lifecycle.AndroidViewModel_androidKt.getApplication;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.inmobiliaria.ui.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CambiarClaveViewModel extends AndroidViewModel {


    private final MutableLiveData<String> errorActual = new MutableLiveData<>();
    private final MutableLiveData<String> errorNueva = new MutableLiveData<>();
    private final MutableLiveData<String> errorConfirmacion = new MutableLiveData<>();
    private final MutableLiveData<Boolean> exitoCambio = new MutableLiveData<>();

    public CambiarClaveViewModel(Application application) {
        super(application);
    }


    public LiveData<String> getErrorActual() { return errorActual; }
    public LiveData<String> getErrorNueva() { return errorNueva; }
    public LiveData<String> getErrorConfirmacion() { return errorConfirmacion; }
    public LiveData<Boolean> getExitoCambio() { return exitoCambio; }

    public void cambiarClave(Context context, String actual, String nueva, String repetir) {
        boolean valido = true;

        if (actual.isEmpty()) {
            errorActual.setValue("Ingrese contraseña actual");
            valido = false;
        } else {
            errorActual.setValue(null);
        }

        if (nueva.isEmpty()) {
            errorNueva.setValue("Ingrese nueva contraseña");
            valido = false;
        } else {
            errorNueva.setValue(null);
        }

        if (repetir.isEmpty()) {
            errorConfirmacion.setValue("Confirme nueva contraseña");
            valido = false;
        } else {
            errorConfirmacion.setValue(null);
        }

        if (!nueva.equals(repetir)) {
            errorConfirmacion.setValue("Las contraseñas no coinciden");
            valido = false;
        }

        if (!valido) return;

        String token = ApiClient.obtenerToken(getApplication());
        ApiClient.InmobiliariaService api = ApiClient.getApiInmobiliaria();

        Call<Void> call = api.cambiarClave("Bearer " + token, actual, nueva);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    exitoCambio.setValue(true);
                } else {
                    errorActual.setValue("Contraseña actual incorrecta");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                errorActual.setValue("Error de conexión");
            }
        });
    }
}
