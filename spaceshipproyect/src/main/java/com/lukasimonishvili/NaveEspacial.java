package com.lukasimonishvili;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NaveEspacial {
    private String nombre;
    private int autonomiaMaxima;
    private int autonomiaActual;
    private boolean sensoresCientificos;
    private Map<TipoMision, Integer> experiencias = new HashMap<>();
    private int capacidadCarga;
    private List<Mision> misionesHecho = new ArrayList<>();

    public NaveEspacial(String nombre, int autonomiaMaxima, boolean sensoresCientificos, int capacidadCarga) {
        this.sensoresCientificos = sensoresCientificos;
        this.autonomiaMaxima = autonomiaMaxima;
        this.autonomiaActual = autonomiaMaxima;
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

    public int getExperiencia(TipoMision tipo){
        return experiencias.getOrDefault(tipo, 0);
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

     public Map<TipoMision, Integer> getExperiencia() {
        return this.experiencias;
    }

    public boolean aptasParaUnaMision(int autonomiaNecesaria, TipoMision tipoExperiencia, int experienciaNecesaria) {
        return this.autonomiaActual >= autonomiaNecesaria &&
               this.experiencias.get(tipoExperiencia) >= experienciaNecesaria;
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

    public void registrarMision(Mision mision) {
        misionesHecho.add(mision);
        System.out.println("Misión registrada: " + mision.getNombre());
    }

    public String getEstatisticaDeNave() {
        String resultado = "";
        resultado += "Nombre de la nave: " + this.nombre + "\n";
        resultado += "Autonomía máxima: " + this.autonomiaMaxima + "\n";
        resultado += "Autonomía actual: " + this.autonomiaActual + "\n";
        resultado += "Sensores científicos: " + (this.sensoresCientificos ? "Sí" : "No") + "\n";
        resultado += "Experiencias:\n";
        resultado += "  Técnica: " + experiencias.get(TipoMision.TECHNICA) + "\n";
        resultado += "  Científica: " + experiencias.get(TipoMision.CIENTIFICA) + "\n";
        resultado += "  Estratégica: " + experiencias.get(TipoMision.ESTRATEGICA) + "\n";
        resultado += "  Experiencia total: " + this.getExperienciaTotal() + "\n";
        resultado += "Capacidad de carga: " + this.capacidadCarga + "\n";
        resultado += "Misiones realizadas:\n";
        for(Mision mision : misionesHecho) {
            String misionEstado = mision.getEstado() == EstadoMission.COMPLETADA ? "Completada" : "Fallida";
            resultado += misionEstado +  "  - " + mision.getNombre() + "\n";
        }
        return resultado;
    }
}
