package com.example.inmobiliaria.ui.perfil;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;


import com.example.inmobiliaria.databinding.FragmentPerfilBinding;

public class PerfilFragment extends Fragment {


    private PerfilViewModel perfilViewModel;
    private FragmentPerfilBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        perfilViewModel =new ViewModelProvider(this).get(PerfilViewModel.class);

        binding = FragmentPerfilBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        perfilViewModel.getPropietario().observe(getViewLifecycleOwner(), propietario -> {
            binding.tvDni.setText(String.valueOf(propietario.getDni()));
            binding.tvNombre.setText(propietario.getNombre());
            binding.tvApellido.setText(propietario.getApellido());
            binding.tvEmail.setText(propietario.getEmail());
            binding.tvTelefono.setText(propietario.getTelefono());

        });
        perfilViewModel.getEstado().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            public void onChanged(Boolean estado) {
                binding.tvDni.setEnabled(true);
                binding.tvNombre.setEnabled(true);
                binding.tvApellido.setEnabled(true);
                binding.tvEmail.setEnabled(true);
                binding.tvTelefono.setEnabled(true);
            }});

        perfilViewModel.getNombreBt().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.btnEditar.setText(s);
            }
        });

        perfilViewModel.leerPropietaro();
        binding.btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String dni = binding.tvDni.getText().toString();
                String nombre = binding.tvNombre.getText().toString();
                String apellido = binding.tvApellido.getText().toString();
                String email = binding.tvEmail.getText().toString();
                String telefono = binding.tvTelefono.getText().toString();

                perfilViewModel.guardar(binding.btnEditar.getText().toString(), dni, nombre, apellido, email, telefono);
            }
        });


        return root;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    }


