package com.lukasimonishvili;
import java.util.List;
import java.util.Scanner;

public class MenuDeInterfaz {
    private final Scanner scanner = new Scanner(System.in);
    private final DepositoDeNaves depositoDeNaves = new DepositoDeNaves();
    private final RegistroMisiones registroMisiones = new RegistroMisiones();
    private final ControlDeMisiones controlDeMisiones = new ControlDeMisiones();

    final String RESET = "\u001B[0m";
    final String GREEN = "\u001B[32m";
    final String CYAN = "\u001B[36m";
    final String YELLOW = "\u001B[33m";
    final String RED = "\u001B[31m";

    String BOLD = "\u001B[1m";
    String SUB = "\\u001B[4m";

    public void mostrarMenu() {
        System.out.println(GREEN+"\n-------BIENVENIDO AL PROGRAMA DE GESTIÓN DE MISIONES ESPACIALES.------"+RESET);
        System.out.println("1. Registrar una nueva nave espacial.");
        System.out.println("2. Registrar una nueva misión.");
        System.out.println("3. Ejecutar una misión.");
        System.out.println("4. Mostrar todas las naves espaciales registradas.");
        System.out.println("5. Mostrar las misiones registradas.");
        System.out.println("6. Mostrar estadísticas de la nave espacial.");
        System.out.println("7. Mostrar ranking de naves espaciales por experiencia.");
        System.out.println("8. Ver el historial de misiones.");
        System.out.println("9. Salir.");
        System.out.print("Seleccione una opción: ");
        int opcion = scanner.nextInt();
        switch (opcion) {
            case 1:
                try{
                    depositoDeNaves.creareNave();
                } catch(Exception e) {
                    System.err.println(BOLD+ RED +"\n---Error al crear la nave espacial. Asegúrese de que los datos ingresados sean correctos.---"+RESET);
                    mostrarMenu();
                }
            break;
            case 2:
                try{
                    registroMisiones.agregarMision();
                } catch(Exception e) {
                    System.err.println(BOLD+ RED +"\n---Error al registrar la misión. Asegúrese de que los datos ingresados sean correctos.---"+RESET);
                    mostrarMenu();
                }
            break;
            case 3:
                menuDeEjecutarMision();
            break;
            case 4:
                depositoDeNaves.mostrarTodosLosNaves();
            break;
            case 5:
                menuDeMostrarMisiones();
            break;
            case 6:
                mostrarEstadisticasNaveEspacial();
            break;
            case 7:
                depositoDeNaves.mostrarRankingNavesPorExperiencia();
                break;
            case 8:
                controlDeMisiones.mostrarHistorial();
            break;
            case 9:
                System.out.println(BOLD+ GREEN+"----Saliendo del programa.----"+RESET);
                return;
            default:
                System.out.println(BOLD+ RED +"---Opción no válida. Intente de nuevo.---"+RESET);
        }
        mostrarMenu();
    }

    private void mostrarEstadisticasNaveEspacial() {
        System.out.println("\n----Mostrar estadísticas de la nave espacial.----");
        System.out.println("Ingrese el nombre de la nave espacial:");
        for(int i = 0; i < depositoDeNaves.naves.size(); i++) {
            System.out.println((i + 1) + ". " + depositoDeNaves.naves.get(i).getNombre());
        }
        int naveSeleccionada = scanner.nextInt() - 1;
        System.out.println("\n------Estadísticas de la nave espacial seleccionada: " );
        System.out.println(depositoDeNaves.naves.get(naveSeleccionada).getEstatisticaDeNave());
    }

    private void menuDeEjecutarMision() {
        System.out.println("\n------Ejecutar una misión.------");
        System.out.println("Ingrese el nombre de la misión que desea ejecutar:");
        List<Mision> misiones = registroMisiones.filtrarMisionesPorEstado(EstadoMission.PENDIENTE);
        if( misiones.isEmpty()) {
            System.out.println("----->No hay misiones pendientes para ejecutar.-----");
            return;
        }
        for(int i = 0; i < misiones.size(); i++) {
            TipoMision tipo = misiones.get(i).getTipo();
            String tipoDeMision = tipo == TipoMision.TECHNICA ? "Técnica" :
                                  tipo == TipoMision.CIENTIFICA ? "Científica" :
                                  "Estratégica";
            System.out.println((i + 1) + ". " + misiones.get(i).getNombre() + " - Tipo: " + tipoDeMision);
        }
        int misionSeleccionada = scanner.nextInt() - 1;
        System.out.println(misiones.get(misionSeleccionada).getNombre() + " ha sido seleccionada.");
        System.out.println("---->Ingrese el la nave espacial que desea usar:");
        List<NaveEspacial> navesAptas = depositoDeNaves.getNavesAptadaParaMision(
            misiones.get(misionSeleccionada).getDuracion(),
            misiones.get(misionSeleccionada).getTipo(),
            misiones.get(misionSeleccionada).getExperienciaRequerida()
        );
        if(navesAptas.isEmpty()) {
            System.out.println("\n---No hay naves espaciales aptas para esta misión.---");
            return;
        }
        for(int i = 0; i < navesAptas.size(); i++) {
            System.out.println((i + 1) + ". " + navesAptas.get(i).getNombre());
        }
        int naveSeleccionada = scanner.nextInt() - 1;
        controlDeMisiones.ejecutarMision(misiones.get(misionSeleccionada), navesAptas.get(naveSeleccionada));
        depositoDeNaves.guardarNavesEnArchivo();
        registroMisiones.guardarMisionesEnArchivo();

    }

    private void menuDeMostrarMisiones() {
        System.out.println("\n-----Mostrar las misiones registradas.-----");
        System.out.println("1. Mostrar todas las misiones.");
        System.out.println("2. Filtrar misiones por estado.");
        System.out.println("3. Filtrar misiones por tipo.");
        System.out.println("4. Filtrar misiones por prioridad.");
        System.out.print("Seleccione una opción: ");

        int opcion = scanner.nextInt();
        switch (opcion) {
            case 1:
                registroMisiones.listarMisiones(registroMisiones.misiones);
            break;
            case 2:
                System.out.println("\nIngrese el estado de la misión.");
                System.out.println("1. Pendiente");
                System.out.println("2. Completada");
                System.out.println("3. Fallida");
                int estado = scanner.nextInt();
                EstadoMission estadoMision = estado == 1 ? EstadoMission.PENDIENTE :
                                            estado == 2 ? EstadoMission.COMPLETADA :
                                            EstadoMission.FALLIDA;
                registroMisiones.listarMisiones(registroMisiones.filtrarMisionesPorEstado(estadoMision));
            break;
            case 3:
                System.out.println("\nIngrese el tipo de misión.");
                System.out.println("1. Tecnica");
                System.out.println("2. Científica");  
                System.out.println("3. Estratégica");
                int tipoMision = scanner.nextInt();
                TipoMision tipo = tipoMision == 1 ? TipoMision.TECHNICA :
                                 tipoMision == 2 ? TipoMision.CIENTIFICA :
                                 TipoMision.ESTRATEGICA;
                registroMisiones.listarMisiones(registroMisiones.filtrarMisionesPorTipo(tipo));
                break;
            case 4:
                System.out.println("\nIngrese la direccion de la prioridad.");
                System.out.println("1. De alta a baja");
                System.out.println("2. De baja a alta");
                int prioridad = scanner.nextInt();
                registroMisiones.listarMisiones(registroMisiones.filtrarMisionesPorPrioridad(prioridad == 1 ? "alta" : "baja"));
                break;
            default:
                System.out.println("\n---Opción no válida. Intente de nuevo.---");
        }
    }
}
