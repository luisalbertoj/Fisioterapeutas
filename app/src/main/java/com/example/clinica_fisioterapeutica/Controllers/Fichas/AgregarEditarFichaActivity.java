package com.example.clinica_fisioterapeutica.Controllers.Fichas;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.clinica_fisioterapeutica.Controllers.Paciente.PacientesActivity;
import com.example.clinica_fisioterapeutica.Models.Categoria;
import com.example.clinica_fisioterapeutica.Models.FichaArchivo;
import com.example.clinica_fisioterapeutica.Models.FichaClinica;
import com.example.clinica_fisioterapeutica.Models.Persona;
import com.example.clinica_fisioterapeutica.Models.ResponseCategoria;
import com.example.clinica_fisioterapeutica.Models.ResponseFichaArchivo;
import com.example.clinica_fisioterapeutica.Models.ResponseTipoproducto;
import com.example.clinica_fisioterapeutica.Models.TipoProducto;
import com.example.clinica_fisioterapeutica.R;
import com.example.clinica_fisioterapeutica.Services.ApiAdapter;
import com.github.barteksc.pdfviewer.PDFView;
import com.nbsp.materialfilepicker.MaterialFilePicker;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;

import java.io.File;
import java.util.Calendar;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AgregarEditarFichaActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    TextView idFichaClinica;
    TextView motivoConsulta;
    TextView diagnostico;
    TextView observaciones;
    TextView empleado;
    TextView cliente;
    TextView servicio;
    private static String[] categorias;
    private static String[] subCategorias;
    TextView txtFile;
    private String pdfFileName;
    private PDFView pdfView;
    public ProgressDialog pDialog;
    public static final int FILE_PICKER_REQUEST_CODE = 1;
    private String pdfPath;
    Spinner spinnerCategoria;
    Spinner spinnerSubcategoria;
    String idCategoria = "";
    String idSubCategoria = "";
    private Spinner spinner;
    private static String[] paths;
    String archivoEliminar = "";
    Button btnEliminarArchivo;
    Button btnEliminar;
    Button btnEmpleado;
    Button btnCliente;
    Button btnServicio;
    Button btnAgregarArchivo;
    String idEmpleado;
    String idCliente;
    String idTipoProducto;

    Intent intentNewActivity;
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
        btnEliminarArchivo = findViewById(R.id.btnEliminarArchivo);
        btnAgregarArchivo = findViewById(R.id.btnAgregarArchivo);
        spinner = findViewById(R.id.spinnerArchivos);
        servicio = findViewById(R.id.servicio);
        spinnerCategoria = findViewById(R.id.spinnerCategoria);
        spinnerSubcategoria = findViewById(R.id.spinnerSubCategoria);

        txtFile = findViewById(R.id.txtFile);
        initDialog();

        Bundle bundle = this.getIntent().getExtras();
        if(bundle != null && bundle.containsKey("idFichaClinica")){
            idFichaClinica.setText(bundle.getString("idFichaClinica"));
            motivoConsulta.setText(bundle.getString("motivoConsulta"));
            this.cargarModelo(bundle.getString("idFichaClinica"));
            btnEliminar.setVisibility(View.VISIBLE);
            btnEmpleado.setVisibility(View.INVISIBLE);
            btnCliente.setVisibility(View.INVISIBLE);

            spinnerCategoria.setVisibility(View.INVISIBLE);
            spinnerSubcategoria.setVisibility(View.INVISIBLE);

        } else {
            btnEliminar.setVisibility(View.INVISIBLE);
            idFichaClinica.setVisibility(View.INVISIBLE);
            btnEliminarArchivo.setVisibility(View.INVISIBLE);
            spinner.setVisibility(View.INVISIBLE);
            txtFile.setVisibility(View.INVISIBLE);
            btnAgregarArchivo.setVisibility(View.INVISIBLE);
            motivoConsulta.setEnabled(true);
            servicio.setVisibility(View.INVISIBLE);
            diagnostico.setEnabled(true);
            btnEmpleado.setEnabled(true);
            btnCliente.setEnabled(true);
        }

        paths = new String[]{"No tiene Archivos"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(AgregarEditarFichaActivity.this,
                android.R.layout.simple_spinner_dropdown_item,paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(AgregarEditarFichaActivity.this);
        btnEliminarArchivo.setVisibility(View.INVISIBLE);

        buscarTipoProducto();
        cargarCategorias();
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
                        loadSpinner();
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
        intentNewActivity = new Intent(AgregarEditarFichaActivity.this, PacientesActivity.class);
        Bundle b = new Bundle();
        b.putString("viewFicha","empleado");
        intentNewActivity.putExtras(b);
        startActivityForResult(intentNewActivity, 1);

    }
    public void buscarCliente(android.view.View view) {
        intentNewActivity = new Intent(AgregarEditarFichaActivity.this, PacientesActivity.class);
        Bundle b = new Bundle();
        b.putString("viewFicha","cliente");
        intentNewActivity.putExtras(b);
        startActivityForResult(intentNewActivity, 1);
    }
    public void buscarTipoProducto() {
        idTipoProducto = "3";
        servicio.setText("Luis");
    }

    public void eliminarArchivoFicha(android.view.View view) {
        if(archivoEliminar.equals("")) {
            return;
        }
        Call<FichaArchivo> call = ApiAdapter.getApiService().deleteFichaArchivo(archivoEliminar);
        call.enqueue(new Callback<FichaArchivo>() {
            @Override
            public void onResponse(Call<FichaArchivo> call, Response<FichaArchivo> response) {
                Toast.makeText(AgregarEditarFichaActivity.this,"Eliminacion correcta",Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<FichaArchivo> call, Throwable t) {
                Log.w("warning",t.getCause());
                Toast.makeText(AgregarEditarFichaActivity.this,"Error: " + t.getCause(),Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        archivoEliminar = "";
    }

    public void launchPicker(android.view.View view) {
        new MaterialFilePicker()
                .withActivity(AgregarEditarFichaActivity.this)
                .withRequestCode(1000)
                .withHiddenFiles(true)
                .start();
    }

    protected void initDialog() {
        pDialog = new ProgressDialog(AgregarEditarFichaActivity.this);
        pDialog.setMessage(getString(R.string.msg_loading));
        pDialog.setCancelable(true);
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
            } else if(split[0].equals("servicio")) {
                idTipoProducto = split[1];
                servicio.setText(split[2]);
            } else {
                String path = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);
                File file = new File(path);
                if (path != null) {
                    Log.d("Path: ", path);
                    pdfPath = path;
                    Toast.makeText(this, "Picked file: " + path, Toast.LENGTH_LONG).show();
                    txtFile.setText(path);
                    uploadFile(file);
                } else {
                    Toast.makeText( AgregarEditarFichaActivity.this, "Error al leer el archivo", Toast.LENGTH_SHORT).show();
                }
            }
        }

    }

    public void uploadFile(File filee) {
        FichaArchivo ficha = new FichaArchivo();
        ficha.setFile(filee);
        ficha.setIdFichaClinica(idFichaClinica.getText().toString());
        ficha.setNombre(filee.getName());
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/octet-stream"), ficha.getFile());
        MultipartBody.Part file = MultipartBody.Part.createFormData("file", ficha.getNombre(), requestBody);
        MultipartBody.Part nombre = MultipartBody.Part.createFormData("motivoConsulta", ficha.getNombre());
        MultipartBody.Part idFichaClinica = MultipartBody.Part.createFormData("idFichaClinica", ficha.getIdFichaClinica());
        Call<ResponseFichaArchivo> call = ApiAdapter.getApiService().uploadFile(file, nombre, idFichaClinica);
        call.enqueue(new Callback<ResponseFichaArchivo>() {
            @Override
            public void onResponse(Call<ResponseFichaArchivo> call,
                                   Response<ResponseFichaArchivo> response) {
                Toast.makeText( AgregarEditarFichaActivity.this, "Carga del archivo exitosa", Toast.LENGTH_SHORT).show();
                Log.v("Upload", "success");
                loadSpinner();
            }

            @Override
            public void onFailure(Call<ResponseFichaArchivo> call, Throwable t) {
                Toast.makeText( AgregarEditarFichaActivity.this, "Upload error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("Upload error:", t.getMessage());
            }
        });
    }

    public void loadSpinner() {
        if(!idFichaClinica.getText().toString().equals("")) {
            Call<ResponseFichaArchivo> call= ApiAdapter.getApiService().getFichaArchivo(idFichaClinica.getText().toString());
            call.enqueue(new Callback<ResponseFichaArchivo>() {
                @Override
                public void onResponse(Call<ResponseFichaArchivo> call, Response<ResponseFichaArchivo> response) {
                    if (response.isSuccessful()) {
                        List<FichaArchivo> fichas = response.body().getLista();
                        if (fichas != null && (response.body().getTotalDatos() != 0)) {
                            Log.v("debug:", "entro a poblar");
                            paths = new String[fichas.size()];
                            int i = 0;
                            for (FichaArchivo ficha: fichas) {
                                paths[i] = ficha.getIdFichaArchivo() + "-" + ficha.getNombre();
                                i++;
                            }
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(AgregarEditarFichaActivity.this,
                                    android.R.layout.simple_spinner_dropdown_item,paths);

                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinner.setAdapter(adapter);
                            spinner.setOnItemSelectedListener(AgregarEditarFichaActivity.this);
                            btnEliminarArchivo.setVisibility(View.VISIBLE);
                        } else {
                            Log.v("debug:", "entro al else");
                            paths = new String[]{"No tiene Archivos"};
                        }
                    }
                }

                @Override
                public void onFailure(Call<ResponseFichaArchivo> call, Throwable t) {
                    Log.w("warning",t.getCause());
                    paths = new String[]{"No tiene Archivos"};
                }
            });
        }

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
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(AgregarEditarFichaActivity.this,
                                android.R.layout.simple_spinner_dropdown_item, categorias);

                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerCategoria.setAdapter(adapter);
                        spinnerCategoria.setOnItemSelectedListener(AgregarEditarFichaActivity.this);
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
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(AgregarEditarFichaActivity.this,
                                android.R.layout.simple_spinner_dropdown_item, subCategorias);

                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerSubcategoria.setAdapter(adapter);
                        spinnerSubcategoria.setOnItemSelectedListener(AgregarEditarFichaActivity.this);
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
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
        String split[] = categorias[position].split("-");
        if(split[0].equalsIgnoreCase("Seleccionar")) {
            return;
        }
        else if(split[0].equalsIgnoreCase("subcategoria")) {
            idSubCategoria = split[1];
        } else {
            idCategoria = split[0];
            cargarSubCategorias();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // TODO Auto-generated method stub
    }


}
