package com.meliksah.banka.app.acc.dto;

import com.meliksah.banka.app.acc.enums.AccAccountType;
import com.meliksah.banka.app.acc.enums.AccCurrencyType;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccAccountSaveRequestDto {

    private BigDecimal currentBalance;
    private AccCurrencyType currencyType;
    private AccAccountType accountType;
}
