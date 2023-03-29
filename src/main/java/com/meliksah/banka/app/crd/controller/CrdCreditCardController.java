package com.meliksah.banka.app.crd.controller;

import com.meliksah.banka.app.crd.dto.*;
import com.meliksah.banka.app.crd.service.CrdCreditCardService;
import com.meliksah.banka.app.gen.dto.RestResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/credit-cards")
public class CrdCreditCardController {

    private final CrdCreditCardService crdCreditCardService;

    @Operation(tags = "Credit Card Controller")
    @GetMapping
    public ResponseEntity findAll(Optional<Integer> pageOptional, Optional<Integer> sizeOptional) {
        List<CrdCreditCardReponseDto> crdCreditCardReponseDtoList = crdCreditCardService.findAll(pageOptional,sizeOptional);
        return ResponseEntity.ok(RestResponse.of(crdCreditCardReponseDtoList));
    }

    @Operation(tags = "Credit Card Controller")
    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable Long id) {
        CrdCreditCardReponseDto crdCreditCardReponseDto = crdCreditCardService.findById(id);
        return ResponseEntity.ok(RestResponse.of(crdCreditCardReponseDto));
    }

    @Operation(tags = "Credit Card Controller")
    @PostMapping
    public ResponseEntity save(@RequestBody CrdCreditCardSaveRequestDto crdCreditCardSaveRequestDto) {
        CrdCreditCardReponseDto crdCreditCardReponseDto = crdCreditCardService.saveCreditCard(crdCreditCardSaveRequestDto);
        return ResponseEntity.ok(RestResponse.of(crdCreditCardReponseDto));
    }

    @Operation(tags = "Credit Card Controller")
    @PatchMapping("/cancel/{cardId}")
    public ResponseEntity cancel(@PathVariable Long cardId) {
        crdCreditCardService.cancel(cardId);
        return ResponseEntity.ok(RestResponse.empty());
    }

    @Operation(tags = "Credit Card Controller")
    @PostMapping("/spend")
    public ResponseEntity spend(@RequestBody CrdCreditCardSpendDto crdCreditCardSpendDto) {
        CrdCreditCardActivityDto crdCreditCardActivityDto = crdCreditCardService.spend(crdCreditCardSpendDto);
        return ResponseEntity.ok(RestResponse.of(crdCreditCardActivityDto));
    }

    @Operation(tags = "Credit Card Controller")
    @PostMapping("/refund/{activityId}")
    public ResponseEntity refund(@PathVariable Long activityId) {
        CrdCreditCardActivityDto crdCreditCardActivityDto = crdCreditCardService.refund(activityId);
        return ResponseEntity.ok(RestResponse.of(crdCreditCardActivityDto));
    }

    @Operation(tags = "Credit Card Controller")
    @PostMapping("/payment")
    public ResponseEntity payment(@RequestBody CrdCreditCardPaymentDto crdCreditCardPaymentDto) {
        CrdCreditCardActivityDto crdCreditCardActivityDto = crdCreditCardService.payment(crdCreditCardPaymentDto);
        return ResponseEntity.ok(RestResponse.of(crdCreditCardActivityDto));
    }

    @Operation(tags = "Credit Card Controller")
    @GetMapping("/{cardId}/activities")
    public ResponseEntity findAllActivities(
            @PathVariable Long cardId,
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate,
            Optional<Integer> pageOptional, Optional<Integer> sizeOptional) {

        List<CrdCreditCardActivityDto> crdCreditCardActivityDtoList = crdCreditCardService.findAllActivities(cardId, startDate, endDate,pageOptional,sizeOptional);
        return ResponseEntity.ok(RestResponse.of(crdCreditCardActivityDtoList));
    }

    @Operation(tags = "Credit Card Controller")
    @GetMapping("/{cardId}/statements")
    public ResponseEntity statement(@PathVariable Long cardId) {
        CrdCreditCardDetails crdCreditCardDetails = crdCreditCardService.statement(cardId);
        return ResponseEntity.ok(RestResponse.of(crdCreditCardDetails));
    }

}
