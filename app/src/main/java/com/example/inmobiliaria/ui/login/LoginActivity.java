package com.example.inmobiliaria.ui.login;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.inmobiliaria.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
    private  LoginViewModel  vModel;
    private ActivityLoginBinding bindig;


    @Override
    protected void onCreate(Bundle savedIstanceState){
        super.onCreate(savedIstanceState);

        vModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(LoginViewModel.class);
        bindig = ActivityLoginBinding.inflate(getLayoutInflater());

        vModel.getmMensaje().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {

            }
        });
        vModel.getmLogin().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {

            }
        });

        bindig.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usuario = bindig

            }
        })

        };
    }


}
