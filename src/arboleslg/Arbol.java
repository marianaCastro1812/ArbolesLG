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
        if (raiz==null){
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
    private Nodo buscarNodo(Nodo actual, String cedula){
    while (actual != null){
        if (!actual.getSw()){
            if (actual.getCedula().equals(cedula)){
                return actual;
            }
        } else {
            Nodo nuevo = actual.getLigaLista();
            if (nuevo.getCedula().equals(cedula)){
                return nuevo;
            }
            Nodo encontrado = buscarNodo(nuevo.getLiga(), cedula);
            if (encontrado != null){
                return encontrado;
            }
        }
        actual = actual.getLiga();
    }
    return null;
}
    public  Nodo BuscarPadre(Nodo raiz, String cedula){
        Nodo p=raiz;
        while (p!=null ){
            if(p.getSw()==false){
                if(p.getCedula().equals(cedula)){
                    return p;
                }
                p=p.getLiga();
                
            }else{
                if (p.getLigaLista().getCedula().equals(cedula)) {
                    return p;
                    
                }
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
    

    public void Insertarhijo(Nodo padre, Nodo hijo) {
        if (padre!=raiz && !padre.getSw() ) {     
        Nodo nuevo= new Nodo(padre.getNombre(),padre.getCedula(),padre.getFecha());
        padre.setSw(true);
        padre.setLigaLista(nuevo);
        nuevo.setLiga(hijo);
            
        }else{
           
            if (padre.getSw()) {
                padre=padre.getLigaLista();
            }
              Nodo actual = padre;
            while (actual.getLiga() != null
                    && //organiza de hijo mayor a menor
//                    actual.getLiga().getCedula().compareTo(hijo.getCedula()) < 0) {
      actual.getLiga().getFecha().compareTo(hijo.getFecha()) < 0){
                actual = actual.getLiga();
            }
  

    hijo.setLiga(actual.getLiga());
    actual.setLiga(hijo);
         
            
        }
        

          
        
    }
    public void verArbol(){
    if (raiz == null ){
        JOptionPane.showMessageDialog(null,
                "Todavía no hay datos en el árbol.",
                "Aviso",
                JOptionPane.WARNING_MESSAGE);
        return;
    }
    
    VentanaArbol ventana = new VentanaArbol(this);
    ventana.setVisible(true);
}
   public void eliminar(Nodo raiz, String cedula){
    if (raiz == null){
        JOptionPane.showMessageDialog(null,
                "Todavía no hay datos en el árbol.",
                "Aviso",
                JOptionPane.WARNING_MESSAGE);
        return;
    }

    if (raiz.getCedula().equals(cedula)){
        eliminarRaiz();
        return; 
    }

    boolean encontrado = eliminarEnCadena(raiz, raiz.getLiga(), cedula);
    if (!encontrado){
        JOptionPane.showMessageDialog(null, "No existe una persona con esa cédula.");
    }
}

// Busca "cedula" en la cadena que empieza en "actual" (hermanos, y recursivamente en sus sublistas)

public boolean eliminarEnCadena(Nodo anterior, Nodo actual, String cedula){
    while (actual != null){

        if (!actual.getSw()){
            // nodo simple
            if (actual.getCedula().equals(cedula)){
                anterior.setLiga(actual.getLiga());
                return true;
            }

        } else {
       
            Nodo nuevo = actual.getLigaLista();

            if (nuevo.getCedula().equals(cedula)){
                // encontramos al padre buscado -> hay que promover a su hijo mayor
                promover(anterior, actual, nuevo);
                return true;
            }

            // no es este padre, seguir buscando dentro de su sublista
            if (eliminarEnCadena(nuevo, nuevo.getLiga(), cedula)){
                return true;
            }
        }

        anterior = actual;
        actual = actual.getLiga();
    }
    return false;
}


private void promover(Nodo anterior, Nodo viejo, Nodo nuevo){
    Nodo mayor = nuevo.getLiga();
    Nodo restoHermanos = mayor.getLiga();
    mayor.setLiga(null);

    Nodo hermano = restoHermanos;
    while (hermano != null){
        Nodo siguiente = hermano.getLiga();
        hermano.setLiga(null);
        Insertarhijo(mayor, hermano);
        hermano = siguiente; 
    }

    anterior.setLiga(mayor);
    mayor.setLiga(viejo.getLiga());
}
public void eliminarRaiz(){
    if (raiz.getLiga() == null){
        raiz = null; // no tenía hijos, el árbol queda vacío
        return;
    }

    Nodo mayor = raiz.getLiga();
    Nodo restoHermanos = mayor.getLiga();
    mayor.setLiga(null);

    // En caso de que el hijo mayor sea padre
    Nodo hijosPropios = null;
    if (mayor.getSw()){
        Nodo nuevoDelMayor = mayor.getLigaLista();
        hijosPropios = nuevoDelMayor.getLiga();
        mayor.setSw(false);
        mayor.setLigaLista(null);
    }

    raiz = mayor;
    mayor.setLiga(hijosPropios);

    // fusionar a los hermanos que le quedaron, como nuevos hijos de la raíz
    Nodo hermano = restoHermanos;
    while (hermano != null){
        Nodo siguiente = hermano.getLiga();
        hermano.setLiga(null);
        Insertarhijo(mayor, hermano); 
        hermano = siguiente;
    }
}
public void actualizar(String cedula){
    if (raiz == null){
        JOptionPane.showMessageDialog(null,
                "Todavía no hay datos en el árbol.",
                "Aviso",
                JOptionPane.WARNING_MESSAGE);
        return;
    }

    Nodo encontrado;
    if (raiz.getCedula().equals(cedula)){
        encontrado = raiz; // la raíz se revisa aparte, igual que en eliminar()
    } else {
        encontrado = buscarNodo(raiz.getLiga(), cedula);
    }

    if (encontrado == null){
        JOptionPane.showMessageDialog(null, "No existe una persona con esa cédula.");
        return;
    }

    String nombre = JOptionPane.showInputDialog("Nuevo nombre:", encontrado.getNombre());
    if (nombre != null && !nombre.isBlank()){
        encontrado.setNombre(nombre);
    }

    DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    String fechaTexto = JOptionPane.showInputDialog(
            "Nueva fecha (dd/MM/yyyy):",
            encontrado.getFecha().format(formato)
    );
    if (fechaTexto != null && !fechaTexto.isBlank()){
        try {
            LocalDate nuevaFecha = LocalDate.parse(fechaTexto, formato);
            encontrado.setFecha(nuevaFecha);
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, "Fecha inválida, no se actualizó.");
        }
    }

    JOptionPane.showMessageDialog(null, "Datos actualizados correctamente.");
}}