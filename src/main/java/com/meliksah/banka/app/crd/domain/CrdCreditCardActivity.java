package com.meliksah.banka.app.crd.domain;

import com.meliksah.banka.app.crd.enums.CrdCreditCardActivityType;
import com.meliksah.banka.app.gen.entity.BaseEntity;
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "CRD_CREDIT_CARD_ACTIVITY")
public class CrdCreditCardActivity extends BaseEntity {

    @SequenceGenerator(name = "CrdCreditCardActivity", sequenceName = "CRD_CREDIT_CARD_ACTIVTY_ID_SEQ", allocationSize = 1)
    @GeneratedValue(generator = "CrdCreditCardActivity")
    @Column
    @Id
    private Long id;

    @Column(name = "ID_CRD_CREDIT_CARD", nullable = false)
    private Long crdCreditCardId;

    @Column(name = "AMOUNT",nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;

    @Column(name = "TRANSACTION_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date transactionDate;

    @Column(name = "DESCRIPTION",nullable = false,length = 100)
    private String description;

    @Column(name = "CARD_ACTIVITY_TYPE",length = 20)
    @Enumerated(EnumType.STRING)
    private CrdCreditCardActivityType cardActivityType;

}
