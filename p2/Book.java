package p2;

public class Book {

    private String nombre, isbn, id, idAutor;
    private boolean disponible;

    public Book (String nombre, String id, String idAutor, String isbn) {

        this.nombre = nombre;
        this.id = id;
        this.idAutor = idAutor;
        this.isbn = isbn;
    }

    public void setDisponible (String disponible) {

        if (disponible.toLowerCase ().equals ("yes")) {

            this.disponible = true;

        } else if (disponible.toLowerCase ().equals ("no")) {

            this.disponible = false;
        }
    }

    public String getNombre () {

        return nombre;
    }

    public boolean getDisponible () {

        return disponible;
    }

    public String getId () {

        return id;
    }

    public String getIdAutor () {

        return idAutor;
    }

    public String getisbn () {

        return isbn;
    }

    public String toString () {

        return "ISBN: " + isbn;
    }
}