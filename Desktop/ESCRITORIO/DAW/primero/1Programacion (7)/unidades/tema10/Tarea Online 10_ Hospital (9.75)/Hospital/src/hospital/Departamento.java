/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hospital;

/**
 *
 * @author Belén
 */
public class Departamento {

    private int numDepto;
    private String nombre;
    private String ubicacion;

    public Departamento() {
    }

    public Departamento(int numDepto, String nombre, String ubicacion) {
        this.numDepto = numDepto;
        this.nombre = nombre;
        this.ubicacion = ubicacion;
    }

    public int getNumDepto() {
        return numDepto;
    }

    public void setNumDepto(int numDepto) {
        this.numDepto = numDepto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    @Override
    public String toString() {
        return nombre
                + "\n---------------------------"
                + "\n Número Departamento: " + numDepto
                + "\n Ubicación: " + ubicacion;

    }

}
