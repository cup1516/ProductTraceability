package com.pt.ptdealerproc.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pt.ptcommonsecurity.util.SecurityUtils;
import com.pt.ptdealerproc.dto.MissionDto;
import com.pt.ptdealerproc.mapper.ProcMissionMapper;
import com.pt.ptdealerproc.mapper.ProcProcessNodeMapper;
import com.pt.ptdealerproc.service.ProcMissionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProcMissionServiceImpl implements ProcMissionService {

    private final ProcMissionMapper procMissionMapper;
    private final ProcProcessNodeMapper procProcessNodeMapper;
    @Override
    public IPage getMissionPage(Page page, MissionDto missionDto,String companyId) {
        missionDto.setWorkerId(SecurityUtils.getId());
        return procMissionMapper.getProcNodePage(page,missionDto,companyId);
    }

    @Override
    public Boolean updateMissionStatus(MissionDto missionDto,String companyId) {
        missionDto.setWorkerId(SecurityUtils.getId());
        return procMissionMapper.updateMissionStatus(missionDto,companyId);
    }


}
