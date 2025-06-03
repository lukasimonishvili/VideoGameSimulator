package com.lukasimonishvili;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RegistroMisiones {
    List<Mision> misiones = new ArrayList<>();
    Scanner lectura = new Scanner(System.in);

    public RegistroMisiones() {
        Mision mision1 = new MisionExploracion("Mapeo Alfa", 10, 9, 2);
        Mision mision2 = new MisionColonizacion("Colonizar Kappa", 8, 10, 2,500);
        Mision mision3=new MisionRecoleccionDatos("Orbita Epsilon", 6, 7, 2);
        
        misiones.add(mision1);
        misiones.add(mision2);
        misiones.add(mision3);

    }

    public void agregarMision() {
        System.out.println("----REGISTRO DE MISIONES.----");
        System.out.println("Ingrese el nombre de la misión: ");
        String nombre = lectura.nextLine();
        
        System.out.println("Ingrese el tipo de exploración: ");
        System.out.println("1-Exploración.");
        System.out.println("2-Recolección de Dattos.");
        System.out.println("3-Colonización.");
        int tipoExploracion=lectura.nextInt();

        System.out.println("Ingrese la duración: ");
        int duracion = lectura.nextInt();
        
        System.out.println("¿Qué nivel prioritario tiene?.");
        System.out.println("Sea 1 menos urgente y 10 el más importante.");
        int prioridad = lectura.nextInt();
        
        System.out.println("¿Qué nivel de experiencia requiere?.");
        int experienciaRequerida = lectura.nextInt();
        //Agregar la autonomia necesaria
        System.out.println("¿Cuánta autonomía máxima existirá?.");
        int autonomia=lectura.nextInt();
        
        Mision nuevaMision=null;
        lectura.close();
        
        switch (tipo) {
            case 1:
                
                break;
        
            default:
                break;
        }
    }

    public void listarMisiones(){
        System.out.println("----LISTA DE MISIONES----");
        if(misiones.isEmpty()){
            System.out.println("No hay misiones para mostrar.");
        }else{
            for (Mision mision : misiones) {
                System.out.println(mision);
            }
        }

    }

}
