package com.lula.tp2_2cuatrilab3_2024.ui.registro;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.lula.tp2_2cuatrilab3_2024.R;
import com.lula.tp2_2cuatrilab3_2024.databinding.ActivityRegistroBinding;

public class RegistroActivity extends AppCompatActivity {
    private ActivityRegistroBinding binding;
    private RegistroActivityViewModel vm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegistroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        vm = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(RegistroActivityViewModel.class);

        binding.btGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long dni = Long.parseLong(binding.etDni.getText().toString());
                String nombre = binding.etNombre.getText().toString();
                String apellido = binding.etApellido.getText().toString();
                String mail = binding.etMail.getText().toString();
                String pass = binding.etPassword.getText().toString();

                vm.guardarUsuario(nombre, apellido, mail, pass, dni);
            }
        });

        vm.getMUsuario().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.tvListadoUsuarios.setText("Usuario: " + s);
            }
        });

        vm.getMNombre().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.etNombre.setText(s);
            }
        });

        vm.getMApellido().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.etApellido.setText(s);
            }
        });

        vm.getMMail().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.etMail.setText(s);
            }
        });

        vm.getMDni().observe(this, new Observer<Long>() {
            @Override
            public void onChanged(Long aLong) {
                binding.etDni.setText(aLong.toString());
            }
        });

        vm.getMPass().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.etPassword.setText(s);
            }
        });
        if(getIntent().getFlags() == Intent.FLAG_ACTIVITY_NEW_TASK)
        {
            vm.recuperarUsuario();
        }
    }
}