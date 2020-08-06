package com.pt.ptdealerproc.dto;


import com.pt.ptdealerproc.entity.ProcProcess;
import com.pt.ptdealerproc.entity.ProcProcessNode;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author wl
 */
@Data
@NoArgsConstructor
public class ProcessDto extends ProcProcess {
	private List<ProcProcessNode> processNodes;

}
