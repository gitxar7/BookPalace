/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import dto.Response_DTO;
import dto.User_DTO;
import entity.Product;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.HibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

/**
 *
 * @author Prince
 */
@WebServlet(name = "CheckSignIn", urlPatterns = {"/CheckSignIn"})
public class CheckSignIn extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {

            Response_DTO response_DTO = new Response_DTO();
            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
            
            if (req.getSession().getAttribute("user") != null) {
                User_DTO user_DTO = (User_DTO) req.getSession().getAttribute("user");
                response_DTO.setSuccess(true);
                response_DTO.setContent(user_DTO.getName());
            }

            resp.setContentType("application/json");
            resp.getWriter().write(gson.toJson(response_DTO));
        } catch (Exception e) {
            System.out.println("Error: CheckSignIn: doGet" + new Date());
            e.printStackTrace();
        }
    }

}
