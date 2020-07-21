package com.pt.ptuser.service.serviceImpl;

import com.pt.ptuser.entity.SysDictData;
import com.pt.ptuser.mapper.SysDictDataMapper;
import com.pt.ptuser.service.SysDictDataService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wl
 */
@Service
@AllArgsConstructor
public class SysDictDataServiceImpl  implements SysDictDataService{

    private final SysDictDataMapper sysDictDataMapper;
    /**
     * 根据字典类型查询字典数据
     *
     * @param dictType 字典类型
     * @return 字典数据集合信息
     */
    @Override
    public List<SysDictData> selectDictDataByType(String dictType)
    {
        return sysDictDataMapper.selectDictDataByType(dictType);
    }
}
