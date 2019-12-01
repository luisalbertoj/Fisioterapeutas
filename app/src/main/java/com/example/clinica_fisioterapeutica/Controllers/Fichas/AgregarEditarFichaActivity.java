package com.example.clinica_fisioterapeutica.Controllers.Fichas;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.clinica_fisioterapeutica.Models.FichaClinica;
import com.example.clinica_fisioterapeutica.Models.Persona;
import com.example.clinica_fisioterapeutica.Models.TipoProducto;
import com.example.clinica_fisioterapeutica.R;
import com.example.clinica_fisioterapeutica.Services.ApiAdapter;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AgregarEditarFichaActivity extends AppCompatActivity {

    TextView idFichaClinica;
    TextView motivoConsulta;
    TextView diagnostico;
    TextView observaciones;
    TextView empleado;
    TextView cliente;
    TextView servicio;
    Button btnEliminar;
    Button btnEmpleado;
    Button btnCliente;
    Button btnServicio;
    String idEmpleado;
    String idCliente;
    String idTipoProducto;


    Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_editar_ficha);
        idFichaClinica = findViewById(R.id.idFichaClinica);
        motivoConsulta = findViewById(R.id.motivoConsulta);
        diagnostico = findViewById(R.id.diagnostico);
        observaciones = findViewById(R.id.observacion);
        empleado = findViewById(R.id.empleado);
        cliente = findViewById(R.id.cliente);
        servicio = findViewById(R.id.producto);
        btnEliminar = findViewById(R.id.btnEliminar);
        btnEmpleado = findViewById(R.id.btnEmpleado);
        btnCliente = findViewById(R.id.btnCliente);
        btnServicio = findViewById(R.id.btnServicio);

        Bundle bundle = this.getIntent().getExtras();
        if(bundle != null && bundle.containsKey("idFichaClinica")){
            idFichaClinica.setText(bundle.getString("idFichaClinica"));
            motivoConsulta.setText(bundle.getString("motivoConsulta"));
            this.cargarModelo(bundle.getString("idFichaClinica"));
            btnEliminar.setVisibility(View.VISIBLE);
            btnEmpleado.setVisibility(View.INVISIBLE);
            btnCliente.setVisibility(View.INVISIBLE);
            btnServicio.setVisibility(View.INVISIBLE);

        } else {
            btnEliminar.setVisibility(View.INVISIBLE);
            idFichaClinica.setVisibility(View.INVISIBLE);
            motivoConsulta.setEnabled(true);
            diagnostico.setEnabled(true);
            btnEmpleado.setEnabled(true);
            btnCliente.setEnabled(true);
            btnServicio.setEnabled(true);
        }

    }

    public void cargarModelo(String idModelo) {
        Call<FichaClinica> call = ApiAdapter.getApiService().getFicha(idModelo);
        call.enqueue(new Callback<FichaClinica>() {
            @Override
            public void onResponse(Call<FichaClinica> call, Response<FichaClinica> response) {
                if (response.isSuccessful()) {
                    FichaClinica ficha = response.body();
                    if (ficha != null) {
                        idFichaClinica.setText(ficha.getIdFichaClinica());
                        motivoConsulta.setText(ficha.getMotivoConsulta());
                        diagnostico.setText(ficha.getDiagnostico());
                        observaciones.setText(ficha.getObservacion());
                        empleado.setText(ficha.getIdEmpleado().getNombre());
                        cliente.setText(ficha.getIdCliente().getNombre());
                        try {
                            servicio.setText(ficha.getIdTipoProducto().getDescripcion());
                        } catch (Exception e) {
                            servicio.setText(ficha.getIdTipoProducto().getIdTipoProducto());
                        }

                        return;
                    } else {
                        Toast.makeText(AgregarEditarFichaActivity.this, "Ficha no encontrada", Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<FichaClinica> call, Throwable t) {
                Toast.makeText(AgregarEditarFichaActivity.this, "Peticcion fallida:" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void AgregarEditarPersona(View view) {
        FichaClinica ficha = new FichaClinica();
        if(!idFichaClinica.getText().toString().equals("")) {
            ficha.setIdFichaClinica(idFichaClinica.getText().toString());
        }
        ficha.setObservacion(observaciones.getText().toString());
        ficha.setMotivoConsulta(motivoConsulta.getText().toString());
        ficha.setDiagnostico(diagnostico.getText().toString());
        ficha.setIdEmpleado(new Persona(idEmpleado));
        ficha.setIdCliente(new Persona(idCliente));
        ficha.setIdTipoProducto(new TipoProducto(idTipoProducto));

        Call<FichaClinica> callFicha= idFichaClinica.getText().toString().equals("")?ApiAdapter.getApiService().createFicha(ficha):ApiAdapter.getApiService().updateFicha(ficha);
        callFicha.enqueue(new Callback<FichaClinica>() {
            @Override
            public void onResponse(Call<FichaClinica> call, Response<FichaClinica> response) {
                Toast.makeText(AgregarEditarFichaActivity.this,"Agregado Correctamente",Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<FichaClinica> call, Throwable t) {
                Log.w("warning",t.getCause());
                if(t.getCause() == null) {
                    finish();
                }
                Toast.makeText(AgregarEditarFichaActivity.this,"Error: " + t.getCause(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void eliminarModelo(View view) {
        Call<FichaClinica> call = ApiAdapter.getApiService().deleteFicha(idFichaClinica.getText().toString());
        call.enqueue(new Callback<FichaClinica>() {
            @Override
            public void onResponse(Call<FichaClinica> call, Response<FichaClinica> response) {
                Toast.makeText(AgregarEditarFichaActivity.this,"Eliminacion correcta",Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<FichaClinica> call, Throwable t) {
                Log.w("warning",t.getCause());
                Toast.makeText(AgregarEditarFichaActivity.this,"Error: " + t.getCause(),Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    public void buscarEmpleado(android.view.View view) {
        idEmpleado = "4";
        empleado.setText("Luis");
    }
    public void buscarCliente(android.view.View view) {
        idCliente = "6";
        cliente.setText("Luis");
    }
    public void buscarTipoProducto(android.view.View view) {
        idTipoProducto = "3";
        servicio.setText("Luis");
    }
}
