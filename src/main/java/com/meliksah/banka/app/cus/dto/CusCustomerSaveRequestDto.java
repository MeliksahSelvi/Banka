package com.meliksah.banka.app.cus.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CusCustomerSaveRequestDto {

    private String name;
    private String surName;
    private Long identityNo;
    private String password;
}
