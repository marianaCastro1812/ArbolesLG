package arboleslg;

import java.time.LocalDate;

public class Nodo {
    private boolean sw;
    private Nodo LigaLista;
    private String nombre;
    private String cedula;
    private LocalDate fecha;
    private Nodo Liga;

    public Nodo(String nombre, String cedula, LocalDate fecha) {
        this.nombre = nombre;
        this.cedula = cedula;
        this.fecha = fecha;
        this.Liga= null;
    }

    public boolean isSw() {
        return sw;
    }

    public void setSw(boolean sw) {
        this.sw = sw;
    }

    public Nodo getLigaLista() {
        return LigaLista;
    }

    public void setLigaLista(Nodo LigaLista) {
        this.LigaLista = LigaLista;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Nodo getLiga() {
        return Liga;
    }

    public void setLiga(Nodo Liga) {
        this.Liga = Liga;
    }
    
    
}
