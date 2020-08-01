package com.pt.ptportal.controller;

import com.pt.ptportal.dao.BlogCommentDao;
import com.pt.ptportal.entity.BlogComments;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.springframework.http.HttpStatus.NOT_MODIFIED;
import static org.springframework.http.HttpStatus.OK;

@Slf4j
@RestController
@RequestMapping(value = "/Blogcomment")
public class BlogCommentController {
    private final BlogCommentDao blogCommentDao;
    @Autowired
    public BlogCommentController(BlogCommentDao commentDao) {
        this.blogCommentDao = commentDao;
    }

    @GetMapping("/getAll/{company_id}")
    public List<BlogComments> getAll(@PathVariable("company_id") String company_id){
      return blogCommentDao.findAllByStateIsTrueAndCompanyId(String.valueOf(company_id));
    }

    @PostMapping("/addOrUpdate")
    public String addOrUpdate(@RequestBody BlogComments blogComments){
        BlogComments result = blogCommentDao.save(blogComments);
        if(result !=null){
            return "success";
        }
        else {
            return "error";
        }
    }

    @GetMapping("/get/by-blog/{id}/{company_id}")
    public Iterable<BlogComments> getCommentByBlog(@PathVariable("id")  int id,@PathVariable("company_id") String company_id){
        return blogCommentDao.findAllByBlogIdAndCompanyIdAndState(id,String.valueOf(company_id),true);
    }
}
