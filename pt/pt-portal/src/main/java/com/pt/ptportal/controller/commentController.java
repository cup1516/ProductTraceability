package com.pt.ptportal.controller;




import com.pt.ptportal.entity.News;
import com.pt.ptportal.entity.comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.*;
import java.util.List;

@RestController
@RequestMapping("/comment")
public class commentController {
    @Autowired
    com.pt.ptportal.dao.commentDao commentDao;

    @GetMapping("/findAll/{page}/{size}/{company_id}")
    public Page<News> findAll(@PathVariable("page") Integer page, @PathVariable("size") Integer size,@PathVariable("company_id") String company_id){
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
        return commentDao.findAll(spec,request);
    }
    //降序
    @GetMapping("/findAllDesc/{page}/{size}/{company_id}")
    public Page<News> findAllDesc(@PathVariable("page") Integer page, @PathVariable("size") Integer size,@PathVariable("company_id") String company_id){
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
        return commentDao.findAll(spec,request);
    }
    //查询，返回的数组类型
    @GetMapping("/findAllById/filter={id}/{company_id}")
    public List<comment> findAllById(@PathVariable("id") Integer id,@PathVariable("company_id") String company_id){
        return commentDao.findAllByIdAndCompanyIdAndStatus(id,String.valueOf(company_id),1);
    }

    //返回对象类型
    @GetMapping("/findById/{id}/{company_id}")
    public comment findById(@PathVariable("id") Integer id,@PathVariable("company_id") String company_id){
        return commentDao.findByIdAndCompanyIdAndStatus(id,String.valueOf(company_id),1);
    }



    @PostMapping("/addOrUpdate")
    public String addOrUpdate(@RequestBody comment comment ){
        com.pt.ptportal.entity.comment result = commentDao.save(comment);
        if(result !=null){
            return "success";
        }
        else {
            return "error";
        }
    }
    @DeleteMapping("/delete/{id}/{company_id}")
    public void delete(@PathVariable("id") Integer id,@PathVariable("company_id") String company_id){
        comment comment = commentDao.findByIdAndCompanyIdAndStatus(id,String.valueOf(company_id),1);
        comment.setStatus(0);
        commentDao.save(comment);

    }

}
