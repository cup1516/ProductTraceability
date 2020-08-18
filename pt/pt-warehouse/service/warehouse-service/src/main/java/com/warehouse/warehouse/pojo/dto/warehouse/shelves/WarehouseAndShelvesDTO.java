package com.warehouse.warehouse.pojo.dto.warehouse.shelves;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * Copyright (C), 2020-2020, si-tech
 * FileName: WarehouseAndShelvesDTO
 *
 * @Author: wanpp
 * @Date: 2020/5/10 15:36
 * Description:
 * Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WarehouseAndShelvesDTO implements Serializable {

    private static final long serialVersionUID = 8140716899649577980L;

    private String value;

    private String label;

    private List<WarehouseChidrenDTO> children;

    private String companyId;

}
