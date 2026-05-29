package models;

public class Sala {
    private int numSala;
    private int asientos;    
    private boolean disponible;

    public Sala() {
        numSala = 0;
        asientos = 0;        
        disponible = true;
    }
    
    public Sala(int numSala, int asientos, boolean disponible) {
        setNumSala(numSala);
        setAsientos(asientos);        
        setDisponible(disponible);
    }

    public int getNumSala() { 
        return numSala; 
    }
    public void setNumSala(int numSala) { 
        this.numSala = numSala; 
    }

    public int getAsientos() {
        return asientos; 
    }
    public void setAsientos(int asientos) {
        this.asientos = asientos; 
    }   

    public boolean isDisponible() {
        return disponible; 
    }
    public void setDisponible(boolean disponible) {
        this.disponible = disponible; 
    }
}