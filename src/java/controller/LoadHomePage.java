/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import entity.Age;
import entity.Genre;
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
@WebServlet(name = "LoadHomePage", urlPatterns = {"/LoadHomePage"})
public class LoadHomePage extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {

            Gson gson = new Gson();

            Session session = HibernateUtil.getSessionFactory().openSession();
            
            Criteria criteria1 = session.createCriteria(Genre.class);
            criteria1.addOrder(Order.asc("name"));
            criteria1.setMaxResults(5);
            List<Genre> genreList = criteria1.list();

            Criteria criteria2 = session.createCriteria(Age.class);
            criteria2.addOrder(Order.asc("id"));
            criteria2.setMaxResults(5);
            List<Age> ageList = criteria2.list();

            Criteria criteria3 = session.createCriteria(Product.class);
            criteria2.addOrder(Order.desc("id"));
            criteria3.setMaxResults(5);
            List<Product> productList = criteria3.list();
            for (Product product : productList) {
                product.setUser(null);

            }

            JsonObject json = new JsonObject();
            json.add("genreList", gson.toJsonTree(genreList));
            json.add("ageList", gson.toJsonTree(ageList));
            json.add("productList", gson.toJsonTree(productList));

            resp.setContentType("application/json");
            resp.getWriter().write(gson.toJson(json));

            session.close();

//            System.out.println("home page");
        } catch (Exception e) {
            System.out.println("Error: LoadHomePage: doGet" + new Date());
            e.printStackTrace();
        }
    }

}
