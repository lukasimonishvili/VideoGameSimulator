package com.lukasimonishvili;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class LeerYescribirJSON {
    public static void main(String[] args) {
        ObjectMapper mapper = new ObjectMapper();
        File archivo = new File("spaceshipproyect/src/main/resources/DatosMisiones.json");

        try {
            // Leer JSON
            JsonNode raiz = mapper.readTree(archivo);
            ArrayNode tiposMision = (ArrayNode) raiz.get("tipos_mision");

            // Buscar "mision_colonizacion"
            for (JsonNode tipo : tiposMision) {
                if (tipo.has("mision_colonizacion")) {
                    ArrayNode colonizaciones = (ArrayNode) tipo.get("mision_colonizacion");

                    // Crear nueva misión
                    ObjectNode nuevaMision = mapper.createObjectNode();
                    nuevaMision.put("nombre", "colonizacionNueva");
                    nuevaMision.put("prioridad", 3);
                    nuevaMision.put("estado", "PENDIENTE");
                    nuevaMision.put("duracion", 10);
                    nuevaMision.put("experienciaCientifica", 20);
                    nuevaMision.put("experienciaEstrategica", 20);
                    nuevaMision.put("experienciaTecnica", 20);
                    nuevaMision.put("capacidadCarga", 1500);

                    // Añadir al array
                    colonizaciones.add(nuevaMision);
                    break;
                }
            }

            // Guardar JSON actualizado
            mapper.writerWithDefaultPrettyPrinter().writeValue(archivo, raiz);
            System.out.println("Nueva misión añadida correctamente.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
