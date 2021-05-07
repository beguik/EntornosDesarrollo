package hospital;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Belén
 */
public class CuerpoEspecialidad {

    private String cuerpo;
    private String denominacionCuerpo;
    private String especialidad;
    private String codEspecialidad;

    public CuerpoEspecialidad() {
    }

    public CuerpoEspecialidad(String cuerpo, String denominacionCuerpo, String especialidad, String codEspecialidad) {

        this.cuerpo = cuerpo;
        this.denominacionCuerpo = denominacionCuerpo;
        this.especialidad = especialidad;
        this.codEspecialidad = codEspecialidad;

    }

    public CuerpoEspecialidad(String cuerpo, String especialidad) {
        this.cuerpo = cuerpo;
        this.especialidad = especialidad;
    }

    public String getCuerpo() {
        return cuerpo;
    }

    public void setCuerpo(String cuerpo) {
        this.cuerpo = cuerpo;
    }

    public String getDenominacionCuerpo() {
        return denominacionCuerpo;
    }

    public void setDenominacionCuerpo(String denominacionCuerpo) {
        this.denominacionCuerpo = denominacionCuerpo;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getCodEspecialidad() {
        return codEspecialidad;
    }

    public void setCodEspecialidad(String codEspecialidad) {
        this.codEspecialidad = codEspecialidad;
    }

    @Override
    public String toString() {
        return especialidad
                + "\n -----------------------"
                + "\n Código: " + codEspecialidad
                + "\n Cuerpo: " + cuerpo
                + "\n Denominación: " + denominacionCuerpo;

    }

}
