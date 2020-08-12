package com.pt.ptmanor.service;


import com.pt.ptmanor.mapper.FinancialFormRepository;
import com.pt.ptmanor.mapper.SystemOrderRepository;
import com.pt.ptmanor.pojo.FinancialForm;
import com.pt.ptmanor.pojo.SystemOrder;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;


@Service
public  class ChartsServiceImpl implements ChartsService {



    @Autowired
    private FinancialFormRepository financialFormRepository;


    @Autowired
    SystemOrderRepository systemOrderRepository;


    @Override
    public Map getFinancialList(String companyId) {


        Specification spec = new Specification() {
            @Override
            public javax.persistence.criteria.Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<javax.persistence.criteria.Predicate> predicates = new ArrayList<>();

                Path<Object> checkStatus =root.get("checkStatus");
                javax.persistence.criteria.Predicate predicate4= criteriaBuilder.equal(checkStatus, "2");

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

        List<SystemOrder> all = systemOrderRepository.findAll(spec);

        List<Float> money = new ArrayList<>();

        int i;
        for (i=0;i<12;i++){
            Float monthMoney = 0.0f;
            for (SystemOrder systemOrder:all){

                Calendar ca = Calendar.getInstance();
                ca.setTime(systemOrder.getCheckTime());

                int month = ca.get(Calendar.MONTH);//第几个月
                if (month==i){
                    monthMoney = monthMoney+systemOrder.getProductTotal();
                }
            }
            money.add(monthMoney);
        }
        Map map= new HashMap();
        map.put("moneyList",money);
        return map;
    }


    @Override
    public Map financialFindList( String year,String buyerName,String companyId) {


        List<FinancialForm> byIsDeletedAndAndBuyerName = financialFormRepository.findByIsDeletedAndAndBuyerNameAndYearAndCompanyId(0, buyerName,year,companyId);
        List<Float> money = new ArrayList<>();
        int i;
        for (i=0;i<12;i++){
            Float monthMoney = 0.0f;
            for (FinancialForm financialForm:byIsDeletedAndAndBuyerName){

                Calendar ca = Calendar.getInstance();
                ca.setTime(financialForm.getDate());

                int month = ca.get(Calendar.MONTH);//第几个月
                if (month==i){
                    monthMoney = monthMoney+financialForm.getMoney();
                }
            }
            money.add(monthMoney);
        }
        Map map= new HashMap();
        map.put("moneyList",money);

        return map;
    }

    @Override
    public Map findByMany(Date year, String buyerName, String productName,String companyId) {

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
                javax.persistence.criteria.Predicate predicate4= criteriaBuilder.equal(checkStatus, "2");

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

        List<SystemOrder> all = systemOrderRepository.findAll(spec);
        List<Float> money = new ArrayList<>();
        int i;
        for (i=0;i<12;i++){
            Float monthMoney = 0.0f;
            for (SystemOrder systemOrder:all){

                Calendar ca = Calendar.getInstance();
                ca.setTime(systemOrder.getCheckTime());

                int month = ca.get(Calendar.MONTH);//第几个月
                if (month==i){
                    monthMoney = monthMoney+systemOrder.getProductTotal();
                }
            }
            money.add(monthMoney);
        }
        Map map= new HashMap();
        map.put("moneyList",money);

        return map;
    }


}
