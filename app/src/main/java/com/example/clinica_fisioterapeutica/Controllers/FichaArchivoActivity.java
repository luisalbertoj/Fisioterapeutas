package com.example.clinica_fisioterapeutica.Controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
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
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.regex.Pattern;

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

        if (requestCode == FILE_PICKER_REQUEST_CODE && resultCode == RESULT_OK) {
            String path = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);
            File file = new File(path);
            if (path != null) {
                Log.d("Path: ", path);
                pdfPath = path;
                Toast.makeText(this, "Picked file: " + path, Toast.LENGTH_LONG).show();
                uploadFile(file);
                txtFile.setText(path);
            }
        }

    }

    public void uploadFile(File filee) {
        File file = filee;
        String id_archivo= "35";
        FichaArchivo ficha = new FichaArchivo();
        ficha.setFile(file);
        ficha.setIdFichaClinica(id_archivo);
        ficha.setNombre(file.getName());
        /*RequestBody requestBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("myfile", file.getName(), requestBody);
        MultipartBody.Part nombre = MultipartBody.Part()*/
        Call<ResponseFichaArchivo> call = ApiAdapter.getApiService().uploadFile(ficha);
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
                .withActivity(this)
                .withRequestCode(FILE_PICKER_REQUEST_CODE)
                .withFilter(Pattern.compile(".*\\.pdf$"))
                .withFilterDirectories(true)
                .withHiddenFiles(true)
                .withTitle("Select PDF file")
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
