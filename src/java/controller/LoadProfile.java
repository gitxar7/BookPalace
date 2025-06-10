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
import entity.Address;
import entity.City;
import entity.Product;
import entity.User;
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
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Prince
 */
@WebServlet(name = "LoadProfile", urlPatterns = {"/LoadProfile"})
public class LoadProfile extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            //            Response_DTO response_DTO = new Response_DTO();
            Session session = HibernateUtil.getSessionFactory().openSession();
            Gson gson = new Gson();
            JsonObject response_json = new JsonObject();
            
            if (req.getSession().getAttribute("user") != null) {
                User_DTO user_DTO = (User_DTO) req.getSession().getAttribute("user");
                Criteria criteria2 = session.createCriteria(User.class);
                criteria2.add(Restrictions.eq("email", user_DTO.getEmail()));
                User user = (User) criteria2.uniqueResult();
                user.setPassword(null);
                user.setVerification(null);
                
                Criteria criteria1 = session.createCriteria(Product.class);
                criteria1.add(Restrictions.eq("user", user));
                List<Product> productList = criteria1.list();
                
                for (Product product : productList) {
                    product.setUser(null);
                }
                
                Criteria criteria3 = session.createCriteria(Address.class);
                criteria3.add(Restrictions.eq("user", user));
                Address address = (Address) criteria3.uniqueResult();
                
                Criteria criteria4 = session.createCriteria(City.class);
                List<City> cityList = criteria4.list();
                
                response_json.addProperty("success", true);
                response_json.add("user", gson.toJsonTree(user));
                response_json.add("address", gson.toJsonTree(address));
                response_json.add("productList", gson.toJsonTree(productList));
                response_json.add("cityList", gson.toJsonTree(cityList));
                
            }
            
            System.out.println(gson.toJson(response_json));
            resp.setContentType("application/json");
            resp.getWriter().write(gson.toJson(response_json));
            session.close();
        } catch (Exception e) {
            System.out.println("Error: LoadProfile: doGet" + new Date());
            e.printStackTrace();
        }
    }
    
}
