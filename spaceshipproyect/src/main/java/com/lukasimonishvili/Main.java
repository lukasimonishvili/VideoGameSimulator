package com.lukasimonishvili;

import java.io.ObjectInputStream.GetField;

public class Main {
    public static void main(String[] args) {
        DepositoDeNaves depositoNaves=new DepositoDeNaves();
        ControlDeMisiones misiones = new ControlDeMisiones();

        Mision mision=new MisionExploracion("Explorar Marte", 9, 5, 2, EstadoMission.COMPLETADA);
        NaveEspacial nave=new NaveEspacial("Atlanta", 1000, true, 500); 
        
        nave.registrarExperiencia(TipoMision.CIENTIFICA, 5);
        nave.getExperienciaTotal();
    
    }
}