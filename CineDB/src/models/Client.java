package models;

public class Client {
    private int id, id_membership;
    private String name, age, phone, email;
    
    public Client() {
        id = 0;
        name = "";
        age = "";
        phone = "";
        email = "";
        id_membership = 0;
    }
    
    // Constructor para registrar datos
    public Client(String name, String age, String phone, String email, 
            int idMembership) {
        setName(name);
        setAge(age);
        setPhone(phone);
        setEmail(email);
        setIdMembership(idMembership);
    }
    
    // Constructor para recuperar datos
    public Client(int id, String name, String age, String phone, String email, 
            int idMembership) {
        setId(id);
        setName(name);
        setAge(age);
        setPhone(phone);
        setEmail(email);
        setIdMembership(idMembership);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }                

    public int getIdMembership() {
        return id_membership;
    }

    public void setIdMembership(int idMembership) {
        this.id_membership = idMembership;
    }
    
}
