package com.pt.ptportal.controller;

import com.pt.ptcommoncore.util.R;
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
    public R getAll(@PathVariable("company_id") String company_id){
      return R.ok(blogCommentDao.findAllByStateIsTrueAndCompanyId(String.valueOf(company_id)));
    }

    @PostMapping("/addOrUpdate")
    public R addOrUpdate(@RequestBody BlogComments blogComments){
       return R.ok(blogCommentDao.save(blogComments));
    }

    @GetMapping("/get/by-blog/{id}/{company_id}")
    public R getCommentByBlog(@PathVariable("id")  int id,@PathVariable("company_id") String company_id){
        return R.ok(blogCommentDao.findAllByBlogIdAndCompanyIdAndState(id,String.valueOf(company_id),true));
    }
}
