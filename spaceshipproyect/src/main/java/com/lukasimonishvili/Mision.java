package com.lukasimonishvili;

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
    
    public void completarMision() {
        if (estado == EstadoMission.PENDIENTE) {
            this.estado = EstadoMission.COMPLETADA;
            System.out.println("Misión completada: " + nombre);
        } else {
            throw new IllegalStateException("La misión ya está completada.");
        }
    }

    public void misionPendiente(){
        if(estado==EstadoMission.PENDIENTE){
            System.out.println("Misión pendiente: " + nombre);
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
    //Validamos si la nave puede ejecutar la misión
    public abstract boolean esApta(NaveEspacial nave); 

    public abstract TipoMision getTipoExperiencia();
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
    
    @Override
    public boolean esApta(NaveEspacial nave) {
        return nave.getAutonomiaActual()>1000 &&
        nave.getExperienciaTotal()>= experienciaRequerida;
    }

    @Override
    public TipoMision getTipoExperiencia(){
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

    @Override
    public boolean esApta(NaveEspacial nave) {
        return nave.tieneSensoresCientificos() && 
               nave.getExperienciaTotal() >= experienciaRequerida;
    }

    @Override
    public TipoMision getTipoExperiencia(){
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
    int capacidadCarga;

    public MisionColonizacion(String nombre, int duracion, int prioridad, int experienciaEstrategica, int capacidadCarga, EstadoMission estado) {
        super(nombre, duracion, prioridad,experienciaEstrategica, estado);
        if(duracion<6){
            throw new IllegalArgumentException("La duración mínima para colonizar es de 6 horas.");
        }
        this.capacidadCarga=capacidadCarga;
    }

    @Override //Falta getCapacidadCarga en NaveEspacial
    public boolean esApta(NaveEspacial nave) {
        return nave.getCapacidadCarga() >= capacidadCarga &&
               nave.aptasParaUnaMision(duracion, TipoMision.ESTRATEGICA, experienciaRequerida);
    }

    @Override
    public TipoMision getTipoExperiencia(){
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
