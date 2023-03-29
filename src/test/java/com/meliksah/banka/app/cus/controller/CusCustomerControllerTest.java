package com.meliksah.banka.app.cus.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.meliksah.banka.app.cus.dto.CusCustomerSaveRequestDto;
import com.meliksah.banka.app.cus.dto.CusCustomerUpdateRequestDto;
import com.meliksah.banka.app.gen.test.BaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/*
 * Integration Test
 * */
@ExtendWith(MockitoExtension.class)
@SpringBootTest
class CusCustomerControllerTest extends BaseTest {

    private MockMvc mockMvc;

    private static final String BASE_PATH = "/api/v1/customers";

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
        this.objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    }

    @Test
    void shouldFindAll() throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                get(BASE_PATH).content("").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();

        boolean success = isSuccess(mvcResult);
        assertTrue(success);
    }

    @Test
    void shouldFindById() throws Exception {
        Long id = 302L;
        String idAsString = String.valueOf(id);

        MvcResult mvcResult = mockMvc.perform(
                get(BASE_PATH + "/" + idAsString).content(idAsString).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();

        boolean success = isSuccess(mvcResult);
        assertTrue(success);
    }

    @Test
    void shouldSave() throws Exception {

        CusCustomerSaveRequestDto cusCustomerSaveRequestDto = CusCustomerSaveRequestDto.builder()
                .name("ali2")
                .surName("demir2")
                .password("12344")
                .identityNo(123456789L)
                .build();

        String cusCustomerSaveRequestDtoAsString = objectMapper.writeValueAsString(cusCustomerSaveRequestDto);

        MvcResult mvcResult = mockMvc.perform(
                post(BASE_PATH).content(cusCustomerSaveRequestDtoAsString).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();

        boolean success = isSuccess(mvcResult);
        assertTrue(success);
    }

    /*
    * normalde önce kayıt sonra silme işlemi yapan bir shouldDelete methodu yazmamız gerekirdi.
    * biz shouldSave methodu içindekileri aynen buraya yazıp sonra dönen RestResponsenin içinden
    * kaydedilen customerıd'yi alıp delete methodunun içine veri olarak geçerken cast hatası aldığımız için
    * bu işlemi burada yapamadık.Ama normalde test methodları tıklarsın ve çalışır.bizim methodumuz sadece silme işlemi
    * yapıyor bu yüzden 2.defa method çağrıldığında test methodu çalışmayacak.
    * */
    @Test
    void shouldDelete() throws Exception {
        Long id = 8L;
        String idAsString = String.valueOf(id);

        MvcResult mvcResultDelete = mockMvc.perform(
                delete(BASE_PATH + "/" + idAsString).content(idAsString).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();

        boolean success = isSuccess(mvcResultDelete);
        assertTrue(success);
    }

    @Test
    void shouldUpdate() throws Exception {

        CusCustomerUpdateRequestDto cusCustomerUpdateRequestDto = CusCustomerUpdateRequestDto.builder()
                .id(4L)
                .name("deneme2")
                .surName("deneme2")
                .identityNo(888888L)
                .password("7772")
                .build();

        String cusCustomerUpdateRequestDtoAsString = objectMapper.writeValueAsString(cusCustomerUpdateRequestDto);

        MvcResult mvcResultUpdate = mockMvc.perform(
                put(BASE_PATH).content(cusCustomerUpdateRequestDtoAsString).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();

        boolean success = isSuccess(mvcResultUpdate);
        assertTrue(success);
    }
}