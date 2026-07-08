package com.tankaokesulu.financeapi.dto;

import lombok.Data;

@Data
public class ExpenseSearchDTO {

    private Long userId;

    private String type;

    private String remark;

    private String startDate;

    private String endDate;
}