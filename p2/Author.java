package p2;

public class Author {

    private String id, idPais, nombre, fechaNacimiento;

    public Author(String nombre, String id, String fechaNacimiento, String idPais) {

        this.nombre = nombre;
        this.id = id;
        this.fechaNacimiento = fechaNacimiento;
        this.idPais = idPais;
    }

    public String getNombre() {

        return nombre;
    }

    public String getId() {

        return id;
    }

    public String getFechaNacimiento() {

        return fechaNacimiento;
    }

    public String getIdPais() {

        return idPais;
    }

    public String toString() {

        return "Nacido en " + fechaNacimiento;
    }
}