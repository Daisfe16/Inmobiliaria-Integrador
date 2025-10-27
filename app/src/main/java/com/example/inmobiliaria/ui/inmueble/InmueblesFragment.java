package com.example.inmobiliaria.ui.inmueble;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.inmobiliaria.R;
import com.example.inmobiliaria.databinding.FragmentInmuebleBinding;
import com.example.inmobiliaria.model.Inmueble;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class InmueblesFragment extends Fragment {
    private FragmentInmuebleBinding binding;
    private InmueblesViewModel vm;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        vm = new ViewModelProvider(this).get(InmueblesViewModel.class);
        binding = FragmentInmuebleBinding.inflate(inflater, container, false);

        vm.getmInmueble().observe(getViewLifecycleOwner(), new Observer<List<Inmueble>>() {
            @Override
            public void onChanged(List<Inmueble> inmuebles) {
                InmuebleAdapter adapter = new InmuebleAdapter(inmuebles, getContext());
                GridLayoutManager glm = new GridLayoutManager(getContext(), 2);
                RecyclerView rv = binding.rvListaInmueble;

                rv.setAdapter(adapter);
                rv.setLayoutManager(glm);
            }
        });
        vm.getNavegarAgregarInmueble().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean navegarAgregarInmueble) {
                if (Boolean.TRUE.equals(navegarAgregarInmueble)) {
                    vm.resetearEventoNavegacionInmueble();
                    NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main);
                    navController.navigate(R.id.action_nav_inmueble_to_agregarFragment);
                }
            }
        });
        binding.fabAgregarInmueble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vm.onAgregarInmuebleClicked();
            }
        });

        //vm.leerInmuebles();

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}