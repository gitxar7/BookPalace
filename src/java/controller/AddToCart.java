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
@WebServlet(name = "AddToCart", urlPatterns = {"/AddToCart"})
public class AddToCart extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Response_DTO response_DTO = new Response_DTO();
            Gson gson = new Gson();
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();

            String pid = req.getParameter("pid");
            String qty = req.getParameter("qty");

            if (!Validations.isInteger(pid)) {
                //invalid id
                response_DTO.setContent("Invalid product id");

            } else if (!Validations.isInteger(qty)) {
                //invalid qty
                response_DTO.setContent("Invalid product quantity");

            } else {

                int productId = Integer.parseInt(pid);
                int quantity = Integer.parseInt(qty);

                if (quantity <= 0) {
                    //quantity must be greater than 0
                    response_DTO.setContent("Quantity must be greater than 0");

                } else {

                    Product product = (Product) session.get(Product.class, productId);

                    if (product != null) {
                        //product found

                        if (req.getSession().getAttribute("user") != null) {
                            //DB Cart

                            User_DTO user_DTO = (User_DTO) req.getSession().getAttribute("user");

                            //search user
                            Criteria criteria1 = session.createCriteria(User.class);
                            criteria1.add(Restrictions.eq("email", user_DTO.getEmail()));

                            User user = (User) criteria1.uniqueResult();

                            //check in db cart
                            Criteria criteria2 = session.createCriteria(Cart.class);
                            criteria2.add(Restrictions.eq("user", user));
                            criteria2.add(Restrictions.eq("product", product));

                            if (criteria2.list().isEmpty()) {
                                //item not found in cart

                                if (quantity <= product.getQuantity()) {
                                    //add product into cart

                                    Cart cart = new Cart();
                                    cart.setProduct(product);
                                    cart.setQuantity(quantity);
                                    cart.setUser(user);

                                    session.save(cart);
                                    transaction.commit();
                                    response_DTO.setSuccess(true);
                                    response_DTO.setContent("Product added to cart");

                                } else {
                                    //not enough stock
                                    response_DTO.setContent("Not enough stock");
                                }

                            } else {
                                //item found in cart

                                Cart cartItem = (Cart) criteria2.uniqueResult();

                                if ((cartItem.getQuantity() + quantity) <= product.getQuantity()) {

                                    cartItem.setQuantity(cartItem.getQuantity() + quantity);
                                    session.update(cartItem);
                                    transaction.commit();

                                    response_DTO.setSuccess(true);
                                    response_DTO.setContent("Quantity updated");

                                } else {
                                    //can't update your cart. Quantity not available
                                    response_DTO.setContent("Quantity not available");
                                }
                            }

                        } else {
                            //Session Cart

                            HttpSession httpSession = req.getSession();

                            if (httpSession.getAttribute("sessionCart") != null) {
                                //session cart found

                                ArrayList<Cart_DTO> sessionCart = (ArrayList<Cart_DTO>) httpSession.getAttribute("sessionCart");

                                Cart_DTO foundCart_DTO = null;

                                for (Cart_DTO cart_DTO : sessionCart) {

                                    if (cart_DTO.getProduct().getId() == product.getId()) {
                                        foundCart_DTO = cart_DTO;
                                        break;
                                    }

                                }

                                if (foundCart_DTO != null) {
                                    //product found

                                    if ((foundCart_DTO.getQty() + quantity) <= product.getQuantity()) {
                                        //update quantity in session cart

                                        foundCart_DTO.setQty(foundCart_DTO.getQty() + quantity);

                                        response_DTO.setSuccess(true);
                                        response_DTO.setContent("Product quantity updated");

                                    } else {
                                        // quantity not available
                                        response_DTO.setContent("Quantity not available");

                                    }

                                } else {
                                    //product not found

                                    if (quantity <= product.getQuantity()) {
                                        //add to session cart
                                        Cart_DTO cart_DTO = new Cart_DTO();
                                        cart_DTO.setProduct(product);
                                        cart_DTO.setQty(quantity);
                                        sessionCart.add(cart_DTO);

                                        response_DTO.setSuccess(true);
                                        response_DTO.setContent("Product added to cart 1");

                                    } else {
                                        //quantity not available
                                        response_DTO.setContent("Quantity not available");

                                    }

                                }

                            } else {
                                //session cart not found

                                if (quantity <= product.getQuantity()) {
                                    //add product into session cart

                                    ArrayList<Cart_DTO> sessionCart = new ArrayList<>();

                                    Cart_DTO cart_DTO = new Cart_DTO();
                                    cart_DTO.setProduct(product);
                                    cart_DTO.setQty(quantity);
                                    sessionCart.add(cart_DTO);

                                    req.getSession().setAttribute("sessionCart", sessionCart);

                                    response_DTO.setSuccess(true);
                                    response_DTO.setContent("Product added to cart 2");
                                    System.out.println(cart_DTO.getProduct().getTitle());

                                } else {
                                    //not enough stock in session cart
                                    response_DTO.setContent("Not enough stock");
                                }

                            }

                        }

                    } else {
                        //product not found
                        response_DTO.setContent("Product not found");
                    }

                }

            }

            resp.setContentType("application/json");
            resp.getWriter().write(gson.toJson(response_DTO));

        } catch (Exception e) {
            System.out.println("Error: AddToCart: doGet" + new Date());
            e.printStackTrace();
        }
    }

}
