package com.meliksah.banka.app.acc.service;

import com.meliksah.banka.app.acc.domain.AccAccount;
import com.meliksah.banka.app.acc.dto.AccAccountDto;
import com.meliksah.banka.app.acc.dto.AccAccountSaveRequestDto;
import com.meliksah.banka.app.acc.mapper.AccAccountMapper;
import com.meliksah.banka.app.acc.service.entityservice.AccAccountEntityService;
import com.meliksah.banka.app.gen.enums.GenStatusType;
import com.meliksah.banka.app.gen.util.NumberUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccAccountService {

    private final AccAccountEntityService accAccountEntityService;

    public List<AccAccountDto> findAll() {
        List<AccAccount> accAccountList = accAccountEntityService.findAllActiveAccAccountList();
        List<AccAccountDto> accAccountDtoList = AccAccountMapper.INSTANCE.accAccountListToAccAccountDtoList(accAccountList);
        return accAccountDtoList;
    }

    public List<AccAccountDto> findAll(Optional<Integer> pageOptional,Optional<Integer> sizeOptional) {
        List<AccAccount> accAccountList = accAccountEntityService.findAllActiveAccAccountList(pageOptional,sizeOptional);
        List<AccAccountDto> accAccountDtoList = AccAccountMapper.INSTANCE.accAccountListToAccAccountDtoList(accAccountList);
        return accAccountDtoList;
    }

    public AccAccountDto findById(Long id) {
        AccAccount accAccount = accAccountEntityService.getByIdWithControl(id);
        AccAccountDto accAccountDto = AccAccountMapper.INSTANCE.accAccountToAccAccountDto(accAccount);
        return accAccountDto;
    }

    @Transactional
    public AccAccountDto save(AccAccountSaveRequestDto accAccountSaveRequestDto) {
        AccAccount accAccount = createAccAccount(accAccountSaveRequestDto);
        AccAccountDto accAccountDto = AccAccountMapper.INSTANCE.accAccountToAccAccountDto(accAccount);
        return accAccountDto;
    }

    private AccAccount createAccAccount(AccAccountSaveRequestDto accAccountSaveRequestDto) {
        Long currentCustomerId = accAccountEntityService.getCurrentCustomerId();
        String ibanNo = getIbanNo();

        AccAccount accAccount = AccAccountMapper.INSTANCE.accAccountSaveRequestDtoToAccAccount(accAccountSaveRequestDto);
        accAccount.setStatusType(GenStatusType.ACTIVE);
        accAccount.setCusCustomerId(currentCustomerId);
        accAccount.setIbanNo(ibanNo);
        accAccount = accAccountEntityService.save(accAccount);
        return accAccount;
    }

    @Transactional
    public void cancel(Long accountId) {
        AccAccount accAccount = accAccountEntityService.getByIdWithControl(accountId);
        accAccount.setStatusType(GenStatusType.PASSIVE);
        accAccount.setCancelDate(new Date());

        accAccountEntityService.save(accAccount);
    }

    private String getIbanNo() {
        String ibanNo = NumberUtil.getRandomNumberAsString(26);
        return ibanNo;
    }
}
