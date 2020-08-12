package com.pt.ptmanor.service;

import com.pt.ptcommonsecurity.util.SecurityUtils;
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

@Service
public class SystemOrderServiceImpl implements SystemOrderService {

    @Autowired
    SystemOrderRepository systemOrderRepository;

    @Override
    public Page myList(int pageNum, int pageRow, final String companyId) {

        Specification spec = new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();

                //1.1 获取比较的属性
                Path<Object> isDeleted =root.get("delFlag");
                //1.2构造查询条件
                Predicate predicate = criteriaBuilder.equal(isDeleted, "0");

                Path<Object> sellerId =root.get("sellerId");
                Predicate predicate1= criteriaBuilder.equal(sellerId, companyId);

                String id = SecurityUtils.getId();
                Path<Object> creatorId =root.get("creatorId");
                Predicate predicate2= criteriaBuilder.equal(creatorId, id);

                predicates.add(predicate);
                predicates.add(predicate1);
                predicates.add(predicate2);

                return  criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        //2. 查询符合条件的数据并返回前端
        //2.1 设置根据创建时间倒叙排序
        List<Sort.Order> orders = new ArrayList<>();
        orders.add(new Sort.Order(Sort.Direction.DESC,"createTime"));
        Sort sort = Sort.by(orders);
        Pageable pageable  = PageRequest.of(pageNum-1,pageRow,sort);
        Page Page = systemOrderRepository.findAll(spec,pageable);

        return Page ;
    }



    @Override
    public Page toReviewList(int pageNum, int pageRow, String companyId) {
        Specification spec = new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();

                //1.1 获取比较的属性
                Path<Object> isDeleted =root.get("delFlag");
                //1.2构造查询条件
                Predicate predicate = criteriaBuilder.equal(isDeleted, "0");

                Path<Object> sellerId =root.get("sellerId");
                Predicate predicate1= criteriaBuilder.equal(sellerId, companyId);

                Path<Object> checkFlag =root.get("checkFlag");
                Predicate predicate3= criteriaBuilder.equal(checkFlag, "1");
                Predicate predicate4= criteriaBuilder.equal(checkFlag, "2");
                Predicate predicate5= criteriaBuilder.or(predicate3, predicate4);

                String id = SecurityUtils.getId();
                Path<Object> reviewerId =root.get("reviewerId");
                Predicate predicate2= criteriaBuilder.equal(reviewerId, id);


                Path<Object> checkStatus =root.get("checkStatus");
                Predicate predicate6= criteriaBuilder.equal(checkStatus, "0");
                Predicate predicate7= criteriaBuilder.equal(checkStatus, "1");
                Predicate predicate8= criteriaBuilder.equal(checkStatus, "3");
                Predicate predicate9= criteriaBuilder.or(predicate6, predicate7);
                Predicate predicate10= criteriaBuilder.or(predicate9, predicate8);

                predicates.add(predicate);
                predicates.add(predicate1);
                predicates.add(predicate2);
                predicates.add(predicate5);
                predicates.add(predicate10);

                return  criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        //2. 查询符合条件的数据并返回前端
        //2.1 设置根据创建时间倒叙排序
        List<Sort.Order> orders = new ArrayList<>();
        orders.add(new Sort.Order(Sort.Direction.DESC,"createTime"));
        Sort sort = Sort.by(orders);
        Pageable pageable  = PageRequest.of(pageNum-1,pageRow,sort);
        Page Page = systemOrderRepository.findAll(spec,pageable);

        return Page ;
    }

    @Override
    public Page finalReviewList(int pageNum, int pageRow, String companyId) {


        Specification spec = new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();

                //1.1 获取比较的属性
                Path<Object> isDeleted =root.get("delFlag");
                //1.2构造查询条件
                Predicate predicate = criteriaBuilder.equal(isDeleted, "0");

                Path<Object> sellerId =root.get("sellerId");
                Predicate predicate1= criteriaBuilder.equal(sellerId, companyId);

                Path<Object> checkFlag =root.get("checkFlag");
                Predicate predicate3= criteriaBuilder.equal(checkFlag, "2");

                predicates.add(predicate);
                predicates.add(predicate1);
                predicates.add(predicate3);

                return  criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        //2. 查询符合条件的数据并返回前端
        //2.1 设置根据创建时间倒叙排序
        List<Sort.Order> orders = new ArrayList<>();
        orders.add(new Sort.Order(Sort.Direction.DESC,"createTime"));
        Sort sort = Sort.by(orders);
        Pageable pageable  = PageRequest.of(pageNum-1,pageRow,sort);
        Page Page = systemOrderRepository.findAll(spec,pageable);

        return Page ;
    }

