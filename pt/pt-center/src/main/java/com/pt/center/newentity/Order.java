package com.pt.center.newentity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "center_order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String number;
    private String company_name;
    private String company_code;
    private String product;
    private String quantity;
    private String singleprice;
    private String totalprice;
    private String create_time;
    private String state;
    private String auditor;

}
