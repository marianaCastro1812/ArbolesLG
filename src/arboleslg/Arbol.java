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
    // Busca una persona dentro de todo el árbol usando su cédula
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
    //Busca el nodo simple en caso de no ser padre o el auxiliar si ya lo es
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
        if (padre!=raiz && !padre.getSw() ) {     //si es primer hijo crea un nodo auxiliar
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
                //  hay que poner a su hijo mayor como nuevo padre
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

//convierte los h¿hermanos en hijos del hijo mayor
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
}


/* ---------- 2. CONSULTAS DE RELACIONES FAMILIARES ---------- */




// Busca el padre directo de una persona usando su cédula
private Nodo buscarPadreNodo(Nodo actual, String cedula){
    if (actual == null){
        return null;
    }

    Nodo hijo = actual.getLiga();

    while (hijo != null){
        if (hijo.getCedula().equals(cedula)){
            return actual;
        }

        hijo = hijo.getLiga();
    }

    if (actual.getSw() && actual.getLigaLista() != null){
        Nodo hijos = actual.getLigaLista().getLiga();

        while (hijos != null){
            if (hijos.getCedula().equals(cedula)){
                return actual;
            }

            hijos = hijos.getLiga();
        }
    }

    Nodo encontrado = buscarPadreNodo(actual.getLiga(), cedula);

    if (encontrado != null){
        return encontrado;
    }

    if (actual.getSw() && actual.getLigaLista() != null){
        encontrado = buscarPadreNodo(
                actual.getLigaLista().getLiga(),
                cedula
        );

        if (encontrado != null){
            return encontrado;
        }
    }

    return null;
}

// Obtiene el primer hijo de una persona según la estructura del árbol
private Nodo obtenerInicioHijos(Nodo persona){
    if (persona == null){
        return null;
    }

    if (persona == raiz){
        return persona.getLiga();
    }

    if (persona.getSw() && persona.getLigaLista() != null){
        return persona.getLigaLista().getLiga();
    }

    return null;
}

// Muestra el padre directo de la persona consultada
public void mostrarPadre(String cedula){
    if (raiz == null){
        JOptionPane.showMessageDialog(null,
                "Todavía no hay datos en el árbol.");
        return;
    }

    Nodo persona = buscarNodo(raiz, cedula);

    if (persona == null){
        JOptionPane.showMessageDialog(null,
                "No existe una persona con esa cédula.");
        return;
    }

    if (persona == raiz){
        JOptionPane.showMessageDialog(null,
                "La persona consultada es la raíz y no tiene padre registrado.");
        return;
    }

    Nodo padre = buscarPadreNodo(raiz, cedula);

    if (padre == null){
        JOptionPane.showMessageDialog(null,
                "No se encontró el padre de la persona.");
        return;
    }

    JOptionPane.showMessageDialog(
            null,
            "PADRE\n\n"
            + "Nombre: " + padre.getNombre()
            + "\nCédula: " + padre.getCedula()
            + "\nFecha: " + padre.getFecha()
    );
}

// Muestra todos los hijos directos de la persona consultada
public void mostrarHijos(String cedula){
    if (raiz == null){
        JOptionPane.showMessageDialog(null,
                "Todavía no hay datos en el árbol.");
        return;
    }

    Nodo persona = buscarNodo(raiz, cedula);

    if (persona == null){
        JOptionPane.showMessageDialog(null,
                "No existe una persona con esa cédula.");
        return;
    }

    Nodo hijo = obtenerInicioHijos(persona);

    if (hijo == null){
        JOptionPane.showMessageDialog(null,
                "La persona no tiene hijos registrados.");
        return;
    }

    String mensaje = "HIJOS DE " + persona.getNombre() + "\n\n";

    while (hijo != null){
        mensaje += "Nombre: " + hijo.getNombre()
                + "\nCédula: " + hijo.getCedula()
                + "\nFecha: " + hijo.getFecha()
                + "\n\n";

        hijo = hijo.getLiga();
    }

    JOptionPane.showMessageDialog(null, mensaje);
}

