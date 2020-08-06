package com.pt.ptportal.controller;
import com.pt.ptportal.entity.notice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.*;
import java.util.List;

@RestController
@RequestMapping("/notice")
public class noticeController {

    @Autowired
    com.pt.ptportal.dao.noticeDao noticeDao;
    @GetMapping("/findAll/{name}/{company_id}")
    public List<notice> findAllbyName(@PathVariable("name") String name,@PathVariable("company_id") String company_id){
        return noticeDao.findAllByNameAndStatusAndCompanyId(name,1,String.valueOf(company_id));
    }
    @GetMapping("/findAll/{company_id}")
    public List<notice> findAll(@PathVariable("company_id") String company_id){
        return noticeDao.findAllByStatusAndIsCheckAndCompanyId(1,2,String.valueOf(company_id));
    }
    @PostMapping("/tocheck")
    public notice tocheck( @RequestBody notice notice){
        notice.setIsCheck(1);
        noticeDao.save(notice);
        return notice;
    }
    @PostMapping("/backCheck")
    public notice backCheck( @RequestBody notice notice){
        notice.setIsCheck(0);
        noticeDao.save(notice);
        return notice;
    }
    @PostMapping("/backCheck3")
    public notice backCheck3( @RequestBody notice notice){
        notice.setIsCheck(3);
        noticeDao.save(notice);
        return notice;
    }
    @PostMapping("/backCheck2")
    public notice backCheck2( @RequestBody notice notice){
        notice.setIsCheck(2);
        noticeDao.save(notice);
        return notice;
    }

    //降序
    @GetMapping("/findAllDesc/{page}/{size}/{company_id}")
    public Page<notice> findAllDesc(@PathVariable("page") Integer page, @PathVariable("size") Integer size,@PathVariable("company_id") String company_id){
         Specification spec = new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Path<Object> status =root.get("status");
                Path<Object> isCheck = root.get("isCheck");
                Path<Object> companyId = root.get("companyId");

                //1.2构造查询条件
                Predicate predicate =criteriaBuilder.and(criteriaBuilder.equal(status, 1),criteriaBuilder.equal(isCheck,2),criteriaBuilder.equal(companyId,String.valueOf(company_id))) ;
                return predicate;
            }
        };
        PageRequest request = PageRequest.of(page,size, Sort.Direction.DESC,"id");
        return noticeDao.findAll(spec,request);
    }
    @GetMapping("/findAll/{page}/{size}/{company_id}")
    public Page<notice> findAll(@PathVariable("page") Integer page, @PathVariable("size") Integer size,@PathVariable("company_id") String company_id){
        Specification spec = new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Path<Object> status =root.get("status");
                Path<Object> isCheck = root.get("isCheck");
                Path<Object> companyId = root.get("companyId");

                //1.2构造查询条件
                Predicate predicate =criteriaBuilder.and(criteriaBuilder.equal(status, 1),criteriaBuilder.equal(isCheck,3),criteriaBuilder.equal(companyId,String.valueOf(company_id))) ;
                return predicate;
            }
        };
        PageRequest request = PageRequest.of(page,size);
        return noticeDao.findAll(spec,request);
    }
    @GetMapping("/findById/{id}/{company_id}")
    public notice findById(@PathVariable("id") Integer id,@PathVariable("company_id") String company_id){
        return noticeDao.findByIdAndCompanyIdAndStatus(id,String.valueOf(company_id),1).get();
    }
    @PostMapping("/addOrUpdate")
    public String addOrUpdate(@RequestBody notice notice ){

        notice result = noticeDao.save(notice);
        if(result !=null){
            return "success";
        }
        else {
            return "error";
        }
    }
    @DeleteMapping("/delete/{id}/{company_id}")
    public void delete(@PathVariable("id") Integer id,@PathVariable("company_id") String company_id){
        notice notice = noticeDao.findByIdAndCompanyIdAndStatus(id,String.valueOf(company_id),1).get();
        notice.setStatus(0);
        noticeDao.save(notice);

    }

}
