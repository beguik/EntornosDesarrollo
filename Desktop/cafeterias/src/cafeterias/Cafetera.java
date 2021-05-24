/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cafeterias;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Belén Guijarro Cubero
 *
 */
public class Cafetera implements InterfazCafetera {

    private int capacidadMaxima;
    private int cantidadActual;

    /**
     * Constructor
     *
     * @param capacidadMaxima -capacidad Maxima de la cafetera
     * @param cantidadActual - capacidad Actual de la cafetera
     */
    public Cafetera(int capacidadMaxima, int cantidadActual) {
        this.cantidadActual = cantidadActual;
        this.capacidadMaxima = Math.max(capacidadMaxima, cantidadActual);
    }

    /**
     * Constructor
     *
     * @param capacidadMaxima -capacidad Maxima de la cafetera
     */
    public Cafetera(int capacidadMaxima) {
        this(capacidadMaxima, 0);
    }

    /**
     * Constructor por defecto
     *
     */
    public Cafetera() {
        this(1000, 0);
    }

    /**
     * @return capacidadMaxima
     *
     */
    @Override
    public int get_capacidadMaxima() {
        return getCapacidadMaxima();
    }

    /**
     * @param capacidadMaxima capacidad maxima
     *
     */
    @Override
    public void set_capacidadMaxima(int capacidadMaxima) {
        this.setCapacidadMaxima(capacidadMaxima);
    }

    /**
     * establece que la cafetera tendrá la capacidad máxima
     *
     */
    @Override
    public void llenarCafetera() {
        setCantidadActual(getCapacidadMaxima());
    }

    /**
     * calcula el tamaño de la taza y la cantidad de cafe y si no hay bastante
     * cafe lanza una excepcion 'no hay cafe'
     *
     * @param tamanoTaza tamaño de la taza
     */
    @Override
    public void servirTaza(int tamanoTaza) {
        setCantidadActual(getCantidadActual() - tamanoTaza);
        if (getCantidadActual() < 0) {
            try {
                setCantidadActual(0);

            } catch (Exception ex) {
                Logger.getLogger(Cafetera.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("No hay café");
            }

        }
    }

    /**
     * pone la cantidad actual de cafe a cero
     *
     */
    @Override
    public void vaciarCafetera() {
        setCantidadActual(0);
    }

    /**
     * calcula si la cantidad es mayor que la capacidad maxima devuelve una
     * excepcion "rebosa"
     *
     * @param cantidad cantidad que se agrega
  
     */
    @Override
    public void agregarCafe(int cantidad) {
        setCantidadActual(getCantidadActual() + cantidad);
        if (getCantidadActual() > getCapacidadMaxima()) {
            try {
                setCantidadActual(getCapacidadMaxima());

            } catch (Exception ex) {
                Logger.getLogger(Cafetera.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Rebosa");
            }

        }

    }

    /**
     * @return the capacidadMaxima
     */
    @Override
    public int getCapacidadMaxima() {
        return capacidadMaxima;
    }

    /**
     * @param capacidadMaxima the capacidadMaxima to set
     */
    @Override
    public void setCapacidadMaxima(int capacidadMaxima) {
        this.capacidadMaxima = capacidadMaxima;
    }

    /**
     * @return the cantidadActual
     */
    @Override
    public int getCantidadActual() {
        return cantidadActual;
    }

    /**
     * @param cantidadActual the cantidadActual to set
     */
    @Override
    public void setCantidadActual(int cantidadActual) {
        this.cantidadActual = cantidadActual;
    }
}
