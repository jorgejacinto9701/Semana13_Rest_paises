package com.example.semana13.service;

import com.example.semana13.entity.Pais;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ServicePais {

    @GET("all")
    public abstract Call<List<Pais>> listaPais();

}
