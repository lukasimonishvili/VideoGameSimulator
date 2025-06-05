package com.lukasimonishvili;

//import com.lukasimonishvili.Mision.EstadoMision;

public class Main {
    public static void main(String[] args) {
        //RegistroMisiones registro = new RegistroMisiones();
        //DepositoDeNaves naves=new DepositoDeNaves();
        ControlDeMisiones misiones = new ControlDeMisiones();
        HistorialDeMisiones historial=new HistorialDeMisiones();
        
        Mision mision=new MisionExploracion("Explorar Marte", 9, 5, 2, EstadoMission.COMPLETADA);
        NaveEspacial nave=new NaveEspacial("Atlanta", 1000, true, 500);
        Mision mision2=new MisionExploracion("Explorar Venus", 9, 9, 6, EstadoMission.COMPLETADA);
        NaveEspacial nave2=new NaveEspacial("Zeus", 2000, true, 500);

        //registro.agregarMision();
        //naves.creareNave();
        nave.registrarExperiencia(TipoMision.CIENTIFICA, 2);
        misiones.ejecutarMision(mision, nave);
        misiones.ejecutarMision(mision2, nave2);
        misiones.mostrarHistorial();
        //registro.listarMisiones();

        
        
        /*
        Mision mision1=new MisionExploracion("Exploración de Marte", 30, 5, 10);
        
        NaveEspacial nave1 = new NaveEspacial("Galáctica", 1200, true, 2000);
        nave1.registrarExperiencia("cientifica", 10);
        System.out.println("\n-----------------------------");
        System.out.println("Datos de la Nave Espacial:");
        System.out.println("Nombre: " + nave1.getNombre());
        System.out.println("Autonomía máxima: " + nave1.getAutonomiaMaxima() + " horas");
        System.out.println("Autonomía actual: " + nave1.getAutonomiaActual() + " horas");
        System.out.println("Sensores científicos: " + (nave1.tieneSensoresCientificos() ? "Sí" : "No"));
        System.out.println("Experiencia total: " + nave1.getExperienciaTotal());
        
        System.out.println("\n-----------------------------");
        System.out.println("Evaluación de la Misión.\n");
        if(mision1.esApta(nave1)) {
            System.out.println("La nave es apta para la misión.");
            nave1.ejecutarMision(mision1.getDuracion(), mision1.getTipoExperiencia(), 20);
            mision1.completarMision();
        } else {
            System.out.println("La nave no es apta para la misión.");
        }
        
        System.out.println("\n----------RESULTADOS-------------");
        System.out.println("\nEstado de la Misión: " + mision1.getEstado());
        System.out.println("Autonomía actual de la nave después de la misión: " + nave1.getAutonomiaActual() + " horas");
        System.out.println("Experiencia total de la nave después de la misión: " + nave1.getExperienciaTotal());
        System.out.println("\n-----------------------------");
        */
    }
}