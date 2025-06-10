/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Prince
 */
@Entity
@Table(name = "product")
public class Product implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "title", length = 100, nullable = false)
    private String title;
    @Column(name = "author", length = 50, nullable = false)
    private String author;
    @Column(name = "description", nullable = false)
    private String description;
    @Column(name = "isbn", length = 45, nullable = false)
    private String isbn;
    @Column(name = "price", nullable = false)
    private double price;
    @Column(name = "quantity", nullable = false)
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "genre_id")
    private Genre genre;
    @ManyToOne
    @JoinColumn(name = "age_rating_id")
    private Age age_rating;
    @ManyToOne
    @JoinColumn(name = "product_condition_id")
    private Condition product_condition;
    @ManyToOne
    @JoinColumn(name = "active_status_id")
    private Status active_status;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "datetime", nullable = false)
    private Date datetime;

    public Product() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public Age getAge_rating() {
        return age_rating;
    }

    public void setAge_rating(Age age_rating) {
        this.age_rating = age_rating;
    }

    public Condition getProduct_condition() {
        return product_condition;
    }

    public void setProduct_condition(Condition product_condition) {
        this.product_condition = product_condition;
    }

    public Status getActive_status() {
        return active_status;
    }

    public void setActive_status(Status active_status) {
        this.active_status = active_status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

}
