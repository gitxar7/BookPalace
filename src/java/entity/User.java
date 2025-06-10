/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Prince
 */
@Entity
@Table(name = "user")
public class User implements Serializable {

    @Id
    @Column(name = "id")
    private int id;
    @Column(name = "name", length = 45, nullable = false)
    private String name;
    @Column(name = "email", length = 100, nullable = false)
    private String email;
    @Column(name = "password", length = 25, nullable = false)
    private String password;
    @Column(name = "verfication", length = 20, nullable = false)
    private String verification;
    @Column(name = "v_code", length = 10, nullable = true)
    private String code;
    @Column(name = "datetime", nullable = false)
    private Date datetime;

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVerification() {
        return verification;
    }

    public void setVerification(String verification) {
        this.verification = verification;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

}
