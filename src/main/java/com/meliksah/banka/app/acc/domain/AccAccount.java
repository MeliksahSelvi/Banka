package com.meliksah.banka.app.acc.domain;

import com.meliksah.banka.app.acc.enums.AccAccountType;
import com.meliksah.banka.app.acc.enums.AccCurrencyType;
import com.meliksah.banka.app.gen.entity.BaseEntity;
import com.meliksah.banka.app.gen.enums.GenStatusType;
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "ACC_ACCOUNT")
public class AccAccount extends BaseEntity {

    @SequenceGenerator(name = "AccAccount", sequenceName = "ACC_ACCOUNT_ID_SEQ", allocationSize = 1)
    @GeneratedValue(generator = "AccAccount")
    @Column
    @Id
    private Long id;

    @Column(name = "ID_CUS_CUSTOMER")
    private Long cusCustomerId;

    @Column(name = "IBAN_NO", length = 26)
    private String ibanNo;

    @Column(name = "CURRENT_BALANCE", precision = 19, scale = 2)
    private BigDecimal currentBalance;

    @Enumerated(EnumType.STRING)
    @Column(name = "CURRENCY_TYPE", length = 20)
    private AccCurrencyType currencyType;

    @Enumerated(EnumType.STRING)
    @Column(name = "ACCOUNT_TYPE", length = 20)
    private AccAccountType accountType;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS_TYPE", length = 20)
    private GenStatusType statusType;

    @Column(name = "CANCEL_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date cancelDate;
}
