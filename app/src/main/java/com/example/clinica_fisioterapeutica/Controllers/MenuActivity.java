package com.example.clinica_fisioterapeutica.Controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.clinica_fisioterapeutica.Controllers.Fichas.FichaActivity;
import com.example.clinica_fisioterapeutica.Controllers.Fichas.FichaArchivoActivity;
import com.example.clinica_fisioterapeutica.Controllers.Paciente.PacientesActivity;
import com.example.clinica_fisioterapeutica.Controllers.Turnos.TurnosActivity;
import com.example.clinica_fisioterapeutica.R;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }
    public void openPacientes(android.view.View view) {
        Intent intentNewActivity = new Intent(MenuActivity.this, PacientesActivity.class);
        startActivity(intentNewActivity);
        return;
    }
    public void openFichas(android.view.View view) {
        Intent intentNewActivity = new Intent(MenuActivity.this, FichaActivity.class);
        startActivity(intentNewActivity);
        return;
    }
    public void openSalir(android.view.View view) {
        Intent intentNewActivity = new Intent(MenuActivity.this, MainActivity.class);
        startActivity(intentNewActivity);
        return;
    }
    public void openTurnos(android.view.View view) {
        Intent intentNewActivity = new Intent(MenuActivity.this, TurnosActivity.class);
        startActivity(intentNewActivity);
        return;
    }
}
