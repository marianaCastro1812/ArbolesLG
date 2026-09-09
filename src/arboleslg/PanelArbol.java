package arboleslg;

import javax.swing.JPanel;
import java.awt.*;

public class PanelArbol extends JPanel {
    private Arbol arbol;

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

        if (arbol.getRaiz() != null){
            dibujarNivel(g2, arbol.getRaiz(), 50, 50);
        }
    }

    // dibuja una "fila" de hermanos y, si alguno tiene sw=true, baja recursivamente
   private int dibujarNivel(Graphics2D g2, Nodo primero, int x, int y){
    int xActual = x;
    Nodo actual = primero;

    while (actual != null){
        // dibujar el nodo actual como un rectángulo
        g2.setColor(new Color(200, 220, 255));
        g2.fillRect(xActual, y, 140, 50);
        g2.setColor(Color.BLACK);
        g2.drawRect(xActual, y, 140, 50);
        g2.drawString(actual.getNombre(), xActual + 5, y + 15);
        g2.drawString("CC: " + actual.getCedula(), xActual + 5, y + 30);
        g2.drawString(actual.getFecha().toString(), xActual + 5, y + 45);

        int anchoUsado = 140;

        if (actual.getSw()){
            // NO dibujamos el nodo cabeza (actual.getLigaLista()),
            // saltamos directo a su primer hijo real
            Nodo primerHijoReal = actual.getLigaLista().getLiga();

            int xHijos = xActual;
            int yHijos = y + 100;
            int anchoHijos = dibujarNivel(g2, primerHijoReal, xHijos, yHijos);

            // línea conectando el padre con la fila de hijos (como árbol, hacia abajo)
            g2.drawLine(xActual + 70, y + 50, xActual + 70, yHijos);

            anchoUsado = Math.max(anchoUsado, anchoHijos);
        }

        xActual += anchoUsado + 40;

        if (actual.getLiga() != null){
            g2.drawLine(xActual - 40, y + 25, xActual, y + 25);
        }

        actual = actual.getLiga();
    }

    return xActual - x;
}
}