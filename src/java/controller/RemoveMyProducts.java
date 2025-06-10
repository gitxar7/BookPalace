/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import com.google.gson.Gson;
import dto.Response_DTO;
import entity.Product;
import java.io.IOException;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.HibernateUtil;
import model.Validations;
import org.hibernate.Session;


/**
 *
 * @author Prince
 */
@WebServlet(name = "RemoveMyProducts", urlPatterns = {"/RemoveMyProducts"})
public class RemoveMyProducts extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Gson gson = new Gson();
            Session session = HibernateUtil.getSessionFactory().openSession();
            Response_DTO response_DTO = new Response_DTO();

            String productId = req.getParameter("pid");
            
//            System.out.println(productId);

            if (Validations.isInteger(productId)) {
                Product product = (Product) session.load(Product.class, Integer.valueOf(productId));
                String p_name = product.getTitle();
                session.delete(product);
                session.beginTransaction().commit();
                session.close();
                response_DTO.setContent(p_name + " has been successfully Deleted.");
                response_DTO.setSuccess(true);
            } else {
            }

            resp.setContentType("application/json");
            resp.getWriter().write(gson.toJson(response_DTO));
        } catch (Exception e) {
            System.out.println("Error: RemoveMyProducts: doGet" + new Date());
            e.printStackTrace();
        }
    }

}
