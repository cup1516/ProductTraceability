package com.pt.center.newentity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "center_company")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String person;
    private String code;
    private String number;
    private String address;
    private String username;
    private String validity;
    private String url;

    public void setAddress(String address) {
        this.address = address;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setValidity(String validity) {
        this.validity = validity;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAddress() {
        return address;
    }

    public String getUsername() {
        return username;
    }

    public String getValidity() {
        return validity;
    }

    public String getUrl() {
        return url;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPerson() {
        return person;
    }

    public String getCode() {
        return code;
    }

    public String getNumber() {
        return number;
    }
}
