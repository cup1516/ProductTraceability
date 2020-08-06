package com.pt.ptdealerproc.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author wl
 */
@Data
@NoArgsConstructor
public class ProcProcessNode {
    private String processId;

    private String nodeId;

    private String workerId;

    private  Integer sort;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH-mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH-mm")
    private Date startTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH-mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH-mm")
    private Date endTime;

    private String status;

}