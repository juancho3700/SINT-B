package p2;

import java.io.PrintWriter;

import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class Sint62P2 extends HttpServlet {
    
    private static final String xmlPath = "/home/eetlabs.local/sint/sint62/public_html/webapps/static/";
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
        String ipClient, ipServer, browser;
        PrintWriter printWriter;

        try {
            
            ipClient = req.getRemoteAddr ();
            ipServer = req.getLocalAddr ();
            browser = req.getHeader ("User-Agent");
            printWriter = res.getWriter ();

        } catch (Exception e) {

            e.printStackTrace ();
            return;
        }

        try {

            fase = Integer.parseInt (req.getParameter ("fase"));

            switch (fase) {
    
                case 1:
    
                    System.out.println ("Fase1");
                    frontend.fase1 (printWriter, dataModel.getCountries ());
                    return;
    
    
                case 2:
                
                    System.out.println ("Fase2");
                    String pais = req.getParameter ("pais");
                    frontend.fase2 (printWriter, dataModel.getCountry (pais), dataModel.getAuthors (pais));
                    return;
    
    
                case 3:
                
                    System.out.println ("Fase3");
                    Author autor = dataModel.getAuthor (req.getParameter ("autor"));
                    frontend.fase3 (printWriter, dataModel.getCountry (autor.getIdPais ()), autor, dataModel.getBooks (autor.getId ()));
                    return;
            }

        } catch (Exception e) {

            try {
                
                frontend.fase0 (printWriter, ipClient, xmlDoc, browser, ipServer);
                
            } catch (Exception ex) {
                
                ex.printStackTrace ();
            }
        }
    }
}
