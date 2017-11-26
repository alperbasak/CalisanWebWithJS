package servlet;

import model.Calisan;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "imageServlet")
public class ImageWebService extends javax.servlet.http.HttpServlet {

    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String employeeID=request.getParameter("id");
        Calisan calisan=entityManagerFactory.createEntityManager().find(Calisan.class,employeeID);
        if(calisan!=null){
            response.setContentLength(calisan.getGorsel().length);
            response.setContentType(calisan.getGorselTuru());
            response.getOutputStream().write(calisan.getGorsel());
        }

    }
}
