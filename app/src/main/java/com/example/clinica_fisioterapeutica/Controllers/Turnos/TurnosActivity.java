package com.example.clinica_fisioterapeutica.Controllers.Turnos;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clinica_fisioterapeutica.Controllers.Fichas.AgregarEditarFichaActivity;
import com.example.clinica_fisioterapeutica.Controllers.Fichas.FichaActivity;
import com.example.clinica_fisioterapeutica.Controllers.Paciente.AdapterPersona;
import com.example.clinica_fisioterapeutica.Controllers.Paciente.AgregarEditarPersonaActivity;
import com.example.clinica_fisioterapeutica.Controllers.Paciente.PacientesActivity;
import com.example.clinica_fisioterapeutica.Models.Persona;
import com.example.clinica_fisioterapeutica.Models.ResponsePersona;
import com.example.clinica_fisioterapeutica.R;
import com.example.clinica_fisioterapeutica.Services.ApiAdapter;
import com.example.clinica_fisioterapeutica.Models.ResponseReserva;

import com.example.clinica_fisioterapeutica.Models.Turno;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TurnosActivity extends AppCompatActivity {
    RecyclerView rvTurno;
    Bundle bundle;
    TextView buscador;
    Button btnAgregar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_turnos);
        rvTurno=findViewById(R.id.rvTurnos);
        LinearLayoutManager layRec =  new LinearLayoutManager(this);
        rvTurno.setLayoutManager(layRec);
        rvTurno.setHasFixedSize(true);
        buscador = findViewById(R.id.tvBuscar);
        btnAgregar = findViewById(R.id.btnAgregar);
        bundle = this.getIntent().getExtras();
        try {
            if(bundle.containsKey("viewTurno")) {
                btnAgregar.setVisibility(View.INVISIBLE);
            }
        } catch (Exception e) {
            Log.v("Bundle", e.getLocalizedMessage());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Call<ResponseReserva> callTurnos = ApiAdapter.getApiService().getReservas("fecha","asc");
        callTurnos.enqueue(new Callback<ResponseReserva>() {
            @Override
            public void onResponse(Call<ResponseReserva> call, Response<ResponseReserva> response) {
                cargarLista(response.body().getLista());
            }

            @Override
            public void onFailure(Call<ResponseReserva> call, Throwable t) {
                Log.w("warning",t.getCause().toString());
            }
        });
    }

    public void irAgregarEditarPersona(View view) {
        Intent intentNewActivity = new Intent(TurnosActivity.this, AgregarEditarFichaActivity.class);
        startActivity(intentNewActivity);
    }

    private void cargarLista(List<Turno> turnos){
        Turno [] lista = new Turno [turnos.size()];
        for (int i=0; i< turnos.size(); i++) {
            lista[i] = turnos.get(i);
        }

        AdapterTurno adapter=new AdapterTurno(lista);
        final Turno [] lista1=lista;
        rvTurno.setAdapter(adapter);

        adapter.setListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Toast.makeText(TurnosActivity.this,"Persona seleccionada "+lista1[rvTurno.getChildAdapterPosition(view)].getFecha(),Toast.LENGTH_LONG).show();
                Intent intentNewActivity;
                try {
                    if(bundle.containsKey("viewFicha")) {
                        intentNewActivity = new Intent(TurnosActivity.this, FichaActivity.class);
                    } else {
                        intentNewActivity = new Intent(TurnosActivity.this, AgregarEditarPersonaActivity.class);

                    }
                } catch (Exception e) {
                    intentNewActivity = new Intent(TurnosActivity.this, AgregarEditarPersonaActivity.class);
                }
                Bundle b = new Bundle();
                b.putString("idPersona","" + lista1[rvTurno.getChildAdapterPosition(view)].getFecha());
                b.putString("nombre",lista1[rvTurno.getChildAdapterPosition(view)].getHoraFin());
                intentNewActivity.putExtras(b);
                startActivity(intentNewActivity);
            }
        });
    }
}
