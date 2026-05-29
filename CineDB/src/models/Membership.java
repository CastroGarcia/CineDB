package models;

public class Membership {
    private int id;
    private String tipo_membresia;
    
    public Membership() {
        id = 0;
        tipo_membresia = "";
    }
        
    public Membership(int id, String membership) {
        setId(id);
        setTipo_membresia(membership);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipo_membresia() {
        return tipo_membresia;
    }

    public void setTipo_membresia(String tipo_membresia) {
        this.tipo_membresia = tipo_membresia;
    }
    
    
}
