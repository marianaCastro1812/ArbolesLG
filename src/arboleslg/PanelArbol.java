package arboleslg;

import javax.swing.JPanel;
import java.awt.*;

public class PanelArbol extends JPanel {
    private Arbol arbol;

    private static final int NODE_WIDTH = 140;
    private static final int NODE_HEIGHT = 50;
    private static final int H_GAP = 40;   // espacio horizontal entre subárboles hermanos
    private static final int V_GAP = 80;   // espacio vertical entre niveles

    public PanelArbol(Arbol arbol){
        this.arbol = arbol;
        this.setBackground(Color.WHITE);
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setFont(new Font("Arial", Font.PLAIN, 12));

        Nodo raiz = arbol.getRaiz();
        if (raiz == null) return;

        // Los hijos de la raíz cuelgan directo de raiz.getLiga() (sin NodoCabeza)
        Nodo hijosRaiz = raiz.getLiga();
        int anchoHijos = (hijosRaiz != null) ? calcularAnchoFila(hijosRaiz) : 0;
        int anchoTotal = Math.max(NODE_WIDTH, anchoHijos);

        int xInicial = (this.getWidth() - anchoTotal) / 2;
        if (xInicial < 10) xInicial = 10;

        int y = 40;
        int rootX = xInicial + (anchoTotal - NODE_WIDTH) / 2;

        dibujarNodo(g2, raiz, rootX, y);

        if (hijosRaiz != null){
            int childrenXLeft = xInicial + (anchoTotal - anchoHijos) / 2;
            int rootCenterX = rootX + NODE_WIDTH / 2;
            int rootBottomY = y + NODE_HEIGHT;
            dibujarFila(g2, hijosRaiz, childrenXLeft, rootBottomY + V_GAP, rootCenterX, rootBottomY);
        }
    }

    // Calcula el ancho total que ocupará una fila de hermanos (y sus subárboles), SIN dibujar
    private int calcularAnchoFila(Nodo primero){
        int total = 0;
        Nodo actual = primero;
        while (actual != null){
            int ancho = NODE_WIDTH;
            if (actual.getSw()){
                Nodo hijos = actual.getLigaLista().getLiga();
                ancho = Math.max(NODE_WIDTH, calcularAnchoFila(hijos));
            }
            total += ancho;
            actual = actual.getLiga();
            if (actual != null) total += H_GAP;
        }
        return total;
    }

    // Dibuja una fila de hermanos, cada uno conectado INDIVIDUALMENTE al padre (fan-out), no entre sí
    private void dibujarFila(Graphics2D g2, Nodo primero, int xLeft, int y, int parentCenterX, int parentBottomY){
        int xActual = xLeft;
        Nodo actual = primero;

        while (actual != null){
            int anchoSubarbol = NODE_WIDTH;
            Nodo hijos = null;

            if (actual.getSw()){
                hijos = actual.getLigaLista().getLiga();
                anchoSubarbol = Math.max(NODE_WIDTH, calcularAnchoFila(hijos));
            }

            int nodeX = xActual + (anchoSubarbol - NODE_WIDTH) / 2;
            int centerX = nodeX + NODE_WIDTH / 2;

            // línea individual desde el centro-abajo del padre hasta el centro-arriba de ESTE hijo
            g2.setColor(Color.GRAY);
            g2.drawLine(parentCenterX, parentBottomY, centerX, y);

            dibujarNodo(g2, actual, nodeX, y);

            if (hijos != null){
                dibujarFila(g2, hijos, xActual, y + NODE_HEIGHT + V_GAP, centerX, y + NODE_HEIGHT);
            }

            xActual += anchoSubarbol + H_GAP;
            actual = actual.getLiga();
        }
    }

    private void dibujarNodo(Graphics2D g2, Nodo nodo, int x, int y){
        g2.setColor(new Color(200, 220, 255));
        g2.fillRect(x, y, NODE_WIDTH, NODE_HEIGHT);
        g2.setColor(Color.BLACK);
        g2.drawRect(x, y, NODE_WIDTH, NODE_HEIGHT);
        g2.drawString(nodo.getNombre(), x + 5, y + 15);
        g2.drawString("CC: " + nodo.getCedula(), x + 5, y + 30);
        g2.drawString(nodo.getFecha().toString(), x + 5, y + 45);
    }
}