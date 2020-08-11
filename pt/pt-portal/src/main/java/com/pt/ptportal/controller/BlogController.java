package com.pt.ptportal.controller;
import com.pt.ptcommoncore.util.R;
import com.pt.ptportal.dao.BlogDao;
import com.pt.ptportal.entity.Blog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.*;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/blog")
public class BlogController {
    private final BlogDao blogDao;
    @Autowired
    public BlogController(BlogDao blogDao) {
        this.blogDao = blogDao;
    }

    @PostMapping("/addOrUpdate")
    public R addOrUpdate(@RequestBody Blog blog){

        return R.ok(blogDao.save(blog));

    }
    @DeleteMapping("/delete/{id}/{company_id}")
    public R delete(@PathVariable("id") int id,@PathVariable("company_id") String company_id){
        Blog blog = blogDao.findByBlogIdAndCompanyId(id,String.valueOf(company_id));
        blog.setState(false);
        return R.ok(blogDao.save(blog));
    }

    //降序
    @GetMapping("/findAllDesc/{page}/{size}/{company_id}")
    public R findAllDesc(@PathVariable("page") Integer page, @PathVariable("size") Integer size,@PathVariable("company_id") String company_id){
        Specification spec = new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                //1.1 获取比较的属性
                Path<Object> state =root.get("state");
                Path<Object> companyId =root.get("companyId");

                //1.2构造查询条件
                Predicate predicate = criteriaBuilder.and(criteriaBuilder.equal(state, true),criteriaBuilder.equal(companyId,String.valueOf(company_id)));
                return predicate;
            }
        };
        PageRequest request = PageRequest.of(page,size, Sort.Direction.DESC,"BlogId");
        return R.ok(blogDao.findAll(spec,request));
    }

    @GetMapping("/get/available/{company_id}")
    public R getAvailable(@PathVariable( "company_id") String company_id){
        return R.ok(blogDao.findAllByStateIsTrueAndCompanyId(String.valueOf(company_id)));
    }

    @GetMapping("/get/by-user/{id}/{company_id}")
    public R getBlogByUser(@PathVariable("id") String id,@PathVariable( "company_id") String company_id){
        return R.ok(blogDao.findAllByUserIdAndCompanyId(id,String.valueOf(company_id)));
    }
    @GetMapping("/get/by-BlogId/{id}/{company_id}")
    public R getBlogByBlogId(@PathVariable("id") int id,@PathVariable( "company_id") String company_id){
        return R.ok(blogDao.findAllByBlogIdAndCompanyId(id,String.valueOf(company_id)));
    }


}