// Muestra los hermanos de la persona consultada
public void mostrarHermanos(String cedula){
    if (raiz == null){
        JOptionPane.showMessageDialog(null,
                "Todavía no hay datos en el árbol.");
        return;
    }

    Nodo persona = buscarNodo(raiz, cedula);

    if (persona == null){
        JOptionPane.showMessageDialog(null,
                "No existe una persona con esa cédula.");
        return;
    }

    if (persona == raiz){
        JOptionPane.showMessageDialog(null,
                "La raíz no tiene hermanos registrados.");
        return;
    }

    Nodo padre = buscarPadreNodo(raiz, cedula);

    if (padre == null){
        JOptionPane.showMessageDialog(null,
                "La persona consultada no tiene hermanos registrados.");
        return;
    }

    Nodo hermano = obtenerInicioHijos(padre);

    String mensaje = "HERMANOS DE " + persona.getNombre() + "\n\n";
    boolean hayHermanos = false;

    while (hermano != null){

        if (!hermano.getCedula().equals(cedula)){
            mensaje += "Nombre: " + hermano.getNombre()
                    + "\nCédula: " + hermano.getCedula()
                    + "\nFecha: " + hermano.getFecha()
                    + "\n\n";

            hayHermanos = true;
        }

        hermano = hermano.getLiga();
    }

    if (!hayHermanos){
        JOptionPane.showMessageDialog(null,
                "La persona consultada no tiene hermanos registrados.");
    } else {
        JOptionPane.showMessageDialog(null, mensaje);
    }
}

// Muestra los hermanos del padre de la persona consultada
public void mostrarTios(String cedula){
    if (raiz == null){
        JOptionPane.showMessageDialog(null,
                "Todavía no hay datos en el árbol.");
        return;
    }

    Nodo persona = buscarNodo(raiz, cedula);

    if (persona == null){
        JOptionPane.showMessageDialog(null,
                "No existe una persona con esa cédula.");
        return;
    }

    Nodo padre = buscarPadreNodo(raiz, cedula);

    if (padre == null){
        JOptionPane.showMessageDialog(null,
                "La persona consultada no tiene tíos registrados.");
        return;
    }

    Nodo abuelo = buscarPadreNodo(raiz, padre.getCedula());

    if (abuelo == null){
        JOptionPane.showMessageDialog(null,
                "La persona consultada no tiene tíos registrados.");
        return;
    }

    Nodo tio = obtenerInicioHijos(abuelo);

    String mensaje = "TÍOS DE " + persona.getNombre() + "\n\n";
    boolean hayTios = false;

    while (tio != null){

        if (!tio.getCedula().equals(padre.getCedula())){
            mensaje += "Nombre: " + tio.getNombre()
                    + "\nCédula: " + tio.getCedula()
                    + "\nFecha: " + tio.getFecha()
                    + "\n\n";

            hayTios = true;
        }

        tio = tio.getLiga();
    }

    if (!hayTios){
        JOptionPane.showMessageDialog(null,
                "La persona consultada no tiene tíos registrados.");
    } else {
        JOptionPane.showMessageDialog(null, mensaje);
    }
}

// Busca los hijos de los hermanos de la persona consultada
public void mostrarSobrinos(String cedula){
    if (raiz == null){
        JOptionPane.showMessageDialog(null,
                "Todavía no hay datos en el árbol.");
        return;
    }

    Nodo persona = buscarNodo(raiz, cedula);

    if (persona == null){
        JOptionPane.showMessageDialog(null,
                "No existe una persona con esa cédula.");
        return;
    }

    Nodo padre = buscarPadreNodo(raiz, cedula);

    if (padre == null){
        JOptionPane.showMessageDialog(null,
                "La persona consultada no tiene sobrinos registrados.");
        return;
    }

    Nodo hermano = obtenerInicioHijos(padre);

    String mensaje = "SOBRINOS DE " + persona.getNombre() + "\n\n";
    boolean haySobrinos = false;

    while (hermano != null){

        if (!hermano.getCedula().equals(cedula)){

            Nodo sobrino = obtenerInicioHijos(hermano);

            while (sobrino != null){

                mensaje += "Nombre: " + sobrino.getNombre()
                        + "\nCédula: " + sobrino.getCedula()
                        + "\nFecha: " + sobrino.getFecha()
                        + "\n\n";

                haySobrinos = true;

                sobrino = sobrino.getLiga();
            }
        }

        hermano = hermano.getLiga();
    }

    if (!haySobrinos){
        JOptionPane.showMessageDialog(null,
                "La persona consultada no tiene sobrinos registrados.");
    } else {
        JOptionPane.showMessageDialog(null, mensaje);
    }
}

