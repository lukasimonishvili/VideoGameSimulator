package com.lukasimonishvili;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RegistroMisiones {
    Scanner lectura = new Scanner(System.in);
    List<Mision> misiones = new ArrayList<>();
    List<Mision> misionesPendientes=new ArrayList<>();
    List<Mision> registroHistorial=new ArrayList<>();
    //private static final String .vscode="misiones_guardadas.json";

    public RegistroMisiones() {
        //cargarMisionesDeJson();
    }

    public void agregarMision() {
        System.out.println("----REGISTRO DE MISIONES.----");
        System.out.println("Ingrese el nombre de la misión: ");
        String nombre = lectura.nextLine();
        
        
        System.out.println("Ingrese la duración: ");
        int duracion = lectura.nextInt();
        
        System.out.println("¿Qué nivel prioritario tiene?.");
        System.out.println("Sea 1 menos urgente y 10 el más importante.");
        int prioridad = lectura.nextInt();
        
        System.out.println("¿Qué nivel de experiencia requiere?.");
        int experienciaRequerida = lectura.nextInt();
        
        System.out.println("Ingrese el tipo de exploración: ");
        System.out.println("1-Exploración.");
        System.out.println("2-Recolección de Datos.");
        System.out.println("3-Colonización.");
        int tipoExploracion=lectura.nextInt();
        
        Mision nuevaMision=null;
        
        switch (tipoExploracion) {
            case 1:
                nuevaMision=new MisionExploracion(nombre, duracion, prioridad, experienciaRequerida);
                break;
            case 2:
                System.out.println("La Recolección de Datos debe durar entre 4 y 8 horas.");
                System.out.println("Ingrese la duración de horas para la Recolección de Datos: ");
                duracion=lectura.nextInt();
                nuevaMision=new MisionRecoleccionDatos(nombre, duracion, prioridad, experienciaRequerida);
                break;
            case 3:
                System.out.println("Ingrese la cantidad de carga requerida.");
                int capacidadCarga=lectura.nextInt();
                nuevaMision = new MisionColonizacion(nombre, duracion, prioridad, experienciaRequerida, capacidadCarga);
                break;
            default:
                System.out.println("Tipo de misión no válido.");
                return;
        }
        if(nuevaMision!=null){
            misiones.add(nuevaMision);
            //guardarMisionesEnJson();
            System.out.println("\nMisión agregada.");
        }
    }

    public void actualizarMisionesPendientes(){
        misionesPendientes.clear();
        for(Mision mision : misiones){
            if (mision.getEstado() == EstadoMission.PENDIENTE) {
                misionesPendientes.add(mision);
            }
        }
    }

    public void listarMisiones(){
        System.out.println("\n----LISTA DE MISIONES----");
        if(misiones.isEmpty()){
            System.out.println("No hay misiones para mostrar.");
        }else{
            for (Mision mision : misiones) {
                System.out.println("Misión: " + mision.getNombre());
                System.out.println("Duración: " + mision.getDuracion() + "  horas.");
                System.out.println("Prioridad: " + mision.getPrioridad());
                System.out.println("Estado: " + mision.getEstado());
                System.out.println("Experiencia requerida: " + mision.getExperienciaRequerida());
            }
        }
        
        System.out.println("\n----MISIONES PENDIENTES.----");
        if(misionesPendientes.isEmpty()){
            System.out.println("No hay misiones pendientes.");
        }else{
            for(Mision mision : misionesPendientes){
                System.out.println("Misión: " + mision.getNombre());
                System.out.println("Duración: " + mision.getDuracion() + "  horas.");
                System.out.println("Prioridad: " + mision.getPrioridad());
                System.out.println("Estado: " + mision.getEstado());
                System.out.println("Experiencia requerida: " + mision.getExperienciaRequerida());
            }
    }
    }

    public void cerrarScanner(){
        lectura.close();
    }


}
