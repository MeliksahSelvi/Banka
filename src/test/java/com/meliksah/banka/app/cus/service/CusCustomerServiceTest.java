package com.meliksah.banka.app.cus.service;

import com.meliksah.banka.app.cus.domain.CusCustomer;
import com.meliksah.banka.app.cus.dto.CusCustomerDto;
import com.meliksah.banka.app.cus.dto.CusCustomerSaveRequestDto;
import com.meliksah.banka.app.cus.dto.CusCustomerUpdateRequestDto;
import com.meliksah.banka.app.cus.service.entityservice.CusCustomerEntityService;
import com.meliksah.banka.app.gen.exception.exceptions.ItemNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

/*
 * UnitTest
 * */
@ExtendWith(MockitoExtension.class)
class CusCustomerServiceTest {

    /*
     * Uygulama ayakta olmadığı için applicationcontext kapalı kendimiz mock işlemleri ile bean'ler oluşmuş gibi
     * taklit ediyoruz.Mock'lanan bean'ler InjectMock'lanan bean'lere taklit ile inject edilir.
     *
     * */
    @InjectMocks
    private CusCustomerService cusCustomerService;

    @Mock
    private CusCustomerEntityService cusCustomerEntityService;

    @Mock
    private PasswordEncoder passwordEncoder;

    /*
     * when methoduna deriz ki şu cusCustomerEntityService.findAll() methodu çalışınca cusCustomerList döndür.
     * Ayrıca methodların parametrelerini değiştirebiliriz. Mesela ArgumentMatchers.anyList()) parametresini verirsek
     * herhangi bir list türünde parametresi olan  method çağrılınca şunu dön diyebiliriz.
     * */
    @Test
    void shouldFindAll() {

        CusCustomer cusCustomer = mock(CusCustomer.class);
        List<CusCustomer> cusCustomerList = new ArrayList<>();
        cusCustomerList.add(cusCustomer);

        when(cusCustomerEntityService.findAll()).thenReturn(cusCustomerList);

        List<CusCustomerDto> result = cusCustomerService.findAll();

        assertEquals(cusCustomerList.size(), result.size());
    }


    @Test
    void shouldFindAllWhenCustomerListIsEmpty() {

        when(cusCustomerEntityService.findAll()).thenReturn(new ArrayList<>());

        List<CusCustomerDto> result = cusCustomerService.findAll();

        assertEquals(0, result.size());
    }

    @Test
    void shouldFindAllWhenCustomerListIsNull() {

        when(cusCustomerEntityService.findAll()).thenReturn(null);

        assertEquals(null, cusCustomerService.findAll());
    }

    @Test
    void shouldSave() {
        CusCustomerSaveRequestDto cusCustomerSaveRequestDto = mock(CusCustomerSaveRequestDto.class);
        when(cusCustomerSaveRequestDto.getPassword()).thenReturn("123");
        CusCustomer cusCustomer = mock(CusCustomer.class);
        when(cusCustomer.getId()).thenReturn(1L);

        when(passwordEncoder.encode("123")).thenReturn("123_melik_delik");
        when(cusCustomerEntityService.save(any())).thenReturn(cusCustomer);

        CusCustomerDto result = cusCustomerService.save(cusCustomerSaveRequestDto);

        assertEquals(1L, result.getId());
    }


    @Test
    void shouldNotSaveParameterIsNull() {
        assertThrows(NullPointerException.class, () -> cusCustomerService.save(null));
    }

    /*
     * verify belirttiğimiz methodun sonuna ulaşıp ulaşılmadığını kontrol için kullanılır.
     * */
    @Test
    void shouldDelete() {
        CusCustomer cusCustomer = mock(CusCustomer.class);

        when(cusCustomerEntityService.getByIdWithControl(anyLong())).thenReturn(cusCustomer);

        cusCustomerService.delete(anyLong());

        verify(cusCustomerEntityService).getByIdWithControl(anyLong());
        verify(cusCustomerEntityService).delete(any());
    }

    @Test
    void shouldNotDeleteWhenIdDoesNotExist() {

        when(cusCustomerEntityService.getByIdWithControl(anyLong())).thenThrow(ItemNotFoundException.class);
        assertThrows(ItemNotFoundException.class, () -> cusCustomerService.delete(anyLong()));

        verify(cusCustomerEntityService).getByIdWithControl(anyLong());
    }

    @Test
    void shouldFindById() {
        Long id = 3L;
        CusCustomer cusCustomer = mock(CusCustomer.class);
        when(cusCustomer.getId()).thenReturn(id);
        when(cusCustomerEntityService.getByIdWithControl(id)).thenReturn(cusCustomer);

        CusCustomerDto result = cusCustomerService.findById(id);

        assertEquals(id, result.getId());
    }

    @Test
    void shouldNotFindByIdWhenIdDoesNotExist() {
        when(cusCustomerEntityService.getByIdWithControl(anyLong())).thenThrow(ItemNotFoundException.class);
        assertThrows(ItemNotFoundException.class, () -> cusCustomerService.findById(anyLong()));

        verify(cusCustomerEntityService).getByIdWithControl(anyLong());
    }

    @Test
    void shouldUpdate() {
        Long identityNo = 123L;
        CusCustomerUpdateRequestDto updateRequestDto = mock(CusCustomerUpdateRequestDto.class);

        CusCustomer cusCustomer = mock(CusCustomer.class);
        when(cusCustomer.getIdentityNo()).thenReturn(identityNo);

        when(cusCustomerEntityService.existsById(anyLong())).thenReturn(true);
        when(cusCustomerEntityService.save(any())).thenReturn(cusCustomer);

        CusCustomerDto result = cusCustomerService.update(updateRequestDto);

        assertEquals(identityNo, result.getIdentityNo());

        verify(cusCustomerEntityService).existsById(anyLong());
        verify(cusCustomerEntityService).save(any());
    }

    @Test
    void shouldNotUpdateWhenIdDoesNotExist() {
        CusCustomerUpdateRequestDto updateRequestDto = mock(CusCustomerUpdateRequestDto.class);

        when(cusCustomerEntityService.existsById(anyLong())).thenReturn(false);
        assertThrows(ItemNotFoundException.class, () -> cusCustomerService.update(updateRequestDto));

        verify(cusCustomerEntityService).existsById(anyLong());
    }
}