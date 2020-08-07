package com.pt.company_id.controller;

import com.pt.company_id.entity.CompanyId;
import com.pt.company_id.entity.SystemUser;
import com.pt.company_id.repository.CompanyIdRepository;
import com.pt.company_id.repository.SystemUserRepository;
import com.pt.company_id.service.InsertData;
import com.pt.ptcommoncore.util.IdUtils;
import com.pt.ptcommoncore.util.UUID;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

@RestController
@RequestMapping("/company_id")
public class CompanyIdController {
    private static final PasswordEncoder ENCODER = new BCryptPasswordEncoder();
    @Autowired
    private CompanyIdRepository companyIdRepository;
    @Autowired
    private SystemUserRepository systemUserRepository;

    @GetMapping("/findCompanyIdById/{id}")
    public CompanyId findCompanyById(@PathVariable("id") String id) {
        CompanyId c1 = companyIdRepository.findByCompanyId(id);
        return c1;
    }
    @GetMapping("/findCompanyIdByUrl/{url}")
    public String findCompanyByUrl(@PathVariable("url") String url) {
        return companyIdRepository.findByUrl(url).getCompanyId();
    }

    @PostMapping("/insert/{url}")
    public String insert(@PathVariable("url") String url) {
        InsertData.ExecuteSql();
        return "success!";
    }
    @GetMapping("/findCompanyIdByNameLike/{name}")
    public List<CompanyId> findByCompanyNameLike(@PathVariable("name") String name) {
        return companyIdRepository.findByCompanyNameLike("%"+name+"%");// 加"%"+参数名+"%"实现模糊查询
    }
    @PostMapping("/save")
    public String save(@RequestBody CompanyId companyId) throws IOException, InterruptedException {
        if (companyIdRepository.findByUrl(companyId.getUrl()) !=null)
        {
            return "该地址已被使用";
        }
        String uid = IdUtils.simpleUUID();//用uuid生成company_id
        String url0 =companyId.getUrl();
        char client_type0 = companyId.getType();
        companyId.setCompanyId(uid);
        CompanyId result = companyIdRepository.save(companyId);
        InsertData.CreateSql(uid,url0,client_type0);
        //Thread.sleep(1000*3);   // 休眠3秒,保证文件被保存
        if (result != null) {
            return "success";
        } else {
            return "error!";
        }
    }

    @PostMapping("/update")
    public String update(@RequestBody CompanyId companyId) {
        CompanyId c0 = companyIdRepository.findByCompanyId(companyId.getCompanyId());
        c0.setUrl(companyId.getUrl());
        c0.setCompanyName(companyId.getCompanyName());
        c0.setType(companyId.getType());
        CompanyId result = companyIdRepository.save(c0);
        if (result != null) {
            return "success";
        } else {
            return "error!";
        }
    }

    @GetMapping("/findAll/{page}/{size}")
    public Page<CompanyId> findAll(@PathVariable("page") Integer page, @PathVariable("size") Integer size){
        PageRequest request = PageRequest.of(page,size);
        return companyIdRepository.findAll(request);
    }
}
