package com.lukasimonishvili;

public abstract class Mision {
    String nombre;
    int duracion;
    int prioridad;
    String estado;
    int experienciaRequerida;
    
    public Mision(String nombre, int duracion, int prioridad, String estado) {
        if (duracion <= 0) {
            throw new IllegalArgumentException("La duración debe ser mayor a 0.");
            
        }
        if(prioridad < 1 || prioridad > 10) {
        throw new IllegalArgumentException("La prioridad debe estar entre 1 y 10.");
        }
        this.nombre = nombre;
        this.duracion = duracion;
        this.prioridad = prioridad;
        this.estado = "Pendiente";
        this.experienciaRequerida = calcularExperienciaRequerida();
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
    
    public void intsetPrioridad(){
        if(prioridad < 1 || prioridad > 10) {
        throw new IllegalArgumentException("La prioridad debe estar entre 1 y 10.");
        }
        this.prioridad = prioridad;
    }
    
    public String getEstado() {
        return estado;
    }

    public int getExperienciaRequerida() {
        return experienciaRequerida;
    }

    public abstract int calcularExperienciaRequerida();

    public void completarMision() {
        if (estado.equals("Pendiente")) {
            estado = "Completada";
        } else {
            throw new IllegalStateException("La misión ya está completada.");
        }
    }

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
    private int autonomia;

    public MisionExploracion(String nombre, int duracion, int prioridad, int autonomia) {
        super(nombre, duracion, prioridad, "Pendiente");
        if(autonomia<=1000){
            throw new IllegalArgumentException("La autonomía debe ser mayor a 1000 km.");
        }
        this.autonomia = autonomia;
    }
    
    @Override
    public int calcularExperienciaRequerida() {
        return getDuracion()*10;
    }
}

class MisionRecoleccionDatos extends Mision{
    private boolean sensoresCientificos;

    public MisionRecoleccionDatos(String nombre, int duracion, int prioridad, boolean sensoresCientificos) {
        super(nombre, duracion, prioridad, "Pendiente");
        if(!sensoresCientificos){
            throw new IllegalArgumentException("La autonomía debe ser mayor a 1000 km.");
        }
        this.sensoresCientificos = sensoresCientificos;
    }

    @Override
    public int calcularExperienciaRequerida() {
        return getDuracion() * 5;
    }
}

class MisionColonizacion extends Mision{
    private int capacidadCarga;


    public MisionColonizacion(String nombre, int duracion, int prioridad, int capacidadCarga) {
        super(nombre, duracion, prioridad, "Pendiente");
        if(capacidadCarga <500){
            throw new IllegalArgumentException("La capacidad de carga debe ser mayor a 500 kg.");
        }
        this.capacidadCarga=capacidadCarga;
    }

    @Override
    public int calcularExperienciaRequerida() {
        return getDuracion() * 20;
    }
}

