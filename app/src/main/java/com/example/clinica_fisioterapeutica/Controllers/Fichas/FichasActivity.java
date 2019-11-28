package com.example.clinica_fisioterapeutica.Controllers.Fichas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.clinica_fisioterapeutica.Models.Persona;
import com.example.clinica_fisioterapeutica.Models.ResponsePersona;
import com.example.clinica_fisioterapeutica.R;
import com.example.clinica_fisioterapeutica.Services.ApiAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FichasActivity extends AppCompatActivity {
    RecyclerView rvPersona;
    TextView buscador;
    Button btnAgregar;
    Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pacientes);
        rvPersona=findViewById(R.id.rvPersona);
        LinearLayoutManager layRec =  new LinearLayoutManager(this);
        rvPersona.setLayoutManager(layRec);
        rvPersona.setHasFixedSize(true);
        buscador = findViewById(R.id.tvBuscarPaciente);
        btnAgregar = findViewById(R.id.btnAgregar);
        FichasActivity.this.buscarPaciente();
        bundle = this.getIntent().getExtras();
        if(bundle.containsKey("viewFicha")) {
            btnAgregar.setVisibility(View.INVISIBLE);
        }
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
        Intent intentNewActivity = new Intent(FichasActivity.this, AgregarEditarFichaActivity.class);
        startActivity(intentNewActivity);
    }

    private void cargarLista(List<Persona> personas){
        Persona [] lista = new Persona [personas.size()];
        for (int i=0; i< personas.size(); i++) {
            lista[i] = personas.get(i);
        }

        AdapterFicha adapter=new AdapterFicha(lista);
        final Persona [] lista1=lista;
        rvPersona.setAdapter(adapter);
        adapter.setListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Toast.makeText(FichasActivity.this,"Persona seleccionada "+lista1[rvPersona.getChildAdapterPosition(view)].getIdPersona(),Toast.LENGTH_LONG).show();
                Intent intentNewActivity;
                if(bundle.containsKey("viewFicha")) {
                    intentNewActivity = new Intent(FichasActivity.this, FichasActivity.class);
                } else {
                    intentNewActivity = new Intent(FichasActivity.this, AgregarEditarFichaActivity.class);

                }
                Bundle b = new Bundle();
                b.putString("idPersona","" + lista1[rvPersona.getChildAdapterPosition(view)].getIdPersona());
                b.putString("nombre",lista1[rvPersona.getChildAdapterPosition(view)].getNombre());
                intentNewActivity.putExtras(b);
                startActivity(intentNewActivity);
            }
        });
    }

    public void buscarPaciente() {
        buscador.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Call<ResponsePersona> callPersonas = ApiAdapter.getApiService().getPersonasLike(
                        "idPersona","asc", "S",
                        "{\"nombre\":\""+buscador.getText().toString()+"\"}");
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

            @Override
            public void afterTextChanged(Editable s) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }
        });

    }

}
