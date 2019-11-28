package com.example.clinica_fisioterapeutica.Controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.example.clinica_fisioterapeutica.Controllers.Paciente.PacientesActivity;
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
        if (campoNombreUsuario.getText().toString().equals("") || campoPassword.getText().toString().equals("")) {
            Toast.makeText(MainActivity.this, "Los campos no pueden estar vacios", Toast.LENGTH_SHORT).show();
        } else {
            Call<ResponsePersona> call = ApiAdapter.getApiService().getPersonas("idPersona", "asc");
            call.enqueue(new Callback<ResponsePersona>() {
                @Override
                public void onResponse(Call<ResponsePersona> call, Response<ResponsePersona> response) {
                    //Toast.makeText(MainActivity.this, "Peticcion exitosa", Toast.LENGTH_SHORT).show();
                    if (response.isSuccessful()) {
                        ResponsePersona responsePersona = response.body();
                        if (responsePersona != null) {

                            for (Persona persona : responsePersona.getLista()) {

                                if (campoNombreUsuario.getText().toString().equals(persona.getUsuarioLogin())) {
                                    Toast.makeText(MainActivity.this, "Usuario logueado" + persona.getUsuarioLogin(), Toast.LENGTH_SHORT).show();
                                    Intent intentNewActivity = new Intent(MainActivity.this, MenuActivity.class);
                                    Bundle b = new Bundle();
                                    b.putString("usuario", campoNombreUsuario.getText().toString());
                                    b.putString("idPersona", "" + persona.getIdPersona());
                                    intentNewActivity.putExtras(b);
                                    startActivity(intentNewActivity);
                                    return;

                                }
                            }
                            Toast.makeText(MainActivity.this, "Usuario o contraseña incorrecto", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(MainActivity.this, "Error al leer los datos", Toast.LENGTH_SHORT).show();
                        }

                    }
                }

                @Override
                public void onFailure(Call<ResponsePersona> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "Peticcion fallida:" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}