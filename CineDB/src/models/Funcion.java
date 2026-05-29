package models;

import java.time.LocalTime;

public class Funcion {
    private int id;
    private int idPelicula;
    private String nombrePelicula;
    private int numSala;
    private LocalTime horaInicio;
    private LocalTime horaFin;

    public Funcion() {}

    public Funcion(int id, int idPelicula, String nombrePelicula, int numSala,
                   LocalTime horaInicio, LocalTime horaFin) {
        this.id = id;
        this.idPelicula = idPelicula;
        this.nombrePelicula = nombrePelicula;
        this.numSala = numSala;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
    }

    public int getId() { 
        return id; 
    }
    public void setId(int id) { 
        this.id = id; 
    }

    public int getIdPelicula() { 
        return idPelicula; 
    }
    public void setIdPelicula(int idPelicula) { 
        this.idPelicula = idPelicula; 
    }

    public String getNombrePelicula() { 
        return nombrePelicula; 
    }
    public void setNombrePelicula(String nombrePelicula) {
        this.nombrePelicula = nombrePelicula; 
    }

    public int getNumSala() { 
        return numSala; 
    }
    public void setNumSala(int numSala) {
        this.numSala = numSala; 
    }

    public LocalTime getHoraInicio() {
        return horaInicio; 
    }
    public void setHoraInicio(LocalTime horaInicio) { 
        this.horaInicio = horaInicio; 
    }

    public LocalTime getHoraFin() {
        return horaFin; 
    }
    public void setHoraFin(LocalTime horaFin) {
        this.horaFin = horaFin; 
    }
    
    public String getHorario() {
        return horaInicio.toString().substring(0, 5) + " - " + horaFin.toString().substring(0, 5);
    }
}
