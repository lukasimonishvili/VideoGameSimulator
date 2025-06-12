package com.lukasimonishvili;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class RegistroMisiones {
    Scanner lectura = new Scanner(System.in);
    List<Mision> misiones = new ArrayList<>();

    public RegistroMisiones() {
        misiones.add(new MisionExploracion("Exploración de Marte", 30, 5, 0, EstadoMission.PENDIENTE));
        misiones.add(new MisionRecoleccionDatos("Recolección de Datos en Venus", 6, 7, 15, EstadoMission.PENDIENTE));
        misiones.add(new MisionColonizacion("Colonización de Titán", 50, 8, 20, 1000, EstadoMission.PENDIENTE));
        misiones.add(new MisionExploracion("Exploración de Júpiter", 40, 6, 12, EstadoMission.PENDIENTE));
        
    }

    public void agregarMision() {
        System.out.println("----REGISTRO DE MISIONES.----");
        System.out.println("Ingrese el nombre de la misión: ");
        String nombre = lectura.nextLine();
        
        
        System.out.println("Ingrese la duración: ");
        System.out.println("Para Misión de Exploración, la duración mínima es de 8 horas.");
        System.out.println("Para Misión de Recolección de Datos, la duración típica debe ser estar entre 4 y 8 horas.");
        System.out.println("Para Misión de Colonización, la duración mínima para colonizar es de 6 horas.");
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
        System.out.println("\n----MISIÓN AGREGADA.----");
    }

    public void listarMisiones(List<Mision> listDeMisiones) {
        System.out.println("\n----LISTA DE MISIONES----");
        if(listDeMisiones.isEmpty()){
            System.out.println("\nNo hay misiones para mostrar.");
        }else{
            for (Mision mision : listDeMisiones) {
                System.out.println("\nMisión: " + mision.getNombre());
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

    private void guardarMisionesEnJson(){
        ObjectMapper mapper=new ObjectMapper();
        File archivoMisiones = new File("spaceshipproyect/src/main/resources/DatosMisiones.json");

        try {
            JsonNode  raiz = mapper.createObjectNode();
            ArrayNode tiposMision = (ArrayNode) raiz.get("tipos_mision");

            String clave ="";
            ObjectNode nuevaMision= mapper.createObjectNode();
            nuevaMision.put("Nombre", Mision.getNombre());
            nuevaMision.put("Duracion", Mision.getDuracion());
            nuevaMision.put("Prioridad", Mision.getPrioridad());
            nuevaMision.put("Estado", Mision.getEstado().toString());
            nuevaMision.put("ExperienciaRequerida", Mision.getExperienciaRequerida());
            if (misiones instanceof MisionExploracion) {
                clave = "mision_exploracion";
            } else if (misiones instanceof MisionRecoleccionDatos) {
                clave = "mision_recoleccion_datos";
            } else if (misiones instanceof MisionColonizacion) {
                clave = "mision_colonizacion";
            }

            
            mapper.writerWithDefaultPrettyPrinter().writeValue(archivoMisiones, misiones);
            System.out.println("Misiones guardadas correctamente en el archivo JSON.");
        } catch (Exception e) {
            System.out.println("Error al guardar las misiones: " + e.getMessage());
        }

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
