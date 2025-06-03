package com.lukasimonishvili;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DepositoDeNaves {
    public List<NaveEspacial> naves = new ArrayList<>();

    public DepositoDeNaves() {
         NaveEspacial nave1 = new NaveEspacial("Nautilus", 3000, false);
         NaveEspacial nave2 = new NaveEspacial("Space-X", 1800, true);
         NaveEspacial nave3 = new NaveEspacial("Black pearl", 2500, true);
         NaveEspacial nave4 = new NaveEspacial("North light", 2700, false);

         naves.add(nave1);
         naves.add(nave2);
         naves.add(nave3);
         naves.add(nave4);
    }

    public void creareNave() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Por favor escriba el nombre de la nave espacial");
        String nombre = scanner.nextLine();
        System.out.println("Por favor escriba la autonomia maxima de la nave espacial");
        int autonomiaMaxima = scanner.nextInt();
        System.out.println("está nave tiene sensores cientificos? yes/no");
        String sensoresCientificosInput = scanner.next();
        boolean sensoresCientific = sensoresCientificosInput.equals("yes");
        NaveEspacial nuevoNaveEspacial = new NaveEspacial(nombre, autonomiaMaxima, sensoresCientific);
        naves.add(nuevoNaveEspacial);
        System.out.println("Nueva nave espacial creada");
    }
}
