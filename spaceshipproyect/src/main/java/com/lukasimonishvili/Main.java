package com.lukasimonishvili;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        RegistroMisiones registro=new RegistroMisiones();
        Scanner scanner=new Scanner(System.in);

        Mision mision1=new MisionExploracion("Exploración de Marte", 30, 5, 10, 1500);
        //registro.agregarMision();
        System.out.println("-----MISION-----");
        System.out.println("\nMisión: " + mision1.getNombre());
        System.out.println("Duración: " + mision1.getDuracion() + "  horas.");
        System.out.println("Prioridad: " + mision1.getPrioridad());
        System.out.println("Estado: " + mision1.getEstado());
        System.out.println("Experiencia requerida: " + mision1.getExperienciaRequerida());

        NaveEspacial nave1 = new NaveEspacial("Galáctica", 1500, true);
        nave1.registrarExperiencia("cientifica", 10);

        System.out.println("\n-----------------------------");
        System.out.println("Datos de la Nave Espacial:");
        System.out.println("Nombre: " + nave1.getNombre());
        System.out.println("Autonomía máxima: " + nave1.getAutonomiaMaxima() + " horas");
        System.out.println("Autonomía actual: " + nave1.getAutonomiaActual() + " horas");
        System.out.println("Sensores científicos: " + (nave1.tieneSensoresCientificos() ? "Sí" : "No"));
        System.out.println("Experiencia total: " + nave1.getExperienciaTotal());
        
        System.out.println("\n-----------------------------");
        System.out.println("Evaluación de la Misión.\n");
        if(mision1.esApta(nave1)) {
            System.out.println("La nave es apta para la misión.");
            nave1.ejecutarMision(mision1.getDuracion(), mision1.getTipoExperiencia(), 20);
            mision1.completarMision();
        } else {
            System.out.println("La nave no es apta para la misión.");
        }
        
        System.out.println("\n----------RESULTADOS-------------");
        System.out.println("\nEstado de la Misión: " + mision1.getEstado());
        System.out.println("Autonomía actual de la nave después de la misión: " + nave1.getAutonomiaActual() + " horas");
        System.out.println("Experiencia total de la nave después de la misión: " + nave1.getExperienciaTotal());
        System.out.println("\n-----------------------------");

        //registro.listarMisiones();
    }
}