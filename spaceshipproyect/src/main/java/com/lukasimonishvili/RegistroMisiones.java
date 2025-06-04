package com.lukasimonishvili;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RegistroMisiones {
    Scanner lectura = new Scanner(System.in);
    List<Mision> misiones = new ArrayList<>();
    List<Mision> misionesPendientes=new ArrayList<>();
    //private static final String .vscode="misiones_guardadas.json";

    public RegistroMisiones() {
        //cargarMisionesDeJson();
    }

    public void agregarMision() {
        System.out.println("----REGISTRO DE MISIONES.----");
        System.out.println("Ingrese el nombre de la misión: ");
        String nombre = lectura.nextLine();
        
        System.out.println("Ingrese el tipo de exploración: ");
        System.out.println("1-Exploración.");
        System.out.println("2-Recolección de Datos.");
        System.out.println("3-Colonización.");
        int tipoExploracion=lectura.nextInt();

        System.out.println("Ingrese la duración: ");
        int duracion = lectura.nextInt();
        
        System.out.println("¿Qué nivel prioritario tiene?.");
        System.out.println("Sea 1 menos urgente y 10 el más importante.");
        int prioridad = lectura.nextInt();
        
        System.out.println("¿Qué nivel de experiencia requiere?.");
        int experienciaRequerida = lectura.nextInt();
        
        Mision nuevaMision=null;
        
        switch (tipoExploracion) {
            case 1:
                nuevaMision=new MisionExploracion(nombre, duracion, prioridad, experienciaRequerida);
                break;
            case 2:
                nuevaMision=new MisionRecoleccionDatos(nombre, duracion, prioridad, experienciaRequerida);
                break;
            case 3:
                System.out.println("Ingrese la cantidad de carga requerida.");
                int capacidadCarga=lectura.nextInt();
                nuevaMision = new MisionColonizacion(nombre, duracion, prioridad, experienciaRequerida, capacidadCarga);
            default:
                System.out.println("Tipo de misión no válido.");
        }
        misiones.add(nuevaMision);
        //guardarMisionesEnJson();
        System.out.println("Misión agregada.");
    }

    public void listarMisiones(){
        System.out.println("----LISTA DE MISIONES----");
        if(misiones.isEmpty()){
            System.out.println("No hay misiones para mostrar.");
        }else{
            for (Mision mision : misiones) {
                System.out.println(mision);
            }
            
            System.out.println("----MISIONES PENDIENTES.----");
            if(misionesPendientes.isEmpty()){
                System.out.println("No hay misiones pendientes.");
            }else{
                for(Mision mision : misionesPendientes){
                    System.out.println(" "+mision.getNombre()+" " + mision.getClass());
                }
            }
        }
    }

    public void cerrarScanner(){
        lectura.close();
    }

}
