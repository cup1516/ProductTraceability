package com.pt.ptportal.controller;


import com.pt.ptcommoncore.util.R;
import com.pt.ptportal.entity.News;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.*;
import java.util.List;

@RestController
@RequestMapping("/News")
public class NewsController {
    @Autowired
    com.pt.ptportal.dao.newsDao newsDao;
    @GetMapping("/findAll/{page}/{size}/{company_id}")
    public R findAll(@PathVariable("page") Integer page, @PathVariable("size") Integer size , @PathVariable("company_id") String company_id){
        Specification spec = new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                //1.1 获取比较的属性
                Path<Object> status =root.get("status");
                Path<Object> companyId =root.get("companyId");
                //1.2构造查询条件
                Predicate predicate = criteriaBuilder.and(criteriaBuilder.equal(status, 1),criteriaBuilder.equal(companyId,String.valueOf(company_id)));
                return predicate;
            }
        };
        PageRequest request = PageRequest.of(page,size);
        return R.ok(newsDao.findAll(spec,request));
    }
    //降序
    @GetMapping("/findAllDesc/{page}/{size}/{company_id}")
    public R findAllDesc(@PathVariable("page") Integer page, @PathVariable("size") Integer size,@PathVariable("company_id") String company_id){
        Specification spec = new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                //1.1 获取比较的属性
                Path<Object> status =root.get("status");
                Path<Object> companyId =root.get("companyId");
                //1.2构造查询条件
                Predicate predicate = criteriaBuilder.and(criteriaBuilder.equal(status, 1),criteriaBuilder.equal(companyId,String.valueOf(company_id)));
                return predicate;
            }
        };
        PageRequest request = PageRequest.of(page,size, Sort.Direction.DESC,"id");
        return R.ok(newsDao.findAll(spec,request));
    }
    //查询，返回的数组类型
    @GetMapping("/findAllById/filter={id}/{company_id}")
    public R findAllById(@PathVariable("id") Integer id,@PathVariable("company_id") Integer company_id){
            return R.ok(newsDao.findAllByIdAndCompanyIdAndStatus(id,String.valueOf(company_id),1));
    }

    //返回对象类型
    @GetMapping("/findById/{id}/{company_id}")
    public R findById(@PathVariable("id") Integer id,@PathVariable("company_id") Integer company_id){
       return R.ok(newsDao.findByIdAndCompanyIdAndStatus(id,String.valueOf(company_id),1));
    }

    @PostMapping("/addOrUpdate")
    public R addOrUpdate(@RequestBody News news){
       return R.ok(newsDao.save(news));

    }
    //逻辑删除，将1变为0
    @DeleteMapping("/delete/{id}/{company_id}")
    public R delete(@PathVariable("id") Integer id,@PathVariable("company_id") Integer company_id){
       News news = newsDao.findByIdAndCompanyIdAndStatus(id,String.valueOf(company_id),1);
       news.setStatus(0);
      return R.ok(newsDao.save(news)) ;
    }
}
