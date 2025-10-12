package com.example.inmobiliaria.ui.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.Toast;

import com.example.inmobiliaria.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private LoginViewModel loginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);


        loginViewModel.getTokenLiveData().observe(this, token -> {
            Toast.makeText(this, "Login exitoso ✅", Toast.LENGTH_SHORT).show();
            // Acá podrías abrir otra Activity o guardar más datos
        });

        loginViewModel.getErrorLiveData().observe(this, error -> {
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
        });

        // Evento del botón
        binding.btnLogin.setOnClickListener(v -> {
            String usuario = binding.etUsuario.getText().toString();
            String clave = binding.etClave.getText().toString();

            if (usuario.isEmpty() || clave.isEmpty()) {
                Toast.makeText(this, "Complete todos los campos", Toast.LENGTH_SHORT).show();
            } else {
                loginViewModel.login(usuario, clave);
            }
        });
    }
}
