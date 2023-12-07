package p2;

import java.util.ArrayList;
import java.io.PrintWriter;

public class FrontEnd {

    public void fase0 (PrintWriter out, String xmlDocument, String ipClient, String browser, String ipServer) throws Exception {

        out.println ("<html>");
        out.println ("<head>");
        out.println ("<meta charset = 'UTF-8'/>");
        out.println ("</head>");

        out.println ("<body>");
        out.println ("<h1> -------------------------------------------------------------- </h1>");
        out.println ("<h1> ----- SERVICIO DE CONSULTA DE LIBROS ----- </h1>");
        out.println ("<h1> -------------------------------------------------------------- </h1>");

        out.println ("<h4>Fichero procesado: " + xmlDocument + "</h4>");
        out.println ("<h4>IP del cliente: " + ipClient + "</h4>");
        out.println ("<h4>Navegador del cliente: " + browser + "</h4>");
        out.println ("<h4>IP del servidor: " + ipServer + "</h4>");

        out.println ("<br>");
        out.println ("<a href='?fase=1'><input type=\"button\" value=\"Avanzar\"></a>");

        out.println ("<br>");
        out.println ("<div class = 'izquierda' id = 'pie_pagina'> Juan Anselmo López Gómez </div>");
        out.println ("</body>");
        out.println ("</html>");
    }


    public void fase1 (PrintWriter out, ArrayList <Country> paises) throws Exception {

        out.println ("<html>");
        out.println ("<head>");
        out.println ("<meta charset = 'UTF-8'/>");
        out.println ("</head>");
 
        out.println ("<body>");
        out.println ("<h1> -------------------------------------------------------------- </h1>");
        out.println ("<h1> ----- SERVICIO DE CONSULTA DE LIBROS ----- </h1>");
        out.println ("<h1> -------------------------------------------------------------- </h1>");
 
        out.println ("<h2>Fase 1</h2>");
        out.println ("<h4>Seleccione el país:</h4>");

        for (Country pais : paises) {

            out.println ("<li><a href='fase=2&pais=" + pais.getId () + "''>" + pais.getNombre () + "</a></li>");
        }

        out.println ("<br>");
        out.println ("<a href='?'><input type=\"button\" value=\"Anterior\"></a>");

        out.println ("<br>");
        out.println ("<div class = 'izquierda' id = 'pie_pagina'> Juan Anselmo López Gómez </div>");
        out.println ("</body>");
        out.println ("</html>");
    }


    public void fase2 (PrintWriter out, Country pais, ArrayList <Author> autores) throws Exception {

        out.println ("<html>");
        out.println ("<head>");
        out.println ("<meta charset = 'UTF-8'/>");
        out.println ("</head>");
 
        out.println ("<body>");
        out.println ("<h1> -------------------------------------------------------------- </h1>");
        out.println ("<h1> ----- SERVICIO DE CONSULTA DE LIBROS ----- </h1>");
        out.println ("<h1> -------------------------------------------------------------- </h1>");
 
        out.println ("<h2>Fase 2</h2>");
        out.println ("<h4>Consultando información del país: " + pais.getNombre () + "</h4>");

        for (Author autor : autores) {

            out.println ("<li><a href='fase=2&autor=" + autor.getId () + "'>" + autor.getNombre () + "</a> Nacido en " + autor.getFechaNacimiento () + "</li>");
        }

        out.println ("<br>");
        out.println ("<a href='?fase=1'><input type=\"button\" value=\"Anterior\"></a>");
 
        out.println ("<br>");
        out.println ("<div class = 'izquierda' id = 'pie_pagina'> Juan Anselmo López Gómez </div>");
        out.println ("</body>");
        out.println ("</html>");
    }


    public void fase3 (PrintWriter out, Country pais, Author autor, ArrayList <Book> libros) throws Exception {

        out.println ("<html>");
        out.println ("<head>");
        out.println ("<meta charset = 'UTF-8'/>");
        out.println ("</head>");
 
        out.println ("<body>");
        out.println ("<h1> -------------------------------------------------------------- </h1>");
        out.println ("<h1> ----- SERVICIO DE CONSULTA DE LIBROS ----- </h1>");
        out.println ("<h1> -------------------------------------------------------------- </h1>");
 
        out.println ("<h2>Fase 2</h2>");
        out.println ("<h4>Consultando información del país: " + pais.getNombre () + "</h4>");
        out.println ("<h4>Consultando información de autor: " + autor.getNombre () + "</h4>");

        for (Book libro : libros) {

            if (libro.getDisponible ()) {

                out.println ("<li><a href=''>" + autor.getNombre () + "</a> ISBN: " + libro.getisbn () + "</li>");

            } else {

                out.println ("<li><a href='' style='color: red;'>" + autor.getNombre () + "</a> " + autor + "</li>");
            }
        }

        out.println ("<br>");
        out.println ("<a href='?fase=2&pais=" + pais.getId () + "'><input type=\"button\" value=\"Anterior\"></a>");
 
        out.println ("<br>");
        out.println ("<div class = 'izquierda' id = 'pie_pagina'> Juan Anselmo López Gómez </div>");
        out.println ("</body>");
        out.println ("</html>");
    }
}