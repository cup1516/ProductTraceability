package com.pt.center.controller;

import com.pt.center.repository.OrderRepository;
import com.pt.center.newentity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderRepository orderRepository;

    @DeleteMapping("/deleteByNumber/{number}")
    void deleta(@PathVariable("number") String number){
        orderRepository.deleteByNumber(number);
    }

    @GetMapping("/findAll/{page}/{size}")
    public Page<Order> findAll(@PathVariable("page") Integer page, @PathVariable("size") Integer size){
        PageRequest request = PageRequest.of(page,size);
        return orderRepository.findAll(request);
    }

    @PostMapping("/save")
    public String save(@RequestBody Order order){
        Order result = orderRepository.save(order);
        if(result != null){
            return "success";
        }else{
            return "error!";
        }
    }

    @PostMapping("/update")
    public String update(@RequestBody Order order){

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式

        Order or0 = orderRepository.findByNumber(order.getNumber());
        or0.setState(order.getState());
        or0.setTotalprice(order.getTotalprice());
        or0.setQuantity(order.getQuantity());
        or0.setSingleprice(order.getSingleprice());
        or0.setProduct(order.getProduct());
        or0.setCreate_time(df.format(new Date()));
        or0.setCompany_name(order.getCompany_name());
        or0.setCompany_code(order.getCompany_code());
        or0.setAuditor(order.getAuditor());
        Order result = orderRepository.save(or0);
        if(result != null){
            return "success";
        }else{
            return "error!";
        }
    }

    @PostMapping("/updateState")
    public String updateState(@RequestBody Order order){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        Order or0 = orderRepository.findByNumber(order.getNumber());
        or0.setState(order.getState());
        or0.setCreate_time(df.format(new Date()));
        or0.setAuditor(order.getAuditor());
        Order result = orderRepository.save(or0);
        if(result != null){
            return "success";
        }else{
            return "error!";
        }
    }
}
