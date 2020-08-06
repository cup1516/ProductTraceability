package com.pt.center.newentity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "center_code_info")
public class Code {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String code;
    private String times;
    private String product;
    private String company;
}
