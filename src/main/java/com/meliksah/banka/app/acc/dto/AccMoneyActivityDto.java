package com.meliksah.banka.app.acc.dto;

import com.meliksah.banka.app.acc.enums.AccAccountActivityType;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class AccMoneyActivityDto {

    private Long accAccountId;
    private BigDecimal amount;
    private AccAccountActivityType activityType;
}
