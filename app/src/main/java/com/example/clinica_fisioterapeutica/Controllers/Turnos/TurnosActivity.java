package com.example.clinica_fisioterapeutica.Controllers.Turnos;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clinica_fisioterapeutica.R;
import com.example.clinica_fisioterapeutica.Services.ApiAdapter;
import com.example.clinica_fisioterapeutica.Models.ResponseReserva;

import com.example.clinica_fisioterapeutica.Models.Reserva;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

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
        String fecha = "";
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        fecha = "" + calendar.get(Calendar.YEAR);
        fecha += "" + (calendar.get(Calendar.MONTH)+1);
        fecha += calendar.get(Calendar.DATE)<10? "0" + calendar.get(Calendar.DATE):"" + calendar.get(Calendar.DATE);
        fecha = "20190903";
        String query = "{\"fechaCadena\":\""+fecha+"\"}";
        Call<ResponseReserva> call = ApiAdapter.getApiService().getReservas("idReserva","dec", query);
        call.enqueue(new Callback<ResponseReserva>() {
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
        Intent intentNewActivity = new Intent(TurnosActivity.this, AgregarReservaActivity.class);
        startActivity(intentNewActivity);
    }

    private void cargarLista(List<Reserva> reservas){
        Reserva[] lista = new Reserva[reservas.size()];
        for (int i = 0; i< reservas.size(); i++) {
            lista[i] = reservas.get(i);
        }

        AdapterTurno adapter=new AdapterTurno(lista);
        final Reserva[] lista1=lista;
        rvTurno.setAdapter(adapter);

        adapter.setListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intentNewActivity;
                intentNewActivity = new Intent(TurnosActivity.this, EditarReservaActivity.class);
                Bundle b = new Bundle();
                b.putString("idReserva","" + lista1[rvTurno.getChildAdapterPosition(view)].getIdReserva());
                intentNewActivity.putExtras(b);
                startActivity(intentNewActivity);
            }
        });
    }
}
