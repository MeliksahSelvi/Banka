package com.meliksah.banka.app.acc.service;

import com.meliksah.banka.app.acc.domain.AccAccount;
import com.meliksah.banka.app.acc.domain.AccAccountActivity;
import com.meliksah.banka.app.acc.dto.AccAccountActivityDto;
import com.meliksah.banka.app.acc.dto.AccMoneyActivityDto;
import com.meliksah.banka.app.acc.dto.AccMoneyActivityRequestDto;
import com.meliksah.banka.app.acc.enums.AccAccountActivityType;
import com.meliksah.banka.app.acc.enums.AccErrorMessage;
import com.meliksah.banka.app.acc.service.entityservice.AccAccountActivityEntityService;
import com.meliksah.banka.app.acc.service.entityservice.AccAccountEntityService;
import com.meliksah.banka.app.gen.enums.GenErrorMessage;
import com.meliksah.banka.app.gen.exception.exceptions.GenBusinessException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


/*
* UnitTest
* */
@ExtendWith(MockitoExtension.class)
class AccAccountActivityServiceTest {

    /*
     * spy yaptık bu sayede aynı class içerisindeki diğer test methodunu atlayabildik.
     * doReturn methodunun devamında when içinde parametre olarak bu inject edilen bean'i verdik.
     * */
    @InjectMocks
    @Spy
    private AccAccountActivityService accAccountActivityService;

    @Mock
    private AccAccountEntityService accAccountEntityService;

    @Mock
    private AccAccountActivityEntityService accAccountActivityEntityService;

    /*
     * moneyOut methodunu zaten aşağıda test edeceğimiz için o methodu atlaması için doReturn methodunu kullandık.
     * */
    @Test
    void shouldWithdraw() {
        Long accountId = 1L;
        BigDecimal amount = new BigDecimal(100);
        AccAccountActivityType activityType = AccAccountActivityType.WITHDRAW;

        AccMoneyActivityRequestDto accMoneyActivityRequestDto = mock(AccMoneyActivityRequestDto.class);
        when(accMoneyActivityRequestDto.getAccAccountId()).thenReturn(accountId);
        when(accMoneyActivityRequestDto.getAmount()).thenReturn(amount);

        AccAccountActivity accAccountActivity = mock(AccAccountActivity.class);
        when(accAccountActivity.getCurrentBalance()).thenReturn(amount);

        AccMoneyActivityDto accMoneyActivityDto = AccMoneyActivityDto.builder()
                .accAccountId(accountId)
                .amount(amount)
                .activityType(activityType)
                .build();

        doReturn(accAccountActivity).when(accAccountActivityService).moneyOut(accMoneyActivityDto);

        AccAccountActivityDto result = accAccountActivityService.withdraw(accMoneyActivityRequestDto);

        assertEquals(amount, result.getCurrentBalance());

        verify(accAccountActivityService).moneyOut(accMoneyActivityDto);
    }

    @Test
    void shouldNotWithdrawWhenParameterIsNull() {
        GenBusinessException genBusinessException = assertThrows(GenBusinessException.class,
                () -> accAccountActivityService.withdraw(null));

        assertEquals(genBusinessException.getBaseErrorMessage(), GenErrorMessage.PARAMETER_CAN_NOT_BE_NULL);
    }

    @Test
    void shouldDeposit() {

        Long accountId = 3L;
        BigDecimal amount = new BigDecimal(25);
        AccAccountActivityType activityType = AccAccountActivityType.DEPOSIT;

        AccMoneyActivityRequestDto activityRequestDto = mock(AccMoneyActivityRequestDto.class);
        when(activityRequestDto.getAccAccountId()).thenReturn(accountId);
        when(activityRequestDto.getAmount()).thenReturn(amount);

        AccAccountActivity accAccountActivity = mock(AccAccountActivity.class);
        when(accAccountActivity.getAccAccountId()).thenReturn(accountId);

        AccMoneyActivityDto accMoneyActivityDto = AccMoneyActivityDto.builder()
                .accAccountId(accountId)
                .amount(amount)
                .activityType(activityType)
                .build();

        doReturn(accAccountActivity).when(accAccountActivityService).moneyIn(accMoneyActivityDto);

        AccAccountActivityDto result = accAccountActivityService.deposit(activityRequestDto);

        assertEquals(accountId, result.getAccAccountId());

        verify(accAccountActivityService).moneyIn(accMoneyActivityDto);
    }

