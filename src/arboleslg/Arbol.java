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

/**
 *
 * @author Mari♥
 */
public class Arbol {
    private Nodo raiz;

    public Arbol(Nodo raiz) {
        this.raiz = raiz;
    }

    public Nodo getRaiz() {
        return raiz;
    }

    public void setRaiz(Nodo raiz) {
        this.raiz = raiz;
    }

    
    public void Insertar(){
        String nombre = JOptionPane.showInputDialog("Ingresa el nombre de la persona: ");
        String Cedula= JOptionPane.showInputDialog("Ingresa la cedula de la persona: ");
        String fechaTexto= JOptionPane.showInputDialog("Ingrese la fecha dd/MM/yyyy");
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
       LocalDate fecha=LocalDate.parse(fechaTexto, formato);

        Nodo nuevo=new Nodo(nombre,Cedula,fecha);
        if (raiz!=null){
            raiz=nuevo;
        }
        else{
            String cedulaPadre= JOptionPane.showInputDialog("Ingresa la Cedula del Padre: ");
            Nodo padre = BuscarPadre(raiz, cedulaPadre);

            if (padre == null) {
                JOptionPane.showMessageDialog(null,
                        "No existe un padre con esa cédula, revise los ancestros.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                Insertarhijo(padre, nuevo);
            }
           
            
        }

    
    
}
    public  Nodo BuscarPadre(Nodo raiz, String cedula){
        Nodo p=raiz;
        while (p!=null ){
            if(p.getSw()==false){
                if(p.getCedula()==cedula){
                    return p;
                }
                p=p.getLiga();
                
            }else{
                Nodo r=p.getLigaLista();
                Nodo encontrado=BuscarPadre(r,cedula);
                if (encontrado != null){
                return encontrado;
            }
                p=p.getLiga();
                
            } 
        }
        
        
        
        return null;
        
    }
    public void Insertarhijo(Nodo padre, Nodo hijo){
        if (padre instanceof NodoCabeza){ //padre es de tipo NodoCabeza?
      
        Nodo actual = padre;
          while (actual.getLiga() != null && //organiza de hijo mayor a menor
       actual.getLiga().getFecha().compareTo(hijo.getFecha()) < 0){
    actual = actual.getLiga();
}
      

    hijo.setLiga(actual.getLiga());
    actual.setLiga(hijo);
        }else{
        
        Nodo nuevo= new NodoCabeza(padre.getNombre(),padre.getCedula(),padre.getFecha());
        padre.setSw(true);
        padre.setLigaLista(nuevo);
        nuevo.setLiga(hijo);
    }}

}
