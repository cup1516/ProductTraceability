package com.pt.ptdealerproc.dto;


import com.pt.ptdealerproc.entity.ProcNodeWorker;
import com.pt.ptdealerproc.entity.ProcProcess;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class ProcessDto extends ProcProcess {
	/**
	 * 加工节点（dynamic与前端子表单组件dynamic对应）
	 */
	private List<ProcNodeWorker> dynamic;
}
