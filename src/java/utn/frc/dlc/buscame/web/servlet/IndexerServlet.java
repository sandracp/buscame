/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utn.frc.dlc.buscame.web.servlet;

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
import utn.frc.dlc.buscame.db.IndexerDB;
import utn.frc.dlc.buscame.rules.Indexer;

/**
 *
 * @author Peña - Ligorria
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
            DateTime start = DateTime.now();
            Indexer indexer = new Indexer();
            String[] files = request.getParameterValues("files-to-index");
            indexer.parseFiles(dirPath, files);
            DateTime end = DateTime.now();
            Duration duration = new Duration(start, end);
            request.setAttribute("duration", duration.getStandardSeconds());
            request.setAttribute("vocabulary", IndexerDB.getInstance().allVocabulary.size());
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
