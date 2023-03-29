package com.meliksah.banka.app.acc.mapper;

import com.meliksah.banka.app.acc.domain.AccAccount;
import com.meliksah.banka.app.acc.dto.AccAccountDto;
import com.meliksah.banka.app.acc.dto.AccAccountSaveRequestDto;
import com.meliksah.banka.app.cus.domain.CusCustomer;
import com.meliksah.banka.app.cus.dto.CusCustomerSaveRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AccAccountMapper {

    AccAccountMapper INSTANCE = Mappers.getMapper(AccAccountMapper.class);

    List<AccAccountDto> accAccountListToAccAccountDtoList(List<AccAccount> accAccountList);

    AccAccountDto accAccountToAccAccountDto(AccAccount accAccount);

    AccAccount accAccountSaveRequestDtoToAccAccount(AccAccountSaveRequestDto accAccountSaveRequestDto);
}
