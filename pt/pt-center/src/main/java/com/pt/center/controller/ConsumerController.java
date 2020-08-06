package com.pt.center.controller;


import com.pt.center.newentity.Company;
import com.pt.center.repository.ConsumerRepository;
import com.pt.center.newentity.Consumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/consumer")
public class ConsumerController {

    @Autowired
    private ConsumerRepository consumerRepository;

    @GetMapping("/findAll/{page}/{size}")
    public Page<Consumer> findAll(@PathVariable("page") Integer page, @PathVariable("size") Integer size){
        PageRequest request = PageRequest.of(page,size);
        return consumerRepository.findAll(request);
    }

    @DeleteMapping("/deleteByPhone/{phone}")
    void deleta(@PathVariable("phone") String phone){
        consumerRepository.deleteByPhone(phone);
    }

    @PostMapping("/update")
    public String update(@RequestBody Consumer consumer){
        //Company c0 = companyRepository.findByUsername(company.getUsername());
        Consumer c0 = consumerRepository.findByPhone(consumer.getPhone());
        c0.setAddress(consumer.getAddress());
        c0.setCode(consumer.getCode());
        c0.setName(consumer.getName());
        c0.setProduct(consumer.getProduct());
        c0.setProduct_company(consumer.getProduct_company());
        Consumer result = consumerRepository.save(c0);
        if(result != null){
            return "success";
        }else{
            return "error!";
        }
    }
}
