package com.meliksah.banka.app.crd.mapper;

import com.meliksah.banka.app.crd.domain.CrdCreditCard;
import com.meliksah.banka.app.crd.dto.CrdCreditCardReponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CrdCreditCardMapper {

    CrdCreditCardMapper INSTANCE = Mappers.getMapper(CrdCreditCardMapper.class);

    CrdCreditCardReponseDto crdCreditCardToCrdCreditCardReponseDto(CrdCreditCard crdCreditCard);

    List<CrdCreditCardReponseDto> crdCreditCardListToCrdCreditCardReponseDtoList(List<CrdCreditCard> crdCreditCard);

}
