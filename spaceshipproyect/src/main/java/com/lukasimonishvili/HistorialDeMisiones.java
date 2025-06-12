package com.lukasimonishvili;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class HistorialDeMisiones {
    private List<RegistroHistorial> registroHistorial =new ArrayList<>();
    List<Mision> misiones = new ArrayList<>();
    private ObjectMapper mapper=new ObjectMapper();
   
    public void registrarResultado(Mision mision, NaveEspacial nave, int experienciaObtenida, String eventoEspecial){
        RegistroHistorial nuevoRegistro=new RegistroHistorial(mision, nave, experienciaObtenida, eventoEspecial);
        registroHistorial.add(nuevoRegistro);
        System.out.println("---NUEVO REGISTRO AGREGADO---");
        guardarHistorialEnJson();
    }

    public void mostrarHistorial(){
        if(registroHistorial.isEmpty()){
            System.out.println("---No se encuentras registros en el historial.---");
        }else{
            for (RegistroHistorial historial : registroHistorial) {
                System.out.println(historial.toString());
            }
        }
    }

    private void guardarHistorialEnJson(){
        File archivoMisiones = new File("spaceshipproyect/src/main/resources/DatosMisiones.json");
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(archivoMisiones, misiones);
            System.out.println("---Misiones guardadas correctamente en el archivo JSON.---");
        } catch (Exception e) {
            System.out.println("--Error al guardar las misiones: " + e.getMessage()+"--");
        }

    }

    public void cargarHistorialDesdeJson(){
        File archivoMisiones = new File("spaceshipproyect/src/main/resources/DatosMisiones.json");
        try{
            if (!archivoMisiones.exists()) {
                System.err.println("Error: El archivo DatosMisiones.json no existe en resources.");
                return;
                
            }
            JsonNode raiz = mapper.readTree(archivoMisiones);
            for(JsonNode mision : raiz){
                Iterator<String> campos = mision.fieldNames();

                while (campos.hasNext()) {
                    String nombre=campos.next();
                    JsonNode misionesArray=mision.get("nombre");
                    System.out.println("Tipo de misión: " + nombre);

                    for (JsonNode misiones : misionesArray) {
                        Iterator<String> atributos = misiones.fieldNames();
                        while (atributos.hasNext()) {
                            String campo = atributos.next();
                            System.out.println(campo + ": " + misiones.get(campo));
                        }
                        System.out.println("---");
                    }
                }
            }
        }catch (IOException e) {
            System.out.println("---Error al cargar el historial desde el archivo JSON: " + e.getMessage());
        }
}
}
