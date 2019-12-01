package com.example.clinica_fisioterapeutica.Controllers.Turnos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Person;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.clinica_fisioterapeutica.Controllers.Fichas.AgregarEditarFichaActivity;
import com.example.clinica_fisioterapeutica.Controllers.Paciente.PacientesActivity;
import com.example.clinica_fisioterapeutica.Models.Persona;
import com.example.clinica_fisioterapeutica.Models.Reserva;
import com.example.clinica_fisioterapeutica.Models.ResponseReserva;
import com.example.clinica_fisioterapeutica.R;
import com.example.clinica_fisioterapeutica.Services.ApiAdapter;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;

import java.io.File;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AgregarReservaActivity extends AppCompatActivity {
    Intent intentNewActivity;
    CalendarView calendar;
    String idEmpleado;
    String idCliente;
    TextView empleado;
    TextView cliente;
    RecyclerView rvAgenda;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_reserva);
        empleado = findViewById(R.id.empleado);
        cliente = findViewById(R.id.cliente);
        calendar = findViewById(R.id.calendarAgenda);
        rvAgenda = findViewById(R.id.rvAgenda);
        LinearLayoutManager layRec =  new LinearLayoutManager(this);
        rvAgenda.setLayoutManager(layRec);
        rvAgenda.setHasFixedSize(true);
        empleado.setText("gustavo");
        idEmpleado = "3";
        fechaHoy();
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int dayOfMonth) {
                String fecha = "";
                fecha += ""+year;
                fecha += "" + ((month+1)<10?"0"+(month+1):(month+1));
                fecha += "" + (dayOfMonth<10?"0"+dayOfMonth:dayOfMonth);
                cargarAgenda(fecha);

            }
        });
        rvAgenda.setVisibility(View.INVISIBLE);
    }

    public void cargarAgenda(String fecha) {
        Call<List<Reserva>> call = ApiAdapter.getApiService().getAgenda(idEmpleado, fecha, "S");
        call.enqueue(new Callback<List<Reserva>>() {
            @Override
            public void onResponse(Call<List<Reserva>> call, Response<List<Reserva>> response) {
                cargarLista(response.body());
            }

            @Override
            public void onFailure(Call<List<Reserva>> call, Throwable t) {
                Log.w("warning",t.getCause().toString());
            }
        });
    }

    private void cargarLista(List<Reserva> reservas){
        Reserva[] lista = new Reserva[reservas.size()];
        for (int i = 0; i< reservas.size(); i++) {
            lista[i] = reservas.get(i);
        }
        Log.v("debug: " , "Renderiza reclicler");
        AdapterTurno adapter=new AdapterTurno(lista);
        final Reserva[] lista1=lista;
        rvAgenda.setAdapter(adapter);

        adapter.setListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                agregarReserva(lista1[rvAgenda.getChildAdapterPosition(view)]);
            }
        });
    }
    public void agregarReserva(Reserva reserva) {
        reserva.setIdCliente(new Persona(idCliente));
        Reserva reservaFormat = new Reserva();
        reservaFormat.setFechaCadena(reserva.getFechaCadena());
        reservaFormat.setHoraInicioCadena(reserva.getHoraInicioCadena());
        reservaFormat.setHoraFinCadena(reserva.getHoraFinCadena());
        reservaFormat.setIdEmpleado(new Persona(idEmpleado));
        reservaFormat.setIdCliente(new Persona(idCliente));
        Call<Reserva> call = ApiAdapter.getApiService().createReserva(reservaFormat);
        call.enqueue(new Callback<Reserva>() {
            @Override
            public void onResponse(Call<Reserva> call, Response<Reserva> response) {
                Toast.makeText(AgregarReservaActivity.this, "Succes", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<Reserva> call, Throwable t) {
                Log.w("warning",t.getCause().toString());
            }
        });
    }
    public String fechaHoy() {
        String fecha;
        Calendar calendarr = Calendar.getInstance(TimeZone.getDefault());
        calendar.setDate(calendarr.getTimeInMillis(), true, true);
        fecha = "" + calendarr.get(Calendar.YEAR);
        fecha += "" + (calendarr.get(Calendar.MONTH)+1);
        fecha += calendarr.get(Calendar.DATE)<10? "0" + calendarr.get(Calendar.DATE):"" + calendarr.get(Calendar.DATE);
        Log.v("Fecha hoy:" , fecha);
        return fecha;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Log.v("resultView", data.getDataString());
            String split[] = data.getDataString().split("-");
            if(split[0].equals("empleado")) {
                Log.v("debug:", "entro hasta aqui");
                idEmpleado = split[1];
                empleado.setText(split[2]);
            } else if(split[0].equals("cliente")) {
                idCliente = split[1];
                cliente.setText(split[2]);
                rvAgenda.setVisibility(View.VISIBLE);
            }
        }

    }

    public void buscarEmpleado(android.view.View view) {
        intentNewActivity = new Intent(AgregarReservaActivity.this, PacientesActivity.class);
        Bundle b = new Bundle();
        b.putString("viewFicha","empleado");
        intentNewActivity.putExtras(b);
        startActivityForResult(intentNewActivity, 1);

    }
    public void buscarCliente(android.view.View view) {
        intentNewActivity = new Intent(AgregarReservaActivity.this, PacientesActivity.class);
        Bundle b = new Bundle();
        b.putString("viewFicha","cliente");
        intentNewActivity.putExtras(b);
        startActivityForResult(intentNewActivity, 1);
    }
}
