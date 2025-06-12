package com.lukasimonishvili;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes;

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "tipo"
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = MisionExploracion.class, name = "CIENTIFICA"),
    @JsonSubTypes.Type(value = MisionRecoleccionDatos.class, name = "TECHNICA"),
    @JsonSubTypes.Type(value = MisionColonizacion.class, name = "ESTRATEGICA")
})
public abstract class Mision {
    protected String nombre;
    protected int duracion;
    protected int prioridad;
    protected EstadoMission estado;
    protected int experienciaRequerida;
    
    public Mision(String nombre, int duracion, int prioridad, int experienciaRequerida, EstadoMission estado) {
        if (duracion <= 0) {
            throw new IllegalArgumentException("La duración debe ser mayor a 0.");
        }
        if(prioridad < 1 || prioridad > 10) {
            throw new IllegalArgumentException("La prioridad debe estar entre 1 y 10.");
        }
        if(experienciaRequerida<0){
            throw new IllegalArgumentException("La experiencia requerida no puede ser negativa.");
        }

        this.nombre = nombre;
        this.duracion = duracion;
        this.prioridad = prioridad;
        this.estado = estado;
        this.experienciaRequerida = experienciaRequerida;
    }

    public String getNombre() {
        return nombre;
    }
    public int getDuracion() {
        return duracion;
    }
    
    public int getPrioridad() {
        return prioridad;
    }
    
    public int getExperienciaRequerida() {
        return experienciaRequerida;
    }
    
    public EstadoMission getEstado() {
        return estado;
    }

    public void setEstado(EstadoMission estado) {
        this.estado = estado;
    }
    
    public void completarMision() {
        if (estado == EstadoMission.PENDIENTE) {
            this.estado = EstadoMission.COMPLETADA;
            System.out.println("\n----Misión completada: " + nombre+" ----");
        } else {
            throw new IllegalStateException("---La misión ya está completada.---");
        }
    }

    public void misionPendiente(){
        if(estado==EstadoMission.PENDIENTE){
            System.out.println("\n----Misión pendiente: " + nombre+" ----");
        }
    }
    
    public void fallarMision() {
        if (estado == EstadoMission.PENDIENTE) {
            this.estado = EstadoMission.FALLIDA;
            System.out.println("Misión fallida: " + nombre);
        } else {
            throw new IllegalStateException("La misión ya está completada o fallida.");
        }
    }

    public abstract TipoMision getTipo();
    //Calculamos la experiencia requerida para la misión

    @Override
    public String toString() {
        return "Mision{" +
                "nombre='" + nombre + '\'' +
                ", duracion=" + duracion +
                ", prioridad=" + prioridad +
                ", estado='" + estado + '\'' +
                ", experienciaRequerida=" + experienciaRequerida +
                '}';
    }
}

class MisionExploracion extends Mision{
    public MisionExploracion(String nombre, int duracion, int prioridad, int experienciaCientifica, EstadoMission estado) {
        super(nombre, duracion, prioridad,experienciaCientifica, estado);
        if(duracion<8){
            throw new IllegalArgumentException("\nLa duración mínima es de 8 horas.\n");
        }
    }

    public MisionExploracion() {
        super("", 1, 1, 0, EstadoMission.PENDIENTE);
    }
    
    @Override
    public TipoMision getTipo(){
        return TipoMision.CIENTIFICA;
    }

    @Override
    public String toString() {
        return "MisionExploracion: {" +
                "nombre = '" + nombre + '\'' +
                ", duracion = " + duracion +
                ", prioridad = " + prioridad +
                ", estado = '" + estado + '\'' +
                ", experienciaRequerida = " + experienciaRequerida +
                '}';
    }
}

class MisionRecoleccionDatos extends Mision {
    public MisionRecoleccionDatos(String nombre, int duracion, int prioridad, int experienciaTecnica, EstadoMission estado) {
        super(nombre, duracion, prioridad,  experienciaTecnica, estado);
        if(duracion>8){
            throw new IllegalArgumentException("La duración típica debe ser estar entre 4 y 8 horas.");
        }
    }

    public MisionRecoleccionDatos() {
        super("", 1, 1, 0, EstadoMission.PENDIENTE);
    }

    @Override
    public TipoMision getTipo(){
        return TipoMision.TECHNICA;
    }

    @Override
    public String toString() {
        return "MisionRecoleccionDatos: {" +
                "nombre = '" + nombre + '\'' +
                ", duracion = " + duracion +
                ", prioridad = " + prioridad +
                ", estado = '" + estado + '\'' +
                ", experienciaRequerida = " + experienciaRequerida +
                '}';
    }
}

class MisionColonizacion extends Mision{
    private int capacidadCarga;

    public MisionColonizacion() {
        super("", 1, 1, 0, EstadoMission.PENDIENTE);
    }

    public int getCapacidadCarga() {
        return capacidadCarga;
    }

    public MisionColonizacion(String nombre, int duracion, int prioridad, int experienciaEstrategica, int capacidadCarga, EstadoMission estado) {
        super(nombre, duracion, prioridad,experienciaEstrategica, estado);
        if(duracion<6){
            throw new IllegalArgumentException("La duración mínima para colonizar es de 6 horas.");
        }
        this.capacidadCarga=capacidadCarga;
    }

    @Override
    public TipoMision getTipo(){
        return TipoMision.ESTRATEGICA;
    }

    @Override
    public String toString() {
        return "MisionColonizacion: {" +
                "nombre = '" + nombre + '\'' +
                ", duracion = " + duracion +
                ", prioridad = " + prioridad +
                ", estado = '" + estado + '\'' +
                ", experienciaRequerida = " + experienciaRequerida +
                ", capacidadCarga = " + capacidadCarga +
                '}';
    }

}
