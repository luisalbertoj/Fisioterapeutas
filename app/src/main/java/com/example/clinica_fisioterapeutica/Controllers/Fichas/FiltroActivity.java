package com.example.clinica_fisioterapeutica.Controllers.Fichas;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.clinica_fisioterapeutica.Controllers.Paciente.PacientesActivity;
import com.example.clinica_fisioterapeutica.Models.Categoria;
import com.example.clinica_fisioterapeutica.Models.ResponseCategoria;
import com.example.clinica_fisioterapeutica.Models.ResponseTipoproducto;
import com.example.clinica_fisioterapeutica.Models.TipoProducto;
import com.example.clinica_fisioterapeutica.R;
import com.example.clinica_fisioterapeutica.Services.ApiAdapter;

import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FiltroActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener  {
    Intent intentNewActivity;
    Intent returnView;
    Bundle bundle;
    String idEmpleado = "";
    String idCliente = "";
    String idCategoria = "";
    String idSubCategoria = "";
    TextView empleado;
    TextView cliente;
    Spinner spinnerCategoria;
    Spinner spinnerSubcategoria;
    Calendar calendar;
    DatePickerDialog dataPiker;
    TextView fechaInicio;
    String fechaInicioText = "";
    String fechaFinText = "";
    DatePickerDialog dataPiker2;
    TextView fechaFin;

    private static String[] categorias;
    private static String[] subCategorias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtro);
        empleado = findViewById(R.id.empleado);
        cliente = findViewById(R.id.cliente);
        fechaInicio = findViewById(R.id.fechaInicio);
        fechaFin = findViewById(R.id.fechaFin);
        spinnerCategoria = findViewById(R.id.spinnerCategoria);
        spinnerSubcategoria = findViewById(R.id.spinnerSubCategoria);
        returnView = new Intent();

    }

    @Override
    protected void onResume() {
        super.onResume();
        cargarCategorias();
    }
    public void buscarEmpleado(android.view.View view) {
        intentNewActivity = new Intent(FiltroActivity.this, PacientesActivity.class);
        Bundle b = new Bundle();
        b.putString("viewFicha","empleado");
        intentNewActivity.putExtras(b);
        startActivityForResult(intentNewActivity, 1);

    }
    public void buscarCliente(android.view.View view) {
        intentNewActivity = new Intent(FiltroActivity.this, PacientesActivity.class);
        Bundle b = new Bundle();
        b.putString("viewFicha","cliente");
        intentNewActivity.putExtras(b);
        startActivityForResult(intentNewActivity, 1);
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
            }
        }

    }

    public void openDataPicker(android.view.View view) {
        calendar = Calendar.getInstance();
        int day = 24;
        int month = 06;
        int year = 1996;
            day = calendar.get(Calendar.DAY_OF_MONTH);
            month = calendar.get(Calendar.MONTH);
            year = calendar.get(Calendar.YEAR);


        dataPiker = new DatePickerDialog(FiltroActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int myear, int mmonth, int mdayOfMonth) {
                fechaInicioText = ""+myear + (mmonth+1<10?(mmonth+1):"0"+(mmonth+1)) + (mdayOfMonth+1<10?(mdayOfMonth+1):"0"+(mdayOfMonth+1));
                fechaInicio.setText(myear + "-" + mmonth+1 + "-" + mdayOfMonth);
            }
        }, day, month, year);
        dataPiker.show();

    }
    public void openDataPicker2(android.view.View view) {
        calendar = Calendar.getInstance();
        int day = 24;
        int month = 06;
        int year = 1996;

            day = calendar.get(Calendar.DAY_OF_MONTH);
            month = calendar.get(Calendar.MONTH);
            year = calendar.get(Calendar.YEAR);


        dataPiker2 = new DatePickerDialog(FiltroActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int myear, int mmonth, int mdayOfMonth) {
                fechaFinText = ""+myear + (mmonth+1<10?(mmonth+1):"0"+(mmonth+1)) + (mdayOfMonth+1<10?(mdayOfMonth+1):"0"+(mdayOfMonth+1));
                fechaFin.setText(myear + "-" + mmonth+1 + "-" + mdayOfMonth);
            }
        }, day, month, year);
        dataPiker2.show();

    }

    public void cargarCategorias() {
            Call<ResponseCategoria> call= ApiAdapter.getApiService().getCategoria("idCategoria","asc");
            call.enqueue(new Callback<ResponseCategoria>() {
                @Override
                public void onResponse(Call<ResponseCategoria> call, Response<ResponseCategoria> response) {
                    if (response.isSuccessful()) {
                        List<Categoria> categoria = response.body().getLista();
                        if (categoria != null && (!response.body().getTotalDatos().equals("0"))) {
                            Log.v("debug:", "entro a poblar");
                            categorias = new String[categoria.size()];
                            categorias[0] = "Seleccionar";
                            for (int i=1; i<categoria.size(); i++) {
                                categorias[i] = categoria.get(i).getIdCategoria() + "-" + categoria.get(i).getDescripcion();

                            }
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(FiltroActivity.this,
                                    android.R.layout.simple_spinner_dropdown_item, categorias);

                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinnerCategoria.setAdapter(adapter);
                            spinnerCategoria.setOnItemSelectedListener(FiltroActivity.this);
                        } else {
                            Log.v("debug:", "entro al else");
                            categorias = new String[]{"No tiene Archivos"};
                        }
                    }
                }

                @Override
                public void onFailure(Call<ResponseCategoria> call, Throwable t) {
                    Log.w("warning",t.getCause());
                    categorias = new String[]{"No tiene Archivos"};
                }
            });
    }
    public void cargarSubCategorias() {
        String ejemplo = ("{ \"idCategoria\": {\n" +
                "                \"idCategoria\": "+idCategoria+"}}");
        Call<ResponseTipoproducto> call= ApiAdapter.getApiService().getTipoProducto(ejemplo);
        call.enqueue(new Callback<ResponseTipoproducto>() {
            @Override
            public void onResponse(Call<ResponseTipoproducto> call, Response<ResponseTipoproducto> response) {
                if (response.isSuccessful()) {
                    List<TipoProducto> tipoProductos = response.body().getLista();
                    if (tipoProductos != null && (!response.body().getTotalDatos().equals("0"))) {
                        Log.v("debug:", "entro a poblar");
                        subCategorias = new String[tipoProductos.size()];
                        subCategorias[0] = "Seleccionar";
                        for (int i=1; i<tipoProductos.size(); i++) {
                            subCategorias[i] = tipoProductos.get(i).getIdTipoProducto() + "-" + tipoProductos.get(i).getDescripcion();
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(FiltroActivity.this,
                                android.R.layout.simple_spinner_dropdown_item, subCategorias);

                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerSubcategoria.setAdapter(adapter);
                        spinnerSubcategoria.setOnItemSelectedListener(FiltroActivity.this);
                    } else {
                        Log.v("debug:", "entro al else");
                        subCategorias = new String[]{"No tiene Archivos"};
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseTipoproducto> call, Throwable t) {
                Log.w("warning",t.getCause());
                categorias = new String[]{"No tiene Archivos"};
            }
        });
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        String split[] = categorias[position].split("-");
        if(split[0].equalsIgnoreCase("Seleccionar")) {
            return;
        }
        else if(split[0].equalsIgnoreCase("subcategoria")) {
            idSubCategoria = split[1];
        } else {
            idCategoria = split[0];
            cargarSubCategorias();
            Log.v("infiito", "esta infinto");
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    public void aplicarFiltro(android.view.View view) {
        String query = "{\n" +
                "\t\"fechaDesdeCadena\": \""+fechaInicioText+"\",\n" +
                "\t\"fechaHastaCadena\": \""+fechaFinText+"\",\n" +
                "\t\"idCliente\": {\n" +
                "\t\t\"idPersona\": \""+idCliente+"\"\n" +
                "\t},\n" +
                "\t\"idEmpleado\": {\n" +
                "\t\t\"idPersona\": \""+idEmpleado+"\"\n" +
                "\t},\n" +
                "\t\"idTipoProducto\": {\n" +
                "\t\t\"idTipoProducto\": \""+idCategoria+"\"\n" +
                "\t}\n" +
                "}";
        returnView.setData(Uri.parse(query));
        setResult(RESULT_OK, returnView);
        finish();
    }
}
