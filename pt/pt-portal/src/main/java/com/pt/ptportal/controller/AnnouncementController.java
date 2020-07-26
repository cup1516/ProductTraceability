package com.pt.ptportal.controller;
import com.pt.ptcommonsecurity.util.SecurityUtils;
import com.pt.ptportal.dao.AnnouncementDao;
import com.pt.ptportal.entity.Announcement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.*;
import java.util.List;

@RestController
@RequestMapping("/Announcement")
public class AnnouncementController {
    @Autowired
    AnnouncementDao announcementDao;
    @GetMapping("/findAll/{page}/{size}")
    public Page findAll(@PathVariable("page") Integer page, @PathVariable("size") Integer size){
        Specification spec = new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                //1.1 获取比较的属性
                Path<Object> status =root.get("status");
                Path<Object> companyId =root.get("companyId");

                //1.2构造查询条件
                Predicate predicate = criteriaBuilder.and(criteriaBuilder.equal(status, 1),criteriaBuilder.equal(companyId,1));
                return predicate;
            }
        };
        PageRequest request = PageRequest.of(page,size);
        return announcementDao.findAll(spec,request);
    }
    @GetMapping("/findAllDesc/{page}/{size}")
    public Page findAllDesc(@PathVariable("page") Integer page, @PathVariable("size") Integer size){
        Specification spec = new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                //1.1 获取比较的属性
                Path<Object> status =root.get("status");
                Path<Object> companyId =root.get("companyId");
                //1.2构造查询条件
                Predicate predicate = criteriaBuilder.and(criteriaBuilder.equal(status, 1),criteriaBuilder.equal(companyId,1));
                return predicate;
            }
        };
        Pageable pageable = PageRequest.of(page,size, Sort.Direction.DESC,"id");
        return announcementDao.findAll(spec,pageable);

    }

    //查询，返回的数组类型
    @GetMapping("/findAllById/filter={id}")
    public List<Announcement> findAllById(@PathVariable("id") Integer id){
        //使用工具类获取企业的id，待修改
        SecurityUtils.getNickName();
        if(announcementDao.findById(id).get().getStatus()==1){
            return  announcementDao.findAllByIdAndCompanyId(id,"1");
        }
        else {
            return null;
        }
    }

    @GetMapping("/findById/{id}")
    public Announcement findById(@PathVariable("id") Integer id){
            return announcementDao.findByIdAndCompanyId(id,"1").get();
    }

    @PostMapping("/addOrUpdate")
    public String addOrUpdate(@RequestBody Announcement announcement){
        announcement.setCompanyId("1");
        Announcement result = announcementDao.save(announcement);
        if(result !=null){
            return "success";
        }
        else {
            return "error";
        }
    }
    //逻辑删除，将1变为0
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable("id") Integer id){
        Announcement announcement = announcementDao.findByIdAndCompanyId(id,"1").get();
        announcement.setStatus(0);
        announcementDao.save(announcement);
        //newsDao.deleteById(id);
    }

}
