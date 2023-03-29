package com.meliksah.banka.app.log.entity;

import com.meliksah.banka.app.gen.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "LOG_DETAIL")
public class LogDetail extends BaseEntity {

    @SequenceGenerator(name = "LogDetail", sequenceName = "LOG_DETAIL_ID_SEQ", allocationSize = 1)
    @GeneratedValue(generator = "LogDetail")
    @Column
    @Id
    private Long id;

    @Column(name = "MESSAGE", length = 100)
    private String message;

    @Column(name = "DETAILS", length = 200)
    private String details;

    @Column(name = "LOG_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date logDate;
}