    @Override
    public Page toCheckList(int pageNum, int pageRow, String companyId) {
        Specification spec = new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();

                //1.1 获取比较的属性
                Path<Object> isDeleted =root.get("delFlag");
                //1.2构造查询条件
                Predicate predicate = criteriaBuilder.equal(isDeleted, "0");

                Path<Object> buyerId =root.get("buyerId");
                Predicate predicate1= criteriaBuilder.equal(buyerId, companyId);

                Path<Object> checkFlag =root.get("checkFlag");
                Predicate predicate3= criteriaBuilder.equal(checkFlag, "2");


                Path<Object> checkStatus =root.get("checkStatus");
                Predicate predicate4= criteriaBuilder.equal(checkStatus, "1");

                predicates.add(predicate);
                predicates.add(predicate1);
                predicates.add(predicate3);
                predicates.add(predicate4);

                return  criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        //2. 查询符合条件的数据并返回前端
        //2.1 设置根据创建时间倒叙排序
        List<Sort.Order> orders = new ArrayList<>();
        orders.add(new Sort.Order(Sort.Direction.DESC,"createTime"));
        Sort sort = Sort.by(orders);
        Pageable pageable  = PageRequest.of(pageNum-1,pageRow,sort);
        Page Page = systemOrderRepository.findAll(spec,pageable);

        return Page ;
    }

    @Override
    public Page finalCheckList(int pageNum, int pageRow, String companyId) {
        Specification spec = new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();

                //1.1 获取比较的属性
                Path<Object> isDeleted =root.get("delFlag");
                //1.2构造查询条件
                Predicate predicate = criteriaBuilder.equal(isDeleted, "0");

                Path<Object> buyerId =root.get("buyerId");
                Predicate predicate1= criteriaBuilder.equal(buyerId, companyId);

                Path<Object> checkStatus =root.get("checkStatus");
                Predicate predicate3= criteriaBuilder.equal(checkStatus, "2");

                predicates.add(predicate);
                predicates.add(predicate1);
                predicates.add(predicate3);

                return  criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        //2. 查询符合条件的数据并返回前端
        //2.1 设置根据创建时间倒叙排序
        List<Sort.Order> orders = new ArrayList<>();
        orders.add(new Sort.Order(Sort.Direction.DESC,"checkTime"));
        Sort sort = Sort.by(orders);
        Pageable pageable  = PageRequest.of(pageNum-1,pageRow,sort);
        Page Page = systemOrderRepository.findAll(spec,pageable);

        return Page ;
    }

    @Override
    public Page invalidOrderList(int pageNum, int pageRow, String companyId) {
        Specification spec = new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();

                //1.1 获取比较的属性
                Path<Object> isDeleted =root.get("delFlag");
                //1.2构造查询条件
                Predicate predicate = criteriaBuilder.equal(isDeleted, "0");

                Path<Object> sellerId =root.get("sellerId");
                Predicate predicate1= criteriaBuilder.equal(sellerId, companyId);

                Path<Object> checkFlag =root.get("checkFlag");
                Predicate predicate3= criteriaBuilder.equal(checkFlag, "3");

                predicates.add(predicate);
                predicates.add(predicate1);
                predicates.add(predicate3);

                return  criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        //2. 查询符合条件的数据并返回前端
        //2.1 设置根据创建时间倒叙排序
        List<Sort.Order> orders = new ArrayList<>();
        orders.add(new Sort.Order(Sort.Direction.DESC,"createTime"));
        Sort sort = Sort.by(orders);
        Pageable pageable  = PageRequest.of(pageNum-1,pageRow,sort);
        Page Page = systemOrderRepository.findAll(spec,pageable);

        return Page ;
    }

