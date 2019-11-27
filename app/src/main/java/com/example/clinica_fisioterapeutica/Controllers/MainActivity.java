package com.example.clinica_fisioterapeutica.Controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.example.clinica_fisioterapeutica.Models.Persona;
import com.example.clinica_fisioterapeutica.Models.ResponsePersona;
import com.example.clinica_fisioterapeutica.R;
import com.example.clinica_fisioterapeutica.Services.ApiAdapter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    EditText campoNombreUsuario;
    EditText campoPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        campoNombreUsuario=findViewById(R.id.txtNombreUsuario);
        campoPassword=findViewById(R.id.txtPassword);
    }

    public void ingresar(android.view.View vista) {
        Call<ResponsePersona> call = ApiAdapter.getApiService().getPersonas("idPersona","asc");
        call.enqueue(new Callback<ResponsePersona>() {
            @Override
            public void onResponse(Call<ResponsePersona> call, Response<ResponsePersona> response) {
                //Toast.makeText(MainActivity.this, "Peticcion exitosa", Toast.LENGTH_SHORT).show();
                if (response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Entro", Toast.LENGTH_SHORT).show();
                    ResponsePersona responsePersona = response.body();
                    if (responsePersona != null) {
                        for (Persona persona : responsePersona.getLista()) {
                            if(campoNombreUsuario.getText().equals(persona.getUsuarioLogin())) {
                                Toast.makeText(MainActivity.this, "Usuario logueado" + persona.getUsuarioLogin(), Toast.LENGTH_SHORT).show();
                            }
                        }

                    } else {
                        Toast.makeText(MainActivity.this, "Error al leer los datos", Toast.LENGTH_SHORT).show();
                    }

                }
            }
            @Override
            public void onFailure(Call<ResponsePersona> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Peticcion fallida", Toast.LENGTH_SHORT).show();
            }
        });
    }
}