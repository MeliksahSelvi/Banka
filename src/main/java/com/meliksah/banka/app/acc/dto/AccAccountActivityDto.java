package com.meliksah.banka.app.acc.dto;

import com.meliksah.banka.app.acc.enums.AccAccountActivityType;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class AccAccountActivityDto {

    private Long accAccountId;
    private BigDecimal amount;
    private Date transactionDate;
    private BigDecimal currentBalance;
    private AccAccountActivityType accountActivityType;
}
