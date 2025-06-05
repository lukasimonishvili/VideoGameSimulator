package com.lukasimonishvili;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;

public class Simulacion {

    private static final String PATH_DATOS_MISION = "DatosMisiones.json";
    private static final String KEY_DATOS_MISION = "tipos_mision";
    private static final String PATH_DATOS_NAVES = "DatosNaves.json";
    private static final String KEY_DATOS_NAVES = "naves";

    private static final String MISION_EXPLORACION_STRING = "mision_exploracion";
    private static final String MISION_RECOLECCION_DATOS_STRING = "mision_recoleccion_datos";
    private static final String MISION_COLONIZACION_STRING = "mision_colonizacion";
    

    public static NaveEspacial buscarNaveApta(Collection<NaveEspacial> naves, int autonomiaNecesaria,
        boolean usarExperienciaTotal, int experienciaNecesaria,
        TipoMision tipoExperiencia) {
        Comparator<NaveEspacial> comparador = Comparator
        .comparingInt(NaveEspacial::getAutonomiaActual).reversed()
        .thenComparing((n1, n2) -> {
            int exp1 = usarExperienciaTotal ? n1.getExperienciaTotal() : n1.getExperiencia().getOrDefault(tipoExperiencia, 0);
            int exp2 = usarExperienciaTotal ? n2.getExperienciaTotal() : n2.getExperiencia().getOrDefault(tipoExperiencia, 0);
            return Integer.compare(exp2, exp1); // reversed
        })
        .thenComparing(NaveEspacial::getNombre);
        
        return naves.stream()
            .filter(n -> n.getAutonomiaActual() >= autonomiaNecesaria)
            .filter(n -> {
                if (usarExperienciaTotal) {
                    return n.getExperienciaTotal() >= experienciaNecesaria;
                } else {
                    return n.getExperiencia().getOrDefault(tipoExperiencia, 0) >= experienciaNecesaria;
                }
            })
            .sorted(comparador)
            .findFirst()
            .orElse(null);
    }

    //get JSON file "DatosMisiones" and parse as JSONArray
    public static JsonArray getMissionTypes(String path, String key) {
        InputStream is = Simulacion.class.getClassLoader().getResourceAsStream(path);
        if (is != null) {
            try (JsonReader reader = Json.createReader(is)) {
                return reader.readObject().getJsonArray(key);
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
    private static Mision createMisionByType(String tipo, String nombre, int prioridad, EstadoMission estado, int duracion, int experienciaCientifica, int experienciaTecnica, int experienciaEstrategica, int capacidadCarga) {
        switch (tipo) {
            case MISION_EXPLORACION_STRING:
                return new MisionExploracion(nombre, duracion, prioridad, experienciaCientifica, estado);
            case MISION_RECOLECCION_DATOS_STRING:
                return new MisionRecoleccionDatos(nombre, duracion, prioridad, experienciaTecnica, estado);
            case MISION_COLONIZACION_STRING:
                return new MisionColonizacion(nombre, duracion, prioridad, experienciaEstrategica, capacidadCarga, estado);
            default:
                System.err.println("Tipo desconocido: " + tipo);
                return null;
        }
    }

    private static List<NaveEspacial> mapDatosNaves(JsonArray listNaves) {
        List<NaveEspacial> naves = new ArrayList<>();

        for (JsonObject naveObj : listNaves.getValuesAs(JsonObject.class)) {
            String nombre = naveObj.getString("nombre");
            int autonomiaMaxima = naveObj.getInt("autonomiaMaxima");
            boolean sensoresCientificos = naveObj.getInt("sensoresCientificos") > 0;
            int capacidadCarga = naveObj.getInt("capacidadCarga");
            JsonObject experienciasObj = naveObj.getJsonObject("experiencias");
            int experienciaCientifica = experienciasObj.getInt("cientifica");
            int experienciaTecnica = experienciasObj.getInt("tecnica");
            int experienciaEstrategica = experienciasObj.getInt("estrategica");

            NaveEspacial nave = new NaveEspacial(
                nombre,
                autonomiaMaxima,
                sensoresCientificos,
                capacidadCarga
            );

            nave.registrarExperiencia(TipoMision.CIENTIFICA, experienciaCientifica);
            nave.registrarExperiencia(TipoMision.TECHNICA, experienciaTecnica);
            nave.registrarExperiencia(TipoMision.ESTRATEGICA, experienciaEstrategica);
            naves.add(nave);
        }
        return naves;
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
                    EstadoMission estado = EstadoMission.valueOf(estadoStr.toUpperCase());
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
        JsonArray listMisiones = getMissionTypes(PATH_DATOS_MISION, KEY_DATOS_MISION);
        JsonArray jsonNaves = getMissionTypes(PATH_DATOS_NAVES, KEY_DATOS_NAVES);
        
        List<Mision> misionesOrdenadas = sortMisionsByPriority(listMisiones);
        List<NaveEspacial> listaNaves = mapDatosNaves(jsonNaves);

        System.out.println("\nMisiones ordenadas por prioridad:");
        for (Mision m : misionesOrdenadas) System.out.println(m + "\n");

        List<NaveEspacial> navesAsignadas = new ArrayList<>();

        for (Mision m : misionesOrdenadas) {
            TipoMision tipoExp = m.getTipoExperiencia();
            int expNecesaria = m.getExperienciaRequerida();
            
            NaveEspacial naveApta = buscarNaveApta(listaNaves, m.getDuracion(), false, expNecesaria, tipoExp);

            if (naveApta == null) {
                // Intentar con experiencia total
                naveApta = buscarNaveApta(listaNaves, m.getDuracion(), true, expNecesaria, tipoExp);
                if (naveApta == null) {
                    System.out.println("\nNo hay naves con experiencia suficiente para la misión '" + m.getNombre() + "' de tipo '" + tipoExp + "'.\nNúmero de naves disponibles: " + listaNaves.size());
                } else {
                    System.out.println("\nAsignada nave '" + naveApta.getNombre() + "' (por experiencia total) " + " a misión '" + m.getNombre() + "'\n");
                    navesAsignadas.add(naveApta);
                    listaNaves.remove(naveApta); // Se elimina de la lista para que no pueda usarse en otras misiones
                    naveApta.ejecutarMision(m.getDuracion(), m.getTipoExperiencia(), 1);
                    m.setEstado(EstadoMission.COMPLETADA);
                }
            } else {
                System.out.println("\nAsignada nave: " + naveApta.getNombre());
                navesAsignadas.add(naveApta);
                listaNaves.remove(naveApta); // Igual aquí la removemos
                naveApta.ejecutarMision(m.getDuracion(), m.getTipoExperiencia(), 1);
                m.setEstado(EstadoMission.COMPLETADA);
            }
        }
    }
}
