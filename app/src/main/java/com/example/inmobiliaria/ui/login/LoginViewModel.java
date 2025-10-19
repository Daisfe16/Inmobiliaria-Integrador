package com.example.inmobiliaria.ui.login;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.inmobiliaria.ui.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends AndroidViewModel {

    private MutableLiveData<Boolean> loginResult;
    private MutableLiveData<String> mensaje;
    private MutableLiveData<Boolean> uYalogeado;

    public LoginViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Boolean> getuYalogeado() {
        if (uYalogeado == null) {
            uYalogeado = new MutableLiveData<>();
        }
        return uYalogeado;
    }

    public LiveData<Boolean> getLoginResult() {
        if (loginResult == null) {
            loginResult = new MutableLiveData<>();
        }
        return loginResult;
    }

    public LiveData<String> getMensaje() {
        if (mensaje == null) {
            mensaje = new MutableLiveData<>();
        }
        return mensaje;
    }

    public void login(String usuario, String clave) {
        if (usuario.isEmpty() || clave.isEmpty()) {
            mensaje.setValue("Debe completar todos los campos");
            return;
        }

        ApiClient.InmobiliariaService api = ApiClient.getApiInmobiliaria();
        Call<String> call = api.login(usuario, clave);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String token = "Bearer " + response.body();
                    ApiClient.guardarToken(getApplication(), token);
                    loginResult.setValue(true);
                    Log.d("TOKEN", token);
                } else {
                    loginResult.setValue(false);
                    mensaje.setValue("Credenciales incorrectas");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                loginResult.setValue(false);
                mensaje.setValue("Error de conexión: " + t.getMessage());
                Log.e("LoginError", t.getMessage(), t);
            }
        });


    }

}
