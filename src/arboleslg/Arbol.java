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
}public void eliminarNivel(int nivelObjetivo){
    if (raiz == null){
        JOptionPane.showMessageDialog(null, "Todavía no hay datos en el árbol.");
        return;
    }
    if (nivelObjetivo < 0){
        JOptionPane.showMessageDialog(null, "El nivel no puede ser negativo.");
        return;
    }

    if (nivelObjetivo == 0){
        raiz = null; // se elimina todo el árbol
        return;
    }

    if (nivelObjetivo == 1){
        raiz.setLiga(null); // se cortan todos los hijos directos de la raíz
        return;
    }

   
    cortarNivel(raiz.getLiga(), 1, nivelObjetivo);
}



private void cortarNivel(Nodo primero, int nivelActual, int nivelObjetivo){
    Nodo actual = primero;

    while (actual != null){
        if (actual.getSw()){
            Nodo nuevo = actual.getLigaLista(); 

            if (nivelActual + 1 == nivelObjetivo){
             
                actual.setSw(false);     
                actual.setLigaLista(null); 
            } else {
                
                cortarNivel(nuevo.getLiga(), nivelActual + 1, nivelObjetivo);
            }
        }
        actual = actual.getLiga();
    }
}public void ancestroComun(String cedulaA, String cedulaB){
    if (raiz == null){
        JOptionPane.showMessageDialog(null, "Todavía no hay datos en el árbol.");
        return;
    }

    Nodo lca = buscarLCA(raiz, cedulaA, cedulaB);

    if (lca == null){
        JOptionPane.showMessageDialog(null, "No se encontró un ancestro común (verifica las cédulas).");
    } else {
        JOptionPane.showMessageDialog(null,
                "El ancestro común más cercano es: " + lca.getNombre() + " (CC: " + lca.getCedula() + ")");
    }
}

// Devuelve el LCA de cedulaA y cedulaB dentro del subárbol que cuelga de "actual" (nivel: hermanos)
private Nodo buscarLCA(Nodo primero, String cedulaA, String cedulaB){
    Nodo encontrado = null;
    int cantidadEncontrados = 0;
    Nodo actual = primero;

    while (actual != null){
        Nodo representante = actual.getSw() ? actual.getLigaLista() : actual;

        boolean esA = representante.getCedula().equals(cedulaA);
        boolean esB = representante.getCedula().equals(cedulaB);

        if (esA || esB){
            cantidadEncontrados++;
            encontrado = representante;

            // si el otro está en el subárbol de este mismo nodo, este nodo ya es el LCA
            String otraCedula = esA ? cedulaB : cedulaA;
            if (actual.getSw() && contiene(representante.getLiga(), otraCedula)){
                return representante;
            }

        } else if (actual.getSw()){
            Nodo resultado = buscarLCA(representante.getLiga(), cedulaA, cedulaB);
            if (resultado != null){
                cantidadEncontrados++;
                encontrado = resultado;
            }
        }

        actual = actual.getLiga();
    }

    // Si entre los hermanos de este nivel aparecieron A y B por separado, "primero" es el LCA visto desde arriba
    // (esto se resuelve automáticamente al burbujear hacia el nodo padre que los llamó)
    if (cantidadEncontrados >= 2){
        return primero; // marcador: señaliza que ambos aparecen en este nivel de hermanos
    }
    return encontrado;
}

// Verifica si una cédula existe dentro del subárbol que cuelga de "primero"
private boolean contiene(Nodo primero, String cedula){
    Nodo actual = primero;
    while (actual != null){
        Nodo representante = actual.getSw() ? actual.getLigaLista() : actual;
        if (representante.getCedula().equals(cedula)){
            return true;
        }
        if (actual.getSw() && contiene(representante.getLiga(), cedula)){
            return true;
        }
        actual = actual.getLiga();
    }
    return false;
}// Pequeña clase auxiliar propia (no es colección, solo agrupa dos referencias)
private static class Resultado {
    Nodo anterior;
    Nodo entrada;
}

// Busca la "entrada" de una cédula en la cadena (el nodo simple, o el nodo sw=true si esa persona es padre),
// junto con el nodo anterior que apunta hacia ella
private Resultado buscarEntrada(Nodo anterior, Nodo actual, String cedula){
    while (actual != null){
        if (!actual.getSw()){
            if (actual.getCedula().equals(cedula)){
                Resultado r = new Resultado();
                r.anterior = anterior;
                r.entrada = actual;
                return r;
            }
        } else {
            Nodo nuevo = actual.getLigaLista();
            if (nuevo.getCedula().equals(cedula)){
                Resultado r = new Resultado();
                r.anterior = anterior;
                r.entrada = actual; // se mueve TODO el nodo sw=true, con su sublista completa
                return r;
            }
            Resultado sub = buscarEntrada(nuevo, nuevo.getLiga(), cedula);
            if (sub != null) return sub;
        }
        anterior = actual;
        actual = actual.getLiga();
    }
    return null;
}




// Verifica si cedulaB está DENTRO del subárbol que representa "entrada" (para evitar ciclos)
private boolean estaEnSubarbol(Nodo entrada, String cedulaB){
    Nodo representante = entrada.getSw() ? entrada.getLigaLista() : entrada;
    if (representante.getCedula().equals(cedulaB)){
        return true;
    }
    if (entrada.getSw()){
        return contiene(representante.getLiga(), cedulaB);
    }
    return false;
}

public void trasladarRama(String cedulaA, String cedulaB){
    if (raiz == null){
        JOptionPane.showMessageDialog(null, "Todavía no hay datos en el árbol.");
        return;
    }
    if (cedulaA.equals(cedulaB)){
        JOptionPane.showMessageDialog(null, "Las dos cédulas deben ser diferentes.");
        return;
    }
    if (raiz.getCedula().equals(cedulaA)){
        JOptionPane.showMessageDialog(null, "No se puede trasladar al ancestro principal (no tiene padre).");
        return;
    }

    Resultado resultado = buscarEntrada(raiz, raiz.getLiga(), cedulaA);
    if (resultado == null){
        JOptionPane.showMessageDialog(null, "No existe una persona con la cédula de A.");
        return;
    }

    if (estaEnSubarbol(resultado.entrada, cedulaB)){
        JOptionPane.showMessageDialog(null,
                "No se puede: la persona B es descendiente de A (generaría un ciclo).");
        return;
    }

    Nodo nodoB = BuscarPadre(raiz, cedulaB);
    if (nodoB == null){
        JOptionPane.showMessageDialog(null, "No existe una persona con la cédula de B.");
        return;
    }

    // 1. Desconectar A (y todo su subárbol) de su posición actual
    Nodo entrada = resultado.entrada;
    resultado.anterior.setLiga(entrada.getLiga());
    entrada.setLiga(null);

    // 2. Reconectar A como hijo de B, en la posición ordenada que le corresponda
    Insertarhijo(nodoB, entrada);

    JOptionPane.showMessageDialog(null, "Traslado realizado correctamente.");
}}