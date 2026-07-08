package com.tankaokesulu.financeapi.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class ExpenseExcel {

    @ExcelProperty("金额")
    private String amount;

    @ExcelProperty("类型")
    private String type;

    @ExcelProperty("备注")
    private String remark;

    @ExcelProperty("创建时间")
    private String createTime;
}