    @Override
    public Page myOrderFindByMany(String companyId, String userId, Date stime, Date etime, String checkFlag, String buyerName, String productName, String orderId, Integer pageNum, Integer pageRow) {
        Specification spec = new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (stime != null){
                    predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.<Date>get("createTime"),stime));
                }
                if (etime != null){
                    predicates.add(criteriaBuilder.lessThanOrEqualTo(root.<Date>get("createTime"),etime));
                }
                if (!StringUtils.isEmpty(buyerName)){
                    Path buyerName1 = root.get("buyerName");
                    predicates.add(criteriaBuilder.equal(buyerName1.as(String.class),buyerName));
                }
                if (!StringUtils.isEmpty(productName)){
                    Path productName1 = root.get("productName");
                    predicates.add(criteriaBuilder.like(productName1.as(String.class),"%"+productName+"%"));
                }

                if (!StringUtils.isEmpty(orderId)){
                    Path orderId1 = root.get("orderId");
                    predicates.add(criteriaBuilder.equal(orderId1.as(String.class),orderId));
                }
                if (!StringUtils.isEmpty(companyId)){
                    Path companyId1 = root.get("sellerId");
                    predicates.add(criteriaBuilder.equal(companyId1.as(String.class),companyId));
                }
                if (!StringUtils.isEmpty(checkFlag)){
                    Path checkFlag1 = root.get("checkFlag");
                    predicates.add(criteriaBuilder.equal(checkFlag1.as(String.class),checkFlag));
                }
                if (!StringUtils.isEmpty(root.get("delFlag"))){
                    Path delFlag1 = root.get("delFlag");
                    predicates.add(criteriaBuilder.equal(delFlag1.as(String.class),"0"));
                }
                if (!StringUtils.isEmpty(root.get("creatorId"))){
                    Path creatorId1 = root.get("creatorId");
                    predicates.add(criteriaBuilder.equal(creatorId1.as(String.class),userId));
                }

                return  criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };

        List<Sort.Order> orders = new ArrayList<>();
        orders.add(new Sort.Order(Sort.Direction.DESC,"createTime"));
        Sort sort = Sort.by(orders);
        Pageable pageable  = PageRequest.of(pageNum-1,pageRow,sort);

        Page Page = systemOrderRepository.findAll(spec,pageable);

        return Page;
    }

    @Override
    public Page toReviewFindByMany(String companyId, String userId, Date stime, Date etime, String creatorName, String buyerName, String productName, String orderId, Integer pageNum, Integer pageRow) {
        Specification spec = new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (stime != null){
                    predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.<Date>get("createTime"),stime));
                }
                if (etime != null){
                    predicates.add(criteriaBuilder.lessThanOrEqualTo(root.<Date>get("createTime"),etime));
                }
                if (!StringUtils.isEmpty(buyerName)){
                    Path buyerName1 = root.get("buyerName");
                    predicates.add(criteriaBuilder.equal(buyerName1.as(String.class),buyerName));
                }
                if (!StringUtils.isEmpty(productName)){
                    Path productName1 = root.get("productName");
                    predicates.add(criteriaBuilder.like(productName1.as(String.class),"%"+productName+"%"));
                }

                if (!StringUtils.isEmpty(orderId)){
                    Path orderId1 = root.get("orderId");
                    predicates.add(criteriaBuilder.equal(orderId1.as(String.class),orderId));
                }
                if (!StringUtils.isEmpty(companyId)){
                    Path companyId1 = root.get("sellerId");
                    predicates.add(criteriaBuilder.equal(companyId1.as(String.class),companyId));
                }
                if (!StringUtils.isEmpty(creatorName)){
                    Path creatorName1 = root.get("creatorName");
                    predicates.add(criteriaBuilder.equal(creatorName1.as(String.class),creatorName));
                }
                if (!StringUtils.isEmpty(root.get("delFlag"))){
                    Path delFlag1 = root.get("delFlag");
                    predicates.add(criteriaBuilder.equal(delFlag1.as(String.class),"0"));
                }
                if (!StringUtils.isEmpty(root.get("reviewerId"))){
                    Path reviewerId1 = root.get("reviewerId");
                    predicates.add(criteriaBuilder.equal(reviewerId1.as(String.class),userId));
                }


                Path<Object> checkFlag =root.get("checkFlag");
                Predicate predicate3= criteriaBuilder.equal(checkFlag, "1");
                Predicate predicate4= criteriaBuilder.equal(checkFlag, "2");
                Predicate predicate5= criteriaBuilder.or(predicate3, predicate4);
                predicates.add(predicate5);

                Path<Object> checkStatus =root.get("checkStatus");
                Predicate predicate6= criteriaBuilder.equal(checkStatus, "0");
                Predicate predicate7= criteriaBuilder.equal(checkStatus, "1");
                Predicate predicate8= criteriaBuilder.equal(checkStatus, "3");
                Predicate predicate9= criteriaBuilder.or(predicate6, predicate7);
                Predicate predicate10= criteriaBuilder.or(predicate9, predicate8);
                predicates.add(predicate10);


                return  criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };

        List<Sort.Order> orders = new ArrayList<>();
        orders.add(new Sort.Order(Sort.Direction.DESC,"createTime"));
        Sort sort = Sort.by(orders);
        Pageable pageable  = PageRequest.of(pageNum-1,pageRow,sort);

        Page Page = systemOrderRepository.findAll(spec,pageable);

        return Page;
    }

    @Override
    public Page finalReviewFindByMany(String companyId, String userId, Date stime, Date etime, String reviewerName, String buyerName, String productName, String orderId, Integer pageNum, Integer pageRow) {


        Specification spec = new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (stime != null){
                    predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.<Date>get("reviewTime"),stime));
                }
                if (etime != null){
                    predicates.add(criteriaBuilder.lessThanOrEqualTo(root.<Date>get("reviewTime"),etime));
                }
                if (!StringUtils.isEmpty(buyerName)){
                    Path buyerName1 = root.get("buyerName");
                    predicates.add(criteriaBuilder.equal(buyerName1.as(String.class),buyerName));
                }
                if (!StringUtils.isEmpty(productName)){
                    Path productName1 = root.get("productName");
                    predicates.add(criteriaBuilder.like(productName1.as(String.class),"%"+productName+"%"));
                }

                if (!StringUtils.isEmpty(orderId)){
                    Path orderId1 = root.get("orderId");
                    predicates.add(criteriaBuilder.equal(orderId1.as(String.class),orderId));
                }
                if (!StringUtils.isEmpty(companyId)){
                    Path companyId1 = root.get("sellerId");
                    predicates.add(criteriaBuilder.equal(companyId1.as(String.class),companyId));
                }
                if (!StringUtils.isEmpty(reviewerName)){
                    Path reviewerName1 = root.get("reviewerName");
                    predicates.add(criteriaBuilder.equal(reviewerName1.as(String.class),reviewerName));
                }
                if (!StringUtils.isEmpty(root.get("delFlag"))){
                    Path delFlag1 = root.get("delFlag");
                    predicates.add(criteriaBuilder.equal(delFlag1.as(String.class),"0"));
                }
                if (!StringUtils.isEmpty(root.get("reviewerId"))){
                    Path reviewerId1 = root.get("reviewerId");
                    predicates.add(criteriaBuilder.equal(reviewerId1.as(String.class),userId));
                }


                Path<Object> checkFlag =root.get("checkFlag");
                Predicate predicate4= criteriaBuilder.equal(checkFlag, "2");
                predicates.add(predicate4);

                return  criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };

        List<Sort.Order> orders = new ArrayList<>();
        orders.add(new Sort.Order(Sort.Direction.DESC,"reviewTime"));
        Sort sort = Sort.by(orders);
        Pageable pageable  = PageRequest.of(pageNum-1,pageRow,sort);

        Page Page = systemOrderRepository.findAll(spec,pageable);

        return Page;
    }

    @Override
    public Page toCheckFindByMany(String companyId, String userId, String sellerName, String productName, String orderId, Integer pageNum, Integer pageRow) {

        Specification spec = new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();



                if (!StringUtils.isEmpty(productName)){
                    Path productName1 = root.get("productName");
                    predicates.add(criteriaBuilder.like(productName1.as(String.class),"%"+productName+"%"));
                }

                if (!StringUtils.isEmpty(orderId)){
                    Path orderId1 = root.get("orderId");
                    predicates.add(criteriaBuilder.equal(orderId1.as(String.class),orderId));
                }
                if (!StringUtils.isEmpty(companyId)){
                    Path buyerId = root.get("buyerId");
                    predicates.add(criteriaBuilder.equal(buyerId.as(String.class),companyId));
                }
                if (!StringUtils.isEmpty(sellerName)){
                    Path sellerName1 = root.get("sellerName");
                    predicates.add(criteriaBuilder.equal(sellerName1.as(String.class),sellerName));
                }

                if (!StringUtils.isEmpty(root.get("delFlag"))){
                    Path delFlag1 = root.get("delFlag");
                    predicates.add(criteriaBuilder.equal(delFlag1.as(String.class),0));
                }

                Path<Object> checkStatus =root.get("checkStatus");
                Predicate predicate4= criteriaBuilder.equal(checkStatus, "1");
                predicates.add(predicate4);

                return  criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };

        List<Sort.Order> orders = new ArrayList<>();
        orders.add(new Sort.Order(Sort.Direction.DESC,"createTime"));
        Sort sort = Sort.by(orders);
        Pageable pageable  = PageRequest.of(pageNum-1,pageRow,sort);

        Page Page = systemOrderRepository.findAll(spec,pageable);

        return Page;
    }

    @Override
    public Page finalCheckFindByMnay(Date stime, Date etime,String companyId,String sellerName, String productName, String orderId, String checkerName, Integer pageNum, Integer pageRow) {
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

                if (!StringUtils.isEmpty(orderId)){
                    Path orderId1 = root.get("orderId");
                    predicates.add(criteriaBuilder.equal(orderId1.as(String.class),orderId));
                }
                if (!StringUtils.isEmpty(companyId)){
                    Path buyerId = root.get("buyerId");
                    predicates.add(criteriaBuilder.equal(buyerId.as(String.class),companyId));
                }
                if (!StringUtils.isEmpty(sellerName)){
                    Path sellerName1 = root.get("sellerName");
                    predicates.add(criteriaBuilder.equal(sellerName1.as(String.class),sellerName));
                }
                if (!StringUtils.isEmpty(checkerName)){
                    Path checkerName1 = root.get("checkerName");
                    predicates.add(criteriaBuilder.equal(checkerName1.as(String.class),checkerName));
                }

                if (!StringUtils.isEmpty(root.get("delFlag"))){
                    Path delFlag1 = root.get("delFlag");
                    predicates.add(criteriaBuilder.equal(delFlag1.as(String.class),"0"));
                }

                Path<Object> checkStatus =root.get("checkStatus");
                Predicate predicate4= criteriaBuilder.equal(checkStatus, "2");
                predicates.add(predicate4);

                return  criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };

        List<Sort.Order> orders = new ArrayList<>();
        orders.add(new Sort.Order(Sort.Direction.DESC,"checkTime"));
        Sort sort = Sort.by(orders);
        Pageable pageable  = PageRequest.of(pageNum-1,pageRow,sort);

        Page Page = systemOrderRepository.findAll(spec,pageable);

        return Page;
    }

    @Override
    public Page invalidFindByMany(String companyId, String userId, Date stime, Date etime, String reviewerName, String buyerName, String productName, String orderId, Integer pageNum, Integer pageRow) {
        Specification spec = new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (stime != null){
                    predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.<Date>get("reviewTime"),stime));
                }
                if (etime != null){
                    predicates.add(criteriaBuilder.lessThanOrEqualTo(root.<Date>get("reviewTime"),etime));
                }
                if (!StringUtils.isEmpty(buyerName)){
                    Path buyerName1 = root.get("buyerName");
                    predicates.add(criteriaBuilder.equal(buyerName1.as(String.class),buyerName));
                }
                if (!StringUtils.isEmpty(productName)){
                    Path productName1 = root.get("productName");
                    predicates.add(criteriaBuilder.like(productName1.as(String.class),"%"+productName+"%"));
                }

                if (!StringUtils.isEmpty(orderId)){
                    Path orderId1 = root.get("orderId");
                    predicates.add(criteriaBuilder.equal(orderId1.as(String.class),orderId));
                }
                if (!StringUtils.isEmpty(companyId)){
                    Path companyId1 = root.get("sellerId");
                    predicates.add(criteriaBuilder.equal(companyId1.as(String.class),companyId));
                }
                if (!StringUtils.isEmpty(reviewerName)){
                    Path reviewerName1 = root.get("reviewerName");
                    predicates.add(criteriaBuilder.equal(reviewerName1.as(String.class),reviewerName));
                }
                if (!StringUtils.isEmpty(root.get("delFlag"))){
                    Path delFlag1 = root.get("delFlag");
                    predicates.add(criteriaBuilder.equal(delFlag1.as(String.class),"0"));
                }
                if (!StringUtils.isEmpty(root.get("reviewerId"))){
                    Path reviewerId1 = root.get("reviewerId");
                    predicates.add(criteriaBuilder.equal(reviewerId1.as(String.class),userId));
                }


                Path<Object> checkFlag =root.get("checkStatus");
                Predicate predicate4= criteriaBuilder.equal(checkFlag, "3");
                predicates.add(predicate4);

                return  criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };

        List<Sort.Order> orders = new ArrayList<>();
        orders.add(new Sort.Order(Sort.Direction.DESC,"reviewTime"));
        Sort sort = Sort.by(orders);
        Pageable pageable  = PageRequest.of(pageNum-1,pageRow,sort);

        Page Page = systemOrderRepository.findAll(spec,pageable);

        return Page;
    }


}
