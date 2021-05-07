/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hospital;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author Belén
 */
public class Hospital {

    /**
     * @param args the command line arguments
     */
    private static Scanner numeros = new Scanner(System.in);
    //defino dos Scanner para evitar posibles fallos al pasar del nextIn (o nextFloat) al nextLine
    private static Scanner entrada = new Scanner(System.in);
    private static Conector bd = new Conector();

    public static void main(String[] args) {

        int opcion = 0;
        boolean repetir = true;

        do {
            pintarMenu();
            while (repetir) {
                try {
                    System.out.println("Introduzca una de las siguientes opicones: ");
                    opcion = entrada.nextInt();
                    repetir = false;
                } catch (InputMismatchException e) {
                    System.out.println("NO SE HA INTRODUCIDO UNA OPCIÓN VALIDA");
                }
                entrada = new Scanner(System.in);
            }
            repetir = true;
            tratarOpcionMenu(opcion);

        } while (opcion != 99);

    }

    private static void pintarMenu() {

        System.out.println("********************************************************************************************");
        System.out.println("*******************************************  MENU  *****************************************");
        System.out.println("********************************************************************************************");
        System.out.println("");
        System.out.println("********************************* Elija una de las siguientes opciones *********************");
        System.out.println("");
        System.out.println("--- (1)  Dar de Alta un Trabajador ---------------------------------------------------------");
        System.out.println("--- (2)  Dar de Baja un Trabajador ---------------------------------------------------------");
        System.out.println("--- (3)  Dar de Baja un Departamento -------------------------------------------------------");
        System.out.println("--- (4)  Borrar un Cuerpo/Especialidad -----------------------------------------------------");
        System.out.println("--- (5)  Modificar Datos -------------------------------------------------------------------");
        System.out.println("--- (6)  Consulta del registro de Trabajadores ordenaos por salario ------------------------");
        System.out.println("--- (7)  Consulta del registro de Trabajadores ordenados por Departamento ------------------");
        System.out.println("--- (8)  Consulta del registro de Trabajadores ordenado por Especialidades -----------------");
        System.out.println("--- (99) Salir -----------------------------------------------------------------------------");
        System.out.println("");
        System.out.println("********************************************************************************************");

    }

    private static void tratarOpcionMenu(int opcion) {

        switch (opcion) {

            case 1:
                altaTrabajador();
                break;
            case 2:
                bajaTrabajador();
                break;
            case 3:
                bajaDepartamento();
                break;
            case 4:
                borrarEspecialidadCuerpo();
                break;
            case 5:
                modificarDatos();
                break;
            case 6:
                consultaTrabajadorSalario();
                break;
            case 7:
                consultaTrabajadorDepartamento();
                break;
            case 8:
                consultaTrabajadorEspecialidad();
                break;
            case 99:
                bd.desconectar();
            default:
                System.out.println("No ha introducido una opcion correcta vuelva a intentarlo.");
                break;

        }

    }

