package com.example.clinica_fisioterapeutica.Controllers.Turnos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.clinica_fisioterapeutica.Controllers.Fichas.AgregarEditarFichaActivity;
import com.example.clinica_fisioterapeutica.Models.FichaClinica;
import com.example.clinica_fisioterapeutica.Models.Reserva;
import com.example.clinica_fisioterapeutica.R;
import com.example.clinica_fisioterapeutica.Services.ApiAdapter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditarReservaActivity extends AppCompatActivity {
    TextView idReserva;
    Button btnCancelar;
    CheckBox flagAsistir;
    TextView horaInicio;
    TextView horaFin;
    TextView fecha;
    TextView observacion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_reserva);

        idReserva = findViewById(R.id.idReserva);
        btnCancelar = findViewById(R.id.cancelar);
        flagAsistir = findViewById(R.id.flagAsistir);
        horaFin = findViewById(R.id.horaFin);
        horaInicio = findViewById(R.id.horaInicio);
        fecha = findViewById(R.id.fecha);
        observacion = findViewById(R.id.observacion);

        Bundle bundle = this.getIntent().getExtras();
        idReserva.setText(bundle.getString("idReserva"));
        cargarModelo(idReserva.getText().toString());


    }

    public void cargarModelo(String idModelo) {
        Call<Reserva> call = ApiAdapter.getApiService().getReserva(idModelo);
        call.enqueue(new Callback<Reserva>() {
            @Override
            public void onResponse(Call<Reserva> call, Response<Reserva> response) {
                if (response.isSuccessful()) {
                    Reserva reserva = response.body();
                    if (reserva != null) {
                        fecha.setText(reserva.getFecha());
                        horaInicio.setText(reserva.getHoraInicio());
                        horaFin.setText(reserva.getHoraFin());
                        try {

                            observacion.setText(reserva.getObservacion());
                            flagAsistir.setChecked(reserva.getFlagAsistio() == null ? false : true);
                        }catch (Exception e){

                        }

                    } else {
                        Toast.makeText(EditarReservaActivity.this, "Ficha no encontrada", Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<Reserva> call, Throwable t) {
                Toast.makeText(EditarReservaActivity.this, "Peticcion fallida:" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void cancelarReserva (android.view.View view){
        Call<Reserva> call = ApiAdapter.getApiService().deleteReserva(idReserva.getText().toString());
        call.enqueue(new Callback<Reserva>() {
            @Override
            public void onResponse(Call<Reserva> call, Response<Reserva> response) {
                if (response.isSuccessful()) {
                    Reserva reserva = response.body();
                    if (reserva != null) {
                        Toast.makeText(EditarReservaActivity.this, "Success", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(EditarReservaActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                    }
                }
                finish();

            }

            @Override
            public void onFailure(Call<Reserva> call, Throwable t) {
                Toast.makeText(EditarReservaActivity.this, "Peticcion fallida:" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void flag (android.view.View view){
        Boolean estado = flagAsistir.isChecked();
        Reserva reserva = new Reserva();
        reserva.setIdReserva(idReserva.getText().toString());
        reserva.setObservacion(observacion.getText().toString());
        if(estado) {
            reserva.setFlagAsistio("S");
        }
            Call<Reserva> call = ApiAdapter.getApiService().updateReserva(reserva);
            call.enqueue(new Callback<Reserva>() {
                @Override
                public void onResponse(Call<Reserva> call, Response<Reserva> response) {
                    if (response.isSuccessful()) {
                        Reserva reserva = response.body();
                        if (reserva != null) {
                            Toast.makeText(EditarReservaActivity.this, "Success", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(EditarReservaActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                        }
                    }
                    finish();

                }

                @Override
                public void onFailure(Call<Reserva> call, Throwable t) {
                    Toast.makeText(EditarReservaActivity.this, "Peticcion fallida:" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            });
    }
}
