package com.lukasimonishvili;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class DepositoDeNaves {
    public List<NaveEspacial> naves = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);
    private ObjectMapper objectMapper = new ObjectMapper();

    public void creareNave() {
        System.out.println("Por favor escriba el nombre de la nave espacial");
        String nombre = scanner.nextLine();
        System.out.println("Por favor escriba la autonomia maxima de la nave espacial");
        int autonomiaMaxima = scanner.nextInt();
        System.out.println("está nave tiene sensores cientificos? yes/no");
        String sensoresCientificosInput = scanner.next();
        boolean sensoresCientific = sensoresCientificosInput.equals("yes");
        System.out.println("Por favor escriba la capacidad carga de la nave espacial");
        int capacidadCarga = scanner.nextInt();
        NaveEspacial nuevoNaveEspacial = new NaveEspacial(nombre, autonomiaMaxima, sensoresCientific, capacidadCarga);
        naves.add(nuevoNaveEspacial);
        System.out.println("Nueva nave espacial creada");
        guardarNavesEnArchivo();
    }

    public void mostrarTodosLosNaves() {
        if (naves.isEmpty()) {
            System.out.println("No hay naves espaciales registradas.");
            return;
        }
        System.out.println("Naves espaciales registradas:");
        for (NaveEspacial nave : naves) {
            System.out.println(nave.getNombre() + " - Autonomía Máxima: " + nave.getAutonomiaMaxima() + ", Capacidad de Carga: " + nave.getCapacidadCarga());
        }
    }

    public List<NaveEspacial> getNavesAptadaParaMision(int duracion, TipoMision tipoExperiencia, int experienciaRequerida) {
        List<NaveEspacial> navesFiltradas = new ArrayList<>();
        for (NaveEspacial nave : naves) {
            if (nave.aptasParaUnaMision(duracion, tipoExperiencia, experienciaRequerida)) {
                navesFiltradas.add(nave);
            }
        }
        return navesFiltradas;
    }

    public void mostrarRankingNavesPorExperiencia(){        
        ArrayList<NaveEspacial> navesOrdenadas = new ArrayList<>(naves);
        
        navesOrdenadas.sort((nave1, nave2)->Integer.compare(
            nave2.getExperienciaTotal(),
            nave1.getExperienciaTotal()
        ));
        System.out.println("Ranking por experiencia :");

        int posicion = 1;
        for (NaveEspacial nave : navesOrdenadas) {
            System.out.println(posicion + ". " + nave.getNombre() + " - " + nave.getExperienciaTotal());
            posicion++;
        }
    }

    public void mostrarRankingNavesPorTipo(TipoMision tipo){
        
        ArrayList<NaveEspacial> navesOrdenadas = new ArrayList<>(naves);
        
        navesOrdenadas.sort((nave1, nave2)->Integer.compare(
            nave2.getExperiencia(tipo),
            nave1.getExperiencia(tipo)
        ));
        System.out.println("Ranking por tipo de experiencia : " + tipo);

        int posicion = 1;
        for (NaveEspacial nave : navesOrdenadas) {
            int experiencia = nave.getExperiencia(tipo);
            System.out.println(posicion + ". " + nave.getNombre() + " - " + experiencia + " experiencia ");
            posicion++;
        }
    }

    public void guardarNavesEnArchivo() {
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        try{
            File archivo = new File("spaceshipproyect/src/main/resources/DatosNaves.json");
            objectMapper.writeValue(archivo, naves);
        }catch (Exception e) {
            System.out.println("Error al guardar las naves en el archivo: " + e.getMessage());
        }
    }
}
