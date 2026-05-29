package models;

public class Client {
    private String curp;
    private int id_membership;
    private String name, age, phone, email;

    public Client() {
        curp = "";
        name = "";
        age  = "";
        phone = "";
        email = "";
        id_membership = 0;
    }

    // Constructor para registrar datos (incluye CURP)
    public Client(String curp, String name, String age, String phone, String email,
            int idMembership) {
        setCurp(curp);
        setName(name);
        setAge(age);
        setPhone(phone);
        setEmail(email);
        setIdMembership(idMembership);
    }

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp == null ? "" : curp.toUpperCase().trim();
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