package com.pt.ptmanor.service;
import com.pt.ptmanor.mapper.FinancialFormRepository;
import com.pt.ptmanor.mapper.SystemOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class FinancialFormServiceImpl implements FinancialFormService {


    @Autowired
    FinancialFormRepository financialFormRepository;


    @Autowired
    SystemOrderRepository systemOrderRepository;

    @Override
    public Page list(int pageNum, int pageRow, String companyId) {

        Specification spec = new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();

                //1.1 获取比较的属性
                Path<Object> isDeleted =root.get("delFlag");
                //1.2构造查询条件
                Predicate predicate = criteriaBuilder.equal(isDeleted, "0");

                Path<Object> sellerId =root.get("sellerId");
                Predicate predicate1 = criteriaBuilder.equal(sellerId, companyId);


                Path<Object> checkStatus =root.get("checkStatus");
                Predicate predicate2 = criteriaBuilder.equal(checkStatus, "2");
                predicates.add(predicate);
                predicates.add(predicate1);
                predicates.add(predicate2);
                return  criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        //2. 查询符合条件的数据并返回前端
        //2.1 设置根据修改时间倒叙排序
//        Pageable pageable  = PageRequest.of(pageNum-1,pageRow);
//        Page<FinancialForm> Page = financialFormRepository.findAll(spec,pageable);

        List<Sort.Order> orders = new ArrayList<>();
        orders.add(new Sort.Order(Sort.Direction.DESC,"checkTime"));
        Sort sort = Sort.by(orders);
        Pageable pageable  = PageRequest.of(pageNum-1,pageRow,sort);

        Page Page = systemOrderRepository.findAll(spec,pageable);

        return Page ;
    }

    @Override
    public Map getList() {
        return null;
    }


    @Override
    public Page findByMany(String companyId, String productName, String orderId, String buyerName, Date etime, Date stime, Integer pageNum, Integer pageRow) {

        Specification spec = new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();

                if (stime != null){
                    predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.<Date>get("checkTime"),stime));
                }
                if (etime != null){
                    predicates.add(criteriaBuilder.lessThanOrEqualTo(root.<Date>get("checkTime"),etime));
                }
                if (!StringUtils.isEmpty(orderId)){
                    Path orderId1 = root.get("orderId");
                    predicates.add(criteriaBuilder.like(orderId1.as(String.class),"%"+orderId+"%"));
                }
                if (!StringUtils.isEmpty(buyerName)){
                    Path buyerName1 = root.get("buyerName");
                    predicates.add(criteriaBuilder.like(buyerName1.as(String.class),buyerName));
                }
                if (!StringUtils.isEmpty(productName)){
                    Path productName1 = root.get("productName");
                    predicates.add(criteriaBuilder.like(productName1.as(String.class),"%"+productName+"%"));
                }
                if (!StringUtils.isEmpty(companyId)){
                    Path sellerId = root.get("sellerId");
                    predicates.add(criteriaBuilder.equal(sellerId.as(String.class),companyId));
                }
                if (!StringUtils.isEmpty(root.get("delFlag"))){
                    Path delFlag1 = root.get("delFlag");
                    predicates.add(criteriaBuilder.equal(delFlag1.as(String.class),"0"));
                }
                Path<Object> checkStatus =root.get("checkStatus");
                Predicate predicate2 = criteriaBuilder.equal(checkStatus, "2");
                predicates.add(predicate2);

                return  criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        Pageable pageable  = PageRequest.of(pageNum-1,pageRow);
        Page Page = systemOrderRepository.findAll(spec,pageable);

        return Page;
    }



}
