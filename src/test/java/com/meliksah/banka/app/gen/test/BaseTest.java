package com.meliksah.banka.app.gen.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.meliksah.banka.app.gen.dto.RestResponse;
import org.springframework.test.web.servlet.MvcResult;

import java.io.UnsupportedEncodingException;

public class BaseTest {

    protected ObjectMapper objectMapper;

    protected boolean isSuccess(MvcResult mvcResult) throws JsonProcessingException, UnsupportedEncodingException {
        RestResponse restResponse = getRestResponse(mvcResult);
        boolean success = isSuccess(restResponse);
        return success;
    }

    protected RestResponse getRestResponse(MvcResult mvcResult) throws JsonProcessingException, UnsupportedEncodingException {
        RestResponse restResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), RestResponse.class);
        return restResponse;
    }

    private boolean isSuccess(RestResponse restResponse) {
        return restResponse.isSuccess();
    }
}
