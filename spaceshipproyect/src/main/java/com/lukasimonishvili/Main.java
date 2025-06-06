package com.lukasimonishvili;

public class Main {
    public static void main(String[] args) {
        DepositoDeNaves depositoNaves=new DepositoDeNaves();
        ControlDeMisiones misiones = new ControlDeMisiones();
        
        Mision mision=new MisionExploracion("Explorar Marte", 9, 5, 2, EstadoMission.COMPLETADA);
        NaveEspacial nave=new NaveEspacial("Atlanta", 1000, true, 500); 
        misiones.ejecutarMision(mision, nave);
        misiones.mostrarHistorial();
        depositoNaves.mostrarRankingNaves();

        
    
    }
}