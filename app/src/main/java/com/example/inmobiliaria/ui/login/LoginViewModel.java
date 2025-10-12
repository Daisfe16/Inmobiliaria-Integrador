package com.example.inmobiliaria.ui.login;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class LoginViewModel extends AndroidViewModel {

    private MutableLiveData<String> mMensaje = new MutableLiveData<>();
    private MutableLiveData<String> mLogin = new MutableLiveData<>();

    public LoginViewModel(@NonNull Application application){
        super(application);

    }

    public LiveData<String> getmMensaje() {
        return mMensaje;
    }

    public LiveData<String> getmLogin() {
        return mLogin;
    }

    public void setmLogin(MutableLiveData<String> mLogin) {
        this.mLogin = mLogin;
    }
}
