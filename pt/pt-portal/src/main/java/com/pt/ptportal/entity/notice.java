package com.pt.ptportal.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Data
@Entity
@Table(name = "notice")
public class notice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    //名称
    private String name;
    //状态位
    private Integer status=1;
    //标题
    private String title;
    //发布时间
    @Column(columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP",insertable = false,updatable = false)
    @Generated(GenerationTime.INSERT)
    private Timestamp createTime;

    //通告的原文
    private String noticeContentMd;
    //HTML
    private String noticeHtml;
    //失效时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date failTime;

    //审核状态 0是未送审  1送审  2送审通过状态 3送审未通过
    private int isCheck=0;

    // 企业id
    private String companyId;

    //自定义返回时间的格式
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    public Timestamp getCreateTime() {
        return createTime;
    }

}
