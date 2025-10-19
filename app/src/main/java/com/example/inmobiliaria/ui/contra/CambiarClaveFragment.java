package com.example.inmobiliaria.ui.contra;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.inmobiliaria.R;
import com.example.inmobiliaria.databinding.FragmentCambiarClaveBinding;
import com.example.inmobiliaria.ui.login.LoginActivity;


public class CambiarClaveFragment extends Fragment {

    private FragmentCambiarClaveBinding binding;
    private CambiarClaveViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCambiarClaveBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(CambiarClaveViewModel.class);

        viewModel.getErrorActual().observe(getViewLifecycleOwner(), error -> {
            binding.etContraActual.setError(error);
        });

        viewModel.getErrorNueva().observe(getViewLifecycleOwner(), error -> {
            binding.etContraNueva.setError(error);
        });

        viewModel.getErrorConfirmacion().observe(getViewLifecycleOwner(), error -> {
            binding.etContraNConfirmar.setError(error);
        });

        viewModel.getExitoCambio().observe(getViewLifecycleOwner(), exito -> {
            if (Boolean.TRUE.equals(exito)) {
                SharedPreferences prefs = requireContext().getSharedPreferences("token", Context.MODE_PRIVATE);
                prefs.edit().remove("token").apply();

                Intent intent = new Intent(requireContext(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });


        binding.btConfirmarContra.setOnClickListener(v -> {
            String actual = binding.etContraActual.getText().toString();
            String nueva = binding.etContraNueva.getText().toString();
            String repetir = binding.etContraNConfirmar.getText().toString();

            viewModel.cambiarClave(requireContext(), actual, nueva, repetir);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
