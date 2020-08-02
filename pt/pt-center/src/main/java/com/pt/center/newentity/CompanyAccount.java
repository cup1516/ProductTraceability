package com.pt.center.newentity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "center_company_account")
public class CompanyAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String code;
    private String username;
    private String password;
    private String validity;
    private String website;


}
