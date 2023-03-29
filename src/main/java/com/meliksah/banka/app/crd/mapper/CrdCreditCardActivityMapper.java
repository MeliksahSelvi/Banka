package com.meliksah.banka.app.crd.mapper;

import com.meliksah.banka.app.crd.domain.CrdCreditCardActivity;
import com.meliksah.banka.app.crd.dto.CrdCreditCardActivityDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CrdCreditCardActivityMapper {

    CrdCreditCardActivityMapper INSTANCE = Mappers.getMapper(CrdCreditCardActivityMapper.class);

    CrdCreditCardActivityDto crdCreditCardActivityToCrdCreditCardActivityDto(CrdCreditCardActivity crdCreditCardActivity);

    List<CrdCreditCardActivityDto> crdCreditCardActivityListToCrdCreditCardActivityDtoList(List<CrdCreditCardActivity> crdCreditCardActivityList);
}
