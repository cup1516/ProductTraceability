package com.pt.center.controller;

import com.pt.center.newentity.Company;
import com.pt.center.newentity.Order;
import com.pt.center.repository.ConsumerRepository;
import com.pt.center.repository.CodeRepository;
import com.pt.center.newentity.Code;
import com.pt.center.newentity.Consumer;
import com.pt.center.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/centerCode")
public class CodeController {
    @Autowired
    private ConsumerRepository consumerRepository;

    @Autowired
    private CodeRepository codeRepository;

    @GetMapping("/findCode/{code}")
    public String findCode(@PathVariable("code") String testcode){
        String resu;
        if(testcode.indexOf("+") == -1){//不包含加号，只有防伪码
            List<Code> res = codeRepository.findByCode(testcode);
            if(res.size() == 0) {
                resu = "您所查询的防伪码不存在，谨防假冒!";
                return resu;
            }
            else{
                String product = res.get(0).getProduct();
                String comp = res.get(0).getCompany();
                String times = res.get(0).getTimes();
                Integer tt = Integer.valueOf(times);
                tt = tt + 1;
                res.get(0).setTimes(String.valueOf(tt));//更新查询次数
                codeRepository.save(res.get(0));
                resu = "该产品为真品。产品:" + product + "  公司：" + comp + "  查询次数：" + tt;
                return resu;
            }
        }
        else{
            String[] split = testcode.split("\\+");
            List<Code> res = codeRepository.findByCode(split[1]);
            if(res.size() == 0) {
                resu = "您所查询的防伪码不存在，谨防假冒!";
                return resu;
            }
            else{
                String product = res.get(0).getProduct();
                String comp = res.get(0).getCompany();
                String times = res.get(0).getTimes();
                Integer tt = Integer.valueOf(times);
                tt = tt + 1;
                res.get(0).setTimes(String.valueOf(tt));//更新查询次数
                codeRepository.save(res.get(0));
                resu = "该产品为真品。产品:" + product + "  公司：" + comp + "  查询次数：" + tt;


                Consumer consumer0 = consumerRepository.findByPhone(split[0]);//设置消费者查询防伪码的信息
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
                consumer0.setQuery_time(df.format(new Date()));
                consumer0.setCode(split[1]);
                consumer0.setProduct(product);
                consumer0.setProduct_company(comp);
                String points = consumer0.getPoints();
                Integer newp = Integer.valueOf(points);
                newp = newp + 1;
                if(times == "0")
                    consumer0.setPoints(String.valueOf(newp));//更新积分
                consumerRepository.save(consumer0);
                return resu;
            }
        }
    }

    @PostMapping("/image")
    public String coversUpload(MultipartFile file) throws Exception {
        String folder = "D:/workspace/img";
        File imageFolder = new File(folder);
        File f = new File(imageFolder, StringUtils.getRandomString(6) + file.getOriginalFilename()
                .substring(file.getOriginalFilename().length() - 4));
        if (!f.getParentFile().exists())
            f.getParentFile().mkdirs();
        try {
            file.transferTo(f);
            String imgURL = "http://localhost:9017/centerCode/image/" + f.getName();
            return imgURL;
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    @PostMapping("/save")
    public String save(@RequestBody Code code){
        Code result = codeRepository.save(code);
        //Company result = companyRepository.save(company);
        if(result != null){
            return "success";
        }else{
            return "error!";
        }
    }

    @DeleteMapping("/deleteById/{id}")
    void deleta(@PathVariable("id") Integer id){
        codeRepository.deleteById(id);
    }

    @PostMapping("/update")
    public String update(@RequestBody Code code){

        List<Code> res = codeRepository.findByCode(code.getCode());
        //res.get(0).setCode(code.getCode());
        res.get(0).setCompany(code.getCompany());
        res.get(0).setImage(code.getImage());
        res.get(0).setProduct(code.getProduct());
        res.get(0).setTimes(code.getTimes());
        Code result = codeRepository.save(res.get(0));
        if(result != null){
            return "success";
        }else{
            return "error!";
        }
    }

    @GetMapping("/findAll/{page}/{size}")
    public Page<Code> findAll(@PathVariable("page") Integer page, @PathVariable("size") Integer size){
        PageRequest request = PageRequest.of(page,size);
        return codeRepository.findAll(request);
    }


}
