package com.example.clinica_fisioterapeutica.Services;

import com.example.clinica_fisioterapeutica.Models.ResponsePersona;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiServices {
    @GET("persona")
    Call<ResponsePersona> getPersonas(
            @Query("orderBy")String orderBy,
            @Query("orderDir")String orderDir
    );

    @GET("persona")
    Call<ResponsePersona> getPersonasLike(
            @Query("orderBy")String orderBy,
            @Query("orderDir")String orderDir,
            @Query("like") String like,
            @Query("ejemplo") String ejemplo
    );

    @FormUrlEncoded
    @POST("upload/photo")
    Call<ResponsePersona> postPhoto(
            @Field("image") String base64,
            @Field("extension") String extension,
            @Field("user_id") String user_id
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
