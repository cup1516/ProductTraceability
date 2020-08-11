package com.pt.ptportal.controller;
import com.pt.ptcommoncore.util.R;
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
    @GetMapping("/findAll/{page}/{size}/{company_id}")
    public R findAll(@PathVariable("page") Integer page, @PathVariable("size") Integer size,@PathVariable("company_id") String company_id){
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
        return R.ok(announcementDao.findAll(spec,request)) ;
    }
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
        Pageable pageable = PageRequest.of(page,size, Sort.Direction.DESC,"id");
        return R.ok(announcementDao.findAll(spec,pageable));

    }

    //查询，返回的数组类型
    @GetMapping("/findAllById/filter={id}/{company_id}")
    public R findAllById(@PathVariable("id") Integer id,@PathVariable("company_id") String company_id){

        if(announcementDao.findById(id).get().getStatus()==1){
            return  R.ok(announcementDao.findAllByIdAndCompanyId(id,String.valueOf(company_id)));
        }
        else {
            return null;
        }
    }

    @GetMapping("/findById/{id}/{company_id}")
    public R findById(@PathVariable("id") Integer id,@PathVariable("company_id") String company_id){
        if(announcementDao.findById(id).get().getStatus()==1){
            return  R.ok(announcementDao.findAllByIdAndCompanyId(id,String.valueOf(company_id)));
        }
        else {
            return null;
        }    }

    @PostMapping("/addOrUpdate")
    public R addOrUpdate(@RequestBody Announcement announcement){
       return R.ok(announcementDao.save(announcement));
    }
    //逻辑删除，将1变为0
    @DeleteMapping("/delete/{id}/{company_id}")
    public R delete(@PathVariable("id") Integer id,@PathVariable("company_id") String company_id){
        Announcement announcement = announcementDao.findByIdAndCompanyId(id,String.valueOf(company_id)).get();
        announcement.setStatus(0);
        return R.ok(announcementDao.save(announcement));
    }

}
