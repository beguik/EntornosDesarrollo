/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package periodico;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Date;
import java.util.Iterator;

/**
 *
 * @author Belén
 */
public class Periodico {

    /**
     * @param args the command line arguments
     */
    private static List<Noticia> noticias = new ArrayList<Noticia>();
    public static Scanner entrada = new Scanner(System.in);
    private static File miFichero = new File("datos.dat");

    public static void main(String[] args) {

        int opcion = 0;
        boolean repetir = true;

        if (!miFichero.exists()) {

            //El metodo CargarDatos lo hemos creado para hacer una primera carga de datos al sistema 
            // y poder trabajar con ellos ya que la introduccion manual es más tediosa.
            cargarDatos();

            do {
                pintarMenu();

                while (repetir) {
                    try {
                        System.out.println("Introduzca una de las siguientes opciones: ");
                        opcion = entrada.nextInt();
                        repetir = false;
                    } catch (InputMismatchException e) {
                        System.out.println("\u001B[33m - NO SE HA INTRODUCIDO UNA OPCIÓN VALIDA -\u001B[30m");
                    }
                    entrada = new Scanner(System.in);
                }
                repetir = true;
                tratarOpcionMenu(opcion);
            } while (opcion != 99);

            System.exit(0);

        } else {

            deserializa();
            Noticia.id = controlarId();

            do {
                pintarMenu();

                while (repetir) {
                    try {
                        System.out.println("Introduce una de las siguientes opciones: ");
                        opcion = entrada.nextInt();
                        repetir = false;
                    } catch (InputMismatchException e) {
                        System.out.println("\u001B[33m - NO SE HA INTRODUCIDO UNA OPCIÓN VALIDA -\u001B[30m");
                    }
                    entrada = new Scanner(System.in);
                }
                repetir = true;
                tratarOpcionMenu(opcion);
            } while (opcion != 99);

            System.exit(0);

        }

    }

    private static void pintarMenu() {

        System.out.println("\u001B[32m");
        System.out.println("\u001B[32m*************************************************");
        System.out.println("\u001B[32m********************  MENU  *********************");
        System.out.println("\u001B[32m*************************************************");
        System.out.println("");
        System.out.println("\u001B[32m***** Elija una de las siguientes opciones ******");
        System.out.println("");
        System.out.println("\u001B[32m--- (1)  Dar de Alta una nueva Noticia -----------");
        System.out.println("\u001B[32m--- (2)  Publicar una Noticia --------------------");
        System.out.println("\u001B[32m--- (3)  Dar de baja una Noticia -----------------");
        System.out.println("\u001B[32m--- (4)  Buscar Noticia por su autor -------------");
        System.out.println("\u001B[32m--- (5)  Ordenar Noticias por fecha --------------");
        System.out.println("\u001B[32m--- (6)  Mostrar Noticias antiguas en Edicion ---");
        System.out.println("\u001B[32m--- (7)  Listar todas las noticias --------------");
        System.out.println("\u001B[32m--- (99) Salir de la aplicacion -----------------");
        System.out.println("");
        System.out.println("\u001B[32m*************************************************\u001B[30m");

    }

    private static void tratarOpcionMenu(int opcion) {

        switch (opcion) {

            case 1:
                altaNoticia();
                break;
            case 2:
                publicarNoticia();
                break;
            case 3:
                bajaNoticia();
                break;
            case 4:
                buscarNoticia();
                break;
            case 5:
                ordenarNoticia();
                break;
            case 6:
                mostrarNoticiasEdicion();
                break;
            case 7:
                listarNoticias();
                break;
            case 99:
                serializar();
                break;
            default:
                System.out.println("No ha introducido una opcion correcta vuelva a intentarlo.");
                break;

        }
    }

