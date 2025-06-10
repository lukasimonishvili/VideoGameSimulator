package com.lukasimonishvili;

import java.util.Random;

public class ControlDeMisiones {
    private HistorialDeMisiones historial=new HistorialDeMisiones();

    public void ejecutarMision(Mision mision, NaveEspacial nave) {
        if (nave.aptasParaUnaMision(mision.getDuracion(), mision.getTipoExperiencia(), mision.getExperienciaRequerida())) {
            System.out.println("Ejecutando misión: " + mision.getNombre());
            Random aleatorio = new Random();
            int numeroAleatorio = aleatorio.nextInt(100);
            int experiencia = 1000;

            if(numeroAleatorio < 10) { 
                mision.fallarMision();
                experiencia = 0;
            }else {
                mision.completarMision();
            }

            String eventoEspecial="";
            if(numeroAleatorio >= 10 && numeroAleatorio <= 14) {
                experiencia = 2000;
                eventoEspecial="Mejora tecnológica.";
                System.out.println("¡Mejora tecnológica! Has obtenido una experiencia doble.");
            }

            if(numeroAleatorio >= 15 && numeroAleatorio <= 19) {
                eventoEspecial="Descubrimiento especial.";
                System.out.println("¡Descubrimiento especial!");
            }

            nave.ejecutarMision(mision.getDuracion(), mision.getTipoExperiencia(), experiencia);
            nave.restaurarNave();
            historial.registrarResultado(mision, nave, experiencia, eventoEspecial);
        } else {
            System.out.println("La nave no está apta para la misión: " + mision.getNombre());
        }
    }

    public void mostrarHistorial(){
        historial.mostrarHistorial();
    }

}
