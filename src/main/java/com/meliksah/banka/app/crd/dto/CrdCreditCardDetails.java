package com.meliksah.banka.app.crd.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@RequiredArgsConstructor
public class CrdCreditCardDetails {

    private final String customerName;
    private final String customerSurname;
    private final Long cardNo;
    private final Date expireDate;
    private final BigDecimal currentDebt;
    private final BigDecimal minimumPaymentAmount;
    private final Date cutoffDate;
    private final Date dueDate;
    private List<CrdCreditCardActivityDto> crdCreditCardActivityDtoList;
}
