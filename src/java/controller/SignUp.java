/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
import model.MailHTML;
import model.Validations;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Prince
 */
@WebServlet(name = "SignUp", urlPatterns = {"/SignUp"})
public class SignUp extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Response_DTO response_DTO = new Response_DTO();

            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
            User_DTO user_DTO = gson.fromJson(req.getReader(), User_DTO.class);

            if (user_DTO.getName().isEmpty()) {
                response_DTO.setContent("Please enter your Name");
            } else if (user_DTO.getEmail().isEmpty()) {
                response_DTO.setContent("Please enter your Email Address");
            } else if (!Validations.isEmailValid(user_DTO.getEmail())) {
                response_DTO.setContent("Invalid Email");
            } else if (user_DTO.getPassword().isEmpty()) {
                response_DTO.setContent("Please enter your Password");
            } else if (!Validations.isPasswordValid(user_DTO.getPassword())) {
                response_DTO.setContent("Password must include at least one uppdercase"
                        + " letter, number, special character and should not be less than"
                        + " eight characters");
            } else {
                Session session = HibernateUtil.getSessionFactory().openSession();

                Criteria criteria = session.createCriteria(User.class);
                criteria.add(Restrictions.eq("email", user_DTO.getEmail()));

                if (!criteria.list().isEmpty()) {
                    response_DTO.setContent("User with this email already exists");
                } else {
                    int code = (int) (Math.random() * 1000000);

                    final User user = new User();
                    user.setEmail(user_DTO.getEmail());
                    user.setName(user_DTO.getName());
                    user.setPassword(user_DTO.getPassword());
                    user.setVerification(String.valueOf(code));
                    user.setDatetime(new Date());

                    Thread sendMailThread = new Thread() {
                        @Override
                        public void run() {
                            MailHTML.sendMail(user.getEmail(), "Book Palace Verification",
                                    "<h1 style=\"color: #2081e2\">your verification code is: " + user.getVerification() + "</h1>");
                        }

                    };
                    sendMailThread.start();

                    session.save(user);
                    session.beginTransaction().commit();
                    req.getSession().setAttribute("email", user_DTO.getEmail());
                    response_DTO.setSuccess(true);
                    response_DTO.setContent("Registration sucessful, check your email");
                }

                session.close();
            }
            resp.setContentType("application/json");
            resp.getWriter().write(gson.toJson(response_DTO));
            System.out.println(gson.toJson(response_DTO));

        } catch (Exception e) {
            System.out.println("Error: Sign Up: doPost " + new Date());
            e.printStackTrace();
        }
    }

}
