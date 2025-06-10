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
import entity.Product;
import entity.User;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.HibernateUtil;
import model.Validations;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Prince
 */
@MultipartConfig
@WebServlet(name = "UpdateCart", urlPatterns = {"/UpdateCart"})
public class UpdateCart extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
//            System.out.println("UpdateCart: doGet");
            Response_DTO response_DTO = new Response_DTO();
            Gson gson = new Gson();
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();

            String cid = req.getParameter("cid");
            String act = req.getParameter("act");

            if (!Validations.isInteger(cid)) {
                //invalid id
                response_DTO.setContent("Invalid cart id");

            } else if (act.isEmpty()) {
                //invalid qty
                response_DTO.setContent("Error! Try again later");

            } else {

                int cartId = Integer.parseInt(cid);
                int quantity;

                if (act.equals("add")) {
                    quantity = 1;
                } else {
                    quantity = -1;
                }

                if (req.getSession().getAttribute("user") != null) {

                    Cart cart = (Cart) session.get(Cart.class, cartId);

                    if (cart != null) {
                        //cart found

                        //DB Cart
                        User_DTO user_DTO = (User_DTO) req.getSession().getAttribute("user");

                        //search user
                        Criteria criteria1 = session.createCriteria(User.class);
                        criteria1.add(Restrictions.eq("email", user_DTO.getEmail()));

                        User user = (User) criteria1.uniqueResult();

                        Product product = cart.getProduct();

                        //check in cart
                        Criteria criteria2 = session.createCriteria(Cart.class);
                        criteria2.add(Restrictions.eq("user", user));
                        criteria2.add(Restrictions.eq("product", product));

                        Cart cartItem = (Cart) criteria2.uniqueResult();

                        if ((cartItem.getQuantity() + quantity) <= product.getQuantity()
                                && (cartItem.getQuantity() + quantity) > 0) {

                            cartItem.setQuantity(cartItem.getQuantity() + quantity);
                            session.update(cartItem);
                            transaction.commit();

                            response_DTO.setSuccess(true);
                            response_DTO.setContent("Quantity updated");

                        } else {
                            //can't update your cart. Quantity not available
                            response_DTO.setContent("Quantity unavailable");
                        }

                    } else {
                        //cart not found
                        response_DTO.setContent("Cart not found");
                    }

                } else {
                    //user not found
//                    response_DTO.setContent("Eror! Try again later...");

                    HttpSession httpSession = req.getSession();
                    if (httpSession.getAttribute("sessionCart") != null) {
//                        System.out.println("1");
                        //session cart found
                        ArrayList<Cart_DTO> sessionCart = (ArrayList<Cart_DTO>) httpSession.getAttribute("sessionCart");

                        Cart_DTO foundCart_DTO = null;

                        for (Cart_DTO cart_DTO : sessionCart) {

                            if (cart_DTO.getId() == Integer.parseInt(cid)) {
                                foundCart_DTO = cart_DTO;
//                                System.out.println("2");
//                                break;
                            }
                            
//                             System.out.println(foundCart_DTO);

                            if (foundCart_DTO != null) {
                                //cart found
//                                System.out.println("3");
                                Product product = foundCart_DTO.getProduct();
                                if ((foundCart_DTO.getQty() + quantity) <= product.getQuantity()
                                        && (foundCart_DTO.getQty() + quantity) > 0) {
//                                    System.out.println("4");
                                    foundCart_DTO.setQty(foundCart_DTO.getQty() + quantity);

                                    response_DTO.setSuccess(true);
                                    response_DTO.setContent("Quantity updated");

                                } else {
                                    //can't update your cart. Quantity not available
                                    response_DTO.setContent("Quantity unavailable");
                                }

                            } else {
                                //cart not found
                                response_DTO.setContent("Cart not found");
                            }

                        }
                    } else {
                        //session cart not found
                         response_DTO.setContent("Cart not found");
                    }
                }

            }

            resp.setContentType("application/json");
            resp.getWriter().write(gson.toJson(response_DTO));

        } catch (Exception e) {
            System.out.println("Error: UpdateCart: doGet" + new Date());
            e.printStackTrace();
        }
    }

}