// Busca los hijos de los tíos de la persona consultada
public void mostrarPrimos(String cedula){
    if (raiz == null){
        JOptionPane.showMessageDialog(null,
                "Todavía no hay datos en el árbol.");
        return;
    }

    Nodo persona = buscarNodo(raiz, cedula);

    if (persona == null){
        JOptionPane.showMessageDialog(null,
                "No existe una persona con esa cédula.");
        return;
    }

    Nodo padre = buscarPadreNodo(raiz, cedula);

    if (padre == null){
        JOptionPane.showMessageDialog(null,
                "La persona consultada no tiene primos registrados.");
        return;
    }

    Nodo abuelo = buscarPadreNodo(raiz, padre.getCedula());

    if (abuelo == null){
        JOptionPane.showMessageDialog(null,
                "La persona consultada no tiene primos registrados.");
        return;
    }

    Nodo tio = obtenerInicioHijos(abuelo);

    String mensaje = "PRIMOS DE " + persona.getNombre() + "\n\n";
    boolean hayPrimos = false;

    while (tio != null){

        if (!tio.getCedula().equals(padre.getCedula())){

            Nodo primo = obtenerInicioHijos(tio);

            while (primo != null){

                mensaje += "Nombre: " + primo.getNombre()
                        + "\nCédula: " + primo.getCedula()
                        + "\nFecha: " + primo.getFecha()
                        + "\n\n";

                hayPrimos = true;

                primo = primo.getLiga();
            }
        }

        tio = tio.getLiga();
    }

    if (!hayPrimos){
        JOptionPane.showMessageDialog(null,
                "La persona consultada no tiene primos registrados.");
    } else {
        JOptionPane.showMessageDialog(null, mensaje);
    }
}

// Muestra la línea directa de padres desde la persona hasta la raíz
public void mostrarAncestros(String cedula){
    if (raiz == null){
        JOptionPane.showMessageDialog(null,
                "Todavía no hay datos en el árbol.");
        return;
    }

    Nodo persona = buscarNodo(raiz, cedula);

    if (persona == null){
        JOptionPane.showMessageDialog(null,
                "No existe una persona con esa cédula.");
        return;
    }

    if (persona == raiz){
        JOptionPane.showMessageDialog(null,
                "La persona consultada es la raíz y no tiene ancestros registrados.");
        return;
    }

    String mensaje = "ANCESTROS DE " + persona.getNombre() + "\n\n";

    Nodo actual = persona;
    boolean hayAncestros = false;

    while (actual != raiz){

        Nodo padre = buscarPadreNodo(raiz, actual.getCedula());

        if (padre == null){
            break;
        }

        mensaje += "Nombre: " + padre.getNombre()
                + "\nCédula: " + padre.getCedula()
                + "\nFecha: " + padre.getFecha()
                + "\n\n";

        hayAncestros = true;

        actual = padre;
    }

    if (!hayAncestros){
        JOptionPane.showMessageDialog(null,
                "No se encontraron ancestros.");
    } else {
        JOptionPane.showMessageDialog(null, mensaje);
    }
}

// Muestra todos los descendientes de la persona consultada
public void mostrarDescendientes(String cedula){
    if (raiz == null){
        JOptionPane.showMessageDialog(null,
                "Todavía no hay datos en el árbol.");
        return;
    }

    Nodo persona = buscarNodo(raiz, cedula);

    if (persona == null){
        JOptionPane.showMessageDialog(null,
                "No existe una persona con esa cédula.");
        return;
    }

    Nodo hijo = obtenerInicioHijos(persona);

    if (hijo == null){
        JOptionPane.showMessageDialog(null,
                "La persona consultada no tiene descendientes.");
        return;
    }

    String mensaje = "DESCENDIENTES DE " + persona.getNombre() + "\n\n";

    mensaje += construirDescendientes(persona);

    JOptionPane.showMessageDialog(null, mensaje);
}

