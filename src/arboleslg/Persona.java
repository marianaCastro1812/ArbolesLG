/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package arboleslg;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.JOptionPane;

/**
 *
 * @author sala208
 */
public class Persona {
    
    public void Registrar(){
        String nombre = JOptionPane.showInputDialog("Ingresa el nombre de la persona: ");
        String Cedula= JOptionPane.showInputDialog("Ingresa la cedula de la persona: ");
        String fechaTexto= JOptionPane.showInputDialog("Ingrese la fecha dd/MM/yyyy");
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
       LocalDate fecha=LocalDate.parse(fechaTexto, formato);

        Nodo nuevo=new Nodo(nombre,Cedula,fecha);
        if (raiz!=null){
            raiz=nuevo;
            nuevo.setSw(true);
        }
        else{
            String cedulaPadre= JOptionPane.showInputDialog("Ingresa la Cedula del Padre: ");
//            BuscarPadre(cedulaPadre);
            
        }

    }
    
}
