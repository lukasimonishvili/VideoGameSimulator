package com.lukasimonishvili;

import java.io.ObjectInputStream.GetField;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = ScannerSingleton.getInstance().getScanner();
        // DepositoDeNaves depositoNaves=new DepositoDeNaves();
        // ControlDeMisiones misiones = new ControlDeMisiones();

        // Mision mision1=new MisionExploracion("Explorar Marte", 9, 5, 2, EstadoMission.COMPLETADA);
        // Mision mision2=new MisionColonizacion("Kappa", 6, 5, 8, 500, EstadoMission.COMPLETADA);
        // Mision mision3=new MisionRecoleccionDatos("Informacion", 5, 6, 3, EstadoMission.COMPLETADA);

        // NaveEspacial nave1=new NaveEspacial("Atlanta", 2000, true, 5000); 
        // NaveEspacial nave2 = new NaveEspacial("Black pearl", 2500, true, 5000);
        // NaveEspacial nave3 = new NaveEspacial("North light", 2700, false, 2500);

        // misiones.ejecutarMision(mision1, nave1);
        // misiones.ejecutarMision(mision2, nave2);
        // misiones.ejecutarMision(mision3, nave3);

        // misiones.mostrarHistorial();

        System.out.println("SIMULADOR DE MISIONES ESPACIALES.");
        System.out.println("OPERADOR:");
        System.out.println("1.REGISTRAR MISIONES.");
        System.out.println("2.REGISTRAR NAVES.");
        System.out.println("3.EJECUTAR MISIONES.");
        System.out.println("4.MOSTRAR HISTORIAL DE MISIONES.");
        System.out.println("5.SALIR.");
        int opcion = 0;
        do{
            switch (scanner.nextInt()) {
                case 1:
                    System.out.println("-----REGISTRAR MISIONES.------");
                    System.out.println("Ingrese el nombre de la mision:"); 
                    String nombreMision = scanner.next();
                    System.out.println("Ingrese la prioridad de la mision (1-10):");
                    int prioridad = scanner.nextInt();
                    System.out.println("Ingrese la duracion de la mision (en horas):");
                    int duracion = scanner.nextInt();
                    System.out.println("Ingrese la experiencia requerida para la mision:");
                    int experienciaRequerida = scanner.nextInt();
                    System.out.println("Ingrese el tipo de mision (1: Exploracion, 2: Colonizacion, 3: Recoleccion de Datos):");
                    int tipoMision = scanner.nextInt();
                    Mision nuevaMision = null;
                    switch (tipoMision) {
                        case 1:
                            nuevaMision = new MisionExploracion(nombreMision, duracion, prioridad, experienciaRequerida, EstadoMission.COMPLETADA);
                            break;
                        case 2:
                            nuevaMision = new MisionColonizacion(nombreMision, duracion, prioridad, experienciaRequerida, 0, EstadoMission.PENDIENTE);
                            break;
                        case 3:
                            nuevaMision = new MisionRecoleccionDatos(nombreMision, duracion, prioridad, experienciaRequerida, EstadoMission.PENDIENTE);
                            break;
                        default:
                            System.out.println("Tipo de mision no valido.");
                    }
                    break;
            
                default:
                    break;
            }
        }
    }
}