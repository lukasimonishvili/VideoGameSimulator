package com.lukasimonishvili;

import java.util.Random;

public class ControlDeMisiones {
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

            if(numeroAleatorio >= 10 && numeroAleatorio <= 14) {
                experiencia = 2000;
                System.out.println("¡Mejora tecnológica! Has obtenido una experiencia doble.");
            }

            if(numeroAleatorio >= 15 && numeroAleatorio <= 19) {
                System.out.println("¡Descubrimiento especial!");
            }

            nave.ejecutarMision(mision.getDuracion(), mision.getTipoExperiencia(), experiencia);
            nave.restaurarNave();
            nave.registrarMision(mision);
        } else {
            System.out.println("La nave no está apta para la misión: " + mision.getNombre());
        }
    }

}
