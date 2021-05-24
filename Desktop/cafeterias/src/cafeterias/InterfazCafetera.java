/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cafeterias;

/**
 *
 * @author Belén Guijarro Cubero

 */
public interface InterfazCafetera {

    void agregarCafe(int cantidad);

    /**
     * @return the cantidadActual
     */
    int getCantidadActual();

    /**
     * @return the capacidadMaxima
     */
    int getCapacidadMaxima();

    int get_capacidadMaxima();

    void llenarCafetera();

    void servirTaza(int tamanoTaza);

    /**
     * @param cantidadActual the cantidadActual to set
     */
    void setCantidadActual(int cantidadActual);

    /**
     * @param capacidadMaxima the capacidadMaxima to set
     */
    void setCapacidadMaxima(int capacidadMaxima);

    void set_capacidadMaxima(int _capacidadMaxima);

    void vaciarCafetera();
    
}
