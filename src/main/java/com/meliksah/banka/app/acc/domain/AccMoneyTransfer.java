package com.meliksah.banka.app.acc.domain;

import com.meliksah.banka.app.acc.enums.AccMoneyTransferType;
import com.meliksah.banka.app.gen.entity.BaseEntity;
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "ACC_MONEY_TRANSFER")
public class AccMoneyTransfer extends BaseEntity {

    @SequenceGenerator(name = "AccMoneyTransfer", sequenceName = "ACC_MONEY_TRANSFER_ID_SEQ", allocationSize = 1)
    @GeneratedValue(generator = "AccMoneyTransfer")
    @Column
    @Id
    private Long id;

    @Column(name = "ID_ACC_ACCOUNT_FROM")
    private Long accountIdFrom;

    @Column(name = "ID_ACC_ACCOUNT_TO")
    private Long accountIdTo;

    @Column(name = "AMOUNT", precision = 19, scale = 2)
    private BigDecimal amount;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "TRANSFER_DATE")
    private Date transferDate;

    @Column(name = "DESCRIPTION",length = 100)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "MONEY_TRANSFER_TYPE", length = 20)
    private AccMoneyTransferType moneyTransferType;
}
