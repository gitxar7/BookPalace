/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

import com.google.gson.annotations.Expose;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Id;

/**
 *
 * @author Prince
 */
public class User_DTO implements Serializable {

    @Expose
    private int id;
    @Expose
    private String name;
    @Expose
    private String email;
    @Expose(serialize = false, deserialize = true)
    private String password;
    @Expose(serialize = false, deserialize = true)
    private String verification;
    @Expose(serialize = false, deserialize = true)
    private String code;
    @Expose
    private Date datetime;

    public User_DTO() {
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
