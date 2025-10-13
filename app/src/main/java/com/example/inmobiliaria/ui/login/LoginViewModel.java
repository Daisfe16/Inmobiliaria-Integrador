package com.example.inmobiliaria.ui.login;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.inmobiliaria.ui.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends AndroidViewModel {

    private MutableLiveData<String> tokenLiveData;
    private MutableLiveData<String> errorLiveData;
    private Context context;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public MutableLiveData<String> getTokenLiveData() {
        if (tokenLiveData == null) {
            tokenLiveData = new MutableLiveData<>();
        }
        return tokenLiveData;
    }

    public MutableLiveData<String> getErrorLiveData() {
        if (errorLiveData == null) {
            errorLiveData = new MutableLiveData<>();
        }
        return errorLiveData;
    }

    // Método que hace la llamada al API
    public void login(String usuario, String clave) {
        ApiClient.InmobiliariaService api = ApiClient.getApiInmobiliaria();

        Call<String> call = api.login(usuario, clave);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String token = response.body();
                    ApiClient.guardarToken(context, token);
                    tokenLiveData.setValue(token);
                } else {
                    errorLiveData.setValue("Usuario o clave incorrectos");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                errorLiveData.setValue("Error de conexión: " + t.getMessage());
            }
        });
    }
}
