/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pena.sandra.buscame.web.servlet;

import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.pena.sandra.buscame.rules.DocumentParser;

/**
 *
 * @author sandra
 */
@WebServlet(name = "IndexerServlet", urlPatterns = {"/IndexerServlet"})
public class IndexerServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String destination;
        try {
            String dirPath = request.getParameter("directory-to-index");
            dirPath = "/home/javier/src/sandra/buscame/DocumentosTP2";
            DateTime start = DateTime.now();
            DocumentParser documentParser = new DocumentParser();
            String[] files = request.getParameterValues("files-to-index");
            System.out.println("=============ssss===================");
            System.out.println(files.length);
            
            documentParser.parseFiles(dirPath, files);
            List<double[]> tfIdf= documentParser.tfIdfCalculator();
            
            for (double[] list : tfIdf) {
                System.out.println("-------------");
                StringBuilder sb = new StringBuilder();
                for (double item : list) {
                    sb.append(String.format("%f ", item));
                }
                System.out.println(sb.toString());
                System.out.println("-------------");
            }
            
            System.out.println("================================");
            
            DateTime end = DateTime.now();
            Duration duration = new Duration(start, end);
            request.setAttribute("duration", duration.getStandardSeconds());
            //request.setAttribute("tfIdf", tfIdf);
            //request.setAttribute("tfIdfSize", tfIdf.size());
            destination = "/indexer.jsp";
        } catch (Exception ex) {
            // Aca redireccionar a la pagina de error
           request.setAttribute("error", ex.toString());
           destination = "/error.jsp";
        }
        ServletContext app = this.getServletContext();
        RequestDispatcher disp = app.getRequestDispatcher(destination);
        disp.forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
