/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package arboleslg;

import javax.swing.JOptionPane;

/**
 *
 * @author sala208
 */
public class ArbolesLG {

    public static void main(String[] args) {
        Arbol arbol = new Arbol(null);
        String[] opciones = {"Insertar persona", "Ver árbol", "Salir"};
        int seleccion;

        do {
            seleccion = JOptionPane.showOptionDialog(
                null,
                "Selecciona una opción:",
                "Menú - Árbol Genealógico",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                opciones,
                opciones[0]
            );

            switch (seleccion) {
                case 0:
                    arbol.Insertar();
                    break;
                case 1:
                    if (arbol.getRaiz() == null){
                        JOptionPane.showMessageDialog(null, "Todavía no hay datos en el árbol.");
                    } else {
                        VentanaArbol ventana = new VentanaArbol(arbol);
                        ventana.setVisible(true);
                    }
                    break;
                case 2:
                case -1: // si cierra la ventana con la X
                    JOptionPane.showMessageDialog(null, "Saliendo...");
                    break;
            }

        } while (seleccion != 2 && seleccion != -1);
    }

    

  
//Hacer Menu     
}