    private static void deserializa() {

        try {

            FileInputStream miFis = null;
            ObjectInputStream miOis = null;
            try {

                miFis = new FileInputStream(miFichero);
                miOis = new ObjectInputStream(miFis);

                noticias = (List<Noticia>) miOis.readObject();

            } catch (ClassNotFoundException ex) {
                System.out.println("\u001B[33m - CLASE NO ENCONTRADA - \u001B[30m");
            } catch (IOException ex) {
                Logger.getLogger(Periodico.class.getName()).log(Level.SEVERE, null, ex);
            }
            miOis.close();
            miFis.close();

        } catch (IOException ex) {
            Logger.getLogger(Periodico.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private static int controlarId() {

        int aux = 0;

        for (int i = 0; i < noticias.size(); i++) {

            if (noticias.get(i) != null && noticias.get(i).getIDNOTICIA() > aux) {
                aux = noticias.get(i).getIDNOTICIA();
            }
        }
        return aux + 1;
    }

    private static void altaNoticia() {
        String titulo, sinopsis, cuerpo, fecha, autor, pregunta;
        Date fechaF = null;
        Noticia n;
        boolean seguir = true, repetir = true, flag = true;

        System.out.println("\u001B[34m- VAMOS A DAR DE ALTA UNA NOTICIA -\u001B[30m");
        System.out.println("Introduzca el Título de la Noticia: ");
        titulo = entrada.nextLine();
        System.out.println("Introduzca un pequeño resumen: ");
        sinopsis = entrada.nextLine();
        System.out.println("Introduzca el cuerpo de la noticia: ");
        cuerpo = entrada.nextLine();
        while (repetir) {
            try {
                do {

                    System.out.println("Introduzca la fecha: El formato debe ser \u001B[31mAAAA-MM-DD: \u001B[30m");
                    fecha = entrada.nextLine();
                    fechaF = formatoFecha(fecha);
                    repetir = false;

                } while (fechaF == null);
            } catch (InputMismatchException | NumberFormatException |ArrayIndexOutOfBoundsException e) {
                System.out.println("\u001B[33m- NO SE HA INTRODUCIDO UNA OPCION VALIDA -\u001B[30m");
            }
            entrada = new Scanner(System.in);
        }
        repetir = true;

        System.out.println("Introduzca el nombre del autor: ");
        autor = entrada.nextLine();

        noticias.add(n = new Noticia(titulo, sinopsis, cuerpo, fechaF, autor));
        do {
            System.out.println("¿Desea introducir palabras clave a la noticia?: \u001B[31m(S/N)\u001B[30m");
            pregunta = entrada.nextLine();

            if (pregunta.equalsIgnoreCase("S")) {
                annadirPalabras(n);
                //los metodos añadirPalabras y relacionarNoticias tienen un bucle que permite 
                //introducir varios valores.
                flag = false;

            } else if (pregunta.equalsIgnoreCase("N")) {
                System.out.println("\u001B[33m- NO SE AÑADIRAN PALABRAS CLAVE -\u001B[30m");
                flag = false;

            } else if (!pregunta.equalsIgnoreCase("S") && !pregunta.equalsIgnoreCase("N")) {
                System.out.println("\u001B[33m- NO HA INDICADO UNA OPCION CORRECTA -\u001B[30m");
                flag = true;
            }

        } while (flag);

        do {

            System.out.println("¿Desea relacionar esta noticia con otras? : \u001B[31m(S/N)\u001B[30m");
            pregunta = entrada.nextLine();

            if (pregunta.equalsIgnoreCase("S")) {
                relacionarNoticias(n);
                flag = false;
            } else if (pregunta.equalsIgnoreCase("N")) {
                System.out.println("\u001B[33m- NO SE RELACCIONARÁ ESTA NOTICIA CON NINGUNA OTRA -\u001B[30m");
                flag = false;
            } else if (!pregunta.equalsIgnoreCase("S") && !pregunta.equalsIgnoreCase("N")) {
                System.out.println("\u001B[33m- NO HA INDICADO UNA OPCION CORRECTA -\u001B[30m");
                flag = true;
            }
        } while (flag);
       
        System.out.println("\u001B[34m- NOTICIA AÑADIDA CORRECTAMENTE -\u001B[30m");
    }

    private static Date formatoFecha(String fecha) {
        Date fechaDate = null;
        int anio, dia, mes;
        boolean form = false;

        String[] palabras = fecha.split("-");
        anio = Integer.parseInt(palabras[0]);
        mes = Integer.parseInt(palabras[1]);
        dia = Integer.parseInt(palabras[2]);

        if (anio >= 1900 && anio <= 2900) {
            if (mes > 0 && mes <= 12) {

                if (mes == 2 && dia > 0 && dia <= 28) {
                    form = true;
                }
                if ((mes == 4 || mes == 6 || mes == 9 || mes == 11) && dia > 0 && dia <= 30) {
                    form = true;
                }
                if (dia > 0 && dia <= 31) {
                    form = true;
                }
            }
        }
        if (form) {
            SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");

            try {
                fechaDate = formato.parse(fecha);
            } catch (ParseException ex) {

                System.out.println("\u001B[33m - EL FORMATO INTRODUCIDO NO ES CORRECTO - \u001B[30m");
            }
        } else {
            System.out.println("\u001B[33m - EL FORMATO INTRODUCIDO NO ES CORRECTO, INTENTELO DE NUEVO -\u001B[30m");
        }

        return fechaDate;
    }

    private static void publicarNoticia() {
        int id = 0;
        String pregunta;
        boolean seguir = true, publicar = true, encontrada = false, flag = true;
        Noticia n;

        while (seguir) {
            try {
                System.out.println("Introduzca la id de la noticia que quiere publicar: ");
                id = entrada.nextInt();
                seguir = false;
                 

            } catch (InputMismatchException e) {
                System.out.println("\u001B[34m - EL FORMATO INTRODUCIDO NO ES CORRECTO, INTENTELO DE NUEVO -\u001B[30m");
                entrada = new Scanner(System.in);
            }

        }

        seguir = true;
        entrada = new Scanner(System.in);

        Iterator<Noticia> it = noticias.iterator();

        while (publicar && it.hasNext()) {
            n = (Noticia) it.next();

            if (n.getIDNOTICIA() == id) {
                encontrada = true;
                do {
                    System.out.println("¿Desea introducir palabras clave a la noticia?: \u001B[31m(S/N)\u001B[30m");
                    pregunta = entrada.nextLine();
                    if (pregunta.equalsIgnoreCase("S")) {
                        annadirPalabras(n);
                        flag = false;
                    } else if (pregunta.equalsIgnoreCase("N")) {
                        System.out.println("\u001B[33m- NO SE AÑADIRAN MAS PALABRAS CLAVES -\u001B[30m");
                        flag = false;
                    } else if (!pregunta.equalsIgnoreCase("S") && !pregunta.equalsIgnoreCase("N")) {
                        System.out.println("\u001B[33m- NO INTRODUJO UN VALOR ADECUADO -\u001B[30m");
                        flag = true;
                    }
                } while (flag);

                do {
                    System.out.println("¿Desea relacionar esta noticia con otras? : \u001B[31m(S/N)\u001B[30m");
                    pregunta = entrada.nextLine();

                    if (pregunta.equalsIgnoreCase("S")) {
                        relacionarNoticias(n);
                        flag = false;
                    } else if (pregunta.equalsIgnoreCase("N")) {
                        System.out.println("\u001B[33m - NO SE RELACCIONARA CON MÁS NOTICIAS -\u001B[30m");
                        flag = false;
                    } else if (!pregunta.equalsIgnoreCase("S") && !pregunta.equalsIgnoreCase("N")) {
                        System.out.println("\u001B[33m- NO INTRODUJO UN VALOR ADECUADO -\u001B[30m");
                        flag = true;
                    }
                } while (flag);

                n.setEstado("P");
                publicar = false;
                System.out.println("\u001B[34m- SE HA PUBLICADO LA NOTICIA -\u001B[30m");
            }

        }
        if (!encontrada) {
            System.out.println("\u001B[33m - LA ID NO CORRESPONDE CON NINGUNA NOTICIA REGISTRADA -\u001B[30m");
        }

    }

    private static void annadirPalabras(Noticia n) {
        String palabra, palabraFormateada;
        boolean salir = true;

        do {
            System.out.println("Introduzca una palabra clave (para terminar escriba \u001B[31msalir\u001B[30m): ");
            palabra = entrada.nextLine();
            palabraFormateada = palabra.toUpperCase();

            if (palabraFormateada.equals("SALIR")) {
                salir = false;
                System.out.println("\u001B[33m- NO SE INSERTARÁN MÁS PALABRAS -\u001B[30m");
            } else {
                n.insertarPalabra(palabraFormateada);
                System.out.println("\u001B[34m- PALABRA INSERTADA -\u001B[30m");
            }
        } while (salir);

    }

    private static void relacionarNoticias(Noticia n) {
        int id = 0;
        boolean salir = true, repetir = true, flag = false;
        Noticia control = null;

        do {
            while (repetir) {
                try {
                    System.out.println("Introduzca el id de la noticia con la que quiere relacionar esta noticia (para terminar escriba un \u001B[31mnumero negativo):\u001B[30m");
                    id = entrada.nextInt();
                    repetir = false;
                } catch (InputMismatchException e) {
                    System.out.println("\u001B[33m - EL VALOR INTRODUCIDO NO ES CORRECTO, VUELVA A INTENTARLO -\u001B[30m");
                }
                entrada = new Scanner(System.in);
            }
            repetir = true;

            if (id < 0) {
                System.out.println("\u001B[33m- NO SE INTRODUCIRA NINGUN CÓDIGO MÁS -\u001B[30m");
                salir = false;
            } else {

                /*este codigo en principio podría reducirse a solo incorporar objetos
                pero entiendo que debería de comprobarse que 
                el codigo de la noticia es valido o no*/
                Iterator<Noticia> it = noticias.iterator();

                while (it.hasNext()) {
                    control = (Noticia) it.next();

                    if (control.getIDNOTICIA() == id) {
                        n.insertarId(id);
                        System.out.println("\u001B[34m- CÓDIGO INSERTADO CORRECTAMENTE -\u001B[30m");
                        flag = true;
                    }

                }
                if (!flag) {
                    System.out.println("\u001B[33m- EL CÓDIGO INTRODUCIDO NO CORRESPONDEA NINGUNA NOTICIA -\u001B[30m");
                }

            }
        } while (salir);
    }

    private static void bajaNoticia() {
        int id = 0;
        boolean seguir = true, baja = true, flag = false;
        Noticia n = null;

        while (seguir) {
            try {
                System.out.println("Introduzca la id de la noticia que quiere dar de baja: ");
                id = entrada.nextInt();

            } catch (InputMismatchException e) {
                System.out.println("\u001B[33m- EL VALOR INTRODUCIDO NO ES CORRECTO, VUELVA A INTENTARLO -\u001B[30m");
            }
            seguir = false;
        }
        entrada = new Scanner(System.in);

        Iterator<Noticia> it = noticias.iterator();

        while (baja && it.hasNext()) {
            n = (Noticia) it.next();

            if (n.getIDNOTICIA() == id) {
                noticias.remove(n);
                baja = false;
                flag = true;
                System.out.println(" \u001B[34m- SE HA ELIMINADO LA NOTICIA CUYA ID ERA: " + id + "\u001B[30m");
            }
        }
        if (!flag) {
            System.out.println("\u001B[33m- NO SE HA ENCONTRADO LA NOTICIA CON LA ID: " + id + "\u001B[30m");
        }
    }

    private static void buscarNoticia() {
        String autor;
        boolean encontrado = false;
        Noticia n = null;

        System.out.println("Introduzca el autor de la noticia que quiere buscar: ");
        autor = entrada.nextLine();

        entrada = new Scanner(System.in);

        Iterator<Noticia> it = noticias.iterator();

        while (it.hasNext()) {
            n = (Noticia) it.next();

            if (n.getAutor().equalsIgnoreCase(autor)) {
                System.out.println(n);
                encontrado = true;
            }
        }
        if (!encontrado) {
            System.out.println("\u001B[33m- NO SE HA ENCONTRADO NINGUNA NOTICIA DE ESE AUTOR -\u001B[30m");
        }
    }

    private static void ordenarNoticia() {

        // como en el ejercicio solo nos pide que mostremos las noticias ordenadas 
        //de forma ascendente  y no que ordenemos el array procedo ha realizar una copia del 
        //array de noticias y ordenar esa copia de forma que no modificamos el array original.
        List<Noticia> nFecha = new ArrayList<>(noticias);
        System.out.println("\u001B[34m------- NOTICIAS ORDENADAS POR FECHA --------\u001B[30m");
        Collections.sort(nFecha);

        for (Noticia n : nFecha) {
            System.out.println(n);
        }

    }

    private static void mostrarNoticiasEdicion() {

        System.out.println("\u001B[34m--------   LISTADO DE NOTICIAS EN EDICIÓN  ------- \u001B[30m");
        for (Noticia n : noticias) {
            if (n.getEstado().equalsIgnoreCase("E") && comprobarF(n)) {
                System.out.println(n);
            }
        }
    }

    private static boolean comprobarF(Noticia n) {
        boolean flag = false;
        Date hoy = new Date();
        int aux;

        aux = (int) (((hoy.getTime() - n.getFecha().getTime()) / 86400000));
        //el metodo getTime devuelve los milisegunos desde 1970
        if (aux > 10) {
            flag = true;
        }
        return flag;
    }

    private static void listarNoticias() {

        System.out.println("\u001B[34m--------   LISTADO DE NOTICIAS   ------- \u001B[30m");
        for (Noticia n : noticias) {
            System.out.println(n);
        }
    }

    private static void serializar() {

        try {
            FileOutputStream miFis = null;
            ObjectOutputStream miOis = null;

            miFis = new FileOutputStream(miFichero);
            miOis = new ObjectOutputStream(miFis);

            miOis.writeObject(noticias);

            miOis.close();
            miFis.close();
            System.out.println("\u001B[34m- DATOS GUARDADOS -\u001B[30m");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Periodico.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Periodico.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private static void cargarDatos() {
        String f1 = "2020-01-01", f2 = "2016-02-01", f3 = "2020-02-15", f4 = "2019-03-20", f5 = "2018-03-12";

        Noticia n1 = new Noticia("Cine de Verano",
                "Visita por los cines más emblematicos",
                "Como es tradicional todos los años, a finales de Junio "
                + "\ncomienza la temporada de Cines de Verano, "
                + "\nofreciendo una cartelera variada, en un entorno ideal, al aire libre "
                + "\ny bajo la luz de la luna o las estrellas, para pasarlo en pareja o en familia.",
                formatoFecha(f1), "Arte en Cordoba");

        Noticia n2 = new Noticia("Robo en el Museo",
                "Aprovechan el cierre de un museo",
                "El mismo día en que Van Gogh hubiera celebrado su cumpleaños "
                + "\n(nació el 30 de marzo de 1853), "
                + "\nlos ladrones aprovecharon el cierre de museos impuesto por el coronavirus "
                + "\npara hacerse con Jardín primaveral.",
                formatoFecha(f2), "La Vanguardia");

        Noticia n3 = new Noticia("Opera en Casa",
                "La opera de París podrá verse Online",
                "Los amantes de la ópera están de enhorabuena. "
                + "\nLa Ópera Nacional de París ha tomado la decisión, en colaboración con France Télévisions, "
                + "\nde emitir de forma gratuita en línea en todo el mundo "
                + "\nvarias de sus producciones más emblemáticas como 'El lago de los cines' o 'El barbero de Sevilla'.",
                formatoFecha(f3), "La Informacion");

        Noticia n4 = new Noticia("Paren el mundo que nos Bajamos",
                "Mafalda será la protagonista del Salón del Comic.",
                "Mafalda, esa Greta Thunberg avant la lettre "
                + "\n(y con más sentido del humor) creada por el dibujante Quino, "
                + "\nserá protagonista en la próxima edición, "
                + "\nla 38ª, del Salón del Cómic de Barcelona (oficialmente Cómic Barcelona). ",
                formatoFecha(f4), "El Pais");

        Noticia n5 = new Noticia("Coronavirus en el mundo",
                "Nueva York duerme y la Mágia de Disney se apaga",
                "El Covid-19 ha ido apagando la actividad en los rincones más icónicos del mundo "
                + "\ncon el fin de frenar la propagación de un virus con presencia en al menos 160 países "
                + "\ny que registra más de 175.000 casos positivos, "
                + "\nconvirtiéndose en una emergencia sanitaria que la Organización Mundial de Salud (OMS) "
                + "\nya ha catalogado como pandemia",
                formatoFecha(f5), "Expreso");

        noticias.add(n1);
        noticias.add(n2);
        noticias.add(n3);
        noticias.add(n4);
        noticias.add(n5);

    }

}
