package com.example.telematica.puroapp.modelo;

public class data {


    private String id;
    private String nombre_completo;
    private int edad;
    private String beca;
    private float mesada;

    public data(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre_completo() {
        return nombre_completo;
    }

    public void setNombre_completo(String nombre_completo) {
        this.nombre_completo = nombre_completo;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getBeca() {
        return beca;
    }

    public void setBeca(String beca) {
        this.beca = beca;
    }

    public float getMesada() {
        return mesada;
    }

    public void setMesada(float mesada) {
        this.mesada = mesada;
    }

    @Override
    public String toString() {
        return "Estudiante: \n" +
                "  id=" + id + "\n" +
                "  nombre_completo=" + nombre_completo + "\n" +
                "  edad=" + edad + "\n" +
                "  beca=" + beca + "\n" +
                "  mesada=" + mesada + "\n";
    }

}