// Recorre recursivamente hijos, nietos, bisnietos y demás descendientes
private String construirDescendientes(Nodo persona){
    String mensaje = "";

    Nodo hijo = obtenerInicioHijos(persona);

    while (hijo != null){

        mensaje += "Nombre: " + hijo.getNombre()
                + "\nCédula: " + hijo.getCedula()
                + "\nFecha: " + hijo.getFecha()
                + "\n\n";

        mensaje += construirDescendientes(hijo);

        hijo = hijo.getLiga();
    }

    return mensaje;
}


/* ---------- 3. CONSULTAS ESTRUCTURALES Y VISUALIZACIÓN ---------- */

// Muestra la persona que tiene la mayor cantidad de hijos directos
public void mayorGrado(){
    if (raiz == null){
        JOptionPane.showMessageDialog(null,
                "Todavía no hay datos en el árbol.");
        return;
    }

    Nodo resultado = buscarMayorGrado(raiz);

    JOptionPane.showMessageDialog(null,
            "NODO CON MAYOR GRADO\n\n"
            + "Nombre: " + resultado.getNombre()
            + "\nCédula: " + resultado.getCedula()
            + "\nFecha: " + resultado.getFecha()
            + "\nCantidad de hijos: " + contarHijos(resultado));
}

// Recorre todo el árbol y busca el nodo con mayor cantidad de hijos
private Nodo buscarMayorGrado(Nodo actual){
    if (actual == null){
        return null;
    }

    Nodo mayor = actual;

    Nodo hijo = obtenerInicioHijos(actual);

    while (hijo != null){
        Nodo candidato = buscarMayorGrado(hijo);

        if (candidato != null &&
                contarHijos(candidato) > contarHijos(mayor)){
            mayor = candidato;
        }

        hijo = hijo.getLiga();
    }

    return mayor;
}

// Cuenta los hijos directos de una persona
private int contarHijos(Nodo persona){
    Nodo hijo = obtenerInicioHijos(persona);
    int contador = 0;

    while (hijo != null){
        contador++;
        hijo = hijo.getLiga();
    }

    return contador;
}

// Busca y muestra la persona más joven de todo el árbol
public void familiarMasJoven(){
    if (raiz == null){
        JOptionPane.showMessageDialog(null,
                "Todavía no hay datos en el árbol.");
        return;
    }

    Nodo joven = buscarMasJoven(raiz);

    JOptionPane.showMessageDialog(null,
            "FAMILIAR MÁS JOVEN\n\n"
            + "Nombre: " + joven.getNombre()
            + "\nCédula: " + joven.getCedula()
            + "\nFecha: " + joven.getFecha());
}

// Recorre todo el árbol buscando la fecha de nacimiento más reciente
private Nodo buscarMasJoven(Nodo actual){
    Nodo joven = actual;

    Nodo hijo = obtenerInicioHijos(actual);

    while (hijo != null){
        Nodo candidato = buscarMasJoven(hijo);

        if (candidato.getFecha().isAfter(joven.getFecha())){
            joven = candidato;
        }

        hijo = hijo.getLiga();
    }

    return joven;
}

// Calcula y muestra la altura total del árbol
public void alturaArbol(){
    if (raiz == null){
        JOptionPane.showMessageDialog(null,
                "Todavía no hay datos en el árbol.");
        return;
    }

    int altura = calcularAltura(raiz);

    JOptionPane.showMessageDialog(null,
            "ALTURA DEL ÁRBOL\n\n"
            + "Cantidad de generaciones: " + altura);
}

// Calcula recursivamente la cantidad de niveles del árbol
private int calcularAltura(Nodo actual){
    if (actual == null){
        return 0;
    }

    int mayorAltura = 0;

    Nodo hijo = obtenerInicioHijos(actual);

    while (hijo != null){
        int alturaHijo = calcularAltura(hijo);

        if (alturaHijo > mayorAltura){
            mayorAltura = alturaHijo;
        }

        hijo = hijo.getLiga();
    }

    return mayorAltura + 1;
}

