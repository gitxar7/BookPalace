/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dto.User_DTO;
import entity.Address;
import entity.Cart;
import entity.City;
import entity.OrderItem;
import entity.OrderStatus;
import entity.Product;
import entity.User;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.HibernateUtil;
import model.PayHere;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Prince
 */
@WebServlet(name = "Checkout", urlPatterns = {"/Checkout"})
public class CheckoutX extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        try {
            Gson gson = new Gson();
            JsonObject responseJsonObject = new JsonObject();
            responseJsonObject.addProperty("success", false);
            HttpSession httpSession = req.getSession();
            
            JsonObject req_json = gson.fromJson(req.getReader(), JsonObject.class);
            
            String line1 = req_json.get("line1").getAsString();
            String line2 = req_json.get("line2").getAsString();
            String postal = req_json.get("postal").getAsString();
            String city = req_json.get("city").getAsString();
            String mobile = req_json.get("mobile").getAsString();
            
            if (line1.isEmpty()) {
                responseJsonObject.addProperty("message", "Please add the Address Line 01");
            } else if (postal.isEmpty()) {
                responseJsonObject.addProperty("message", "Please add the Postal Code");
            } else if (mobile.isEmpty()) {
                responseJsonObject.addProperty("message", "Please add the Mobile Number");
            } else if (city.isEmpty()) {
                responseJsonObject.addProperty("message", "Please Select the City");
            } else {
                
                City city1 = (City) session.get(City.class, Integer.valueOf(city));
                if (city1 == null) {
                    responseJsonObject.addProperty("message", "Invalid City");
                } else {
                    //checkout
                    if (httpSession.getAttribute("user") != null) {
                        //user signed in

                        //get user from db
                        User_DTO user_DTO = (User_DTO) httpSession.getAttribute("user");
                        Criteria criteria1 = session.createCriteria(User.class);
                        criteria1.add(Restrictions.eq("email", user_DTO.getEmail()));
                        User user = (User) criteria1.uniqueResult();

                        //get address from db
                        Criteria criteria2 = session.createCriteria(Address.class);
                        criteria2.add(Restrictions.eq("user", user));
                        criteria2.addOrder(Order.desc("id"));
                        criteria2.setMaxResults(1);
                        
                        if (criteria2.list().isEmpty()) {
                            //current address not found
                            responseJsonObject.addProperty("message", "Current address not found. Please create a new address");
                            
                        } else {
                            //get the current address
                            Address address = (Address) criteria2.list().get(0);
//                            address.setCity(city1);
//                            address.setLine1(line1);
//                            address.setLine2(line2);
//                            address.setUser(user);
//                            address.setMobile(mobile);
//                            session.update(address);
//                            session.beginTransaction().commit();

                            //***do the checkout process
                            entity.Order order = new entity.Order();
                            order.setAddress(address);
                            order.setUser(user);
                            order.setDatetime(new Date());
                            int order_id = (int) session.save(order);

                            //get cart items
                            Criteria criteria4 = session.createCriteria(Cart.class);
                            criteria4.add(Restrictions.eq("user", user));
                            List<Cart> cartList = criteria4.list();

                            //get order status (1.Paiad) from DB
                            OrderStatus order_Status = (OrderStatus) session.get(OrderStatus.class, 1);

                            //create order item in DB
                            double amount = 0;
                            String items = "";
                            for (Cart cartItem : cartList) {

                                //calculate amount
                                amount += address.getCity().getDelivery();
                                //calculate amount

                                //Get Item details
                                items += cartItem.getProduct().getTitle() + " x" + cartItem.getQuantity() + " ";
                                //Get Item details

                                Product product = cartItem.getProduct();
                                
                                OrderItem order_item = new OrderItem();
                                order_item.setOrder(order);
                                order_item.setOrderStatus(order_Status);
                                order_item.setProduct(product);
                                order_item.setQuantity(cartItem.getQuantity());
                                session.save(order_item);

                                //update product qty in DB
                                product.setQuantity(product.getQuantity() - cartItem.getQuantity());
                                session.update(product);

                                //delete cart item from DB
                                session.delete(cartItem);
                                
                            }
                            
                            transaction.commit();

                            //set payment data (start)
                            String merchant_id = "1221775";
                            String formatted_amount = new DecimalFormat("0.00").format(amount);
                            String currency = "LKR";
                            String merchantSecret = "MTI2OTc2NzM5MDIxNDExODYxNTAxMzEwMjcxNDc1MTU5Mzk3NDY3OQ==";
                            String merchantSecretMdHash = PayHere.getMd5(merchantSecret);
                            
                            JsonObject payhere = new JsonObject();
                            payhere.addProperty("merchant_id", merchant_id);
                            
                            payhere.addProperty("return_url", "");
                            payhere.addProperty("cancel_url", "");
                            payhere.addProperty("notify_url", ""); //***

                            payhere.addProperty("first_name", user.getName());
                            payhere.addProperty("last_name", "");
                            payhere.addProperty("email", user.getEmail());
                            
                            payhere.addProperty("phone", "");
                            payhere.addProperty("address", "");
                            payhere.addProperty("city", "");
                            payhere.addProperty("country", "");
                            
                            payhere.addProperty("order_id", String.valueOf(order_id));
                            payhere.addProperty("items", items);
                            payhere.addProperty("currency", "LKR");
                            payhere.addProperty("amount", formatted_amount);
                            payhere.addProperty("sandbox", true);

                            //Generate MD5 Hash
                            //merahantID + orderID + amountFormatted + currency + getMd5(merchantSecret)
                            String md5Hash = PayHere.getMd5(merchant_id + order_id + formatted_amount + currency + merchantSecretMdHash);
                            payhere.addProperty("hash", md5Hash);

                            //set payment data (end)
                            responseJsonObject.addProperty("success", true);
                            responseJsonObject.addProperty("message", "Checkout completed");
                            
                            responseJsonObject.add("payhereJson", gson.toJsonTree(payhere));
                        }
                        
                    } else {
                        //user not signed in
                        responseJsonObject.addProperty("message", "User not signed in");
                    }
                    //checkout
                }
            }
            
            resp.setContentType("application/json");
            resp.getWriter().write(gson.toJson(responseJsonObject));
            System.out.println(responseJsonObject);
        } catch (Exception e) {
            transaction.rollback();
            System.out.println("Error: Checkout: doPost" + new Date());
            e.printStackTrace();
        }
    }
    
}
