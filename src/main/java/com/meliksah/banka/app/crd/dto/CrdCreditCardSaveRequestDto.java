package com.meliksah.banka.app.crd.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class CrdCreditCardSaveRequestDto {

    @NotNull
    private BigDecimal earning;
    private String cutoffDay;
}
