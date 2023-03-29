package com.meliksah.banka.app.crd.domain;

import com.meliksah.banka.app.gen.entity.BaseEntity;
import com.meliksah.banka.app.gen.enums.GenStatusType;
import javax.persistence.*;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "CRD_CREDIT_CARD")
public class CrdCreditCard extends BaseEntity {

    @SequenceGenerator(name = "CrdCreditCard", sequenceName = "CRD_CREDIT_CARD_ID_SEQ", allocationSize = 1)
    @GeneratedValue(generator = "CrdCreditCard")
    @Column
    @Id
    private Long id;

    @Column(name = "ID_CUS_CUSTOMER", nullable = false)
    private Long cusCustomerId;

    @Column(name = "CARD_NO", nullable = false)
    private Long cardNo;

    @Column(name = "CVV_NO", nullable = false)
    private Long cvvNo;

    @Column(name = "EXPIRE_DATE", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date expireDate;

    @Column(name = "TOTAL_LIMIT", precision = 19, scale = 2)
    private BigDecimal totalLimit;

    @Column(name = "AVAILABLE_CARD_LIMIT", precision = 19, scale = 2)
    private BigDecimal availableCardLimit;

    @Column(name = "CURRENT_DEBT", precision = 19, scale = 2)
    private BigDecimal currentDebt;

    @Column(name = "MINIMUM_PAYMENT_AMOUNT", precision = 19, scale = 2)
    private BigDecimal minimumPaymentAmount;

    @Column(name = "CUTOFF_DATE", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date cutoffDate;

    @Column(name = "DUE_DATE", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dueDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS_TYPE", length = 20)
    private GenStatusType statusType;

    @Column(name = "CANCEL_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date cancelDate;
}
