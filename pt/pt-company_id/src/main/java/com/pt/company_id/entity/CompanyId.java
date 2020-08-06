package com.pt.company_id.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Data
@Table(name = "system_company_info")
//@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class CompanyId {
    @Id
    //@GeneratedValue(generator = "jpa-uuid")
    //@Column(name="company_id", length = 32)
    @TableId(value = "company_id",type = IdType.INPUT)
    private String companyId;
    @Column(name="company_name")
    private String companyName;
    private char type;
    private String url;
}