    @Test
    void shouldNotDepositWhenParameterIsNull() {
        GenBusinessException genBusinessException = assertThrows(GenBusinessException.class,
                () -> accAccountActivityService.deposit(null));
        assertEquals(genBusinessException.getBaseErrorMessage(), GenErrorMessage.PARAMETER_CAN_NOT_BE_NULL);
    }

    @Test
    void shouldMoneyOut() {
        Long accountId = 4L;
        BigDecimal amount = new BigDecimal(550);
        BigDecimal currentBalance = new BigDecimal(600);
        BigDecimal newBalance = currentBalance.subtract(amount);
        AccAccountActivityType activityType = AccAccountActivityType.WITHDRAW;

        AccAccount accAccount = mock(AccAccount.class);
        when(accAccount.getCurrentBalance()).thenReturn(currentBalance);

        AccAccountActivity accAccountActivity = mock(AccAccountActivity.class);
        when(accAccountActivity.getCurrentBalance()).thenReturn(newBalance);

        when(accAccountEntityService.getByIdWithControl(accountId)).thenReturn(accAccount);
        when(accAccountActivityEntityService.save(any())).thenReturn(accAccountActivity);
        when(accAccountEntityService.save(accAccount)).thenReturn(accAccount);

        AccMoneyActivityDto accMoneyActivityDto = AccMoneyActivityDto.builder()
                .accAccountId(accountId)
                .amount(amount)
                .activityType(activityType)
                .build();

        AccAccountActivity result = accAccountActivityService.moneyOut(accMoneyActivityDto);

        assertEquals(result.getCurrentBalance(),newBalance);
    }

    @Test
    void shouldNotMoneyOutWhenParameterIsNull() {
        GenBusinessException genBusinessException = assertThrows(GenBusinessException.class,
                () -> accAccountActivityService.moneyOut(null));
        assertEquals(genBusinessException.getBaseErrorMessage(),GenErrorMessage.PARAMETER_CAN_NOT_BE_NULL);
    }

    @Test
    void shouldNotMoneyOutWhenBalanceIsInsufficient() {
        Long accountId = 4L;
        BigDecimal amount = new BigDecimal(550);
        BigDecimal currentBalance = new BigDecimal(500);
        BigDecimal newBalance = currentBalance.subtract(amount);
        AccAccountActivityType activityType = AccAccountActivityType.WITHDRAW;

        AccAccount accAccount = mock(AccAccount.class);
        when(accAccount.getCurrentBalance()).thenReturn(currentBalance);

        when(accAccountEntityService.getByIdWithControl(accountId)).thenReturn(accAccount);

        AccMoneyActivityDto accMoneyActivityDto = AccMoneyActivityDto.builder()
                .accAccountId(accountId)
                .amount(amount)
                .activityType(activityType)
                .build();

        GenBusinessException genBusinessException = assertThrows(GenBusinessException.class,
                () -> accAccountActivityService.moneyOut(accMoneyActivityDto));

        assertEquals(genBusinessException.getBaseErrorMessage(), AccErrorMessage.INSUFFICIENT_BALANCE);
    }

    @Test
    void shouldMoneyIn() {

        Long accountIdTo = 3L;
        BigDecimal amount = new BigDecimal(500);
        BigDecimal currentBalance = new BigDecimal(400);
        BigDecimal newBalance = amount.add(currentBalance);

        AccAccountActivityType activityType = AccAccountActivityType.DEPOSIT;

        AccAccount accAccount = mock(AccAccount.class);
        when(accAccount.getCurrentBalance()).thenReturn(currentBalance);

        AccAccountActivity accAccountActivity = mock(AccAccountActivity.class);
        when(accAccountActivity.getCurrentBalance()).thenReturn(newBalance);

        when(accAccountEntityService.getByIdWithControl(accountIdTo)).thenReturn(accAccount);
        when(accAccountActivityEntityService.save(any())).thenReturn(accAccountActivity);
        when(accAccountEntityService.save(accAccount)).thenReturn(accAccount);

        AccMoneyActivityDto accMoneyActivityDto = AccMoneyActivityDto.builder()
                .accAccountId(accountIdTo)
                .amount(amount)
                .activityType(activityType).build();
        AccAccountActivity result = accAccountActivityService.moneyIn(accMoneyActivityDto);

        assertEquals(result.getCurrentBalance(),newBalance);
    }

    @Test
    void shouldNotMoneyInWhenParameterIsNull() {
        GenBusinessException genBusinessException = assertThrows(GenBusinessException.class,
                () -> accAccountActivityService.moneyIn(null));
        assertEquals(genBusinessException.getBaseErrorMessage(),GenErrorMessage.PARAMETER_CAN_NOT_BE_NULL);
    }
}