package com.meliksah.banka.app.acc.mapper;

import com.meliksah.banka.app.acc.domain.AccAccountActivity;
import com.meliksah.banka.app.acc.domain.AccMoneyTransfer;
import com.meliksah.banka.app.acc.dto.AccAccountActivityDto;
import com.meliksah.banka.app.acc.dto.AccMoneyTransferDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AccAccountActivityMapper {

    AccAccountActivityMapper INSTANCE = Mappers.getMapper(AccAccountActivityMapper.class);

    AccAccountActivityDto accAccountActivityToAccAccountActivityDto(AccAccountActivity accAccountActivity);
}