// Busca y muestra el nivel en el que se encuentra una persona
public void nivelRegistro(String cedula){
    if (raiz == null){
        JOptionPane.showMessageDialog(null,
                "Todavía no hay datos en el árbol.");
        return;
    }

    int nivel = buscarNivel(raiz, cedula, 0);

    if (nivel == -1){
        JOptionPane.showMessageDialog(null,
                "No existe una persona con esa cédula.");
    } else {
        JOptionPane.showMessageDialog(null,
                "NIVEL DEL REGISTRO\n\n"
                + "Cédula: " + cedula
                + "\nNivel: " + nivel);
    }
}

// Busca recursivamente el nivel de una persona
private int buscarNivel(Nodo actual, String cedula, int nivel){
    if (actual == null){
        return -1;
    }

    if (actual.getCedula().equals(cedula)){
        return nivel;
    }

    Nodo hijo = obtenerInicioHijos(actual);

    while (hijo != null){
        int resultado = buscarNivel(hijo, cedula, nivel + 1);

        if (resultado != -1){
            return resultado;
        }

        hijo = hijo.getLiga();
    }

    return -1;
}

// Muestra todas las personas que pertenecen a un nivel específico
public void registrosPorNivel(int nivel){
    if (raiz == null){
        JOptionPane.showMessageDialog(null,
                "Todavía no hay datos en el árbol.");
        return;
    }

    if (nivel < 0){
        JOptionPane.showMessageDialog(null,
                "El nivel no puede ser negativo.");
        return;
    }

    String mensaje = "REGISTROS DEL NIVEL " + nivel + "\n\n";

    mensaje = obtenerRegistrosNivel(
            raiz,
            0,
            nivel,
            mensaje
    );

    if (mensaje.equals("REGISTROS DEL NIVEL " + nivel + "\n\n")){
        JOptionPane.showMessageDialog(null,
                "No existen personas en ese nivel.");
    } else {
        JOptionPane.showMessageDialog(null, mensaje);
    }
}

// Recorre el árbol hasta encontrar el nivel solicitado
private String obtenerRegistrosNivel(
        Nodo actual,
        int nivelActual,
        int nivelObjetivo,
        String mensaje){

    if (actual == null){
        return mensaje;
    }

    if (nivelActual == nivelObjetivo){
        Nodo persona = actual;

        while (persona != null){
            mensaje += "Nombre: " + persona.getNombre()
                    + "\nCédula: " + persona.getCedula()
                    + "\nFecha: " + persona.getFecha()
                    + "\n\n";

            persona = persona.getLiga();
        }

        return mensaje;
    }

    Nodo hijo = obtenerInicioHijos(actual);

    if (hijo != null){
        mensaje = obtenerRegistrosNivel(
                hijo,
                nivelActual + 1,
                nivelObjetivo,
                mensaje
        );
    }

    return mensaje;
}

// Busca y muestra la persona que se encuentra en el nivel más profundo
public void nodoMayorNivel(){
    if (raiz == null){
        JOptionPane.showMessageDialog(null,
                "Todavía no hay datos en el árbol.");
        return;
    }

    NodoNivel resultado = buscarNodoMayorNivel(
            raiz,
            0,
            new NodoNivel()
    );

    JOptionPane.showMessageDialog(null,
            "NODO CON MAYOR NIVEL\n\n"
            + "Nombre: " + resultado.persona.getNombre()
            + "\nCédula: " + resultado.persona.getCedula()
            + "\nFecha: " + resultado.persona.getFecha()
            + "\nNivel: " + resultado.nivel);
}

// Busca recursivamente el nodo que está más profundo
private NodoNivel buscarNodoMayorNivel(
        Nodo actual,
        int nivel,
        NodoNivel mayor){

    if (actual == null){
        return mayor;
    }

    if (nivel > mayor.nivel){
        mayor.persona = actual;
        mayor.nivel = nivel;
    }

    Nodo hijo = obtenerInicioHijos(actual);

    while (hijo != null){
        mayor = buscarNodoMayorNivel(
                hijo,
                nivel + 1,
                mayor
        );

        hijo = hijo.getLiga();
    }

    return mayor;
}

// Guarda temporalmente una persona y su nivel
private static class NodoNivel {
    Nodo persona;
    int nivel = -1;
}


/* ---------- 4. OTRAS OPERACIONES ---------- */