    private static void altaTrabajador() {

        Trabajador t;
        int identificador = 0, numDepartamento = 0;
        String nombre, nombreTrabajador, cuerpo, codEspecialidad, nombreDepar, ubicacion;
        String nombreEspecialidad, especialidad;
        float salario = 0;
        boolean repetir = true;

        System.out.println("Introduzca los datos de un nuevo Trabajador");
        System.out.println("Nombre: ");
        nombre = entrada.nextLine();
        nombreTrabajador = nombre.toUpperCase();

        while (repetir) {
            try {
                System.out.println("Identificador: ");
                identificador = numeros.nextInt();
                repetir = false;
            } catch (InputMismatchException e) {
                System.out.println("El formato introducido no es correcto; Vuelva a intentarlo. ");
                numeros = new Scanner(System.in);
            }
        }
        repetir = true;

        while (repetir) {
            try {
                System.out.println("Numero de Departamento: ");
                numDepartamento = numeros.nextInt();
                repetir = false;
            } catch (InputMismatchException e) {
                System.out.println("El formato introducido no es correcto; Vuelva a intentarlo. ");
                numeros = new Scanner(System.in);
            }
        }
        repetir = true;

        System.out.println("Especialidad: ");
        codEspecialidad = entrada.nextLine();

        System.out.println("Cuerpo: ");
        cuerpo = entrada.nextLine();

        while (repetir) {
            try {
                System.out.println("Salario: ");
                salario = numeros.nextFloat();
                repetir = false;
            } catch (InputMismatchException e) {
                System.out.println("El formato introducido no es correcto; Vuelva a intentarlo. ");
                numeros = new Scanner(System.in);
            }
        }
        repetir = true;

        t = new Trabajador(identificador, nombreTrabajador, salario, numDepartamento, cuerpo, codEspecialidad);

        if (bd.comprobarEspecialidadCuerpo(t) && bd.comprobarDepartamento(t) /*&&!bd.buscartrabajador*/) {
            bd.altaTrabajador(t);

        } else {
            if (!bd.comprobarDepartamento(t)) {

                System.out.println("El Departamenteo  donde desea incorporar este trabajador, NO EXISTE.");
                System.out.println("Procedemos a crearlo");
                System.out.println("Nombre del nuevo Departamento: ");
                nombreDepar = entrada.nextLine();
                System.out.println("Ubicación del nuevo Departamento: ");
                ubicacion = entrada.nextLine();
                Departamento d = new Departamento(t.getNumDepartamento(), nombreDepar, ubicacion);
                if (bd.altaDepartamento(d)) {
                    System.out.println("Se ha dado de alta el Departamento:");
                    System.out.println(d);
                }

            }
            if (!bd.comprobarEspecialidadCuerpo(t)) {

                System.out.println("La Especialidad de este Trabajador, NO EXISTE.");
                System.out.println("Procedemos a crearla");
                System.out.println("Nombre de la nueva Especialidad es: ");
                nombreEspecialidad = entrada.nextLine();
                System.out.println("La denominación de la nueva Especialidad:  ");
                especialidad = entrada.nextLine();
                CuerpoEspecialidad e = new CuerpoEspecialidad(t.getCuerpo(), nombreEspecialidad, especialidad, t.getCodEspecialidad());
                if (bd.altaEspecialidad(e)) {
                    System.out.println("Se ha dado de alta la Especialidad:");
                    System.out.println(e);
                }
            }
            bd.altaTrabajador(t);
            System.out.println("Se ha dado de alta al Trabajador:");
            System.out.println(t);

        }

    }

    private static void bajaTrabajador() {
        String nombre, nombreTrabajador, confirmacion;
        Trabajador t;
        boolean flag = false;

        System.out.println("Introduzca el nombre del Trabajador que quiere dar de baja: ");
        nombre = entrada.nextLine();
        nombreTrabajador = nombre.toUpperCase();

        t = bd.buscarTrabajador(nombreTrabajador);
        if (t == null) {
            System.out.println("El Trabajador cuyo nombre es: " + nombreTrabajador + "no se encuentra registrado en el sistema.");

        } else {
            do {
                System.out.println("Procedemos a borrar los datos del Trabajador: ");
                System.out.println(t);
                System.out.println("¿Esta de acuerdo? (S/N)");
                confirmacion = entrada.nextLine();
                if (confirmacion.equalsIgnoreCase("S")) {
                    bd.bajaTrabajador(nombreTrabajador);
                    flag = false;
                } else if (confirmacion.equalsIgnoreCase("N")) {
                    System.out.println("No se borrará este Trabajador");
                    flag = false;
                } else if (!confirmacion.equalsIgnoreCase("S") && confirmacion.equalsIgnoreCase("N")) {
                    System.out.println("La opcion indicada no es correcta.");
                    flag = true;
                }

            } while (flag);
        }

    }

