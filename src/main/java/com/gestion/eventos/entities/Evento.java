package com.gestion.eventos.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Entity
@Table(name = "eventos")
public class Evento {

    //Hacemos uso de los constructores sin lombok por tema id

    @Id
    private String id;
    private String nombre;
    private LocalDate fecha;
    private String ubicacion;
    private int capacidad;

    // Constructor por defecto
    public Evento() {
        this.id = UUID.randomUUID().toString();
    }

    // Constructor con todos los atributos excepto el id
    public Evento(String nombre, LocalDate fecha, String ubicacion, int capacidad) {
        this.id = UUID.randomUUID().toString();
        this.nombre = nombre;
        this.fecha = fecha;
        this.ubicacion = ubicacion;
        this.capacidad = capacidad;
    }







}
