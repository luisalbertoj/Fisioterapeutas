package com.example.clinica_fisioterapeutica.Controllers.Paciente;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Person;
import java.util.Calendar;
import android.os.Bundle;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.clinica_fisioterapeutica.Controllers.MainActivity;
import com.example.clinica_fisioterapeutica.Models.Persona;
import com.example.clinica_fisioterapeutica.Models.ResponsePersona;
import com.example.clinica_fisioterapeutica.R;
import com.example.clinica_fisioterapeutica.Services.ApiAdapter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    Calendar calendar;
    DatePickerDialog dataPiker;




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

        Bundle bundle = this.getIntent().getExtras();
        if(bundle != null && bundle.containsKey("idPersona")){
            idPersona.setText(bundle.getString("idPersona"));
            nombre.setText(bundle.getString("nombre"));
            this.cargarPersona(bundle.getString("idPersona"));
        }

    }

    public void cargarPersona(String idpersona) {
        Call<Persona> call = ApiAdapter.getApiService().getPersona(idpersona);
        call.enqueue(new Callback<Persona>() {
            @Override
            public void onResponse(Call<Persona> call, Response<Persona> response) {
                if (response.isSuccessful()) {
                    Persona persona = response.body();
                    if (persona != null) {
                        idPersona.setText(persona.getIdPersona());
                        nombre.setText(persona.getNombre());
                        apellido.setText(persona.getApellido());
                        telefono.setText(persona.getTelefono());
                        email.setText(persona.getEmail());
                        ruc.setText(persona.getRuc());
                        cedula.setText(persona.getCedula());
                        tipoPersona.setText(persona.getTipoPersona());
                        fechaNacimiento.setText(persona.getFechaNacimiento());
                        return;
                    } else {
                        Toast.makeText(AgregarEditarPersonaActivity.this, "Persona no encontrada", Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<Persona> call, Throwable t) {
                Toast.makeText(AgregarEditarPersonaActivity.this, "Peticcion fallida:" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void AgregarEditarPersona(android.view.View view) {
        Persona persona = new Persona();
        if(!idPersona.getText().toString().equals("")) {
            persona.setIdPersona(idPersona.getText().toString());
        }
        persona.setNombre(nombre.getText().toString());
        persona.setApellido(apellido.getText().toString());
        persona.setCedula(cedula.getText().toString());
        persona.setEmail(email.getText().toString());
        persona.setFechaNacimiento(fechaNacimiento.getText().toString() + "  00:00:00");
        persona.setRuc(ruc.getText().toString());
        persona.setTelefono(telefono.getText().toString());
        persona.setTipoPersona(tipoPersona.getText().toString());

        Call<Persona> callPersona=idPersona==null?ApiAdapter.getApiService().createPersona(persona):ApiAdapter.getApiService().updatePersona(persona);
        callPersona.enqueue(new Callback<Persona>() {
            @Override
            public void onResponse(Call<Persona> call, Response<Persona> response) {
                Toast.makeText(AgregarEditarPersonaActivity.this,"Agregado Correctamente",Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<Persona> call, Throwable t) {
                Log.w("warning",t.getCause());
                if(t.getCause() == null) {
                    finish();
                }
                Toast.makeText(AgregarEditarPersonaActivity.this,"Error: " + t.getCause(),Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void openDataPicker(android.view.View view) {
        calendar = Calendar.getInstance();
        int day = 24;
        int month = 06;
        int year = 1996;
        if(!fechaNacimiento.getText().toString().equals("")) {
            String fechaPer = fechaNacimiento.getText().toString();
            String [] split = fechaPer.split("-");
             day = Integer.parseInt(split[2]);
             month = Integer.parseInt(split[1]);
             year = Integer.parseInt(split[0]);
        } else {
             day = calendar.get(Calendar.DAY_OF_MONTH);
             month = calendar.get(Calendar.MONTH);
             year = calendar.get(Calendar.YEAR);
        }

        dataPiker = new DatePickerDialog(AgregarEditarPersonaActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int myear, int mmonth, int mdayOfMonth) {
                fechaNacimiento.setText(myear + "-" + mmonth+1 + "-" + mdayOfMonth);
            }
        }, day, month, year);
        dataPiker.show();

    }
}
