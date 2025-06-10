/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dto.Response_DTO;
import entity.Age;
import entity.Cart;
import entity.Condition;
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
import model.Validations;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Prince
 */
@WebServlet(name = "Search", urlPatterns = {"/Search"})
public class Search extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Gson gson = new Gson();
            Session session = HibernateUtil.getSessionFactory().openSession();
            JsonObject sorting_json = gson.fromJson(req.getReader(), JsonObject.class);
            JsonObject response_json = new JsonObject();
            Criteria criteria1 = session.createCriteria(Product.class);

            if (!sorting_json.get("text").getAsString().isEmpty()) {
                String text = sorting_json.get("text").getAsString();

                criteria1.add(Restrictions.like("title", text, MatchMode.ANYWHERE));
            }
            if (!"0".equals(sorting_json.get("genre").getAsString())) {
                int genre_id = sorting_json.get("genre").getAsInt();

                Criteria criteria = session.createCriteria(Genre.class);
                criteria.add(Restrictions.eq("id", genre_id));
                Genre genre = (Genre) criteria.uniqueResult();

                criteria1.add(Restrictions.eq("genre", genre));
            }
            if (!"0".equals(sorting_json.get("age").getAsString())) {
                int age_rating_id = sorting_json.get("age").getAsInt();

                Criteria criteria = session.createCriteria(Age.class);
                criteria.add(Restrictions.eq("id", age_rating_id));
                Age age = (Age) criteria.uniqueResult();

                criteria1.add(Restrictions.eq("age_rating", age));
            }

            if (!"0".equals(sorting_json.get("condition").getAsString())) {
                int condition_id = sorting_json.get("condition").getAsInt();

                Criteria criteria = session.createCriteria(Condition.class);
                criteria.add(Restrictions.eq("id", condition_id));
                Condition condition = (Condition) criteria.uniqueResult();

                criteria1.add(Restrictions.eq("product_condition", condition));
            }

            if (!"0".equals(sorting_json.get("from").getAsString()) && sorting_json.get("from").getAsInt() > 0) {
                double starting_price = sorting_json.get("from").getAsDouble();

                criteria1.add(Restrictions.ge("price", starting_price));
            }

            if (!"0".equals(sorting_json.get("to").getAsString()) && sorting_json.get("to").getAsInt() > 0) {
                double end_price = sorting_json.get("to").getAsDouble();

                criteria1.add(Restrictions.le("price", end_price));
            }

            String sort_id = sorting_json.get("sort").getAsString();

            switch (sort_id) {
                case "1":
                    criteria1.addOrder(Order.desc("id"));
                    break;
                case "2":
                    criteria1.addOrder(Order.asc("id"));
                    break;
                case "3":
                    criteria1.addOrder(Order.asc("price"));
                    break;
                case "4":
                    criteria1.addOrder(Order.asc("title"));
                    break;
                default:
                    break;
            }

//            System.out.println(sorting_json.get("genre").getAsString());
//            System.out.println(sorting_json.get("age").getAsString());
            response_json.addProperty("allProductCount", criteria1.list().size());

            int firstResult = sorting_json.get("first_result").getAsInt();
            boolean maxResult = sorting_json.get("max_result").getAsBoolean();
            criteria1.setFirstResult(firstResult);
            if (maxResult) {
                criteria1.setMaxResults(2);
            }
            List<Product> productList = criteria1.list();
            for (Product product : productList) {
                product.setUser(null);
            }

            response_json.addProperty("success", true);
            response_json.add("productList", gson.toJsonTree(productList));

            resp.setContentType("application/json");
//            System.out.println(gson.toJson(productList));
            resp.getWriter().write(gson.toJson(response_json));

            session.close();

        } catch (Exception e) {
            System.out.println("Error: Search: doPost" + new Date());
            e.printStackTrace();
        }
    }

}
