package com.pt.ptdealerproc.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pt.ptdealerproc.dto.MissionDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wl
 */
@Mapper
public interface ProcMissionMapper {
    IPage<List<MissionDto>> getProcNodePage(Page page, @Param("query") MissionDto missionDto,@Param("companyId") String companyId);

    Boolean updateMissionStatus(@Param("missionDto") MissionDto missionDto,@Param("companyId") String companyId);
}
