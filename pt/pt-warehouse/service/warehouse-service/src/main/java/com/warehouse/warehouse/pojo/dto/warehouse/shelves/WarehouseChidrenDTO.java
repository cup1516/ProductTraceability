package com.warehouse.warehouse.pojo.dto.warehouse.shelves;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Copyright (C), 2020-2020, si-tech
 * FileName: WarehouseChidrenDTO
 *
 * @Author: wanpp
 * @Date: 2020/5/10 15:39
 * Description:
 * Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WarehouseChidrenDTO implements Serializable {

    private static final long serialVersionUID = -2991715534159163101L;

    private String value;

    private String label;

    private ShelvesDTO shelvesDTO;

    private String companyId;

}
