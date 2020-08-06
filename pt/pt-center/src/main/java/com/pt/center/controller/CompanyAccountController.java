package com.pt.center.controller;

import com.pt.center.repository.CompanyAccountRepository;
import com.pt.center.repository.CompanyRepository;
import com.pt.center.newentity.CompanyAccount;
import com.pt.center.newentity.Company;
import com.pt.center.service.UsernameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/companyAccount")
public class CompanyAccountController {

    @Autowired
    CompanyAccountRepository companyAccountRepository;
    @Autowired
    UsernameService usernameService;
    @Autowired
    CompanyRepository companyRepository;

    @PostMapping("/save")
    public String save(@RequestBody CompanyAccount companyAccount){
        CompanyAccount result = companyAccountRepository.save(companyAccount);
        if(result != null){
            return "success";
        }else{
            return "error!";
        }
    }

    @PostMapping("/update")
    public String update(@RequestBody CompanyAccount companyAccount){
        Company company = companyRepository.findByCode(companyAccount.getCode());//修改company表中的有效性
        company.setValidity(companyAccount.getValidity());
        companyRepository.save(company);

        CompanyAccount c0 = companyAccountRepository.findByCode(companyAccount.getCode());
        CompanyAccount result;
        if(c0==null){//原表没有这个公司信息
            CompanyAccount c1 = new CompanyAccount();
            c1.setName(companyAccount.getName());
            c1.setCode(companyAccount.getCode());
            String s1 = companyAccount.getValidity();
            if(s1.equals("1")){
                int num=(int) (Math.random()*(10-7+1)+7);//帐号7-10位，前5位字母，后面全是数字
                int num2=(int) (Math.random()*(12-8+1)+8);//密码8-12位，随机字母数字符号
                //int num3=(int) (Math.random()*(10-6+1)+6);//网站名6-10位，随机字母
                String username = usernameService.randomUsername(num);
                String pw = usernameService.randomGenerate(num2);
                //String wb = usernameService.randomWebsite(num3);
                //c1.setWebsite(wb);
                c1.setValidity(s1);
                c1.setWebsite(company.getUrl());
                c1.setUsername(username);
                c1.setPassword(pw);
            }
            result = companyAccountRepository.save(c1);
        }
        else{//有公司信息
            String s0 = companyAccount.getValidity();
            c0.setValidity(s0);
            if(s0.equals("1")){
                int num=(int) (Math.random()*(10-7+1)+7);//帐号7-10位，前5位字母，后面全是数字
                int num2=(int) (Math.random()*(12-8+1)+8);//密码8-12位，随机字母数字符号
                //int num3=(int) (Math.random()*(10-6+1)+6);//网站名6-10位，随机字母
                String username = usernameService.randomUsername(num);
                String pw = usernameService.randomGenerate(num2);
                //String wb = usernameService.randomWebsite(num3);
                //c0.setWebsite(wb);
                c0.setWebsite(company.getUrl());
                c0.setUsername(username);
                c0.setPassword(pw);
            }
            result = companyAccountRepository.save(c0);
        }
        /*Company code = companyRepository.findByCode(company.getCode());
        Company number = companyRepository.findByNumber(company.getNumber());
        if(code!=null)
            return"code";
        if(number!=null)
            return "number";*/
        //c0.setPerson(company.getPerson());
        if(result != null){
            return "success";
        }else{
            return "error!";
        }
    }

    @GetMapping("/findCompanyAccount/{username}")
    public CompanyAccount findCompanyAccountByUsername(@PathVariable("username") String username){
        Company c1 = companyRepository.findByUsername(username);
        if(c1==null)
            return null;
        CompanyAccount ca0 = companyAccountRepository.findByCode(c1.getCode());
        return ca0;
    }
}
