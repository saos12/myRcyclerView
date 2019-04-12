package com.saos.admin.myrcyclerview;

import java.io.Serializable;

/**
 * Created by Admin on 12/09/2018.
 */

public class ActivityModel implements Serializable {

    private int id;
    private String nombre;
    private double duracion;
    private String ubicacion;
    private String descripcion;
    private String fechaInicio;


    public ActivityModel(int id, String nombre, double duracion, String ubicacion, String descripcion, String fechaInicio) {
        this.id = id;
        this.nombre = nombre;
        this.duracion = duracion;
        this.ubicacion = ubicacion;
        this.descripcion = descripcion;
        this.fechaInicio = fechaInicio;
    }

    public ActivityModel(String nombre, double duracion, String ubicacion, String descripcion, String fechaInicio) {
        this.nombre = nombre;
        this.duracion = duracion;
        this.ubicacion = ubicacion;
        this.descripcion = descripcion;
        this.fechaInicio = fechaInicio;
    }

    public ActivityModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getDuracion() {
        return duracion;
    }

    public void setDuracion(double duracion) {
        this.duracion = duracion;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }
}
