/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package periodico;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author Belén
 */
public class Noticia implements Serializable, Comparable {

    private static final long serialVersionUID = 1L;

    public static int id = 1;
    //generamos un contador estatico para que el codigo no se repita
    //lo inicializamos a 1 para que empieze en 1.
    private final int IDNOTICIA;
    //ponemos la variable a final para que una vez que se cree, quede como constante y no pueda cambiarse.
    //por tanto escribimos el nombre en mayúscula y no tendrá metodo setter para modificarla.
    private String titulo;
    private String sinopsis;
    private String cuerpo;
    private Date fecha;
    private String autor;
    private String estado = "E";
    //vamos a marcar por defecto el estado en edicion
    private Set palabrasClave = new TreeSet();
    private Set listasId = new TreeSet();

    public Noticia() {
        IDNOTICIA = id;
        id++;
    }

    public Noticia(String titulo, String sinopsis, String cuerpo, Date fecha, String autor) {
        this.IDNOTICIA = id;
        id++;
        this.titulo = formatoTitulo(titulo);
        this.sinopsis = sinopsis;
        this.cuerpo = cuerpo;
        this.fecha = fecha;
        this.autor = autor;
    }

    public Noticia(String titulo, Date fecha, String sinopsis, String cuerpo, String autor, String estado) {
        this.IDNOTICIA = id;
        id++;
        this.titulo = formatoTitulo(titulo);
        this.fecha = fecha;
        this.sinopsis = sinopsis;
        this.cuerpo = cuerpo;
        this.autor = autor;
        this.estado = comprobarEstado(estado);
        // Este metodo lo cree cuando entendi en el enunciado que la noticia al crearla
        //podría crearse con el estado E o con el estado P como en principio hemos dicho 
        //que se crea en E por defecto y ya se pasa a P con el menu del main no sería necesario 
        //pero como dejo este constructor abierto para poder crear noticias con todos sus atributos
        //dejo el metodo. 
    }

    public static int getId() {
        return id;
    }
// dejo el setId para poder recuperar y actualizar el id static cuando abrimo la aplicacion
// ya que al cerrar la aplicacion se borra el static y al abrirse empezaría por cero.

    public static void setId(int id) {
        Noticia.id = id;
    }

    public int getIDNOTICIA() {
        return IDNOTICIA;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    public String getCuerpo() {
        return cuerpo;
    }

    public void setCuerpo(String cuerpo) {
        this.cuerpo = cuerpo;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Set getPalabrasClave() {
        return palabrasClave;
    }

    public void setPalabrasClave(Set palabrasClave) {
        this.palabrasClave = palabrasClave;
    }

    public Set getListasId() {
        return listasId;
    }

    public void setListasId(Set listasId) {
        this.listasId = listasId;
    }

    @Override
    public String toString() {
        return "\n"
                + "\n\033[31m" + titulo
                + "\u001B[0m\n" + fecha
                + "\n\033[31m---------------------------"
                + "\n" + sinopsis
                + "\n\033[31m***************************"
                + "\n" + cuerpo
                + "\n\033[31m***************************"
                + "\n" + autor + " , " + IDNOTICIA + " / " + estado
                + "\nPalabras Clave: " + palabrasClave
                + "\nNoticias Relaccionadas: " + listasId
                + "\n\033[31m---------------------------";

    }

    // estos dos metodos nos van a permitir insertar en el TreeSet desde el main
    public void insertarPalabra(String x) {
        palabrasClave.add(x);
    }

    public void insertarId(Integer i) {
        listasId.add(i);
    }

    private String comprobarEstado(String x) {
        String control;

        if (x.toUpperCase().substring(1).equals("P")) {
            control = "P";
        } else {
            control = "E";
        }
        return control;
    }

    private String formatoTitulo(String t) {
        String aux;
        aux = t.toUpperCase();

        return aux;
    }

    @Override
    public int compareTo(Object o) {
        Noticia n;
        n = (Noticia) o;

        return fecha.compareTo(n.fecha);

//hago una llamada al metodo compareTo de la clase Date
    }

}
