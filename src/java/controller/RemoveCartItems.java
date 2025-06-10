/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import com.google.gson.Gson;
import dto.Cart_DTO;
import dto.Response_DTO;
import dto.User_DTO;
import entity.Cart;
import entity.Genre;
import entity.Product;
import entity.User;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.HibernateUtil;
import model.Validations;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Prince
 */
@WebServlet(name = "RemoveCartItems", urlPatterns = {"/RemoveCartItems"})
public class RemoveCartItems extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Gson gson = new Gson();
            Response_DTO response_DTO = new Response_DTO();
            String cartId = req.getParameter("cid");
            String del = req.getParameter("del");

//            System.out.println(cartId);
//            System.out.println("hello");
            if (Validations.isInteger(cartId)) {
                if (req.getSession().getAttribute("user") != null) {
                    Session session = HibernateUtil.getSessionFactory().openSession();
                    if (del.equals("all")) {
                        User_DTO user_DTO = (User_DTO) req.getSession().getAttribute("user");
                        Criteria criteria2 = session.createCriteria(User.class);
                        criteria2.add(Restrictions.eq("email", user_DTO.getEmail()));
                        User user = (User) criteria2.uniqueResult();
                        Criteria criteria1 = session.createCriteria(Cart.class);
                        criteria1.add(Restrictions.eq("user", user));
                        List<Cart> cartList = criteria1.list();
//                    System.out.println(user.getEmail());

                        for (Cart cart : cartList) {
//                        System.out.println(cart.getProduct().getTitle());
//                        System.out.println("1");
                            session.delete(cart);
                        }

//                    System.out.println("2");
                        response_DTO.setContent("Your Cart has been successfully cleared.");
                        session.beginTransaction().commit();
                        session.close();
                        response_DTO.setSuccess(true);
                    } else {
                        Cart cart = (Cart) session.load(Cart.class, Integer.parseInt(cartId));
                        String p_name = cart.getProduct().getTitle();
                        session.delete(cart);
                        session.beginTransaction().commit();
                        session.close();
                        response_DTO.setContent(p_name + " has successfully Deleted from your Cart.");
                        response_DTO.setSuccess(true);
                    }
                } else {
                    //user not found
//                    response_DTO.setContent("User not found.");
                    HttpSession httpSession = req.getSession();
                    if (httpSession.getAttribute("sessionCart") != null) {
                        ArrayList<Cart_DTO> sessionCart = (ArrayList<Cart_DTO>) httpSession.getAttribute("sessionCart");
                        if (del.equals("all")) {
                            httpSession.setAttribute("sessionCart", null);
                            response_DTO.setContent("Your Cart has been successfully cleared.");
                            response_DTO.setSuccess(true);
                        } else {

                            String p_name = "Product X";

                            for (Cart_DTO cart_DTO : sessionCart) {

                                if (cart_DTO.getId() == Integer.parseInt(cartId)) {
                                    sessionCart.remove(cart_DTO);
                                    p_name = cart_DTO.getProduct().getTitle();
                                }

                            }

                            httpSession.setAttribute("sessionCart", sessionCart);
                            response_DTO.setContent(p_name + " has successfully Deleted from your Cart.");
                            response_DTO.setSuccess(true);
                        }

                    } else {
                        //session cart not found
                        response_DTO.setContent("Cart not found");
                    }
                }

            } else {
                response_DTO.setContent("Invalid Cart ID.");
            }

            resp.setContentType("application/json");
            resp.getWriter().write(gson.toJson(response_DTO));
        } catch (Exception e) {
            System.out.println("Error: RemoveCartItems: doGet" + new Date());
            e.printStackTrace();
        }
    }

}
