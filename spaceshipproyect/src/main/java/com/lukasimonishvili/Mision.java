package com.lukasimonishvili;

public abstract class Mision {
    enum EstadoMision {
        PENDIENTE,
        COMPLETADA
    }
    protected String nombre;
    protected int duracion;
    protected int autonomiaMaxima;
    protected int prioridad;
    protected EstadoMision estado;
    protected int experienciaRequerida;
    
    public Mision(String nombre, int duracion, int prioridad, int experienciaRequerida, int autonomiaMaxima) {
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
        this.estado = EstadoMision.PENDIENTE;
        this.experienciaRequerida = experienciaRequerida;
        this.autonomiaMaxima = autonomiaMaxima;
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
    
    public EstadoMision getEstado() {
        return estado;
    }
    
    public int getAutonomiaMaxima(){
        return autonomiaMaxima;
    }
    
    public void completarMision() {
        if (estado == EstadoMision.PENDIENTE) {
            this.estado = EstadoMision.COMPLETADA;
            System.out.println("Misión completada: " + nombre);
        } else {
            throw new IllegalStateException("La misión ya está completada.");
        }
    }
    
    //Validamos si la nave puede ejecutar la misión
    public abstract boolean esApta(NaveEspacial nave); 

    public abstract String getTipoExperiencia();
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
    public MisionExploracion(String nombre, int duracion, int prioridad, int experienciaCientifica, int autonomia) {
        super(nombre, duracion, prioridad,experienciaCientifica, autonomia);
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
    public String getTipoExperiencia(){
        return "científica";
    }
}

class MisionRecoleccionDatos extends Mision{
    public MisionRecoleccionDatos(String nombre, int duracion, int prioridad, int experienciaTecnica, int autonomia) {
        super(nombre, duracion, prioridad,  experienciaTecnica, autonomia);
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
    public String getTipoExperiencia(){
        return "técnica";
    }
}

class MisionColonizacion extends Mision{
    int capacidadCarga;

    public MisionColonizacion(String nombre, int duracion, int prioridad, int experienciaEstrategica, int capacidadCarga, int autonomia) {
        super(nombre, duracion, prioridad,experienciaEstrategica, autonomia);
        if(duracion<6){
            throw new IllegalArgumentException("La duración mínima para colonizar es de 6 horas.");
        }
        this.capacidadCarga=capacidadCarga;
    }

    @Override //Falta getCapacidadCarga en NaveEspacial
    public boolean esApta(NaveEspacial nave) {
        return nave.getCapacidadCarga() >= capacidadCarga &&
               nave.aptasParaUnaMision(duracion, "estrategica", experienciaRequerida);
    }

    @Override
    public String getTipoExperiencia(){
        return "estrategica";
    }

}

//