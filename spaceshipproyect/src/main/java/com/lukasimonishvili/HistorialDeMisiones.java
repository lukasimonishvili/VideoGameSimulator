package com.lukasimonishvili;

import java.util.ArrayList;
import java.util.List;

public class HistorialDeMisiones {
    private List<RegistroHistorial> registroHistorial =new ArrayList<>();
   
    public void registrarResultado(Mision mision, NaveEspacial nave, int experienciaObtenida, String eventoEspecial){
        RegistroHistorial nuevoRegistro=new RegistroHistorial(mision, nave, experienciaObtenida, eventoEspecial);
        registroHistorial.add(nuevoRegistro);
        System.out.println("---NUEVO REGISTRO AGREGADO---");

    }

    public void mostrarHistorial(){
        if(registroHistorial.isEmpty()){
            System.out.println("No se encuentras registros en el historial.");
        }else{
            for (RegistroHistorial historial : registroHistorial) {
                System.out.println(historial);
            }
        }
    }
}
