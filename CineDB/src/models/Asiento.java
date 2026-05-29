package models;

public class Asiento {
    public enum Estado {
        DISPONIBLE, ROTO, MANTENIMIENTO
    }

    private int id;
    private int numSala;
    private int fila;
    private int columna;
    private Estado estado;

    public Asiento() {}

    public Asiento(int id, int numSala, int fila, int columna, Estado estado) {
        this.id = id;
        this.numSala = numSala;
        this.fila = fila;
        this.columna = columna;
        this.estado = estado;
    }

    public int getId() {
        return id; 
    }
    public void setId(int id) {
        this.id = id; 
    }

    public int getNumSala() {
        return numSala; 
    }
    public void setNumSala(int numSala) {
        this.numSala = numSala; 
    }

    public int getFila() {
        return fila; 
    }
    public void setFila(int fila) {
        this.fila = fila; 
    }

    public int getColumna() { 
        return columna; 
    }
    public void setColumna(int columna) { 
        this.columna = columna; 
    }

    public Estado getEstado() { 
        return estado; 
    }
    public void setEstado(Estado estado) { 
        this.estado = estado; 
    }

    public String getLabel() {
        return (char)('A' + fila - 1) + String.valueOf(columna);
    }
}
