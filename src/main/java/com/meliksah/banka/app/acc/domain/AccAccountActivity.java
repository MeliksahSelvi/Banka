package com.meliksah.banka.app.acc.domain;

import com.meliksah.banka.app.acc.enums.AccAccountActivityType;
import com.meliksah.banka.app.gen.entity.BaseEntity;
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "ACC_ACCOUNT_ACTIVITY")
public class AccAccountActivity extends BaseEntity {

    @SequenceGenerator(name = "AccAccountActivity", sequenceName = "ACC_ACCOUNT_ACTIVITY_ID_SEQ", allocationSize = 1)
    @GeneratedValue(generator = "AccAccountActivity")
    @Column
    @Id
    private Long id;

    @Column(name = "ID_ACC_ACCOUNT")
    private Long accAccountId;

    @Column(name = "AMOUNT", precision = 19, scale = 2)
    private BigDecimal amount;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "TRANSACTION_DATE")
    private Date transactionDate;

    @Column(name = "CURRENT_BALANCE", precision = 19, scale = 2)
    private BigDecimal currentBalance;

    @Enumerated(EnumType.STRING)
    @Column(name = "ACCOUNT_ACTIVITY_TYPE", length = 20)
    private AccAccountActivityType accountActivityType;
}
