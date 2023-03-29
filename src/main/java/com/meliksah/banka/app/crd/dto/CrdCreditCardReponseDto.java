package com.meliksah.banka.app.crd.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class CrdCreditCardReponseDto {

    private Long id;
    private Long cusCustomerId;
    private Long cardNo;
    private Long cvvNo;
    private Date expireDate;
    private BigDecimal totalLimit;
    private BigDecimal availableCardLimit;
    private BigDecimal currentDebt;
    private BigDecimal minimumPaymentAmount;
    private Date cutoffDate;
    private Date dueDate;
}
