package com.pt.center.newentity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "center_consumer")
public class Consumer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String phone;
    private String query_time;
    @Column(name = "query_address")
    private String address;
    @Column(name = "query_code")
    private String code;
    private String product;
    private String product_company;
    private String points;
}
