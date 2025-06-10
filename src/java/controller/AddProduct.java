/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dto.Response_DTO;
import dto.User_DTO;
import entity.Age;
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
@MultipartConfig
@WebServlet(name = "AddProduct", urlPatterns = {"/AddProduct"})
public class AddProduct extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String genreId = req.getParameter("genreId");
            String ageId = req.getParameter("ageId");
            String conditionId = req.getParameter("conditionId");
            String title = req.getParameter("title");
            String author = req.getParameter("author");
            String isbn = req.getParameter("isbn");
            String price = req.getParameter("price");
            String quantity = req.getParameter("quantity");
            String description = req.getParameter("description");

            Part image = req.getPart("image");

            Session session = HibernateUtil.getSessionFactory().openSession();
            Gson gson = new Gson();
            Response_DTO response_DTO = new Response_DTO();
//            response_DTO.setContent(genreId + " " + ageId + " " + conditionId + " "
//                    + title + " " + author + " " + isbn + " " + price + " " + quantity + " " + description + " " + image);
//basic validations<
            if (!Validations.isInteger(genreId)) {
                response_DTO.setContent("invalid Genre");
            }
            if (!Validations.isInteger(ageId)) {
                response_DTO.setContent("invalid Age-Rating");
            } else if (!Validations.isInteger(conditionId)) {
                response_DTO.setContent("invalid Condition");
            } else if (title.isEmpty()) {
                response_DTO.setContent("Please add the Product Title");
            } else if (author.isEmpty()) {
                response_DTO.setContent("Please add the Product Author");
            } else if (isbn.isEmpty()) {
                response_DTO.setContent("Please add the Product isbn");
            } else if (price.isEmpty()) {
                response_DTO.setContent("Please add a Product Price");
            } else if (!Validations.isDouble(price)) {
                response_DTO.setContent("Please add a Valid Price");
            } else if (Double.parseDouble(price) <= 0) {
                response_DTO.setContent("Price must be greater than 0");
            } else if (quantity.isEmpty()) {
                response_DTO.setContent("Please add a Product Quantity");
            } else if (!Validations.isInteger(quantity)) {
                response_DTO.setContent("Please add a Valid Quantity");
            } else if (Integer.parseInt(quantity) <= 0) {
                response_DTO.setContent("Quantity must be greater than 0");
            } else if (description.isEmpty()) {
                response_DTO.setContent("Please add a Product Description");
            } else if (image.getSubmittedFileName() == null) {
                response_DTO.setContent("Please upload a Cover Iamge");
            } else {
//basic validations>                
                //select option validations<                
                //genre<
                Genre genre = (Genre) session.get(Genre.class, Integer.parseInt(genreId));
                if (genre == null) {
                    response_DTO.setContent("Invalid Genre");
                } else {
                    //age<
                    Age age_rating = (Age) session.get(Age.class, Integer.parseInt(ageId));
                    if (age_rating == null) {
                        response_DTO.setContent("Invalid Age-Rating");
                    } else {
                        //condition<
                        Condition condition = (Condition) session.get(Condition.class, Integer.parseInt(conditionId));
                        if (condition == null) {
                            response_DTO.setContent("Invalid Condition");
                        } else {

                            //add product< 
                            Product product = new Product();

                            product.setTitle(title);
                            product.setAuthor(author);
                            product.setDescription(description);
                            product.setIsbn(isbn);
                            product.setPrice(Double.parseDouble(price));
                            product.setQuantity(Integer.parseInt(quantity));

                            product.setGenre(genre);
                            product.setAge_rating(age_rating);
                            product.setProduct_condition(condition);

                            product.setActive_status((Status) session.load(Status.class, 1));
                            product.setDatetime(new Date());
                            //get User
                            User_DTO user_DTO = (User_DTO) req.getSession().getAttribute("user");
                            Criteria criteria = session.createCriteria(User.class);
                            criteria.add(Restrictions.eq("email", user_DTO.getEmail()));
                            User user = (User) criteria.uniqueResult();
                            product.setUser(user);

                            int pid = (int) session.save(product);
                            session.beginTransaction().commit();

//                            String applicationPath = req.getServletContext().getRealPath("");
//                            File folder = new File(applicationPath + "//ProductCoverImages");
//                            if (!folder.exists()) {
//                                folder.mkdir();
//                            }
                            String applicationPath = req.getServletContext().getRealPath("");
                            String newApplicationPath = applicationPath.replace("build" + File.separator + "web", "web");

                            File folder = new File(newApplicationPath + File.separator + "ProductCoverImages");
                            folder.mkdir();

                            File file = new File(folder, pid + ".png");
                            InputStream inputStream1 = image.getInputStream();
                            Files.copy(inputStream1, file.toPath(), StandardCopyOption.REPLACE_EXISTING);

//                            response_DTO.setSuccess(true);
//                            response_DTO.setContent("Product Added Successfully!");

                            response_DTO.setContent("Product Successfully Added");
                            response_DTO.setSuccess(true);
                            //add product>
                        }
                        //codition>
                    }
                    //age>
                }
                //genre>
                //select option validations>               
            }

            session.close();
            resp.setContentType("application/json");
            resp.getWriter().write(gson.toJson(response_DTO));
            System.out.println(gson.toJson(response_DTO));
        } catch (Exception e) {
            System.out.println("Error: AddProduct: doPost" + new Date());
            e.printStackTrace();
        }
    }

}
