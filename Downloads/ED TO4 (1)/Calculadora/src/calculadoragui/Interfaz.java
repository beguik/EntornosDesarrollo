package calculadoragui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 *
 * Genera el aspecto visual de una calculadora basica y su funcionalidad.
 *
 * @author Domingo J. Arribas
 * @version 0.2
 * @see <a href= "https://www.calculadoraonline.org/calculadora-basica.html">
 *        Calculadora basica
 *      </a>
 * @since 0.1
 *
 */
public class Interfaz implements ActionListener {

    JTextField campoTextoSup, campoTextoInf;
    Panel panelNorte, areaMemoria, areaOperaciones;
    JPanel panelSur, areaNumerica;
    JButton mc, mr, ms, mMas, mMenos, teclasNumero[], teclasOperacion[];
    String[] operaciones = {"R", "C", "+", "/", "-", "*", "="};
    String cadenaOperacion = "";
    float operando1, operando2, resultado, M;//variables para las teclasOperacion
    int codigoOperador; //para controlar el tipo de operaciones que se realiza
    boolean controlOp = false;//control sobre escribir un nuevo numero despues de alguna operacioncambia a true cuando se ha realizado una operacion

    /**
     * Constructor por defecto
     *
     */
    public Interfaz() {

        JFrame jfMain = new JFrame("Calculator");
        jfMain.setLayout(new BorderLayout(4, 4));

        norte();
        sur();

        jfMain.add(panelNorte, BorderLayout.NORTH);
        jfMain.add(panelSur, BorderLayout.CENTER);

        jfMain.setLocation(100, 80);
        jfMain.setResizable(false);
        jfMain.setVisible(true);
        jfMain.setSize(300, 380);
        jfMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     *
     * Crea la estructura superior de la calculadora para la introducion de datos
     *
     */
    public void norte() {

        panelNorte = new Panel(null);

        campoTextoSup = new JTextField("");
        campoTextoInf = new JTextField("0");

        campoTextoSup.setHorizontalAlignment(JTextField.RIGHT);
        campoTextoInf.setHorizontalAlignment(JTextField.RIGHT);

        //Quitar bordes a los campos de texto
        campoTextoSup.setBorder(BorderFactory.createLineBorder(Color.white));
        campoTextoInf.setBorder(BorderFactory.createLineBorder(Color.white));

        //desabilitando los campos de texto
        campoTextoSup.setEditable(false);
        campoTextoInf.setEditable(false);

        campoTextoSup.setBackground(Color.white);
        campoTextoInf.setBackground(Color.white);

        panelNorte.add(campoTextoSup);
        panelNorte.add(campoTextoInf);

        campoTextoSup.setBounds(35, 10, 200, 15);
        campoTextoInf.setBounds(35, 25, 200, 30);

        panelNorte.setSize(270, 47);
        panelNorte.setVisible(true);

    }

    /**
     *
     * Crea la estructura inferior de la calculadora incluida la botonera
     *
     */
    public void sur() {

        panelSur = new JPanel(new BorderLayout(6, 50));
        panelSur.setLayout(new BorderLayout(4, 4));

        botMem();
        botNum();
        botOpe();

        panelSur.add(areaMemoria, BorderLayout.NORTH);
        panelSur.add(areaNumerica, BorderLayout.CENTER);
        panelSur.add(areaOperaciones, BorderLayout.EAST);

        panelSur.setSize(270, 330);
    }

    /**
     *
     * Crea la parte grafica del area de botones de memoria de la calculadora
     *
     */
    public void botMem() {

        areaMemoria = new Panel(null);

        mc = new JButton("MC");
        mr = new JButton("MR");
        ms = new JButton("MS");
        mMas = new JButton("M+");
        mMenos = new JButton("M-");

        mc.setFont(new Font("Arial", Font.BOLD, 11));
        mr.setFont(new Font("Arial", Font.BOLD, 11));
        ms.setFont(new Font("Arial", Font.BOLD, 11));
        mMas.setFont(new Font("Arial", Font.BOLD, 11));
        mMenos.setFont(new Font("Arial", Font.BOLD, 11));

        mc.setMargin(new Insets(1, 1, 1, 1));
        mr.setMargin(new Insets(1, 1, 1, 1));
        ms.setMargin(new Insets(1, 1, 1, 1));
        mMas.setMargin(new Insets(1, 1, 1, 1));
        mMenos.setMargin(new Insets(1, 1, 1, 1));

        mc.setBounds(35, 0, 33, 33);
        mr.setBounds(78, 0, 33, 33);
        ms.setBounds(121, 0, 33, 33);
        mMas.setBounds(164, 0, 33, 33);
        mMenos.setBounds(207, 0, 33, 33);

        areaMemoria.add(mc);
        areaMemoria.add(mr);
        areaMemoria.add(ms);
        areaMemoria.add(mMas);
        areaMemoria.add(mMenos);

        mc.addActionListener(this);
        mr.addActionListener(this);
        ms.addActionListener(this);
        mMas.addActionListener(this);
        mMenos.addActionListener(this);

        areaMemoria.setSize(270, 45);
        areaMemoria.setVisible(true);
    }

    /**
     *
     * Crea la parte grafica del area de botones numericos de la calculadora
     *
     */
    public void botNum() {

        areaNumerica = new JPanel(null);

        int xFila3 = 121, xFila2 = 121, xFila1 = 121, yFila3 = 0, yFila2 = 43, yFila1 = 86;
        teclasNumero = new JButton[11];

        //*****************************************
        //bloque para crear los botones, añadirlos y asignar teclasNumero
        for (int i = 0; i <= 10; i++) {

            if (i <= 9) {
                teclasNumero[i] = new JButton("" + i);
                areaNumerica.add(teclasNumero[i]);
                teclasNumero[i].setMargin(new Insets(1, 1, 1, 1));
                teclasNumero[i].addActionListener(this);
            } else {
                teclasNumero[i] = new JButton(".");
                areaNumerica.add(teclasNumero[i]);
                teclasNumero[i].setMargin(new Insets(1, 1, 1, 1));
                teclasNumero[i].addActionListener(this);
            }
        }

        //***************************************************************************
        //bloque para posicionar botones
        for (int i = 10; i >= 0; i--) {

            if (i == 10) {
                teclasNumero[i].setBounds(121, 129, 35, 35);
            } else {
                if (i <= 9 && i >= 7) {
                    teclasNumero[i].setBounds(xFila3, yFila3, 35, 35);
                    xFila3 -= 43;
                } else if (i <= 6 && i >= 4) {
                    yFila3 += 43;
                    teclasNumero[i].setBounds(xFila2, yFila2, 35, 35);
                    xFila2 -= 43;
                } else if (i <= 3 && i >= 1) {
                    yFila3 += 43;
                    teclasNumero[i].setBounds(xFila1, yFila1, 35, 35);
                    xFila1 -= 43;
                } else if (i == 0) {
                    teclasNumero[i].setBounds(35, 129, 78, 35);
                }
            }
        }

        areaNumerica.setSize(170, 150);
        areaNumerica.setVisible(true);
    }

    /**
     *
     * Crea la parte grafica del area de botones de operaciones de la calculadora
     *
     */
    public void botOpe() {

        areaOperaciones = new Panel(null);

        int contador = 0, x = 0, y = 0;

        teclasOperacion = new JButton[7];

        for (int i = 0; i <= 6; i++) {
            if (contador <= 1) {

                teclasOperacion[i] = new JButton(operaciones[i]);
                areaOperaciones.add(teclasOperacion[i]);

                teclasOperacion[i].setBounds(x, y, 30, 35);

                teclasOperacion[i].setMargin(new Insets(1, 1, 1, 1));
                teclasOperacion[i].addActionListener(this);
                x += 33;
                contador++;
            } else {
                if (i == 6) {
                    x = 0;
                    y += 43;
                    teclasOperacion[i] = new JButton(operaciones[i]);
                    areaOperaciones.add(teclasOperacion[i]);

                    teclasOperacion[i].setBounds(x, y, 65, 35);

                    teclasOperacion[i].setMargin(new Insets(1, 1, 1, 1));
                    teclasOperacion[i].addActionListener(this);
                    x += 33;
                    contador++;
                } else {
                    contador = 0;
                    x = 0;
                    y += 43;
                    teclasOperacion[i] = new JButton(operaciones[i]);
                    areaOperaciones.add(teclasOperacion[i]);

                    teclasOperacion[i].setBounds(x, y, 30, 35);

                    teclasOperacion[i].setMargin(new Insets(1, 1, 1, 1));
                    teclasOperacion[i].addActionListener(this);
                    x += 33;
                    contador++;
                }
            }
        }

        areaOperaciones.setVisible(true);
        areaOperaciones.setSize(120, 200);
    }

    /**
     *
     * Devuelve si una cadena es un numero o no
     *
     * @param cadena Una cadena de operacion
     * @exception NumberFormatException Si la cadena no tiene formato de numero
     * @return <ul>
     * <li>true: la cadena es un numero</li>
     * <li>false: la cadena no es un numero</li>
     * </ul>
     *
     */
    public boolean isN(String cadena) {

        try {
            int numero = Integer.parseInt(cadena);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     *
     * Crea la funcionalidad operativa de la calculadora al pulsar los botones
     *
     * @param evento Evento generado por un boton cuando es presionado
     *
     */
    @Override
    public void actionPerformed(ActionEvent evento) {

        if (isN(evento.getActionCommand())) { //cuando se oprimen teclasNumero
            pulsarTeclasNumericas(evento);

        } else {//cuando se oprime el resto de botones        
            pulsarTeclasNoNumericas(evento);
        }
    }

    
     /**
     *
     * Genera la funcionalidad operativa al pulsar botones numericos
     *
     * @param e Evento generado por un boton cuando es presionado
     *
     */
    public void pulsarTeclasNumericas(ActionEvent e) {
        if (campoTextoSup.getText().equals("")) {
            cadenaOperacion += e.getActionCommand();
            campoTextoInf.setText(cadenaOperacion);
        } else {
            if (codigoOperador == 0) {
                if (controlOp) {
                    cadenaOperacion = "";
                    campoTextoSup.setText(campoTextoInf.getText());
                    cadenaOperacion += e.getActionCommand();
                    campoTextoInf.setText(cadenaOperacion);
                    controlOp = false;
                } else {
                    cadenaOperacion = "";
                    cadenaOperacion += campoTextoInf.getText() + e.getActionCommand();
                    campoTextoInf.setText(cadenaOperacion);
                }
            } else {
                cadenaOperacion = "";
                cadenaOperacion += campoTextoInf.getText() + e.getActionCommand();
                campoTextoInf.setText(cadenaOperacion);
            }
        }
    }

    
    /**
     *
     * Genera la funcionalidad operativa al pulsar botones no numericos
     *
     * @param e Evento generado por un boton cuando es presionado
     *
     */
    public void pulsarTeclasNoNumericas(ActionEvent e) {

        if (e.getActionCommand().equals("R")) {
            pulsarTeclaR();
        }
        if (e.getActionCommand().equals("C")) { //para reiniciar valores y limpiar pantalla
            pulsarTeclaC();
        }
        if (e.getActionCommand().equals("MC")) {//para limpiar la memoria de la calculadora
            pulsarTeclaMC();
        }
        if (e.getActionCommand().equals("MR")) {//para mostrar valor almacenado en la memoria
            pulsarTeclaMR();
        }
        if (e.getActionCommand().equals("MS")) {//guardar un valor en la memoria
            pulsarTeclaMS();
        }
        if (e.getActionCommand().equals("M+")) {//sumar valor de la pantalla con el valor de la memoria
            M += Float.parseFloat(campoTextoInf.getText());
        }
        if (e.getActionCommand().equals("M-")) {//restar valor de la pantalla con el valor de la memoria
            M -= Float.parseFloat(campoTextoInf.getText());
        }
        if (e.getActionCommand().equals(".")) {//usar el punto para los decimales
            pulsarTeclaDecimal();
        }
        if (e.getActionCommand().equals("+")) {//boton suma
            pulsarTeclaSuma(e);
        }
        if (e.getActionCommand().equals("-")) {//cuando se decide restar
            pulsarTeclaResta(e);
        }
        if (e.getActionCommand().equals("*")) {//cuando se decide multiplicar
            pulsarTeclaMult(e);
        }
        if (e.getActionCommand().equals("/")) {//cuando se decide dividir
            pulsarTeclaDiv(e);
        }
        if (e.getActionCommand().equals("=") && !campoTextoInf.getText().equals("")) {
            pulsarTeclaResultado();
        }
    }

    
    /**
     *
     * Realiza la raiz cuadrada del numero pulsado
     *
     */
    public void pulsarTeclaR() {
        campoTextoSup.setText("");
        Float a = Float.parseFloat(campoTextoInf.getText());
        campoTextoInf.setText("" + Math.sqrt(a));
    }

    
    /**
     *
     * Limpia los campos de la pantalla de la calculadora y operandos
     *
     */
    public void pulsarTeclaC() {
        codigoOperador = 0;
        operando1 = 0;
        operando2 = 0;
        resultado = 0;
        campoTextoSup.setText("");
        campoTextoInf.setText("0");
        cadenaOperacion = "";
    }

    
     /**
     *
     * Limpia la memoria de la calculadora
     *
     */
    public void pulsarTeclaMC() {
        ms.setForeground(Color.black);
        campoTextoSup.setText("");
        campoTextoInf.setText("0");
        M = 0;
    }

    
     /**
     *
     * Recupera en la pantalla de la calculadora lo almacenado en la memoria
     *
     */
    public void pulsarTeclaMR() {
        campoTextoSup.setText("");
        campoTextoInf.setText(String.valueOf(M));
    }

    
    /**
     *
     * Guarda en memoria el numero de pantalla de la calculadora y cambia el formato del boton
     *
     */
    public void pulsarTeclaMS() {
        ms.setForeground(Color.red);
        M = Float.parseFloat(campoTextoInf.getText());
    }

    
    /**
     *
     * Añade decimales a un numero del campo de pantalla
     *
     */
    public void pulsarTeclaDecimal() {
        cadenaOperacion = "";
        if (teclasNumero[10].isEnabled()) {
            teclasNumero[10].setEnabled(false);
            cadenaOperacion = campoTextoInf.getText() + ".";
            campoTextoInf.setText(cadenaOperacion);
        }
    }

    
    /**
     *
     * Asigna el codigo 1 correspondiente a una suma y valida nuevas operaciones sobre la suma
     * 
     * @param e Evento generado cuando se ha presionado la tecla +
     *
     */
    public void pulsarTeclaSuma(ActionEvent e) {
        teclasNumero[10].setEnabled(true);
        cadenaOperacion = "";
        if (codigoOperador == 1) {

        } else if (codigoOperador == 0) {//validacion para no chocar con otras operaciones
            if (campoTextoSup.getText().equals("")) {
                operando1 = Float.parseFloat(campoTextoInf.getText());
                cadenaOperacion += campoTextoSup.getText() + campoTextoInf.getText();
                campoTextoSup.setText(cadenaOperacion + " " + e.getActionCommand() + " ");
                campoTextoInf.setText("");
                codigoOperador = 1;
            } else {
                if (!controlOp) {//validacion para nuevas operaciones
                    operando1 = Float.parseFloat(campoTextoInf.getText());
                    cadenaOperacion += campoTextoInf.getText();
                    campoTextoSup.setText(cadenaOperacion + " " + e.getActionCommand() + " ");
                    campoTextoInf.setText("");
                    codigoOperador = 1;
                } else {//usar otras teclasOperacion con la suma
                    operando1 = Float.parseFloat(campoTextoInf.getText());
                    cadenaOperacion += campoTextoSup.getText();
                    campoTextoSup.setText(cadenaOperacion + " " + e.getActionCommand() + " ");
                    campoTextoInf.setText("");
                    codigoOperador = 1;
                }
            }
        }
    }

    
    /**
     *
     * Asigna el codigo 2 correspondiente a una resta y valida nuevas operaciones sobre la resta
     * 
     * @param e Evento generado cuando se ha presionado la tecla -
     *
     */
    public void pulsarTeclaResta(ActionEvent e) {
        teclasNumero[10].setEnabled(true);
        cadenaOperacion = "";
        if (codigoOperador == 2) {

        } else if (codigoOperador == 0) {//validacion para no chocar con otras operaciones
            if (campoTextoSup.getText().equals("")) {
                operando1 = Float.parseFloat(campoTextoInf.getText());
                cadenaOperacion += campoTextoSup.getText() + campoTextoInf.getText();
                campoTextoSup.setText(cadenaOperacion + " " + e.getActionCommand() + " ");
                campoTextoInf.setText("");
                codigoOperador = 2;
            } else {
                if (!controlOp) {//validacion para nuevas operaciones
                    operando1 = Float.parseFloat(campoTextoInf.getText());
                    cadenaOperacion += campoTextoInf.getText();
                    campoTextoSup.setText(cadenaOperacion + " " + e.getActionCommand() + " ");
                    campoTextoInf.setText("");
                    codigoOperador = 2;
                } else {//usar otras teclasOperacion con la resta
                    operando1 = Float.parseFloat(campoTextoInf.getText());
                    cadenaOperacion += campoTextoSup.getText();
                    campoTextoSup.setText(cadenaOperacion + " " + e.getActionCommand() + " ");
                    campoTextoInf.setText("");
                    codigoOperador = 2;
                }
            }
        }
    }

    
    /**
     *
     * Asigna el codigo 3 correspondiente a un producto y valida nuevas operaciones sobre dicho producto
     * 
     * @param e Evento generado cuando se ha presionado la tecla X
     *
     */
    public void pulsarTeclaMult(ActionEvent e) {
        teclasNumero[10].setEnabled(true);
        cadenaOperacion = "";
        if (codigoOperador == 3) {

        } else if (codigoOperador == 0) {//validacion para no chocar con otras operaciones
            if (campoTextoSup.getText().equals("")) {
                operando1 = Float.parseFloat(campoTextoInf.getText());
                cadenaOperacion += campoTextoSup.getText() + campoTextoInf.getText();
                campoTextoSup.setText(cadenaOperacion + " " + e.getActionCommand() + " ");
                campoTextoInf.setText("");
                codigoOperador = 3;
            } else {
                if (!controlOp) {//validacion para nuevas operaciones
                    operando1 = Float.parseFloat(campoTextoInf.getText());
                    cadenaOperacion += campoTextoInf.getText();
                    campoTextoSup.setText(cadenaOperacion + " " + e.getActionCommand() + " ");
                    campoTextoInf.setText("");
                    codigoOperador = 3;
                } else {//usar otras teclasOperacion con la multiplicacion
                    operando1 = Float.parseFloat(campoTextoInf.getText());
                    cadenaOperacion += campoTextoSup.getText();
                    campoTextoSup.setText(cadenaOperacion + " " + e.getActionCommand() + " ");
                    campoTextoInf.setText("");
                    codigoOperador = 3;
                }
            }
        }
    }

 
     /**
     *
     * Asigna el codigo 4 correspondiente a un producto y valida nuevas operaciones sobre dicha operacion
     * 
     * @param e Evento generado cuando se ha presionado la tecla /
     *
     */
    public void pulsarTeclaDiv(ActionEvent e) {
        teclasNumero[10].setEnabled(true);
        cadenaOperacion = "";
        if (codigoOperador == 4) {

        } else if (codigoOperador == 0) {//validacion para no chocar con otras operaciones
            if (campoTextoSup.getText().equals("")) {
                operando1 = Float.parseFloat(campoTextoInf.getText());
                cadenaOperacion += campoTextoSup.getText() + campoTextoInf.getText();
                campoTextoSup.setText(cadenaOperacion + " " + e.getActionCommand() + " ");
                campoTextoInf.setText("");
                codigoOperador = 4;
            } else {
                if (!controlOp) {//validacion para nuevas operaciones
                    operando1 = Float.parseFloat(campoTextoInf.getText());
                    cadenaOperacion += campoTextoInf.getText();
                    campoTextoSup.setText(cadenaOperacion + " " + e.getActionCommand() + " ");
                    campoTextoInf.setText("");
                    codigoOperador = 4;
                } else {//usar otras teclas de operacion con la division
                    operando1 = Float.parseFloat(campoTextoInf.getText());
                    cadenaOperacion += campoTextoSup.getText();
                    campoTextoSup.setText(cadenaOperacion + " " + e.getActionCommand() + " ");
                    campoTextoInf.setText("");
                    codigoOperador = 4;
                }
            }
        }
    }

       
    /**
     *
     * Realiza la operacion matématica correspondiente al codigo asignado tras pulsar la tecla = y muestra el resultado
     *
     */
    public void pulsarTeclaResultado() {
        cadenaOperacion = "";
        cadenaOperacion += campoTextoSup.getText() + campoTextoInf.getText();
        campoTextoSup.setText(cadenaOperacion);
        operando2 = Float.parseFloat(campoTextoInf.getText());

        switch (codigoOperador) {
            case 1:
                operSuma();
                break;
            case 2:
                operResta();
                break;
            case 3:
                operProducto();
                break;
            case 4:
                if (Float.parseFloat(campoTextoInf.getText()) != 0) {
                    operDivision();
                }
                break;
        }
        campoTextoInf.setText(String.valueOf(resultado));
        codigoOperador = 0;
    }

       
    /**
     *
     * Realiza la suma entre dos numeros
     *
     */
    public void operSuma() {
        resultado = operando1 + operando2;
    }

       
    /**
     *
     * Realiza la resta entre dos numeros
     *
     */
    public void operResta() {
        resultado = operando1 - operando2;
    }

       
    /**
     *
     * Realiza el producto entre dos numeros
     *
     */
    public void operProducto() {
        resultado = operando1 * operando2;
    }

       
    /**
     *
     * Realiza la division entre dos numeros
     *
     */
    public void operDivision() {
        resultado = operando1 / operando2;
    }
}
