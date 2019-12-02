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

import com.example.clinica_fisioterapeutica.Controllers.Paciente.PacientesActivity;
import com.example.clinica_fisioterapeutica.Controllers.Turnos.AgregarReservaActivity;
import com.example.clinica_fisioterapeutica.Models.FichaClinica;
import com.example.clinica_fisioterapeutica.Models.Persona;
import com.example.clinica_fisioterapeutica.Models.ResponseFichaClinica;
import com.example.clinica_fisioterapeutica.Models.ResponsePersona;
import com.example.clinica_fisioterapeutica.R;
import com.example.clinica_fisioterapeutica.Services.ApiAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FichaActivity extends AppCompatActivity {
    Intent intentNewActivity;
    RecyclerView rvFichaClinica;
    TextView buscador;
    Button btnAgregar;
    Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fichas);
        rvFichaClinica =findViewById(R.id.rvFicha);
        LinearLayoutManager layRec =  new LinearLayoutManager(this);
        rvFichaClinica.setLayoutManager(layRec);
        rvFichaClinica.setHasFixedSize(true);
        buscador = findViewById(R.id.tvBuscar);
        btnAgregar = findViewById(R.id.btnAgregar);
        bundle = this.getIntent().getExtras();
        try {
            if(bundle.containsKey("viewFicha")) {
                btnAgregar.setVisibility(View.INVISIBLE);
            }
        } catch (Exception e) {
            Log.v("Bundle", e.getLocalizedMessage());
        }
    cargarr();
    }
    public void cargarr() {
        Call<ResponseFichaClinica> callFichas = ApiAdapter.getApiService().getFichas("idFichaClinica","dec");
        callFichas.enqueue(new Callback<ResponseFichaClinica>() {
            @Override
            public void onResponse(Call<ResponseFichaClinica> call, Response<ResponseFichaClinica> response) {
                cargarLista(response.body().getLista());
            }

            @Override
            public void onFailure(Call<ResponseFichaClinica> call, Throwable t) {
                Log.w("warning",t.getCause().toString());
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
    }
    public void cargarModelo(String query) {
        Call<ResponseFichaClinica> callFichas = ApiAdapter.getApiService().getFichaLike("idFichaClinica","dec", query);
        callFichas.enqueue(new Callback<ResponseFichaClinica>() {
            @Override
            public void onResponse(Call<ResponseFichaClinica> call, Response<ResponseFichaClinica> response) {
                cargarLista(response.body().getLista());
            }

            @Override
            public void onFailure(Call<ResponseFichaClinica> call, Throwable t) {
                Log.w("warning",t.getCause().toString());
            }
        });
    }
    public void irAgregarEditarPersona(View view) {
        Intent intentNewActivity = new Intent(FichaActivity.this, AgregarEditarFichaActivity.class);
        startActivity(intentNewActivity);
    }

    private void cargarLista(List<FichaClinica> fichas){
        FichaClinica [] lista = new FichaClinica [fichas.size()];
        for (int i=0; i< fichas.size(); i++) {
            lista[i] = fichas.get(i);
        }

        AdapterListaFicha adapter=new AdapterListaFicha(lista);
        final FichaClinica [] lista1=lista;
        rvFichaClinica.setAdapter(adapter);
        adapter.setListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Toast.makeText(FichaActivity.this,"Ficha seleccionada "+lista1[rvFichaClinica.getChildAdapterPosition(view)].getIdFichaClinica(),Toast.LENGTH_LONG).show();
                Intent intentNewActivity;
                try {
                    if(bundle.containsKey("viewFicha")) {
                        intentNewActivity = new Intent(FichaActivity.this, AgregarEditarFichaActivity.class);
                    } else {
                        intentNewActivity = new Intent(FichaActivity.this, AgregarEditarFichaActivity.class);

                    }
                } catch (Exception e) {
                    intentNewActivity = new Intent(FichaActivity.this, AgregarEditarFichaActivity.class);
                }
                Bundle b = new Bundle();
                b.putString("idFichaClinica","" + lista1[rvFichaClinica.getChildAdapterPosition(view)].getIdFichaClinica());
                intentNewActivity.putExtras(b);
                startActivity(intentNewActivity);
            }
        });
    }

    public void filtrar(android.view.View view) {
        intentNewActivity = new Intent(FichaActivity.this, FiltroActivity.class);
        Bundle b = new Bundle();
        b.putString("viewFicha","empleado");
        intentNewActivity.putExtras(b);
        startActivityForResult(intentNewActivity, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Log.v("resultView", data.getDataString());
            cargarModelo(data.getDataString());

        }

    }

}
