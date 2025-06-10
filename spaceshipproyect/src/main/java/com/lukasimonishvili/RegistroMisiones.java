package com.lukasimonishvili;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class RegistroMisiones {
    Scanner lectura = new Scanner(System.in);
    List<Mision> misiones = new ArrayList<>();
    //private static final String .vscode="misiones_guardadas.json";

    public RegistroMisiones() {
        misiones.add(new MisionExploracion("Exploración de Marte", 30, 5, 0, EstadoMission.PENDIENTE));
        misiones.add(new MisionRecoleccionDatos("Recolección de Datos en Venus", 6, 7, 15, EstadoMission.PENDIENTE));
        misiones.add(new MisionColonizacion("Colonización de Titán", 50, 8, 20, 1000, EstadoMission.PENDIENTE));
        misiones.add(new MisionExploracion("Exploración de Júpiter", 40, 6, 12, EstadoMission.PENDIENTE));
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
                nuevaMision=new MisionExploracion(nombre, duracion, prioridad, experienciaRequerida, EstadoMission.PENDIENTE);
                break;
            case 2:
                System.out.println("La Recolección de Datos debe durar entre 4 y 8 horas.");
                System.out.println("Ingrese la duración de horas para la Recolección de Datos: ");
                duracion=lectura.nextInt();
                nuevaMision=new MisionRecoleccionDatos(nombre, duracion, prioridad, experienciaRequerida, EstadoMission.PENDIENTE);
                break;
            case 3:
                System.out.println("Ingrese la cantidad de carga requerida.");
                int capacidadCarga=lectura.nextInt();
                nuevaMision = new MisionColonizacion(nombre, duracion, prioridad, experienciaRequerida, capacidadCarga, EstadoMission.PENDIENTE);
                break;
            default:
                System.out.println("Tipo de misión no válido.");
        }
        misiones.add(nuevaMision);
        //guardarMisionesEnJson();
        System.out.println("\nMisión agregada.");
    }

    public void listarMisiones(List<Mision> listDeMisiones) {
        System.out.println("\n----LISTA DE MISIONES----");
        if(listDeMisiones.isEmpty()){
            System.out.println("No hay misiones para mostrar.");
        }else{
            for (Mision mision : listDeMisiones) {
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

    
    public List<Mision> filtrarMisionesPorTipo(TipoMision tipoMision) {
        List<Mision> misionesFiltradas = new ArrayList<>();
        for (Mision mision : misiones) {
            if (mision.getTipoExperiencia() == tipoMision) {
                misionesFiltradas.add(mision);
            }
        }
        return misionesFiltradas;
    }

    public List<Mision> filtrarMisionesPorEstado(EstadoMission estado) {
        List<Mision> misionesFiltradas = new ArrayList<>();
        for (Mision mision : misiones) {
            if (mision.getEstado() == estado) {
                misionesFiltradas.add(mision);
            }
        }
        return misionesFiltradas;
    }

    public List<Mision> filtrarMisionesPorPrioridad(String direccionDePrioridad) throws IllegalArgumentException {
        if(direccionDePrioridad.equals("alta")) {
            misiones.sort(Comparator.comparingInt(Mision::getPrioridad).reversed());
        }else if(direccionDePrioridad.equals("baja")) {
            misiones.sort(Comparator.comparingInt(Mision::getPrioridad));
        } else {
            throw new IllegalArgumentException("Dirección de prioridad no válida. Use 'alta' o 'baja'.");
        }
        return misiones;
    }
}
