package p2;

public class Book implements Comparable <Book> {

    private String titulo, isbn, identificador, autor, disponible;

    public Book (String titulo, String identificador, String autor, String isbn) {

        this.titulo = titulo;
        this.identificador = identificador;
        this.autor = autor;
        this.isbn = isbn;
    }


    public void setDisponible (String disponible) {

        if (disponible.toLowerCase ().equals ("yes")) {

            this.disponible = "yes";
            return;
        } 

        this.disponible = "no";
    }


    public String getTitulo () {

        return titulo;
    }


    public String getDisponible () {

        return disponible;
    }


    public String getIdentificador () {

        return identificador;
    }


    public String getAutor () {

        return autor;
    }

    
    public String getISBN () {

        return isbn;
    }


    @Override
    public int compareTo (Book libro) {

        if (libro.getIdentificador ().compareTo (this.identificador) > 0) {

            return -1;

        } else {

            return 1;
        }
    }
}