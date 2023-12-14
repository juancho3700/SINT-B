package p2;

import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;


public class DataModel {

    public static final String JAXP_LANGUAGE = "http://java.sun.com/xml/jaxp/properties/schemaLanguage";
    private static final String SCHEMA = "http://www.w3.org/2001/XMLSchema";

    private ArrayList <Country> paises = new ArrayList <Country> ();
    private ArrayList <Author> autores = new ArrayList <Author> ();
    private ArrayList <Book> libros = new ArrayList <Book> ();

    public DataModel (String xmlDoc) {

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance ();
        dbf.setValidating (true);
        dbf.setNamespaceAware (true);
        dbf.setAttribute (JAXP_LANGUAGE, SCHEMA);

        try {

            createData (xmlDoc, dbf.newDocumentBuilder ());

        } catch (Exception e) {

            e.printStackTrace ();
        }
    }

    public void createData (String xmlDoc, DocumentBuilder db) throws Exception {

        Document doc;
        NodeList nlDoc;
        Element element;

        String nombre, id, fechaNacimiento, idPais, isbn, disponible;

        doc = db.parse (new InputSource (new URL (xmlDoc).openStream ()));
        doc.getDocumentElement ().normalize ();

        nlDoc = doc.getElementsByTagName ("pais");

        for (int i = 0; i < nlDoc.getLength (); i++) {

            element = (Element) nlDoc.item (i);

            id = element.getAttribute ("identificador").trim ();
            nombre = element.getTextContent ().trim ();

            paises.add (new Country (nombre, id));
        }

        nlDoc = doc.getElementsByTagName ("autor");

        for (int i = 0; i < nlDoc.getLength (); i++) {

            element = (Element) nlDoc.item (i);

            id = element.getAttribute ("identificador").trim ();
            fechaNacimiento = element.getAttribute ("nacimiento").trim ();
            idPais = element.getAttribute ("pais").trim ();
            nombre = element.getTextContent ().trim ();

            autores.add (new Author (nombre, id, fechaNacimiento, idPais));
        }

        nlDoc = doc.getElementsByTagName ("libro");

        for (int i = 0; i < nlDoc.getLength (); i++) {

            element = (Element) nlDoc.item (i);

            id = element.getAttribute ("identificador").trim ();
            isbn = element.getAttribute ("ISBN").trim ();
            idPais = element.getAttribute ("autor").trim ();
            nombre = element.getTextContent ().replaceAll ("\\s+", " ").trim ();

            libros.add (new Book (nombre, id, idPais, isbn));

            try {
                disponible = element.getAttribute ("disponible").trim ();
                libros.get (libros.size () - 1).setDisponible (disponible);

            } catch (Exception e) {

                libros.get (libros.size () - 1).setDisponible ("");

            }
        }
    }


    public ArrayList <Author> getAuthors (String countryId) {

        if (countryId == null) {

            return autores;
        }

        ArrayList <Author> autoresPais = new ArrayList <Author> ();

        for (Author autor : autores) {

            if (autor.getPais ().equals (countryId)) {

                autoresPais.add (autor);
            }
        }

        return autoresPais;
    }


    public ArrayList <Book> getBooks (String authorId) {

        if (authorId == null) {

            return libros;
        }

        ArrayList <Book> librosAutor = new ArrayList <Book> ();

        for (Book libro : libros) {

            if (libro.getAutor ().equals (authorId)) {

                librosAutor.add (libro);
            }
        }

        return librosAutor;
    }


    public ArrayList <Country> getCountries () {

        return paises;
    }


    public Author getAuthor (String authorId) {

        for (Author autor : autores) {

            if (autor.getIdentificador ().equals (authorId)) {

                return autor;
            }
        }

        return null;
    }


    public Country getCountry (String countryId) {

        for (Country pais : paises) {

            if (pais.getIdentificador ().equals (countryId)) {

                return pais;
            }
        }

        return null;
    }

    
    public Book getBook (String bookId) {

        for (Book libro : libros) {

            if (libro.getIdentificador ().equals (bookId)) {

                return libro;
            }
        }

        return null;
    }


    public void printAll () {

        System.out.println ("\n----- PAISES -----\n");

        for (Country pais : paises) {

            System.out.println (pais.getNombre () + " - " + pais.getIdentificador ());
        }

        System.out.println ("\n\n----- AUTORES -----");

        for (Author autor : autores) {

            System.out.println (autor.getNombre () + " (" + autor.getIdentificador () + "): Nacido en " + autor.getNacimiento () + ", " + autor.getPais ());
        }

        System.out.println ("\n\n----- LIBROS -----");

        for (Book libro : libros) {

            System.out.println (libro.getTitulo () + " (" + libro.getIdentificador () + "): Escrito por " + libro.getAutor () + ", " + libro.getISBN ());
        }
    }
}