package com.example.clinica_fisioterapeutica.Controllers.Fichas;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.clinica_fisioterapeutica.Models.ResponseFichaArchivo;
import com.example.clinica_fisioterapeutica.Models.FichaArchivo;
import com.example.clinica_fisioterapeutica.R;
import com.example.clinica_fisioterapeutica.Services.ApiAdapter;
import com.github.barteksc.pdfviewer.PDFView;
import com.nbsp.materialfilepicker.MaterialFilePicker;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;


import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FichaArchivoActivity extends AppCompatActivity {
    Handler mainThreadHandler = new Handler();
    private TextView txtRetrofitResult;
    TextView txtFile;
    String imagepath;
    Intent intentU;

    private String pdfFileName;
    private PDFView pdfView;
    public ProgressDialog pDialog;
    public static final int FILE_PICKER_REQUEST_CODE = 1;
    private String pdfPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ficha_archivo);
        txtFile = findViewById(R.id.textFile);
        initDialog();

    }
    public void retroUploadFile(View view) {
        launchPicker();



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            String path = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);
            File file = new File(path);
            if (path != null) {
                Log.d("Path: ", path);
                pdfPath = path;
                Toast.makeText(this, "Picked file: " + path, Toast.LENGTH_LONG).show();
                txtFile.setText(path);
                uploadFile(file);
            } else {
                Toast.makeText(FichaArchivoActivity.this, "Error al leer el archivo", Toast.LENGTH_SHORT).show();
            }
        }

    }

    public void uploadFile(File filee) {
        FichaArchivo ficha = new FichaArchivo();
        ficha.setFile(filee);
        ficha.setIdFichaClinica("35");
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
                Log.v("Upload", "success");
            }

            @Override
            public void onFailure(Call<ResponseFichaArchivo> call, Throwable t) {
                Log.e("Upload error:", t.getMessage());
            }
        });
    }

    private void launchPicker() {
        new MaterialFilePicker()
                .withActivity(FichaArchivoActivity.this)
                .withRequestCode(1000)
                .withHiddenFiles(true)
                .start();
    }

    protected void initDialog() {

        pDialog = new ProgressDialog(this);
        pDialog.setMessage(getString(R.string.msg_loading));
        pDialog.setCancelable(true);
    }


    protected void showpDialog() {

        if (!pDialog.isShowing()) pDialog.show();
    }

    protected void hidepDialog() {

        if (pDialog.isShowing()) pDialog.dismiss();
    }


}
