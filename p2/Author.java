package p2;

public class Author implements Comparable <Author> {

    private String identificador, pais, nombre, nacimiento;

    public Author (String nombre, String identificador, String nacimiento, String pais) {

        this.nombre = nombre;
        this.identificador = identificador;
        this.nacimiento = nacimiento;
        this.pais = pais;
    }


    public String getNombre () {

        return nombre;
    }


    public String getIdentificador () {

        return identificador;
    }


    public String getNacimiento () {

        return nacimiento;
    }


    public String getPais () {

        return pais;
    }
    

    @Override
    public int compareTo (Author autor) {

        if (autor.getIdentificador ().compareTo (this.identificador) > 0) {

            return -1;

        } else {

            return 1;
        }
    }
}