public void eliminarNivel(int nivelObjetivo){
    if (raiz == null){
        JOptionPane.showMessageDialog(null, "Todavía no hay datos en el árbol.");
        return;
    }
    if (nivelObjetivo <= 0){
        JOptionPane.showMessageDialog(null, "No se puede eliminar el nivel 0 (el ancestro principal).");
        return;
    }

    if (nivelObjetivo == 1){
        // Los hijos de la raíz desaparecen; sus nietos suben a ser hijos directos de la raíz
        Nodo hijo = raiz.getLiga();
        raiz.setLiga(null); // se vacía el nivel 1

        while (hijo != null){
            Nodo siguienteHijo = hijo.getLiga();

            // rescatar los hijos de este nodo antes de descartarlo
            if (hijo.getSw()){
                Nodo nietos = hijo.getLigaLista().getLiga();
                reengancharTodos(raiz, nietos);
            }

            hijo = siguienteHijo;
        }
        return;
    }

    // Para niveles 2+, recorremos hasta el nivel anterior (nivelObjetivo - 1) y ahí hacemos lo mismo
    procesarNivel(raiz.getLiga(), 1, nivelObjetivo);
}

// Recorre hasta encontrar los nodos del nivel (nivelObjetivo - 1) y elimina sus hijos, subiendo a los nietos
private void procesarNivel(Nodo primero, int nivelActual, int nivelObjetivo){
    Nodo actual = primero;

    while (actual != null){
        if (actual.getSw()){
            Nodo representante = actual.getLigaLista();

            if (nivelActual + 1 == nivelObjetivo){
                // los hijos de "representante" son el nivel a eliminar
                Nodo hijo = representante.getLiga();
                representante.setLiga(null); // se vacía su lista de hijos

                while (hijo != null){
                    Nodo siguienteHijo = hijo.getLiga();

                    if (hijo.getSw()){
                        Nodo nietos = hijo.getLigaLista().getLiga();
                        reengancharTodos(representante, nietos);
                    }

                    hijo = siguienteHijo;
                }

                // si al final no quedó ningún nieto, este nodo ya no es padre
                if (representante.getLiga() == null){
                    actual.setSw(false);
                    actual.setLigaLista(null);
                }

            } else {
                procesarNivel(representante.getLiga(), nivelActual + 1, nivelObjetivo);
            }
        }
        actual = actual.getLiga();
    }
}

// Engancha una cadena de nodos como hijos de "nuevoPadre", uno por uno, respetando el orden por cédula
private void reengancharTodos(Nodo nuevoPadre, Nodo cadena){
    Nodo actual = cadena;
    while (actual != null){
        Nodo siguiente = actual.getLiga();
        actual.setLiga(null);
        Insertarhijo(nuevoPadre, actual); // reutiliza tu método, ordena automáticamente
        actual = siguiente;
    }
}

public void ancestroComun(String cedulaA, String cedulaB){
    if (raiz == null){
        JOptionPane.showMessageDialog(null, "Todavía no hay datos en el árbol.");
        return;
    }

    Nodo ac = buscarAC(raiz, cedulaA, cedulaB);

    if (ac == null){
        JOptionPane.showMessageDialog(null, "No se encontró un ancestro común (verifica las cédulas).");
    } else {
        JOptionPane.showMessageDialog(null,
                "El ancestro común más cercano es: " + ac.getNombre() + " (CC: " + ac.getCedula() + ")");
    }
}

// Devuelve el Ancestro en comun de cedulaA y cedulaB dentro del subárbol que cuelga de "actual" 
private Nodo buscarAC(Nodo primero, String cedulaA, String cedulaB){
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

            // si el otro está en el subárbol de este mismo nodo, este nodo ya es el Ancestro en Comun
            String otraCedula = esA ? cedulaB : cedulaA;
            if (actual.getSw() && contiene(representante.getLiga(), otraCedula)){
                return representante;
            }

        } else if (actual.getSw()){
            Nodo resultado = buscarAC(representante.getLiga(), cedulaA, cedulaB);
            if (resultado != null){
                cantidadEncontrados++;
                encontrado = resultado;
            }
        }

        actual = actual.getLiga();
    }

    // Si entre los hermanos de este nivel aparecieron A y B por separado, "primero" es el Ancestro en Comun visto desde arriba
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
}

// Pequeña clase auxiliar propia (no es colección, solo agrupa dos referencias)
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