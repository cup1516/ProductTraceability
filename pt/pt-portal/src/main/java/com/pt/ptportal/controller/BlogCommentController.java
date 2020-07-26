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
    @GetMapping("/getAll")
    public List<BlogComments> getAll(){
      return blogCommentDao.findAllByStateIsTrueAndCompanyId("1");
    }

    @PostMapping("/addOrUpdate")
    public String addOrUpdate(@RequestBody BlogComments blogComments){
        blogComments.setCompanyId("1");
        BlogComments result = blogCommentDao.save(blogComments);
        if(result !=null){
            return "success";
        }
        else {
            return "error";
        }
    }

    @GetMapping("/get/by-blog/{id}")
    public Iterable<BlogComments> getCommentByBlog(@PathVariable("id")  int id){
        return blogCommentDao.findAllByBlogIdAndCompanyIdAndState(id,"1",true);
    }
}
