package p2;

public class Country implements Comparable <Country> {

    private String nombre, identificador;

    public Country (String nombre, String identificador) {

        this.nombre = nombre;
        this.identificador = identificador;
    }

    public String getNombre () {

        return nombre;
    }

    public String getIdentificador () {

        return identificador;
    }


    @Override
    public int compareTo (Country pais) {

        if (pais.getIdentificador ().compareTo (this.identificador) > 0) {

            return -1;

        } else {

            return 1;
        }
    }
}