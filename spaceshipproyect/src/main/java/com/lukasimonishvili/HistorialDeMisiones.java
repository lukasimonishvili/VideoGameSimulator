package com.lukasimonishvili;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class HistorialDeMisiones {
    private List<RegistroHistorial> registroHistorial =new ArrayList<>();
    private ObjectMapper mapper=new ObjectMapper();

    public HistorialDeMisiones() {
        cargarHistorialDesdeJson();
    }
   
    public void registrarResultado(Mision mision, NaveEspacial nave, int experienciaObtenida, String eventoEspecial){
        RegistroHistorial nuevoRegistro=new RegistroHistorial(mision, nave, experienciaObtenida, eventoEspecial);
        registroHistorial.add(nuevoRegistro);
        System.out.println("---NUEVO REGISTRO AGREGADO---");
        guardarHistorialEnJson();
    }

    public void mostrarHistorial(){
        if(registroHistorial.isEmpty()){
            System.out.println("---No se encuentran registros en el historial.---");
        }else{
            System.out.println("\n---HISTORIAL DE MISIONES---");
            for (RegistroHistorial historial : registroHistorial) {
                String resultado = "\nMisión: " + historial.getMision().getNombre() + "\nNave: " + historial.getNave().getNombre() +
                        "\nEstado: " + (historial.getMision().getEstado() == EstadoMission.COMPLETADA ? "Completada" : "Fallida") +
                        "\nExperiencia Obtenida: " + historial.getExperienciaObtenida() +
                        "\nEvento Especial: " + historial.getEventoEspecial()+"---";
                System.out.println(resultado);
            }
        }
    }

    private void guardarHistorialEnJson(){
        File archivoMisiones = new File("spaceshipproyect/src/main/resources/DatosHistorial.json");
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(archivoMisiones, registroHistorial);
            System.out.println("---Misiones guardadas correctamente en el archivo JSON.---");
        } catch (Exception e) {
            System.out.println("---Error al guardar las misiones: " + e.getMessage()+"--");
        }

    }

    public void cargarHistorialDesdeJson(){
        File archivoMisiones = new File("spaceshipproyect/src/main/resources/DatosHistorial.json");
        try{
            registroHistorial = mapper.readValue(archivoMisiones, new TypeReference<List<RegistroHistorial>>() {});
            System.out.println(registroHistorial.size() + " registros cargados desde el archivo JSON.");
        }catch (Exception e) {
            System.out.println("---Error al cargar el historial desde el archivo Json:  " + e.getMessage()+" ---");
        }
    }
}
