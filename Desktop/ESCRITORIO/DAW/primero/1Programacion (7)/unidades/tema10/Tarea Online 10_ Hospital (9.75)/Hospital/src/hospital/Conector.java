/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hospital;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Belén
 */
public class Conector {

    private String bd = "semiprog";
    private String login = "semiprog";
    private String password = "semiprog";
    private String url = "jdbc:mysql://194.53.148.103/" + bd + "?serverTimezone=" + TimeZone.getDefault().getID();
    private String prefijoTabla = "beguicub433";
    private Connection conn;

    /* para local seria
    
     private String bd = "test";
    private String login ="root";
    private String password = "";
    private String url ="jdbc:mysql://localhost:3306" +bd+ "?serverTimezone="+TimeZone.getDefault().getID();
       
     */
    public Conector() {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            try {
                conn = DriverManager.getConnection(url, login, password);
                if (conn != null) {
                    System.out.println("Conexion a la base de datos " + bd + " lista");

                }

            } catch (SQLException ex) {
                Logger.getLogger(Conector.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Conector.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public Connection getConn() {
        return conn;
    }

    public boolean comprobarDepartamento(Trabajador t) {

        boolean flag = false;
        int n;

        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT COUNT(NOMBRE) FROM " + prefijoTabla + "_Departamento WHERE numDepto = '" + t.getNumDepartamento() + "'");

            if (rs.next()) {
                n = rs.getInt(1);
                if (n > 0) {
                    flag = true;
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(Conector.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        return flag;

    }

    public boolean comprobarEspecialidadCuerpo(Trabajador t) {

        boolean flag = false;
        int n;

        try {

            Statement stc = conn.createStatement();
            ResultSet rsc = stc.executeQuery("SELECT COUNT(*) FROM " + prefijoTabla + "_EspecialidadCuerpo "
                    + "WHERE (cuerpo = '" + t.getCuerpo() + "') AND (codEspecialidad = '" + t.getCodEspecialidad() + "')");
            if (rsc.next()) {
                n = rsc.getInt(1);
                if (n > 0) {
                    flag = true;

                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Conector.class.getName()).log(Level.SEVERE, null, ex);
        }
        return flag;
    }

    public void altaTrabajador(Trabajador t) {

        try {
            Statement st = conn.createStatement();

            st = conn.createStatement();
            int i = st.executeUpdate("INSERT INTO " + prefijoTabla + "_Trabajador "
                    + "(IDENTIFICADOR, NOMBRETRABAJADOR, SALARIO, NUMDEPARTAMENTO, CUERPO, CODESPECIALIDAD)"
                    + " VALUES('" + t.getIdentificador() + "','"
                    + t.getNombreTrabajador() + "','"
                    + t.getSalario() + "','"
                    + t.getNumDepartamento() + "','"
                    + t.getCuerpo() + "','"
                    + t.getCodEspecialidad() + "')");
            System.out.println("Se ha insertado " + i + " filas.");
            st.close();

        } catch (SQLException ex) {
            Logger.getLogger(Conector.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

    }

    public boolean altaDepartamento(Departamento d) {
        boolean exito = false;
        int i;
        try {
            Statement st = conn.createStatement();
            i = st.executeUpdate("INSERT INTO " + prefijoTabla + "_Departamento VALUES('" + d.getNombre() + "','" + d.getUbicacion() + "','" + d.getNumDepto() + "')");
            System.out.println("Se ha insertado " + 1 + " fila.");
            exito = true;
        } catch (SQLException ex) {
            Logger.getLogger(Conector.class.getName()).log(Level.SEVERE, null, ex);
        }
        return exito;

    }

    public boolean altaEspecialidad(CuerpoEspecialidad e) {

        boolean exito = false;
        try {
            Statement st = conn.createStatement();
            st.executeUpdate("INSERT INTO " + prefijoTabla + "_EspecialidadCuerpo VALUES('" + e.getCuerpo() + "','" + e.getDenominacionCuerpo() + "','" + e.getEspecialidad() + "','" + e.getCodEspecialidad() + "')");
            exito = true;
        } catch (SQLException ex) {
            Logger.getLogger(Conector.class.getName()).log(Level.SEVERE, null, ex);
        }
        return exito;

    }

    public boolean bajaTrabajador(String nombre) {

        boolean exito = false;
        int i;
        try {

            Statement st = conn.createStatement();
            i = st.executeUpdate("DELETE FROM " + prefijoTabla + "_Trabajador WHERE  nombreTrabajador = '" + nombre + "'");
            System.out.println("Se ha eliminado " + i + " filas.");
            exito = true;
        } catch (SQLException ex) {
            Logger.getLogger(Conector.class.getName()).log(Level.SEVERE, null, ex);
        }
        return exito;

    }

    public Trabajador buscarTrabajador(String nombreTrabajador) {
        Trabajador t = null;
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM " + prefijoTabla + "_Trabajador WHERE nombreTrabajador  ='" + nombreTrabajador + "'");
            if (rs.next()) {
                t = new Trabajador();
                t.setNombreTrabajador(nombreTrabajador);
                t.setIdentificador(rs.getInt(1));
                t.setSalario(rs.getFloat(3));
                t.setNumDepartamento(rs.getInt(4));
                t.setCuerpo(rs.getString(5));
                t.setCodEspecialidad(rs.getString(6));

            }

        } catch (SQLException ex) {
            Logger.getLogger(Conector.class.getName()).log(Level.SEVERE, null, ex);
        }
        return t;
    }

    public Departamento buscarDepartamento(int numDepartamento) {
        Departamento d = null;

        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM " + prefijoTabla + "_Departamento WHERE numDepto  ='" + numDepartamento + "'");
            if (rs.next()) {
                d = new Departamento();
                d.setNombre(rs.getString(1));
                d.setUbicacion(rs.getString(2));
                d.setNumDepto(numDepartamento);
            }

        } catch (SQLException ex) {
            Logger.getLogger(Conector.class.getName()).log(Level.SEVERE, null, ex);
        }
        return d;
    }

    public boolean trabajadoresDepeartamento(Departamento d) {
        boolean flag = false;
        int n;

        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT COUNT(nombreTrabajador) FROM " + prefijoTabla + "_Trabajador WHERE numDepartamento = '" + d.getNumDepto() + "'");

            if (rs.next()) {
                n = rs.getInt(1);
                if (n > 0) {
                    flag = true;
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(Conector.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(flag);
        return flag;
    }

    public boolean traspasarTrabajadores(Departamento d, Departamento df) {
        int i;
        boolean flag = false;

        try {
            Statement st = conn.createStatement();
            i = st.executeUpdate("UPDATE " + prefijoTabla + "_Trabajador SET numDepartamento = " + df.getNumDepto() + " WHERE numDepartamento = " + d.getNumDepto());

            System.out.println("Se han modifciado " + i + " lineas");
            st.close();
            flag = true;

        } catch (SQLException ex) {
            Logger.getLogger(Conector.class.getName()).log(Level.SEVERE, null, ex);
        }
        return flag;
    }

    public boolean bajaDepartamento(Departamento d) {
        boolean exito = false;
        int i;
        try {

            Statement st = conn.createStatement();
            i = st.executeUpdate("DELETE FROM " + prefijoTabla + "_Departamento WHERE  numDepto = '" + d.getNumDepto() + "'");
            System.out.println("Se ha eliminado " + i + " filas.");
            exito = true;
        } catch (SQLException ex) {
            Logger.getLogger(Conector.class.getName()).log(Level.SEVERE, null, ex);
        }
        return exito;

    }

    public CuerpoEspecialidad buscarCuerpoEspecialidad(String cuerpo, String codEspecialidad) {
        CuerpoEspecialidad e = null;

        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM " + prefijoTabla + "_EspecialidadCuerpo WHERE (cuerpo  ='" + cuerpo + "') AND (codEspecialidad='" + codEspecialidad + "')");
            if (rs.next()) {
                e = new CuerpoEspecialidad();
                e.setCuerpo(rs.getString(1));
                e.setDenominacionCuerpo(rs.getString(2));
                e.setEspecialidad(rs.getString(3));
                e.setCodEspecialidad(rs.getString(4));

            }

        } catch (SQLException ex) {
            Logger.getLogger(Conector.class.getName()).log(Level.SEVERE, null, ex);
        }
        return e;

    }

    public boolean trabajadoresEspecialidad(CuerpoEspecialidad e) {
        boolean flag = false;
        int n;

        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT COUNT(nombreTrabajador) FROM " + prefijoTabla + "_Trabajador WHERE (cuerpo = '" + e.getCuerpo() + "') AND (codEspecialidad= '" + e.getCodEspecialidad() + "')");

            if (rs.next()) {
                n = rs.getInt(1);
                if (n > 0) {
                    flag = true;
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(Conector.class.getName()).log(Level.SEVERE, null, ex);
        }

        return flag;
    }

    public boolean bajaCuerpoEspecialidad(CuerpoEspecialidad e) {

        boolean exito = false;
        int i;
        try {

            Statement st = conn.createStatement();
            i = st.executeUpdate("DELETE FROM " + prefijoTabla + "_EspecialidadCuerpo WHERE  (cuerpo = '" + e.getCuerpo() + "') AND (codEspecialidad= '" + e.getCodEspecialidad() + "')");
            System.out.println("Se ha eliminado " + i + " filas.");
            exito = true;
        } catch (SQLException ex) {
            Logger.getLogger(Conector.class.getName()).log(Level.SEVERE, null, ex);
        }
        return exito;

    }

    public boolean modificarTrabajador(Trabajador t) {

        int i;
        boolean flag = false;

        try {
            Statement st = conn.createStatement();
            i = st.executeUpdate("UPDATE " + prefijoTabla + "_Trabajador SET "
                    + "identificador = " + t.getIdentificador()
                    + ", salario = " + t.getSalario()
                    + ", numDepartamento= " + t.getNumDepartamento()
                    + ", cuerpo= '" + t.getCuerpo()
                    + "', codEspecialidad= '" + t.getCodEspecialidad()
                    + "' WHERE nombreTrabajador = '" + t.getNombreTrabajador() + "'");

            System.out.println("Se han modifciado " + i + " lineas");
            st.close();
            flag = true;

        } catch (SQLException ex) {
            Logger.getLogger(Conector.class.getName()).log(Level.SEVERE, null, ex);
        }
        return flag;

    }

    public boolean modificarDepartmento(Departamento d) {
        int i;
        boolean flag = false;

        try {
            Statement st = conn.createStatement();
            i = st.executeUpdate("UPDATE " + prefijoTabla + "_Departamento SET "
                    + "nombre = '" + d.getNombre()
                    + "', direccion = '" + d.getUbicacion()
                    + "' WHERE numDepto = " + d.getNumDepto());

            System.out.println("Se han modifciado " + i + " lineas");
            st.close();
            flag = true;

        } catch (SQLException ex) {
            Logger.getLogger(Conector.class.getName()).log(Level.SEVERE, null, ex);
        }
        return flag;

    }

    public boolean modificarEspecialidad(CuerpoEspecialidad c) {
        int i;
        boolean flag = false;

        try {
            Statement st = conn.createStatement();
            i = st.executeUpdate("UPDATE " + prefijoTabla + "_EspecialidadCuerpo SET "
                    + "denominacion = '" + c.getDenominacionCuerpo()
                    + "', especialidad = '" + c.getEspecialidad()
                    + "' WHERE (cuerpo = '" + c.getCuerpo() + "') and (codEspecialidad ='" + c.getCodEspecialidad() + "')");

            System.out.println("Se han modifciado " + i + " lineas");
            st.close();
            flag = true;

        } catch (SQLException ex) {
            Logger.getLogger(Conector.class.getName()).log(Level.SEVERE, null, ex);
        }
        return flag;

    }

    public ArrayList<Trabajador> consultaTrabajadorSalario() {

        ArrayList<Trabajador> listadoSalario = new ArrayList<>();

        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT identificador, nombreTrabajador, salario, numDepartamento, cuerpo, codEspecialidad"
                    + " FROM " + prefijoTabla + "_Trabajador ORDER BY salario DESC");
            while (rs.next()) {
                Trabajador t = new Trabajador();
                t.setIdentificador(rs.getInt(1));
                t.setNombreTrabajador(rs.getString(2));
                t.setSalario(rs.getFloat(3));
                t.setNumDepartamento(rs.getInt(4));
                t.setCuerpo(rs.getString(5));
                t.setCodEspecialidad(rs.getString(6));
                listadoSalario.add(t);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Conector.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listadoSalario;
    }

    public ArrayList<Trabajador> consultaTrabajadorDepartamento() {
        ArrayList<Trabajador> listadoDepartamentos = new ArrayList<>();

        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT identificador, nombreTrabajador, salario, numDepartamento, cuerpo, codEspecialidad"
                    + " FROM " + prefijoTabla + "_Trabajador ORDER BY numDepartamento");
            while (rs.next()) {
                Trabajador t = new Trabajador();
                t.setIdentificador(rs.getInt(1));
                t.setNombreTrabajador(rs.getString(2));
                t.setSalario(rs.getFloat(3));
                t.setNumDepartamento(rs.getInt(4));
                t.setCuerpo(rs.getString(5));
                t.setCodEspecialidad(rs.getString(6));
                listadoDepartamentos.add(t);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Conector.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listadoDepartamentos;

    }

    public ArrayList<Trabajador> consultaTrabajadorEspecialidad() {

        ArrayList<Trabajador> listadoDepartamentos = new ArrayList<>();

        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT identificador, nombreTrabajador, salario, numDepartamento, cuerpo, codEspecialidad"
                    + " FROM " + prefijoTabla + "_Trabajador ORDER BY cuerpo, codEspecialidad");
            while (rs.next()) {
                Trabajador t = new Trabajador();
                t.setIdentificador(rs.getInt(1));
                t.setNombreTrabajador(rs.getString(2));
                t.setSalario(rs.getFloat(3));
                t.setNumDepartamento(rs.getInt(4));
                t.setCuerpo(rs.getString(5));
                t.setCodEspecialidad(rs.getString(6));
                listadoDepartamentos.add(t);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Conector.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listadoDepartamentos;

    }

    public void desconectar() {
        conn = null;
        System.out.println("La conexion a la  base de datos " + bd + " ha terminado");
    }

}
