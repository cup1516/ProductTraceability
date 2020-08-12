package com.pt.ptmanor.service.painting;


import com.pt.ptmanor.mapper.painting.CropsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class CropsServiceImpl implements CropsService {

    @Autowired
    CropsRepository cropsRepository;

    @Override
    public Page list(int pageNum, int pageRow,String companyId) {
        //1.自定义查询条件 （idDelete == 0）
        Specification spec = new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();

                //1.1 获取比较的属性
                Path<Object> isDeleted =root.get("isDeleted");
                //1.2构造查询条件
                Predicate predicate = criteriaBuilder.equal(isDeleted, 0);

                Path<Object> companyId1 =root.get("companyId");
                Predicate predicate1 = criteriaBuilder.equal(companyId1, companyId);
                predicates.add(predicate);
                predicates.add(predicate1);

                return  criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        //2. 查询符合条件的数据并返回前端
        Pageable pageable  = PageRequest.of(pageNum-1,pageRow);
        Page Page = cropsRepository.findAll(spec,pageable);

        return Page ;
    }


    @Override
    public Page findByMany(String name, Integer pageNum, Integer pageRow,String companyId) {
        Specification spec = new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();

                if (!StringUtils.isEmpty(name)){
                    Path name1 = root.get("name");
                    predicates.add(criteriaBuilder.like(name1.as(String.class),"%"+name+"%"));
                }

                if (!StringUtils.isEmpty(root.get("isDeleted"))){
                    Path isDeleted1 = root.get("isDeleted");
                    predicates.add(criteriaBuilder.equal(isDeleted1.as(Integer.class),0));
                }
                if (!StringUtils.isEmpty(root.get("companyId"))){
                    Path companyId1 = root.get("companyId");
                    predicates.add(criteriaBuilder.equal(companyId1.as(String.class),companyId));
                }

                return  criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        Pageable pageable  = PageRequest.of(pageNum-1,pageRow);
        Page Page = cropsRepository.findAll(spec,pageable);
        return Page;
    }
}
