package com.meliksah.banka.app.crd.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CrdCreditCardPaymentDto {

    private Long crdCreditCardId;
    private BigDecimal amount;
}
