package com.pt.ptportal.controller;
import com.pt.ptcommoncore.util.R;
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
    public R findAllbyName(@PathVariable("name") String name,@PathVariable("company_id") String company_id){
        return R.ok(noticeDao.findAllByNameAndStatusAndCompanyId(name,1,String.valueOf(company_id)));
    }
    @GetMapping("/findAll/{company_id}")
    public R findAll(@PathVariable("company_id") String company_id){
        return R.ok(noticeDao.findAllByStatusAndIsCheckAndCompanyId(1,2,String.valueOf(company_id)));
    }
    @GetMapping("/findAll/filter={id}/{company_id}")
    public R findByIdAndCompanyId(@PathVariable("id") Integer id,@PathVariable("company_id") String company_id){
        return R.ok(noticeDao.findByIdAndCompanyIdAndStatus(id,String.valueOf(company_id),1).get());
    }
    //送审
    @PostMapping("/tocheck")
    public R tocheck( @RequestBody notice notice){
        notice.setIsCheck(1);
        noticeDao.save(notice);
        return R.ok(notice);
    }
    //撤回审核
    @PostMapping("/backCheck")
    public R backCheck( @RequestBody notice notice){
        notice.setIsCheck(0);
        noticeDao.save(notice);
        return R.ok(notice);
    }
    //审核不通过
    @PostMapping("/backCheck3")
    public R backCheck3( @RequestBody notice notice){
        notice.setIsCheck(3);
        noticeDao.save(notice);
        return R.ok(notice);
    }
    //审核通过
    @PostMapping("/backCheck2")
    public R backCheck2( @RequestBody notice notice){
        notice.setIsCheck(2);
        noticeDao.save(notice);
        return R.ok(notice);
    }

    //降序 查找所有审核通过的宣传
    @GetMapping("/findAllDesc/{page}/{size}/{company_id}")
    public R findAllDesc(@PathVariable("page") Integer page, @PathVariable("size") Integer size,@PathVariable("company_id") String company_id){
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
        return R.ok(noticeDao.findAll(spec,request));
    }
    //查找当前用户的所有宣传内容
    @GetMapping("/findAllNotice/{page}/{size}/{user_name}/{company_id}")
    public R findAllNotice(@PathVariable("page") Integer page, @PathVariable("size") Integer size,@PathVariable String user_name,@PathVariable("company_id") String company_id){
        Specification spec = new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Path<Object> status =root.get("status");
                Path<Object> companyId = root.get("companyId");
                Path<Object> name = root.get("name");

                //1.2构造查询条件
                Predicate predicate =criteriaBuilder.and(criteriaBuilder.equal(status, 1),criteriaBuilder.equal(name,String.valueOf(user_name)),criteriaBuilder.equal(companyId,String.valueOf(company_id))) ;
                return predicate;
            }
        };
        PageRequest request = PageRequest.of(page,size);
        return R.ok(noticeDao.findAll(spec,request));
    }
    //审核未通过
    @GetMapping("/findAll/{page}/{size}/{company_id}")
    public R findAll(@PathVariable("page") Integer page, @PathVariable("size") Integer size,@PathVariable("company_id") String company_id){
        Specification spec = new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Path<Object> status =root.get("status");
                Path<Object> isCheck = root.get("isCheck");
                Path<Object> companyId = root.get("companyId");

                //1.2构造查询条件
                Predicate predicate =criteriaBuilder.and(criteriaBuilder.equal(status, 1),criteriaBuilder.equal(isCheck,1),criteriaBuilder.equal(companyId,String.valueOf(company_id))) ;
                return predicate;
            }
        };
        PageRequest request = PageRequest.of(page,size);
        return R.ok(noticeDao.findAll(spec,request));
    }
    //查询
    @GetMapping("/findById/{id}/{company_id}")
    public R findById(@PathVariable("id") Integer id,@PathVariable("company_id") String company_id){
        return R.ok(noticeDao.findByIdAndCompanyIdAndStatus(id,String.valueOf(company_id),1).get());
    }
    //添加
    @PostMapping("/addOrUpdate")
    public R addOrUpdate(@RequestBody notice notice ){
        return R.ok(noticeDao.save(notice));
    }
    //删除
    @DeleteMapping("/delete/{id}/{company_id}")
    public R delete(@PathVariable("id") Integer id,@PathVariable("company_id") String company_id){
        notice notice = noticeDao.findByIdAndCompanyIdAndStatus(id,String.valueOf(company_id),1).get();
        notice.setStatus(0);
        return R.ok(noticeDao.save(notice));
    }

}
