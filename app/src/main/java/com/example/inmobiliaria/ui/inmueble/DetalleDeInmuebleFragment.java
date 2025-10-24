package com.example.inmobiliaria.ui.inmueble;

import static com.example.inmobiliaria.ui.request.ApiClient.URLBASE;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.inmobiliaria.R;
import com.example.inmobiliaria.databinding.FragmentDetalleDeInmuebleBinding;
import com.example.inmobiliaria.databinding.FragmentInmuebleBinding;

public class DetalleDeInmuebleFragment extends Fragment {

    private DetalleDeInmuebleViewModel mViewModel;
    private FragmentDetalleDeInmuebleBinding  binding;


    public static DetalleDeInmuebleFragment newInstance() {
        return new DetalleDeInmuebleFragment();
    }






    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentDetalleDeInmuebleBinding.inflate(inflater, container, false);



        mViewModel = new ViewModelProvider(this).get(DetalleDeInmuebleViewModel.class);

        mViewModel.getmInmueble().observe(getViewLifecycleOwner(), inmueble -> {
            binding.tvIdInmueble.setText(inmueble.getIdInmueble() + "");
            binding.tvDireccionI.setText(inmueble.getDireccion());
            binding.tvUsoI.setText(inmueble.getUso());
            binding.tvAmbientesI.setText(inmueble.getAmbientes() + "");
            binding.tvLatitudI.setText(inmueble.getLatitud() + "");
            binding.tvLongitudI.setText(inmueble.getLongitud() + "");
            binding.tvValorI.setText(inmueble.getValor() + "");
            binding.checkDisponible.setChecked(inmueble.isDisponible());
            Glide.with(this)
                    .load(URLBASE + inmueble.getImagen())
                    .placeholder(R.drawable.inm)
                    .error("null")
                    .into(binding.imgInmuebleD);
        });
        mViewModel.obtenerInmueble(getArguments());

        binding.checkDisponible.setOnClickListener(view -> {
            Boolean disponible = binding.checkDisponible.isChecked();
            mViewModel.actualizarInmueble(disponible);
        });




        return binding.getRoot();


        };



    }




