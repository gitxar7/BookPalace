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
import entity.City;
import entity.Condition;
import entity.Genre;
import entity.Product;
import entity.Status;
import entity.User;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import model.HibernateUtil;
import model.Validations;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Prince
 */
@WebServlet(name = "AddAddress", urlPatterns = {"/AddAddress"})
public class AddAddress extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Gson gson = new Gson();
            Response_DTO response_DTO = new Response_DTO();
            Session session = HibernateUtil.getSessionFactory().openSession();
            JsonObject req_json = gson.fromJson(req.getReader(), JsonObject.class);

            String line1 = req_json.get("line1").getAsString();
            String line2 = req_json.get("line2").getAsString();
            String postal = req_json.get("postal").getAsString();
            String city = req_json.get("city").getAsString();

            if (line1.isEmpty()) {
                response_DTO.setContent("Please add the Address Line 01");
            } else if (postal.isEmpty()) {
                response_DTO.setContent("Please add the Postal Code");
            } else if (city.isEmpty()) {
                response_DTO.setContent("Please Select the City");
            } else {

                City city1 = (City) session.get(City.class, Integer.valueOf(city));
                if (city1 == null) {
                    response_DTO.setContent("Invalid City");
                } else {
                    if (req.getSession().getAttribute("user") == null) {
                        response_DTO.setContent("Error: Please Sign In");
                    } else {
                        User_DTO user_DTO = (User_DTO) req.getSession().getAttribute("user");
                        Criteria criteria1 = session.createCriteria(User.class);
                        criteria1.add(Restrictions.eq("email", user_DTO.getEmail()));
                        User user = (User) criteria1.uniqueResult();

                        Criteria criteria2 = session.createCriteria(Address.class);
                        criteria2.add(Restrictions.eq("user", user));
                        Address address = (Address) criteria2.uniqueResult();

                        if (address == null) {
                            Address address1 = new Address();
                            address1.setCity(city1);
                            address1.setLine1(line1);
                            address1.setLine2(line2);
                            address1.setUser(user);
                            session.save(address1);
                            session.beginTransaction().commit();

                            response_DTO.setContent("Address has been Added");
                            response_DTO.setSuccess(true);
                        } else {
                            address.setCity(city1);
                            address.setLine1(line1);
                            address.setLine2(line2);
                            address.setUser(user);
                            session.update(address);
                            session.beginTransaction().commit();

                            response_DTO.setContent("Address has been Updated");
                            response_DTO.setSuccess(true);
                        }
                    }
                }
            }

            session.close();
            resp.setContentType("application/json");
            resp.getWriter().write(gson.toJson(response_DTO));
            System.out.println(gson.toJson(response_DTO));
        } catch (Exception e) {
            System.out.println("Error: AddAddress: doPost" + new Date());
            e.printStackTrace();
        }
    }

}
