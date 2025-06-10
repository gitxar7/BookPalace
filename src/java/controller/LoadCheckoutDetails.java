/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dto.Response_DTO;
import dto.User_DTO;
import entity.Address;
import entity.Age;
import entity.Cart;
import entity.City;
import entity.Condition;
import entity.Genre;
import entity.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.HibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Prince
 */
@WebServlet(name = "LoadCheckoutDetails", urlPatterns = {"/LoadCheckoutDetails"})
public class LoadCheckoutDetails extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
//            System.out.println("LoadCheckoutDetails: doGet");
            Gson gson = new Gson();
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("success", false);

            HttpSession httpSession = req.getSession();
            Session session = HibernateUtil.getSessionFactory().openSession();

            if (httpSession.getAttribute("user") != null) {

                User_DTO user_DTO = (User_DTO) httpSession.getAttribute("user");

                //get user from DB
                Criteria criteria1 = session.createCriteria(User.class);
                criteria1.add(Restrictions.eq("email", user_DTO.getEmail()));
                User user = (User) criteria1.uniqueResult();

                Criteria criteria2 = session.createCriteria(Address.class);
                criteria2.add(Restrictions.eq("user", user));
                Address address = (Address) criteria2.uniqueResult();

                //get cities from DB
                Criteria criteria3 = session.createCriteria(City.class);
                criteria3.addOrder(Order.asc("name"));
                List<City> cityList = criteria3.list();
                
                Criteria criteria4 = session.createCriteria(Cart.class);
                criteria4.add(Restrictions.eq("user", user));
                List<Cart> cartList = criteria4.list();

                address.setUser(null);
                jsonObject.add("address", gson.toJsonTree(address));
                jsonObject.add("cityList", gson.toJsonTree(cityList));

                
                int stotal = 0;
//                int service = 0;
                int qty = 0;

                for (Cart cart : cartList) {
                    cart.setUser(null);
                    cart.getProduct().setUser(null);
                    qty += cart.getQuantity();

                    int itemTotal = (int) (cart.getProduct().getPrice() * cart.getQuantity());
                    stotal += itemTotal;
//                    service = (int) (0.05*stotal);
//                    System.out.println(service);
                }

//                int total = service + stotal;
                jsonObject.addProperty("stotal", stotal);
                jsonObject.addProperty("qty", qty);
                jsonObject.addProperty("name", user.getName());

                jsonObject.add("cartList", gson.toJsonTree(cartList));

                jsonObject.addProperty("success", true);

            } else {
                jsonObject.addProperty("message", "Sign In to Checkout");
            }

            resp.setContentType("application/json");
            resp.getWriter().write(gson.toJson(jsonObject));
            session.close();

        } catch (Exception e) {
            System.out.println("Error: LoadCheckoutDetails: doGet " + new Date());
            e.printStackTrace();
        }
    }

}
