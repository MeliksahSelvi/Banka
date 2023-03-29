package com.meliksah.banka.app.cus.controller;

import com.meliksah.banka.app.cus.dto.CusCustomerDto;
import com.meliksah.banka.app.cus.dto.CusCustomerSaveRequestDto;
import com.meliksah.banka.app.cus.dto.CusCustomerUpdateRequestDto;
import com.meliksah.banka.app.cus.service.CusCustomerService;
import com.meliksah.banka.app.gen.dto.RestResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tags;
import lombok.RequiredArgsConstructor;
import org.springframework.core.metrics.StartupStep;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/customers")
public class CusCustomerController {

    private final CusCustomerService cusCustomerService;

    @Operation(tags = "Customer Controller",summary = "Get All Customer")
    @GetMapping
    public ResponseEntity findAll(Optional<Integer> pageOptional, Optional<Integer> sizeOptional) {
        List<CusCustomerDto> cusCustomerDtoList = cusCustomerService.findAll(pageOptional,sizeOptional);
        return ResponseEntity.ok(RestResponse.of(cusCustomerDtoList));
    }

    @Operation(tags = "Customer Controller",summary = "Find Customer")
    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable Long id) {
        CusCustomerDto cusCustomerDto = cusCustomerService.findById(id);
        return ResponseEntity.ok(RestResponse.of(cusCustomerDto));
    }

    @Operation(tags = "Customer Controller",summary = "Save Customer")
    @PostMapping
    public ResponseEntity save(@RequestBody CusCustomerSaveRequestDto cusCustomerSaveRequestDto) {
        CusCustomerDto cusCustomerDto = cusCustomerService.save(cusCustomerSaveRequestDto);

        WebMvcLinkBuilder linkGet = WebMvcLinkBuilder.linkTo(//hateoas get linki
                WebMvcLinkBuilder.methodOn(
                        this.getClass()).findById(cusCustomerDto.getId()));

        WebMvcLinkBuilder linkDelete=WebMvcLinkBuilder.linkTo(//hateoas delete linki
                WebMvcLinkBuilder.methodOn(this.getClass()).delete(cusCustomerDto.getId()));

        EntityModel entityModel = EntityModel.of(cusCustomerDto)
                .add(linkGet.withRel("find-by-id"))
                .add(linkDelete.withRel("delete"));

        MappingJacksonValue mappingJacksonValue=new MappingJacksonValue(entityModel);
        return ResponseEntity.ok(RestResponse.of(mappingJacksonValue));
    }

    @Operation(tags = "Customer Controller",summary = "Delete Customer")
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        cusCustomerService.delete(id);
        return ResponseEntity.ok(RestResponse.empty());
    }


    @Operation(tags = "Customer Controller",summary = "Update Customer")
    @PutMapping
    public ResponseEntity update(@RequestBody CusCustomerUpdateRequestDto cusCustomerUpdateRequestDto) {
        CusCustomerDto cusCustomerDto = cusCustomerService.update(cusCustomerUpdateRequestDto);
        return ResponseEntity.ok(RestResponse.of(cusCustomerDto));
    }

}
