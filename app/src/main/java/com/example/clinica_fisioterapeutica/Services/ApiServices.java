package com.example.clinica_fisioterapeutica.Services;


import com.example.clinica_fisioterapeutica.Models.FichaArchivo;
import com.example.clinica_fisioterapeutica.Models.ResponseFichaArchivo;

import com.example.clinica_fisioterapeutica.Models.FichaClinica;
import com.example.clinica_fisioterapeutica.Models.Persona;
import com.example.clinica_fisioterapeutica.Models.ResponseFichaClinica;

import com.example.clinica_fisioterapeutica.Models.ResponsePersona;
import com.example.clinica_fisioterapeutica.Models.ResponseReserva;
import com.example.clinica_fisioterapeutica.Models.Reserva;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;

import retrofit2.http.Part;

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

    // --------------- Rutas Ficha --------------
    @GET("fichaClinica")
    Call<ResponseFichaClinica> getFichas(
            @Query("orderBy")String orderBy,
            @Query("orderDir")String orderDir
    );
    @GET("fichaClinica/{idFichaClinica}")
    Call<FichaClinica> getFicha(
            @Path("idFichaClinica") String idFichaClinica
    );
    @GET("fichaClinica")
    Call<ResponseFichaClinica> getFichaLike(
            @Query("orderBy")String orderBy,
            @Query("orderDir")String orderDir,
            @Query("like") String like,
            @Query("ejemplo") String ejemplo
    );
    @Headers({
            "Content-Type: application/json",
            "usuario:gustavo"
    })
    @POST("fichaClinica")
    Call<FichaClinica> createFicha(@Body FichaClinica fichaClinica);

    @PUT("fichaClinica")
    Call<FichaClinica> updateFicha(@Body FichaClinica fichaClinica);

    @DELETE("fichaClinica/{idFichaClinica}")
    Call<FichaClinica> deleteFicha(
            @Path("idFichaClinica") String idFichaClinica
    );

    @Multipart
    @POST("fichaArchivo/archivo")
            Call<ResponseFichaArchivo> uploadFile(
            @Part MultipartBody.Part file,
            @Part MultipartBody.Part nombre,
            @Part MultipartBody.Part idFichaClinica
            );

    @GET("fichaArchivo")
    Call<ResponseFichaArchivo> getFichaArchivo(
            @Query("idFichaClinica") String idFichaClinica
    );

    @DELETE("fichaArchivo/{idFichaArchivo}")
    Call<FichaArchivo> deleteFichaArchivo(
            @Path("idFichaArchivo") String idFichaArchivo
    );
    // -----------------------------------------------

    // --------------- Rutas Turnos --------------

    @GET("reserva")
    Call<ResponseReserva> getReservas(
            @Query("orderBy")String orderBy,
            @Query("orderDir")String orderDir,
            @Query("ejemplo") String ejemplo
    );
    @GET("reserva/{idReserva}")
    Call<Reserva> getReserva(
            @Path("idReserva") String idReserva
    );
    @GET("reserva")
    Call<ResponseReserva> getReservasLike(
            @Query("orderBy")String orderBy,
            @Query("orderDir")String orderDir,
            @Query("like") String like,
            @Query("ejemplo") String ejemplo
    );
    @Headers({
            "Content-Type: application/json",
            "usuario:gustavo"
    })
    @POST("reserva")
    Call<Reserva> createReserva(@Body Reserva reserva);

    @Headers({
            "Content-Type: application/json"
    })
    @PUT("reserva")
    Call<Reserva> updateReserva(@Body Reserva reserva);

    @DELETE("reserva/{idReserva}")
    Call<Reserva> deleteReserva(
            @Path("idReserva") String idReserva

    );
    @GET("persona/{idPersona}/agenda")
    Call<List<Reserva>> getAgenda(
            @Path("idPersona") String idPersona,
            @Query("fecha") String fecha,
            @Query("disponible") String disponible
    );
}
