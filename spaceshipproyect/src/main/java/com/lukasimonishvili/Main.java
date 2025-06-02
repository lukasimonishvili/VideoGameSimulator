package com.lukasimonishvili;

public class Main {
    public static void main(String[] args) {
       Mision mision1=new MisionExploracion("Exploración de Marte", 30, 5, 2000);
        
        System.out.println("Misión: " + mision1.getNombre());
        System.out.println("Duración: " + mision1.getDuracion() + " días");
        System.out.println("Prioridad: " + mision1.getPrioridad());
        System.out.println("Estado: " + mision1.getEstado());
        System.out.println("Experiencia requerida: " + mision1.getExperienciaRequerida());
        
        mision1.completarMision(); 
    }
}