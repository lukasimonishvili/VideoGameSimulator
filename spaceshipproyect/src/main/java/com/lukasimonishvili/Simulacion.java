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

    private static Mision createMisionByType(String tipo, String nombre, int prioridad, Mision.EstadoMision estado) {
        switch (tipo) {
            case "mision_exploracion":
                return new MisionExploracion(tipo, nombre, 5, prioridad, 100, estado);
            case "mision_recoleccion_datos":
                return new MisionRecoleccionDatos(tipo, nombre, 4, prioridad, 80, estado);
            case "mision_colonizacion":
                return new MisionColonizacion(tipo, nombre, 10, prioridad, 200, 1, estado);
            default:
                System.err.println("Tipo desconocido: " + tipo);
                return null;
        }
    }

    private static List<Mision> sortMisionsByPriority(JsonArray listMisiones) {
        List<Mision> misiones = new ArrayList<>();

        for (JsonObject tipoObj : listMisiones.getValuesAs(JsonObject.class)) {
            for (String tipo : tipoObj.keySet()) {
                JsonArray arrayMisiones = tipoObj.getJsonArray(tipo);

                for (JsonObject m : arrayMisiones.getValuesAs(JsonObject.class)) {
                    String nombre = m.getString("nombre");
                    int prioridad = m.getInt("prioridad");
                    String estadoStr = m.getString("estado");

                    if (!estadoStr.equalsIgnoreCase("PENDIENTE")) {
                        continue; // saltar las misiones no pendientes
                    }

                    try {
                        Mision.EstadoMision estado = Mision.EstadoMision.valueOf(estadoStr.toUpperCase());
                        Mision nuevaMision = createMisionByType(tipo, nombre, prioridad, estado);
                        if (nuevaMision != null) {
                            misiones.add(nuevaMision);
                        }
                    } catch (IllegalArgumentException e) {
                        System.err.println("Estado inválido para misión '" + nombre + "': " + estadoStr);
                    }
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
