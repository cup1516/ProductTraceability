package com.pt.ptmanor.service;

import com.pt.ptmanor.mapper.SaleAmountRepository;
import com.pt.ptmanor.mapper.SystemOrderRepository;
import com.pt.ptmanor.pojo.Product;
import com.pt.ptmanor.pojo.SaleAmount;
import com.pt.ptmanor.pojo.SystemOrder;
import com.pt.ptmanor.pojo.product.Invoice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.*;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class SaleAmountServiceImpl implements SaleAmountService {

    @Autowired
    SaleAmountRepository saleAmountRepository;

    @Autowired
    SystemOrderRepository systemOrderRepository;

    @Override
    public Page list(int pageNum, int pageRow,String companyId) {

        //1.自定义查询条件 （idDelete == 0）
        Specification spec = new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                //1.1 获取比较的属性
                Path<Object> delFlag =root.get("delFlag");
                //1.2构造查询条件
                Predicate predicate = criteriaBuilder.equal(delFlag, "0");
                Path<Object> sellerId =root.get("sellerId");
                Predicate predicate1 = criteriaBuilder.equal(sellerId, companyId);

                Path<Object> checkStatus =root.get("checkStatus");
                Predicate predicate2 = criteriaBuilder.equal(checkStatus, "3");

                predicates.add(predicate);
                predicates.add(predicate1);
                predicates.add(predicate2);
                return  criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        List<Sort.Order> orders = new ArrayList<>();
        orders.add(new Sort.Order(Sort.Direction.DESC,"checkTime"));
        Sort sort = Sort.by(orders);
        Pageable pageable  = PageRequest.of(pageNum-1,pageRow,sort);
        Page<Product> Page = systemOrderRepository.findAll(spec,pageable);
        return Page ;
    }


    @Override
    public Page findByMany(String companyId,String productName, Date stime, Date etime, Integer pageNum, Integer pageRow) {
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
                if (!StringUtils.isEmpty(productName)){
                    Path productName1 = root.get("productName");
                    predicates.add(criteriaBuilder.like(productName1.as(String.class),"%"+productName+"%"));
                }
                if (!StringUtils.isEmpty(root.get("delFlag"))){
                    Path delFlag1 = root.get("delFlag");
                    predicates.add(criteriaBuilder.equal(delFlag1.as(String.class),"0"));
                }
                if (!StringUtils.isEmpty(root.get("sellerId"))){
                    Path sellerId = root.get("sellerId");
                    predicates.add(criteriaBuilder.equal(sellerId.as(String.class),companyId));
                }

                Path<Object> checkStatus =root.get("checkStatus");
                Predicate predicate2 = criteriaBuilder.equal(checkStatus, "3");
                predicates.add(predicate2);

                return  criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        Pageable pageable  = PageRequest.of(pageNum-1,pageRow);
        Page<Invoice> Page = systemOrderRepository.findAll(spec,pageable);

        return Page;
    }


    @Override
    public Map chart(String companyId) {
        Specification spec = new Specification() {
            @Override
            public javax.persistence.criteria.Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<javax.persistence.criteria.Predicate> predicates = new ArrayList<>();

                Path<Object> checkStatus =root.get("checkStatus");
                javax.persistence.criteria.Predicate predicate4= criteriaBuilder.equal(checkStatus, "3");

                Path<Object> delFlag =root.get("delFlag");
                javax.persistence.criteria.Predicate predicate3= criteriaBuilder.equal(delFlag, "0");
                predicates.add(predicate4);

                String s1;
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
                Date date = new Date();
                s1 = sdf.format(date);
                int year = Integer.valueOf(s1).intValue();
                Calendar ca = Calendar.getInstance();
                ca.setTime(date);
                ca.set(year,1,1,0,0,0);
                Date date1 = ca.getTime();

                ca.add(Calendar.YEAR,+1);
                Date date2 = ca.getTime();
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.<Date>get("checkTime"),date1));

                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.<Date>get("checkTime"),date2));

                return  criteriaBuilder.and(predicates.toArray(new javax.persistence.criteria.Predicate[predicates.size()]));
            }
        };

        List<SystemOrder> list = systemOrderRepository.findAll(spec);

        List<Float> money = new ArrayList<>();
        int i;
        for (i=0;i<12;i++){
            Float all =  0.0f ;

            for (SystemOrder systemOrder:list){

                Calendar ca = Calendar.getInstance();
                ca.setTime(systemOrder.getCheckTime());

                int month = ca.get(Calendar.MONTH);//第几个月
                if (month==i){
                     all = all+systemOrder.getProductAmount();
                }
            }
            money.add(all);
        }
        Map map= new HashMap();
        map.put("list",money);
        return map;
    }


    @Override
    public Map findChart(Date year, String productName, String buyerName,String companyId) {

        String s1;
        Integer year1 ;
        if (year==null){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
            Date date = new Date();
            s1 = sdf.format(date);
            year1 = Integer.valueOf(s1).intValue();

        }else {
            System.out.println(year.toString());
            System.out.println(year);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
            s1 = sdf.format(year);
            year1 = Integer.valueOf(s1).intValue();

        }

        Specification spec = new Specification() {
            @Override
            public javax.persistence.criteria.Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<javax.persistence.criteria.Predicate> predicates = new ArrayList<>();

                Path<Object> checkStatus =root.get("checkStatus");
                javax.persistence.criteria.Predicate predicate4= criteriaBuilder.equal(checkStatus, "3");

                Path<Object> delFlag =root.get("delFlag");
                javax.persistence.criteria.Predicate predicate3= criteriaBuilder.equal(delFlag, "0");

                if (!StringUtils.isEmpty(buyerName)){
                    Path buyerName1 = root.get("buyerName");
                    predicates.add(criteriaBuilder.equal(buyerName1.as(String.class),buyerName));
                }

                if (!StringUtils.isEmpty(productName)){
                    Path productName1 = root.get("productName");
                    predicates.add(criteriaBuilder.equal(productName1.as(String.class),productName));
                }

                predicates.add(predicate4);
                predicates.add(predicate3);


                Calendar ca = Calendar.getInstance();
                ca.set(year1,1,1,0,0,0);
                Date date1 = ca.getTime();
                ca.add(Calendar.YEAR,+1);
                Date date2 = ca.getTime();
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.<Date>get("checkTime"),date1));
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.<Date>get("checkTime"),date2));

                return  criteriaBuilder.and(predicates.toArray(new javax.persistence.criteria.Predicate[predicates.size()]));
            }
        };
        List<SystemOrder> list = systemOrderRepository.findAll(spec);
        List<Float> money = new ArrayList<>();
        int i;
        for (i=0;i<12;i++){
            Float all =  0.0f ;
            for (SystemOrder systemOrder:list){

                Calendar ca = Calendar.getInstance();
                ca.setTime(systemOrder.getCheckTime());

                int month = ca.get(Calendar.MONTH);//第几个月
                if (month==i){
                    all = all+systemOrder.getProductAmount();
                }
            }
            money.add(all);
        }
        Map map= new HashMap();
        map.put("list",money);
        return map;

    }


}
