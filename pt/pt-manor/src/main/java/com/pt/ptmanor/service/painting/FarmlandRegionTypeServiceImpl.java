package com.pt.ptmanor.service.painting;


import com.pt.ptmanor.mapper.painting.FarmlandRegionTypeRepository;
import com.pt.ptmanor.pojo.product.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;

@Service
public class FarmlandRegionTypeServiceImpl  implements  FarmlandRegionTypeService{

    @Autowired
    FarmlandRegionTypeRepository farmlandRegionTypeRepository;

    @Override
    public Page list(int pageNum, int pageRow) {
        //1.自定义查询条件 （idDelete == 0）
        Specification spec = new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                //1.1 获取比较的属性
                Path<Object> isDeleted =root.get("isDeleted");
                //1.2构造查询条件
                Predicate predicate = criteriaBuilder.equal(isDeleted, 0);
                return predicate;
            }
        };
        //2. 查询符合条件的数据并返回前端
        Pageable pageable  = PageRequest.of(pageNum-1,pageRow);
        Page<Product> Page = farmlandRegionTypeRepository.findAll(spec,pageable);

        return Page ;
    }
}
