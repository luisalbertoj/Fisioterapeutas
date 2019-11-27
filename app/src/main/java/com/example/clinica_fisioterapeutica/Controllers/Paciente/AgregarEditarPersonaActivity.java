package com.example.clinica_fisioterapeutica.Controllers.Paciente;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.clinica_fisioterapeutica.R;

public class AgregarEditarPersonaActivity extends AppCompatActivity {

    TextView idPersona;
    TextView nombre;
    TextView apellido;
    TextView telefono;
    TextView email;
    TextView ruc;
    TextView cedula;
    TextView tipoPersona;
    TextView fechaNacimiento;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_editar_persona);
        idPersona = findViewById(R.id.idPersona);
        nombre = findViewById(R.id.nombre);
        apellido = findViewById(R.id.apellido);
        telefono = findViewById(R.id.telefono);
        email = findViewById(R.id.email);
        ruc = findViewById(R.id.ruc);
        cedula = findViewById(R.id.cedula);
        tipoPersona = findViewById(R.id.tipoPersona);
        fechaNacimiento = findViewById(R.id.fechaNacimiento);

        
    }
}
