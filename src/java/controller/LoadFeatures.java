/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import entity.Age;
import entity.Condition;
import entity.Genre;
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
@WebServlet(name = "LoadFeatures", urlPatterns = {"/LoadFeatures"})
public class LoadFeatures extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Gson gson = new Gson();

            Session session = HibernateUtil.getSessionFactory().openSession();
            
            Criteria criteria1 = session.createCriteria(Genre.class);
            criteria1.addOrder(Order.asc("name"));
            List<Genre> genreList = criteria1.list();

            Criteria criteria2 = session.createCriteria(Age.class);
            criteria2.addOrder(Order.asc("id"));
            List<Age> ageList = criteria2.list();

            Criteria criteria3 = session.createCriteria(Condition.class);
            criteria3.addOrder(Order.asc("name"));
            List<Condition> conditionList = criteria3.list();

            JsonObject json = new JsonObject();
            json.add("genreList", gson.toJsonTree(genreList));
            json.add("ageList", gson.toJsonTree(ageList));
            json.add("conditionList", gson.toJsonTree(conditionList));

            resp.setContentType("application/json");
            resp.getWriter().write(gson.toJson(json));

            session.close();

//        System.out.println("LoadFeatures");
        } catch (Exception e) {
            System.out.println("Error: LoadFeatures: doGet " + new Date());
            e.printStackTrace();
        }
    }

}
