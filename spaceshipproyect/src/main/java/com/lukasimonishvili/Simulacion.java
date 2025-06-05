package com.lukasimonishvili;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;

public class Simulacion {

    private static final String PATH_DATOS_MISION = "DatosMisiones.json";
    private static final String KEY_DATOS_MISION = "tipos_mision";

    private static final String MISION_EXPLORACION_STRING = "mision_exploracion";
    private static final String MISION_RECOLECCION_DATOS_STRING = "mision_recoleccion_datos";
    private static final String MISION_COLONIZACION_STRING = "mision_colonizacion";
    

    public static NaveEspacial buscarMejorNaveApta(List<NaveEspacial> naves, int autonomiaNecesaria, String tipoExperiencia, int experienciaNecesaria) {
        Optional<NaveEspacial> mejorNave = naves.stream()
            // Filtrar solo las naves aptas para la misión
            .filter(n -> n.aptasParaUnaMision(autonomiaNecesaria, tipoExperiencia, experienciaNecesaria))
            // Ordenar según los criterios indicados (descendente autonomíaActual, descendente experiencia total, ascendente nombre)
            .sorted(Comparator.comparingInt(NaveEspacial::getAutonomiaActual).reversed()
                    .thenComparing(Comparator.comparingInt(NaveEspacial::getExperienciaTotal).reversed())
                    .thenComparing(NaveEspacial::getNombre))
            .findFirst();
        return mejorNave.orElse(null);
    }
    
    //get JSON file "DatosMisiones" and parse as JSONArray
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
    // create mission by type (MisionExploracion...)
    private static Mision createMisionByType(String tipo, String nombre, int prioridad, Mision.EstadoMision estado, int duracion, int experienciaCientifica, int experienciaTecnica, int experienciaEstrategica, int capacidadCarga) {
        switch (tipo) {
            case MISION_EXPLORACION_STRING:
                return new MisionExploracion(nombre, duracion, prioridad, estado, experienciaCientifica);
            case MISION_RECOLECCION_DATOS_STRING:
                return new MisionRecoleccionDatos(nombre, duracion, prioridad, estado, experienciaTecnica);
            case MISION_COLONIZACION_STRING:
                return new MisionColonizacion(nombre, duracion, prioridad, estado, experienciaEstrategica, capacidadCarga);
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

                    int duracion = m.getInt("duracion");
                    int experienciaCientifica = m.getInt("experienciaCientifica");
                    int experienciaTecnica = m.getInt("experienciaTecnica");
                    int experienciaEstrategica = m.getInt("experienciaEstrategica");
                    int capacidadCarga = m.getInt("capacidadCarga");
                    // saltar las misiones no pendientes
                    if (!estadoStr.equalsIgnoreCase("PENDIENTE")) {
                        continue;
                    }
                    Mision.EstadoMision estado = Mision.EstadoMision.valueOf(estadoStr.toUpperCase());
                    Mision nuevaMision = createMisionByType(tipo, nombre, prioridad, estado, duracion, experienciaCientifica, experienciaTecnica, experienciaEstrategica, capacidadCarga);
                    if (nuevaMision != null) {
                        misiones.add(nuevaMision);
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
        for (Mision m: misionesOrdenadas) System.out.println(m);


    }
}

