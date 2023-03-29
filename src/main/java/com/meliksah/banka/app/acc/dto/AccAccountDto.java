package com.meliksah.banka.app.acc.dto;

import com.meliksah.banka.app.acc.enums.AccAccountType;
import com.meliksah.banka.app.acc.enums.AccCurrencyType;
import com.meliksah.banka.app.gen.enums.GenStatusType;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccAccountDto {

    private Long cusCustomerId;
    private String ibanNo;
    private BigDecimal currentBalance;
    private AccCurrencyType currencyType;
    private AccAccountType accountType;
    private GenStatusType statusType;
}
