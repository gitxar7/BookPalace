/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import com.google.gson.Gson;
import dto.Cart_DTO;
import dto.User_DTO;
import entity.Cart;
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
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Prince
 */
@WebServlet(name = "LoadCartItems", urlPatterns = {"/LoadCartItems"})
public class LoadCartItems extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Gson gson = new Gson();
            HttpSession httpSession = req.getSession();
            ArrayList<Cart_DTO> cart_DTO_List = new ArrayList<>();
            Session session = HibernateUtil.getSessionFactory().openSession();

            if (httpSession.getAttribute("user") != null) {
                //DB Cart

                User_DTO user_DTO = (User_DTO) httpSession.getAttribute("user");

                //search user
                Criteria criteria1 = session.createCriteria(User.class);
                criteria1.add(Restrictions.eq("email", user_DTO.getEmail()));
                User user = (User) criteria1.uniqueResult();

                //search cart items for the user
                Criteria criteria2 = session.createCriteria(Cart.class);
                criteria2.add(Restrictions.eq("user", user));
                List<Cart> cartList = criteria2.list();

                for (Cart cart : cartList) {

                    Cart_DTO cart_DTO = new Cart_DTO();

                    Product product = cart.getProduct();
                    product.getUser().setEmail(null);
                    product.getUser().setPassword(null);
                    product.getUser().setVerification(null);

                    cart_DTO.setProduct(product);
                    cart_DTO.setQty(cart.getQuantity());
                    cart_DTO.setId(cart.getId());

                    cart_DTO_List.add(cart_DTO);

                }

            } else {
                //Session Cart

                if (httpSession.getAttribute("sessionCart") != null) {
                    ArrayList<Cart_DTO> sessionCart = (ArrayList<Cart_DTO>) httpSession.getAttribute("sessionCart");

                    for (Cart_DTO cart_DTO : sessionCart) {

                        Criteria criteria1 = session.createCriteria(Product.class);
                        criteria1.add(Restrictions.eq("id", cart_DTO.getProduct().getId()));
                        Product product = (Product) criteria1.uniqueResult();
                        
                        cart_DTO.setProduct(product);

                        cart_DTO.getProduct().getUser().setEmail(null);
                        cart_DTO.getProduct().getUser().setPassword(null);
                        cart_DTO.getProduct().getUser().setVerification(null);

                        cart_DTO_List.add(cart_DTO);
                    }

                    System.out.println(cart_DTO_List);
                } else {
                    //cart empty
                }
            }

            session.close();
            resp.setContentType("application/json");
            resp.getWriter().write(gson.toJson(cart_DTO_List));
//            System.out.println(gson.toJson(cart_DTO_List));

        } catch (Exception e) {
            System.out.println("Error: LoadCartItems: doGet" + new Date());
            e.printStackTrace();
        }
    }

}
