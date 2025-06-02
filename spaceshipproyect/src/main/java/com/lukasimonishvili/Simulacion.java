package com.lukasimonishvili;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;

public class Simulacion {

    private static final String PATH_DATOS_MISION = "DatosMisiones.json";
    private static final String KEY_DATOS_MISION = "tipos_mision";

    // Clase auxiliar para representar una misión
    static class Mision {
        String tipo;
        String nombre;
        int prioridad;

        Mision(String tipo, String nombre, int prioridad) {
            this.tipo = tipo;
            this.nombre = nombre;
            this.prioridad = prioridad;
        }
    }

    public static JsonArray getMissionTypes() {
        InputStream is = Simulacion.class.getClassLoader().getResourceAsStream(PATH_DATOS_MISION);
        if (is != null) {
            try (JsonReader reader = Json.createReader(is)) {
                return reader.readObject().getJsonArray(KEY_DATOS_MISION);
            } catch (Exception e) {
                System.err.println("Error al leer o parsear el JSON:");
                e.printStackTrace();
            }
        } else {
            System.err.println("Error: No se encontró el archivo DatosMisiones.json en resources.");
        }
        return Json.createArrayBuilder().build();
    }

    public static List<Mision> sortMisionsByPriority(JsonArray listMisiones) {
        List<Mision> misiones = new ArrayList<>();

        for (JsonObject tipoObj : listMisiones.getValuesAs(JsonObject.class)) {
            for (String tipo : tipoObj.keySet()) {
                JsonArray arrayMisiones = tipoObj.getJsonArray(tipo);
                for (JsonObject m : arrayMisiones.getValuesAs(JsonObject.class)) {
                    String nombre = m.getString("nombre");
                    int prioridad = m.getInt("prioridad");
                    misiones.add(new Mision(tipo, nombre, prioridad));
                }
            }
        }
        misiones.sort(Comparator.comparingInt(m -> m.prioridad));
        return misiones;
    }

    public static void main(String[] args) {
        JsonArray listMisiones = getMissionTypes();
        List<Mision> misionesOrdenadas = sortMisionsByPriority(listMisiones);

        System.out.println("Misiones ordenadas por prioridad:");
        for (Mision m : misionesOrdenadas) {
            System.out.println("Tipo: " + m.tipo + ", Nombre: " + m.nombre + ", Prioridad: " + m.prioridad);
        }
    }
}