    private static void bajaDepartamento() {
        int numDepartamento, numeroDeparTraspaso;
        String confirmacion, nomDeparTras, ubiDeparTras;
        Departamento d, dt, df;
        boolean flag = false;

        System.out.println("Introduzca el codigo del Departamento que quiere dar de baja: ");
        numDepartamento = numeros.nextInt();

        d = bd.buscarDepartamento(numDepartamento);
        if (d == null) {
            System.out.println("El Departamento cuyo numero identificativo es: " + numDepartamento + "no se encuentra registrado en el sistema.");
        }
        if (bd.trabajadoresDepeartamento(d)) {
            System.out.println("El departamento indicado tiene trabajadores.");
            System.out.println("Si confirma su eliminación los trabajadores serán transferidos a un departamento alternativo.");

            do {
                System.out.println("Procedemos a borrar los datos del Departamento: ");
                System.out.println(d);
                System.out.println("¿Esta de acuerdo? (S/N)");
                confirmacion = entrada.nextLine();
                if (confirmacion.equalsIgnoreCase("S")) {
                    System.out.println("¿A que departamento quiere traspasar a los empleados?");
                    System.out.println("Indique el Número de Departamento: ");
                    numeroDeparTraspaso = entrada.nextInt();
                    dt = bd.buscarDepartamento(numeroDeparTraspaso);
                    if (dt == null) {
                        System.out.println("El departamento indicado no existe, procedemos a darlo de alta. ");
                        System.out.println("Indique el nombre del departamento: ");
                        nomDeparTras = entrada.nextLine();
                        System.out.println("Inidique la Ubicación: ");
                        ubiDeparTras = entrada.nextLine();
                        df = new Departamento(numeroDeparTraspaso, nomDeparTras, ubiDeparTras);
                        bd.altaDepartamento(df);
                        bd.traspasarTrabajadores(d, df);
                    } else {
                        bd.traspasarTrabajadores(d, dt);
                    }

                    bd.bajaDepartamento(d);

                    flag = false;
                } else if (confirmacion.equalsIgnoreCase("N")) {
                    System.out.println("No se borrará este Departamento");
                    flag = false;
                } else if (!confirmacion.equalsIgnoreCase("S") && confirmacion.equalsIgnoreCase("N")) {
                    System.out.println("La opcion indicada no es correcta.");
                    flag = true;
                }

            } while (flag);
        } else {
            do {
                System.out.println("Procedemos a borrar los datos del Departamento: ");
                System.out.println(d);
                System.out.println("¿Esta de acuerdo? (S/N)");
                confirmacion = entrada.nextLine();
                if (confirmacion.equalsIgnoreCase("S")) {
                    bd.bajaDepartamento(d);
                    flag = false;
                } else if (confirmacion.equalsIgnoreCase("N")) {
                    System.out.println("No se borrará este Departamento");
                    flag = false;
                } else if (!confirmacion.equalsIgnoreCase("S") && confirmacion.equalsIgnoreCase("N")) {
                    System.out.println("La opcion indicada no es correcta.");
                    flag = true;
                }

            } while (flag);

        }

    }

    private static void borrarEspecialidadCuerpo() {
        String cuerpo, codEspecialidad, confirmacion;
        CuerpoEspecialidad e;
        boolean flag = false;

        System.out.println("Introduzca el Código de la Especialidad de que desea dar de baja:  ");
        codEspecialidad = entrada.nextLine();
        System.out.println("Introduzca el Cuerpo de la Especialidad de que desea dar de baja:  ");
        cuerpo = entrada.nextLine();

        e = bd.buscarCuerpoEspecialidad(cuerpo, codEspecialidad);
        if (e == null) {
            System.out.println("La Especialidad inicada no se encuentra registrada en el sistema.");

        } else if (bd.trabajadoresEspecialidad(e)) {
            System.out.println("El departamento indicado tiene trabajadores.");
            System.out.println("NO PUEDE ELIMINARSE");

        } else {
            do {
                System.out.println("Procedemos a borrar los datos de la Especialidad: ");
                System.out.println(e);
                System.out.println("¿Esta de acuerdo? (S/N)");
                confirmacion = entrada.nextLine();
                if (confirmacion.equalsIgnoreCase("S")) {
                    bd.bajaCuerpoEspecialidad(e);
                    flag = false;
                } else if (confirmacion.equalsIgnoreCase("N")) {
                    System.out.println("No se borrará esta Especialidad");
                    flag = false;
                } else if (!confirmacion.equalsIgnoreCase("S") && confirmacion.equalsIgnoreCase("N")) {
                    System.out.println("La opcion indicada no es correcta.");
                    flag = true;
                }

            } while (flag);
        }
    }

    private static void modificarDatos() {
        int opcion = 0;
        boolean repetir = true;

        do {
            menuModificaciones();
            while (repetir) {
                try {
                    System.out.println("Introduzca una de las siguientes opicones: ");
                    opcion = entrada.nextInt();
                    repetir = false;
                } catch (InputMismatchException e) {
                    System.out.println("NO SE HA INTRODUCIDO UNA OPCIÓN VALIDA");
                }
                entrada = new Scanner(System.in);
            }
            repetir = true;
            tratarModificaciones(opcion);

        } while (opcion != 99);

    }

    private static void menuModificaciones() {

        System.out.println("*************************************************");
        System.out.println("*************  MENU MODIFICACIONES  *************");
        System.out.println("*************************************************");
        System.out.println("");
        System.out.println("***** Elija una de las siguientes opciones ******");
        System.out.println("");
        System.out.println("--- (1)  Modificar datos de Trabajador ----------");
        System.out.println("--- (2)  Modificar datos de Departamento --------");
        System.out.println("--- (3)  Modificar datos de Especialidad --------");
        System.out.println("--- (99) Salir de este menún sin hacer nada -----");
        System.out.println("");
        System.out.println("*************************************************");
    }

