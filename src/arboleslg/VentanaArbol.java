package arboleslg;

import javax.swing.JScrollPane;
import java.awt.Dimension;
import javax.swing.JScrollBar;
import javax.swing.SwingUtilities;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author Mari♥
 */
public class VentanaArbol extends javax.swing.JFrame {
        Arbol arbol1;
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(VentanaArbol.class.getName());

    /**
     * Creates new form VentanaArbol
     */
   

    public VentanaArbol(Arbol arbol) {
        initComponents();
        this.arbol1 = arbol;

        PanelArbol panel = new PanelArbol(arbol);
        panel.setPreferredSize(new Dimension(3000, 2000));

        JScrollPane scroll = new JScrollPane(panel);
        this.setContentPane(scroll);
        this.setSize(1600, 900);
        this.setLocationRelativeTo(null);

        // Centrar el scroll horizontal una vez la ventana ya se muestre
        SwingUtilities.invokeLater(() -> {
            JScrollBar hBar = scroll.getHorizontalScrollBar();
            hBar.setValue((hBar.getMaximum() - hBar.getVisibleAmount()) / 2);
        });
    }

    public void actualizar(){
        this.repaint();
    }

    // initComponents(), main(), etc. — el resto de tu clase queda igual

   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
