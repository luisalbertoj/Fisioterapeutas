package com.example.clinica_fisioterapeutica.Controllers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.clinica_fisioterapeutica.Models.Persona;
import com.example.clinica_fisioterapeutica.Models.ResponsePersona;
import com.example.clinica_fisioterapeutica.R;
import com.example.clinica_fisioterapeutica.Services.ApiAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PacientesActivity extends AppCompatActivity {
    RecyclerView rvPersona;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pacientes);
        rvPersona=findViewById(R.id.rvPersona);
        LinearLayoutManager layRec =  new LinearLayoutManager(this);
        rvPersona.setLayoutManager(layRec);
        rvPersona.setHasFixedSize(true);
    }
    @Override
    protected void onResume() {
        super.onResume();
        Call<ResponsePersona> callPersonas = ApiAdapter.getApiService().getPersonas("idPersona","asc");
        callPersonas.enqueue(new Callback<ResponsePersona>() {
            @Override
            public void onResponse(Call<ResponsePersona> call, Response<ResponsePersona> response) {
                cargarLista(response.body().getLista());
            }

            @Override
            public void onFailure(Call<ResponsePersona> call, Throwable t) {
                Log.w("warning",t.getCause().toString());
            }
        });
    }

    public void irAgregarEditarPersona(View view) {
        Intent intentNewActivity = new Intent(PacientesActivity.this, AgregarEditarPersonaActivity.class);
        startActivity(intentNewActivity);
    }

    private void cargarLista(List<Persona> personas){
        Persona [] lista = new Persona [personas.size()];
        for (int i=0; i< personas.size(); i++) {
            lista[i] = personas.get(i);
        }

        AdapterPersona adapter=new AdapterPersona(lista);
        final Persona [] lista1=lista;
        rvPersona.setAdapter(adapter);
        adapter.setListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Toast.makeText(PacientesActivity.this,"categoria seleccionada "+lista1[rvPersona.getChildAdapterPosition(view)].getIdPersona(),Toast.LENGTH_LONG).show();
                Intent intentNewActivity = new Intent(PacientesActivity.this, AgregarEditarPersonaActivity.class);
                Bundle b = new Bundle();
                b.putString("idPersona","" + lista1[rvPersona.getChildAdapterPosition(view)].getIdPersona());
                b.putString("nombre",lista1[rvPersona.getChildAdapterPosition(view)].getNombre());
                intentNewActivity.putExtras(b);
                startActivity(intentNewActivity);

            }
        });
    }
}
