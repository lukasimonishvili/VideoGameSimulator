package com.lukasimonishvili;

import java.util.HashMap;
import java.util.Map;

public class NaveEspacial {
    private String nombre;
    private int autonomiaMaxima;
    private int autonomiaActual;
    private boolean sensoresCientificos;
    private Map<TipoMision, Integer> experiencias = new HashMap<>();
    private int capacidadCarga;

    public NaveEspacial(String nombre, int autonomiaMaxima, boolean sensoresCientificos, int capacidadCarga) {
        this.sensoresCientificos = sensoresCientificos;
        this.autonomiaMaxima = autonomiaMaxima;
        this.autonomiaActual = autonomiaMaxima; // Inicialmente la autonomía actual es igual a la máxima
        this.nombre = nombre;
        this.capacidadCarga = capacidadCarga;
        experiencias.put(TipoMision.TECHNICA, 0);
        experiencias.put(TipoMision.CIENTIFICA, 0);
        experiencias.put(TipoMision.ESTRATEGICA, 0);
    }

    public String getNombre() {
        return nombre;
    }

    public int getAutonomiaMaxima() {
        return this.autonomiaMaxima;
    }

    public int getAutonomiaActual() {
        return this.autonomiaActual;
    }

    public boolean tieneSensoresCientificos() {
        return this.sensoresCientificos;
    }

    public void registrarExperiencia(TipoMision tipo, int cantidad) {
        if (cantidad < 0) {
            System.out.println("No se puede registrar una cantidad negativa de experiencia.");
            return;
        }
        experiencias.put(tipo, experiencias.get(tipo) + cantidad);
        System.out.println("Experiencia registrada. Tipo: " + tipo + ", Cantidad: " + cantidad);
    }

    public int getExperienciaTotal() {
        return experiencias.values().stream().mapToInt(Integer::intValue).sum();
    }

    public boolean aptasParaUnaMision(int autonomiaNecesaria, TipoMision tipoExperiencia, int experienciaNecesaria) {
        boolean resultado = true;
        if(autonomiaNecesaria > this.autonomiaMaxima) {
            System.out.println("La nave no tiene suficiente autonomía para la misión.");
            resultado = false;
        }

        if(!experiencias.containsKey(tipoExperiencia)) {
            System.out.println("Tipo de experiencia no válido: " + tipoExperiencia);
            resultado = false;
        } else if(experiencias.get(tipoExperiencia) < experienciaNecesaria) {
            System.out.println("La nave no tiene suficiente experiencia en " + tipoExperiencia + " para la misión.");
            resultado = false;
        }

        return resultado;
    }

    public void ejecutarMision(int autonomiaNecesaria, TipoMision tipoExperiencia, int experienciaAdquirida) {
        this.autonomiaActual -= autonomiaNecesaria;
        System.out.println("Misión ejecutada. Autonomía restante: " + this.autonomiaActual);
        this.registrarExperiencia(tipoExperiencia, experienciaAdquirida);

    }

    public void restaurarNave() {
        this.autonomiaActual = this.autonomiaMaxima;
        System.out.println("La nave ha sido restaurada a su autonomía máxima: " + this.autonomiaMaxima);
    }

    public int getCapacidadCarga() {
        return this.capacidadCarga;
    }
}
