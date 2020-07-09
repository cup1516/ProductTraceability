package com.pt.ptdealerproc.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pt.ptdealerproc.dto.MissionDto;

/**
 * @author wl
 */
public interface ProcMissionService {

    IPage getMissionPage(Page page, MissionDto missionDto);

    /**
     * 修改任务状态
     *
     * @return 结果
     */
    Boolean updateMissionStatus(MissionDto missionDto);


}
