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
public class Trabajador {

    private int identificador;
    private String nombreTrabajador;
    private float salario;
    private int numDepartamento;
    private String cuerpo;
    private String codEspecialidad;

    public Trabajador() {
    }

    public Trabajador(int identificador, String nombreTrabajador, float salario, int numDepartamento, String cuerpo, String codEspecialidad) {
        this.identificador = identificador;
        this.nombreTrabajador = nombreTrabajador;
        this.salario = salario;
        this.numDepartamento = numDepartamento;
        this.cuerpo = cuerpo;
        this.codEspecialidad = codEspecialidad;
    }

    
    public int getIdentificador() {
        return identificador;
    }

    public void setIdentificador(int identificador) {
        this.identificador = identificador;
    }

    public String getNombreTrabajador() {
        return nombreTrabajador;
    }

    public void setNombreTrabajador(String nombreTrabajador) {
        this.nombreTrabajador = nombreTrabajador;
    }

    public float getSalario() {
        return salario;
    }

    public void setSalario(float salario) {
        this.salario = salario;
    }

    public int getNumDepartamento() {
        return numDepartamento;
    }

    public void setNumDepartamento(int numDepartamento) {
        this.numDepartamento = numDepartamento;
    }

    public String getCuerpo() {
        return cuerpo;
    }

    public void setCuerpo(String cuerpo) {
        this.cuerpo = cuerpo;
    }

    public String getCodEspecialidad() {
        return codEspecialidad;
    }

    public void setCodEspecialidad(String codEspecialidad) {
        this.codEspecialidad = codEspecialidad;
    }

    @Override
    public String toString() {
        return "\n\u001B[31m"+ nombreTrabajador
                +"\n\u001B[30mSalario:"+salario
                + "\n--------------------------------------"
                + "\nid: " + identificador
                + "\nDepartamento: " + numDepartamento
                + "\nCuerpo: " + cuerpo + " // codEspe.: " + codEspecialidad
                + "\n--------------------------------------";
    }

}
