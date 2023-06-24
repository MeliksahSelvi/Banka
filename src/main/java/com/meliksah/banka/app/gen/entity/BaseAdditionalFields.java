package com.meliksah.banka.app.gen.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;

@Embeddable
@Getter
@Setter
public class BaseAdditionalFields {

    @Column(name = "CREATE_DATE", updatable = false)
    @CreatedDate
    private Date createDate;

    @Column(name = "UPDATE_DATE")
    @LastModifiedDate
    private Date updateDate;

    @Column(name = "CREATED_BY",updatable = false)
    @CreatedBy
    private Long createdBy;

    @Column(name = "UPDATED_BY")
    @LastModifiedBy
    private Long updatedBy;
}
