package com.lukasimonishvili;

public class RegistroHistorial {
    private Mision mision;
    private NaveEspacial nave;
    private int experienciaObtenida;
    private String eventoEspecial;

    public RegistroHistorial(Mision mision, NaveEspacial nave, int experienciaObtenida, String eventoEspecial){
        this.mision=mision;
        this.nave=nave;
        this.experienciaObtenida=experienciaObtenida;
        this.eventoEspecial=eventoEspecial;
    }

    public Mision getMision(){return mision;}
    public NaveEspacial getNave(){return nave;}
    public int getExperienciaObtenida(){return experienciaObtenida;}
    public String getEventoEspecial(){return eventoEspecial;}

    @Override
    public String toString(){
        return "RegistroHistorial{" +
                "mision=" + mision +
                ", nave=" + nave +
                ", resultadoMision=" + mision.getEstado() +
                ", experienciaObtenida=" + experienciaObtenida +
                ", eventoEspecial='" + eventoEspecial + '\'' +
                '}';
    }
}
