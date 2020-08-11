package com.pt.ptmanor.service;

import org.springframework.data.domain.Page;

import java.util.Date;

public interface SystemOrderService {

    Page myList(int pageNum, int pageRow, String companyId);


    Page toReviewList(int pageNum, int pageRow, String companyId);


    Page finalReviewList(int pageNum, int pageRow, String companyId);


    Page toCheckList(int pageNum, int pageRow, String companyId);

    Page finalCheckList(int pageNum, int pageRow, String companyId);

    Page invalidOrderList(int pageNum, int pageRow, String companyId);


    Page myOrderFindByMany(String companyId, String userId, Date stime, Date etime, String checkFlag, String buyerName, String productName, String orderId, Integer pageNum, Integer pageRow);


    Page toReviewFindByMany(String companyId, String userId, Date stime, Date etime, String creatorName, String buyerName, String productName, String orderId, Integer pageNum, Integer pageRow);

    Page finalReviewFindByMany(String companyId, String userId, Date stime, Date etime, String reviewerName, String buyerName, String productName, String orderId, Integer pageNum, Integer pageRow);



    Page toCheckFindByMany(String companyId, String userId,  String sellerName, String productName, String orderId, Integer pageNum, Integer pageRow);


    Page finalCheckFindByMnay(Date stime, Date etime,String companyId, String sellerName, String productName, String orderId, String checkerName,Integer pageNum, Integer pageRow);


    Page invalidFindByMany(String companyId, String userId, Date stime, Date etime, String reviewerName, String buyerName, String productName, String orderId, Integer pageNum, Integer pageRow);

}
