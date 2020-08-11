package com.pt.ptmanor.mapper;



import com.pt.ptmanor.pojo.Charts;


import java.util.List;

//@Mapper
public interface ChartsMapper  {
    int deleteByPrimaryKey(Long id);

    int insert(Charts record);

    int insertSelective(Charts record);

    Charts selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Charts record);

    int updateByPrimaryKey(Charts record);


}