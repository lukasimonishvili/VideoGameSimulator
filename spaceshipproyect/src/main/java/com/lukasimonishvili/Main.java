package com.lukasimonishvili;

import java.io.ObjectInputStream.GetField;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = ScannerSingleton.getInstance().getScanner();
        DepositoDeNaves depositoNaves=new DepositoDeNaves();
        ControlDeMisiones misiones = new ControlDeMisiones();

        Mision mision1=new MisionExploracion("Explorar Marte", 9, 5, 2, EstadoMission.COMPLETADA);
        Mision mision2=new MisionColonizacion("Kappa", 6, 5, 8, 500, EstadoMission.COMPLETADA);
        Mision mision3=new MisionRecoleccionDatos("Informacion", 5, 6, 3, EstadoMission.COMPLETADA);

        NaveEspacial nave1=new NaveEspacial("Atlanta", 2000, true, 5000); 
        NaveEspacial nave2 = new NaveEspacial("Black pearl", 2500, true, 5000);
        NaveEspacial nave3 = new NaveEspacial("North light", 2700, false, 2500);

        misiones.ejecutarMision(mision1, nave1);
        misiones.ejecutarMision(mision2, nave2);
        misiones.ejecutarMision(mision3, nave3);

        misiones.mostrarHistorial();
    }
}