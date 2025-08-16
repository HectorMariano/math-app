package com.example.genio10;

public class Actividad {
    private String Unidad;
    private String Fecha;
    private String Num_Ejercicios;
    private int Aciertos;
    private int Fallos;

    public Actividad(){

    }

    public Actividad(String unidad, String fecha, String num_Ejercicios, int aciertos, int fallos) {
        this.Unidad = unidad;
        this.Fecha = fecha;
        this.Num_Ejercicios = num_Ejercicios;
        this.Aciertos = aciertos;
        this.Fallos = fallos;
    }

    public String getUnidad() {
        return Unidad;
    }

    public void setUnidad(String unidad) {
        Unidad = unidad;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String fecha) {
        Fecha = fecha;
    }

    public String getNum_Ejercicios() {
        return Num_Ejercicios;
    }

    public void setNum_Ejercicios(String num_Ejercicios) {
        Num_Ejercicios = num_Ejercicios;
    }

    public int getAciertos() {
        return Aciertos;
    }

    public void setAciertos(int aciertos) {
        Aciertos = aciertos;
    }

    public int getFallos() {
        return Fallos;
    }

    public void setFallos(int fallos) {
        Fallos = fallos;
    }
}
