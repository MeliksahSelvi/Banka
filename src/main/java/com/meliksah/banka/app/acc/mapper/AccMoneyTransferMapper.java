package com.meliksah.banka.app.acc.mapper;

import com.meliksah.banka.app.acc.domain.AccAccount;
import com.meliksah.banka.app.acc.domain.AccMoneyTransfer;
import com.meliksah.banka.app.acc.dto.AccAccountSaveRequestDto;
import com.meliksah.banka.app.acc.dto.AccMoneyTransferDto;
import com.meliksah.banka.app.acc.dto.AccMoneyTransferSaveRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AccMoneyTransferMapper {

    AccMoneyTransferMapper INSTANCE = Mappers.getMapper(AccMoneyTransferMapper.class);

    AccMoneyTransfer accMoneyTransferSaveRequestDtoToAccMoneyTransfer(AccMoneyTransferSaveRequestDto accMoneyTransferSaveRequestDto);

    AccMoneyTransferDto accMoneyTransferToAccMoneyTransferDto(AccMoneyTransfer accMoneyTransfer);
}
