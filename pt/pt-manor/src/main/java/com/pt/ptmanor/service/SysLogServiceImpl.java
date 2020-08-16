package com.pt.ptmanor.service;


import com.pt.ptcommonsecurity.util.SecurityUtils;
import com.pt.ptmanor.aspect.SysLog;
import com.pt.ptmanor.aspect.SysLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class SysLogServiceImpl implements SysLogService{

    @Autowired
    SysLogRepository sysLogRepository;

    @Override
    public Page list(int pageNum, int pageRow) {


        Specification spec = new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();


                Path<Object> isDeleted =root.get("isDeleted");
                Predicate predicate1= criteriaBuilder.equal(isDeleted, 0);

                String companyId = SecurityUtils.getCompanyId();
                Path<Object> companyId1 =root.get("companyId");
                Predicate predicate2= criteriaBuilder.equal(companyId1, companyId);
                predicates.add(predicate2);
                predicates.add(predicate1);

                return  criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };

        List<Sort.Order> orders = new ArrayList<>();
        orders.add(new Sort.Order(Sort.Direction.DESC,"createDate"));
        Sort sort = Sort.by(orders);

        Pageable pageable  = PageRequest.of(pageNum-1,pageRow,sort);
        Page<SysLog> Page = sysLogRepository.findAll(spec,pageable);

        return Page ;
    }
}
