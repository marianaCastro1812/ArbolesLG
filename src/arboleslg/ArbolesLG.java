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
        int categoria;

        do {
            String[] categorias = {
                "1. Gestión de Personas",
                "2. Consultas de Relaciones Familiares",
                "3. Consultas Estructurales y Visualización",
                "4. Otras operaciones",
                "Salir"
            };

            categoria = JOptionPane.showOptionDialog(
                null,
                "Selecciona una categoría:",
                "Menú - Árbol Genealógico",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                categorias,
                categorias[0]
            );

            switch (categoria) {
                case 0: menuGestionPersonas(arbol); break;
                case 1: menuRelacionesFamiliares(arbol); break;
                case 2: menuEstructural(arbol); break;
                case 3: menuOtrasOperaciones(arbol); break;
                case 4:
                case -1:
                    JOptionPane.showMessageDialog(null, "Saliendo...");
                    break;
            }

        } while (categoria != 4 && categoria != -1);
    }

    // ---------- 1. GESTIÓN DE PERSONAS ----------
    private static void menuGestionPersonas(Arbol arbol){
        String[] opciones = {"Registrar", "Eliminar (Conservando linaje)", "Actualizar", "Volver"};
        int op;
        do {
            op = JOptionPane.showOptionDialog(
                null, "Gestión de Personas:", "Gestión de Personas",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, opciones, opciones[0]
            );

            switch (op){
                case 0:
                    arbol.Insertar();
                    break;
                case 1:
                    if (arbol.getRaiz() == null){
                        JOptionPane.showMessageDialog(null, "Todavía no hay datos en el árbol.");
                    } else {
                        String cedula = JOptionPane.showInputDialog("Cédula de la persona a eliminar:");
                        if (cedula != null && !cedula.isBlank()){
                            arbol.eliminar(arbol.getRaiz(), cedula);
                        }
                    }
                    break;
                case 2:
                    if (arbol.getRaiz() == null){
                        JOptionPane.showMessageDialog(null, "Todavía no hay datos en el árbol.");
                    } else {
                        String cedulaAct = JOptionPane.showInputDialog("Cédula de la persona a actualizar:");
                        if (cedulaAct != null && !cedulaAct.isBlank()){
                            arbol.actualizar(cedulaAct);
                        }
                    }
                    break;
            }
        } while (op != 3 && op != -1);
    }

    // ---------- 2. CONSULTAS DE RELACIONES FAMILIARES ----------
    private static void menuRelacionesFamiliares(Arbol arbol) {
        String[] opciones = {
            "Padre", "Hijos", "Hermanos", "Tíos",
            "Sobrinos", "Primos", "Ancestros", "Descendientes", "Volver"
        };

        int op;

        do {
            op = JOptionPane.showOptionDialog(
                    null,
                    "Consultas de Relaciones Familiares:",
                    "Relaciones Familiares",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    opciones,
                    opciones[0]
            );

            if (op >= 0 && op <= 7) {
                String cedula = JOptionPane.showInputDialog(
                        "Cédula de la persona:"
                );

                if (cedula == null || cedula.isBlank()) {
                    continue;
                }

                switch (op) {
                    case 0:
                        arbol.mostrarPadre(cedula);
                        break;

                    case 1:
                        arbol.mostrarHijos(cedula);
                        break;

                    case 2:
                        arbol.mostrarHermanos(cedula);
                        break;

                    case 3:
                        arbol.mostrarTios(cedula);
                        break;

                    case 4:
                        arbol.mostrarSobrinos(cedula);
                        break;

                    case 5:
                        arbol.mostrarPrimos(cedula);
                        break;

                    case 6:
                        arbol.mostrarAncestros(cedula);
                        break;

                    case 7:
                        arbol.mostrarDescendientes(cedula);
                        break;
                }
            }

        } while (op != 8 && op != -1);
    }
    // ---------- 3. CONSULTAS ESTRUCTURALES Y VISUALIZACIÓN ----------
private static void menuEstructural(Arbol arbol){
    String[] opciones = {
        "Visualizar Árbol",
        "Nodo con Mayor Grado",
        "Familiar más joven",
        "Altura del Árbol",
        "Nivel de un Registro",
        "Registros por Nivel",
        "Nodo con Mayor Nivel",
        "Volver"
    };

    int op;

    do {
        op = JOptionPane.showOptionDialog(
            null,
            "Consultas Estructurales y Visualización:",
            "Consultas Estructurales",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.PLAIN_MESSAGE,
            null,
            opciones,
            opciones[0]
        );

        if (op >= 0 && op <= 6){

            switch (op){

                case 0:
                    arbol.verArbol();
                    break;

                case 1:
                    arbol.mayorGrado();
                    break;

                case 2:
                    arbol.familiarMasJoven();
                    break;

                case 3:
                    arbol.alturaArbol();
                    break;

                case 4:
                    String cedula = JOptionPane.showInputDialog(
                            "Ingrese la cédula de la persona:"
                    );

                    if (cedula != null && !cedula.isBlank()){
                        arbol.nivelRegistro(cedula);
                    }
                    break;

                case 5:
                    String nivelTexto = JOptionPane.showInputDialog(
                            "Ingrese el nivel que desea consultar:"
                    );

                    if (nivelTexto != null && !nivelTexto.isBlank()){
                        try {
                            int nivel = Integer.parseInt(nivelTexto);
                            arbol.registrosPorNivel(nivel);
                        } catch (NumberFormatException e){
                            JOptionPane.showMessageDialog(
                                    null,
                                    "Debe ingresar un número entero."
                            );
                        }
                    }
                    break;

                case 6:
                    arbol.nodoMayorNivel();
                    break;
            }
        }

    } while (op != 7 && op != -1);
}

    // ---------- 4. OTRAS OPERACIONES ----------
    private static void menuOtrasOperaciones(Arbol arbol){
        String[] opciones = {
            "Eliminar Nivel", "Ancestro Común Más Cercano", "Trasladar Rama (Adopción)", "Volver"
        };
        int op;
        do {
            op = JOptionPane.showOptionDialog(
                null, "Otras Operaciones:", "Otras Operaciones",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, opciones, opciones[0]
            );

            switch (op){
                case 0:
                 
                    String nivelTexto = JOptionPane.showInputDialog("Número de generación a eliminar:");
                    if (nivelTexto != null && !nivelTexto.isBlank()) {
                        try {
                            int nivel = Integer.parseInt(nivelTexto);
                            arbol.eliminarNivel(nivel);
                            JOptionPane.showMessageDialog(null, "Generación eliminada correctamente.");
                        } catch (NumberFormatException e) {
                            JOptionPane.showMessageDialog(null, "Debes ingresar un número válido.");
                        }
                    }
                    break;
               
            case 1:
                String ced1 = JOptionPane.showInputDialog("Cédula de la persona A:");
                String ced2 = JOptionPane.showInputDialog("Cédula de la persona B:");
                if (ced1 != null && ced2 != null && !ced1.isBlank() && !ced2.isBlank()){
                    arbol.ancestroComun(ced1, ced2);
                }
                break;
                case 2:
                    String cedA = JOptionPane.showInputDialog("Cédula de la persona A (a trasladar):");
                    String cedB = JOptionPane.showInputDialog("Cédula de la persona B (nuevo padre):");
                    if (cedA != null && cedB != null && !cedA.isBlank() && !cedB.isBlank()) {
                        arbol.trasladarRama(cedA, cedB);
                    }
    break;
            }
        } while (op != 3 && op != -1);
    }
    
}    

  
//Hacer Menu     

