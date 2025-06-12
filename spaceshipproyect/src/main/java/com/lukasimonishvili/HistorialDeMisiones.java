package com.lukasimonishvili;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class HistorialDeMisiones {
    private List<RegistroHistorial> registroHistorial =new ArrayList<>();
    List<Mision> misiones = new ArrayList<>();
   
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
        ObjectMapper mapper=new ObjectMapper();
        File archivoMisiones = new File("spaceshipproyect/src/main/resources/DatosMisiones.json");
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(archivoMisiones, misiones);
            System.out.println("---Misiones guardadas correctamente en el archivo JSON.---");
        } catch (Exception e) {
            System.out.println("--Error al guardar las misiones: " + e.getMessage()+"--");
        }

    }
}
