package com.meliksah.banka.app.cus.mapper;

import com.meliksah.banka.app.cus.domain.CusCustomer;
import com.meliksah.banka.app.cus.dto.CusCustomerDto;
import com.meliksah.banka.app.cus.dto.CusCustomerSaveRequestDto;
import com.meliksah.banka.app.cus.dto.CusCustomerUpdateRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CusCustomerMapper {

    CusCustomerMapper INSTANCE = Mappers.getMapper(CusCustomerMapper.class);

    CusCustomer cusCustomerSaveRequestDtoToCusCustomer(CusCustomerSaveRequestDto cusCustomerSaveRequestDto);

    CusCustomer cusCustomerUpdateRequestDtoToCusCustomer(CusCustomerUpdateRequestDto cusCustomerUpdateRequestDto);

    List<CusCustomerDto> cusCustomerListToCusCustomerDtoList(List<CusCustomer> cusCustomerList);

    CusCustomerDto cusCustomerToCusCustomerDto(CusCustomer cusCustomer);
}
