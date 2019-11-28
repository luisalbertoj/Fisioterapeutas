package com.example.clinica_fisioterapeutica.Controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.clinica_fisioterapeutica.Models.ResponseFichaArchivo;
import com.example.clinica_fisioterapeutica.Models.FichaArchivo;
import com.example.clinica_fisioterapeutica.R;
import com.example.clinica_fisioterapeutica.Services.ApiAdapter;


import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

import okio.Buffer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FichaArchivoActivity extends AppCompatActivity {
    Handler mainThreadHandler = new Handler();
    private TextView txtRetrofitResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ficha_clinica);

    }
    public void retroUploadFile(View view) {
        uploadFile();
    }

    public void uploadFile() {
        File file = new File(getCacheDir(), "fileToUpload.txt");

        Uri uri = Uri.fromFile(file);
      //  Long id_archivo= ApiAdapter.getApiService().getIdFichaClinica();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("myfile", file.getName(), requestBody);
     //   MultipartBody.Part nombre = MultipartBody.Part()
     //   Call<ResponseFichaArchivo> call = ApiAdapter.getApiService().uploadFile(body, nombre);
     //   dumpCallableResponse(call);
    }
    private <T> void dumpCallableResponse(Call<T> callableResponse) {
        //TODO: request() has to be called in a background thread, since it makes some heavy work
        Request request = callableResponse.request();
        try {
            Buffer buffer = new Buffer();
            String show = request.toString() + "headers: " + request.headers() + "\n";
            if (request.body() != null) {
                request.body().writeTo(buffer);
                show += "Body : " + buffer.readString(Charset.defaultCharset());
            }

            updateResult(show + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
        callableResponse.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
             /*   try {
                 //   updateResult(txtRetrofitResult.getText() + ("\nResponse : " + getStrFromResponseBody(response)));
                } catch (IOException e) {
                    e.printStackTrace();
               }*/
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                String str = "onFailure";
                if (t instanceof Exception)
                    str += ((Exception) t).getMessage();
              //  Toast.makeText(ActivityRetrofitDemo.this, str, Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void updateResult(String myResponse) {
        Message msg = Message.obtain();
        msg.obj = myResponse;
        mainThreadHandler.sendMessage(msg);
        //Message msg = Message.obtain(mainThreadHandler);
        //msg.sendToTarget();
    }

}
