package com.meliksah.banka.app.cus.service;

import com.meliksah.banka.app.cus.domain.CusCustomer;
import com.meliksah.banka.app.cus.dto.CusCustomerDto;
import com.meliksah.banka.app.cus.dto.CusCustomerSaveRequestDto;
import com.meliksah.banka.app.cus.dto.CusCustomerUpdateRequestDto;
import com.meliksah.banka.app.cus.enums.CusErrorMessage;
import com.meliksah.banka.app.cus.mapper.CusCustomerMapper;
import com.meliksah.banka.app.cus.service.entityservice.CusCustomerEntityService;
import com.meliksah.banka.app.gen.exception.exceptions.ItemNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CusCustomerService {

    private final CusCustomerEntityService cusCustomerEntityService;
    private final PasswordEncoder passwordEncoder;
    public List<CusCustomerDto> findAll() {

        List<CusCustomer> cusCustomerList = cusCustomerEntityService.findAll();

        List<CusCustomerDto> cusCustomerDtoList = CusCustomerMapper.INSTANCE.cusCustomerListToCusCustomerDtoList(cusCustomerList);

        return cusCustomerDtoList;
    }

    public List<CusCustomerDto> findAll(Optional<Integer> pageOptional, Optional<Integer> sizeOptional) {

        List<CusCustomer> cusCustomerList = cusCustomerEntityService.findAll(pageOptional,sizeOptional);

        List<CusCustomerDto> cusCustomerDtoList = CusCustomerMapper.INSTANCE.cusCustomerListToCusCustomerDtoList(cusCustomerList);

        return cusCustomerDtoList;
    }

    public CusCustomerDto save(CusCustomerSaveRequestDto cusCustomerSaveRequestDto) {

        CusCustomer cusCustomer = CusCustomerMapper.INSTANCE.cusCustomerSaveRequestDtoToCusCustomer(cusCustomerSaveRequestDto);

        String password = passwordEncoder.encode(cusCustomer.getPassword());
        cusCustomer.setPassword(password);

        cusCustomer = cusCustomerEntityService.save(cusCustomer);

        CusCustomerDto cusCustomerDto = CusCustomerMapper.INSTANCE.cusCustomerToCusCustomerDto(cusCustomer);
        return cusCustomerDto;
    }

    public void delete(Long id) {
        CusCustomer cusCustomerWithControl = cusCustomerEntityService.getByIdWithControl(id);

        cusCustomerEntityService.delete(cusCustomerWithControl);
    }

    public CusCustomerDto findById(Long id) {
        CusCustomer cusCustomer = cusCustomerEntityService.getByIdWithControl(id);

        CusCustomerDto cusCustomerDto = CusCustomerMapper.INSTANCE.cusCustomerToCusCustomerDto(cusCustomer);
        return cusCustomerDto;
    }


    public CusCustomerDto update(CusCustomerUpdateRequestDto cusCustomerUpdateRequestDto) {

        controlIsCustomerExist(cusCustomerUpdateRequestDto);

        CusCustomer cusCustomer = CusCustomerMapper.INSTANCE.cusCustomerUpdateRequestDtoToCusCustomer(cusCustomerUpdateRequestDto);
        cusCustomer = cusCustomerEntityService.save(cusCustomer);

        CusCustomerDto cusCustomerDto = CusCustomerMapper.INSTANCE.cusCustomerToCusCustomerDto(cusCustomer);
        return cusCustomerDto;
    }

    private void controlIsCustomerExist(CusCustomerUpdateRequestDto cusCustomerUpdateRequestDto) {

        Long id = cusCustomerUpdateRequestDto.getId();
        boolean isExist = cusCustomerEntityService.existsById(id);
        if (!isExist) {
            throw new ItemNotFoundException(CusErrorMessage.CUSTOMER_NOT_FOUND);
        }
    }
}
