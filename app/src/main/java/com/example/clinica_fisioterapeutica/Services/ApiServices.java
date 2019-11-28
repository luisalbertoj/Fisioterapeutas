package com.example.clinica_fisioterapeutica.Services;

import com.example.clinica_fisioterapeutica.Models.ResponseFichaArchivo;
import com.example.clinica_fisioterapeutica.Models.ResponsePersona;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiServices {
    @GET("persona")
    Call<ResponsePersona> getPersonas(
            @Query("orderBy")String orderBy,
            @Query("orderDir")String orderDir
    );

    @FormUrlEncoded
    @Multipart
    @POST("FichaArchivo/archivo")
    Call<ResponseFichaArchivo> uploadFile(
            @Part("file") MultipartBody.Part file,
            @Field("nombre") String nombre,
            @Field("idFichaClinica") Long idFichaClinica
    );

    @GET("login")
    Call<ResponsePersona> getLogin(
            @Query("username") String username,
            @Query("password") String password
    );

    @FormUrlEncoded
    @POST("product")
    Call<ResponsePersona> postNewProduct(
            @Field("code") String code,
            @Field("name") String name,
            @Field("description") String description
    );
}
