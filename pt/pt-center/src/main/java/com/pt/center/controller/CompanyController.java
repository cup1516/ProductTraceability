package com.pt.center.controller;



import com.pt.center.repository.CompanyRepository;
import com.pt.center.newentity.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/company")
public class CompanyController {
    @Autowired
    private CompanyRepository companyRepository;

    @DeleteMapping("/deleteById/{id}")
    void deleta(@PathVariable("id") Integer id){
        companyRepository.deleteById(id);
    }

    @GetMapping("/findAll/{page}/{size}")
    public Page<Company> findAll(@PathVariable("page") Integer page, @PathVariable("size") Integer size){
        PageRequest request = PageRequest.of(page,size);
        return companyRepository.findAll(request);
    }

    @GetMapping("/findCompanyByUsername/{username}")
    public Company findCompanyByUsername(@PathVariable("username") String username){
        Company c1 = companyRepository.findByUsername(username);
        return c1;
    }

    @PostMapping("/save")
    public String save(@RequestBody Company company){
        Company result = companyRepository.save(company);
        if(result != null){
            return "success";
        }else{
            return "error!";
        }
    }

    @PostMapping("/update")
    public String update(@RequestBody Company company){
        Company c0 = companyRepository.findByUsername(company.getUsername());
        /*Company code = companyRepository.findByCode(company.getCode());
        Company number = companyRepository.findByNumber(company.getNumber());
        if(code!=null)
            return"code";
        if(number!=null)
            return "number";*/
        c0.setAddress(company.getAddress());
        c0.setCode(company.getCode());
        c0.setName(company.getName());
        c0.setNumber(company.getNumber());
        c0.setPerson(company.getPerson());
        Company result = companyRepository.save(c0);
        if(result != null){
            return "success";
        }else{
            return "error!";
        }
    }

    @GetMapping("/findOne/{str}")
    public String findOne(@PathVariable("str") String testStr){
        Pattern p = Pattern.compile(".*\\d+.*");
        Matcher m = p.matcher(testStr);
        String res = "";
        if (m.matches()) {//有数字 查找统一社会信用码
            Company c1 = companyRepository.findByCode(testStr);
            if(c1 == null)
                res = "查无此公司";
            else {
                String c1Name = c1.getName();
                String c1Code = c1.getCode();
                res = "查询的公司："+c1Name+"  统一社会信用码："+c1Code;
            }
        }
        else{//没有数字 找名字
            Company c2 = companyRepository.findByName(testStr);
            if(c2 == null)
                res = "查无此公司";
            else {
                String c2Name = c2.getName();
                String c2Code = c2.getCode();
                res = "查询的公司："+c2Name+"  统一社会信用码："+c2Code;
            }
        }
        return res;
    }
}
