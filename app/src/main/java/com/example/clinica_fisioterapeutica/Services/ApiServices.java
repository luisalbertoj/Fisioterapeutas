package com.example.clinica_fisioterapeutica.Services;

import com.example.clinica_fisioterapeutica.Models.Persona;
import com.example.clinica_fisioterapeutica.Models.ResponsePersona;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiServices {

    // --------------- Rutas Persona --------------
    @GET("persona")
    Call<ResponsePersona> getPersonas(
            @Query("orderBy")String orderBy,
            @Query("orderDir")String orderDir
    );
    @GET("persona/{idPersona}")
    Call<Persona> getPersona(
            @Path("idPersona") String idPersona
    );
    @GET("persona")
    Call<ResponsePersona> getPersonasLike(
            @Query("orderBy")String orderBy,
            @Query("orderDir")String orderDir,
            @Query("like") String like,
            @Query("ejemplo") String ejemplo
    );

    @POST("persona")
    Call<Persona> createPersona(@Body Persona persona);

    @PUT("persona")
    Call<Persona> updatePersona(@Body Persona persona);

    @DELETE("persona/{idPersona}")
    Call<Persona> deletePersona(
            @Path("idPersona") String idPersona
    );

    // -----------------------------------------------
}
