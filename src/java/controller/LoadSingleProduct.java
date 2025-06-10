/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import entity.Product;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.HibernateUtil;
import model.Validations;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Prince
 */
@MultipartConfig
@WebServlet(name = "LoadSingleProduct", urlPatterns = {"/LoadSingleProduct"})
public class LoadSingleProduct extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Gson gson = new Gson();
            Session session = HibernateUtil.getSessionFactory().openSession();

            String productId = req.getParameter("pid");

            if (Validations.isInteger(productId)) {

                Product product = (Product) session.get(Product.class, Integer.parseInt(productId));
                product.getUser().setEmail(null);
                product.getUser().setVerification(null);
                product.getUser().setPassword(null);

                Criteria criteria1 = session.createCriteria(Product.class);
                criteria1.add(Restrictions.eq("genre", product.getGenre()));
                criteria1.add(Restrictions.ne("id", product.getId()));
                criteria1.setMaxResults(5);

                List<Product> productList = criteria1.list();

                for (Product product1 : productList) {
                    product1.getUser().setEmail(null);
                    product1.getUser().setVerification(null);
                    product1.getUser().setPassword(null);
                }

                JsonObject jsonObject = new JsonObject();
                jsonObject.add("product", gson.toJsonTree(product));
                jsonObject.add("productList", gson.toJsonTree(productList));

                session.close();
                resp.setContentType("application/json");
                resp.getWriter().write(gson.toJson(jsonObject));

            } else {
            }
        } catch (Exception e) {
            System.out.println("Error: LoadSingleProduct: doGet" + new Date());
            e.printStackTrace();
        }
    }

}
