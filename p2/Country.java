package p2;

public class Country {

    private String nombre, id;

    public Country(String nombre, String id) {

        this.nombre = nombre;
        this.id = id;
    }

    public String getNombre() {

        return nombre;
    }

    public String getId() {

        return id;
    }

    public String toString() {

        return nombre;
    }
}