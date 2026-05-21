package models;

public class Sala {
    private int numSala;
    private int asientos;
    private int idFunciones;
    private boolean disponible;

    public Sala() {
        numSala = 0;
        asientos = 0;
        idFunciones = 0;
        disponible = true;
    }

    // Constructor con todos los campos (para mostrar en tabla)
    public Sala(int numSala, int asientos, int idFunciones, boolean disponible) {
        setNumSala(numSala);
        setAsientos(asientos);
        setIdFunciones(idFunciones);
        setDisponible(disponible);
    }

    public int getNumSala() { return numSala; }
    public void setNumSala(int numSala) { this.numSala = numSala; }

    public int getAsientos() { return asientos; }
    public void setAsientos(int asientos) { this.asientos = asientos; }

    public int getIdFunciones() { return idFunciones; }
    public void setIdFunciones(int idFunciones) { this.idFunciones = idFunciones; }

    public boolean isDisponible() { return disponible; }
    public void setDisponible(boolean disponible) { this.disponible = disponible; }
}