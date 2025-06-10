/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dto.Response_DTO;
import dto.User_DTO;
import entity.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.HibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Prince
 */
@WebServlet(name = "Verification", urlPatterns = {"/Verification"})
public class Verification extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Response_DTO response_DTO = new Response_DTO();

            Gson gson = new Gson();
            JsonObject dto = gson.fromJson(req.getReader(), JsonObject.class);
            String verification = dto.get("verification").getAsString();

            if (req.getSession().getAttribute("email") != null) {

                String email = req.getSession().getAttribute("email").toString();

                Session session = HibernateUtil.getSessionFactory().openSession();

                Criteria criteria = session.createCriteria(User.class);
                criteria.add(Restrictions.eq("email", email));
                criteria.add(Restrictions.eq("verification", verification));

                if (!criteria.list().isEmpty()) {

                    User user = (User) criteria.uniqueResult();
                    user.setVerification("Verified");

                    session.update(user);
                    session.beginTransaction().commit();

                    User_DTO user_DTO = new User_DTO();
                    user_DTO.setName(user.getName());
                    user_DTO.setEmail(user.getEmail());

                    req.getSession().removeAttribute("email");
                    req.getSession().setAttribute("user", user_DTO);

                    response_DTO.setSuccess(true);
                    response_DTO.setContent("Verification success");

                } else {
                    //invalid code
                    response_DTO.setContent("Invalid Verification code!");
                }

            } else {
                response_DTO.setContent("Verification unavailable! Please Sign in");
            }

            resp.setContentType("application/json");
            resp.getWriter().write(gson.toJson(response_DTO));
        } catch (Exception e) {
            System.out.println("Error: Verification: doPost" + new Date());
            e.printStackTrace();
        }
    }

}
