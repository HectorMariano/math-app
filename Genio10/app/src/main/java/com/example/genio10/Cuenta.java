package com.example.genio10;

public class Cuenta {
    private String Nombre;

    public Cuenta(){

    }

    public Cuenta(String Nombre){
        this.Nombre = Nombre;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }
}
