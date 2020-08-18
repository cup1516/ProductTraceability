package com.warehouse.warehouse.service.app.logging;

import com.warehouse.warehouse.pojo.dto.logging.LoggingDTO;

import java.util.List;

public interface LoggingService {
    List<LoggingDTO> describeFuzzy(LoggingDTO loggingDTO);
}
