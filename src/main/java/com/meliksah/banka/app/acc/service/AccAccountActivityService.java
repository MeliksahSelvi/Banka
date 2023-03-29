package com.meliksah.banka.app.acc.service;

import com.meliksah.banka.app.acc.domain.AccAccount;
import com.meliksah.banka.app.acc.domain.AccAccountActivity;
import com.meliksah.banka.app.acc.dto.AccAccountActivityDto;
import com.meliksah.banka.app.acc.dto.AccMoneyActivityDto;
import com.meliksah.banka.app.acc.dto.AccMoneyActivityRequestDto;
import com.meliksah.banka.app.acc.enums.AccAccountActivityType;
import com.meliksah.banka.app.acc.enums.AccErrorMessage;
import com.meliksah.banka.app.acc.mapper.AccAccountActivityMapper;
import com.meliksah.banka.app.acc.service.entityservice.AccAccountActivityEntityService;
import com.meliksah.banka.app.acc.service.entityservice.AccAccountEntityService;
import com.meliksah.banka.app.gen.enums.GenErrorMessage;
import com.meliksah.banka.app.gen.exception.exceptions.GenBusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Date;

@Service
@Transactional
@RequiredArgsConstructor
public class AccAccountActivityService {

    private final AccAccountEntityService accAccountEntityService;
    private final AccAccountActivityEntityService accAccountActivityEntityService;

    public AccAccountActivityDto deposit(AccMoneyActivityRequestDto accMoneyActivityRequestDto) {
        validateAccMoneyActivityRequestDto(accMoneyActivityRequestDto);

        Long accAccountId = accMoneyActivityRequestDto.getAccAccountId();
        BigDecimal amount = accMoneyActivityRequestDto.getAmount();

        AccMoneyActivityDto accMoneyActivityDto = AccMoneyActivityDto.builder()
                .accAccountId(accAccountId)
                .amount(amount)
                .activityType(AccAccountActivityType.DEPOSIT)
                .build();

        AccAccountActivity accAccountActivity = moneyIn(accMoneyActivityDto);

        AccAccountActivityDto accAccountActivityDto = AccAccountActivityMapper.INSTANCE.accAccountActivityToAccAccountActivityDto(accAccountActivity);
        return accAccountActivityDto;
    }

    public AccAccountActivityDto withdraw(AccMoneyActivityRequestDto accMoneyActivityRequestDto) {

        validateAccMoneyActivityRequestDto(accMoneyActivityRequestDto);
        Long accAccountId = accMoneyActivityRequestDto.getAccAccountId();
        BigDecimal amount = accMoneyActivityRequestDto.getAmount();

        AccMoneyActivityDto accMoneyActivityDto = AccMoneyActivityDto.builder()
                .accAccountId(accAccountId)
                .amount(amount)
                .activityType(AccAccountActivityType.WITHDRAW)
                .build();

        AccAccountActivity accAccountActivity = moneyOut(accMoneyActivityDto);

        AccAccountActivityDto accAccountActivityDto = AccAccountActivityMapper.INSTANCE.accAccountActivityToAccAccountActivityDto(accAccountActivity);
        return accAccountActivityDto;
    }

    public AccAccountActivity moneyOut(AccMoneyActivityDto accMoneyActivityDto) {

        validateAccMoneyActivityDto(accMoneyActivityDto);

        Long accountIdFrom = accMoneyActivityDto.getAccAccountId();
        BigDecimal amount = accMoneyActivityDto.getAmount();
        AccAccountActivityType activityType = accMoneyActivityDto.getActivityType();

        AccAccount accAccount = accAccountEntityService.getByIdWithControl(accountIdFrom);

        BigDecimal newBalance = accAccount.getCurrentBalance().subtract(amount);

        validateBalance(newBalance);

        AccAccountActivity accAccountActivity = createAccAccountActivity(accountIdFrom, activityType, amount, newBalance);

        updateCurrentBalance(accAccount, newBalance);

        return accAccountActivity;
    }

    public AccAccountActivity moneyIn(AccMoneyActivityDto accMoneyActivityDto) {

        validateAccMoneyActivityDto(accMoneyActivityDto);

        Long accountIdTo = accMoneyActivityDto.getAccAccountId();
        BigDecimal amount = accMoneyActivityDto.getAmount();
        AccAccountActivityType activityType = accMoneyActivityDto.getActivityType();

        AccAccount accAccount = accAccountEntityService.getByIdWithControl(accountIdTo);

        BigDecimal newBalance = accAccount.getCurrentBalance().add(amount);

        AccAccountActivity accAccountActivity = createAccAccountActivity(accountIdTo, activityType, amount, newBalance);

        updateCurrentBalance(accAccount, newBalance);

        return accAccountActivity;
    }

    private void validateBalance(BigDecimal remainingBalance) {
        if (remainingBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new GenBusinessException(AccErrorMessage.INSUFFICIENT_BALANCE);
        }
    }

    private void updateCurrentBalance(AccAccount accAccount, BigDecimal newBalance) {
        accAccount.setCurrentBalance(newBalance);
        accAccountEntityService.save(accAccount);
    }

    private AccAccountActivity createAccAccountActivity(Long accountId, AccAccountActivityType accountActivityType, BigDecimal amount, BigDecimal newBalance) {
        AccAccountActivity accAccountActivity = new AccAccountActivity();
        accAccountActivity.setAccAccountId(accountId);
        accAccountActivity.setAccountActivityType(accountActivityType);
        accAccountActivity.setAmount(amount);
        accAccountActivity.setCurrentBalance(newBalance);
        accAccountActivity.setTransactionDate(new Date());
        return accAccountActivityEntityService.save(accAccountActivity);
    }

    private void validateAccMoneyActivityRequestDto(AccMoneyActivityRequestDto accMoneyActivityRequestDto) {
        if (accMoneyActivityRequestDto == null) {
            throw new GenBusinessException(GenErrorMessage.PARAMETER_CAN_NOT_BE_NULL);
        }
    }

    private void validateAccMoneyActivityDto(AccMoneyActivityDto accMoneyActivityDto) {
        if (accMoneyActivityDto == null) {
            throw new GenBusinessException(GenErrorMessage.PARAMETER_CAN_NOT_BE_NULL);
        }
    }
}
