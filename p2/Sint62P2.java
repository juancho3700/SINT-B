package p2;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;

import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class Sint62P2 extends HttpServlet {
    
    private static final String xmlPath = "https://manolo.webs.uvigo.gal/SINT/";
    private static final String xmlDoc = "libreria.xml";

    private DataModel dataModel;
    private FrontEnd frontend = new FrontEnd ();

    
    public void init (ServletConfig servletConfig) {

        try {
            
            dataModel = new DataModel (xmlPath + xmlDoc);

        } catch (Exception e) {
            
            e.printStackTrace ();
        }
    }

    
    public void doGet (HttpServletRequest req, HttpServletResponse res) {
        
        int fase;
        PrintWriter printWriter;

        if (req.getRequestURI ().contains ("P2Lib/")) {

            try {
                getRest (req, res);
                
            } catch (Exception e) {
                
                e.printStackTrace ();
            }

            return;
        }

        try {
            printWriter = res.getWriter ();

        } catch (Exception e) {
            
            e.printStackTrace ();
            return;
        }
        
        try {

            fase = Integer.parseInt (req.getParameter ("fase"));

            switch (fase) {
    
                case 1:
    
                    frontend.fase1 (printWriter, dataModel.getCountries ());
                    return;
    
    
                case 2:
                
                    String pais = req.getParameter ("pais");
                    frontend.fase2 (printWriter, dataModel.getCountry (pais), dataModel.getAuthors (pais));
                    return;
    
    
                case 3:
                
                    Author autor = dataModel.getAuthor (req.getParameter ("autor"));
                    frontend.fase3 (printWriter, req.getContextPath() + "static/style.css", dataModel.getCountry (autor.getPais ()), autor, dataModel.getBooks (autor.getIdentificador ()));
                    return;
            }

        } catch (Exception e) {

            try {

                String ipClient = req.getRemoteAddr ();
                String ipServer = req.getLocalAddr ();
                String browser = req.getHeader ("User-Agent");
                
                frontend.fase0 (printWriter, ipClient, xmlDoc, browser, ipServer);
                
            } catch (Exception ex) {
                
                ex.printStackTrace ();
            }
        }
    }


    private void getRest (HttpServletRequest req, HttpServletResponse res) throws Exception {

        boolean error = false;
        String reqUrl = req.getRequestURI ();
        String restRequest [] = reqUrl.substring (reqUrl.lastIndexOf ("v1") + 3, reqUrl.length ()).split ("/");
        
        JSONArray jsonA = null;
        JSONObject jsonO = new JSONObject ();
        
        res.setContentType ("application/json");
        res.setCharacterEncoding ("UTF-8");

        if (restRequest.length == 2) {

            switch (restRequest [0]) {

                case "libro":

                    Book libro = dataModel.getBook (restRequest [1]);

                    if (libro == null) {

                        error = true;
                        break;
                    }

                    jsonO.put ("titulo", libro.getTitulo ());
                    jsonO.put ("identificador", libro.getIdentificador ());
                    jsonO.put ("autor", libro.getAutor ());
                    jsonO.put ("ISBN", libro.getISBN ());
                    jsonO.put ("disponible", libro.getDisponible ());
                    
                    break;


                case "autor":

                    Author autor = dataModel.getAuthor (restRequest [1]);

                    if (autor == null) {

                        error = true;
                        break;
                    }
                    
                    jsonO.put ("nombre", autor.getNombre ());
                    jsonO.put ("nacimiento", autor.getNacimiento ());
                    jsonO.put ("identificador", autor.getIdentificador ());
                    jsonO.put ("pais", autor.getPais ());

                    break;


                case "pais":

                    Country pais = dataModel.getCountry (restRequest [1]);

                    if (pais == null) {

                        error = true;
                        break;
                    }

                    jsonO.put ("nombre", pais.getNombre ());
                    jsonO.put ("identificador", pais.getIdentificador ());

                    break;
            }

            jsonA = new JSONArray ().put (jsonO);

        } else {

            String idItem = null;

            if (restRequest.length == 3) {

                idItem = restRequest [2];
            }

            switch (restRequest [0]) {

                case "libros":

                    ArrayList <Book> libros = dataModel.getBooks (idItem);
                    Collections.sort (libros);
                    jsonA = new JSONArray (libros);
                    break;


                case "autores":

                    ArrayList <Author> autores = dataModel.getAuthors (idItem);
                    Collections.sort (autores);
                    jsonA = new JSONArray (autores);
                    break;


                case "paises":

                    ArrayList <Country> paises = dataModel.getCountries ();
                    Collections.sort (paises);
                    jsonA = new JSONArray (paises);
                    break;
            }
        }

        res.getWriter ().println (jsonA.toString ());

        if (error) {

            res.setStatus (404);

        } else {

            res.setStatus (200);
        }
    }
}