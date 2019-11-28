package com.example.clinica_fisioterapeutica.Controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.clinica_fisioterapeutica.Models.ResponseFichaArchivo;
import com.example.clinica_fisioterapeutica.Models.FichaArchivo;
import com.example.clinica_fisioterapeutica.R;
import com.example.clinica_fisioterapeutica.Services.ApiAdapter;


import java.io.File;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FichaArchivoActivity extends AppCompatActivity {
    Handler mainThreadHandler = new Handler();
    private TextView txtRetrofitResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ficha_archivo);

    }
    public void retroUploadFile(View view) {
        uploadFile();
    }

    public void uploadFile() {
        File file = new File(getCacheDir(), "fileToUpload.txt");

        Uri uri = Uri.fromFile(file);
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


}
