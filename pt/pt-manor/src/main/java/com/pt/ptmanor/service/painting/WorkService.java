package com.pt.ptmanor.service.painting;

import org.springframework.data.domain.Page;

import java.util.Date;

public interface WorkService {

    Page materialList(int pageNum, int pageRow, String companyId);

    Page list(int pageNum, int pageRow, String companyId);

    Page findByMany(String companyId, Date stime, Date etime, String work, String crops, String staff, String id, String findFarmlandRegionId, Integer pageNum, Integer pageRow);

    Page findByManyMaterial(String companyId, Date stime, Date etime, String work, String crops, String staff, String id, String field, Integer pageNum, Integer pageRow);


    //我的作业单
    Page myList(int pageNum, int pageRow, String userName, String companyId);

    //待审核作业单
    Page toCheckList(int pageNum, int pageRow, String userName, String companyId);

    //已审批作业单
    Page finalList(int pageNum, int pageRow, String companyId);

    //我的作业单，查询
    Page myWorkFindByMany(String companyId, String userName, Date stime, Date etime, String work, String crops, Integer checkNumber, String id, String findFarmlandRegionId, Integer pageNum, Integer pageRow);

    //待办作业单，查询
    Page toCheckWorkFindByMany(String companyId, String userName, Date stime, Date etime, String work, String crops, String staff, String id, String findFarmlandRegionId, Integer pageNum, Integer pageRow);


    //已审核作业单，查询
    Page finalWorkFindByMany(String companyId, Date stime, Date etime, String staff, String id, String findFarmlandRegionId, Integer checkNumber, Integer pageNum, Integer pageRow);


}
