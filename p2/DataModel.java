package p2;

import java.io.FileInputStream;
import java.io.File;
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

        ArrayList <String> datosClase = new ArrayList <String >();

        doc = db.parse (new InputSource (new FileInputStream (new File (xmlDoc))));
        doc.getDocumentElement ().normalize ();

        nlDoc = doc.getElementsByTagName ("pais");

        for (int i = 0; i < nlDoc.getLength (); i++) {

            datosClase.clear ();
            element = (Element) nlDoc.item (i);

            datosClase.add (element.getAttribute ("identificador").trim ());
            datosClase.add (element.getTextContent ().trim ());

            paises.add (new Country (datosClase.get (1), datosClase.get (0)));
        }

        nlDoc = doc.getElementsByTagName ("autor");

        for (int i = 0; i < nlDoc.getLength (); i++) {

            datosClase.clear ();
            element = (Element) nlDoc.item (i);

            datosClase.add (element.getAttribute ("identificador").trim ());
            datosClase.add (element.getAttribute ("nacimiento").trim ());
            datosClase.add (element.getAttribute ("pais").trim ());
            datosClase.add (element.getTextContent ().trim ());

            autores.add (new Author (datosClase.get (3), datosClase.get (0), datosClase.get (1), datosClase.get (2)));
        }

        nlDoc = doc.getElementsByTagName ("libro");

        for (int i = 0; i < nlDoc.getLength (); i++) {

            datosClase.clear ();
            element = (Element) nlDoc.item (i);

            datosClase.add (element.getAttribute ("identificador").trim ());
            datosClase.add (element.getAttribute ("ISBN").trim ());
            datosClase.add (element.getAttribute ("autor").trim ());
            datosClase.add (element.getTextContent ().replaceAll ("\\s+", " ").trim ());

            libros.add (new Book (datosClase.get (3), datosClase.get (0), datosClase.get (1), datosClase.get (2)));

            try {
                datosClase.add (element.getAttribute ("disponible").trim ());
                libros.get (libros.size () - 1).setDisponible (datosClase.get (4));

            } catch (Exception e) {}
        }
    }


    public ArrayList <Author> getAuthors (String countryId) {

        ArrayList <Author> autoresPais = new ArrayList <Author> ();

        for (Author autor : autores) {

            if (autor.getIdPais ().equals (countryId)) {

                autoresPais.add (autor);
            }
        }

        return autoresPais;
    }


    public ArrayList <Book> getBooks (String authorId) {

        ArrayList <Book> librosAutor = new ArrayList <Book> ();

        for (Book libro : libros) {

            if (libro.getIdAutor ().equals (authorId)) {

                librosAutor.add (libro);
            }
        }

        return librosAutor;
    }


    public ArrayList <Country> getCountries () {

        System.out.println ("Printeo los paises");
        System.out.println (paises);
        return paises;
    }


    public Author getAuthor (String authorId) {

        for (Author autor : autores) {

            if (autor.getId ().equals (authorId)) {

                return autor;
            }
        }

        return (new Author ("", "", "", ""));
    }


    public Country getCountry (String countryId) {

        for (Country pais : paises) {

            if (pais.getId ().equals (countryId)) {

                return pais;
            }
        }

        return (new Country ("", ""));
    }

    
    public Book getBook (String bookId) {

        for (Book libro : libros) {

            if (libro.getId ().equals (bookId)) {

                return libro;
            }
        }

        return (new Book ("", "", "", ""));
    }
}