    private static void tratarModificaciones(int opcion) {

        switch (opcion) {

            case 1:
                modificarTrabajador();
                break;
            case 2:
                modificarDepartamento();
                break;
            case 3:
                modificarEspecialidad();
                break;
            case 99:
                System.out.println("Se ha decidido no modificar ningún campo.");
                break;

        }
    }

    private static void modificarTrabajador() {
        String nombre, cuerpo, codEspecialidad, confirmacion, nombreDepar, ubicacion;
        String nombreEspecialidad, especialidad;
        Trabajador t, nuevoT;
        int identificador, numDepartamento;
        float salario;

        System.out.println("Procedemos a modificar datos de un Trabajador.");
        System.out.println("Indique el nombre del trabajador que desea modificar: ");
        nombre = entrada.nextLine();

        t = bd.buscarTrabajador(nombre);
        //a continuación guardamos las variables con los datos del trabajador,
        //las que modifiquemos cambiaran su valor y las que no quedaran con los datos de trabajador
        //para poder pasar todos los paramentros a la clase DAO para poder ejecutar la sentencia.

        identificador = t.getIdentificador();
        salario = t.getSalario();
        numDepartamento = t.getNumDepartamento();
        cuerpo = t.getCuerpo();
        codEspecialidad = t.getCodEspecialidad();

        System.out.println("Vamos a modificar los datos del siguiente trabajador:");
        System.out.println(t);
        System.out.println("¿Esta de acuerdo?(presione 'S' si quiere continuar con la modificación)");
        confirmacion = entrada.nextLine();
        if (confirmacion.equalsIgnoreCase("S")) {

            System.out.println("¿Desea modificar el identificador? (presione 'S' para modificar)");
            confirmacion = entrada.nextLine();
            if (confirmacion.equalsIgnoreCase("S")) {
                System.out.println("Escriba el nuveo identificador: ");
                identificador = entrada.nextInt();
            }

            System.out.println("¿Desea modificar el salario? (presione 'S' para modificar)");
            confirmacion = entrada.nextLine();
            if (confirmacion.equalsIgnoreCase("S")) {
                System.out.println("Escriba el nuevo salario: ");
                salario = numeros.nextFloat();
            }

            System.out.println("¿Desea modificar el Numero de Departamento? (presione 'S' para modificar)");
            confirmacion = entrada.nextLine();
            if (confirmacion.equalsIgnoreCase("S")) {
                System.out.println("Escriba el nuevo Numero de Departamento: ");
                numDepartamento = numeros.nextInt();
            }

            System.out.println("¿Desea modificar el cuerpo de la Especialidad? (presione 'S' para modificar)");
            confirmacion = entrada.nextLine();
            if (confirmacion.equalsIgnoreCase("S")) {
                System.out.println("Escriba el nuevo cuerpo de Especialidad: ");
                cuerpo = entrada.nextLine();
            }

            System.out.println("¿Desea modificar el Código de la Especialidad? (presione 'S' para modificar)");
            confirmacion = entrada.nextLine();
            if (confirmacion.equalsIgnoreCase("S")) {
                System.out.println("Escriba el nuevo Código de la Especialidad: ");
                codEspecialidad = entrada.nextLine();
            }

            nuevoT = new Trabajador(identificador, nombre, salario, numDepartamento, cuerpo, codEspecialidad);

            if (bd.comprobarEspecialidadCuerpo(nuevoT) && bd.comprobarDepartamento(nuevoT)) {
                bd.modificarTrabajador(nuevoT);
            } else {
                if (!bd.comprobarDepartamento(nuevoT)) {
                    System.out.println("El Departamenteo  donde desea incorporar este trabajador, NO EXISTE.");
                    System.out.println("Procedemos a crearlo");
                    System.out.println("Nombre del nuevo Departamento: ");
                    nombreDepar = entrada.nextLine();
                    System.out.println("Ubicación del nuevo Departamento: ");
                    ubicacion = entrada.nextLine();
                    Departamento d = new Departamento(t.getNumDepartamento(), nombreDepar, ubicacion);
                    if (bd.altaDepartamento(d)) {
                        System.out.println("Se ha dado de alta el Departamento:");
                        System.out.println(d);
                    }
                }
                if (!bd.comprobarEspecialidadCuerpo(nuevoT)) {

                    System.out.println("La Especialidad de este Trabajador, NO EXISTE.");
                    System.out.println("Procedemos a crearla");
                    System.out.println("Nombre de la nueva Especialidad es: ");
                    nombreEspecialidad = entrada.nextLine();
                    System.out.println("La denominación de la nueva Especialidad:  ");
                    especialidad = entrada.nextLine();
                    CuerpoEspecialidad e = new CuerpoEspecialidad(t.getCuerpo(), nombreEspecialidad, especialidad, t.getCodEspecialidad());
                    if (bd.altaEspecialidad(e)) {
                        System.out.println("Se ha dado de alta la Especialidad:");
                        System.out.println(e);
                    }
                }
            }
            bd.modificarTrabajador(nuevoT);

        } else {
            System.out.println("No se modificara este trabajador.");
        }

    }

