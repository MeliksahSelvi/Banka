package com.meliksah.banka.app.acc.service;

import com.meliksah.banka.app.acc.domain.AccAccountActivity;
import com.meliksah.banka.app.acc.domain.AccMoneyTransfer;
import com.meliksah.banka.app.acc.dto.AccMoneyActivityDto;
import com.meliksah.banka.app.acc.dto.AccMoneyTransferDto;
import com.meliksah.banka.app.acc.dto.AccMoneyTransferSaveRequestDto;
import com.meliksah.banka.app.acc.enums.AccAccountActivityType;
import com.meliksah.banka.app.acc.mapper.AccMoneyTransferMapper;
import com.meliksah.banka.app.acc.service.entityservice.AccMoneyTransferEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Date;

@Service
@Transactional
@RequiredArgsConstructor
public class AccMoneyTransferService {

    private final AccMoneyTransferEntityService accMoneyTransferEntityService;
    private final AccAccountActivityService accAccountActivityService;


    public AccMoneyTransferDto transferMoney(AccMoneyTransferSaveRequestDto accMoneyTransferSaveRequestDto) {
        AccMoneyTransfer accMoneyTransfer = AccMoneyTransferMapper.INSTANCE.accMoneyTransferSaveRequestDtoToAccMoneyTransfer(accMoneyTransferSaveRequestDto);
        accMoneyTransfer.setTransferDate(new Date());

        Long accountIdFrom = accMoneyTransfer.getAccountIdFrom();
        Long accountIdTo = accMoneyTransfer.getAccountIdTo();
        BigDecimal amount = accMoneyTransfer.getAmount();

        AccMoneyActivityDto accMoneyActivityDtoOut = AccMoneyActivityDto.builder()
                .accAccountId(accountIdFrom)
                .amount(amount)
                .activityType(AccAccountActivityType.SEND)
                .build();

        accAccountActivityService.moneyOut(accMoneyActivityDtoOut);

        AccMoneyActivityDto accMoneyActivityDtoIn  = AccMoneyActivityDto.builder()
                .accAccountId(accountIdTo)
                .amount(amount)
                .activityType(AccAccountActivityType.GET)
                .build();

        accAccountActivityService.moneyIn(accMoneyActivityDtoIn);

        accMoneyTransfer = accMoneyTransferEntityService.save(accMoneyTransfer);

        AccMoneyTransferDto accMoneyTransferDto = AccMoneyTransferMapper.INSTANCE.accMoneyTransferToAccMoneyTransferDto(accMoneyTransfer);

        return accMoneyTransferDto;
    }


}