    private static void modificarDepartamento() {
        int numDepto;
        String nombre, ubicacion, confirmacion;
        Departamento d, nuevoD;

        System.out.println("Procedemos a modificar datos de un Departamento.");
        System.out.println("Indique el numero de Departamento que desea modificar: ");
        numDepto = numeros.nextInt();

        d = bd.buscarDepartamento(numDepto);
        nombre = d.getNombre();
        ubicacion = d.getUbicacion();

        System.out.println("Vamos a modificar los datos del siguiente Departamento:");
        System.out.println(d);
        System.out.println("¿Esta de acuerdo?(presione 'S' si quiere continuar con la modificación)");
        confirmacion = entrada.nextLine();
        if (confirmacion.equalsIgnoreCase("S")) {

            System.out.println("¿Desea modificar el nombre? (presione 'S' para modificar)");
            confirmacion = entrada.nextLine();
            if (confirmacion.equalsIgnoreCase("S")) {
                System.out.println("Escriba el nuveo nombre: ");
                nombre = entrada.nextLine();
            }

            System.out.println("¿Desea modificar la ubicación? (presione 'S' para modificar)");
            confirmacion = entrada.nextLine();
            if (confirmacion.equalsIgnoreCase("S")) {
                System.out.println("Escriba la nueva ubicacion: ");
                ubicacion = entrada.nextLine();
            }

            nuevoD = new Departamento(numDepto, nombre, ubicacion);
            bd.modificarDepartmento(nuevoD);

        } else {
            System.out.println("Se ha decidido no modificar este Departamento.");
        }

    }

    private static void modificarEspecialidad() {
        String cuerpo, denominacionCuerpo, especialidad, codEspecialidad, confirmacion;
        CuerpoEspecialidad cE, cNuevo;

        System.out.println("Procedemos a modificar datos de una Especialidad.");
        System.out.println("Indique el cuerpo de la Especialidad que quiere modificar: ");
        cuerpo = entrada.nextLine();
        System.out.println("Indique el Código de la Especialidad que quiere modificar: ");
        codEspecialidad = entrada.nextLine();

        cE = bd.buscarCuerpoEspecialidad(cuerpo, codEspecialidad);
        denominacionCuerpo = cE.getDenominacionCuerpo();
        especialidad = cE.getEspecialidad();

        System.out.println("Vamos a modificar los datos de la siguiente Especialidad:");
        System.out.println(cE);
        System.out.println("¿Esta de acuerdo?(presione 'S' si quiere continuar con la modificación)");
        confirmacion = entrada.nextLine();
        if (confirmacion.equalsIgnoreCase("S")) {

            System.out.println("¿Desea modificar la Especialidad? (presione 'S' para modificar)");
            confirmacion = entrada.nextLine();
            if (confirmacion.equalsIgnoreCase("S")) {
                System.out.println("Escriba la nueva Especialidad: ");
                especialidad = entrada.nextLine();
            }
            System.out.println("¿Desea modificar la Denominación? (presione 'S' para modificar)");
            confirmacion = entrada.nextLine();
            if (confirmacion.equalsIgnoreCase("S")) {
                System.out.println("Escriba la nueva Denominación: ");
                denominacionCuerpo = entrada.nextLine();
            }
            cNuevo = new CuerpoEspecialidad(cuerpo, denominacionCuerpo, especialidad, codEspecialidad);
            bd.modificarEspecialidad(cE);

        } else {
            System.out.println("Se ha decidido no modificar la Especialidad. ");
        }
    }

    private static void consultaTrabajadorSalario() {
        System.out.println("Listado de los trabajadores");
        System.out.println("Ordenados por el salario de forma descendente");
        System.out.println("*********************************************");
        System.out.println(bd.consultaTrabajadorSalario());
    }

    private static void consultaTrabajadorDepartamento() {
        System.out.println("Listado de los trabajadores");
        System.out.println("Ordenados por departamentos");
        System.out.println("*********************************************");
        System.out.println(bd.consultaTrabajadorDepartamento());
    }

    private static void consultaTrabajadorEspecialidad() {
        System.out.println("Listado de los trabajadores");
        System.out.println("Ordenados por especialidad");
        System.out.println("*********************************************");
        System.out.println(bd.consultaTrabajadorEspecialidad());

    }

}
